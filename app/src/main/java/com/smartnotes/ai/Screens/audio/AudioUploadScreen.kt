package com.smartnotes.ai.Screens.audio

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.Screens.common.dashedBorder
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

private fun getAudioFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    result = cursor.getString(nameIndex)
                }
            }
        } finally {
            cursor?.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}

@Composable
fun AudioUploadScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: (title: String, fileUri: String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    var hasSelectedFile by remember { mutableStateOf(false) }
    var selectedFileName by remember { mutableStateOf("Audio Recording.mp3") }
    var selectedFileUri by remember { mutableStateOf("") }
    var audioTitle by remember { mutableStateOf("") }
    var uploadError by remember { mutableStateOf<String?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            try {
                context.contentResolver.takePersistableUriPermission(
                    selectedUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) {
            }
            val name = getAudioFileName(context, selectedUri) ?: "Audio.mp3"
            selectedFileName = name
            selectedFileUri = selectedUri.toString()
            audioTitle = name.substringBeforeLast(".")
            hasSelectedFile = true
            uploadError = null
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // BACKGROUND IMAGE
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 12.dp)
                    .height(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.Transparent)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = DarkText,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Audio Upload",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(36.dp))
            }

            Spacer(modifier = Modifier.height(70.dp))

            // DASHED DROPZONE BOX
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .dashedBorder(Color(0xFF000000), 2.dp, 10.dp, 8.dp, 30.dp)
                    .background(Color.White.copy(alpha = 0.4f))
                    .clickable {
                        filePickerLauncher.launch(
                            arrayOf(
                                "audio/*",
                                "application/ogg",
                                "application/x-flac",
                                "audio/mpeg",
                                "audio/wav",
                                "*/*"
                            )
                        )
                    }
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFC4B5FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.container),
                            contentDescription = "Upload",
                            tint = Color(0xFF000000),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (hasSelectedFile) selectedFileName else "Audio Documents",
                        fontFamily = Lufga,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (hasSelectedFile) "Selected Audio File" else ".mp3, .wav, .flac, .m4a, etc...",
                        fontFamily = Lufga,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF48464D),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        modifier = Modifier
                            .height(40.dp)
                            .width(137.dp),
                        onClick = {
                            filePickerLauncher.launch(
                                arrayOf(
                                    "audio/*",
                                    "application/ogg",
                                    "application/x-flac",
                                    "audio/mpeg",
                                    "audio/wav",
                                    "*/*"
                                )
                            )
                        },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE5DEFF)
                        )
                    ) {
                        Text(
                            text = if (hasSelectedFile) "Change Audio" else "Choose Audio",
                            fontFamily = Lufga,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF60597F)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(34.dp))

            // AUDIO TITLE SECTION
            Text(
                text = "Audio Title",
                fontFamily = Lufga,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // TITLE TEXT FIELD
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = audioTitle,
                    onValueChange = {
                        audioTitle = it
                        uploadError = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        color = DarkText
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (audioTitle.isEmpty()) {
                            Text(
                                text = "Enter Audio Title",
                                fontFamily = Lufga,
                                fontSize = 15.sp,
                                color = Color(0xFF808080)
                            )
                        }
                        innerTextField()
                    }
                )
            }

            uploadError?.let { error ->
                Text(
                    text = error,
                    color = Color(0xFFB00020),
                    fontFamily = Lufga,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // BOTTOM CONTINUE BUTTON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp)
            ) {
                Button(
                    onClick = {
                        when {
                            !hasSelectedFile || selectedFileUri.isBlank() -> {
                                uploadError = "Choose an audio file before continuing."
                            }
                            audioTitle.isBlank() -> {
                                uploadError = "Enter a title for the audio file."
                            }
                            else -> onContinueClick(audioTitle.trim(), selectedFileUri)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Continue",
                        color = Color.White,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AudioUploadScreenPreview() {
    AudioUploadScreen()
}
