package com.open.recyclerdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.recyclerdemo.R;

/**
 * loading footer view for ListView/GridView/RecyclerView when loading by spit page
 */
public class EndlessFooterView extends RelativeLayout {
    private final static String TAG = "EndlessFooterView";

    protected State mState = State.Normal;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;
    private ProgressBar mLoadingProgress;
    private TextView mLoadingText;

    public EndlessFooterView(Context context) {
        super(context);
        init(context);
    }

    public EndlessFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EndlessFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.endless_footer, this);
        setOnClickListener(null);
        setState(State.Normal);
    }

    public State getState() {
        return mState;
    }

    /**
     * set endless footer status
     *
     * @param status
     */
    public void setState(State status) {
        if (mState == status) {
            Log.d(TAG, "setState, state not change, return");
            return;
        }

        mState = status;

        switch (status) {
            case Normal:
                dealNormal();
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

    private void dealNormal(){
        setOnClickListener(null);
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

    private void dealLoading() {
        setOnClickListener(null);
        if (mTheEndView != null) {
            mTheEndView.setVisibility(GONE);
        }

        if (mNetworkErrorView != null) {
            mNetworkErrorView.setVisibility(GONE);
        }

        if (mLoadingView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
            mLoadingView = viewStub.inflate();

            mLoadingProgress = (ProgressBar) mLoadingView.findViewById(R.id.loading_progress);
            mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
        } else {
            mLoadingView.setVisibility(VISIBLE);
        }

        mLoadingView.setVisibility(VISIBLE);

        mLoadingProgress.setVisibility(View.VISIBLE);
        mLoadingText.setText(R.string.list_footer_loading);
    }

    private void dealEnd() {
        setOnClickListener(null);
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }

        if (mNetworkErrorView != null) {
            mNetworkErrorView.setVisibility(GONE);
        }

        if (mTheEndView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);
            mTheEndView = viewStub.inflate();
        } else {
            mTheEndView.setVisibility(VISIBLE);
        }

        mTheEndView.setVisibility(VISIBLE);
    }

    private void dealError() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }

        if (mTheEndView != null) {
            mTheEndView.setVisibility(GONE);
        }

        if (mNetworkErrorView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.error_viewstub);
            mNetworkErrorView = viewStub.inflate();
        } else {
            mNetworkErrorView.setVisibility(VISIBLE);
        }

        mNetworkErrorView.setVisibility(VISIBLE);
    }

    public static enum State {
        Normal,    // normal status, not loading, normal show list
        End,       // no more data
        Loading,  // data loading
        Error      // load fail
    }
}