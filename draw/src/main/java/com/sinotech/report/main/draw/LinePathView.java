package com.sinotech.report.main.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LinePathView extends View {
    private float mX;
    private float mY;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private Canvas mCanvas;
    private Bitmap mBitmap;

    public LinePathView(Context context) {
        super(context);
        init(context);
    }

    public LinePathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置样式
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(18);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.GREEN);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重置路径
                mPath.reset();
                //记录轨迹
                touchDown(event);
                mCanvas.drawPath(mPath, mPaint);
                break;
            case MotionEvent.ACTION_UP:
                //把记录的轨迹画出
                mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
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
        mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);
        mCanvas.drawPath(mPath, mPaint);
    }

    /**
     * 更新画笔路径
     *
     * @param event 触摸事件
     */
    private void touchDown(MotionEvent event) {
        mX = event.getX();
        mY = event.getY();
        mPath.moveTo(mX, mY);
        mCanvas.drawPoint(mX,mY,mPaint);
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
}
