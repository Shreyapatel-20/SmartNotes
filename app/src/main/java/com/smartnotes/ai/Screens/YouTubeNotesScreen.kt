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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
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
    var searchQuery by remember { mutableStateOf("") }

    val sampleLinks = remember {
        listOf(
            YouTubeLinkItemData(
                title = "Flutter tutorial",
                url = "https://youtube.com/watch?v=...",
                backgroundColor = Color(0xffF2E2FE),
                iconBackgroundColor = Color(0xffFAF4FF)
            ),
            YouTubeLinkItemData(
                title = "AI Productivity",
                url = "https://youtube.com/watch?v=...",
                backgroundColor = Color(0xffD1F2F0),
                iconBackgroundColor = Color(0xffECFCFB)
            ),
            YouTubeLinkItemData(
                title = "UI UX Tutorial Full Video",
                url = "https://youtube.com/watch?v=...",
                backgroundColor = Color(0xffF2E2FE),
                iconBackgroundColor = Color(0xffFAF4FF)
            ),
            YouTubeLinkItemData(
                title = "AI Productivity",
                url = "https://youtube.com/watch?v=...",
                backgroundColor = Color(0xffD1F2F0),
                iconBackgroundColor = Color(0xffECFCFB)
            )
        )
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

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkText,
                            modifier = Modifier.size(24.dp)
                        )
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
                    sampleLinks.forEach { item ->
                        YouTubeLinkCard(item = item)
                    }
                }

                Spacer(modifier = Modifier.height(283.dp))
            }
        }

        // BOTTOM BUTTON
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical =39.dp ),
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
