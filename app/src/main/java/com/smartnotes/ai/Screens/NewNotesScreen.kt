package com.smartnotes.ai.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import android.R.attr.icon
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF171717)

@Composable
fun NewNotesScreen(
    onBackClick: () -> Unit = {},
    onNewNoteClick: () -> Unit = {},
    onNoteClick: (String) -> Unit = {}
){
    val notesPrevious = listOf(
        "Exam IMP Question",
        "Exam IMP Question",
        "Exam IMP Question"
    )

    val notesFebruary = listOf(
        "Aman Preparation",
        "Monthly Expense"
    )

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
                        imageVector =Icons.AutoMirrored.Filled.ArrowBack,
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // PREVIOUS 30 DAYS
                item {
                    Text(
                        text = "Previous 30 Days",
                        fontSize = 18.sp,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF242529),
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }

                items(notesPrevious) { note ->

                    NoteCard(
                        title = note,
                        onClick = { onNoteClick(note) }
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }

                // FEBRUARY HEADING
                item {
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "February",
                        fontFamily = Lufga,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                }

                items(notesFebruary) { note ->
                    Spacer(modifier = Modifier.height(10.dp))
                    NoteCard(
                        title = note,
                        onClick = { onNoteClick(note) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NEW NOTE BUTTON
            Box(
                modifier = Modifier
                    .padding(vertical = 39.dp, horizontal = 0.dp)
            ) {
                Button(
                    onClick = onNewNoteClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .width(400.dp),
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
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .width(400.dp)
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
                contentDescription = "tablar notes",
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
                text = "08/01/26   What is UI/UX?",
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