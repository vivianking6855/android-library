package com.open.hugerecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vivian on 2017/9/21.
 * recycler view third party template: https://github.com/captain-miao/RecyclerViewUtils
 */

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    // item type
    private static final int TYPE_NORMAL = -1; // normal item

    // model data
    protected List<T> mItemList;

    //header view and footer view can't be recycled
    private List<RecyclerView.ViewHolder> mHeaderViews = new ArrayList<>();
    //store the ViewHolder for remove header view
    private Map<View, RecyclerView.ViewHolder> mHeaderViewHolderMap = new HashMap<>();
    private int mHeaderSize;
    //header view and footer view can't be recycled
    private List<RecyclerView.ViewHolder> mFooterViews = new ArrayList<>();
    //store the ViewHolder for remove footer view
    private Map<View, RecyclerView.ViewHolder> mFooterViewHolderMap = new HashMap<>();
    private int mFooterSize;

    public BaseRecyclerAdapter() {
        mItemList = new ArrayList<>();
    }

    // normal item holder
    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    // normal item bind
    public abstract void onBindItemViewHolder(VH holder, int position);

    @Override
    public int getItemViewType(int position) {
        // no header and footer or normal type
        if (isContentView(position)) {
            return TYPE_NORMAL;
        }
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // normal
        if (TYPE_NORMAL == viewType) {
            return onCreateItemViewHolder(parent, viewType);
        }
        if (viewType < mHeaderSize) {// header
            return mHeaderViews.get(viewType);
        }
        // footer
        return mFooterViews.get(viewType - getBasicItemCount() - mHeaderSize);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isContentView(position)) {
            onBindItemViewHolder((VH) holder, position - mHeaderSize);
        }
    }

    @Override
    public int getItemCount() {
        // has header & footer
        return getBasicItemCount() + mHeaderSize + mFooterSize;
    }

    private boolean isContentView(int position) {
        return position >= mHeaderSize && position < (getBasicItemCount() + mHeaderSize);
    }

    private int getBasicItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public void addHeaderView(View view) {
        RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view) {
        };
        holder.setIsRecyclable(false);
        mHeaderViewHolderMap.put(view, holder);
        mHeaderViews.add(holder);
        mHeaderSize = mHeaderViews.size();
        notifyItemInserted(mHeaderSize - 1);
    }

    public void removeHeaderView(View view) {
        RecyclerView.ViewHolder viewHolder = mHeaderViewHolderMap.get(view);
        if (mHeaderViews.remove(viewHolder)) {
            mHeaderSize = mHeaderViews.size();
            mHeaderViewHolderMap.remove(view);
            notifyDataSetChanged();
        }
    }

    public void addFooterView(View view) {
        RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view) {
        };
        holder.setIsRecyclable(false);
        mFooterViewHolderMap.put(view, holder);
        mFooterViews.add(holder);
        mFooterSize = mFooterViews.size();
        notifyItemInserted(mFooterSize - 1);
    }

    public void removeFooterView(View view) {
        RecyclerView.ViewHolder viewHolder = mFooterViewHolderMap.get(view);
        if (viewHolder != null && mFooterViews.remove(viewHolder)) {
            mFooterSize = mFooterViews.size();
            mFooterViewHolderMap.remove(view);
            notifyDataSetChanged();
        }
    }

}
