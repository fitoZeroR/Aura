package com.example.aura.data

import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalEntryDao: JournalEntryDao) {
    val allEntries: Flow<List<JournalEntry>> = journalEntryDao.getAllEntries()

    suspend fun insert(entry: JournalEntry) {
        journalEntryDao.insert(entry)
    }

    suspend fun delete(entry: JournalEntry) {
        journalEntryDao.delete(entry)
    }
}
