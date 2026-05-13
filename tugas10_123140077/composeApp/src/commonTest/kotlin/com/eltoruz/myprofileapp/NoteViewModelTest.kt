package com.eltoruz.myprofileapp.viewmodel

import app.cash.turbine.test
import com.eltoruz.myprofileapp.data.Note
import com.eltoruz.myprofileapp.data.NoteRepository
import com.eltoruz.myprofileapp.data.SettingsManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
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
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockRepository = mockk<NoteRepository>()
    private val mockSettingsManager = mockk<SettingsManager>()

    private val testNote1 = Note(id = 1, title = "Catatan Pertama", content = "Isi catatan pertama", timestamp = 1000L)
    private val testNote2 = Note(id = 2, title = "Catatan Kedua", content = "Isi catatan kedua", timestamp = 2000L)
    private val testNotes = listOf(testNote1, testNote2)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { mockSettingsManager.sortOrder } returns "updated_at"
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(testNotes)
        coEvery { mockRepository.searchNotes(any()) } returns flowOf(testNotes)
        coEvery { mockRepository.getFavoriteNotes() } returns flowOf(emptyList())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialUiStateIsLoading() = runTest {
        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.notesUiState.test {
            val state = awaitItem()
            assertIs<NotesUiState>(state)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun uiStateEmitsContentWhenNotesExist() = runTest {
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(testNotes)

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.notesUiState.test {
            skipItems(1)
            val state = awaitItem()
            assertIs<NotesUiState.Content>(state)
            assertEquals(2, state.notes.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun uiStateEmitsEmptyWhenNoNotes() = runTest {
        coEvery { mockRepository.getAllNotes(any()) } returns flowOf(emptyList())

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.notesUiState.test {
            skipItems(1)
            val state = awaitItem()
            assertIs<NotesUiState.Empty>(state)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun addNoteCallsRepositoryInsert() = runTest {
        coEvery { mockRepository.insertNote(any(), any()) } just Runs

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)
        viewModel.addNote("Judul Baru", "Konten Baru")
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { mockRepository.insertNote("Judul Baru", "Konten Baru") }
    }

    @Test
    fun deleteNoteCallsRepositoryDelete() = runTest {
        coEvery { mockRepository.deleteNote(any()) } just Runs

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)
        viewModel.deleteNote(1)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { mockRepository.deleteNote(1L) }
    }

    @Test
    fun updateNoteCallsRepositoryUpdate() = runTest {
        coEvery { mockRepository.updateNote(any(), any(), any()) } just Runs

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)
        viewModel.updateNote(1, "Updated Title", "Updated Content")
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { mockRepository.updateNote(1L, "Updated Title", "Updated Content") }
    }

    @Test
    fun toggleFavoriteCallsRepositoryToggle() = runTest {
        coEvery { mockRepository.toggleFavorite(any()) } just Runs

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)
        viewModel.toggleFavorite(2)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { mockRepository.toggleFavorite(2L) }
    }

    @Test
    fun searchQueryUpdatesStateFlow() = runTest {
        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.searchQuery.test {
            assertEquals("", awaitItem())

            viewModel.updateSearchQuery("test query")

            assertEquals("test query", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getNoteByIdReturnsNullWhenNotFound() = runTest {
        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)
        val result = viewModel.getNoteById(999)
        assertNull(result)
    }

    @Test
    fun addNoteTrimsTitleAndContent() = runTest {
        coEvery { mockRepository.insertNote(any(), any()) } just Runs

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)
        viewModel.addNote("  Judul Spasi  ", "  Konten Spasi  ")
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { mockRepository.insertNote("Judul Spasi", "Konten Spasi") }
    }

    @Test
    fun sortOrderInitialValueFromSettings() = runTest {
        every { mockSettingsManager.sortOrder } returns "title"
        coEvery { mockRepository.getAllNotes("title") } returns flowOf(testNotes)

        val viewModel = NoteViewModel(mockRepository, mockSettingsManager)

        viewModel.sortOrder.test {
            assertEquals("title", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
