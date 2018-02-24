package com.open.recyclerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.recyclerdemo.view.CustomerEndlessFooterView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // set on click listener
        ViewGroup group = (ViewGroup) findViewById(R.id.layout_root);
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            if (child instanceof TextView) {
                child.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_simple:
                startActivity(SimpleRecyclerActivity.class);
                break;
            case R.id.tv_header_footer:
                startActivity(HeaderFooterActivity.class);
                break;
            case R.id.endless:
                startActivity(EndlessActivity.class);
                break;
            case R.id.endless_user:
                startActivity(EndlessCustormerActivity.class);
                break;
            case R.id.swipe:
                startActivity(SwipeRefreshActivity.class);
                break;
            case R.id.pull_refresh:
                startActivity(PullRefreshActivity.class);
                break;
            default:
                break;
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
