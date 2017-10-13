package com.open.recyclerdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.open.hugerecyclerview.listener.HugeRecyclerOnScrollListener;
import com.open.hugerecyclerview.utils.EndlessFooterUtils;
import com.open.recyclerdemo.adapter.EndlessRecyclerAdapter;
import com.open.recyclerdemo.base.BaseActivity;
import com.open.recyclerdemo.listener.IEndlessListener;
import com.open.recyclerdemo.model.SampleModel;
import com.open.recyclerdemo.presenter.EndlessPresenter;
import com.open.recyclerdemo.view.EndlessFooterView;
import com.open.utislib.net.NetworkUtils;
import com.open.utislib.window.ToastUtils;

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
    private EndlessFooterUtils mFooterUtil;

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
        mFooterUtil = new EndlessFooterUtils(new EndlessFooterView(this));
        // set scroll listener
        mHugeOnScrollListener = new HugeRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                EndlessFooterView.State state = mFooterUtil.getFooterViewState(mRecyclerView);
                // still loading, do nothing
                if (EndlessFooterView.State.Loading == state) {
                    Log.d(TAG, "still loading, now return");
                    return;
                }
                // no more data
                if (mCurrentNum > TOTAL_SIZE) {
                    mFooterUtil.setEnd(EndlessActivity.this, mRecyclerView, PAGE_SIZE);
                    return;
                }

                refreshData();
            }
        };
        // add scroll listener
        mRecyclerView.addOnScrollListener(mHugeOnScrollListener);

        mHint = (TextView) findViewById(R.id.tv_hint);
    }

    private void refreshData() {
        if (!NetworkUtils.isConnected(EndlessActivity.this)) {
            mFooterUtil.setError(EndlessActivity.this, mRecyclerView, PAGE_SIZE, mFooterClick);
            Log.w(TAG, "net work no connected");
            ToastUtils.INSTANCE.showToast(EndlessActivity.this, "net work no connected",
                    Toast.LENGTH_SHORT);
            return;
        }

        // loading more data
        mFooterUtil.setLoading(EndlessActivity.this, mRecyclerView, PAGE_SIZE);
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
        mFooterUtil.setNormal(EndlessActivity.this, mRecyclerView, PAGE_SIZE);
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            refreshData();
        }
    };

    @Override
    public void OnRefreshFail(String error) {
        mFooterUtil.setError(EndlessActivity.this, mRecyclerView, PAGE_SIZE, mFooterClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
