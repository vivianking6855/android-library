package com.open.recyclerdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author vivian
 *         BaseActivity used for project refactoring
 *         initData
 *         initView
 *         loadData
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView(savedInstanceState);
        loadData();
    }

    /**
     * Init data.
     */
    protected abstract void initData();

    /**
     * Init view.
     * please setContentView here
     *
     * @param savedInstanceState the saved instance state
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * Load data.
     */
    protected abstract void loadData();

}

