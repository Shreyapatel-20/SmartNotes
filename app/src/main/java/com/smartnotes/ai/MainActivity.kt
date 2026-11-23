package com.smartnotes.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.smartnotes.ai.Screens.AddScheduleMeetingScreen
import com.smartnotes.ai.Screens.AddYouTubeLinkScreen
import com.smartnotes.ai.Screens.AudioFilesScreen
import com.smartnotes.ai.Screens.DocumentFilesScreen
import com.smartnotes.ai.Screens.HomeScreen
import com.smartnotes.ai.Screens.MeetingLinksScreen
import com.smartnotes.ai.Screens.MeetingNotesScreen
import com.smartnotes.ai.Screens.NewNotesScreen
import com.smartnotes.ai.Screens.NotesScreen
import com.smartnotes.ai.Screens.ProfileScreen
import com.smartnotes.ai.Screens.RecentMeetingLinksScreen
import com.smartnotes.ai.Screens.RecentMeetingNotesScreen
import com.smartnotes.ai.Screens.RecordingAudioScreen
import com.smartnotes.ai.Screens.RegisterScreen
import com.smartnotes.ai.Screens.ScheduleTimelineScreen
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
                        onNavigateToNewNotes = {
                            currentScreen = "new_notes"
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

                "new_notes" -> {
                    NewNotesScreen(
                        onBackClick = {
                            currentScreen = "home"
                        }
                    )
                }

                "meeting_links" -> {
                    MeetingLinksScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onRecentTabClick = {
                            currentScreen = "recent_meeting_links"
                        },
                        onMeetingCardClick = {
                            currentScreen = "meeting_notes"
                        },
                        onShowScheduleClick = {
                            currentScreen = "schedule_meeting"
                        },
                        onScheduleMeetingClick = {
                            currentScreen = "add_schedule_meeting"
                        }
                    )
                }

                "recent_meeting_links" -> {
                    RecentMeetingLinksScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onUpcomingTabClick = {
                            currentScreen = "meeting_links"
                        },
                        onMeetingCardClick = {
                            currentScreen = "recent_meeting_notes"
                        },
                        onShowScheduleClick = {
                            currentScreen = "schedule_meeting"
                        },
                        onScheduleMeetingClick = {
                            currentScreen = "add_schedule_meeting"
                        }
                    )
                }

                "recent_meeting_notes" -> {
                    RecentMeetingNotesScreen(
                        onBackClick = {
                            currentScreen = "recent_meeting_links"
                        }
                    )
                }

                "schedule_meeting" -> {
                    ScheduleTimelineScreen(
                        onBackClick = {
                            currentScreen = "recent_meeting_links"
                        },
                        onScheduleTabClick = {
                            currentScreen = "add_schedule_meeting"
                        }
                    )
                }

                "add_schedule_meeting" -> {
                    AddScheduleMeetingScreen(
                        onBackClick = {
                            currentScreen = "recent_meeting_links"
                        },
                        onMeetingToggleClick = {
                            currentScreen = "schedule_meeting"
                        },
                        onContinueClick = {
                            currentScreen = "recent_meeting_links"
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

                "profile" -> {
                    ProfileScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onBottomNavItemSelected = { index ->
                            when (index) {
                                0 -> currentScreen = "home"
                                1 -> currentScreen = "notes"
                                3 -> currentScreen = "profile"
                            }
                        }
                    )
                }

            }
        }
    }
}
