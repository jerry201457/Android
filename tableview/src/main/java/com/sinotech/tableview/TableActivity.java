package com.sinotech.tableview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.sequence.BaseSequenceFormat;
import com.bin.david.form.data.format.tip.MultiLineBubbleTip;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.PageTableData;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {
    private SmartTable<Form> smartTable;
    //    private RefreshLayout mRefreshLayout;
    private ImageView lastIv;
    private ImageView nextIv;
    private TextView currentPageTv;
    /**
     * 本地当前总条数
     */
    private int mTotalLocal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        smartTable = findViewById(R.id.table);
//        mRefreshLayout = findViewById(R.id.table_refreshLayout);
        lastIv = findViewById(R.id.last_iv);
        nextIv = findViewById(R.id.next_iv);
        currentPageTv = findViewById(R.id.currentPage_tv);
        //设置 Header 为 贝塞尔雷达 样式
//        mRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//        //设置 Footer 为 球脉冲 样式
//        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        try {
            initTable(smartTable, getList(22));
//            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//                @Override
//                public void onRefresh(RefreshLayout refreshLayout) {
//                    initTable(smartTable, getList(22));
//                    refreshLayout.finishRefresh();
//                }
//            });
//            mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//                @Override
//                public void onLoadMore(RefreshLayout refreshLayout) {
//                    smartTable.addData(getList(3), true);
//                    refreshLayout.finishLoadMore(1000);
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initTable(final SmartTable smartTable, final List<Form> formList) {
        final Column<String> columnBillDeptName = new Column<String>("开票部门", "column1");
        Column<String> columnOrderCount = new Column<String>("运单数量", "column2");
        Column<String> columnItemQty = new Column<String>("件数", "column3");
        Column<String> column4 = new Column<String>("代收款", "column4");
        Column<String> column5 = new Column<String>("提付运费", "column5");
        Column<String> column6 = new Column<String>("现付运费", "column6");
        Column<String> column7 = new Column<String>("现付月结运费", "column7");
        Column<String> column8 = new Column<String>("提付月结运费", "column8");
        Column<String> column9 = new Column<String>("回单运费", "column9");
        final PageTableData tableData = new PageTableData("收货统计", formList, columnBillDeptName, columnOrderCount, columnItemQty,
                column4, column5, column6, column7, column8, column9);
        //设置文字字体大小
        FontStyle.setDefaultTextSpSize(getContext(), 16);
        //设置列标题绘制格式化
        int size = DensityUtils.dp2px(this, 15);
        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size, size, TitleImageDrawFormat.RIGHT, 10) {
            @Override
            protected Context getContext() {
                return TableActivity.this;
            }

            @Override
            protected int getResourceID(Column column) {
                if (!column.isParent()) {
                    if (tableData.getSortColumn() == column) {
                        if (column.isReverseSort()) {
                            return R.mipmap.sort_down;
                        }
                        return R.mipmap.sort_up;
                    }
                }
                return 0;
            }
        });

        //固定列
        columnBillDeptName.setFixed(true);
        //缩放
        smartTable.setZoom(true);
        //点击事件
        smartTable.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {
                //点击排序
                if (!columnInfo.column.isParent()) {
                    smartTable.setSortColumn(columnInfo.column, !columnInfo.column.isReverseSort());
                }
            }
        });
        //批注
        MultiLineBubbleTip<Column> tip = new MultiLineBubbleTip<Column>(getContext(), R.mipmap.round_rect, R.mipmap.triangle,
                new FontStyle().setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))) {
            @Override
            public boolean isShowTip(Column column, int position) {
                if (column == columnBillDeptName) {
                    return true;
                }
                return false;
            }

            @Override
            public String[] format(Column column, int position) {
                Form form = formList.get(position);
                return new String[]{"开票部门:" + form.getColumn1(), "运单数量:" + form.getColumn2()};
            }
        };
        //设置批注背景色
        tip.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_red));
        //透明度
        tip.setAlpha(0.5f);
        smartTable.getProvider().setTip(tip);
//        columnOrderCount.setComparator(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//
//                return Integer.parseInt(o1) - Integer.parseInt(o2);
//            }
//        });
//        //排序,是否反序
//        smartTable.setSortColumn(columnBillDeptName, false);
        //对齐方式
        columnBillDeptName.setTextAlign(Paint.Align.LEFT);
        columnOrderCount.setTextAlign(Paint.Align.CENTER);
        columnItemQty.setTextAlign(Paint.Align.RIGHT);
        //单元格合并
//        columnBillDeptName.setAutoMerge(true);
        //设置开启自动统计
        columnOrderCount.setAutoCount(true);
        columnItemQty.setAutoCount(true);
        column4.setAutoCount(true);
        //设置是否显示统计总数
        tableData.setShowCount(true);
        columnBillDeptName.setMaxMergeCount(3);
        //设置最小宽度
        smartTable.getConfig().setMinTableWidth(getWindowManager().getDefaultDisplay().getWidth());
        //设置标题
        FontStyle titleStyle = new FontStyle();
        titleStyle.setAlign(Paint.Align.CENTER);
        titleStyle.setTextColor(Color.BLACK).setTextSize(DensityUtils.sp2px(getContext(), 30));
        smartTable.getConfig().setTableTitleStyle(titleStyle);
        //设置序列号,从0开始，0不显示;
        smartTable.getConfig().setYSequenceFormat(new BaseSequenceFormat() {
            @Override
            public String format(Integer integer) {
                return String.valueOf(integer);
            }
        });
        //设置序列背景色
        TableConfig config = smartTable.getConfig();
        config.setYSequenceBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(), R.color.color_y_sequence)));
        config.setXSequenceBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(), R.color.color_y_sequence)));
        config.setLeftAndTopBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_red));
        //设置网格线颜色
        config.getContentGridStyle().setColor(ContextCompat.getColor(getContext(), R.color.color_white));
        //设置背景色
        config.setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (cellInfo.row % 2 == 0) {
                    return ContextCompat.getColor(getContext(), R.color.color_gray);
                }
                return TableConfig.INVALID_COLOR;
            }
        });
        //列标题背景色设置
        config.setColumnTitleBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(), R.color.color_dark)));
        //列标题字体样式
        config.setColumnTitleStyle(new FontStyle(DensityUtils.dp2px(getContext(), 18), ContextCompat.getColor(getContext(), R.color.color_white)));
        //设置页数
        tableData.setPageSize(20);
        lastIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableData.setCurrentPage(tableData.getCurrentPage() - 1);
                smartTable.notifyDataChanged();
                currentPageTv.setText(String.valueOf(tableData.getCurrentPage()));


            }
        });
        nextIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableData.setCurrentPage(tableData.getCurrentPage() + 1);
                smartTable.notifyDataChanged();
                currentPageTv.setText(String.valueOf(tableData.getCurrentPage()));

            }
        });
        smartTable.setTableData(tableData);
    }

    private List<Form> getList(int size) {
        List<Form> formList = new ArrayList<>();
        String[] billDeptNames = {"测试分理处", "南阳", "洛阳", "新乡", "漯河", "周口"};
        for (int i = 0; i < size; i++) {
            Form form = new Form(billDeptNames[(int) (Math.random() * 6)], (int) (Math.random() * 100), (int) (Math.random() * 300));
            formList.add(form);
        }
//        Collections.sort(formList, new Comparator<Form>() {
//            @Override
//            public int compare(Form o1, Form o2) {
//                return PinYin.getPinYinFirst(o1.getColumn1()).compareTo(PinYin.getPinYinFirst(o2.getColumn1()));
//            }
//        });
        return formList;
    }

    public Context getContext() {
        return this;
    }
}
