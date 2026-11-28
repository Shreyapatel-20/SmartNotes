package com.smartnotes.ai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID


data class ChatMessageItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val timestamp: String = "Just now",
    val isError: Boolean = false,
    val showAttachment: Boolean = false
)

data class AiChatUiState(
    val messages: List<ChatMessageItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class AiChatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AiChatUiState())
    val uiState: StateFlow<AiChatUiState> = _uiState.asStateFlow()

    private val apiKey = "YOUR_api_key"
    //private val apiKey= BuildConfig.GEMINI_API_KEY
    private val generativeModel by lazy {
        try {
            GenerativeModel(
                modelName = "gemini-2.0-flash",
                apiKey = apiKey
            )
        } catch (_: Throwable) {
            null
        }
    }

    private val chatSession by lazy {
        try {
            generativeModel?.startChat()
        } catch (_: Throwable) {
            null
        }
    }

    init {
        // Welcome message
        _uiState.update { currentState ->
            currentState.copy(
                messages = listOf(
                    ChatMessageItem(
                        text = "Hello! I am your AI Assistant powered by Gemini. Ask me anything about your notes, meetings, or general questions!",
                        isUser = false,
                        timestamp = "Just now"
                    )
                )
            )
        }
    }

    fun sendMessage(prompt: String) {
        val trimmedPrompt = prompt.trim()
        if (trimmedPrompt.isEmpty()) return

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

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val replyText = callGeminiRestApi(trimmedPrompt, apiKey, _uiState.value.messages)
                val aiMsg = ChatMessageItem(
                    text = replyText,
                    isUser = false,
                    timestamp = getCurrentTime()
                )
                _uiState.update { state ->
                    state.copy(
                        messages = state.messages + aiMsg,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                val friendlyError = when {
                    e.message?.contains("429") == true || e.message?.contains("quota") == true ->
                        "Rate limit reached. Please wait a moment and try again."
                    e.message?.contains("401") == true ->
                        "Authentication failed. Please check your Gemini API key."
                    e.message?.contains("Unable to resolve host") == true || e is java.net.UnknownHostException ->
                        "Network error. Please check your internet connection."
                    else -> "Error: ${e.localizedMessage ?: "Failed to receive response"}"
                }

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

    private fun getCurrentTime(): String {
        return try {
            val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
            sdf.format(java.util.Date())
        } catch (_: Exception) {
            "Just now"
        }
    }

    private suspend fun callGeminiRestApi(
        prompt: String,
        apiKey: String,
        history: List<ChatMessageItem>
    ): String {
        return withContext(Dispatchers.IO) {
            val modelsToTry = listOf("gemini-1.5-flash", "gemini-1.5-pro", "gemini-pro", "gemini-2.0-flash")
            var lastError = "Failed to connect to Gemini."

            for (model in modelsToTry) {
                try {
                    val encodedKey = java.net.URLEncoder.encode(apiKey, "UTF-8")
                    val urlString = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$encodedKey"

                    val connection = (URL(urlString).openConnection() as HttpURLConnection).apply {
                        requestMethod = "POST"
                        setRequestProperty("Content-Type", "application/json")
                        setRequestProperty("x-goog-api-key", apiKey)
                        setRequestProperty("Authorization", "Bearer $apiKey")
                        doOutput = true
                        connectTimeout = 15000
                        readTimeout = 15000
                    }

                    val contentsArray = JSONArray()
                    for (msg in history.takeLast(10)) {
                        if (msg.isError) continue
                        val role = if (msg.isUser) "user" else "model"
                        contentsArray.put(JSONObject().apply {
                            put("role", role)
                            put("parts", JSONArray().apply {
                                put(JSONObject().apply {
                                    put("text", msg.text)
                                })
                            })
                        })
                    }

                    contentsArray.put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })

                    val jsonBody = JSONObject().apply {
                        put("contents", contentsArray)
                    }

                    OutputStreamWriter(connection.outputStream).use { writer ->
                        writer.write(jsonBody.toString())
                        writer.flush()
                    }

                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val responseString = connection.inputStream.bufferedReader().use { it.readText() }
                        val jsonResponse = JSONObject(responseString)
                        val candidates = jsonResponse.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val candidate = candidates.getJSONObject(0)
                            val content = candidate.optJSONObject("content")
                            val parts = content?.optJSONArray("parts")
                            if (parts != null && parts.length() > 0) {
                                return@withContext parts.getJSONObject(0).optString("text", "No text response.")
                            }
                        }
                        return@withContext "No valid response received from Gemini."
                    } else {
                        val errorString = connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "Unknown error"
                        lastError = when (responseCode) {
                            429 -> "Rate limit reached (429). Please wait a few seconds and try again."
                            401 -> "API Error (401): Unauthorized key."
                            else -> "API Error ($responseCode): $errorString"
                        }
                        if (responseCode != 404) {
                            return@withContext lastError
                        }
                    }
                } catch (e: Exception) {
                    lastError = "Network error: ${e.localizedMessage ?: "Please check internet connection"}"
                }
            }
            lastError
        }
    }
}
