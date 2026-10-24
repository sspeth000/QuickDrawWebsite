# Recovered Android source notes

The files in this directory are based on the user's uploaded `classes.dex_Decompiler.com(1).zip`. The package name is preserved as `com.draw.rkb.drawsimply`. Decompiled files are useful reference material but are not the original developer's source project and may contain decompiler artifacts.

## Java source files copied into this repository
- `com/draw/rkb/drawsimply/DrawingView.java`
- `com/draw/rkb/drawsimply/AboutActivity.java`
- `com/draw/rkb/drawsimply/HelpActivity.java`
- `com/draw/rkb/drawsimply/SplashScreenActivity.java`
- `com/draw/rkb/drawsimply/Places.java`

The uploaded ZIP also contains `MainActivity.java`, `R.java`, and matching Smali files. The full decompiler ZIP is not yet mirrored into this repository; see the root README for that remaining work.

## Original resource names observed in the supplied base APK
- `res/layout/activity_main.xml`
- `res/layout/activity_about.xml`
- `res/layout/activity_help.xml`
- `res/layout/activity_splash_screen.xml`
- `res/layout/dialog_brush_size.xml`
- `res/layout/dialog_eraser_size.xml`
- `res/layout/bgcolor_dialog.xml`
- `res/drawable-mdpi-v4/toolbarlogo.png`
- `res/drawable-mdpi-v4/paintmain.png`
- `res/drawable-mdpi-v4/key.png`
- `res/drawable/new_file.png`
- `res/drawable/open_file.png`
- `res/drawable/save_file.png`

Five small original PNG files have been extracted into `assets/original/`. The APK's Android XML resources are compiled binary XML, so they require a resource decoder to become ordinary readable XML files.
