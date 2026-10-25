# Recovered Android layout specification

Derived by decoding the compiled Android XML resources in the supplied Quick Draw 1.47 base APK. Measurements below are Android dp unless noted. This is a source-grounded layout map; Android themes and resource references can still affect final pixels.

## Main screen — `res/layout/activity_main.xml`

- Root: full-size vertical `LinearLayout`.
- First child: `include` of `res/layout/app_bar.xml`.
- Tool strip: horizontal `LinearLayout`, centered contents, dark background `#4c4d4e`, 1 dp parent padding, and `wrap_content` height. It has three equal-weight horizontal groups: **Brush**, **Eraser**, and **Background**. Each group centers its white label and 24 dp image icon; the label has a 2 dp end margin.
- Drawing surface: `com.draw.rkb.drawsimply.DrawingView`, full width, height 0 dp with weight 1, white background. This is the flexible central region.
- Palette: a horizontal scroll view below the drawing surface, with no scrollbars. Its inner horizontal layout is `wrap_content` height and has a 5 dp bottom margin. It contains 44 color image buttons, each 25 dp wide × 38 dp high, with 2 dp margins. `pallet.xml` overlays thin transparent strokes and 10 dp rounded corners; `pallet_pressed.xml` adds an approximately 1.25 dp dark selection outline. The actual color is supplied by the button background.
- The menu is provided by `res/menu/menu.xml`; the recovered handlers include New Drawing Sheet, Open, Save, About, More Apps, and Share.

## Toolbar — `res/layout/app_bar.xml`

- AppCompat toolbar with the original palette icon as its navigation icon. The toolbar's layout height is `wrap_content` and the AppCompat theme supplies the action-bar minimum height.
- Original toolbar logo image is `res/drawable-mdpi-v4/toolbarlogo.png`, rendered at 25 × 25 dp with a 15 dp start margin in the included layout.

## Brush size dialog — `res/layout/dialog_brush_size.xml`

- Vertical layout with dark `#4c4d4e` background.
- Header text: “Choose Brush Size :”, 20 sp bold white; brush icon 45 × 45 dp; 10 dp vertical padding; 2 dp gray divider.
- Six full-width ImageButtons, top margins of 15 dp then 5 dp between items, and a 10 dp bottom margin on the last option. Button heights are 30, 40, 50, 55, 60, and 85 dp. Their white shape drawables have intrinsic heights 5, 10, 20, 30, 40, and 100 dp, respectively, each with a 100 dp intrinsic width; this distinction matters when reproducing Android ImageView scaling.

## Eraser size dialog — `res/layout/dialog_eraser_size.xml`

- Same dark dialog header style and 2 dp divider.
- Three eraser options in one horizontal centered row, using the original `medium_er`, `large_er`, and `ultra_large_er` drawable resources. The ImageButtons are 50, 70, and 80 dp square; their white shape drawables have intrinsic sizes 16, 35, and 50 dp square.

## Background dialog — `res/layout/bgcolor_dialog.xml`

- Vertical dark layout with 20 dp padding.
- Header and background icon, followed by a horizontal scrollable palette on a near-white strip. Color buttons are 25 × 45 dp with 2 dp margins, matching the main palette's ordering.

## Other screens

- `activity_about.xml`: light gray-blue background `#e4e7ec`, toolbar `#c9ccd2`, blue accent `#0baae9`; title and descriptive text are centered, with “Update” and “Recommend App” buttons.
- `activity_help.xml`: same light background and toolbar; includes the original storage-permission help text and a settings button.
- `activity_splash_screen.xml`: dark `#4c4d4e` background; centered logo, “Draw & Paint” serif label, 220 dp square image, and footer text.

## Web reconstruction decisions

- The CSS has been updated against these decoded values: the tool strip now wraps its icon/text content, the main palette uses the XML's exact swatch dimensions/margins and thin selection border, brush preview shapes use their recovered intrinsic sizes, and eraser buttons use their recovered square dimensions.

The web page now follows the recovered main-screen order: toolbar → three-control dark strip → flexible white canvas → horizontally scrolling palette. The browser version recreates Android dialogs and native actions with HTML dialogs, file input, PNG export, and browser sharing where supported. It cannot invoke Android storage permissions, Android intents, or run the APK bytecode. Undo/redo are browser-only extras.
