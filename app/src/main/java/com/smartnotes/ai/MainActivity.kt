package com.smartnotes.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.smartnotes.ai.Screens.AddNewNotesScreen
import com.smartnotes.ai.Screens.AddDocumentScreen
import com.smartnotes.ai.Screens.AddScheduleMeetingScreen
import com.smartnotes.ai.Screens.AddYouTubeLinkScreen
import com.smartnotes.ai.Screens.AudioFilesScreen
import com.smartnotes.ai.Screens.DocumentFilesScreen
import com.smartnotes.ai.Screens.DocumentTitleScreen
import com.smartnotes.ai.Screens.DocumentViewerScreen
import com.smartnotes.ai.Screens.HomeScreen
import com.smartnotes.ai.Screens.MeetingLinksScreen
import com.smartnotes.ai.Screens.MeetingNotesScreen
import com.smartnotes.ai.Screens.NewNotesScreen
import com.smartnotes.ai.Screens.NoteDetailsScreen
import com.smartnotes.ai.Screens.NotesScreen
import com.smartnotes.ai.Screens.ProfileScreen
import com.smartnotes.ai.Screens.RecentMeetingLinksScreen
import com.smartnotes.ai.Screens.RecentMeetingNotesScreen
import com.smartnotes.ai.Screens.RecordingAudioScreen
import com.smartnotes.ai.Screens.RegisterScreen
import com.smartnotes.ai.Screens.ScheduleTimelineScreen
import com.smartnotes.ai.Screens.YouTubeNotesScreen
import com.smartnotes.ai.db.UserDatabaseHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val dbHelper = remember { UserDatabaseHelper(context) }
            val savedName = remember { dbHelper.getUserName() }

            var currentScreen by remember {
                mutableStateOf(if (savedName.isNotBlank()) "home" else "register")
            }
            var selectedNoteId by remember { mutableIntStateOf(0) }
            var selectedNoteTitle by remember { mutableStateOf("") }
            var selectedNoteDescription by remember { mutableStateOf("") }
            var selectedFileName by remember { mutableStateOf("Project Proposal.pdf") }
            var selectedFileDetails by remember { mutableStateOf("2.4 MB • 12 Pages") }
            var selectedFileUri by remember { mutableStateOf("") }
            var selectedDocTitle by remember { mutableStateOf("Resume") }

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
                        },
                        onNavigateToProfile = {
                            currentScreen = "profile"
                        }
                    )
                }

                "new_notes" -> {
                    NewNotesScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onNewNoteClick = {
                            currentScreen = "add_new_notes"
                        },
                        onNoteClick = { id, title, description ->
                            selectedNoteId = id
                            selectedNoteTitle = title
                            selectedNoteDescription = description
                            currentScreen = "view_note"
                        }
                    )
                }

                "add_new_notes" -> {
                    AddNewNotesScreen(
                        onBackClick = {
                            currentScreen = "new_notes"
                        },
                        onSaveSuccess = {
                            currentScreen = "new_notes"
                        }
                    )
                }

                "view_note" -> {
                    NoteDetailsScreen(
                        noteId = selectedNoteId,
                        noteTitle = selectedNoteTitle,
                        noteDescription = selectedNoteDescription,
                        onBackClick = {
                            currentScreen = "new_notes"
                        },
                        onSaveSuccess = {
                            currentScreen = "new_notes"
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
                        },
                        onNewDocumentClick = {
                            currentScreen = "add_document"
                        },
                        onDocumentClick = { title, details, fileUri ->
                            selectedDocTitle = title
                            selectedFileDetails = details
                            selectedFileUri = fileUri
                            currentScreen = "document_viewer"
                        }
                    )
                }

                "add_document" -> {
                    AddDocumentScreen(
                        onBackClick = {
                            currentScreen = "document_files"
                        },
                        onContinueClick = { name, details, fileUri ->
                            selectedFileName = name
                            selectedFileDetails = details
                            selectedFileUri = fileUri
                            currentScreen = "document_title"
                        }
                    )
                }

                "document_title" -> {
                    DocumentTitleScreen(
                        fileName = selectedFileName,
                        fileDetails = selectedFileDetails,
                        fileUri = selectedFileUri,
                        onBackClick = {
                            currentScreen = "add_document"
                        },
                        onSaveSuccess = {
                            currentScreen = "document_files"
                        }
                    )
                }

                "document_viewer" -> {
                    DocumentViewerScreen(
                        docTitle = selectedDocTitle,
                        fileUri = selectedFileUri,
                        onBackClick = {
                            currentScreen = "document_files"
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
