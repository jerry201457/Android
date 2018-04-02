package com.sinotech.report.main.draw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class LineActivity extends AppCompatActivity {
    private FrameLayout mlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        LinePathView linePathView=new LinePathView(this);
        mlayout = findViewById(R.id.line_linePathView);
        mlayout.addView(linePathView);
    }
}
