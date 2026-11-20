
package com.smartnotes.ai.Screens

import android.graphics.fonts.Font
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.smartnotes.ai.R
import com.smartnotes.ai.ui.theme.*

@Composable
fun RegisterScreen(
    onContinueClick: (String) -> Unit
) {

    var userName by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.home
            ),
            contentDescription = "Background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 10.dp)
                .offset(y = 8.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "What should we call you?",
                fontFamily = Lufga,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(50.dp))

            BasicTextField(
                value = userName,
                onValueChange = { newValue ->
                    userName = newValue
                },

                modifier = Modifier
                    .width(400.dp)
                    .height(60.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(40.dp)
                    )
                    .padding(horizontal = 16.dp),

                singleLine = true,

                textStyle = TextStyle(
                    fontFamily = Lufga,
                    fontSize = 12.sp,
                    color = Color.Black
                ),

                decorationBox = { innerTextField ->

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        if (userName.isEmpty()) {
                            Text(
                                text = "User Name",
                                fontFamily = Lufga,
                                fontWeight= FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color(0xFF808080)
                            )
                        }

                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    if (userName.isNotBlank()) {
                        onContinueClick(userName)
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .width(400.dp),

                shape = RoundedCornerShape(40.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),

                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Continue",
                    fontFamily = Lufga,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}
