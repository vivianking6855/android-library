package com.open.recyclerdemo.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.open.recyclerdemo.adapter.EndlessRecyclerAdapter;
import com.open.recyclerdemo.view.EndlessFooterView;

/**
 * footer view state util for RecyclerView paging loading
 */
public class EndlessFooterUtils {
    private static final String TAG = "EndlessFooterUtils";

    /**
     * Sets footer view state.
     *
     * @param context       the context
     * @param recyclerView  the recycler view
     * @param pageSize      the page size
     * @param state         the state
     * @param errorListener the error listener
     */
    public static void setFooterViewState(Context context, RecyclerView recyclerView, int pageSize,
                                          EndlessFooterView.State state, View.OnClickListener errorListener) {
        // get adapter of recycler view
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null || !(adapter instanceof EndlessRecyclerAdapter)) {
            Log.w(TAG, "setFooterViewState adapter invalidate");
            return;
        }
        EndlessRecyclerAdapter eadapter = (EndlessRecyclerAdapter) adapter;
        // if less than one page, not need to add EndlessFooter
        if (eadapter.getBasicItemCount() < pageSize) {
            return;
        }

        // create or get endless footer
        EndlessFooterView endlessFooterView;
        if (eadapter.getFooterView().size() > 0) { // if already has endless footer
            endlessFooterView = (EndlessFooterView) eadapter.getFooterView().get(0);
        } else {// create a new endless footer view
            endlessFooterView = new EndlessFooterView(context);
            eadapter.addFooterView(endlessFooterView);
        }

        // set state
        endlessFooterView.setState(state);
        if (state == EndlessFooterView.State.Error && errorListener != null) {
            endlessFooterView.setOnClickListener(errorListener);
        }
    }

    /**
     * get EndlessFooterView status
     *
     * @param recyclerView recycler view
     */
    public static EndlessFooterView.State getFooterViewState(RecyclerView recyclerView) {
        // get adapter of recycler view
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null || !(adapter instanceof EndlessRecyclerAdapter)) {
            Log.w(TAG, "getFooterViewState adapter invalidate");
            return EndlessFooterView.State.Normal;
        }
        // get endless footer view
        EndlessRecyclerAdapter eadapter = (EndlessRecyclerAdapter) adapter;
        if (eadapter.getFooterView().size() > 0) {
            EndlessFooterView endlessFooterView = (EndlessFooterView) eadapter.getFooterView().get(0);
            return endlessFooterView.getState();
        }

        return EndlessFooterView.State.Normal;
    }

}
