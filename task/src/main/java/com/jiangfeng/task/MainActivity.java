package com.jiangfeng.task;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity {
    private Button mStartBtn;
    private Button mStopBtn;
    private Button mCancelBtn;
    private final String TAG=MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartBtn = findViewById(R.id.main_start_btn);
        mStopBtn = findViewById(R.id.main_stop_btn);
        mCancelBtn = findViewById(R.id.main_cancel_btn);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.INTERNET,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                        Toast.makeText(getBaseContext(), "已授权", Toast.LENGTH_SHORT).show();
                    } else {
                        // At least one permission is denied
                        Toast.makeText(getBaseContext(), "未授权", Toast.LENGTH_SHORT).show();
                    }
                });

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doService();
                Intent intent = new Intent(getBaseContext(), MyJobService.class);
                startService(intent);
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(), MyJobService.class));
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void cancel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.cancelAll();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册监听屏幕的广播
        OnePixelReceiver mOnepxReceiver = new OnePixelReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(mOnepxReceiver, intentFilter);

    }

    private void doService() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
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
