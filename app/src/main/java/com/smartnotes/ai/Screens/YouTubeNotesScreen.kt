package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.db.YouTubeNoteModel
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF171717)

data class YouTubeLinkItemData(
    val title: String,
    val url: String = "https://youtube.com/watch?v=...",
    val backgroundColor: Color,
    val iconBackgroundColor: Color = Color.White.copy(alpha = 0.85f)
)

@Composable
fun YouTubeNotesScreen(
    onBackClick: () -> Unit = {},
    onAddNewLinkClick: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var searchQuery by remember { mutableStateOf("") }
    var youtubeNotesList by remember {
        mutableStateOf(
            if (isPreview) {
                listOf(
                    YouTubeNoteModel(1, "Flutter tutorial", "https://youtube.com/watch?v=..."),
                    YouTubeNoteModel(2, "AI Productivity", "https://youtube.com/watch?v=..."),
                    YouTubeNoteModel(3, "UI UX Tutorial Full Video", "https://youtube.com/watch?v=..."),
                    YouTubeNoteModel(4, "AI Productivity", "https://youtube.com/watch?v=...")
                )
            } else {
                emptyList()
            }
        )
    }

    LaunchedEffect(Unit) {
        if (!isPreview && dbHelper != null) {
            youtubeNotesList = dbHelper.getAllYouTubeNotes()
        }
    }

    val filteredList = youtubeNotesList.filter { item ->
        searchQuery.isBlank() ||
        item.title.contains(searchQuery, ignoreCase = true) ||
        item.url.contains(searchQuery, ignoreCase = true)
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

        // MAIN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
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
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
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
                        text = "YouTube Notes",
                        fontSize = 20.sp,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Spacer(modifier = Modifier.size(40.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // SEARCH BAR
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
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
                            tint = Color.Gray,
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
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // SECTION HEADER
                Text(
                    text = "All YouTube Links",
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(20.dp))

                // LIST OF LINKS
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    val cardColors = listOf(
                        Color(0xffF2E2FE) to Color(0xffFAF4FF),
                        Color(0xffD1F2F0) to Color(0xffECFCFB)
                    )

                    if (filteredList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.isNotBlank()) "No YouTube links found matching '$searchQuery'" else "No YouTube links added yet.",
                                fontFamily = Lufga,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        filteredList.forEachIndexed { index, item ->
                            val colorPair = cardColors[index % cardColors.size]
                            YouTubeLinkCard(
                                item = YouTubeLinkItemData(
                                    title = item.title,
                                    url = item.url,
                                    backgroundColor = colorPair.first,
                                    iconBackgroundColor = colorPair.second
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(180.dp))
            }
        }

        // BOTTOM BUTTON
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 39.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp).width(400.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .clickable { onAddNewLinkClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "add",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "new Links",
                    color = Color.White,
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun YouTubeLinkCard(item: YouTubeLinkItemData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp).width(400.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(item.backgroundColor)
            .clickable { }
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Video Icon inside circle
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(item.iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.video_filled),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Title and URL
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                fontFamily = Lufga,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = item.url,
                fontFamily = Lufga,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF5B6CF9),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Arrow icon
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Open Link",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun YouTubeNotesScreenPreview() {
    YouTubeNotesScreen()
}
