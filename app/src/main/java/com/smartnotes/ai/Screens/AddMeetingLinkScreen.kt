package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF171717)

@Composable
fun AddMeetingLinkScreen(
    folderTitle: String = "YouTube",
    onBackClick: () -> Unit = {},
    onContinueClick: (name: String, link: String) -> Unit = { _, _ -> }
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var nameText by remember { mutableStateOf("") }
    var linkText by remember { mutableStateOf("") }

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
                        text = "URL & Video Notes",
                        fontSize = 20.sp,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )

                    Spacer(modifier = Modifier.size(40.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // FIELD 1: GIVE NAME / VIDEO TITLE
                Text(
                    text = "Video Title",
                    fontFamily = Lufga,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = nameText,
                        onValueChange = { nameText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontFamily = Lufga,
                            fontSize = 15.sp,
                            color = DarkText
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (nameText.isEmpty()) {
                                Text(
                                    text = "Give Name",
                                    fontFamily = Lufga,
                                    fontSize = 14.sp,
                                    color = Color(0xff808080)
                                )
                            }
                            innerTextField()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // FIELD 2: ADD MEETING LINK
                Text(
                    text = "Add Link",
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = linkText,
                        onValueChange = { linkText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontFamily = Lufga,
                            fontSize = 15.sp,
                            color = DarkText
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (linkText.isEmpty()) {
                                Text(
                                    text = "Add link here",
                                    fontFamily = Lufga,
                                    fontSize = 15.sp,
                                    color = Color(0xff808080)
                                )
                            }
                            innerTextField()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(90.dp))
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .clickable {
                        val trimmedName = nameText.trim()
                        val trimmedLink = linkText.trim()
                        val targetFolder = if (folderTitle.isNotBlank()) folderTitle else "YouTube"
                        val titleToSave = if (trimmedName.isNotBlank()) trimmedName else "Video"
                        val urlToSave = if (trimmedLink.isNotBlank()) trimmedLink else "https://youtube.com/..."
                        dbHelper?.insertYouTubeNote(targetFolder, titleToSave, urlToSave)
                        onContinueClick(titleToSave, urlToSave)
                    },
                contentAlignment = Alignment.Center
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

@Preview(showBackground = true)
@Composable
fun AddMeetingLinkScreenPreview() {
    AddMeetingLinkScreen()
}
