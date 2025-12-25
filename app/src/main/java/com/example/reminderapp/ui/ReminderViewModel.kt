package com.example.reminderapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.database.ReminderDatabase
import com.example.reminderapp.database.ReminderRepository
import com.example.reminderapp.model.ReminderModel
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ReminderRepository
    val allReminders: LiveData<List<ReminderModel>>

    init {
        val reminderDao = ReminderDatabase.getDatabase(application).reminderDao()
        repository = ReminderRepository(reminderDao)
        allReminders = repository.allReminders
    }

    fun insert(reminder: ReminderModel, onInserted: (Long) -> Unit) = viewModelScope.launch {
        val id = repository.insert(reminder)
        onInserted(id)
    }

    fun update(reminder: ReminderModel) = viewModelScope.launch {
        repository.update(reminder)
    }

    fun delete(reminder: ReminderModel) = viewModelScope.launch {
        repository.delete(reminder)
    }
}
