package com.example.aura.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JournalEntry::class], version = 1, exportSchema = false)
abstract class AuraDatabase : RoomDatabase() {
    abstract fun journalEntryDao(): JournalEntryDao
}
