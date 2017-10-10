package com.open.recyclerdemo.model;

/**
 * Created by vivian on 2017/9/26.
 * model for second Fragment
 */

public class SampleModel {
    public String mTitle;

    SampleModel(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
