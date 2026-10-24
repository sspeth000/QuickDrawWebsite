package com.sspeth.quickdraw;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.*;
import java.io.OutputStream;

public final class MainActivity extends Activity {
    private static final int OPEN=10,SAVE=11;
    private static final int[] COLORS={0xFF000000,0xFF2D2F30,0xFF5B5C5E,0xFF999A9C,0xFFC8CDD2,0xFFFFFFFF,0xFF642C00,0xFF7B4F2D,0xFFAB8669,0xFFFF0000,0xFF9C2222,0xFFEF5454,0xFFD49090,0xFF00FF00,0xFF447444,0xFF41A141,0xFF56D956,0xFF9BE29B,0xFF0000FF,0xFF40507C,0xFF488CB3,0xFF6565D7,0xFFB2B2E1,0xFFFF00D5,0xFFE678D3,0xFF943484,0xFFFF7B00,0xFFFFA048,0xFFE9B685,0xFF00FFF6,0xFF1BA39C,0xFF5BCAC7,0xFF92D4D2,0xFFB300FF,0xFF80399E,0xFFC865F2,0xFFD19EE9,0xFFFFFF00,0xFF94941A,0xFFE7E73E,0xFFF7F79A,0xFFFFB569,0xFFFFCC98,0xFFE6BE96};
    private final int[] brushSizes={2,4,7,12,20,32},eraserSizes={7,12,20};
    private final String[] brushNames={"Ultra-small","Small","Medium","Large","Ultra-large","Extreme-large"},eraserNames={"Medium","Large","Ultra-large"};
    private DrawingView drawing; private int sheetColor=Color.WHITE,selectedColor=Color.BLACK,selectedSize=7; private boolean erasing;
    private int dp(float n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
    private Button btn(String label,Runnable run){Button b=new Button(this);b.setText(label);b.setAllCaps(false);b.setOnClickListener(v->run.run());return b;}
    private TextView label(String s){TextView t=new TextView(this);t.setText(s);t.setTextColor(0xFF303030);t.setPadding(dp(10),dp(3),dp(10),dp(3));return t;}
    @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(0xFF303030);build();drawing.setPaintColor(selectedColor);drawing.setBrushWidth(selectedSize);}
    private void build(){
        LinearLayout root=new LinearLayout(this);root.setOrientation(1);root.setBackgroundColor(0xFFF4F4F4);
        LinearLayout bar=new LinearLayout(this);bar.setGravity(Gravity.CENTER_VERTICAL);bar.setBackgroundColor(0xFF303030);
        TextView title=label("✎ Quick Draw");title.setTextColor(Color.WHITE);title.setTextSize(20);bar.addView(title,new LinearLayout.LayoutParams(0,dp(52),1));
        bar.addView(btn("New",this::newSheet));bar.addView(btn("Open",this::openImage));bar.addView(btn("Save",this::saveImage));root.addView(bar);
        LinearLayout tools=new LinearLayout(this);tools.addView(btn("Brush",()->setEraser(false)),new LinearLayout.LayoutParams(0,dp(45),1));tools.addView(btn("Eraser",()->setEraser(true)),new LinearLayout.LayoutParams(0,dp(45),1));tools.addView(btn("Undo",()->drawing.undo()),new LinearLayout.LayoutParams(0,dp(45),1));root.addView(tools);
        root.addView(label("Brush / eraser size"));
        HorizontalScrollView sizesScroll=new HorizontalScrollView(this);LinearLayout sizes=new LinearLayout(this);
        for(int i=0;i<brushNames.length;i++){final int ix=i;sizes.addView(btn(brushNames[i],()->chooseSize(ix)));}
        sizesScroll.addView(sizes);root.addView(sizesScroll);
        root.addView(label("Paint colors (44 colors)"));
        HorizontalScrollView paletteScroll=new HorizontalScrollView(this);LinearLayout palette=new LinearLayout(this);
        for(int color:COLORS){Button swatch=new Button(this);swatch.setText(" ");swatch.setMinWidth(dp(38));swatch.setBackgroundTintList(android.content.res.ColorStateList.valueOf(color));swatch.setOnClickListener(v->{selectedColor=color;drawing.setPaintColor(color);setEraser(false);});palette.addView(swatch,new LinearLayout.LayoutParams(dp(43),dp(42)));}
        paletteScroll.addView(palette);root.addView(paletteScroll);
        root.addView(btn("Sheet background",this::chooseBackground));
        drawing=new DrawingView(this);root.addView(drawing,new LinearLayout.LayoutParams(-1,0,1));setContentView(root);
    }
    private void setEraser(boolean enabled){erasing=enabled;drawing.setErasing(enabled);if(enabled){selectedSize=eraserSizes[0];}else{selectedSize=7;drawing.setPaintColor(selectedColor);}drawing.setBrushWidth(selectedSize);}
    private void chooseSize(int i){if(erasing){i=Math.min(i,eraserSizes.length-1);selectedSize=eraserSizes[i];}else selectedSize=brushSizes[i];drawing.setBrushWidth(selectedSize);}
    private void newSheet(){new AlertDialog.Builder(this).setTitle("New Drawing Sheet").setMessage("Do you want to start new?").setNegativeButton("Cancel",null).setPositiveButton("Yes",(d,w)->drawing.clearDrawing()).show();}
    private void chooseBackground(){String[] names=new String[COLORS.length];for(int i=0;i<COLORS.length;i++)names[i]=String.format("#%06X",COLORS[i]&0xffffff);new AlertDialog.Builder(this).setTitle("Background color").setItems(names,(d,i)->{sheetColor=COLORS[i];drawing.setSheetColor(sheetColor);}).setNegativeButton("Cancel",null).show();}
    private void openImage(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("image/*");i.addCategory(Intent.CATEGORY_OPENABLE);startActivityForResult(i,OPEN);}
    private void saveImage(){Intent i=new Intent(Intent.ACTION_CREATE_DOCUMENT);i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("image/png");i.putExtra(Intent.EXTRA_TITLE,"QuickDraw.png");startActivityForResult(i,SAVE);}
    @Override protected void onActivityResult(int req,int result,Intent data){super.onActivityResult(req,result,data);if(result!=RESULT_OK||data==null||data.getData()==null)return;Uri uri=data.getData();try{if(req==SAVE){Bitmap b=drawing.renderBitmap(sheetColor);try(OutputStream o=getContentResolver().openOutputStream(uri)){if(o!=null)b.compress(Bitmap.CompressFormat.PNG,100,o);}b.recycle();Toast.makeText(this,"Drawing saved",Toast.LENGTH_SHORT).show();}else if(req==OPEN){Bitmap b;if(android.os.Build.VERSION.SDK_INT>=28){android.graphics.ImageDecoder.Source source=android.graphics.ImageDecoder.createSource(getContentResolver(),uri);b=android.graphics.ImageDecoder.decodeBitmap(source);}else{b=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);}drawing.setReferenceBitmap(b);Toast.makeText(this,"Image opened",Toast.LENGTH_SHORT).show();}}catch(Exception e){Toast.makeText(this,"Unable to process image",Toast.LENGTH_LONG).show();}}
}
