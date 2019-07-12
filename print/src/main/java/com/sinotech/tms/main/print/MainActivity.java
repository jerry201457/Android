package com.sinotech.tms.main.print;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sinotech.tms.main.print.sdk.BlueUtil;
import com.sinotech.tms.main.print.sdk.CPCLPrint;
import com.tbruyelle.rxpermissions2.RxPermissions;


public class MainActivity extends AppCompatActivity {
    Button printBtn;
    RxPermissions rxPermissions = new RxPermissions(this);
//    private final String address = "00:00:05:DF:57:42";
    private final String address = "00:15:83:C9:65:84";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printBtn = findViewById(R.id.print_btn);
        initEvent();
    }

    private void initEvent() {
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print(address);
            }
        });

    }

    private void print(String address) {
        if(BlueUtil.checkPrinter(address)){
            CPCLPrint print=new CPCLPrint();
            print.connect(address);
            print.createPrintPage(70,50);
            print.printText(0,7,7,10,10,"测试测试");
            print.print();
//            print.close();
        }
    }


}
