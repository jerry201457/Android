package com.sinotech.report.main.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LinePathView extends View {
    private final String TAG = LinePathView.class.getName();
    private float mX;
    private float mY;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private List<Path> mPathList = new ArrayList<>();

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
        mPath = new Path();
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置样式
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(18);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setTextSize(25f);
        mBitmap = Bitmap.createBitmap(1000, 2000, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
        testDraw();


    }

    private void testDraw() {
        if (mPaint == null) {
            Log.i(TAG, "---paint is null");
        }
        mPath.lineTo(100, 200);
        mPath.moveTo(200, 300);
        mPath.lineTo(300, 400);
        mPath.addCircle(300, 300, 60, Path.Direction.CCW);
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Log.i(TAG, "---width:" + getWidth() + "---height:" + getHeight());
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "---x:" + event.getX() + "---y:" + event.getX());
                //重置路径
//                mPath.reset();
                //记录轨迹
//                touchDown(event);
                mX = x;
                mY = y;
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "---x:" + event.getX() + "---y:" + event.getX());
//                touchMove(event);
                mPath.lineTo(x, y);
                mX = x;
                mY = y;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "---x:" + event.getX() + "---y:" + event.getX());
                //把记录的轨迹画出
                mPath.lineTo(x, y);
//                mPath.reset();
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
//        mCanvas.drawPath(mPath, mPaint);
        mCanvas.drawBitmap(mBitmap, 0, 0, null);
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
        Log.i(TAG, "---x:" + mX + "---y:" + mY);
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
        Log.i(TAG, "---x:" + mX + "---y:" + mY);
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
