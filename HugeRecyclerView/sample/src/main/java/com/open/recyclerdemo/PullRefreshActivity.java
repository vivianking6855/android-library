package com.open.recyclerdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.open.recyclerdemo.adapter.EndlessRecyclerAdapter;
import com.open.recyclerdemo.base.BaseActivity;
import com.open.recyclerdemo.listener.IEndlessListener;
import com.open.recyclerdemo.model.SampleModel;
import com.open.recyclerdemo.presenter.EndlessPresenter;
import com.open.utislib.net.NetworkUtils;
import com.open.utislib.window.ToastUtils;

import java.util.List;

public class PullRefreshActivity extends BaseActivity implements IEndlessListener {
    private static final String TAG = "RefreshActivity";

    // pull refresh frame
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    // hint text view
    private TextView mHint;
    // presenter of MVP
    private EndlessPresenter mPresenter;
    // adapter
    private EndlessRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

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
        setContentView(R.layout.activity_pull_refresh);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new EndlessRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mHint = (TextView) findViewById(R.id.tv_hint);

        initRefreshFrame();
    }

    private void initRefreshFrame() {
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_view_frame);
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }
        });

    }

    private void refreshData() {
        if (!NetworkUtils.isConnected(PullRefreshActivity.this)) {
            Log.w(TAG, "net work no connected");
            ToastUtils.INSTANCE.showToast(PullRefreshActivity.this, "net work no connected",
                    Toast.LENGTH_SHORT);
            ptrClassicFrameLayout.refreshComplete();
            return;
        }

        // loading more data
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
        ptrClassicFrameLayout.refreshComplete();
    }

    @Override
    public void OnRefreshFail(String error) {
        mHint.setText(getString(R.string.load_fail) + error);
        ptrClassicFrameLayout.refreshComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}