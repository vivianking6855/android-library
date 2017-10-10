package com.open.recyclerdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.open.recyclerdemo.adapter.EndlessRecyclerAdapter;
import com.open.recyclerdemo.base.BaseActivity;
import com.open.recyclerdemo.listener.HugeRecyclerOnScrollListener;
import com.open.recyclerdemo.listener.IEndlessListener;
import com.open.recyclerdemo.model.SampleModel;
import com.open.recyclerdemo.presenter.EndlessPresenter;
import com.open.recyclerdemo.view.EndlessFooterUtils;
import com.open.recyclerdemo.view.EndlessFooterView;

import java.util.List;

public class EndlessActivity extends BaseActivity implements IEndlessListener {
    private static final String TAG = "EndlessActivity";

    // hint text view
    private TextView mHint;
    // presenter of MVP
    private EndlessPresenter mPresenter;
    // adapter
    private EndlessRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private HugeRecyclerOnScrollListener mHugeOnScrollListener;
    // all data account in server
    private static final int TOTAL_SIZE = 64;
    // each page size count
    private static final int PAGE_SIZE = 10;
    // current data for test
    private int mCurrentNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        mPresenter = new EndlessPresenter();
        mPresenter.setListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_head_footer);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new EndlessRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        // set scroll listener
        mHugeOnScrollListener = new HugeRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                EndlessFooterView.State state = EndlessFooterUtils.getFooterViewState(mRecyclerView);
                // still loading, do nothing
                if (EndlessFooterView.State.Loading == state) {
                    Log.d(TAG, "still loading, now return");
                    return;
                }
                // no more data
                if (mCurrentNum > TOTAL_SIZE) {
                    EndlessFooterUtils.setFooterViewState(EndlessActivity.this,
                            mRecyclerView, PAGE_SIZE, EndlessFooterView.State.End, null);
                    return;
                }

                refreshData();
            }
        };
        // add scroll listener
        mRecyclerView.addOnScrollListener(mHugeOnScrollListener);

        mHint = (TextView) findViewById(R.id.tv_hint);
    }

    private void refreshData(){
        // loading more data
        EndlessFooterUtils.setFooterViewState(EndlessActivity.this,
                mRecyclerView, PAGE_SIZE, EndlessFooterView.State.Loading, null);
        mPresenter.refreshData();
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
    public void OnRefreshSuccess(List<SampleModel> data) {
        mAdapter.addData(data);
        mCurrentNum += data.size();
        EndlessFooterUtils.setFooterViewState(EndlessActivity.this, mRecyclerView,
                PAGE_SIZE, EndlessFooterView.State.Normal, null);
    }

    @Override
    public void OnRefreshFail(String error) {
        EndlessFooterUtils.setFooterViewState(EndlessActivity.this, mRecyclerView,
                PAGE_SIZE, EndlessFooterView.State.Error, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
