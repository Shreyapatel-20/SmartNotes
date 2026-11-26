package com.smartnotes.ai.Screens

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.smartnotes.ai.R
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val DarkText = Color(0xFF242529)

@Composable
fun RecordingAudioScreen(
    onBackClick: () -> Unit = {},
    onCloseClick: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var isRecording by remember { mutableStateOf(false) }
    var recordedFilePath by remember { mutableStateOf("") }
    var mediaRecorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var showNameDialog by remember { mutableStateOf(false) }
    var customRecordingName by remember { mutableStateOf("Voice Recording") }

    val startRecording = {
        try {
            val file = File(context.cacheDir, "recording_${System.currentTimeMillis()}.mp3")
            val recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
            mediaRecorder = recorder
            isRecording = true
            recordedFilePath = file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val stopRecording = {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
        } catch (_: Exception) {
        }
        mediaRecorder = null
        isRecording = false
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startRecording()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                mediaRecorder?.stop()
                mediaRecorder?.release()
            } catch (_: Exception) {
            }
        }
    }

    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = {
                showNameDialog = false
                onCloseClick()
            },
            title = {
                Text("Name Your Recording", fontFamily = Lufga, fontWeight = FontWeight.Bold)
            },
            text = {
                OutlinedTextField(
                    value = customRecordingName,
                    onValueChange = { customRecordingName = it },
                    singleLine = true,
                    textStyle = TextStyle(fontFamily = Lufga),
                    label = { Text("Recording Name", fontFamily = Lufga) }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showNameDialog = false
                        if (recordedFilePath.isNotBlank()) {
                            val timestamp = SimpleDateFormat("dd/MM/yy • hh:mm a", Locale.getDefault()).format(Date())
                            dbHelper?.insertAudioFile(
                                title = customRecordingName.ifBlank { "Voice Recording" },
                                timestamp = timestamp,
                                fileUri = recordedFilePath
                            )
                        }
                        onCloseClick()
                    }
                ) {
                    Text("Save", fontFamily = Lufga, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showNameDialog = false
                        if (recordedFilePath.isNotBlank()) {
                            val timestamp = SimpleDateFormat("dd/MM/yy • hh:mm a", Locale.getDefault()).format(Date())
                            dbHelper?.insertAudioFile(
                                title = "Voice Recording",
                                timestamp = timestamp,
                                fileUri = recordedFilePath
                            )
                        }
                        onCloseClick()
                    }
                ) {
                    Text("Skip", fontFamily = Lufga)
                }
            }
        )
    }

    val waveformHeights = listOf(
        25.dp, 40.dp, 58.dp, 28.dp,
        75.dp, 53.dp, 38.dp, 35.dp,
        47.dp, 59.dp, 75.dp, 47.dp,
        35.dp, 35.dp
    )

    val infiniteTransition = rememberInfiniteTransition(label = "waveAnim")
    val phase = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

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
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 58.dp, bottom = 20.dp)
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.Transparent)
                        .clickable {
                            stopRecording()
                            onBackClick()
                        },
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
                    text = if (isRecording) "Recording..." else "Recording",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(36.dp))
            }

            Spacer(modifier = Modifier.size(20.dp))

            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .shadow(
                            elevation = 24.dp,
                            shape = CircleShape,
                            spotColor = Color(0xFFC084FC).copy(alpha = 0.35f)
                        )
                        .clip(CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color(0xFF60A5FF),
                                    0.33f to Color(0xFF7AFBFF),
                                    0.59f to Color(0xFFA9C6FF),
                                    1.0f to Color(0xFFE8A2FF)
                                )
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.weight(0.3f))
            Spacer(modifier = Modifier.size(20.dp))

            // AUDIO WAVEFORM
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(95.78.dp).fillMaxWidth()
            ) {
                waveformHeights.forEachIndexed { index, baseHeight ->
                    val currentHeight = if (isRecording) {
                        baseHeight * (0.5f + 0.5f * kotlin.math.abs(kotlin.math.sin(Math.toRadians((phase.value + index * 30).toDouble()))).toFloat())
                    } else {
                        baseHeight
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.5.dp)
                            .width(6.dp)
                            .height(currentHeight)
                            .clip(RoundedCornerShape(50))
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.0f to Color(0xffD970DE),
                                        100.0f to Color(0xff9B86FD)
                                    )
                                )
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SUBTITLE TEXT
            Text(
                text = if (isRecording) "Recording audio in progress..." else "Tap mic to start recording\nor organize meetings.",
                fontFamily = Lufga,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF808080),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.size(30.dp))

            // MIC & CLOSE CONTROLS
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                // MIC BUTTON
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)
                        .shadow(12.dp, CircleShape, spotColor = Color(0x339333EA))
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFD970DE), Color(0xFF9B86FD))
                            )
                        )
                        .clickable {
                            if (isRecording) {
                                stopRecording()
                            } else {
                                val permissionCheck = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.RECORD_AUDIO
                                )
                                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                                    startRecording()
                                } else {
                                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.microphone),
                        contentDescription = "Microphone",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // CLOSE BUTTON
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterEnd)
                        .clip(CircleShape)
                        .background(Color(0xFFF3F4F6))
                        .clickable {
                            stopRecording()
                            onCloseClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = DarkText,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.size(20.dp))

            // REPLAY & DONE BUTTONS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        stopRecording()
                        if (recordedFilePath.isNotBlank()) {
                            try {
                                File(recordedFilePath).delete()
                            } catch (_: Exception) {
                            }
                        }
                        recordedFilePath = ""
                        val permissionCheck = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        )
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            startRecording()
                        } else {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Replay",
                        color = Color.White,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                }

                Button(
                    onClick = {
                        stopRecording()
                        if (recordedFilePath.isNotBlank()) {
                            showNameDialog = true
                        } else {
                            onCloseClick()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Done",
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
fun RecordingAudioScreenPreview() {
    RecordingAudioScreen()
}
