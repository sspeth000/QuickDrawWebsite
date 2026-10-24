# Quick Draw — Android project reconstruction

The previous browser-only mock-up has been removed. This repository is now structured as an Android Studio project.

## Current status
This is a **reconstructed Android implementation**, not a byte-identical restoration of the original APK. The provided decompiler archive includes decompiled Java and Smali, but not the complete original Android Studio project, original Gradle configuration, or complete resource tree. Recovered behavior and reconstructed code must not be mistaken for original source files.

## Implemented
- Freehand drawing with anti-aliased, round strokes
- Brush and eraser modes with size presets
- The 44-color palette identified from the decompiled app
- Sheet background selection and new-sheet confirmation
- Open an image from the Android document picker as a drawing reference
- Undo and PNG export through Android's document picker

## Project
- `settings.gradle`, `build.gradle`, `gradle.properties`
- `app/build.gradle`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/sspeth/quickdraw/MainActivity.java`
- `app/src/main/java/com/sspeth/quickdraw/DrawingView.java`
- `app/src/main/res/values/`

## Build
Open the repository in Android Studio with JDK 17 and allow Gradle to sync. Run the `app` configuration on an Android device or emulator. This project has not yet been verified by a successful Android build in this environment.

## Original app details
The decompiled package name is `com.draw.rkb.drawsimply`. This reconstruction uses `com.sspeth.quickdraw` so it is clearly distinguished from the original app. A full source/resource restoration still requires bringing the original decompiled classes and extracted APK resources into the repository and resolving decompiler/build issues.