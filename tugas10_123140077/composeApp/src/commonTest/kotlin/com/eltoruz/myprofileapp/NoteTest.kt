package com.eltoruz.myprofileapp.data

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class NoteTest {

    private lateinit var sampleNote: Note

    @BeforeTest
    fun setup() {
        sampleNote = Note(
            id = 1,
            title = "Test Note",
            content = "This is a test content",
            timestamp = 1000L,
            isFavorite = false
        )
    }

    @Test
    fun noteHasCorrectDefaultValues() {
        val note = Note()
        assertEquals(0, note.id)
        assertEquals("", note.title)
        assertEquals("", note.content)
        assertEquals(0L, note.timestamp)
        assertFalse(note.isFavorite)
    }

    @Test
    fun noteEquality() {
        val note1 = Note(id = 1, title = "Hello", content = "World", timestamp = 100L)
        val note2 = Note(id = 1, title = "Hello", content = "World", timestamp = 100L)
        assertEquals(note1, note2)
    }

    @Test
    fun noteInequalityOnDifferentId() {
        val note1 = Note(id = 1, title = "Same", content = "Same")
        val note2 = Note(id = 2, title = "Same", content = "Same")
        assertNotEquals(note1, note2)
    }

    @Test
    fun noteCopyModifiesCorrectly() {
        val modified = sampleNote.copy(title = "Updated Title", isFavorite = true)
        assertEquals("Updated Title", modified.title)
        assertTrue(modified.isFavorite)
        assertEquals(sampleNote.id, modified.id)
        assertEquals(sampleNote.content, modified.content)
    }

    @Test
    fun noteWithLongTitleIsValid() {
        val longTitle = "a".repeat(255)
        val note = Note(id = 10, title = longTitle, content = "Content")
        assertEquals(255, note.title.length)
        assertNotNull(note.title)
    }

    @Test
    fun noteWithEmptyContentIsAllowed() {
        val note = Note(id = 5, title = "Title Only", content = "")
        assertEquals("", note.content)
        assertEquals("Title Only", note.title)
    }

    @Test
    fun noteTimestampIsPreserved() {
        val timestamp = 1715000000000L
        val note = Note(id = 3, title = "Timestamped", content = "Content", timestamp = timestamp)
        assertEquals(timestamp, note.timestamp)
    }

    @Test
    fun noteFavoriteToggleThroughCopy() {
        assertFalse(sampleNote.isFavorite)
        val favorited = sampleNote.copy(isFavorite = true)
        assertTrue(favorited.isFavorite)
        val unfavorited = favorited.copy(isFavorite = false)
        assertFalse(unfavorited.isFavorite)
    }

    @Test
    fun noteToStringContainsRelevantData() {
        val note = Note(id = 7, title = "Debug Note", content = "Debug content")
        val str = note.toString()
        assertTrue(str.contains("7") || str.contains("Debug Note"))
    }
}
