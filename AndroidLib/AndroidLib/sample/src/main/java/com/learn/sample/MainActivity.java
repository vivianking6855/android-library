package com.learn.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.open.utislib.bitmap.BitmapUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void test(){
        BitmapUtils.safeRecycle(null);
    }
}
