package com.open.recyclerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.recyclerdemo.R;
import com.open.hugerecyclerview.adapter.BaseRecyclerAdapter;
import com.open.recyclerdemo.model.SampleModel;

import java.util.List;

/**
 * Created by vivian on 2017/9/21.
 * recycler view third party template: https://github.com/captain-miao/RecyclerViewUtils
 */

public class HeaderFooterRecyclerAdapter extends
        BaseRecyclerAdapter<SampleModel, HeaderFooterRecyclerAdapter.ItemViewHolder> {

    private Context mContext;

    public HeaderFooterRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<SampleModel> data) {
        mItemList.clear();
        mItemList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<SampleModel> data) {
        int len = mItemList.size();
        mItemList.addAll(data);
        notifyItemChanged(len);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        holder.hint.setText(mItemList.get(position).mTitle);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView hint;

        ItemViewHolder(View view) {
            super(view);
            hint = (TextView) view.findViewById(R.id.tv_content);
        }
    }

}
