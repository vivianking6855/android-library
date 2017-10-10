package com.open.hugerecyclerview.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.open.hugerecyclerview.BaseEndlessFooterView;
import com.open.hugerecyclerview.adapter.BaseRecyclerAdapter;

import java.security.InvalidParameterException;

/**
 * footer view state util for RecyclerView paging loading
 * you must set footer view before you use setEnd, setNormal, setLoading
 */
public class EndlessFooterUtils {
    private static final String TAG = "EndlessFooterUtils";
    private BaseEndlessFooterView mView;

    public EndlessFooterUtils(BaseEndlessFooterView view) {
        mView = view;
    }

    /**
     * Sets end.you must set footer view before you use
     *
     * @param context      the context
     * @param recyclerView the recycler view
     * @param pageSize     the page size
     */
    public void setEnd(Context context, RecyclerView recyclerView, int pageSize) {
        setFooterViewState(context, recyclerView, pageSize, BaseEndlessFooterView.State.End, null);
    }

    /**
     * Sets normal.you must set footer view before you use
     *
     * @param context      the context
     * @param recyclerView the recycler view
     * @param pageSize     the page size
     */
    public void setNormal(Context context, RecyclerView recyclerView, int pageSize) {
        setFooterViewState(context, recyclerView, pageSize, BaseEndlessFooterView.State.Normal, null);
    }

    /**
     * Sets loading.you must set footer view before you use
     *
     * @param context      the context
     * @param recyclerView the recycler view
     * @param pageSize     the page size
     */
    public void setLoading(Context context, RecyclerView recyclerView, int pageSize) {
        setFooterViewState(context, recyclerView, pageSize, BaseEndlessFooterView.State.Loading, null);
    }

    /**
     * Sets error.you must set footer view before you use
     *
     * @param context      the context
     * @param recyclerView the recycler view
     * @param pageSize     the page size
     */
    public void setError(Context context, RecyclerView recyclerView, int pageSize) {
        setFooterViewState(context, recyclerView, pageSize, BaseEndlessFooterView.State.Error, null);
    }

    /**
     * Sets footer view state.
     *
     * @param context       the context
     * @param recyclerView  the recycler view
     * @param pageSize      the page size
     * @param state         the state
     * @param errorListener the error listener
     */
    private void setFooterViewState(Context context, RecyclerView recyclerView, int pageSize,
                                           BaseEndlessFooterView.State state, View.OnClickListener errorListener) {
        if (mView == null) {
            Log.e(TAG, "you must set footer view before you use");
            throw new InvalidParameterException("you must call setFooterView to set footer view before you use");
        }
        // get adapter of recycler view
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            Log.w(TAG, "setFooterViewState adapter invalidate");
            return;
        }
        BaseRecyclerAdapter eadapter = (BaseRecyclerAdapter) adapter;
        // if less than one page, not need to add EndlessFooter
        if (eadapter.getBasicItemCount() < pageSize) {
            return;
        }

        // create or get endless footer
        if (eadapter.getFooterView().size() <= 0) { // create a new endless footer view
            eadapter.addFooterView(mView);
        }

        // set state
        mView.setState(state);
        if (state == BaseEndlessFooterView.State.Error && errorListener != null) {
            mView.setOnClickListener(errorListener);
        }
    }

    /**
     * get endless footer view status
     *
     * @param recyclerView recycler view
     * @return the footer view state
     */
    public BaseEndlessFooterView.State getFooterViewState(RecyclerView recyclerView) {
        // get adapter of recycler view
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            Log.w(TAG, "getFooterViewState adapter invalidate");
            return BaseEndlessFooterView.State.Normal;
        }
        // get endless footer view
        BaseRecyclerAdapter eadapter = (BaseRecyclerAdapter) adapter;
        if (eadapter.getFooterView().size() > 0) {
            BaseEndlessFooterView endlessFooterView = (BaseEndlessFooterView) eadapter.getFooterView().get(0);
            return endlessFooterView.getState();
        }

        return BaseEndlessFooterView.State.Normal;
    }

}