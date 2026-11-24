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
    val title: String,
    val url: String
)

data class DocumentModel(
    val id: Int = 0,
    val title: String,
    val details: String = "12 MB • 12/01/16",
    val fileUri: String = ""
)

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "SmartNotesUser.db"
        private const val DATABASE_VERSION = 5
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
        private const val COLUMN_YT_TITLE = "title"
        private const val COLUMN_YT_URL = "url"

        private const val TABLE_DOCS = "documents"
        private const val COLUMN_DOC_ID = "doc_id"
        private const val COLUMN_DOC_TITLE = "title"
        private const val COLUMN_DOC_DETAILS = "details"
        private const val COLUMN_DOC_URI = "file_uri"
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
                + COLUMN_YT_TITLE + " TEXT NOT NULL, "
                + COLUMN_YT_URL + " TEXT NOT NULL)")
        db.execSQL(createYouTubeTable)

        val createDocsTable = ("CREATE TABLE " + TABLE_DOCS + " ("
                + COLUMN_DOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DOC_TITLE + " TEXT NOT NULL, "
                + COLUMN_DOC_DETAILS + " TEXT, "
                + COLUMN_DOC_URI + " TEXT)")
        db.execSQL(createDocsTable)

        // Seed default sample data
        seedDefaultNotes(db)
        seedDefaultYouTubeNotes(db)
        seedDefaultDocuments(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            val createNotesTable = ("CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + " ("
                    + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NOTE_TITLE + " TEXT NOT NULL, "
                    + COLUMN_NOTE_SUBTITLE + " TEXT, "
                    + COLUMN_NOTE_DATE + " TEXT)")
            db.execSQL(createNotesTable)
            seedDefaultNotes(db)
        }
        if (oldVersion < 3) {
            val createYouTubeTable = ("CREATE TABLE IF NOT EXISTS " + TABLE_YOUTUBE + " ("
                    + COLUMN_YT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_YT_TITLE + " TEXT NOT NULL, "
                    + COLUMN_YT_URL + " TEXT NOT NULL)")
            db.execSQL(createYouTubeTable)
            seedDefaultYouTubeNotes(db)
        }
        if (oldVersion < 4) {
            val createDocsTable = ("CREATE TABLE IF NOT EXISTS " + TABLE_DOCS + " ("
                    + COLUMN_DOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DOC_TITLE + " TEXT NOT NULL, "
                    + COLUMN_DOC_DETAILS + " TEXT)")
            db.execSQL(createDocsTable)
            seedDefaultDocuments(db)
        }
        if (oldVersion < 5) {
            try {
                db.execSQL("ALTER TABLE $TABLE_DOCS ADD COLUMN $COLUMN_DOC_URI TEXT")
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
        val sampleYt = listOf(
            "Flutter tutorial" to "https://youtube.com/watch?v=...",
            "AI Productivity" to "https://youtube.com/watch?v=...",
            "UI UX Tutorial Full Video" to "https://youtube.com/watch?v=...",
            "AI Productivity" to "https://youtube.com/watch?v=..."
        )
        for (item in sampleYt) {
            val values = ContentValues().apply {
                put(COLUMN_YT_TITLE, item.first)
                put(COLUMN_YT_URL, item.second)
            }
            db.insert(TABLE_YOUTUBE, null, values)
        }
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

    // YOUTUBE NOTES Methods
    fun insertYouTubeNote(title: String, url: String): Long {
        return try {
            val db = writableDatabase ?: return -1L
            val values = ContentValues().apply {
                put(COLUMN_YT_TITLE, title.ifBlank { "YouTube Video" })
                put(COLUMN_YT_URL, url.trim())
            }
            db.insert(TABLE_YOUTUBE, null, values)
        } catch (e: Exception) {
            -1L
        }
    }

    fun getAllYouTubeNotes(): List<YouTubeNoteModel> {
        val list = mutableListOf<YouTubeNoteModel>()
        try {
            val db = readableDatabase ?: return list
            val cursor = db.rawQuery("SELECT * FROM $TABLE_YOUTUBE ORDER BY $COLUMN_YT_ID DESC", null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YT_ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YT_TITLE))
                    val url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YT_URL))
                    list.add(YouTubeNoteModel(id, title, url))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
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
}
