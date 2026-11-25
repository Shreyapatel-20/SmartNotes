package com.smartnotes.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.smartnotes.ai.Screens.AddNewNotesScreen
import com.smartnotes.ai.Screens.AddDocumentScreen
import com.smartnotes.ai.Screens.AddImageFolderScreen
import com.smartnotes.ai.Screens.AddImageScreen
import com.smartnotes.ai.Screens.AddScheduleMeetingScreen
import com.smartnotes.ai.Screens.AddYouTubeLinkScreen
import com.smartnotes.ai.Screens.AudioFilesScreen
import com.smartnotes.ai.Screens.AudioUploadScreen
import com.smartnotes.ai.Screens.DocumentFilesScreen
import com.smartnotes.ai.Screens.DocumentTitleScreen
import com.smartnotes.ai.Screens.DocumentViewerScreen
import com.smartnotes.ai.Screens.HomeScreen
import com.smartnotes.ai.Screens.ImageFilesScreen
import com.smartnotes.ai.Screens.ImageViewerScreen
import com.smartnotes.ai.Screens.MeetingLinksScreen
import com.smartnotes.ai.Screens.MeetingNotesScreen
import com.smartnotes.ai.Screens.NewNotesScreen
import com.smartnotes.ai.Screens.NoteDetailsScreen
import com.smartnotes.ai.Screens.NotesScreen
import com.smartnotes.ai.Screens.ProfileScreen
import com.smartnotes.ai.Screens.RecentMeetingLinksScreen
import com.smartnotes.ai.Screens.RecentMeetingNotesScreen
import com.smartnotes.ai.Screens.RecordingAudioScreen
import com.smartnotes.ai.Screens.RecordingFilesScreen
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
            var selectedFolderTitle by remember { mutableStateOf("Nature") }

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
                        onNavigateToAudioUpload = {
                            currentScreen = "audio_upload"
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
                            currentScreen = "audio_upload"
                        }
                    )
                }

                "audio_upload" -> {
                    AudioUploadScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onContinueClick = { title, fileUri ->
                            dbHelper.insertAudioFile(title = title, fileUri = fileUri)
                            currentScreen = "audio_files"
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
                        onNavigateToRecordingFiles = {
                            currentScreen = "recording_files"
                        },
                        onNavigateToImageFiles = {
                            currentScreen = "image_files"
                        }
                    )
                }

                "image_files" -> {
                    ImageFilesScreen(
                        onBackClick = {
                            currentScreen = "notes"
                        },
                        onAddNewImageClick = {
                            currentScreen = "add_image_folder"
                        },
                        onFolderClick = { folderTitle ->
                            selectedFolderTitle = folderTitle
                            currentScreen = "image_viewer"
                        }
                    )
                }

                "image_viewer" -> {
                    ImageViewerScreen(
                        folderTitle = selectedFolderTitle,
                        onBackClick = {
                            currentScreen = "image_files"
                        },
                        onAddImageClick = {
                            currentScreen = "add_image"
                        }
                    )
                }

                "add_image_folder" -> {
                    AddImageFolderScreen(
                        onBackClick = {
                            currentScreen = "image_files"
                        },
                        onContinueClick = {
                            currentScreen = "image_files"
                        }
                    )
                }

                "add_image" -> {
                    AddImageScreen(
                        folderTitle = selectedFolderTitle,
                        onBackClick = {
                            currentScreen = "image_viewer"
                        },
                        onContinueClick = {
                            currentScreen = "image_viewer"
                        },
                        onFolderFieldClick = {
                            currentScreen = "add_image_folder"
                        }
                    )
                }

                "recording_files" -> {
                    RecordingFilesScreen(
                        onBackClick = {
                            currentScreen = "home"
                        },
                        onNewRecordingClick = {
                            currentScreen = "recording_audio"
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
