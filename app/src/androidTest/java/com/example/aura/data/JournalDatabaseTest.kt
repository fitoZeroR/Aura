package com.example.aura.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class JournalDatabaseTest {
    private lateinit var journalDao: JournalEntryDao
    private lateinit var db: AuraDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AuraDatabase::class.java
        ).build()
        journalDao = db.journalEntryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeEntryAndReadInList() = runBlocking {
        val entry = JournalEntry(
            title = "Test Title",
            date = System.currentTimeMillis(),
            content = "Test Content",
            mood = "Happy"
        )
        journalDao.insert(entry)
        val allEntries = journalDao.getAllEntries().first()
        assertEquals(allEntries[0].title, entry.title)
    }

    @Test
    fun deleteEntry() = runBlocking {
        val entry = JournalEntry(
            id = 1,
            title = "Test Title",
            date = System.currentTimeMillis(),
            content = "Test Content",
            mood = "Happy"
        )
        journalDao.insert(entry)
        val entriesBefore = journalDao.getAllEntries().first()
        assertEquals(1, entriesBefore.size)

        journalDao.delete(entriesBefore[0])
        val entriesAfter = journalDao.getAllEntries().first()
        assertEquals(0, entriesAfter.size)
    }
}
