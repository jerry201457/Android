package com.sinotech.report.main.draw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sinotech.report.main.draw.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LinePathView extends View {
    private final String TAG = LinePathView.class.getName();
    private float mX;
    private float mY;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int mPaintColor;
    private float mPaintWidth;

    public LinePathView(Context context) {
        super(context);
        init(context, null);
    }

    public LinePathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LinePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinePathView);
        mPaintWidth = typedArray.getDimensionPixelSize(R.styleable.LinePathView_paintWidth, 20);
        mPaintColor = typedArray.getColor(R.styleable.LinePathView_paintColor, Color.RED);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置样式
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setTextSize(25f);
        mBitmap = Bitmap.createBitmap(1000, 2000, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Log.i(TAG, "---width:" + getWidth() + "---height:" + getHeight());
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
        if (mBitmap != null) {
            mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                //把记录的轨迹画出
                mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 更新画笔路径
     *
     * @param event 触摸事件
     */
    private void touchDown(MotionEvent event) {
        //重置路径
        mPath.reset();
        mX = event.getX();
        mY = event.getY();
        mPath.moveTo(mX, mY);
    }

    /**
     * 手势移动过程更新路径
     *
     * @param event 触摸事件
     */
    private void touchMove(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        final float startX = mX;
        final float startY = mY;
        //移动距离取绝对值
        final float dx = Math.abs(x - startX);
        final float dy = Math.abs(y - startY);
        if (dx > 3 || dy > 3) {
            //起点和终点的一半
            mPath.quadTo(startX, startY, (x + startX) / 2, (y + startY) / 2);
            mX = x;
            mY = y;
        }
    }

    /**
     * 把画布保存到路径下的文件
     *
     * @param path 指定路径
     */
    public void save(String path) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] buffer = bos.toByteArray();
        if (buffer != null) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write(buffer);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除画布
     */
    public void clear() {
        if (mCanvas != null) {
            mPaint.setColor(mPaintColor);
            mPaint.setStrokeWidth(mPaintWidth);
            mCanvas.drawColor(Color.WHITE);
            invalidate();
        }
    }


}
