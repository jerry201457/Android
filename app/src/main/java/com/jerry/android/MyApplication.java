package com.jerry.android;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sinotech.tms.main.libcore.base.BaseApplication;
import com.sinotech.tms.main.libcore.utils.X;

/**
 * Created by Administrator on 2018/2/23.
 *
 * @author LWH
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private static boolean isDebug() {
        if (TextUtils.isEmpty(X.app().getPackageName())) {
            return false;
        }
        try {
            PackageManager pm = X.app().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(X.app().getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
