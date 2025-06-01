
![ic_launcher_round](https://github.com/user-attachments/assets/3d1eb16d-4db5-4fbc-8dd2-9ee271ffed48)

# Sound Enhancer - Clearer & Louder Sound for Android

## Overview
Sound Enhancer is a lightweight Android app designed to improve your phone's audio experience by boosting volume and enhancing sound clarity. It is especially useful for devices with low sound output or less clear speakers.

---

## Features

- **Powerful Volume Boost:** Uses `LoudnessEnhancer` to increase overall sound volume beyond system defaults.
- **Audio Clarity Improvement:** Applies advanced audio effects to reduce background noise and make dialogs clearer.
- **Persistent Notification:** Shows a foreground notification while boosting is active, preventing accidental app closure.
- **Quick Settings Tile:** Easily toggle volume boost directly from the Quick Settings panel for instant control.
- **Lightweight:** Runs with a minimal footprint (~30 MB RAM usage), ensuring smooth performance on all devices.
- **Automatic Sync:** Notification and Quick Settings Tile states stay synchronized for seamless user experience.

---

## How It Works

The app uses Android’s native `LoudnessEnhancer` audio effect tied to the global audio session to amplify sound output. Additional audio processing can be applied using the `Equalizer` effect to improve voice clarity and reduce unwanted background noise.

By running as a foreground service, the app maintains a persistent notification that lets you toggle boosting without losing functionality when the system is under memory pressure. The Quick Settings tile offers fast toggling without opening the app.

---

## Usage

1. Launch the app or start the volume boost service.
2. Enable volume boosting via the app or the persistent notification toggle.
3. For quick access, pull down the notification shade and toggle boost on/off.
4. Use the Quick Settings Tile (swipe down twice from the top of the screen) to control boosting without opening the app.
5. When done, disable boosting from notification or Quick Settings tile.

---

## Why Use Volume Booster?

Many smartphones suffer from low audio output or unclear speaker sound, making videos and music less enjoyable. Volume Booster solves this by:

- Increasing maximum volume safely
- Enhancing audio clarity for voice/dialogs
- Offering easy controls without complex settings
- Maintaining performance with low resource use

---
## Building from Source

1. Clone the repository:  
   `git clone https://github.com/anuj-softech/SoundEnhancer.git`

2. Open in Android Studio.

3. Build and run on device/emulator (Android 9+ recommended).

---

## Download APK

Download the latest APK from the release section on GitHub repo to install directly without building.

---

## Technical Details

- Android `LoudnessEnhancer` for volume amplification
- Foreground Service with persistent notification
- Quick Settings Tile for convenient toggling
- Minimal background memory and CPU usage

---
