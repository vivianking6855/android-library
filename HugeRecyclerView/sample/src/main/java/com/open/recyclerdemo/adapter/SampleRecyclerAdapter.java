package com.open.recyclerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.recyclerdemo.R;
import com.open.recyclerdemo.model.SampleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/10/10.
 */

public class SampleRecyclerAdapter extends RecyclerView.Adapter {
    private Context mContext;

    // model data
    protected List<SampleModel> mItemList;

    public SampleRecyclerAdapter(Context context) {
        mContext = context;
        mItemList = new ArrayList<>();
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).hint.setText(mItemList.get(position).mTitle);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView hint;

        ItemViewHolder(View view) {
            super(view);
            hint = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}
