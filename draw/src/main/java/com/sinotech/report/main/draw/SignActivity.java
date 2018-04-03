package com.sinotech.report.main.draw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sinotech.report.main.draw.view.LinePathView;

import java.io.File;

public class SignActivity extends AppCompatActivity implements View.OnClickListener {
    private LinePathView mPathView;
    private Button mSaveBtn;
    private Button mClearBtn;
    public final static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "linePathView.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mPathView = findViewById(R.id.sign_linePathView);
        mSaveBtn = findViewById(R.id.sign_saveBtn);
        mClearBtn = findViewById(R.id.sign_clearBtn);
        mSaveBtn.setOnClickListener(this);
        mClearBtn.setOnClickListener(this);
        mPathView.delete(PATH);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_saveBtn:
                mPathView.save(PATH);
                setResult(100);
                finish();
                break;
            case R.id.sign_clearBtn:
                mPathView.clear();
                break;
            default:
                break;
        }
    }
}
