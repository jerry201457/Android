package com.jerry.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.math.BigDecimal;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getName();
    private Button mCompleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompleteBtn = findViewById(R.id.main_completeBtn);
        mCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initData();
                mCompleteBtn.setText(doubleAdd());
            }
        });

    }

    private String doubleAdd() {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(0.1));
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(0.2));
        return String.valueOf(bigDecimal.add(bigDecimal1));
//        double a=0.1f;
//        double b=0.2f;
//        return String.valueOf(a+b);
    }

    private void initData() {
        //Observable的创建
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //执行一些其他动作

                //执行完毕，触发回调，通知观察者
                e.onNext(longRunning());
            }
        });
        //Observer的创建
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                //观察者接收到通知,进行相关操作
                Log.i(TAG, "---onNext:" + s);
                mCompleteBtn.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        //订阅
        observable.subscribe(observer);
    }

    private String longRunning() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "complete";
    }
}
