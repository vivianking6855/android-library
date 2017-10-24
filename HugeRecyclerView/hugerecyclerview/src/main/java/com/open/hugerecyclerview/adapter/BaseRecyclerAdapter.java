package com.open.hugerecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/9/21.
 * recycler view adapter, support footer,header
 */

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    // item type
    private static final int TYPE_NORMAL = -1; // normal item

    // model data
    protected List<T> mItemList;

    //header view and footer view
    private List<View> mHeaderViews = new ArrayList<>();
    private int mHeaderSize;
    private List<View> mFooterViews = new ArrayList<>();
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
            return new ViewHolder(mHeaderViews.get(viewType));
        }
        // footer
        return new ViewHolder(mFooterViews.get(viewType - getBasicItemCount() - mHeaderSize));
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

    public int getBasicItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public void addHeaderView(View view) {
        mHeaderViews.add(view);
        mHeaderSize = mHeaderViews.size();
        notifyItemInserted(mHeaderSize - 1);
    }

    public void removeHeaderView(View view) {
        if (mHeaderViews.remove(view)) {
            mHeaderSize = mHeaderViews.size();
            notifyDataSetChanged();
        }
    }

    public List<View> getHeaderView() {
        return mHeaderViews;
    }

    public void addFooterView(View view) {
        mFooterViews.add(view);
        mFooterSize = mFooterViews.size();
        notifyItemInserted(mFooterSize - 1);
    }

    public void removeFooterView(View view) {
        if (mFooterViews.remove(view)) {
            mFooterSize = mFooterViews.size();
            notifyDataSetChanged();
        }
    }

    public List<View> getFooterView() {
        return mFooterViews;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
