package com.open.hugerecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewStub;

/**
 * loading footer view for ListView/GridView/RecyclerView when loading by spit page
 */
public class EndlessFooterView extends BaseEndlessFooterView {

    public EndlessFooterView(Context context) {
        this(context, null, 0);
    }

    public EndlessFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EndlessFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void setContentView(Context context) {
        inflate(context, R.layout.endless_footer, this);
    }

    @Override
    protected void setLoadingView() {
        if (mLoadingView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
            mLoadingView = viewStub.inflate();
        }
    }

    @Override
    protected void setErrorView() {
        if (mErrorView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.error_viewstub);
            mErrorView = viewStub.inflate();
        }
    }

    @Override
    protected void setEndView() {
        if (mTheEndView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);
            mTheEndView = viewStub.inflate();
        }
    }

}