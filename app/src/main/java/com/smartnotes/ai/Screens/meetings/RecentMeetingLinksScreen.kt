package com.smartnotes.ai.Screens.meetings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
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

private val DarkText = Color.Black

data class RecentMeetingData(
    val title: String = "Weekly Team Meeting",
    val dateTime: String,
    val backgroundColor: Color,
    val arrowBackgroundColor: Color
)

@Composable
fun RecentMeetingLinksScreen(
    onBackClick: () -> Unit = {},
    onUpcomingTabClick: () -> Unit = {},
    onMeetingCardClick: () -> Unit = {},
    onShowScheduleClick: () -> Unit = {},
    onScheduleMeetingClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(1) } // 0: Upcoming, 1: Recent

    val recentMeetings = listOf(
        RecentMeetingData(dateTime = "01/02/26 • 10:30 AM", backgroundColor = Color(0xffFEDBE3), arrowBackgroundColor = Color(0xffFEF1F4)),
        RecentMeetingData(dateTime = "28/01/26 • 11:30 AM", backgroundColor = Color(0xFFD1F2F0), arrowBackgroundColor = Color(0xffECFCFB)),
        RecentMeetingData(dateTime = "28/01/26 • 11:30 AM", backgroundColor = Color(0xFFF2E2FE), arrowBackgroundColor = Color(0xffF0EDFF)),
        RecentMeetingData(dateTime = "28/01/26 • 11:30 AM", backgroundColor = Color(0xFFFFF3D3), arrowBackgroundColor = Color(0xffFFF9E5)),
        RecentMeetingData(dateTime = "28/01/26 • 11:30 AM", backgroundColor = Color(0xFFD1F2F0), arrowBackgroundColor = Color(0xffECFCFB))
    )

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

            Spacer(modifier = Modifier.height(15.dp))

            // SEARCH BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
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
                                fontWeight = FontWeight.Normal,
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
                    .height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Upcoming Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(30.dp))
                        .then(
                            if (selectedTab == 0) Modifier.background(Color.Black)
                            else Modifier.border(1.dp, Color(0xFFB0B0B0), RoundedCornerShape(30.dp))
                        )
                        .clickable {
                            selectedTab = 0
                            onUpcomingTabClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Upcoming",
                        fontFamily = Lufga,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedTab == 0) Color.White else DarkText
                    )
                }

                // Recent Tab (Selected)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(30.dp))
                        .then(
                            if (selectedTab == 1) Modifier.background(Color.Black)
                            else Modifier.border(1.dp, Color(0xFFB0B0B0), RoundedCornerShape(30.dp))
                        )
                        .clickable { selectedTab = 1 },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Recent",
                        fontFamily = Lufga,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedTab == 1) Color.White else DarkText
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // MEETING CARDS LIST
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(recentMeetings) { meeting ->
                    RecentMeetingCard(
                        data = meeting,
                        onClick = onMeetingCardClick
                    )
                }

                // BOTTOM ACTION CARDS
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Left Action Card -> opens Schedule Meeting
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(Color(0xFFEFE9FE))
                                .clickable { onShowScheduleClick() }
                                .padding(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xffFAF4FF)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.url),
                                        contentDescription = "Link",
                                        tint = DarkText,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Text(
                                    text = "Show\nSchedule\nmetting",
                                    fontFamily = Lufga,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium,
                                    color = DarkText,
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        // Right Action Card -> opens Schedule Meeting Form
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(0xFFFFE4D6))
                                .clickable { onScheduleMeetingClick() }
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xffFFEEE0)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.schedule),
                                        contentDescription = "schedule",
                                        tint = DarkText,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Text(
                                    text = "Schedule\nMeeting",
                                    fontFamily = Lufga,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium,
                                    color = DarkText,
                                    lineHeight = 20.sp
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
fun RecentMeetingCard(
    data: RecentMeetingData,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(data.backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = data.title,
                fontFamily = Lufga,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = Color(0xFF808080),
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = data.dateTime,
                    fontFamily = Lufga,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF808080)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(color = data.arrowBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Open Notes",
                tint = DarkText,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecentMeetingLinksScreenPreview() {
    RecentMeetingLinksScreen()
}
