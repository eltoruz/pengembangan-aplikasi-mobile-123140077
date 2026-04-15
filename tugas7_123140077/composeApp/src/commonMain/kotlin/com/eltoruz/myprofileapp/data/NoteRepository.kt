package com.eltoruz.myprofileapp.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.eltoruz.myprofileapp.db.NotesDatabase
import com.eltoruz.myprofileapp.db.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class NoteRepository(private val database: NotesDatabase) {

    private val queries = database.noteEntityQueries

    // Get all notes as Flow (sorted by updated_at desc)
    fun getAllNotes(sortOrder: String = "updated_at"): Flow<List<Note>> {
        val query = when (sortOrder) {
            "title" -> queries.selectAllByTitle()
            "created_at" -> queries.selectAllByCreatedAt()
            else -> queries.selectAll()
        }
        return query.asFlow().mapToList(Dispatchers.IO).map { entities ->
            entities.map { it.toNote() }
        }
    }

    // Get note by ID
    suspend fun getNoteById(id: Long): Note? {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOneOrNull()?.toNote()
        }
    }

    // Insert new note
    suspend fun insertNote(title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.IO) {
            queries.insert(title, content, now, now)
        }
    }

    // Update note
    suspend fun updateNote(id: Long, title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.IO) {
            queries.update(title, content, now, id)
        }
    }

    // Delete note
    suspend fun deleteNote(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }

    // Search notes
    fun searchNotes(query: String): Flow<List<Note>> {
        val searchQuery = "%$query%"
        return queries.search(searchQuery, searchQuery)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toNote() } }
    }

    // Toggle favorite
    suspend fun toggleFavorite(id: Long) {
        withContext(Dispatchers.IO) {
            queries.toggleFavorite(id)
        }
    }

    // Get favorite notes
    fun getFavoriteNotes(): Flow<List<Note>> {
        return queries.selectFavorites()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toNote() } }
    }
}

// Extension to convert SQLDelight generated NoteEntity to our Note data class
fun NoteEntity.toNote(): Note {
    return Note(
        id = id.toInt(),
        title = title,
        content = content,
        timestamp = updated_at,
        isFavorite = is_favorite == 1L
    )
}
