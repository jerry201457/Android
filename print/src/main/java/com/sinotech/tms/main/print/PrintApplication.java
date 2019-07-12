package com.sinotech.tms.main.print;

import android.app.Application;

public class PrintApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        X.Ext.init(this);
    }
}
