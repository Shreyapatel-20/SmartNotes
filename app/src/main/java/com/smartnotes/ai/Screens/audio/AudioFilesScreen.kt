package com.smartnotes.ai.Screens.audio

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.smartnotes.ai.R
import com.smartnotes.ai.db.AudioFileModel
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga
import kotlinx.coroutines.delay
import java.util.Locale

private val DarkText = Color(0xFF242529)

private fun formatMillis(millis: Int): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    return String.format(Locale.getDefault(), "%d : %02d s", minutes, seconds)
}

@Composable
fun AudioFilesScreen(
    onBackClick: () -> Unit = {},
    onNewAudioFileClick: () -> Unit = {},
    onEditAudioClick: (String) -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var searchQuery by remember { mutableStateOf("") }
    var audioFilesList by remember {
        mutableStateOf(
            if (isPreview) {
                listOf(
                    AudioFileModel(1, "Project Meeting", "12 : 45 s", "Today • 9:58 pm", ""),
                    AudioFileModel(2, "Team Meeting", "8 : 20 s", "Today • 10:15 am", "")
                )
            } else {
                emptyList()
            }
        )
    }

    LaunchedEffect(Unit) {
        if (!isPreview && dbHelper != null) {
            audioFilesList = dbHelper.getAllAudioFiles()
        }
    }

    val filteredList = audioFilesList.filter { file ->
        searchQuery.isBlank() ||
        file.title.contains(searchQuery, ignoreCase = true) ||
        file.timestamp.contains(searchQuery, ignoreCase = true)
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
                .padding(horizontal = 16.dp)
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 12.dp)
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
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
                    text = "Audio Files",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(36.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // SEARCH BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search",
                    tint = Color(0xFF808080),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        color = DarkText
                    ),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search",
                                fontFamily = Lufga,
                                fontSize = 16.sp,
                                color = Color(0xFF999999)
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECTION TITLE
            Text(
                text = "All Audio files",
                fontFamily = Lufga,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            // AUDIO FILES LIST
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                if (filteredList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.isNotBlank()) "No audio files found matching '$searchQuery'" else "No audio files uploaded yet.",
                                fontFamily = Lufga,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(
                        items = filteredList,
                        key = { it.id }
                    ) { file ->
                        var showEditDialog by remember { mutableStateOf(false) }
                        var newTitleText by remember { mutableStateOf(file.title) }

                        if (showEditDialog) {
                            AlertDialog(
                                onDismissRequest = { showEditDialog = false },
                                title = {
                                    Text("Edit Audio Name", fontFamily = Lufga, fontWeight = FontWeight.Bold)
                                },
                                text = {
                                    OutlinedTextField(
                                        value = newTitleText,
                                        onValueChange = { newTitleText = it },
                                        singleLine = true,
                                        textStyle = TextStyle(fontFamily = Lufga)
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showEditDialog = false
                                            dbHelper?.updateAudioFileTitle(file.id, newTitleText)
                                            audioFilesList = dbHelper?.getAllAudioFiles() ?: emptyList()
                                        }
                                    ) {
                                        Text("Save", fontFamily = Lufga, fontWeight = FontWeight.Bold)
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showEditDialog = false }) {
                                        Text("Cancel", fontFamily = Lufga)
                                    }
                                }
                            )
                        }

                        val cardBg = if (file.id % 2 == 0) Color(0xFFF1E4FF) else Color(0xFFFFF6D6)
                        val accent = if (file.id % 2 == 0) Color(0xffC67FFC) else Color(0xFFFFD86C)
                        val iconBg = if (file.id % 2 == 0) Color(0xFFF9F5FF) else Color(0xFFFFFDF5)

                        SwipeableAudioCard(
                            file = file,
                            cardBg = cardBg,
                            accent = accent,
                            iconBg = iconBg,
                            onEditClick = { showEditDialog = true },
                            onDelete = {
                                dbHelper?.deleteAudioFile(file.id)
                                audioFilesList = dbHelper?.getAllAudioFiles() ?: emptyList()
                            }
                        )
                    }
                }
            }

            // NEW AUDIO FILE BUTTON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp)
            ) {
                Button(
                    onClick = onNewAudioFileClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "new Audio file",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableAudioCard(
    file: AudioFileModel,
    cardBg: Color,
    accent: Color,
    iconBg: Color,
    onEditClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFFE53935))
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Delete",
                        color = Color.White,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        },
        content = {
            AudioFileCard(
                title = file.title,
                timestamp = file.timestamp,
                duration = file.duration,
                fileUri = file.fileUri,
                cardBackgroundColor = cardBg,
                accentColor = accent,
                iconBackgroundColor = iconBg,
                onEditClick = onEditClick
            )
        },
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioFileCard(
    title: String,
    timestamp: String,
    duration: String,
    fileUri: String,
    cardBackgroundColor: Color,
    accentColor: Color,
    iconBackgroundColor: Color,
    onEditClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var currentPositionMs by remember { mutableIntStateOf(0) }
    var totalDurationMs by remember { mutableIntStateOf(1000) }
    val interactionSource = remember { MutableInteractionSource() }

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    DisposableEffect(fileUri) {
        if (fileUri.isNotBlank()) {
            try {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(context, Uri.parse(fileUri))
                    prepare()
                    totalDurationMs = this.duration.takeIf { it > 0 } ?: 1000
                    setOnCompletionListener {
                        isPlaying = false
                        currentProgress = 0f
                        currentPositionMs = 0
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        onDispose {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()
            } catch (_: Exception) {
            }
        }
    }

    LaunchedEffect(isPlaying, mediaPlayer) {
        if (isPlaying && mediaPlayer != null) {
            while (isPlaying && mediaPlayer != null) {
                try {
                    currentPositionMs = mediaPlayer?.currentPosition ?: 0
                    totalDurationMs = mediaPlayer?.duration?.takeIf { it > 0 } ?: 1000
                    currentProgress = currentPositionMs.toFloat() / totalDurationMs.toFloat()
                } catch (_: Exception) {
                }
                delay(200L)
            }
        }
    }

    val currentTimeStr = formatMillis(currentPositionMs)
    val totalTimeStr = if (mediaPlayer != null && mediaPlayer!!.duration > 0) formatMillis(mediaPlayer!!.duration) else duration

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(cardBackgroundColor)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        // TOP ROW (Mic icon, Title, Subtitle, Edit icon)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.microphone),
                    contentDescription = "Microphone",
                    tint = DarkText,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontFamily = Lufga,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = timestamp,
                    fontFamily = Lufga,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF808080),
                    maxLines = 1
                )
            }

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onEditClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit_thin),
                    contentDescription = "Edit",
                    tint = DarkText,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // PROGRESS SLIDER (Vertically Centered)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Slider(
                value = currentProgress,
                onValueChange = { newProgress ->
                    currentProgress = newProgress
                    try {
                        mediaPlayer?.seekTo((newProgress * totalDurationMs).toInt())
                    } catch (_: Exception) {
                    }
                },
                interactionSource = interactionSource,
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                    )
                },
                track = { sliderState ->
                    SliderDefaults.Track(
                        sliderState = sliderState,
                        modifier = Modifier
                            .height(4.dp)
                            .fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            activeTrackColor = accentColor,
                            inactiveTrackColor = Color(0xFFE2E8F0)
                        ),
                        thumbTrackGapSize = 0.dp
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.size(2.dp))

        // TIME INDICATORS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentTimeStr,
                fontFamily = Lufga,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF808080)
            )
            Text(
                text = totalTimeStr,
                fontFamily = Lufga,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF808080)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // PLAYBACK CONTROLS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FastRewind,
                contentDescription = "Rewind",
                tint = DarkText,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        try {
                            mediaPlayer?.let { mp ->
                                val newPos = (mp.currentPosition - 10000).coerceAtLeast(0)
                                mp.seekTo(newPos)
                                currentPositionMs = newPos
                                currentProgress = currentPositionMs.toFloat() / totalDurationMs.toFloat()
                            }
                        } catch (_: Exception) {
                        }
                    }
            )

            Spacer(modifier = Modifier.width(30.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(accentColor)
                    .clickable {
                        try {
                            if (isPlaying) {
                                mediaPlayer?.pause()
                                isPlaying = false
                            } else {
                                mediaPlayer?.start()
                                isPlaying = true
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = DarkText,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Icon(
                imageVector = Icons.Default.FastForward,
                contentDescription = "Fast Forward",
                tint = DarkText,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        try {
                            mediaPlayer?.let { mp ->
                                val newPos = (mp.currentPosition + 10000).coerceAtMost(mp.duration)
                                mp.seekTo(newPos)
                                currentPositionMs = newPos
                                currentProgress = currentPositionMs.toFloat() / totalDurationMs.toFloat()
                            }
                        } catch (_: Exception) {
                        }
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AudioFilesScreenPreview() {
    AudioFilesScreen()
}
