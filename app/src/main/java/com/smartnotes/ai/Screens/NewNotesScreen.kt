package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.db.NoteModel
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF171717)

@Composable
fun NewNotesScreen(
    onBackClick: () -> Unit = {},
    onNewNoteClick: () -> Unit = {},
    onNoteClick: (Int, String, String) -> Unit = { _, _, _ -> }
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var notesList by remember {
        mutableStateOf(
            if (isPreview) {
                listOf(
                    NoteModel(1, "Exam IMP Question", "08/01/26   What is UI/UX?", "08/01/26"),
                    NoteModel(2, "Aman Preparation", "08/01/26   Study preparation", "08/01/26"),
                    NoteModel(3, "Monthly Expense", "08/01/26   Monthly budget", "08/01/26")
                )
            } else {
                emptyList()
            }
        )
    }

    LaunchedEffect(Unit) {
        if (!isPreview && dbHelper != null) {
            notesList = dbHelper.getAllNotes()
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
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // BACK ICON
                Box(
                    modifier = Modifier
                        .size(32.dp)
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

                // TITLE
                Text(
                    text = "Notes",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF242529)
                )

                Spacer(modifier = Modifier.width(32.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // NOTES LIST
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                item {
                    Text(
                        text = "All Saved Notes",
                        fontSize = 18.sp,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF242529),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                if (notesList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No notes saved yet.\nClick '+ new Notes' below to create one!",
                                fontFamily = Lufga,
                                fontSize = 14.sp,
                                color = Color(0xFF808080),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(notesList) { note ->
                        val subText = note.subtitle.ifBlank { "${note.dateText}   Saved note" }
                        NoteCard(
                            title = note.title,
                            subtitle = subText,
                            onClick = { onNoteClick(note.id, note.title, subText) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NEW NOTE BUTTON
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 0.dp)
            ) {
                Button(
                    onClick = onNewNoteClick,
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
                        contentDescription = "add",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "new Notes",
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


// NOTE CARD
@Composable
fun NoteCard(
    title: String,
    subtitle: String = "08/01/26   What is UI/UX?",
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xffF2E2FE))
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // NOTE ICON BOX
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFFF8F2FF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.tabler_notes),
                contentDescription = "tabler notes",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // TITLE AND DATE
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                fontFamily = Lufga,
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF242529),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                fontFamily = Lufga,
                text = subtitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF777780),
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewNotesScreenPreview() {
    NewNotesScreen()
}
