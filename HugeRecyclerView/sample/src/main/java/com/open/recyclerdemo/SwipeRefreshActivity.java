package com.open.recyclerdemo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.open.recyclerdemo.adapter.SampleRecyclerAdapter;
import com.open.recyclerdemo.base.BaseActivity;
import com.open.recyclerdemo.listener.ISampleListener;
import com.open.recyclerdemo.model.SampleModel;
import com.open.recyclerdemo.presenter.SamplePresenter;

import java.util.List;

public class SwipeRefreshActivity extends BaseActivity implements ISampleListener {
    private SamplePresenter mPresenter;
    private TextView mHint;

    private SampleRecyclerAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        mPresenter = new SamplePresenter();
        mPresenter.setListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_swipe_refresh);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new SampleRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mHint = (TextView) findViewById(R.id.tv_hint);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                // load data and refresh ui here
                mPresenter.getData();
            }
        });
    }

    @Override
    protected void loadData() {
        mPresenter.getData();
    }

    @Override
    public void OnLoadStart() {
        mHint.setText(getString(R.string.load_start));
    }

    @Override
    public void OnLoadSuccess(List<SampleModel> data) {
        mHint.setText(getString(R.string.load_success));
        mAdapter.addData(data);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void OnLoadFail(String error) {
        mHint.setText(getString(R.string.load_fail) + error);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}