package com.open.recyclerdemo.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by vivian on 2017/9/12.
 * Listener for First Fragment
 */

public abstract class HugeRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private final static String TAG = HugeRecyclerOnScrollListener.class.getSimpleName();

    // if data is still loading
    private boolean loading = false;
    // all item count by LayoutManager.getItemCount();
    private int mTotalCount;
    // visible item count by LayoutManager.getChildCount();
    private int mVisibleCount;
    // last visible position count by LayoutManager.findLastVisibleItemPosition()
    private int mLastVisiblePosition;

    public abstract void onLoadMore();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        mLastVisiblePosition = layoutManager.findLastVisibleItemPosition();
    }

    /**
     * newState
     *
     * @param recyclerView the recycler view
     * @param newState     0: 当前屏幕停止滚动；
     *                     1: 屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；
     *                     2: 随用户的操作，屏幕上产生的惯性滑动；
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        // get layout manager
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        mVisibleCount = layoutManager.getChildCount();
        mTotalCount = layoutManager.getItemCount();
        Log.d(TAG, "mVisibleCount = " + mVisibleCount + ";mTotalCount = " +
                mTotalCount + ";mLastVisibleItemPosition = " + mLastVisiblePosition
                + ";newState=" + newState);

        if (mVisibleCount > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && mLastVisiblePosition >= mTotalCount - 2) {
            onLoadMore();
        }
    }
}

