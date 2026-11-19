
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
private val Purple = Color(0xFFB978E8)
private val DarkText = Color(0xFF171717)

@Composable
fun HomeScreen() {

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
                            .clickable { },
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
            color = Color.Gray
        )
        Text(
            text = "Emily, ",
            fontSize = 30.sp,
            color = Color.Black
        )
        Text(
            text = "i'd be happy",
            fontSize = 30.sp,
            color = Color.Gray
        )
    }
    Text(
        text = "to help what's needed?",
        fontSize = 30.sp,
        fontWeight = FontWeight.Medium,
        color = DarkText
    )
}
                Spacer(modifier = Modifier.height(81.dp))

                // FEATURE CARDS
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(234.dp)
                        .width(166.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // CAPTURE AUDIO
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(22.dp))
                            .background(Color(0xFFFFECBA))
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
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black,
                                    lineHeight = 23.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Begin Audio Note",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(7.dp))

                            Button(
                                onClick = { },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFFB900)
                                )
                            ) {
                                Text(
                                    text = "Start recording",
                                    fontSize = 12.sp,
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
                                    .clickable { }
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

                            Text(
                                "New",
                                color = Color.White,
                                fontSize = 9.sp,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(y = (-7).dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFFFF7777))
                                    .padding(
                                        horizontal = 12.dp,
                                        vertical = 3.dp
                                    )
                            )
                        }

                        // NOTE CARD
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(0xFF242529))
                                .clickable { }
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        "View all",
                        color = Color.Gray,
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
                ) {
                    NoteItem(
                        title = "YouTube URL",
                        subtitle = "Recording & transcribe meeting",
                        icon = painterResource(R.drawable.youtube),
                        background = Color(0xFFD1F1EF),

                        )
                }
                Spacer(modifier = Modifier.height(12.dp))

                // NOTE 2
                NoteItem(
                    title = "Meeting Link",
                    subtitle = "Recording & transcribe meeting",
                    icon = painterResource(R.drawable.video),
                    background = Color(0xFFF0E1FF)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // NOTE 3
                NoteItem(
                    title = "Recording Audio",
                    subtitle = "Recording with Microphone",
                    icon = painterResource(R.drawable.microphone),
                    background = Color(0xFFFFE5CA),

                )

                Spacer(modifier = Modifier.height(12.dp))
                NoteItem(
                    title = "Upload Document",
                    subtitle = "Upload PDF & other File",
                    icon = painterResource(R.drawable.document),
                    background = Color(0xFFD1F1EF),

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
            BottomNavigationBar()
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
    iconColor: Color = Color.Black
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(background)
            .clickable { }
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
                fontWeight = FontWeight.Medium
            )

            Text(
                subtitle,
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





@Composable
fun BottomNavigationBar() {

    var selectedIndex by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(66.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(Color(0xFFE6E6E6))
            .padding(4.dp),

        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomNavItem(
            icon = Icons.Default.Home,
            selected = selectedIndex == 0,
            onClick = { selectedIndex = 0 }
        )

        BottomNavItem(
            icon = Icons.Default.Description,
            selected = selectedIndex == 1,
            onClick = { selectedIndex = 1 }
        )

        BottomNavItem(
            icon = Icons.Default.ChatBubbleOutline,
            selected = selectedIndex == 2,
            onClick = { selectedIndex = 2 }
        )

        BottomNavItem(
            icon = Icons.Default.Person,
            selected = selectedIndex == 3,
            onClick = { selectedIndex = 3 }
        )
    }
}
// BOTTOM NAV ITEM

@Composable
fun BottomNavItem(
    icon: ImageVector,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = if (selected) {
                        listOf(
                            Color(0xFF634DE3),
                            Color(0xFFB978E8)
                        )
                    } else {
                        listOf(
                            Color(0xFFFDFBFA),
                            Color(0xFFFDFBFA)
                        )

                    }
                )
            )
            .clickable { onClick() },

        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) Color.White else DarkText,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
