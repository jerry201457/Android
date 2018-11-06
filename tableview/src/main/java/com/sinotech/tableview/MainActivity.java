package com.sinotech.tableview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.tip.MultiLineBubbleTip;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SmartTable<Form> smartTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smartTable = findViewById(R.id.table);
        final Column<String> columnBillDeptName = new Column<String>("开票部门", "column1");
        Column<String> columnOrderCount = new Column<String>("运单数量", "column2");
        Column<String> columnItemQty = new Column<String>("件数", "column3");
        Column<String> column4 = new Column<String>("代收款", "column4");
        Column<String> column5 = new Column<String>("提付运费", "column5");
        Column<String> column6 = new Column<String>("现付运费", "column6");
        Column<String> column7 = new Column<String>("现付月结运费", "column7");
        Column<String> column8 = new Column<String>("提付月结运费", "column8");
        Column<String> column9 = new Column<String>("回单运费", "column9");
        columnBillDeptName.setAutoCount(true);
        columnOrderCount.setAutoCount(true);
        final TableData tableData = new TableData("收货统计", getList(), columnBillDeptName, columnOrderCount, columnItemQty, column4, column5, column6, column7, column8, column9);
        smartTable.setTableData(tableData);
        //设置背景色
        smartTable.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (cellInfo.row % 2 == 0) {
                    return ContextCompat.getColor(getContext(), R.color.color_blue);
                }
                return TableConfig.INVALID_COLOR;
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
                Form form = getList().get(position);
                return new String[]{"开票部门:" + form.getColumn1(), "运单数量:" + form.getColumn2()};
            }
        };
        //设置批注背景色
        tip.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_red));
        //透明度
        tip.setAlpha(0.5f);
        smartTable.getProvider().setTip(tip);

    }

    private List<Form> getList() {
        List<Form> formList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Form form = new Form("00000" + i + 1, i * 2 + 1 + "", i * 3 + "");
            formList.add(form);
        }
        return formList;
    }

    public Context getContext() {
        return this;
    }
}
