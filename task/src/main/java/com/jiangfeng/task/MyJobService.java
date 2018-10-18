package com.jiangfeng.task;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private final String TAG = MyJobService.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "---onCreate  ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"---onStartCommand");
        doService();
        return START_STICKY;
    }

    int count = 0;

    @Override
    public boolean onStartJob(final JobParameters params) {

        Log.i(TAG, "---onStartJob run count="+count);
        count++;
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.i(TAG, "---onStartJob run count="+count);
//                Toast.makeText(getApplicationContext(),"onStartJob run",Toast.LENGTH_SHORT).show();
//                count++;
//                if (count >= 10) {
//                    //任务执行完毕调用终止
//                    jobFinished(params, false);
//                    Log.i(TAG,"---onStartJob jobFinished");
//                }
//            }
//        }, TimeUnit.SECONDS.toSeconds(5));
        //如果返回false的话，系统会自动结束本job；
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "---onStopJob  ");
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "---onDestroy  ");
    }



    // 服务是否运行
    public boolean isServiceRunning(String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        // 获取运行服务再启动
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            System.out.println(info.processName);
            if (info.processName.equals(serviceName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    private void doService() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            return;
//        }
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this, MyJobService.class));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            //最小延迟
            builder.setMinimumLatency(3000);
            //最长延迟
            builder.setOverrideDeadline(5000);
            //重试方案,线性方案||指数方案
            builder.setBackoffCriteria(10000, JobInfo.BACKOFF_POLICY_LINEAR);
            //设置首次执行前最大延迟时间
            builder.setTriggerContentMaxDelay(10000);
        } else {
            //周期间隔时间
            builder.setPeriodic(3000);
        }
        //是否充电状态下执行
        builder.setRequiresCharging(true);
        //是否重启后继续后台操作
        builder.setPersisted(true);
        //执行时网络状态
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //设置手机是否空闲的条件
//        builder.setRequiresDeviceIdle(false);
        JobInfo jobInfo = builder.build();
        int schedule = jobScheduler.schedule(jobInfo);
        if (schedule <= 0) {
            Log.i(TAG, "---执行失败,schedule=" + schedule);
            Toast.makeText(getApplicationContext(), "执行失败", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "---执行成功,schedule=" + schedule);
            Toast.makeText(getApplicationContext(), "执行成功", Toast.LENGTH_SHORT).show();
        }
    }
}
