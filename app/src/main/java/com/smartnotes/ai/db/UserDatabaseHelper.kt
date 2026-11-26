package com.smartnotes.ai.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class NoteModel(
    val id: Int = 0,
    val title: String,
    val subtitle: String = "",
    val dateText: String = ""
)

data class YouTubeNoteModel(
    val id: Int = 0,
    val folderName: String = "YouTube",
    val videoTitle: String = "Video Title",
    val url: String
)

data class DocumentModel(
    val id: Int = 0,
    val title: String,
    val details: String = "12 MB • 12/01/16",
    val fileUri: String = ""
)

data class AudioFileModel(
    val id: Int = 0,
    val title: String,
    val duration: String = "12 : 45 s",
    val timestamp: String = "Today • 9:58 pm",
    val fileUri: String = ""
)

data class ImageModel(
    val id: Int = 0,
    val folderName: String,
    val note: String,
    val imageUri: String,
    val timestamp: String = "08/01/26   1 images"
)

data class MeetingLinkModel(
    val id: Int = 0,
    val title: String,
    val url: String
)

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "SmartNotesUser.db"
        private const val DATABASE_VERSION = 17
        private const val TABLE_USER = "user_profile"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "user_name"

        private const val TABLE_NOTES = "notes"
        private const val COLUMN_NOTE_ID = "note_id"
        private const val COLUMN_NOTE_TITLE = "title"
        private const val COLUMN_NOTE_SUBTITLE = "subtitle"
        private const val COLUMN_NOTE_DATE = "date_text"

        private const val TABLE_YOUTUBE = "youtube_notes"
        private const val COLUMN_YT_ID = "yt_id"
        private const val COLUMN_YT_FOLDER = "folder_name"
        private const val COLUMN_YT_VID_TITLE = "video_title"
        private const val COLUMN_YT_URL = "url"

        private const val TABLE_DOCS = "documents"
        private const val COLUMN_DOC_ID = "doc_id"
        private const val COLUMN_DOC_TITLE = "title"
        private const val COLUMN_DOC_DETAILS = "details"
        private const val COLUMN_DOC_URI = "file_uri"

        private const val TABLE_AUDIO = "audio_files"
        private const val COLUMN_AUDIO_ID = "audio_id"
        private const val COLUMN_AUDIO_TITLE = "title"
        private const val COLUMN_AUDIO_DURATION = "duration"
        private const val COLUMN_AUDIO_TIMESTAMP = "timestamp"
        private const val COLUMN_AUDIO_URI = "file_uri"

        private const val TABLE_IMAGES = "images"
        private const val COLUMN_IMG_ID = "img_id"
        private const val COLUMN_IMG_FOLDER = "folder_name"
        private const val COLUMN_IMG_NOTE = "note"
        private const val COLUMN_IMG_URI = "image_uri"
        private const val COLUMN_IMG_TIMESTAMP = "timestamp"

        private const val TABLE_MEETING_LINKS = "meeting_links"
        private const val COLUMN_ML_ID = "ml_id"
        private const val COLUMN_ML_TITLE = "title"
        private const val COLUMN_ML_URL = "url"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = ("CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL)")
        db.execSQL(createUserTable)

        val createNotesTable = ("CREATE TABLE " + TABLE_NOTES + " ("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOTE_TITLE + " TEXT NOT NULL, "
                + COLUMN_NOTE_SUBTITLE + " TEXT, "
                + COLUMN_NOTE_DATE + " TEXT)")
        db.execSQL(createNotesTable)

        val createYouTubeTable = ("CREATE TABLE " + TABLE_YOUTUBE + " ("
                + COLUMN_YT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_YT_FOLDER + " TEXT NOT NULL, "
                + COLUMN_YT_VID_TITLE + " TEXT NOT NULL, "
                + COLUMN_YT_URL + " TEXT NOT NULL)")
        db.execSQL(createYouTubeTable)

        val createDocsTable = ("CREATE TABLE " + TABLE_DOCS + " ("
                + COLUMN_DOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DOC_TITLE + " TEXT NOT NULL, "
                + COLUMN_DOC_DETAILS + " TEXT, "
                + COLUMN_DOC_URI + " TEXT)")
        db.execSQL(createDocsTable)

        val createAudioTable = ("CREATE TABLE " + TABLE_AUDIO + " ("
                + COLUMN_AUDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AUDIO_TITLE + " TEXT NOT NULL, "
                + COLUMN_AUDIO_DURATION + " TEXT, "
                + COLUMN_AUDIO_TIMESTAMP + " TEXT, "
                + COLUMN_AUDIO_URI + " TEXT)")
        db.execSQL(createAudioTable)

        val createImagesTable = ("CREATE TABLE " + TABLE_IMAGES + " ("
                + COLUMN_IMG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_IMG_FOLDER + " TEXT NOT NULL, "
                + COLUMN_IMG_NOTE + " TEXT, "
                + COLUMN_IMG_URI + " TEXT, "
                + COLUMN_IMG_TIMESTAMP + " TEXT)")
        db.execSQL(createImagesTable)

        val createMeetingLinksTable = ("CREATE TABLE " + TABLE_MEETING_LINKS + " ("
                + COLUMN_ML_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ML_TITLE + " TEXT NOT NULL, "
                + COLUMN_ML_URL + " TEXT NOT NULL)")
        db.execSQL(createMeetingLinksTable)

        // Seed default sample data
        seedDefaultNotes(db)
        seedDefaultDocuments(db)
        seedDefaultImages(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 17) {
            try {
                db.execSQL("DELETE FROM $TABLE_YOUTUBE WHERE $COLUMN_YT_FOLDER IN ('Youtube', 'YouTube', 'Instagram', 'Facebook')")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun seedDefaultNotes(db: SQLiteDatabase) {
        val sampleNotes = listOf(
            Triple("Exam IMP Question", "08/01/26   What is UI/UX?", "08/01/26"),
            Triple("Aman Preparation", "08/01/26   Study preparation", "08/01/26"),
            Triple("Monthly Expense", "08/01/26   Monthly budget & bills", "08/01/26")
        )
        for (note in sampleNotes) {
            val values = ContentValues().apply {
                put(COLUMN_NOTE_TITLE, note.first)
                put(COLUMN_NOTE_SUBTITLE, note.second)
                put(COLUMN_NOTE_DATE, note.third)
            }
            db.insert(TABLE_NOTES, null, values)
        }
    }

    private fun seedDefaultYouTubeNotes(db: SQLiteDatabase) {
        // No dummy data seeded
    }

    private fun seedDefaultDocuments(db: SQLiteDatabase) {
        val sampleDocs = listOf(
            Triple("Resume", "12 MB • 12/01/16", "")
        )
        for (doc in sampleDocs) {
            val values = ContentValues().apply {
                put(COLUMN_DOC_TITLE, doc.first)
                put(COLUMN_DOC_DETAILS, doc.second)
                put(COLUMN_DOC_URI, doc.third)
            }
            db.insert(TABLE_DOCS, null, values)
        }
    }

    private fun seedDefaultImages(db: SQLiteDatabase) {
        val sampleImages = listOf(
            listOf("Exam IMP Question", "Sample note", "", "08/01/26   5 images"),
            listOf("Agman Preparation", "Sample note 2", "", "08/01/26   5 images")
        )
        for (img in sampleImages) {
            val values = ContentValues().apply {
                put(COLUMN_IMG_FOLDER, img[0])
                put(COLUMN_IMG_NOTE, img[1])
                put(COLUMN_IMG_URI, img[2])
                put(COLUMN_IMG_TIMESTAMP, img[3])
            }
            db.insert(TABLE_IMAGES, null, values)
        }
    }

    // USER PROFILE METHODS
    fun saveUserName(name: String) {
        try {
            val db = writableDatabase ?: return
            val values = ContentValues().apply {
                put(COLUMN_NAME, name.trim())
            }

            val cursor = db.rawQuery("SELECT $COLUMN_ID FROM $TABLE_USER LIMIT 1", null)
            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                db.update(TABLE_USER, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
            } else {
                db.insert(TABLE_USER, null, values)
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getUserName(): String {
        return try {
            val db = readableDatabase ?: return ""
            var name = ""
            val cursor = db.rawQuery("SELECT $COLUMN_NAME FROM $TABLE_USER LIMIT 1", null)
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            }
            cursor.close()
            name
        } catch (e: Exception) {
            ""
        }
    }

    // NOTES Persistence Methods
    fun insertNote(title: String, subtitle: String = "", dateText: String = "08/01/26"): Long {
        return try {
            val db = writableDatabase ?: return -1L
            val values = ContentValues().apply {
                put(COLUMN_NOTE_TITLE, title.ifBlank { "Untitled Note" })
                val sub = if (subtitle.isNotBlank()) {
                    if (subtitle.contains("/")) subtitle else "$dateText   $subtitle"
                } else {
                    "$dateText   Note content"
                }
                put(COLUMN_NOTE_SUBTITLE, sub)
                put(COLUMN_NOTE_DATE, dateText)
            }
            db.insert(TABLE_NOTES, null, values)
        } catch (e: Exception) {
            -1L
        }
    }

    fun updateNote(noteId: Int, title: String, subtitle: String, dateText: String = "08/01/26"): Int {
        return try {
            val db = writableDatabase ?: return -1
            val values = ContentValues().apply {
                put(COLUMN_NOTE_TITLE, title.ifBlank { "Untitled Note" })
                val sub = if (subtitle.isNotBlank()) {
                    if (subtitle.contains("/")) subtitle else "$dateText   $subtitle"
                } else {
                    "$dateText   Note content"
                }
                put(COLUMN_NOTE_SUBTITLE, sub)
                put(COLUMN_NOTE_DATE, dateText)
            }
            if (noteId > 0) {
                db.update(TABLE_NOTES, values, "$COLUMN_NOTE_ID = ?", arrayOf(noteId.toString()))
            } else {
                db.update(TABLE_NOTES, values, "$COLUMN_NOTE_TITLE = ?", arrayOf(title))
            }
        } catch (e: Exception) {
            -1
        }
    }

    fun getAllNotes(): List<NoteModel> {
        val list = mutableListOf<NoteModel>()
        try {
            val db = readableDatabase ?: return list
            val cursor = db.rawQuery("SELECT * FROM $TABLE_NOTES ORDER BY $COLUMN_NOTE_ID DESC", null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NOTE_ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_TITLE))
                    val subtitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_SUBTITLE)) ?: ""
                    val dateText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_DATE)) ?: ""
                    list.add(NoteModel(id, title, subtitle, dateText))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun deleteNote(noteId: Int): Int {
        return try {
            val db = writableDatabase ?: return -1
            db.delete(TABLE_NOTES, "$COLUMN_NOTE_ID = ?", arrayOf(noteId.toString()))
        } catch (e: Exception) {
            -1
        }
    }

    // YOUTUBE / URL NOTES Methods (Folder Name, Video Title, URL)
    fun insertYouTubeNote(folderName: String, videoTitle: String, url: String): Long {
        var result = -1L
        try {
            val db = writableDatabase ?: return -1L
            val values = ContentValues().apply {
                put(COLUMN_YT_FOLDER, folderName.ifBlank { "YouTube" })
                put(COLUMN_YT_VID_TITLE, videoTitle.ifBlank { "Video Title" })
                put(COLUMN_YT_URL, url.trim())
            }
            result = db.insert(TABLE_YOUTUBE, null, values)
            if (result == -1L) {
                db.execSQL("DROP TABLE IF EXISTS $TABLE_YOUTUBE")
                db.execSQL("CREATE TABLE $TABLE_YOUTUBE ($COLUMN_YT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_YT_FOLDER TEXT NOT NULL, $COLUMN_YT_VID_TITLE TEXT NOT NULL, $COLUMN_YT_URL TEXT NOT NULL)")
                result = db.insert(TABLE_YOUTUBE, null, values)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun getAllYouTubeNotes(): List<YouTubeNoteModel> {
        val list = mutableListOf<YouTubeNoteModel>()
        try {
            val db = readableDatabase ?: return list
            var cursor: android.database.Cursor? = null
            try {
                cursor = db.rawQuery("SELECT * FROM $TABLE_YOUTUBE ORDER BY $COLUMN_YT_ID DESC", null)
            } catch (e: Exception) {
                val wDb = writableDatabase
                if (wDb != null) {
                    wDb.execSQL("DROP TABLE IF EXISTS $TABLE_YOUTUBE")
                    wDb.execSQL("CREATE TABLE $TABLE_YOUTUBE ($COLUMN_YT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_YT_FOLDER TEXT NOT NULL, $COLUMN_YT_VID_TITLE TEXT NOT NULL, $COLUMN_YT_URL TEXT NOT NULL)")
                    cursor = db.rawQuery("SELECT * FROM $TABLE_YOUTUBE ORDER BY $COLUMN_YT_ID DESC", null)
                }
            }

            if (cursor != null && cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex(COLUMN_YT_ID)
                val folderIdx = cursor.getColumnIndex(COLUMN_YT_FOLDER)
                val titleIdx = cursor.getColumnIndex(COLUMN_YT_VID_TITLE)
                val legacyTitleIdx = cursor.getColumnIndex("title")
                val urlIdx = cursor.getColumnIndex(COLUMN_YT_URL)

                do {
                    val id = if (idIdx != -1) cursor.getInt(idIdx) else 0
                    val folderName = if (folderIdx != -1) cursor.getString(folderIdx) ?: "YouTube" else "YouTube"
                    val videoTitle = if (titleIdx != -1) {
                        cursor.getString(titleIdx) ?: "Video Title"
                    } else if (legacyTitleIdx != -1) {
                        cursor.getString(legacyTitleIdx) ?: "Video Title"
                    } else "Video Title"
                    val url = if (urlIdx != -1) cursor.getString(urlIdx) ?: "" else ""

                    list.add(YouTubeNoteModel(id, folderName, videoTitle, url))
                } while (cursor.moveToNext())
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun deleteYouTubeNote(ytId: Int): Int {
        return try {
            val db = writableDatabase ?: return -1
            db.delete(TABLE_YOUTUBE, "$COLUMN_YT_ID = ?", arrayOf(ytId.toString()))
        } catch (e: Exception) {
            -1
        }
    }

    fun deleteYouTubeNotesByFolder(folderName: String): Int {
        return try {
            val db = writableDatabase ?: return -1
            db.delete(TABLE_YOUTUBE, "$COLUMN_YT_FOLDER = ?", arrayOf(folderName))
        } catch (e: Exception) {
            -1
        }
    }

    // DOCUMENTS Methods
    fun insertDocument(title: String, details: String = "2.4 MB • 12 Pages", fileUri: String = ""): Long {
        return try {
            val db = writableDatabase ?: return -1L
            val values = ContentValues().apply {
                put(COLUMN_DOC_TITLE, title.ifBlank { "Project Proposal.pdf" })
                put(COLUMN_DOC_DETAILS, details)
                put(COLUMN_DOC_URI, fileUri)
            }
            db.insert(TABLE_DOCS, null, values)
        } catch (e: Exception) {
            -1L
        }
    }

    fun getAllDocuments(): List<DocumentModel> {
        val list = mutableListOf<DocumentModel>()
        try {
            val db = readableDatabase ?: return list
            val cursor = db.rawQuery("SELECT * FROM $TABLE_DOCS ORDER BY $COLUMN_DOC_ID DESC", null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOC_ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOC_TITLE))
                    val details = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOC_DETAILS)) ?: "12 MB • 12/01/16"
                    val fileUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOC_URI)) ?: ""
                    list.add(DocumentModel(id, title, details, fileUri))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun deleteDocument(docId: Int): Int {
        return try {
            val db = writableDatabase ?: return -1
            db.delete(TABLE_DOCS, "$COLUMN_DOC_ID = ?", arrayOf(docId.toString()))
        } catch (e: Exception) {
            -1
        }
    }

    // AUDIO FILES Methods
    fun insertAudioFile(title: String, duration: String = "12 : 45 s", timestamp: String = "Today • 9:58 pm", fileUri: String = ""): Long {
        return try {
            val db = writableDatabase ?: return -1L
            val values = ContentValues().apply {
                put(COLUMN_AUDIO_TITLE, title.ifBlank { "Voice Recording" })
                put(COLUMN_AUDIO_DURATION, duration)
                put(COLUMN_AUDIO_TIMESTAMP, timestamp)
                put(COLUMN_AUDIO_URI, fileUri)
            }
            db.insert(TABLE_AUDIO, null, values)
        } catch (e: Exception) {
            -1L
        }
    }

    fun getAllAudioFiles(): List<AudioFileModel> {
        val list = mutableListOf<AudioFileModel>()
        try {
            val db = readableDatabase ?: return list
            val cursor = db.rawQuery("SELECT * FROM $TABLE_AUDIO ORDER BY $COLUMN_AUDIO_ID DESC", null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AUDIO_ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUDIO_TITLE))
                    val duration = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUDIO_DURATION)) ?: "12 : 45 s"
                    val timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUDIO_TIMESTAMP)) ?: "Today"
                    val fileUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUDIO_URI)) ?: ""
                    list.add(AudioFileModel(id, title, duration, timestamp, fileUri))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun deleteAudioFile(audioId: Int): Int {
        return try {
            val db = writableDatabase ?: return -1
            db.delete(TABLE_AUDIO, "$COLUMN_AUDIO_ID = ?", arrayOf(audioId.toString()))
        } catch (e: Exception) {
            -1
        }
    }

    fun updateAudioFileTitle(audioId: Int, newTitle: String): Int {
        return try {
            val db = writableDatabase ?: return -1
            val values = ContentValues().apply {
                put(COLUMN_AUDIO_TITLE, newTitle.ifBlank { "Voice Recording" })
            }
            db.update(TABLE_AUDIO, values, "$COLUMN_AUDIO_ID = ?", arrayOf(audioId.toString()))
        } catch (e: Exception) {
            -1
        }
    }

    // IMAGES Methods
    fun insertImage(folderName: String, note: String, imageUri: String, timestamp: String = "08/01/26   1 images"): Long {
        return try {
            val db = writableDatabase ?: return -1L
            val values = ContentValues().apply {
                put(COLUMN_IMG_FOLDER, folderName.ifBlank { "Exam IMP Question" })
                put(COLUMN_IMG_NOTE, note)
                put(COLUMN_IMG_URI, imageUri)
                put(COLUMN_IMG_TIMESTAMP, timestamp)
            }
            db.insert(TABLE_IMAGES, null, values)
        } catch (e: Exception) {
            -1L
        }
    }

    fun getAllImages(): List<ImageModel> {
        val list = mutableListOf<ImageModel>()
        try {
            val db = readableDatabase ?: return list
            val cursor = db.rawQuery("SELECT * FROM $TABLE_IMAGES ORDER BY $COLUMN_IMG_ID DESC", null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG_ID))
                    val folderName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG_FOLDER))
                    val note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG_NOTE)) ?: ""
                    val imageUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG_URI)) ?: ""
                    val timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG_TIMESTAMP)) ?: "08/01/26   1 images"
                    list.add(ImageModel(id, folderName, note, imageUri, timestamp))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun deleteImage(imgId: Int): Int {
        return try {
            val db = writableDatabase ?: return -1
            db.delete(TABLE_IMAGES, "$COLUMN_IMG_ID = ?", arrayOf(imgId.toString()))
        } catch (e: Exception) {
            -1
        }
    }

    fun deleteImagesByFolder(folderName: String): Int {
        return try {
            val db = writableDatabase ?: return -1
            db.delete(TABLE_IMAGES, "$COLUMN_IMG_FOLDER = ?", arrayOf(folderName))
        } catch (e: Exception) {
            -1
        }
    }
}
