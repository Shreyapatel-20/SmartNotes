package com.smartnotes.ai.Screens.ai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.Screens.common.BottomNavigationBar
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF171717)

@Composable
fun AiChatScreen(
    onSettingsClick: () -> Unit = {},
    onStartChatClick: () -> Unit = {},
    onBottomNavItemSelected: (Int) -> Unit = {}
) {
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 12.dp)
                    .height(55.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = "Ai Chat",
                    fontFamily = Lufga,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xffE5E1FE))
                        .clickable { onSettingsClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.setting),
                        contentDescription = "Settings",
                        tint = DarkText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // CENTRAL GLOWING GRADIENT ORB
            Box(
                modifier = Modifier
                    .size(340.dp)
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

            Spacer(modifier = Modifier.height(48.dp))

            // WELCOME TEXT
            Text(
                text = buildAnnotatedString {
                    append("Welcome to ")
                    withStyle(
                        style = SpanStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFD970DE),
                                    Color(0xFF9B86FD)
                                )
                            ),
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("AI Chat")
                    }
                },
                fontFamily = Lufga,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = DarkText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Welcome to AI chat smart, fast, and friendly\nConversations anytime",
                fontFamily = Lufga,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF808080),
                textAlign = TextAlign.Center,
                lineHeight = 23.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // START CHAT BUTTON
            Button(
                onClick = onStartChatClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Start Chat",
                    color = Color.White,
                    fontFamily = Lufga,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(90.dp))
        }

        // BOTTOM NAVIGATION BAR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            BottomNavigationBar(
                selectedIndex = 2,
                onItemSelected = { index ->
                    onBottomNavItemSelected(index)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AiChatScreenPreview() {
    AiChatScreen()
}
