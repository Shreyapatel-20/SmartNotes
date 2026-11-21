package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Videocam
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

private val DarkText = Color(0xFF000000)

@Composable
fun MeetingLinksScreen(
    onBackClick: () -> Unit = {},
    onMeetingCardClick: () -> Unit = {},
    onShowScheduleClick: () -> Unit = {},
    onScheduleMeetingClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Upcoming, 1: Recent

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
                    .padding(top = 48.dp, bottom = 12.dp)
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
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
                    text = "Meeting Links",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(36.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // SEARCH BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp).width(400.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search",
                    tint = Color(0xFF808080),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        color = DarkText
                    ),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search",
                                fontFamily = Lufga,
                                fontSize = 16.sp,
                                color = Color(0xFF999999)
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TABS (Upcoming / Recent)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Upcoming Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(26.dp))
                        .background(if (selectedTab == 0) Color.Black else Color(0xff808080))
                        .clickable { selectedTab = 0 },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Upcoming",
                        fontFamily = Lufga,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedTab == 0) Color.White else DarkText
                    )
                }

                // Recent Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(26.dp))
                        .background(if (selectedTab == 1) Color.Black else Color.Transparent)
                        .clickable { selectedTab = 1 },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Recent",
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedTab == 1) Color.White else DarkText
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CONTENT AREA
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                // CARD 1: Weekly Team Meeting
                item {
                    MeetingCard(
                        backgroundColor = Color(0xFFFEDBE3),
                        innerBoxColor = Color(0xFFFEF1F4),
                        badgeText = "Next Meeting",
                        title = "Weekly Team Meeting",
                        timeText = "Today • 10:30 AM",
                        startInText = "2h 15m",
                        joinButtonColor = Color(0xFFFFC5D3),
                        onCardClick = onMeetingCardClick
                    )
                }

                // CARD 2: Project Meeting
                item {
                    MeetingCard(
                        backgroundColor = Color(0xFFFFF3D3),
                        innerBoxColor = Color(0xFFFFF9E5),
                        badgeText = "Next Meeting",
                        title = "Project Meeting",
                        timeText = "Today • 04:30 PM",
                        startInText = "6h 23m",
                        joinButtonColor = Color(0xFFFFE8A8),
                        onCardClick = onMeetingCardClick
                    )
                }

                // BOTTOM ACTION CARDS
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Left Action Card
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(Color(0xFFEFE9FE))
                                .clickable { onShowScheduleClick() }
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xffFAF4FF)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painterResource(R.drawable.url),
                                        contentDescription = "Link",
                                        tint = DarkText,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Text(
                                    text = "Show\nSchedule\nmetting",
                                    fontFamily = Lufga,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = DarkText,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        // Right Action Card
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(Color(0xFFFFE4D6))
                                .clickable { onScheduleMeetingClick() }
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xffFFEEE0)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painterResource(R.drawable.schedule),
                                        contentDescription = "Schedule",
                                        tint = DarkText,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.size(17.dp))
                                Text(
                                    text = "Schedule\nMeeting",
                                    fontFamily = Lufga,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DarkText,
                                    lineHeight = 23.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MeetingCard(
    backgroundColor: Color,
    innerBoxColor: Color = Color.White.copy(alpha = 0.65f),
    badgeText: String,
    title: String,
    timeText: String,
    startInText: String,
    joinButtonColor: Color,
    onCardClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth().height(199.dp).width(400.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(backgroundColor)
            .clickable { onCardClick() }
            .padding(18.dp)
    ) {
        // TOP BADGE
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = badgeText,
                fontFamily = Lufga,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // TITLE AND SUBTITLE WITH ARROW BUTTON
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontFamily = Lufga,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(4.dp))

                // TIME SUBTITLE
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = Color(0xFF777780),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = timeText,
                        fontFamily = Lufga,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF777780)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onCardClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Open Meeting Notes",
                    tint = DarkText,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // INNER COUNTDOWN AND JOIN BOX
        Row(
            modifier = Modifier
                .fillMaxWidth().height(83.dp).width(372.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(innerBoxColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Start in",
                    fontFamily = Lufga,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF000000)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = startInText,
                    fontFamily = Lufga,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            }

            // JOIN BUTTON
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(joinButtonColor)
                    .clickable { }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.video),
                    contentDescription = "Join",
                    tint = Color(0xff000000),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Join",
                    fontFamily = Lufga,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkText
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeetingLinksScreenPreview() {
    MeetingLinksScreen()
}
