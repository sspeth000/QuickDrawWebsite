package com.draw.rkb.drawsimply;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;

/* Decompiled from the supplied classes.dex_Decompiler.com(1).zip. */
public class DrawingView extends View {
    private float brushSize;
    private Bitmap canvasBitmap;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Paint drawPaint;
    private Path drawPath;
    private boolean erase;
    private float lastBrushSize;
    private int paintColor;
    private int previousColor;

    public DrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paintColor = ViewCompat.MEASURED_STATE_MASK;
        this.previousColor = ViewCompat.MEASURED_STATE_MASK;
        this.erase = false;
        setUpDrawing();
    }

    private void setUpDrawing() {
        this.drawPath = new Path();
        Paint paint = new Paint();
        this.drawPaint = paint;
        paint.setColor(this.paintColor);
        this.drawPaint.setAntiAlias(true);
        this.drawPaint.setStyle(Paint.Style.STROKE);
        this.drawPaint.setStrokeJoin(Paint.Join.ROUND);
        this.drawPaint.setStrokeCap(Paint.Cap.ROUND);
        this.canvasPaint = new Paint(4);
        float integer = getResources().getInteger(R.integer.small_size);
        this.brushSize = integer;
        this.lastBrushSize = integer;
        this.drawPaint.setStrokeWidth(integer);
    }

    @Override
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.canvasBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.drawCanvas = new Canvas(this.canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.canvasBitmap, 0.0f, 0.0f, this.canvasPaint);
        canvas.drawPath(this.drawPath, this.drawPaint);
    }

    public void drawImage(Bitmap bitmap) {
        this.drawCanvas.drawBitmap(bitmap, 0.0f, 0.0f, this.canvasPaint);
        invalidate();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.drawPath.moveTo(x, y);
        } else if (action == 1) {
            if (this.erase) {
                this.drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
            this.drawCanvas.drawPath(this.drawPath, this.drawPaint);
            this.drawPath.reset();
            this.drawPaint.setXfermode(null);
        } else {
            if (action != 2) {
                return false;
            }
            this.drawPath.lineTo(x, y);
        }
        invalidate();
        return true;
    }

    public void setColor(String str) {
        invalidate();
        int color = Color.parseColor(str);
        this.paintColor = color;
        this.drawPaint.setColor(color);
        this.previousColor = this.paintColor;
    }

    public void setBrushSize(float f) {
        float fApplyDimension = TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
        this.brushSize = fApplyDimension;
        this.drawPaint.setStrokeWidth(fApplyDimension);
    }

    public void setLastBrushSize(float f) {
        this.lastBrushSize = f;
    }

    public float getLastBrushSize() {
        return this.lastBrushSize;
    }

    public void setErase(boolean z) {
        this.erase = z;
        if (z) {
            this.drawPaint.setColor(-1);
        } else {
            this.drawPaint.setColor(this.previousColor);
            this.drawPaint.setXfermode(null);
        }
    }

    public void startNew() {
        this.drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}