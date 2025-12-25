package com.example.reminderapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.reminderapp.model.ReminderModel

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderModel): Long
    
    @Update
    suspend fun updateReminder(reminder: ReminderModel)
    
    @Delete
    suspend fun deleteReminder(reminder: ReminderModel)
    
    @Query("SELECT * FROM reminders ORDER BY dateTime ASC")
    fun getAllReminders(): LiveData<List<ReminderModel>>
    
    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getReminderById(id: Int): ReminderModel?
}
