package com.example.aura

import android.app.Application
import androidx.room.Room
import com.example.aura.data.AuraDatabase
import com.example.aura.data.JournalRepository

class AuraApplication : Application() {
    private val database by lazy {
        Room.databaseBuilder(
            this,
            AuraDatabase::class.java,
            "aura_database",
        ).build()
    }

    val repository by lazy {
        JournalRepository(database.journalEntryDao())
    }
}
