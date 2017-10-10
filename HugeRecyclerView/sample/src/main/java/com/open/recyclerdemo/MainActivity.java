package com.open.recyclerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_simple).setOnClickListener(this);
        findViewById(R.id.tv_header_footer).setOnClickListener(this);
        findViewById(R.id.endless).setOnClickListener(this);
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
            default:
                break;
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
