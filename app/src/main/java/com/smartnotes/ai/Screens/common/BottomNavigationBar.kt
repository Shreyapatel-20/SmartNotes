package com.smartnotes.ai.Screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smartnotes.ai.R

private val DarkText = Color(0xFF171717)

@Composable
fun BottomNavigationBar(
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {}
) {
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
            icon = painterResource(R.drawable.homeicon),
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) }
        )

        BottomNavItem(
            icon = painterResource(R.drawable.document),
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) }
        )

        BottomNavItem(
            icon = painterResource(R.drawable.ai_magic),
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) }
        )

        BottomNavItem(
            icon = painterResource(R.drawable.profileicon),
            selected = selectedIndex == 3,
            onClick = { onItemSelected(3) }
        )
    }
}

@Composable
fun BottomNavItem(
    icon: Painter,
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
                            Color(0xFFB978E8),
                            Color(0xFF634DE3)
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
            painter = icon,
            contentDescription = null,
            tint = if (selected) Color.White else DarkText,
            modifier = Modifier.size(26.dp)
        )
    }
}
