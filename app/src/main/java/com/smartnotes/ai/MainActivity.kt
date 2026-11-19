package com.smartnotes.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.smartnotes.ai.Screens.HomeScreen
import com.smartnotes.ai.Screens.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentScreen by remember {
                mutableStateOf("register")
            }

            when (currentScreen) {

                "register" -> {
                    RegisterScreen(
                        onContinueClick = { username ->
                            currentScreen = "home"
                        }
                    )
                }

                "home" -> {
                    HomeScreen()
                }
            }
        }
    }
}



