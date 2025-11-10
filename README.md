# Caffeine Tracker ☕📉

[![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue?logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Compose-2024.10.00-purple)](https://developer.android.com/compose)
[![Room](https://img.shields.io/badge/Room-2.6.1-orange)](https://developer.android.com/training/data-storage/room)

**A clean, modern Android app to track my caffeine intake with a real-time decay graph.**  
No more guessing how much coffee is still in my system at 3 AM.

## Why I made this
I drink way too much coffee. I wanted to see exactly how long that triple espresso stays in my blood.  
So I built this: log your caffeine, watch it decay over time (5-hour half-life), and never crash again.

## Features
- **Real-time decay graph** using MPAndroidChart (shows ±12h from now)
- **Material 3** design with dark mode support
- **Room database** (persists logs even after closing)
- **Jetpack Compose + MVVM** (clean, testable code)
- **Presets**: 25mg, 50mg, 100mg buttons
- **Date & time picker** (default: now)
- **Empty state** with clean graph + message

## Screenshots
<img src="screenshots/empty.png" width="300"/> <img src="screenshots/filled.png" width="300"/>

*(I'll add real screenshots later – just run the app, it's gorgeous)*

## Tech Stack
- **Language**: Kotlin 2.0.21
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM + Repository pattern
- **Database**: Room with Flow
- **Graph**: MPAndroidChart (v3.1.0) via AndroidView
- **No Hilt/Dagger** – kept it simple, no debugging hell

## How to run
```bash
git clone https://github.com/norman-glad/CaffeineTracker.git
cd CaffeineTracker
./gradlew build
