package com.smartnotes.ai.Screens.profile
import com.smartnotes.ai.Screens.common.BottomNavigationBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import com.smartnotes.ai.R
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onMenuItemClick: (String) -> Unit = {},
    onBottomNavItemSelected: (Int) -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    var savedUserName by remember {
        mutableStateOf(
            if (isPreview) "" else UserDatabaseHelper(context).getUserName()
        )
    }
    val displayName = if (savedUserName.isNotBlank()) savedUserName else "Mikal jordan"

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
                    .padding(top = 58.dp, bottom = 12.dp)
                    .height(22.dp),
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
                    text = "Profile",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(36.dp))
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // AVATAR WITH EDIT BADGE
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.size(110.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .shadow(2.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { onEditProfileClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.edit_thin),
                            contentDescription = "Edit Profile",
                            tint = DarkText,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // USER NAME
                Text(
                    text = displayName,
                    fontFamily = Lufga,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(32.dp))

                // MENU OPTIONS CARD
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(26.dp))
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
                            .background(Color.White.copy(alpha = 0.2f))
                            .border(1.dp, Color.White, RoundedCornerShape(26.dp))
                            .padding(vertical = 6.dp)
                    ) {
                        // 1. Like us, Rate us
                        ProfileMenuItemVector(
                            icon = painterResource(R.drawable.star_line),
                            title = "Like us, Rate us",
                            onClick = { onMenuItemClick("Rate") }
                        )

                        HorizontalDivider(color = Color.White.copy(alpha = 0.6f), thickness = 0.8.dp)

                        // 2. Notification
                        ProfileMenuItemPainter(
                            icon = painterResource(R.drawable.notification_line),
                            title = "Notification",
                            onClick = { onMenuItemClick("Notification") }
                        )

                        HorizontalDivider(color = Color.White.copy(alpha = 0.6f), thickness = 0.8.dp)

                        // 3. Share App
                        ProfileMenuItemVector(
                            icon = painterResource(R.drawable.share),
                            title = "Share App",
                            onClick = { onMenuItemClick("Share") }
                        )

                        HorizontalDivider(color = Color.White.copy(alpha = 0.6f), thickness = 0.8.dp)

                        // 4. Privacy Policy
                        ProfileMenuItemVector(
                            icon = painterResource(R.drawable.privacy_tip_outline),
                            title = "Privacy Policy",
                            onClick = { onMenuItemClick("Privacy") }
                        )

                        HorizontalDivider(color = Color.White.copy(alpha = 0.6f), thickness = 0.8.dp)

                        // 5. Authentication
                        ProfileMenuItemPainter(
                            icon = painterResource(R.drawable.humbleicons_document),
                            title = "Authentication",
                            onClick = { onMenuItemClick("Authentication") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
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
                selectedIndex = 3,
                onItemSelected = onBottomNavItemSelected
            )
        }
    }
}

@Composable
fun ProfileMenuItemVector(
    icon: Painter,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = title,
            tint = DarkText,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontFamily = Lufga,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = DarkText
        )
    }
}

@Composable
fun ProfileMenuItemPainter(
    icon: Painter,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = title,
            tint = DarkText,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontFamily = Lufga,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = DarkText
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
