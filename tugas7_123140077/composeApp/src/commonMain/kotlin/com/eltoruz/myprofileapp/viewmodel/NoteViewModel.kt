package com.eltoruz.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltoruz.myprofileapp.data.Note
import com.eltoruz.myprofileapp.data.NoteRepository
import com.eltoruz.myprofileapp.data.SettingsManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// UI States
sealed class NotesUiState {
    object Loading : NotesUiState()
    object Empty : NotesUiState()
    data class Content(val notes: List<Note>) : NotesUiState()
}

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModel(
    private val repository: NoteRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Sort order from settings
    private val _sortOrder = MutableStateFlow(settingsManager.sortOrder)
    val sortOrder: StateFlow<String> = _sortOrder.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(true)

    // All notes depending on search query and sort order
    val notesUiState: StateFlow<NotesUiState> = combine(
        _searchQuery,
        _sortOrder,
        _isLoading
    ) { query, sort, loading ->
        Triple(query, sort, loading)
    }.flatMapLatest { (query, sort, _) ->
        if (query.isBlank()) {
            repository.getAllNotes(sort)
        } else {
            repository.searchNotes(query)
        }
    }.map { notes ->
        _isLoading.value = false
        if (notes.isEmpty()) {
            NotesUiState.Empty
        } else {
            NotesUiState.Content(notes)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NotesUiState.Loading
    )

    // Favorite notes
    val favoriteNotes: StateFlow<List<Note>> = repository.getFavoriteNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getNoteById(id: Int): Note? {
        // For navigation-based detail, we use a blocking approach
        // In a more complex app, you'd use a separate state for this
        val currentState = notesUiState.value
        return when (currentState) {
            is NotesUiState.Content -> currentState.notes.find { it.id == id }
            else -> null
        } ?: favoriteNotes.value.find { it.id == id }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(title.trim(), content.trim())
        }
    }

    fun updateNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            repository.updateNote(id.toLong(), title.trim(), content.trim())
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNote(id.toLong())
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            repository.toggleFavorite(id.toLong())
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSortOrder(sort: String) {
        _sortOrder.value = sort
        settingsManager.sortOrder = sort
    }
}
