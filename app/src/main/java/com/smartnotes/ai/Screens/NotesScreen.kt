package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF171717)

data class NoteCardData(
    val title: String,
    val subtitle: String = "Aug 20 - 9:58 PM",
    val tag: String,
    val tagColor: Color,
    val icon: Painter,
    val iconTintColor: Color,
    val backgroundColor: Color,
    val iconbackgroundcolor: Color,
    val arrowColor: Color
)

@Composable
fun NotesScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToYouTubeNotes: () -> Unit = {},
    onNavigateToAudioFiles: () -> Unit = {},
    onNavigateToDocumentFiles: () -> Unit = {},
    onNavigateToMeetingLinks: () -> Unit = {},
    onNavigateToRecordingFiles: () -> Unit = {},
    onNavigateToImageFiles: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // BACKGROUND IMAGE
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = "Notes background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // TOP BAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(50.dp))

                    Text(
                        text = "Notes",
                        fontSize = 20.sp,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.85f))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.notification),
                            contentDescription = "Notification",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // SEARCH BAR
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(horizontal = 18.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "Search",
                            tint = Color(0xff808080),
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        BasicTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.weight(1f),
                            textStyle = TextStyle(
                                fontFamily = Lufga,
                                fontSize = 16.sp,
                                color = DarkText
                            ),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Search",
                                        fontFamily = Lufga,
                                        fontSize = 16.sp,
                                        color = Color(0xff808080)
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ALL NOTES HEADER
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "All Notes",
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = DarkText
                    )

                    Text(
                        text = "View all",
                        color = Color(0xff808080),
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // NOTES GRID
                val noteCards = listOf(
                    NoteCardData(
                        title = "Audio file",
                        subtitle = "Aug 20 - 9:58 PM",
                        tag = "File 2 note",
                        tagColor = Color(0xFF422BC4),
                        icon = painterResource(R.drawable.microphone),
                        iconTintColor = Color.Black,
                        backgroundColor = Color(0xFFE1DBFF),
                        iconbackgroundcolor = Color(0xffF0EDFF),
                        arrowColor = Color(0xFF422BC4)
                    ),
                    NoteCardData(
                        title = "Document file",
                        subtitle = "Aug 20 - 9:58 PM",
                        tag = "File 1 note",
                        tagColor = Color(0xFF339891),
                        icon = painterResource(R.drawable.documents_outline),
                        iconTintColor = Color.Black,
                        backgroundColor = Color(0xFFC9F0EE),
                        iconbackgroundcolor = Color(0xffE4F9F6),
                        arrowColor = Color(0xFF339891)
                    ),
                    NoteCardData(
                        title = "Meeting Links",
                        subtitle = "Aug 20 - 9:58 PM",
                        tag = "File 1 note",
                        tagColor = Color(0xFFD5854E),
                        icon = painterResource(R.drawable.video),
                        iconTintColor = Color.Black,
                        backgroundColor = Color(0xFFFFE1CE),
                        iconbackgroundcolor = Color(0xffFFEEE0),
                        arrowColor = Color(0xFFD5854E)
                    ),
                    NoteCardData(
                        title = "Image file",
                        subtitle = "Aug 20 - 9:58 PM",
                        tag = "File 1 note",
                        tagColor = Color(0xFFD4AB33),
                        icon = painterResource(R.drawable.image),
                        iconTintColor = Color.Black,
                        iconbackgroundcolor = Color(0xffFFF9E5),
                        backgroundColor = Color(0xFFFFF3D3),
                        arrowColor = Color(0xFFD4AB33)
                    ),
                    NoteCardData(
                        title = "Recording audio",
                        subtitle = "Aug 20 - 9:58 PM",
                        tag = "File 2 note",
                        tagColor = Color(0xFF422BC4),
                        icon = painterResource(R.drawable.microphone),
                        iconTintColor = Color.Black,
                        backgroundColor = Color(0xFFE1DBFF),
                        iconbackgroundcolor = Color(0xffF0EDFF),
                        arrowColor = Color(0xFF422BC4)
                    ),
                    NoteCardData(
                        title = "URL file",
                        subtitle = "Aug 20 - 9:58 PM",
                        tag = "File 1 note",
                        tagColor = Color(0xFF339891),
                        icon = painterResource(R.drawable.url),
                        iconTintColor = Color.Black,
                        backgroundColor = Color(0xFFC9F0EE),
                        iconbackgroundcolor = Color(0xffE4F9F6),
                        arrowColor = Color(0xFF339891)
                    )

                )

                // Render in pairs
                noteCards.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        for (item in rowItems) {
                            Box(modifier = Modifier.weight(1f)) {
                                NoteCardItem(
                                    item = item,
                                    onClick = {
                                        when (item.title) {
                                            "Audio file" -> onNavigateToAudioFiles()
                                            "Document file" -> onNavigateToDocumentFiles()
                                            "Meeting Links" -> onNavigateToMeetingLinks()
                                            "Recording audio" -> onNavigateToRecordingFiles()
                                            "Image file" -> onNavigateToImageFiles()
                                            "YouTube Link", "YouTube URL" -> onNavigateToYouTubeNotes()
                                        }
                                    }
                                )
                            }
                        }
                        if (rowItems.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // BOTTOM NAVIGATION
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            BottomNavigationBar(
                selectedIndex = 1,
                onItemSelected = { index ->
                    if (index == 0) {
                        onNavigateToHome()
                    }
                }
            )
        }
    }
}

@Composable
fun NoteCardItem(
    item: NoteCardData,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(211.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(item.backgroundColor)
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: Icon circle + Tag
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(color = item.iconbackgroundcolor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = item.icon,
                        contentDescription = null,
                        tint = item.iconTintColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = item.tag,
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = item.tagColor
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Middle text
            Column {
                Text(
                    text = item.title,
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.subtitle,
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xff808080)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Bottom row: "View now" + Arrow
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View now",
                    fontFamily = Lufga,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "View now",
                    tint = item.arrowColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    NotesScreen()
}
