package com.sinotech.tms.main.print;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sinotech.tms.main.print.sdk.BlueUtil;
import com.sinotech.tms.main.print.sdk.CPCLPrint;
import com.tbruyelle.rxpermissions2.RxPermissions;


public class MainActivity extends AppCompatActivity {
    RxPermissions rxPermissions = new RxPermissions(this);
    private final String address_xt413 = "00:00:05:DF:57:42";
    private final String address_hanyin = "00:15:83:C9:65:84";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.hanyin_print_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            print(address_hanyin);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        findViewById(R.id.XT413_print_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            print(address_xt413);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    private void print(String address) throws InterruptedException {
        if (BlueUtil.checkPrinter(address)) {
            CPCLPrint print = new CPCLPrint();
            if (print.connect(address)) {
                Thread.sleep(4000);
                if (print.createPrintPage(70, 50)) {
                    Thread.sleep(2000);
                    print.printText(0, 7, 7, 10, 10, "测试测试1");
                    print.printText(0, 8, 7, 10, 20, "测试测试2");
                }
                Thread.sleep(2000);
                print.print();
                Thread.sleep(4000);
                print.close();
            }
        }
    }


}
