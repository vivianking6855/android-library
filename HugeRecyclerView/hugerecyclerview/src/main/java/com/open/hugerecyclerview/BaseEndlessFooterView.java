package com.open.hugerecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * loading footer view for ListView/GridView/RecyclerView when loading by spit page
 */
public abstract class BaseEndlessFooterView extends RelativeLayout {
    private final static String TAG = "BaseEndlessFooterView";

    // failed view
    protected View mNetworkErrorView;
    // end, no more data view
    protected View mTheEndView;
    // loading views
    protected View mLoadingView;

    // view state
    protected State mState = State.Normal;

    public enum State {
        Normal,    // normal status, not loading, normal show list
        End,       // no more data
        Loading,  // data loading
        Error      // load fail
    }

    // set view layout
    protected abstract void setContentView();

    // set loading view
    protected abstract void setLoadingView();

    // set error view
    protected abstract void setErrorView();

    // set end/no more data view
    protected abstract void setEndView();

    public BaseEndlessFooterView(Context context) {
        super(context);
    }

    public BaseEndlessFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseEndlessFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void initView() {
        setContentView();
        setOnClickListener(null);
        setState(State.Normal);
    }

    public State getState() {
        return mState;
    }

    /**
     * set endless footer status
     *
     * @param state status of endless footer view
     */
    public void setState(State state) {
        // check status
        if (mState == state) {
            Log.d(TAG, "setState, state not change, return");
            return;
        }
        mState = state;

        // hide all first
        hideAllView();

        // deal with different state
        switch (state) {
            case Normal:
                setOnClickListener(null);
                break;
            case Loading:
                dealLoading();
                break;
            case End:
                dealEnd();
                break;
            case Error:
                dealError();
                break;
            default:
                break;
        }
    }

    private void dealLoading() {
        setOnClickListener(null);
        setLoadingView();
        if (mLoadingView != null) {
            mLoadingView.setVisibility(VISIBLE);
        }
    }

    private void dealEnd() {
        setOnClickListener(null);
        setEndView();
        if (mTheEndView != null) {
            mTheEndView.setVisibility(VISIBLE);
        }
    }

    private void dealError() {
        setErrorView();
        if (mNetworkErrorView != null) {
            mNetworkErrorView.setVisibility(VISIBLE);
        }
    }

    private void hideAllView() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }
        if (mTheEndView != null) {
            mTheEndView.setVisibility(GONE);
        }
        if (mNetworkErrorView != null) {
            mNetworkErrorView.setVisibility(GONE);
        }
    }
}