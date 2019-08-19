package com.sinotech.report.main.draw;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView基类适配器
 * Created by LWH on 2017-04-28.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHold> {
    private Context mContext;
    protected List<T> mList;
    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    /**
     * 条目事件
     *
     * @param listener 条目事件监听
     */
    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        mOnRecyclerItemClickListener = listener;
    }

    /**
     * Created by LWH at 2017-04-27 17:43
     * 条目事件
     */
    public interface OnRecyclerItemClickListener {
        //条目点击事件
        void onItemClick(View view, int position);

        //条目长按事件
        void onItemLongClick(View view, int position);
    }

    @Override
    public BaseViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(), parent, false);
        return new BaseViewHold(view);
    }

    /**
     * @param holder   BaseRecyclerAdapter.BaseViewHold
     * @param position 索引
     */
    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.BaseViewHold holder, int position) {
        bindData(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 绑定数据
     *
     * @param holder   ViewHolder
     * @param position 索引
     */
    protected abstract void bindData(BaseViewHold holder, int position);

    /**
     * 获取布局ID
     */
    protected abstract int getLayoutId();

    /**
     * 添加数据
     */
    public void notifyData(List<T> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        } else {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    public void append(List<T> response) {
        if (response != null) {
            mList.addAll(response);
        }
        notifyDataSetChanged();
    }
    /**
     * 根据索引移除对象
     *
     * @param index 索引
     */
    public void remove(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }

    /**
     * 移除指定对象
     *
     * @param object 对象
     */
    public void remove(T object) {
        mList.remove(object);
        notifyDataSetChanged();
    }

    /**
     * 清空列表
     */
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    /**
     * Created by LWH at 2017-05-09 9:42
     * 获取界面数据集合
     */
    public List<T> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class BaseViewHold extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;

        BaseViewHold(final View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
            itemView.setOnClickListener(v -> {
                if (mOnRecyclerItemClickListener != null) {
                    mOnRecyclerItemClickListener.onItemClick(itemView, getLayoutPosition());
                }
            });
            itemView.setOnLongClickListener(v -> {
                if (mOnRecyclerItemClickListener != null) {
                    mOnRecyclerItemClickListener.onItemLongClick(itemView, getLayoutPosition());
                }
                return true;
            });
        }

        /**
         * 获取item布局
         */
        public View getItemView() {
            return itemView;
        }

        /**
         * Created by LWH at 2017-05-02 13:35
         * 通过ID获取控件
         */
        public View getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }

        /**
         * Created by LWH at 2017-05-02 13:41
         * 控件绑定内容
         */
        public BaseViewHold setText(int viewId, String text) {
            TextView textView = (TextView) getView(viewId);
            textView.setText(text);
            return this;
        }

        /**
         * Created by LWH at 2017-05-11 17:17
         * 控件绑定内容
         */
        public BaseViewHold setText(int viewId, int resId) {
            TextView textView = (TextView) getView(viewId);
            textView.setText(mContext.getResources().getString(resId));
            return this;
        }

        /**
         * Created by LWH at 2017-05-12 9:30
         * 设置背景颜色
         */
        public BaseViewHold setBackGroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        /**
         * Created by LWH at 2017-05-19 16:27
         * 设置控件是否可见
         */
        public BaseViewHold setVisibility(int viewId, int visibility) {
            View view = getView(viewId);
            view.setVisibility(visibility);
            return this;
        }

        /**
         * Created by LWH at 2017-05-02 13:42
         * 绑定资源图片
         */
        public BaseViewHold setImageResource(int viewId, int resId) {
            ImageView imageView = (ImageView) getView(viewId);
            imageView.setImageResource(resId);
            return this;
        }

        /**
         * Created by LWH at 2017-05-02 13:42
         * 绑定网络图片
         */
        public BaseViewHold bindImage(int viewId, String url) {
            ImageView imageView = (ImageView) getView(viewId);

            return this;
        }

        public BaseViewHold setOnClickListener(int viewId, View.OnClickListener onClickListener) {
            View view = getView(viewId);
            view.setOnClickListener(onClickListener);
            return this;
        }

        /**
         * Created by LWH at 2017/5/23 17:01
         * 设置选择监听
         */
        public BaseViewHold setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
            CompoundButton compoundButton = (CompoundButton) getView(viewId);
            compoundButton.setOnCheckedChangeListener(listener);
            return this;
        }

        public BaseViewHold setChecked(int viewId, boolean isChecked) {
            CompoundButton compoundButton = (CompoundButton) getView(viewId);
            compoundButton.setChecked(isChecked);
            return this;
        }
    }
}


