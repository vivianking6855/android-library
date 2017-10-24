package com.open.hugerecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * loading footer base view for ListView/GridView/RecyclerView when loading by spit page
 */
public abstract class BaseEndlessFooterView extends RelativeLayout {
    private final static String TAG = "BaseEndlessFooterView";

    // failed view
    protected View mErrorView;
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
    protected abstract void setContentView(Context context);

    // set loading view
    protected abstract void setLoadingView();

    // set error view
    protected abstract void setErrorView();

    // set end/no more data view
    protected abstract void setEndView();

    public BaseEndlessFooterView(Context context) {
        this(context,null,0);
    }

    public BaseEndlessFooterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseEndlessFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        setContentView(context);
        setOnClickListener(null);
        setState(State.Normal);

        // inflate views
        setLoadingView();
        setEndView();
        setErrorView();
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
        if (mLoadingView != null) {
            mLoadingView.setVisibility(VISIBLE);
        }
    }

    private void dealEnd() {
        setOnClickListener(null);
        if (mTheEndView != null) {
            mTheEndView.setVisibility(VISIBLE);
        }
    }

    private void dealError() {
        if (mErrorView != null) {
            mErrorView.setVisibility(VISIBLE);
        }
    }

    private void hideAllView() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }
        if (mTheEndView != null) {
            mTheEndView.setVisibility(GONE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(GONE);
        }
    }
}