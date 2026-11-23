package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
fun AddScheduleMeetingScreen(
    onBackClick: () -> Unit = {},
    onMeetingToggleClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    var meetingName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableIntStateOf(14) }
    var selectedSlot by remember { mutableStateOf("9:00 AM") }

    val morningSlots = listOf("8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM")
    val afternoonSlots = listOf("3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM")

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

            // SEGMENTED TOGGLE (Meeting / Schedule - "Schedule" selected)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White.copy(alpha = 0.85f))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Meeting (Unselected)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(26.dp))
                        .background(Color.Transparent)
                        .clickable { onMeetingToggleClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Meeting",
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkText
                    )
                }

                // Schedule (Selected)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(26.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Schedule",
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // FORM CONTENT
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ADD MEETING NAME LABEL & FIELD
                Column {
                    Text(
                        text = "Add meeting Name",
                        fontFamily = Lufga,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .width(400.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = meetingName,
                            onValueChange = { meetingName = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontFamily = Lufga,
                                fontSize = 16.sp,
                                color = DarkText
                            ),
                            decorationBox = { innerTextField ->
                                if (meetingName.isEmpty()) {
                                    Text(
                                        text = "Meeting Name",
                                        fontFamily = Lufga,
                                        fontSize = 16.sp,
                                        color = Color(0xFF999999)
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }

                // DATE PICKER CARD
                Column(
                    modifier = Modifier
                        .fillMaxWidth().height(161.dp)
                        .clip(RoundedCornerShape(22.dp))
                        //.background(Color(0xFFF3EBFD))
                        .background(Color.White.copy(alpha = 0.09f))
                        .border(1.dp,Color.White, RoundedCornerShape(28.dp))
                        .blur(276.2.dp)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Previous Month",
                            tint = DarkText,
                            modifier = Modifier.size(30.dp)
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
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val days = listOf(
                            "Sun" to 12, "Mon" to 13, "Tue" to 14,
                            "Wed" to 15, "Thu" to 16, "Fri" to 17, "Sat" to 18
                        )

                        days.forEach { (day, dateNum) ->
                            val isSelected = (dateNum == selectedDate)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable { selectedDate = dateNum }
                                    .height(72.dp).width(44.dp)
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
                                    .padding(horizontal = 8.dp, vertical = 10.dp)

                            ) {
                                Text(
                                    text = day,
                                    fontFamily = Lufga,
                                    fontSize = 11.sp,
                                    color = if (isSelected) Color.White else Color(0xFF808080)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = dateNum.toString(),
                                    fontFamily = Lufga,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else DarkText
                                )
                            }
                        }
                    }
                }

                // MORNING SLOTS
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .blur(276.2.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(26.dp))
                        .background(Color.White.copy(alpha = 0.09f))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Morning",
                            fontFamily = Lufga,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkText
                        )
                        Text(
                            text = "6 Slots",
                            fontFamily = Lufga,
                            fontSize = 14.sp,
                            color = Color(0xFF808080)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    SlotGrid(
                        slots = morningSlots,
                        selectedSlot = selectedSlot,
                        onSlotSelected = { selectedSlot = it }
                    )
                }

                // AFTERNOON SLOTS
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .blur(276.2.dp)
                        .background(Color.White.copy(alpha = 0.09f))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Afternoon",
                            fontFamily = Lufga,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText
                        )
                        Text(
                            text = "6 Slots",
                            fontFamily = Lufga,
                            fontSize = 12.sp,
                            color = Color(0xFF808080)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    SlotGrid(
                        slots = afternoonSlots,
                        selectedSlot = selectedSlot,
                        onSlotSelected = { selectedSlot = it }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // CONTINUE BUTTON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(bottom = 28.dp)
            ) {
                Button(
                    onClick = onContinueClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
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
        Spacer(modifier = Modifier.height(56.dp))

    }
}

@Composable
fun SlotGrid(
    slots: List<String>,
    selectedSlot: String,
    onSlotSelected: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val rows = slots.chunked(3)
        rows.forEach { rowSlots ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowSlots.forEach { slot ->
                    val isSelected = (slot == selectedSlot)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(39.dp)
                            .clip(RoundedCornerShape(30.dp))
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
                            .border(1.dp, color =if(isSelected) Color.Transparent else  Color(0xff808080), RoundedCornerShape(30.dp))
                            .clip(RoundedCornerShape(30.dp))
                            .clickable { onSlotSelected(slot) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = slot,
                            fontFamily = Lufga,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (isSelected) Color.White else DarkText
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScheduleMeetingScreenPreview() {
    AddScheduleMeetingScreen()
}
