package com.example.reminderapp.database

import androidx.lifecycle.LiveData
import com.example.reminderapp.model.ReminderModel

class ReminderRepository(private val reminderDao: ReminderDao) {
    
    val allReminders: LiveData<List<ReminderModel>> = reminderDao.getAllReminders()
    
    suspend fun insert(reminder: ReminderModel): Long {
        return reminderDao.insertReminder(reminder)
    }
    
    suspend fun update(reminder: ReminderModel) {
        reminderDao.updateReminder(reminder)
    }
    
    suspend fun delete(reminder: ReminderModel) {
        reminderDao.deleteReminder(reminder)
    }
    
    suspend fun getReminderById(id: Int): ReminderModel? {
        return reminderDao.getReminderById(id)
    }
}
