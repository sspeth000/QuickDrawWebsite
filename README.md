# Quick Draw — Draw & Paint 1.47 reconstruction

This repository has been restarted using the supplied **Quick Draw 1.47 XAPK** and **decompiler ZIP** as the reference. The live GitHub Pages app is a browser port of recovered behavior, not a raw APK running in Safari.

## Source artifacts inspected
- XAPK manifest: package `com.draw.rkb.drawsimply`, app name Quick Draw, version 1.47, minimum Android SDK 21, target SDK 34.
- Base APK: `com.draw.rkb.drawsimply.apk`; 751 ZIP entries, including compiled Android resources and original drawable assets.
- Decompiler archive: 5,650 entries; contains both `sources/` Java output and `smali/` output.
- Recovered application classes in the archive: `MainActivity.java`, `DrawingView.java`, `AboutActivity.java`, `HelpActivity.java`, `SplashScreenActivity.java`, `Places.java`, and generated `R.java`.

## Behavior verified in decompiled source
- The original `MainActivity` defines the 44 paint colors and has actions for brush-size selection, eraser-size selection, sheet background selection, New, Open, Save, About, Share, and More Apps.
- The original `DrawingView` draws anti-aliased rounded paths to a bitmap-backed canvas, switches to clear-mode for erasing, supports a reference image, and clears the sheet.
- The original app uses Android layouts, drawables, intents, and MediaStore. Those Android-specific pieces cannot run directly in GitHub Pages and must be adapted for a browser.

## GitHub Pages build
- [Open the live browser build](https://sspeth000.github.io/QuickDrawWebsite/)
- `index.html`: browser app shell
- `web/app.js`: Canvas drawing, the recovered 44-color palette, size presets, background chooser, open/save/share and dialogs
- `web/style.css`: responsive styling
- `assets/quick-draw-mark.svg`: newly recreated browser mark, not claimed to be the original APK icon

## Android source
- `app/` and the Gradle files are a separate reconstructed Android project retained in the repository.
- `android-recovered/` holds readable source recovered from the supplied decompiler archive where added. Decompiled source is not the original developer's full project and can contain decompiler artifacts.
- The complete uploaded decompiler ZIP and XAPK are the authoritative source artifacts; the current browser build does not yet mirror every APK binary asset or every decompiled file into this Git repository. That is an outstanding part of the reconstruction, not something this README claims is complete.

## Important limitations
A raw APK is not browser-executable. GitHub Pages can host the HTML/JavaScript port; it cannot launch Android bytecode in Safari. This reconstruction aims to reproduce app behavior and visuals in a browser while preserving Android source separately. Browser file picking and PNG download replace Android gallery intents and MediaStore saving.
