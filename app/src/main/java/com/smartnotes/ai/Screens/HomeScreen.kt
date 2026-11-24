
package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import com.smartnotes.ai.ui.theme.Lufga
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import com.smartnotes.ai.db.UserDatabaseHelper

private val Purple = Color(0xFFB978E8)
private val DarkText = Color(0xFF171717)

@Composable
fun HomeScreen(
    onNavigateToNotes: () -> Unit = {},
    onNavigateToNewNotes: () -> Unit = {},
    onNavigateToYouTubeNotes: () -> Unit = {},
    onNavigateToRecordingAudio: () -> Unit = {},
    onNavigateToAudioFiles: () -> Unit = {},
    onNavigateToDocumentFiles: () -> Unit = {},
    onNavigateToMeetingLinks: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    var savedUserName by remember {
        mutableStateOf(
            if (isPreview) "" else UserDatabaseHelper(context).getUserName()
        )
    }
    val displayName = if (savedUserName.isNotBlank()) "$savedUserName, " else "Emily, "

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // BACKGROUND IMAGE
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = "Home background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // HOME SCREEN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
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
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.75f))
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

                    Spacer(modifier = Modifier.width(10.dp))

                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable { onNavigateToProfile() },
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))
Column(
    modifier=Modifier.padding(start=6.dp)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // GREETING
        Text(
            text = "Hi ",
            fontSize = 30.sp,
            fontFamily = Lufga,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
        Text(
            text = displayName,
            fontSize = 30.sp,
            fontFamily = Lufga,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Text(
            text = "i'd be happy",
            fontSize = 30.sp,
            fontFamily = Lufga,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
    }
    Text(
        text = "to help what's needed?",
        fontSize = 30.sp,
        fontFamily = Lufga,
        fontWeight = FontWeight.Normal,
        color = DarkText
    )
}
                Spacer(modifier = Modifier.height(81.dp))

                // FEATURE CARDS
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(234.dp)
                        .width(166.dp),
                        //.clip(RoundedCornerShape(30.dp)),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // CAPTURE AUDIO
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(22.dp))
                            .background(Color(0xFFFFECBA))
                            .clickable { onNavigateToAudioFiles() }
                    ) {

                        // Sunburst background graphic centered behind top-left mic circle
                        Image(
                            painter = painterResource(R.drawable.micbackground),
                            contentDescription = null,
                            modifier = Modifier
                                .offset(x = (-30).dp, y = (-30).dp)
                                .size(172.dp),
                            contentScale = ContentScale.FillWidth
                        )

                        // Card content
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.85f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.microphone),
                                    contentDescription = "Microphone",
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Column(
                                modifier = Modifier.padding(top = 45.dp, start = 5.dp)
                            ) {
                                Text(
                                    text = "Capture Your\nAudio Note Here",
                                    fontSize = 16.sp,
                                    fontFamily = Lufga,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black,
                                    lineHeight = 23.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Begin Audio Note",
                                    fontSize = 14.sp,
                                    fontFamily = Lufga,
                                    fontWeight = FontWeight.Thin,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(7.dp))

                            Button(
                                onClick = onNavigateToAudioFiles,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp).width(150.dp),
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFFB900)
                                )
                            ) {
                                Text(
                                    text = "Start recording",
                                    fontSize = 12.sp,
                                    fontFamily = Lufga,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                            }
                        }
                    }


                    // RIGHT SIDE CARDS
                    Column(
                        modifier = Modifier
                            .weight(1.1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        // UPLOAD AUDIO
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(Color(0xFFD7D0FE))
                                    .clickable { onNavigateToAudioFiles() }
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.uploadaudio),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .offset(x =(7).dp,y=(-20).dp )

                                        .height(122.dp).width(120.dp),
                                    alpha = 0.75f
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE5E3FD)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.upload),
                                            contentDescription = "Upload",
                                            tint = Color.Black,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "Upload audio",
                                            fontSize = 16.sp,
                                            fontFamily = Lufga,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black
                                        )

                                        Icon(
                                            Icons.Default.ArrowForward,
                                            contentDescription = null,
                                            tint = Color(0xFF634DE3)
                                        )
                                    }
                                }


                            }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-16).dp, y = (-12).dp)
                                    .zIndex(10f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFFFF7777))
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "New",
                                    color = Color.White,
                                    fontFamily = Lufga,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }



                        }

                        // NOTE CARD
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(0xFF242529))
                                .clickable { onNavigateToNewNotes() }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.uploadaudio),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .offset(x =(7).dp,y=(-20).dp )

                                    .height(122.dp).width(120.dp),
                                alpha = 0.75f
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {

                                Box (
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF515357).copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.notes),
                                        contentDescription = "Note",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF515357).copy(alpha = 0.5f))
                                            .padding(13.dp)


                                    )
                                }



                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Note",
                                    fontSize = 16.sp,
                                    fontFamily = Lufga,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                                Icon(
                                    Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
                    }

                Spacer(modifier = Modifier.height(20.dp))

                // ALL NOTES
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "All Notes",
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        "View all",
                        color = Color.Gray,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // NOTE 1
                Box(
                    modifier = Modifier
                        .height(70.dp)
                        .width(400.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFFD1F1EF))
                        .clickable { onNavigateToYouTubeNotes() }
                ) {
                    NoteItem(
                        title = "YouTube URL",
                        subtitle = "Recording & transcribe meeting",
                        icon = painterResource(R.drawable.youtube),
                        background = Color(0xFFD1F1EF),
                        onClick = { onNavigateToYouTubeNotes() }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                // NOTE 2
                NoteItem(
                    title = "Meeting Link",
                    subtitle = "Recording & transcribe meeting",
                    icon = painterResource(R.drawable.video),
                    background = Color(0xFFF0E1FF),
                    onClick = onNavigateToMeetingLinks
                )

                Spacer(modifier = Modifier.height(12.dp))

                // NOTE 3
                NoteItem(
                    title = "Recording Audio",
                    subtitle = "Recording with Microphone",
                    icon = painterResource(R.drawable.microphone),
                    background = Color(0xFFFFE5CA),
                    onClick = onNavigateToRecordingAudio
                )

                Spacer(modifier = Modifier.height(12.dp))
                NoteItem(
                    title = "Upload Document",
                    subtitle = "Upload PDF & other File",
                    icon = painterResource(R.drawable.documents_outline),
                    background = Color(0xFFD1F1EF),
                    onClick = onNavigateToDocumentFiles
                )
            }

            // BOTTOM NAVIGATION

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            BottomNavigationBar(
                selectedIndex = 0,
                onItemSelected = { index ->
                    when (index) {
                        1 -> onNavigateToNotes()
                        3 -> onNavigateToProfile()
                    }
                }
            )
        }
    }
}


// NOTE ITEM
@Composable
fun NoteItem(
    title: String,
    subtitle: String,
    icon: Painter,
    background: Color,
    showAll: Boolean = false,
    titleSize: TextUnit = 18.sp,
    subtitleSize: TextUnit = 14.sp,
    titleColor: Color = Color.Black,
    subtitleColor: Color = Color.Gray,
    iconSize: Dp = 30.dp,
    iconColor: Color = Color.Black,
    fontFamily: FontFamily=Lufga,
    fontWeight: FontWeight= FontWeight.Normal,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(background)
            .clickable { onClick() }
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.75f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                title,
                fontSize = 16.sp,
                fontFamily=Lufga,
                fontWeight = FontWeight.Medium
            )

            Text(
                subtitle,
                fontFamily=Lufga,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        if (showAll) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black)
                    .clickable { }
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            ) {
                Text(
                    "All",
                    color = Color.White,
                    fontSize = 11.sp
                )
            }

        } else {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Open note",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
