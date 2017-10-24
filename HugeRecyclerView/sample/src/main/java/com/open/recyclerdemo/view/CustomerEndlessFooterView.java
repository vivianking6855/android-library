package com.open.recyclerdemo.view;

import android.content.Context;

import com.open.hugerecyclerview.EndlessFooterView;
import com.open.recyclerdemo.R;

/**
 * customer endless loading footer view for ListView/GridView/RecyclerView when loading by spit page
 */
public class CustomerEndlessFooterView extends EndlessFooterView {

    public CustomerEndlessFooterView(Context context) {
        super(context);
    }

    @Override
    protected void setContentView(Context context) {
        inflate(context, R.layout.endless_user_footer, this);
    }

}