package com.sinotech.tms.main.print;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.Toast;

public class ToastUtil {
    private static Toast mToast;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private final static String THREAD_MAIN = "main";

    /**
     * 显示toast(可以子线程中使用Toast)
     *
     * @param msg Toast消息
     */
    @SuppressLint("ShowToast")
    public static void showToast(final String msg) {
        try {
            // 判断是在子线程，还是主线程
            if (THREAD_MAIN.equals(Thread.currentThread().getName())) {
                if (mToast == null) {
                    mToast = Toast.makeText(X.app(), msg, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(msg);
                }
                mToast.show();
            } else {
                // 子线程
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(X.app(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
            //android API25系统BUG,TOAST异常
        }

    }
}
