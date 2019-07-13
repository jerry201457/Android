package com.jerry.android;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class MenuAdapter extends BGARecyclerViewAdapter<Menu> {
    public MenuAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Menu model) {
        helper.setText(R.id.item_menu_name_tv, model.getName());
    }
}
