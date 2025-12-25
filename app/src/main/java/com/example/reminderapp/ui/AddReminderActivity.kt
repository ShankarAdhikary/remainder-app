package com.example.reminderapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.reminderapp.databinding.ActivityAddReminderBinding
import com.example.reminderapp.model.ReminderModel
import com.example.reminderapp.utils.NotificationUtils
import java.text.SimpleDateFormat
import java.util.*

class AddReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReminderBinding
    private lateinit var reminderViewModel: ReminderViewModel
    private var selectedDateTime: Long = 0
    private var reminderId: Int = 0
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reminderViewModel = ViewModelProvider(this)[ReminderViewModel::class.java]

        // Check if we're editing an existing reminder
        intent?.let {
            if (it.hasExtra("REMINDER_ID")) {
                isEditMode = true
                reminderId = it.getIntExtra("REMINDER_ID", 0)
                binding.etTitle.setText(it.getStringExtra("REMINDER_TITLE"))
                binding.etDescription.setText(it.getStringExtra("REMINDER_DESCRIPTION"))
                selectedDateTime = it.getLongExtra("REMINDER_DATE_TIME", 0)
                updateDateTimeDisplay()
                binding.btnSaveReminder.text = "Update Reminder"
            }
        }

        binding.btnSelectDateTime.setOnClickListener {
            showDateTimePicker()
        }

        binding.btnSaveReminder.setOnClickListener {
            saveReminder()
        }
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        if (selectedDateTime != 0L) {
            calendar.timeInMillis = selectedDateTime
        }

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        calendar.set(year, month, dayOfMonth, hourOfDay, minute, 0)
                        selectedDateTime = calendar.timeInMillis
                        updateDateTimeDisplay()
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateTimeDisplay() {
        if (selectedDateTime != 0L) {
            val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
            binding.tvSelectedDateTime.text = dateFormat.format(Date(selectedDateTime))
        }
    }

    private fun saveReminder() {
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedDateTime == 0L) {
            Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedDateTime < System.currentTimeMillis()) {
            Toast.makeText(this, "Please select a future date and time", Toast.LENGTH_SHORT).show()
            return
        }

        val reminder = ReminderModel(
            id = if (isEditMode) reminderId else 0,
            title = title,
            description = description,
            dateTime = selectedDateTime
        )

        if (isEditMode) {
            reminderViewModel.update(reminder)
            NotificationUtils.cancelReminder(this, reminderId)
            NotificationUtils.scheduleReminder(this, reminderId, title, description, selectedDateTime)
            Toast.makeText(this, "Reminder updated", Toast.LENGTH_SHORT).show()
        } else {
            reminderViewModel.insert(reminder) { id ->
                NotificationUtils.scheduleReminder(this, id.toInt(), title, description, selectedDateTime)
            }
            Toast.makeText(this, "Reminder added", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
