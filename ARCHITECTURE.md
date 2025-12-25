# Architecture Diagram

## App Architecture (MVVM Pattern)

```
┌─────────────────────────────────────────────────────────────┐
│                         UI LAYER                             │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────────┐          ┌──────────────────┐        │
│  │  MainActivity    │          │ AddReminderActivity│       │
│  │                  │          │                  │        │
│  │  - RecyclerView  │          │  - Input Forms   │        │
│  │  - FAB Button    │◄────────►│  - Date Picker   │        │
│  │  - Toolbar       │          │  - Save Button   │        │
│  └────────┬─────────┘          └────────┬─────────┘        │
│           │                              │                   │
│           │         Observes             │ Calls             │
│           │         LiveData             │ Methods           │
│           ▼                              ▼                   │
│  ┌──────────────────────────────────────────────┐           │
│  │         ReminderViewModel                     │           │
│  │                                               │           │
│  │  - allReminders: LiveData<List<Reminder>>   │           │
│  │  - insert(reminder)                          │           │
│  │  - update(reminder)                          │           │
│  │  - delete(reminder)                          │           │
│  └────────────────┬──────────────────────────────┘           │
│                   │                                          │
└───────────────────┼──────────────────────────────────────────┘
                    │
                    │ Uses Repository
                    ▼
┌─────────────────────────────────────────────────────────────┐
│                      DATA LAYER                              │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────────────────────────────────────┐           │
│  │         ReminderRepository                   │           │
│  │                                               │           │
│  │  - insert(reminder): Long                    │           │
│  │  - update(reminder)                          │           │
│  │  - delete(reminder)                          │           │
│  │  - allReminders: LiveData                    │           │
│  └────────────────┬──────────────────────────────┘           │
│                   │                                          │
│                   │ Uses DAO                                 │
│                   ▼                                          │
│  ┌──────────────────────────────────────────────┐           │
│  │         ReminderDao                          │           │
│  │                                               │           │
│  │  @Insert insertReminder(reminder)            │           │
│  │  @Update updateReminder(reminder)            │           │
│  │  @Delete deleteReminder(reminder)            │           │
│  │  @Query getAllReminders()                    │           │
│  └────────────────┬──────────────────────────────┘           │
│                   │                                          │
│                   │ Accesses Database                        │
│                   ▼                                          │
│  ┌──────────────────────────────────────────────┐           │
│  │         ReminderDatabase                     │           │
│  │         (Room Database)                      │           │
│  │                                               │           │
│  │  Table: reminders                            │           │
│  │  - id (PK, auto-increment)                   │           │
│  │  - title                                     │           │
│  │  - description                               │           │
│  │  - dateTime                                  │           │
│  └──────────────────────────────────────────────┘           │
│                                                              │
└──────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   NOTIFICATION SYSTEM                        │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────────┐          ┌──────────────────┐        │
│  │ NotificationUtils│          │  AlarmReceiver   │        │
│  │                  │          │                  │        │
│  │ - scheduleReminder() ──────►│ - onReceive()    │        │
│  │ - cancelReminder()           │ - showNotification()     │
│  │ - createChannel()            │                  │        │
│  └──────────────────┘          └──────────────────┘        │
│         │                                                    │
│         │ Uses                                               │
│         ▼                                                    │
│  ┌──────────────────┐                                       │
│  │  AlarmManager    │                                       │
│  │  (Android System)│                                       │
│  └──────────────────┘                                       │
│                                                              │
└──────────────────────────────────────────────────────────────┘

## Data Flow Examples

### Adding a New Reminder:
1. User fills form in AddReminderActivity
2. User taps "Save Reminder"
3. AddReminderActivity calls viewModel.insert()
4. ViewModel calls repository.insert()
5. Repository calls dao.insertReminder()
6. Room inserts into database and returns ID
7. NotificationUtils schedules alarm with AlarmManager
8. LiveData notifies observers
9. MainActivity RecyclerView updates automatically

### Notification Trigger:
1. AlarmManager fires at scheduled time
2. AlarmReceiver.onReceive() is called
3. AlarmReceiver creates and shows notification
4. User taps notification → Opens MainActivity

### Editing a Reminder:
1. User taps edit icon in MainActivity
2. AddReminderActivity opens with pre-filled data
3. User modifies and saves
4. Old alarm is canceled
5. New alarm is scheduled
6. Database is updated
7. UI refreshes via LiveData

## Component Relationships

```
ReminderAdapter ──► Uses ──► ReminderModel
MainActivity ──► Observes ──► ReminderViewModel
AddReminderActivity ──► Uses ──► ReminderViewModel
ReminderViewModel ──► Uses ──► ReminderRepository
ReminderRepository ──► Uses ──► ReminderDao
ReminderDao ──► Accesses ──► ReminderDatabase
NotificationUtils ──► Schedules ──► AlarmManager
AlarmManager ──► Triggers ──► AlarmReceiver
AlarmReceiver ──► Shows ──► Notification
```
