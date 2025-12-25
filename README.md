# Reminder App

An Android application built with Kotlin that helps users manage their events effectively with notification reminders.

## Features

- **Add Reminders**: Create reminders with title, description, and scheduled time
- **Edit & Delete Reminders**: Manage existing reminders easily
- **Notification Reminders**: Receive push notifications when reminders are due
- **Simple UI**: Clean and minimalistic design using Material Design components

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Persistence Library
- **UI Components**: Material Design Components
- **Notifications**: Android AlarmManager and NotificationManager
- **Lifecycle**: AndroidX Lifecycle (ViewModel, LiveData)

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/reminderapp/
│   │   ├── database/
│   │   │   ├── ReminderDao.kt
│   │   │   ├── ReminderDatabase.kt
│   │   │   └── ReminderRepository.kt
│   │   ├── model/
│   │   │   └── ReminderModel.kt
│   │   ├── receiver/
│   │   │   └── AlarmReceiver.kt
│   │   ├── ui/
│   │   │   ├── AddReminderActivity.kt
│   │   │   ├── MainActivity.kt
│   │   │   ├── ReminderAdapter.kt
│   │   │   └── ReminderViewModel.kt
│   │   └── utils/
│   │       └── NotificationUtils.kt
│   └── res/
│       ├── layout/
│       │   ├── activity_main.xml
│       │   ├── activity_add_reminder.xml
│       │   └── item_reminder.xml
│       └── values/
│           ├── colors.xml
│           ├── strings.xml
│           └── themes.xml
```

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device (API 24+)

## Permissions

The app requires the following permissions:
- `POST_NOTIFICATIONS`: To display notifications
- `SCHEDULE_EXACT_ALARM`: To schedule exact alarms for reminders
- `USE_EXACT_ALARM`: To use exact alarm functionality

## Requirements

- Android SDK 24 or higher
- Android Studio Arctic Fox or newer
- Kotlin 1.9.0 or higher
