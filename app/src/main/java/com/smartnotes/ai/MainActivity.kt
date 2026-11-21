package com.smartnotes.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.smartnotes.ai.Screens.AddYouTubeLinkScreen
import com.smartnotes.ai.Screens.AudioFilesScreen
import com.smartnotes.ai.Screens.DocumentFilesScreen
import com.smartnotes.ai.Screens.HomeScreen
import com.smartnotes.ai.Screens.MeetingLinksScreen
import com.smartnotes.ai.Screens.MeetingNotesScreen
import com.smartnotes.ai.Screens.NotesScreen
import com.smartnotes.ai.Screens.RecordingAudioScreen
import com.smartnotes.ai.Screens.RegisterScreen
import com.smartnotes.ai.Screens.YouTubeNotesScreen

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
                        onContinueClick = { _ ->
                            currentScreen = "home"
                        }
                    )
                }

                "home" -> {
                    HomeScreen(
                        onNavigateToNotes = {
                            currentScreen = "notes"
                        },
                        onNavigateToYouTubeNotes = {
                            currentScreen = "youtube_notes"
                        },
                        onNavigateToRecordingAudio = {
                            currentScreen = "recording_audio"
                        },
                        onNavigateToAudioFiles = {
                            currentScreen = "audio_files"
                        },
                        onNavigateToDocumentFiles = {
                            currentScreen = "document_files"
                        },
                        onNavigateToMeetingLinks = {
                            currentScreen = "meeting_links"
                        }
                    )
                }

                "meeting_links" -> {
                    MeetingLinksScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onMeetingCardClick = {
                            currentScreen = "meeting_notes"
                        }
                    )
                }

                "meeting_notes" -> {
                    MeetingNotesScreen(
                        onBackClick = {
                            currentScreen = "meeting_links"
                        }
                    )
                }

                "recording_audio" -> {
                    RecordingAudioScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onCloseClick = {
                            currentScreen = "home"
                        }
                    )
                }

                "audio_files" -> {
                    AudioFilesScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onNewAudioFileClick = {
                            currentScreen = "recording_audio"
                        }
                    )
                }

                "document_files" -> {
                    DocumentFilesScreen(
                        onBackClick = {
                            currentScreen = "home"
                        }
                    )
                }

                "notes" -> {
                    NotesScreen(
                        onNavigateToHome = {
                            currentScreen = "home"
                        },
                        onNavigateToYouTubeNotes = {
                            currentScreen = "youtube_notes"
                        }
                    )
                }

                "youtube_notes" -> {
                    YouTubeNotesScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onAddNewLinkClick = {
                            currentScreen = "add_youtube_link"
                        }
                    )
                }

                "add_youtube_link" -> {
                    AddYouTubeLinkScreen(
                        onBackClick = {
                            currentScreen = "youtube_notes"
                        },
                        onContinueClick = { _, _ ->
                            currentScreen = "youtube_notes"
                        }
                    )
                }

            }
        }
    }
}
