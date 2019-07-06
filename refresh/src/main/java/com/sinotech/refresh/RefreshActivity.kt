package com.sinotech.refresh

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.header.BezierRadarHeader

class RefreshActivity : AppCompatActivity() {
    private var refreshLayout: RefreshLayout? = null
    private var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh)
//        refreshLayout = findViewById<View>(R.id.refresh_layout)
        tv = findViewById(R.id.tv)
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout!!.setRefreshHeader(BezierRadarHeader(this).setEnableHorizontalDrag(true))
        //设置 Footer 为 球脉冲 样式
        refreshLayout!!.setRefreshFooter(BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale))
        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            tv!!.text = "刷新"
            refreshLayout.finishRefresh(2000)
        }
        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
            tv!!.text = "加载更多..."
            refreshLayout.finishLoadMore(2000)
        }
    }
}
