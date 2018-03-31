package com.sinotech.report.main.draw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sinotech.report.main.draw.view.HandWriteView;

import java.io.File;
import java.io.IOException;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {
    private HandWriteView mWriteView;
    private Button mSaveBtn;
    private Button mClearBtn;
    public static String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ls.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        mWriteView = findViewById(R.id.draw_handWriteView);
        mSaveBtn = findViewById(R.id.draw_saveBtn);
        mClearBtn = findViewById(R.id.draw_clearBtn);
        mSaveBtn.setOnClickListener(this);
        mClearBtn.setOnClickListener(this);
    }

    private void save(HandWriteView handWriteView) {
        if (handWriteView.isSign()) {

        } else {
            Toast.makeText(this, "您没有签名~", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.draw_saveBtn:
                try {
                    mWriteView.save(path1);
                    Intent intent=new Intent();
                    setResult(-1,intent);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.draw_clearBtn:
                Intent intent2 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
