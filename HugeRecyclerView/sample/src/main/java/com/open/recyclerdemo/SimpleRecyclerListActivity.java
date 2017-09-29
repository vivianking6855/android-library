package com.open.recyclerdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.open.recyclerdemo.adapter.SampleRecyclerAdapter;
import com.open.recyclerdemo.model.SampleModel;
import com.open.recyclerdemo.presenter.SamplePresenter;

import java.util.List;

import com.open.recyclerdemo.base.BaseActivity;
import listener.ISampleListener;

public class SimpleRecyclerListActivity extends BaseActivity implements ISampleListener {
    private SamplePresenter mPresenter;
    private TextView mHint;

    private SampleRecyclerAdapter mAdapter;

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
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new SampleRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mHint = (TextView) findViewById(R.id.tv_hint);
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
        mAdapter.setData(data);
    }

    @Override
    public void OnLoadFail(String error) {
        mHint.setText(getString(R.string.load_fail) + error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
