package com.eltoruz.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.eltoruz.myprofileapp.data.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteViewModel : ViewModel() {

    private var nextId = 6
    private var timestampCounter = 1712300000000L

    private val _notes = MutableStateFlow(
        listOf(
            Note(
                id = 1,
                title = "Belajar Kotlin",
                content = "Kotlin adalah bahasa pemrograman modern yang digunakan untuk pengembangan Android, server-side, dan multiplatform. Kotlin mendukung null safety, extension functions, dan coroutines.",
                timestamp = 1711900000000L,
                isFavorite = true
            ),
            Note(
                id = 2,
                title = "Compose Multiplatform",
                content = "Compose Multiplatform memungkinkan kita membangun UI deklaratif yang berjalan di Android, iOS, Desktop, dan Web menggunakan satu codebase Kotlin.",
                timestamp = 1711986400000L,
                isFavorite = false
            ),
            Note(
                id = 3,
                title = "MVVM Architecture",
                content = "Model-View-ViewModel memisahkan logika bisnis dari UI. ViewModel menyimpan state menggunakan StateFlow dan memungkinkan UI reactive.",
                timestamp = 1712072800000L,
                isFavorite = true
            ),
            Note(
                id = 4,
                title = "State Management",
                content = "remember + mutableStateOf menyimpan state yang survive recomposition. State hoisting memindahkan state ke parent composable.",
                timestamp = 1712159200000L,
                isFavorite = false
            ),
            Note(
                id = 5,
                title = "Navigation Component",
                content = "NavHost, NavController, dan Routes digunakan untuk navigasi multi-screen dalam Compose Multiplatform. Gunakan sealed class untuk mendefinisikan routes.",
                timestamp = 1712245600000L,
                isFavorite = true
            )
        )
    )
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun getNoteById(id: Int): Note? {
        return _notes.value.find { it.id == id }
    }

    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = nextId++,
            title = title.trim(),
            content = content.trim(),
            timestamp = timestampCounter++,
            isFavorite = false
        )
        _notes.update { it + newNote }
    }

    fun updateNote(id: Int, title: String, content: String) {
        _notes.update { list ->
            list.map { note ->
                if (note.id == id) {
                    note.copy(
                        title = title.trim(),
                        content = content.trim(),
                        timestamp = timestampCounter++
                    )
                } else note
            }
        }
    }

    fun deleteNote(id: Int) {
        _notes.update { list -> list.filter { it.id != id } }
    }

    fun toggleFavorite(id: Int) {
        _notes.update { list ->
            list.map { note ->
                if (note.id == id) note.copy(isFavorite = !note.isFavorite) else note
            }
        }
    }
}
