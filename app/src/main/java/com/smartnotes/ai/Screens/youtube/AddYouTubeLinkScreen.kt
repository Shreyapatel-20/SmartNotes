package com.smartnotes.ai.Screens.youtube

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
fun AddYouTubeLinkScreen(
    initialFolderTitle: String = "",
    onBackClick: () -> Unit = {},
    onContinueClick: (folder: String, title: String, link: String) -> Unit = { _, _, _ -> }
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var folderText by remember { mutableStateOf(initialFolderTitle) }
    var videoTitleText by remember { mutableStateOf("") }
    var linkText by remember { mutableStateOf("") }

    var existingFolders by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        if (!isPreview && dbHelper != null) {
            existingFolders = dbHelper.getAllYouTubeNotes().map { it.folderName }.distinct()
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
                        text = "YouTube Link",
                        fontSize = 20.sp,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Spacer(modifier = Modifier.size(40.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // FIELD 1: FOLDER NAME (GIVE NAME)
                Text(
                    text = "Give Name",
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(12.dp))

                // EXISTING FOLDER PILLS (Quick select existing folder if available)
                if (existingFolders.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        existingFolders.take(3).forEach { folder ->
                            val isSelected = folderText.equals(folder, ignoreCase = true)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) Color(0xFFC4B5FF) else Color.White)
                                    .clickable { folderText = folder }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = folder,
                                    fontFamily = Lufga,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = DarkText
                                )
                            }
                        }
                    }
                }

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
                        value = folderText,
                        onValueChange = { folderText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontFamily = Lufga,
                            fontSize = 15.sp,
                            color = DarkText
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (folderText.isEmpty()) {
                                Text(
                                    text = "Give Name (Folder Name)",
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

                // FIELD 2: VIDEO TITLE
                Text(
                    text = "Video Title",
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(12.dp))

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
                        value = videoTitleText,
                        onValueChange = { videoTitleText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontFamily = Lufga,
                            fontSize = 15.sp,
                            color = DarkText
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (videoTitleText.isEmpty()) {
                                Text(
                                    text = "Video Title",
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

                // FIELD 3: ADD YOUTUBE LINK
                Text(
                    text = "Add YouTube Link",
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(12.dp))

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
                                    text = "Add link your Video",
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
                        val trimmedFolder = folderText.trim()
                        val trimmedTitle = videoTitleText.trim()
                        val trimmedLink = linkText.trim()

                        val folderToSave = if (trimmedFolder.isNotBlank()) trimmedFolder else "YouTube"
                        val titleToSave = if (trimmedTitle.isNotBlank()) trimmedTitle else "Video Title"
                        val linkToSave = if (trimmedLink.isNotBlank()) trimmedLink else "https://youtube.com/..."

                        dbHelper?.insertYouTubeNote(
                            folderName = folderToSave,
                            videoTitle = titleToSave,
                            url = linkToSave
                        )
                        onContinueClick(folderToSave, titleToSave, linkToSave)
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
fun AddYouTubeLinkScreenPreview() {
    AddYouTubeLinkScreen()
}
