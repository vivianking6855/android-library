package com.open.recyclerdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.hugerecyclerview.BaseEndlessFooterView;
import com.open.recyclerdemo.R;

/**
 * loading footer view for ListView/GridView/RecyclerView when loading by spit page
 */
public class EndlessFooterView extends BaseEndlessFooterView {
    private final static String TAG = "EndlessFooterView";

    private TextView mLoadingText;
    private ProgressBar mLoadingProgress;

    private Context mContext;

    public EndlessFooterView(Context context){
        super(context);
        mContext = context;
        initView();
    }

    @Override
    protected void setContentView() {
        inflate(mContext, R.layout.endless_footer, this);
    }

    @Override
    protected void setLoadingView() {
        if (mLoadingView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
            mLoadingView = viewStub.inflate();

            mLoadingProgress = (ProgressBar) mLoadingView.findViewById(R.id.loading_progress);
            mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
        }
        mLoadingProgress.setVisibility(View.VISIBLE);
        mLoadingText.setText(R.string.list_footer_loading);
    }

    @Override
    protected void setErrorView() {
        if (mNetworkErrorView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.error_viewstub);
            mNetworkErrorView = viewStub.inflate();
        }
    }

    @Override
    protected void setEndView() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);
        mTheEndView = viewStub.inflate();
    }
}