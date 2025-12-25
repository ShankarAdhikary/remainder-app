# Building and Running the Reminder App

## Prerequisites

- Android Studio Arctic Fox or newer
- Android SDK 24 or higher
- Kotlin 1.9.0 or higher

## Build Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/ShankarAdhikary/remainder-app.git
   cd remainder-app
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Navigate to the cloned repository and select it
   - Wait for Gradle sync to complete

3. **Build the project**
   - Click "Build" → "Make Project" or press `Ctrl+F9` (Windows/Linux) or `Cmd+F9` (Mac)
   - Wait for the build to complete

4. **Run the app**
   - Connect an Android device via USB or start an Android emulator
   - Click "Run" → "Run 'app'" or press `Shift+F10` (Windows/Linux) or `Ctrl+R` (Mac)
   - Select your device/emulator from the list

## Using the App

### Adding a Reminder
1. Tap the floating action button (+) on the main screen
2. Enter a title for your reminder
3. Enter a description (optional)
4. Tap "Select Date & Time" to choose when you want to be reminded
5. Tap "Save Reminder"

### Editing a Reminder
1. On the main screen, tap the edit icon on the reminder you want to modify
2. Update the details as needed
3. Tap "Update Reminder"

### Deleting a Reminder
1. On the main screen, tap the delete icon on the reminder you want to remove
2. Confirm the deletion in the dialog

## Permissions

The app will request the following permissions when needed:
- **Notifications**: Required to show reminder notifications (Android 13+)
- **Exact Alarms**: Required to schedule reminders at precise times

## Troubleshooting

### Gradle Sync Failed
- Ensure you have a stable internet connection
- Try "File" → "Invalidate Caches / Restart"
- Check that you have the required Android SDK versions installed

### App doesn't show notifications
- Check that notification permissions are granted in device settings
- Ensure the reminder time is set in the future
- Check that battery optimization is not restricting the app

### Build errors
- Clean the project: "Build" → "Clean Project"
- Rebuild: "Build" → "Rebuild Project"
- Update Gradle if prompted

## Project Structure

```
ReminderApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/reminderapp/
│   │   │   ├── database/       # Room database components
│   │   │   ├── model/          # Data models
│   │   │   ├── receiver/       # Broadcast receivers
│   │   │   ├── ui/             # Activities and adapters
│   │   │   └── utils/          # Utility classes
│   │   └── res/                # Resources (layouts, drawables, etc.)
│   └── build.gradle.kts        # App-level build configuration
├── build.gradle.kts            # Project-level build configuration
└── settings.gradle.kts         # Gradle settings
```

## Testing

The app stores reminders locally using Room database, so your data persists across app restarts. Notifications are scheduled using Android's AlarmManager for reliable delivery.

## Notes

- Reminders must be set for a future date/time
- The app uses Material Design 3 components for a modern look
- All database operations are performed asynchronously to maintain smooth UI performance
