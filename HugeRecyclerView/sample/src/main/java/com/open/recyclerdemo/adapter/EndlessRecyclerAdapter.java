package com.open.recyclerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.hugerecyclerview.BaseRecyclerAdapter;
import com.open.recyclerdemo.R;
import com.open.recyclerdemo.model.SampleModel;
import com.open.recyclerdemo.view.EndlessFooterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/10/9.
 */

public class EndlessRecyclerAdapter extends
        BaseRecyclerAdapter<SampleModel, EndlessRecyclerAdapter.ItemViewHolder> {
    private Context mContext;

    public EndlessRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<SampleModel> data) {
        mItemList.clear();
        mItemList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<SampleModel> data) {
        mItemList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public EndlessRecyclerAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new EndlessRecyclerAdapter.ItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(EndlessRecyclerAdapter.ItemViewHolder holder, int position) {
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
