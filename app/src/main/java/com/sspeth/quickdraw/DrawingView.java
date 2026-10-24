package com.sspeth.quickdraw;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public final class DrawingView extends View {
    private static final class Stroke {
        final Path path = new Path();
        final int color; final float width; final boolean erase;
        Stroke(int c,float w,boolean e){color=c;width=w;erase=e;}
    }
    private final List<Stroke> strokes=new ArrayList<>();
    private Stroke current;
    private int paintColor=Color.BLACK;
    private float brushWidth=7f;
    private boolean erasing;
    private Bitmap referenceBitmap;
    private final Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
    public DrawingView(Context c){super(c);init();}
    public DrawingView(Context c,AttributeSet a){super(c,a);init();}
    public DrawingView(Context c,AttributeSet a,int s){super(c,a,s);init();}
    private void init(){setLayerType(View.LAYER_TYPE_SOFTWARE,null);setBackgroundColor(Color.WHITE);}
    public void setPaintColor(int c){paintColor=c;if(erasing)setErasing(false);}
    public void setBrushWidth(float w){brushWidth=Math.max(1,w);}
    public void setErasing(boolean e){erasing=e;invalidate();}
    public void setSheetColor(int c){referenceBitmap=null;setBackgroundColor(c);invalidate();}
    public void setReferenceBitmap(Bitmap bitmap){referenceBitmap=bitmap;invalidate();}
    public void clearDrawing(){strokes.clear();current=null;referenceBitmap=null;invalidate();}
    public void undo(){if(!strokes.isEmpty())strokes.remove(strokes.size()-1);invalidate();}
    private void drawStroke(Canvas c,Stroke s){
        paint.reset();paint.setAntiAlias(true);paint.setDither(true);paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);paint.setStrokeCap(Paint.Cap.ROUND);paint.setStrokeWidth(s.width);
        paint.setColor(s.color);paint.setXfermode(s.erase?new PorterDuffXfermode(PorterDuff.Mode.CLEAR):null);
        c.drawPath(s.path,paint);paint.setXfermode(null);
    }
    private void drawReference(Canvas c,int w,int h){
        if(referenceBitmap==null||referenceBitmap.isRecycled())return;
        paint.reset();paint.setFilterBitmap(true);
        Rect src=new Rect(0,0,referenceBitmap.getWidth(),referenceBitmap.getHeight());
        Rect dst=new Rect(0,0,w,h);c.drawBitmap(referenceBitmap,src,dst,paint);
    }
    @Override protected void onDraw(Canvas c){
        super.onDraw(c);int save=c.saveLayer(0,0,getWidth(),getHeight(),null);
        drawReference(c,getWidth(),getHeight());
        for(Stroke s:strokes)drawStroke(c,s);if(current!=null)drawStroke(c,current);
        c.restoreToCount(save);
    }
    @Override public boolean onTouchEvent(MotionEvent e){
        float x=e.getX(),y=e.getY();
        switch(e.getActionMasked()){
            case MotionEvent.ACTION_DOWN:current=new Stroke(paintColor,brushWidth,erasing);current.path.moveTo(x,y);current.path.lineTo(x+.1f,y+.1f);invalidate();return true;
            case MotionEvent.ACTION_MOVE:if(current!=null){for(int i=0;i<e.getHistorySize();i++)current.path.lineTo(e.getHistoricalX(i),e.getHistoricalY(i));current.path.lineTo(x,y);invalidate();}return true;
            case MotionEvent.ACTION_UP:case MotionEvent.ACTION_CANCEL:if(current!=null){strokes.add(current);current=null;invalidate();}performClick();return true;
            default:return true;
        }
    }
    @Override public boolean performClick(){super.performClick();return true;}
    public Bitmap renderBitmap(int background){
        int w=Math.max(1,getWidth()),h=Math.max(1,getHeight());Bitmap b=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(b);c.drawColor(background);drawReference(c,w,h);
        int save=c.saveLayer(0,0,w,h,null);for(Stroke s:strokes)drawStroke(c,s);if(current!=null)drawStroke(c,current);c.restoreToCount(save);return b;
    }
}
