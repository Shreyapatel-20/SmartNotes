package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.ui.theme.Lufga
import androidx.compose.ui.modifier.*

private val DarkText = Color(0xFF000000)

data class TimelineMeetingData(
    val startTime: String,
    val endTime: String,
    val title: String,
    val subtitle: String = "To Start a project align goals clarity roles and set expectations.........",
    val cardBackgroundColor: Color,
    val accentStripeColor: Color
)

@Composable
fun ScheduleTimelineScreen(
    onBackClick: () -> Unit = {},
    onScheduleTabClick: () -> Unit = {},
    onMeetingCardClick: () -> Unit = {}
) {
    var selectedDayIndex by remember { mutableIntStateOf(2) } // Tue 14 default
    var selectedSegmentTab by remember { mutableIntStateOf(0) } // 0: Meeting, 1: Schedule

    val daysList = remember {
        listOf(
            "Sun" to "12",
            "Mon" to "13",
            "Tue" to "14",
            "Wed" to "15",
            "Thu" to "16",
            "Fri" to "17",
            "Sat" to "18"
        )
    }

    val timelineMeetings = remember {
        listOf(
            TimelineMeetingData("09 AM", "10 AM", "Meeting 1", cardBackgroundColor = Color(0xFFE2D8FD), accentStripeColor = Color(0xFF8B5CF6)),
            TimelineMeetingData("11 AM", "12 AM", "Meeting 2", cardBackgroundColor = Color(0xFFD0F2EF), accentStripeColor = Color(0xFF14B8A6)),
            TimelineMeetingData("11 AM", "12 AM", "Meeting 3", cardBackgroundColor = Color(0xFFFFD8D8), accentStripeColor = Color(0xFFF43F5E)),
            TimelineMeetingData("11 AM", "12 AM", "Meeting 4", cardBackgroundColor = Color(0xFFFFF3C4), accentStripeColor = Color(0xFFEAB308)),
            TimelineMeetingData("11 AM", "12 AM", "Meeting 5", cardBackgroundColor = Color(0xFFF3E8FF), accentStripeColor = Color(0xFFA855F7))
        )
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
                    text = "Schedule Meeting",
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

            // SEGMENTED TOGGLE (Meeting / Schedule)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(276.2.dp)
                        .background(Color.White.copy(alpha = 0.09f))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .background(Color.White)
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Meeting Tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(26.dp))
                            .background(if (selectedSegmentTab == 0) Color.Black else Color.Transparent)
                            .clickable { selectedSegmentTab = 0 },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Meeting",
                            fontFamily = Lufga,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (selectedSegmentTab == 0) Color.White else DarkText
                        )
                    }

                    // Schedule Tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(26.dp))
                            .background(if (selectedSegmentTab == 1) Color.Black else Color.Transparent)
                            .clickable {
                                selectedSegmentTab = 1
                                onScheduleTabClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Schedule",
                            fontFamily = Lufga,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (selectedSegmentTab == 1) Color.White else DarkText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // CALENDAR WEEK PICKER BOX (Matching Screenshot with 276.2dp blur & 9% white opacity)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(276.2.dp)
                        .background(Color.White.copy(alpha = 0.09f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.background(Color.White.copy(alpha = 0.09f))
                        .border(1.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(28.dp))
                        .padding(16.dp)
                ) {
                    // Month Header (< June 2026 >)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Previous Month",
                            tint = DarkText,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { }
                        )

                        Text(
                            text = "June 2026",
                            fontFamily = Lufga,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = DarkText
                        )

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Next Month",
                            tint = DarkText,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { }
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Days Row with Outlined Capsule Pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        daysList.forEachIndexed { index, pair ->
                            val isSelected = (index == selectedDayIndex)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .width(44.dp)
                                    .height(70.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    //.background(Color.Transparent)
                                    .background(
                                        brush=if (isSelected){
                                            Brush.verticalGradient(
                                        colors=listOf(
                                            Color(0xFFC084FC),
                                            Color(0xff9B86FD)
                                        )
                                            )
                                    } else {
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Transparent
                                                )
                                            )})
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) Color.Black else Color(0xFFC0C0C0),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { selectedDayIndex = index }
                                    .padding(vertical = 6.dp)
                            ) {
                                Text(
                                    text = pair.first,
                                    fontFamily = Lufga,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = if (isSelected) Color.Black else Color(0xFF808080)
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = pair.second,
                                    fontFamily = Lufga,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.Black else Color(0xFF808080)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TIMELINE LIST
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(timelineMeetings) { item ->
                    TimelineMeetingCardRow(
                        data = item,
                        onClick = onMeetingCardClick
                    )
                }
            }
        }
    }

}

@Composable
fun TimelineMeetingCardRow(
    data: TimelineMeetingData,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // TOP TIME MARKER
        Row(
            //verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column (
                modifier = Modifier.height(88.dp).width(67.dp),

            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.startTime,
                        fontFamily = Lufga,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF808080),
                        modifier = Modifier.width(48.dp)
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFC8C8C8),
                        modifier = Modifier.width(21.dp)
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(

                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                    //.padding(top = 4.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = data.endTime,
                        fontFamily = Lufga,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF808080),
                        modifier = Modifier.width(48.dp)
                    )
               // Spacer(modifier = Modifier.weight(1f))
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFC8C8C8),
                        modifier = Modifier
                            .width(21.dp)
                    )
                }

            }



            Spacer(modifier = Modifier.width(6.dp))
//
//
            // OUTER BOX
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(88.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(data.accentStripeColor)
            ) {

                // INNER BOX
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 8.dp,  // Left accent stripe
                            //top = 4.dp,
                            //end = 4.dp,
                            //bottom = 4.dp
                        )
                        .clip(RoundedCornerShape(26.dp))
                        .background(data.cardBackgroundColor)
                        .clickable { onClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = data.title,
                            fontFamily = Lufga,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = DarkText
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = data.subtitle,
                            fontFamily = Lufga,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF242529),
                            lineHeight = 17.sp
                        )
                    }
                }
            }

        }


    }
    Spacer(modifier = Modifier.size(15.dp))
}

@Preview(showBackground = true)
@Composable
fun ScheduleTimelineScreenPreview() {
    ScheduleTimelineScreen()
}
