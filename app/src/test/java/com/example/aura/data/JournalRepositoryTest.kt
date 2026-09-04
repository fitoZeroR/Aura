package com.example.aura.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class JournalRepositoryTest {

    private lateinit var repository: JournalRepository
    private val journalEntryDao: JournalEntryDao = mockk()

    @Before
    fun setUp() {
        every { journalEntryDao.getAllEntries() } returns flowOf(emptyList())
        repository = JournalRepository(journalEntryDao)
    }

    @Test
    fun `insert calls dao insert`() = runBlocking {
        // Given
        val entry = JournalEntry(
            title = "Test Title",
            date = 123456789L,
            content = "Test Content",
            mood = "Happy"
        )
        coEvery { journalEntryDao.insert(entry) } returns Unit

        // When
        repository.insert(entry)

        // Then
        coVerify(exactly = 1) { journalEntryDao.insert(entry) }
    }

    @Test
    fun `delete calls dao delete`() = runBlocking {
        // Given
        val entry = JournalEntry(
            title = "Test Title",
            date = 123456789L,
            content = "Test Content",
            mood = "Happy"
        )
        coEvery { journalEntryDao.delete(entry) } returns Unit

        // When
        repository.delete(entry)

        // Then
        coVerify(exactly = 1) { journalEntryDao.delete(entry) }
    }

    @Test
    fun `allEntries returns flow from dao`() = runBlocking {
        // Given
        val entries = listOf(
            JournalEntry(title = "Entry 1", date = 1L, content = "Content 1", mood = "Neutral"),
            JournalEntry(title = "Entry 2", date = 2L, content = "Content 2", mood = "Sad")
        )
        every { journalEntryDao.getAllEntries() } returns flowOf(entries)

        // Re-create repository to capture the mocked flow during initialization
        val repository = JournalRepository(journalEntryDao)

        // When
        val resultFlow = repository.allEntries

        // Then
        resultFlow.collect { result ->
            assertEquals(entries, result)
        }
    }
}
