# Features and Implementation Details

## Core Features

### 1. Add Reminders
- Users can create new reminders with:
  - **Title**: Required field for the reminder name
  - **Description**: Optional field for additional details
  - **Date & Time**: Future date and time when the reminder should trigger
  
**Implementation**: 
- Uses Material Design TextInputLayout for clean input fields
- DatePickerDialog and TimePickerDialog for easy date/time selection
- Validation ensures all required fields are filled and time is in the future

### 2. Edit & Delete Reminders
- **Edit**: Tap the edit icon on any reminder to modify it
- **Delete**: Tap the delete icon and confirm deletion
- Changes are immediately reflected in the UI

**Implementation**:
- Edit mode pre-fills form with existing reminder data
- Delete operation shows confirmation dialog to prevent accidental deletion
- When editing, existing alarm is canceled and rescheduled with new time
- When deleting, scheduled alarm is also canceled

### 3. Notification Reminders
- Push notifications appear at the scheduled time
- Notifications include reminder title and description
- Tapping notification opens the app

**Implementation**:
- Uses Android AlarmManager for precise timing
- NotificationManager displays the notification
- Notification channel created for Android 8.0+
- AlarmReceiver BroadcastReceiver handles alarm events
- Supports exact alarms for accurate timing

### 4. Simple UI
- Material Design 3 components throughout
- Clean, minimalistic design
- Easy navigation between screens
- Floating Action Button (FAB) for quick access to add reminders

**Implementation**:
- Material Components library for consistent design
- RecyclerView with CardView items for reminder list
- CoordinatorLayout for smooth FAB behavior
- Toolbar with app branding

## Technical Architecture

### MVVM Pattern
```
View (Activities) ↔ ViewModel ↔ Repository ↔ DAO ↔ Database
```

- **View**: Activities display UI and handle user interactions
- **ViewModel**: Manages UI-related data and survives configuration changes
- **Repository**: Single source of truth for data operations
- **DAO**: Database access methods
- **Database**: Room persistence library for local storage

### Data Flow

1. **Adding a Reminder**:
   - User fills form → ViewModel.insert() → Repository → DAO → Room Database
   - After insert, schedules alarm via NotificationUtils
   - LiveData notifies observers of data change
   - UI updates automatically

2. **Editing a Reminder**:
   - User modifies form → ViewModel.update() → Repository → DAO → Room Database
   - Cancels old alarm and schedules new one
   - LiveData notifies observers
   - UI updates

3. **Deleting a Reminder**:
   - User confirms deletion → ViewModel.delete() → Repository → DAO → Room Database
   - Cancels scheduled alarm
   - LiveData notifies observers
   - UI updates

4. **Displaying Reminders**:
   - MainActivity observes LiveData from ViewModel
   - Room query returns all reminders ordered by dateTime
   - RecyclerView adapter displays list
   - Changes automatically propagate to UI

5. **Triggering Notifications**:
   - AlarmManager fires at scheduled time
   - AlarmReceiver receives broadcast
   - Creates and displays notification
   - User can tap to open app

## Libraries Used

### Core Android
- `androidx.core:core-ktx`: Kotlin extensions for Android
- `androidx.appcompat:appcompat`: Backward compatibility
- `androidx.constraintlayout:constraintlayout`: Flexible layouts
- `androidx.coordinatorlayout:coordinatorlayout`: Advanced layouts

### Material Design
- `com.google.android.material:material`: Material Design components

### Room Database
- `androidx.room:room-runtime`: Room database runtime
- `androidx.room:room-ktx`: Kotlin extensions for Room
- `androidx.room:room-compiler`: Annotation processor (KSP)

### Lifecycle
- `androidx.lifecycle:lifecycle-viewmodel-ktx`: ViewModel with Kotlin coroutines
- `androidx.lifecycle:lifecycle-livedata-ktx`: LiveData with Kotlin extensions
- `androidx.lifecycle:lifecycle-runtime-ktx`: Lifecycle runtime

### UI Components
- `androidx.recyclerview:recyclerview`: RecyclerView for lists

### Background Work
- `androidx.work:work-runtime-ktx`: WorkManager (for future enhancements)

## Permissions Required

### POST_NOTIFICATIONS
- **Purpose**: Display notifications to the user
- **Required**: Android 13 (API 33) and above
- **Request**: Automatically requested when needed

### SCHEDULE_EXACT_ALARM
- **Purpose**: Schedule alarms at exact times
- **Required**: Android 12 (API 31) and above
- **Request**: Special permission for exact alarms

### USE_EXACT_ALARM
- **Purpose**: Use exact alarm functionality
- **Required**: Android 12 (API 31) and above

## Database Schema

### Reminders Table
```kotlin
@Entity(tableName = "reminders")
data class ReminderModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,           // Auto-generated unique ID
    val title: String,          // Reminder title (required)
    val description: String,    // Reminder description (optional)
    val dateTime: Long          // Timestamp in milliseconds
)
```

**Indexes**: None (small dataset expected)
**Version**: 1

## Future Enhancements

Potential features to add:
- [ ] Recurring reminders (daily, weekly, monthly)
- [ ] Categories/tags for organizing reminders
- [ ] Search and filter functionality
- [ ] Multiple notification sounds
- [ ] Snooze functionality
- [ ] Dark theme support
- [ ] Backup and restore
- [ ] Widget for home screen
- [ ] Priority levels
- [ ] Reminder templates

## Performance Considerations

- All database operations run on background threads (coroutines)
- LiveData ensures UI updates only when activity is active
- RecyclerView efficiently displays large lists
- ViewBinding eliminates findViewById() overhead
- Room database is lightweight and fast
- Singleton pattern for database instance
