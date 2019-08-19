package com.sinotech.report.main.draw;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Objects;

public class ScrollLayoutActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private LinearLayout layout1, layout2, layout3, layout4, layout5;
    private final String TAG = "ScrollLayoutActivity";
    private NestedScrollView scrollView;
    private String[] titles = new String[]{"基础信息", "账户信息", "权限", "照片上传", "保存"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_layout);
        tabLayout = findViewById(R.id.tablayout);
        scrollView = findViewById(R.id.scrollView);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText(titles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[1]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[2]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[3]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[4]));
        tabLayout.setSelectedTabIndicatorColor(Color.RED);
        tabLayout.setTabTextColors(Color.GRAY, Color.BLUE);

        initEvent();
    }


    private void initEvent() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int x, int y, int oldX, int oldY) {
                Rect rect = new Rect();
                scrollView.getHitRect(rect);
                boolean localVisibleRect1 = layout1.getLocalVisibleRect(rect);
                boolean localVisibleRect2 = layout2.getLocalVisibleRect(rect);
                boolean localVisibleRect3 = layout3.getLocalVisibleRect(rect);
                boolean localVisibleRect4 = layout4.getLocalVisibleRect(rect);
                boolean localVisibleRect5 = layout5.getLocalVisibleRect(rect);
                Log.i(TAG, "layout1=" + localVisibleRect1);
                Log.i(TAG, "layout2=" + localVisibleRect2);
                Log.i(TAG, "layout3=" + localVisibleRect3);
                Log.i(TAG, "layout4=" + localVisibleRect4);
                Log.i(TAG, "layout5=" + localVisibleRect5);

                LinearLayout childAt = (LinearLayout) scrollView.getChildAt(0);
                for(int position=0;position<childAt.getChildCount();position++){
                    if(childAt.getChildAt(position).getLocalVisibleRect(rect)){
                        Objects.requireNonNull(tabLayout.getTabAt(position)).select();
                        break;
                    }
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                LinearLayout childAt = (LinearLayout) scrollView.getChildAt(0);
//                for(int position=0;position<tab.getPosition();position++){
//                    scrollView.scrollTo(childAt.getChildAt(position).getLeft(),childAt.getChildAt(position).getTop());
//                }

                switch (tab.getPosition()) {
                    case 0:
                        scrollView.scrollTo(layout1.getLeft(), layout1.getTop());
                        break;
                    case 1:
                        scrollView.scrollTo(layout2.getLeft(), layout2.getTop());
                        break;
                    case 2:
                        scrollView.scrollTo(layout3.getLeft(), layout3.getTop());
                        break;
                    case 3:
                        scrollView.scrollTo(layout4.getLeft(), layout4.getTop());
                        break;
                    case 4:
                        scrollView.scrollTo(layout5.getLeft(), layout5.getTop());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
