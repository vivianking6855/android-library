package com.open.recyclerdemo.listener;

import com.open.recyclerdemo.model.SampleModel;

import java.util.List;

/**
 * Created by vivian on 2017/9/12.
 * Listener for First Fragment
 */

public interface IEndlessListener {
    void OnLoadStart();

    void OnLoadSuccess(List<SampleModel> data);

    void OnLoadFail(String error);

    void OnRefreshSuccess(List<SampleModel> data);

    void OnRefreshFail(String error);
}
