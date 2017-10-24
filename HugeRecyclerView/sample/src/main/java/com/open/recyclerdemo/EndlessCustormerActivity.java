package com.open.recyclerdemo;

import android.os.Bundle;

import com.open.hugerecyclerview.EndlessFooterView;
import com.open.hugerecyclerview.utils.EndlessFooterUtils;
import com.open.recyclerdemo.view.CustomerEndlessFooterView;

public class EndlessCustormerActivity extends EndlessActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mFooterUtil = new EndlessFooterUtils(new CustomerEndlessFooterView(this));
    }

}
