# Recovered Android layout specification

Derived by decoding the compiled Android XML resources in the supplied Quick Draw 1.47 base APK. Measurements below are Android dp unless noted. This is a source-grounded layout map; Android themes and resource references can still affect final pixels.

## Main screen — `res/layout/activity_main.xml`

- Root: full-size vertical `LinearLayout`.
- First child: `include` of `res/layout/app_bar.xml`.
- Tool strip: horizontal `LinearLayout`, centered contents, dark background `#4c4d4e`, 1 dp padding. It has three equal-weight horizontal groups: **Brush**, **Eraser**, and **Background**. Labels are white and paired with an image icon.
- Drawing surface: `com.draw.rkb.drawsimply.DrawingView`, full width, height 0 dp with weight 1, white background. This is the flexible central region.
- Palette: a horizontal scroll view below the drawing surface. It contains 44 color image buttons, each 25 dp wide × 38 dp high, 2 dp margins, and a 5 dp bottom margin. The swatch drawable is `pallet`; selected swatches use `pallet_pressed`.
- The menu is provided by `res/menu/menu.xml`; the recovered handlers include New Drawing Sheet, Open, Save, About, More Apps, and Share.

## Toolbar — `res/layout/app_bar.xml`

- AppCompat toolbar with the original navigation/background icon reference.
- Original toolbar logo image is `res/drawable-mdpi-v4/toolbarlogo.png`, rendered at 25 × 25 dp with a 15 dp start margin in the included layout.

## Brush size dialog — `res/layout/dialog_brush_size.xml`

- Vertical layout with dark `#4c4d4e` background.
- Header text: “Choose Brush Size :”, 20 sp bold white; brush icon 45 × 45 dp; 10 dp vertical padding; 2 dp gray divider.
- Six full-width size options, top margins of 15 dp then 5 dp between items; heights 30, 40, 50, 55, 60, and 85 dp. The original drawable resources are `ultra_small`, `small`, `medium`, `large`, `ultra_large`, and `extreme_large`.

## Eraser size dialog — `res/layout/dialog_eraser_size.xml`

- Same dark dialog header style and 2 dp divider.
- Three eraser options in one horizontal centered row, using the original `medium_er`, `large_er`, and `ultra_large_er` drawable resources at 50, 70, and 80 dp square sizes.

## Background dialog — `res/layout/bgcolor_dialog.xml`

- Vertical dark layout with 20 dp padding.
- Header and background icon, followed by a horizontal scrollable palette on a near-white strip. Color buttons are 25 × 45 dp with 2 dp margins, matching the main palette's ordering.

## Other screens

- `activity_about.xml`: light gray-blue background `#e4e7ec`, toolbar `#c9ccd2`, blue accent `#0baae9`; title and descriptive text are centered, with “Update” and “Recommend App” buttons.
- `activity_help.xml`: same light background and toolbar; includes the original storage-permission help text and a settings button.
- `activity_splash_screen.xml`: dark `#4c4d4e` background; centered logo, “Draw & Paint” serif label, 220 dp square image, and footer text.

## Web reconstruction decisions

The web page now follows the recovered main-screen order: toolbar → three-control dark strip → flexible white canvas → horizontally scrolling palette. The browser version recreates Android dialogs and native actions with HTML dialogs, file input, PNG export, and browser sharing where supported. It cannot invoke Android storage permissions, Android intents, or run the APK bytecode. Undo/redo are browser-only extras.
