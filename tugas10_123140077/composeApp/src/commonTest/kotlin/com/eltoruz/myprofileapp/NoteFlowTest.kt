package com.eltoruz.myprofileapp.viewmodel

import app.cash.turbine.test
import com.eltoruz.myprofileapp.data.Note
import com.eltoruz.myprofileapp.data.NoteRepository
import com.eltoruz.myprofileapp.data.SettingsManager
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NoteFlowTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockRepository = mockk<NoteRepository>()
    private val mockSettingsManager = mockk<SettingsManager>()

    private val note1 = Note(id = 1, title = "Flow Note 1", content = "Content 1", timestamp = 1000L)
    private val note2 = Note(id = 2, title = "Flow Note 2", content = "Content 2", timestamp = 2000L)
    private val note3 = Note(id = 3, title = "Favorite Note", content = "Fav Content", timestamp = 3000L, isFavorite = true)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { mockSettingsManager.sortOrder } returns "updated_at"
        coEvery { mockRepository.getFavoriteNotes() } returns flowOf(emptyList())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun notesUiStateFlowEmitsLoadingThenContent() = runTest {
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(listOf(note1, note2))
        coEvery { mockRepository.searchNotes(any()) } returns flowOf(listOf(note1, note2))

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.notesUiState.test {
            val first = awaitItem()
            assertIs<NotesUiState>(first)

            val second = awaitItem()
            assertIs<NotesUiState.Content>(second)
            assertEquals(2, second.notes.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun notesUiStateFlowEmitsLoadingThenEmpty() = runTest {
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(emptyList())
        coEvery { mockRepository.searchNotes(any()) } returns flowOf(emptyList())

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.notesUiState.test {
            val first = awaitItem()
            assertIs<NotesUiState>(first)

            val second = awaitItem()
            assertIs<NotesUiState.Empty>(second)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun searchQueryFlowEmitsCorrectSequence() = runTest {
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(listOf(note1, note2))
        coEvery { mockRepository.searchNotes(any()) } returns flowOf(listOf(note1))

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.searchQuery.test {
            assertEquals("", awaitItem())

            viewModel.updateSearchQuery("Flow")
            assertEquals("Flow", awaitItem())

            viewModel.updateSearchQuery("")
            assertEquals("", awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun favoriteNotesFlowEmitsCorrectList() = runTest {
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(listOf(note1, note2, note3))
        coEvery { mockRepository.searchNotes(any()) } returns flowOf(emptyList())
        coEvery { mockRepository.getFavoriteNotes() } returns flowOf(listOf(note3))

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.favoriteNotes.test {
            val first = awaitItem()
            val favorites = if (first.isEmpty()) awaitItem() else first

            assertEquals(1, favorites.size)
            assertTrue(favorites[0].isFavorite)
            assertEquals("Favorite Note", favorites[0].title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun notesUiStateReflectsCorrectNoteData() = runTest {
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(listOf(note1))
        coEvery { mockRepository.searchNotes(any()) } returns flowOf(listOf(note1))

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.notesUiState.test {
            skipItems(1)
            val content = awaitItem() as NotesUiState.Content
            assertEquals("Flow Note 1", content.notes[0].title)
            assertEquals("Content 1", content.notes[0].content)
            assertEquals(1000L, content.notes[0].timestamp)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun sortOrderFlowEmitsDefaultThenUpdated() = runTest {
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(listOf(note1))
        coEvery { mockRepository.searchNotes(any()) } returns flowOf(emptyList())
        every { mockSettingsManager.sortOrder = any() } answers { }

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.sortOrder.test {
            assertEquals("updated_at", awaitItem())

            viewModel.updateSortOrder("title")
            assertEquals("title", awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
