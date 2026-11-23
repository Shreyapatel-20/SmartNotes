package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

@Composable
fun RecentMeetingNotesScreen(
    onBackClick: () -> Unit = {}
) {
    var titleText by remember { mutableStateOf("") }
    var notesText by remember { mutableStateOf("") }

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
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                // TOP BAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, bottom = 16.dp)
                        .height(24.dp),
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
                        text = "Meeting notes",
                        fontFamily = Lufga,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )

                    Spacer(modifier = Modifier.width(36.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // TITLE FIELD
                BasicTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = Lufga,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    ),
                    decorationBox = { innerTextField ->
                        if (titleText.isEmpty()) {
                            Text(
                                text = "Title here",
                                fontFamily = Lufga,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF808080)
                            )
                        }
                        innerTextField()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // DIVIDER LINE
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFD1D5DB)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // NOTES FIELD
                BasicTextField(
                    value = notesText,
                    onValueChange = { notesText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textStyle = TextStyle(
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        color = DarkText
                    ),
                    decorationBox = { innerTextField ->
                        if (notesText.isEmpty()) {
                            Text(
                                text = "Notes here",
                                fontFamily = Lufga,
                                fontSize = 20.sp,
                                color = Color(0xFF808080)
                            )
                        }
                        innerTextField()
                    }
                )

                // FORMATTING TOOLBAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.9f))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Aa",
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText,
                        modifier = Modifier.clickable { }
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
                        contentDescription = "List",
                        tint = DarkText,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )

                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = "Table",
                        tint = DarkText,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )

                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = "Attachment",
                        tint = DarkText,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )

                    Icon(
                        imageVector = Icons.Default.FormatBold,
                        contentDescription = "Bold",
                        tint = DarkText,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )

                    Icon(
                        imageVector = Icons.Default.FormatItalic,
                        contentDescription = "Italic",
                        tint = DarkText,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )

                    Icon(
                        imageVector = Icons.Default.FormatUnderlined,
                        contentDescription = "Underline",
                        tint = DarkText,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            // KEYBOARD VIEW GRAPHIC
            //MockupKeyboard()
        }
    }
}

//@Composable
//fun MockupKeyboard() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color(0xFFE2E4E9))
//            .padding(horizontal = 6.dp, vertical = 8.dp)
//    ) {
//        // SUGGESTIONS BAR
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 6.dp),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            Text(
//                text = "“The”",
//                fontFamily = Lufga,
//                fontSize = 14.sp,
//                color = DarkText
//            )
//            Text(
//                text = "the",
//                fontFamily = Lufga,
//                fontSize = 14.sp,
//                color = DarkText
//            )
//            Text(
//                text = "to",
//                fontFamily = Lufga,
//                fontSize = 14.sp,
//                color = DarkText
//            )
//        }
//
//        Spacer(modifier = Modifier.height(6.dp))
//
//        // ROW 1
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            val keys1 = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
//            keys1.forEach { key ->
//                KeyButton(text = key, modifier = Modifier.weight(1f))
//            }
//        }
//
//        Spacer(modifier = Modifier.height(6.dp))
//
//        // ROW 2
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 14.dp),
//            horizontalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            val keys2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l")
//            keys2.forEach { key ->
//                KeyButton(text = key, modifier = Modifier.weight(1f))
//            }
//        }
//
//        Spacer(modifier = Modifier.height(6.dp))
//
//        // ROW 3
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(4.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            KeyButton(text = "⇧", modifier = Modifier.weight(1.3f))
//            val keys3 = listOf("z", "x", "c", "v", "b", "n", "m")
//            keys3.forEach { key ->
//                KeyButton(text = key, modifier = Modifier.weight(1f))
//            }
//            KeyButton(text = "⌫", modifier = Modifier.weight(1.3f))
//        }
//
//        Spacer(modifier = Modifier.height(6.dp))
//
//        // ROW 4 (Bottom Bar)
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(6.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            KeyButton(text = "ABC", modifier = Modifier.weight(1.5f))
//
//            // Spacebar
//            Box(
//                modifier = Modifier
//                    .weight(5f)
//                    .height(42.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color.White)
//            )
//
//            // Enter / Action
//            Box(
//                modifier = Modifier
//                    .weight(1.5f)
//                    .height(42.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color(0xFF007AFF)),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "↵",
//                    color = Color.White,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // BOTTOM ICONS
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "😊",
//                fontSize = 20.sp
//            )
//
//            Icon(
//                imageVector = Icons.Default.Mic,
//                contentDescription = "Voice",
//                tint = Color(0xFF666666),
//                modifier = Modifier.size(20.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun KeyButton(
//    text: String,
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier
//            .height(42.dp)
//            .clip(RoundedCornerShape(6.dp))
//            .background(Color.White),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = text,
//            fontFamily = Lufga,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Medium,
//            color = DarkText
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun RecentMeetingNotesScreenPreview() {
    RecentMeetingNotesScreen()
}
