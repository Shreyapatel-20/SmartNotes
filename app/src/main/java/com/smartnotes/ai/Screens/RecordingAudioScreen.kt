package com.smartnotes.ai.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

@Composable
fun RecordingAudioScreen(
    onBackClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    onRecordClick: () -> Unit = {}
) {
    val waveformHeights = listOf(
        25.dp, 40.dp, 58.dp, 28.dp,
        75.dp, 53.dp, 38.dp, 35.dp,
        47.dp, 59.dp, 75.dp, 47.dp,
        35.dp, 35.dp
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
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 20.dp)
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
                    text = "Recording",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(36.dp))
            }

            Spacer(modifier = Modifier.size(53.dp))

            Box(
                modifier = Modifier
                    .size(342.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .shadow(
                            elevation = 24.dp,
                            shape = CircleShape,
                            spotColor = Color(0xFFC084FC).copy(alpha = 0.35f)
                        )
                        .clip(CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color(0xFF60A5FF),
                                    0.33f to Color(0xFF7AFBFF),
                                    0.59f to Color(0xFFA9C6FF),
                                    1.0f to Color(0xFFE8A2FF)
                                )
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.weight(0.4f))
            Spacer(modifier = Modifier.size(34.11.dp))
            // AUDIO WAVEFORM
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(95.78.dp).fillMaxWidth()
            ) {
                waveformHeights.forEach { height ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.5.dp)
                            .width(6.dp)
                            .height(height)
                            .clip(RoundedCornerShape(50))
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.0f to Color(0xffD970DE),
                                        100.0f to Color(0xff9B86FD),

                                    )
                                )
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.11.dp))

            // SUBTITLE TEXT
            Text(
                text = "Easily record and organize meetings\nwith Smart Notes.",
                fontFamily = Lufga,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF808080),
                textAlign = TextAlign.Center,
                lineHeight = 23.sp
            )

            Spacer(modifier = Modifier.size(52.dp))

            // BOTTOM CONTROLS
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp).height(60.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
            ) {


                // MIC BUTTON
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)
                        .shadow(12.dp, CircleShape, spotColor = Color(0x339333EA))
                        .clip(CircleShape)

                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFD970DE), Color(0xFF9B86FD))
                            )
                        )
                        .clickable { onRecordClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.microphone),
                        contentDescription = "Microphone",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                //Spacer(modifier = Modifier.size(99.dp))

                // CLOSE BUTTON
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterEnd)
                        //.shadow(4.dp, CircleShape, spotColor = Color.Black.copy(alpha = 0.1f))
                        //.clip(CircleShape)
                        .clickable { onCloseClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = DarkText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            Spacer(modifier = Modifier.size(40.dp))

            }
            Spacer(modifier = Modifier.size(150.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordingAudioScreenPreview() {
    RecordingAudioScreen()
}
