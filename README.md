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
- `web/app.js`: Canvas drawing, the recovered 44-color palette, brush/eraser presets, background chooser, open/save/share, history, and dialogs
- `web/style.css`: Android-style screen proportions, dark tool strip, flexible white canvas, horizontal palette, dialog styling, and keyboard focus states
- `android-recovered/layouts/recovered-layout-spec.md`: decoded measurements and structure from the original compiled Android layouts
- `assets/original/brush-icon.svg`, `eraser-icon.svg`, and `background-icon.svg`: vector paths reconstructed from the original APK's compiled drawable resources
- Undo/redo buttons plus `Ctrl/⌘+Z`, `Ctrl/⌘+Shift+Z`, and `Ctrl/⌘+Y` shortcuts; history tracks strokes, background changes, new sheets, and opened images
- Eraser clears the editable canvas layer, including an opened image, matching the recovered Android `DrawingView` clear-mode behavior
- `assets/quick-draw-mark.svg`: newly recreated browser mark, not claimed to be the original APK icon

## Android source
- `app/` and the Gradle files are a separate reconstructed Android project retained in the repository.
- `android-recovered/` holds readable source recovered from the supplied decompiler archive where added. Decompiled source is not the original developer's full project and can contain decompiler artifacts.
- The complete uploaded decompiler ZIP and XAPK are the authoritative source artifacts; the current browser build does not yet mirror every APK binary asset or every decompiled file into this Git repository. That is an outstanding part of the reconstruction, not something this README claims is complete.

## Fidelity notes
- Confirmed from decompiled Java: rounded anti-aliased strokes, bitmap-backed drawing, clear-mode erasing, drawing an opened image onto the canvas, the 44-color palette, and the named menu actions.
- Recovered from compiled XML: the main screen's vertical order, dark three-control strip, flexible white drawing view, horizontal 44-color palette, palette swatch dimensions, dialog layout proportions, and the original tool vector paths. See `android-recovered/layouts/recovered-layout-spec.md`.
- Still approximated: platform-specific toolbar/menu rendering, Android density/font metrics, dialog chrome, and exact pixel-level behavior across devices. The web version is a close structural reconstruction, not a pixel-identical Android screenshot.
- Browser history and keyboard shortcuts are extra conveniences, not claims about the Android app.

## Important limitations
A raw APK is not browser-executable. GitHub Pages can host the HTML/JavaScript port; it cannot launch Android bytecode in Safari. This reconstruction aims to reproduce app behavior and visuals in a browser while preserving Android source separately. Browser file picking and PNG download replace Android gallery intents and MediaStore saving.
