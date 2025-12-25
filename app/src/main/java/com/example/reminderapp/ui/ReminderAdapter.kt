package com.example.reminderapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reminderapp.R
import com.example.reminderapp.model.ReminderModel
import java.text.SimpleDateFormat
import java.util.*

class ReminderAdapter(
    private val onEditClick: (ReminderModel) -> Unit,
    private val onDeleteClick: (ReminderModel) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var reminders = emptyList<ReminderModel>()

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvReminderTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvReminderDescription)
        val dateTimeTextView: TextView = itemView.findViewById(R.id.tvReminderDateTime)
        val editButton: ImageButton = itemView.findViewById(R.id.btnEdit)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val currentReminder = reminders[position]
        holder.titleTextView.text = currentReminder.title
        holder.descriptionTextView.text = currentReminder.description
        
        val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        holder.dateTimeTextView.text = dateFormat.format(Date(currentReminder.dateTime))

        holder.editButton.setOnClickListener {
            onEditClick(currentReminder)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(currentReminder)
        }
    }

    override fun getItemCount() = reminders.size

    fun setReminders(reminders: List<ReminderModel>) {
        this.reminders = reminders
        notifyDataSetChanged()
    }
}
