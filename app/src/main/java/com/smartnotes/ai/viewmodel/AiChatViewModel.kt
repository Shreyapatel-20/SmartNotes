package com.smartnotes.ai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.UnknownHostException
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class ChatMessageItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val timestamp: String = "Just now",
    val isError: Boolean = false,
    val showAttachment: Boolean = false,
    val isLocalOnly: Boolean = false
)

data class AiChatUiState(
    val messages: List<ChatMessageItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class AiChatViewModel(private val apiKey: String) : ViewModel() {

    private val _uiState = MutableStateFlow(AiChatUiState())
    val uiState: StateFlow<AiChatUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                messages = listOf(
                    ChatMessageItem(
                        text = "Hello! I am your AI Assistant powered by Gemini. Ask me anything about your notes, meetings, or general questions!",
                        isUser = false,
                        timestamp = "Just now",
                        isLocalOnly = true
                    )
                )
            )
        }
    }

    fun sendMessage(prompt: String) {
        val trimmedPrompt = prompt.trim()
        if (trimmedPrompt.isEmpty()) return

        val currentState = _uiState.value
        if (currentState.isLoading) return

        // Capture only completed conversation turns before adding the current prompt.
        val conversationHistory = completedConversation(
            currentState.messages.filter { !it.isError && !it.isLocalOnly }
        )
        val userMsg = ChatMessageItem(
            text = trimmedPrompt,
            isUser = true,
            timestamp = getCurrentTime()
        )

        _uiState.update { state ->
            state.copy(
                messages = state.messages + userMsg,
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            try {
                val configuredApiKey = apiKey.trim().takeIf { it.isNotBlank() }
                    ?: throw MissingGeminiApiKeyException()
                val replyText = callGeminiRestApi(trimmedPrompt, configuredApiKey, conversationHistory)
                val aiMsg = ChatMessageItem(
                    text = replyText,
                    isUser = false,
                    timestamp = getCurrentTime()
                )
                _uiState.update { state ->
                    state.copy(messages = state.messages + aiMsg, isLoading = false)
                }
            } catch (e: Exception) {
                val friendlyError = getFriendlyError(e)
                val errorMsg = ChatMessageItem(
                    text = friendlyError,
                    isUser = false,
                    timestamp = getCurrentTime(),
                    isError = true
                )

                _uiState.update { state ->
                    state.copy(
                        messages = state.messages + errorMsg,
                        isLoading = false,
                        errorMessage = friendlyError
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun getFriendlyError(error: Exception): String = when (error) {
        is MissingGeminiApiKeyException -> "Add your Gemini API key using the API key button, then try again."
        is GeminiApiException -> when (error.statusCode) {
            400 -> "Gemini rejected the request: ${error.apiMessage}"
            401 -> "Gemini rejected the API key. Check the key in Google AI Studio and update it here."
            403 -> "This key or project cannot use the Gemini API. Check its API access and free-tier eligibility in Google AI Studio."
            404 -> "The Gemini model or API endpoint was not found. Check the model name or API availability."
            429 -> "Gemini free-tier quota or rate limit reached. Check your usage and try again later."
            in 500..599 -> "Gemini is temporarily unavailable. Please try again shortly."
            else -> "Gemini request failed (HTTP ${error.statusCode}): ${error.apiMessage}"
        }
        is GeminiResponseException -> error.message ?: "Gemini returned no text. Please try again."
        is UnknownHostException -> "Network error. Check your internet connection and try again."
        is SocketTimeoutException -> "Gemini took too long to respond. Please try again."
        is IOException -> "Network error while contacting Gemini: ${error.localizedMessage ?: "Please try again."}"
        else -> "Could not get a Gemini response: ${error.localizedMessage ?: "Please try again."}"
    }

    private fun getCurrentTime(): String = try {
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    } catch (_: Exception) {
        "Just now"
    }

    private fun completedConversation(messages: List<ChatMessageItem>): List<ChatMessageItem> {
        val completed = mutableListOf<ChatMessageItem>()
        var pendingUserMessage: ChatMessageItem? = null

        messages.forEach { message ->
            if (message.isUser) {
                pendingUserMessage = message
            } else {
                pendingUserMessage?.let { userMessage ->
                    completed += userMessage
                    completed += message
                    pendingUserMessage = null
                }
            }
        }
        return completed
    }

    private suspend fun callGeminiRestApi(
        prompt: String,
        apiKey: String,
        history: List<ChatMessageItem>
    ): String = withContext(Dispatchers.IO) {
        val contentsArray = JSONArray()
        history
            .takeLast(MAX_HISTORY_MESSAGES)
            .dropWhile { !it.isUser }
            .forEach { message -> contentsArray.put(message.toGeminiContent()) }
        contentsArray.put(ChatMessageItem(text = prompt, isUser = true).toGeminiContent())

        val requestBody = JSONObject().put("contents", contentsArray)
        val connection = (URL("$API_BASE_URL/$MODEL_NAME:generateContent").openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("x-goog-api-key", apiKey)
            connectTimeout = CONNECTION_TIMEOUT_MS
            readTimeout = READ_TIMEOUT_MS
            doOutput = true
        }

        try {
            connection.outputStream.use { output ->
                output.write(requestBody.toString().toByteArray(StandardCharsets.UTF_8))
            }

            val responseCode = connection.responseCode
            val responseBody = (if (responseCode in 200..299) connection.inputStream else connection.errorStream)
                ?.bufferedReader(StandardCharsets.UTF_8)
                ?.use { it.readText() }
                .orEmpty()

            if (responseCode !in 200..299) {
                val apiMessage = try {
                    JSONObject(responseBody).optJSONObject("error")
                        ?.optString("message")
                        ?.takeIf { it.isNotBlank() }
                } catch (_: Exception) {
                    null
                } ?: "No error details returned."
                throw GeminiApiException(responseCode, apiMessage)
            }

            parseGeminiResponse(responseBody)
        } finally {
            connection.disconnect()
        }
    }

    private fun parseGeminiResponse(responseBody: String): String {
        val response = JSONObject(responseBody)
        val candidates = response.optJSONArray("candidates")
        val firstCandidate = candidates?.optJSONObject(0)
            ?: throw GeminiResponseException("Gemini did not return a response candidate. Please try rephrasing your message.")
        val parts = firstCandidate.optJSONObject("content")?.optJSONArray("parts")
        val reply = buildString {
            if (parts != null) {
                for (index in 0 until parts.length()) {
                    val part = parts.optJSONObject(index) ?: continue
                    if (part.has("text") && part.opt("text") != JSONObject.NULL) {
                        append(part.optString("text"))
                    }
                }
            }
        }.trim()

        if (reply.isEmpty()) {
            val blockReason = response.optJSONObject("promptFeedback")?.optString("blockReason")
            val message = if (!blockReason.isNullOrBlank()) {
                "Gemini blocked this prompt ($blockReason). Please rephrase it."
            } else {
                "Gemini returned no text. Please try again."
            }
            throw GeminiResponseException(message)
        }
        return reply
    }

    private fun ChatMessageItem.toGeminiContent(): JSONObject = JSONObject().apply {
        put("role", if (isUser) "user" else "model")
        put("parts", JSONArray().put(JSONObject().put("text", text)))
    }

    private class MissingGeminiApiKeyException : Exception()

    private class GeminiResponseException(message: String) : Exception(message)

    private class GeminiApiException(val statusCode: Int, val apiMessage: String) :
        Exception("Gemini API error $statusCode: $apiMessage")

    private companion object {
        const val API_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"
        const val MODEL_NAME = "gemini-3.5-flash-lite"
        const val MAX_HISTORY_MESSAGES = 12
        const val CONNECTION_TIMEOUT_MS = 15_000
        const val READ_TIMEOUT_MS = 30_000
    }
}
