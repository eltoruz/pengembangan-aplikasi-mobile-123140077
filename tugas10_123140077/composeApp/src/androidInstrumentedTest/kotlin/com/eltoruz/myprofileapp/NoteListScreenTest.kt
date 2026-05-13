package com.eltoruz.myprofileapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.eltoruz.myprofileapp.data.Note
import com.eltoruz.myprofileapp.screens.NoteListScreen
import com.eltoruz.myprofileapp.utils.TestTags
import com.eltoruz.myprofileapp.viewmodel.NotesUiState
import org.junit.Rule
import org.junit.Test

class NoteListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyState_showsEmptyMessage() {
        composeTestRule.setContent {
            NoteListScreen(
                uiState = NotesUiState.Empty,
                searchQuery = "",
                onSearchQueryChange = {},
                onNoteClick = {},
                onToggleFavorite = {},
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.EMPTY_STATE)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Belum ada catatan")
            .assertIsDisplayed()
    }

    @Test
    fun loadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            NoteListScreen(
                uiState = NotesUiState.Loading,
                searchQuery = "",
                onSearchQueryChange = {},
                onNoteClick = {},
                onToggleFavorite = {},
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_STATE)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Memuat catatan...")
            .assertIsDisplayed()
    }

    @Test
    fun contentState_showsNotesList() {
        val notes = listOf(
            Note(id = 1, title = "Test Note Satu", content = "Konten satu"),
            Note(id = 2, title = "Test Note Dua", content = "Konten dua")
        )

        composeTestRule.setContent {
            NoteListScreen(
                uiState = NotesUiState.Content(notes),
                searchQuery = "",
                onSearchQueryChange = {},
                onNoteClick = {},
                onToggleFavorite = {},
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.NOTES_LIST)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Test Note Satu")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Test Note Dua")
            .assertIsDisplayed()
    }

    @Test
    fun searchInput_isDisplayed() {
        composeTestRule.setContent {
            NoteListScreen(
                uiState = NotesUiState.Empty,
                searchQuery = "",
                onSearchQueryChange = {},
                onNoteClick = {},
                onToggleFavorite = {},
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.SEARCH_INPUT)
            .assertIsDisplayed()
    }

    @Test
    fun emptySearchResult_showsSearchNotFoundMessage() {
        composeTestRule.setContent {
            NoteListScreen(
                uiState = NotesUiState.Empty,
                searchQuery = "query tidak ada",
                onSearchQueryChange = {},
                onNoteClick = {},
                onToggleFavorite = {},
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.EMPTY_STATE)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Tidak ditemukan")
            .assertIsDisplayed()
    }

    @Test
    fun contentState_showsCorrectNoteCount() {
        val notes = listOf(
            Note(id = 1, title = "Catatan A", content = "Content A"),
            Note(id = 2, title = "Catatan B", content = "Content B"),
            Note(id = 3, title = "Catatan C", content = "Content C")
        )

        composeTestRule.setContent {
            NoteListScreen(
                uiState = NotesUiState.Content(notes),
                searchQuery = "",
                onSearchQueryChange = {},
                onNoteClick = {},
                onToggleFavorite = {},
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithText("3 catatan")
            .assertIsDisplayed()
    }
}
