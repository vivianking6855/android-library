package com.open.applib.activity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.open.applib.activity.presenter.BasePresenter;

/**
 * The type Base mvp activity.
 * you need implement createPresenter to create Presenter
 * which must extends {@link BasePresenter}
 *
 *
 * @param <V> the type parameter
 * @param <T> the type parameter
 */
public abstract class BaseMVPActivity<V, T extends BasePresenter<V>> extends BaseActivity {
    protected T mPresenter; // presenter

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create presenter
        mPresenter = createPresenter();
        mPresenter.attachReference((V) this);
    }

    protected abstract T createPresenter();
}
