# Quick Draw Android reconstruction

This repository now contains an Android Studio project rather than the previous browser mock-up.

The supplied XAPK and decompiler archive were inspected. The decompiler archive contains about 2,825 decompiled Java files and 2,825 Smali files, but does not include the original Android resource tree or Gradle project. Therefore, an exact file-for-file restoration of the original project cannot be honestly claimed from those inputs alone.

This is a clean-room Android implementation of the main drawing workflow, based on observed behavior: freehand drawing, round brush strokes, eraser, undo, the 44-color palette, brush-size presets, sheet background selection, opening an image through Android's document picker, and PNG export.

## Open and build
Open this repository in Android Studio with JDK 17. Let Gradle sync, then run the app configuration on an Android device or emulator.

- Application ID: com.sspeth.quickdraw
- Minimum Android version: API 23
- Main UI and drawing implementation: app/src/main/java/com/sspeth/quickdraw/
- Manifest and resources: app/src/main/

## Source fidelity notes
- The original package observed in the supplied decompiled sources was com.draw.rkb.drawsimply; this reconstruction uses com.sspeth.quickdraw to distinguish it from the original app.
- The decompiled Java and Smali were not copied wholesale into this new project.
- Android Studio project and resource files that were absent from the supplied archive have been reconstructed rather than represented as recovered originals.
- This is not a byte-identical or file-for-file copy of the APK.
