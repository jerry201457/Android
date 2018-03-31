package com.sinotech.report.main.draw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LineActivity extends AppCompatActivity {
    private LinePathView mLinePathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        mLinePathView = findViewById(R.id.line_linePathView);
    }
}
