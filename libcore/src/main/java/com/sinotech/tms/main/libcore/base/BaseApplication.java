package com.sinotech.tms.main.libcore.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.sinotech.tms.main.libcore.utils.X;

/**
 * Created by Administrator on 2018/2/23.
 *
 * @author LWH
 */

public class BaseApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        X.Ext.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
