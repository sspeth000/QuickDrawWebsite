# MainActivity.java — recovered behavior map

**Reference:** `sources/com/draw/rkb/drawsimply/MainActivity.java` in the uploaded `classes.dex_Decompiler.com(1).zip`.

This note is an indexed summary of the decompiled source, not a substitute for the complete original decompiler output. The full class is about 41 KB in the uploaded archive and has not yet been copied byte-for-byte into this repository.

## Recovered members and behavior
- Package: `com.draw.rkb.drawsimply`
- Drawing view: `R.id.canvas`
- Controls: `R.id.layoutBrush`, `R.id.layoutEraser`, `R.id.layoutBackground`, and `R.id.paint_colors`
- Brush size resources: `ultra_small_size`, `small_size`, `medium_size`, `large_size`, `ultra_large_size`, `extreme_large_size`
- Eraser size resources: `mediumEr_size`, `largeEr_size`, `ultra_largeEr_size`
- Menu handlers: `buttonNew1`, `buttonOpen`, `buttonSave1`, `buttonAbout`, `buttonMoreApps`, `buttonShare1`
- Color selection is handled by `paintClicked(View)`, which calls `DrawingView.setColor(...)`, disables erasing, and restores the last brush size.
- Brush and eraser choices use `dialog_brush_size` and `dialog_eraser_size`.
- Background selection opens `bgcolor_dialog` and applies the chosen color to the drawing view.
- New sheet asks for confirmation before calling `DrawingView.startNew()`.
- Save confirms before using Android `MediaStore.Images.Media.insertImage`.
- Open uses the Android image picker; share builds an Android share intent.
- About links to the Play Store listing; More Apps opens the developer's WordPress page.

The exact 44-color array recovered from this class is recorded in `palette.json`. The browser build adapts Android intents and MediaStore behavior to browser file selection and PNG download.
