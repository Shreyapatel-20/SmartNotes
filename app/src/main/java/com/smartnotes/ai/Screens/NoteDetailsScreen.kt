package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.GridView
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
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

private fun stripDatePrefix(text: String): String {
    val regex = Regex("^\\d{2}/\\d{2}/\\d{2}\\s+")
    return text.replace(regex, "").trim()
}

@Composable
fun NoteDetailsScreen(
    noteId: Int = 0,
    noteTitle: String = "Exam IMP Question",
    noteDescription: String = "What is UI/UX?\n\nKey Concepts:\n1. User Interface (UI) focuses on visual design.\n2. User Experience (UX) focuses on overall usability.",
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var titleText by remember { mutableStateOf(noteTitle) }
    var notesText by remember { mutableStateOf(stripDatePrefix(noteDescription)) }

    LaunchedEffect(noteTitle, noteDescription) {
        titleText = noteTitle
        notesText = stripDatePrefix(noteDescription)
    }

    val saveNoteAction = {
        val trimmedTitle = titleText.trim()
        val trimmedNotes = notesText.trim()
        if (trimmedTitle.isNotEmpty() || trimmedNotes.isNotEmpty()) {
            dbHelper?.updateNote(
                noteId = noteId,
                title = if (trimmedTitle.isNotEmpty()) trimmedTitle else "Untitled Note",
                subtitle = trimmedNotes
            )
        }
        onSaveSuccess()
        onBackClick()
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
                    .padding(top = 48.dp, bottom = 16.dp)
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { saveNoteAction() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back and Save",
                        tint = DarkText,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "notes",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                // SAVE CHECKMARK
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { saveNoteAction() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save Note",
                        tint = DarkText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // EDITABLE TITLE FIELD
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
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF808080)
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // DIVIDER LINE
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFCCCCCC)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // EDITABLE NOTES CONTENT FIELD (NO DATE PREFIX)
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
                            fontSize = 16.sp,
                            color = Color(0xFF808080)
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // FORMATTING TOOLBAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White.copy(alpha = 0.85f))
                    .padding(horizontal = 20.dp, vertical = 12.dp),
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDetailsScreenPreview() {
    NoteDetailsScreen()
}
