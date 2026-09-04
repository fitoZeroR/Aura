package com.example.aura.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aura.data.JournalEntry
import com.example.aura.data.JournalRepository
import kotlinx.coroutines.launch
import java.time.Instant

class ComposerViewModel(private val repository: JournalRepository) : ViewModel() {

    var title by mutableStateOf("")
    var date by mutableLongStateOf(Instant.now().toEpochMilli())
    var content by mutableStateOf("")
    var mood by mutableStateOf("Calm")

    val isInputValid: Boolean
        get() = title.isNotBlank() && content.isNotBlank()

    fun resetFields() {
        title = ""
        date = Instant.now().toEpochMilli()
        content = ""
        mood = "Calm"
    }

    fun saveEntry(onSuccess: () -> Unit) {
        if (isInputValid) {
            viewModelScope.launch {
                val entry = JournalEntry(
                    title = title,
                    date = date,
                    content = content,
                    mood = mood,
                )
                repository.insert(entry)
                resetFields()
                onSuccess()
            }
        }
    }

    class Factory(private val repository: JournalRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ComposerViewModel::class.java)) {
                return ComposerViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
