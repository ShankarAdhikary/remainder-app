package com.example.reminderapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminderapp.R
import com.example.reminderapp.databinding.ActivityMainBinding
import com.example.reminderapp.model.ReminderModel
import com.example.reminderapp.utils.NotificationUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var reminderViewModel: ReminderViewModel
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create notification channel
        NotificationUtils.createNotificationChannel(this)

        // Setup RecyclerView
        adapter = ReminderAdapter(
            onEditClick = { reminder -> editReminder(reminder) },
            onDeleteClick = { reminder -> showDeleteDialog(reminder) }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Setup ViewModel
        reminderViewModel = ViewModelProvider(this)[ReminderViewModel::class.java]
        reminderViewModel.allReminders.observe(this) { reminders ->
            reminders?.let { adapter.setReminders(it) }
        }

        // FAB click listener
        binding.fabAddReminder.setOnClickListener {
            val intent = Intent(this, AddReminderActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editReminder(reminder: ReminderModel) {
        val intent = Intent(this, AddReminderActivity::class.java).apply {
            putExtra("REMINDER_ID", reminder.id)
            putExtra("REMINDER_TITLE", reminder.title)
            putExtra("REMINDER_DESCRIPTION", reminder.description)
            putExtra("REMINDER_DATE_TIME", reminder.dateTime)
        }
        startActivity(intent)
    }

    private fun showDeleteDialog(reminder: ReminderModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete Reminder")
            .setMessage("Are you sure you want to delete this reminder?")
            .setPositiveButton("Delete") { _, _ ->
                reminderViewModel.delete(reminder)
                NotificationUtils.cancelReminder(this, reminder.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
