# Quick Reference Guide

## Key Files and Their Purposes

### UI Layer (`app/src/main/java/com/example/reminderapp/ui/`)
- **MainActivity.kt** - Main screen showing list of reminders
- **AddReminderActivity.kt** - Screen for creating/editing reminders
- **ReminderAdapter.kt** - Manages RecyclerView items
- **ReminderViewModel.kt** - Handles UI-related data logic

### Data Layer (`app/src/main/java/com/example/reminderapp/database/`)
- **ReminderModel.kt** - Data class representing a reminder
- **ReminderDao.kt** - Database access methods
- **ReminderDatabase.kt** - Room database instance
- **ReminderRepository.kt** - Mediates between ViewModel and DAO

### Notification System
- **NotificationUtils.kt** (`utils/`) - Schedule and manage notifications
- **AlarmReceiver.kt** (`receiver/`) - Receives and handles alarms

### Resources (`app/src/main/res/`)
- **layout/activity_main.xml** - Main screen layout
- **layout/activity_add_reminder.xml** - Add/edit screen layout
- **layout/item_reminder.xml** - RecyclerView item layout

## Common Tasks

### Adding a New Field to Reminders

1. Update `ReminderModel.kt`:
```kotlin
data class ReminderModel(
    // ... existing fields ...
    val newField: String  // Add your new field
)
```

2. Update database version in `ReminderDatabase.kt`:
```kotlin
@Database(entities = [ReminderModel::class], version = 2)  // Increment version
```

3. Add migration strategy or allow destructive migration for development

4. Update UI to include the new field

### Changing Theme Colors

Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="purple_500">#FFYOURCOLOR</color>
```

### Adding New DAO Methods

In `ReminderDao.kt`:
```kotlin
@Query("SELECT * FROM reminders WHERE title LIKE :search")
suspend fun searchReminders(search: String): List<ReminderModel>
```

### Modifying Notification Sound

In `AlarmReceiver.kt`, update notification builder:
```kotlin
.setSound(yourSoundUri)
```

## Important Constants

### Notification
- Channel ID: `"reminder_channel"`
- Channel Name: `"Reminder Notifications"`

### Intent Extras
- `"REMINDER_ID"` - Reminder ID
- `"REMINDER_TITLE"` - Reminder title
- `"REMINDER_DESCRIPTION"` - Reminder description
- `"REMINDER_DATE_TIME"` - Reminder timestamp

### Database
- Database name: `"reminder_database"`
- Table name: `"reminders"`

## Permissions Explained

### POST_NOTIFICATIONS
Required for Android 13+ to display notifications. App will request this at runtime.

### SCHEDULE_EXACT_ALARM
Required for scheduling exact alarms. Some devices may restrict this.

### USE_EXACT_ALARM
Allows using exact alarm APIs for time-sensitive notifications.

## Testing Tips

### Unit Tests
Test ViewModel and Repository logic:
```kotlin
@Test
fun insertReminder_insertsIntoDatabase() {
    // Test implementation
}
```

### UI Tests
Test Activities with Espresso:
```kotlin
@Test
fun clickFab_opensAddActivity() {
    // Test implementation
}
```

### Database Tests
Test Room database operations:
```kotlin
@Test
fun writeAndReadReminder() {
    // Test implementation
}
```

## Common Issues and Solutions

### Gradle Sync Failed
- Clean project: Build → Clean Project
- Invalidate caches: File → Invalidate Caches / Restart

### Notifications Not Showing
- Check permissions in device settings
- Verify notification channel is created
- Ensure time is in the future

### Database Errors
- Version conflict: Update database version and add migration
- Schema mismatch: Clean and rebuild project

### Build Errors
- Update Android SDK if needed
- Sync Gradle files
- Check internet connection for dependency downloads

## Performance Tips

1. **Database Operations**: Always use coroutines for Room operations
2. **RecyclerView**: Use DiffUtil for efficient updates (future enhancement)
3. **LiveData**: Observe only in active lifecycle states
4. **Memory**: Avoid storing large objects in ViewModel

## Security Best Practices

1. **Input Validation**: Already implemented for title and date
2. **SQL Injection**: Room handles parameterized queries
3. **Sensitive Data**: Don't store sensitive info in reminders
4. **PendingIntents**: Use FLAG_IMMUTABLE (already implemented)

## Extending the App

### Add Categories
1. Create Category entity
2. Add foreign key in ReminderModel
3. Update DAO with category queries
4. Add category selection in UI

### Add Search
1. Add SearchView in MainActivity
2. Create search query in DAO
3. Filter LiveData based on search

### Add Dark Theme
1. Create night values directory
2. Add dark color scheme
3. Update theme configuration

## Build Variants

### Debug
```bash
./gradlew assembleDebug
```

### Release
```bash
./gradlew assembleRelease
```

## Useful Commands

```bash
# List all Gradle tasks
./gradlew tasks

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Generate APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug
```

## Code Style

- Follow Kotlin coding conventions
- Use meaningful variable names
- Keep functions small and focused
- Document complex logic with comments
- Use data classes for models

## Resources

- [Android Developers](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Material Design](https://material.io/design)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
