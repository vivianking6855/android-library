package com.open.recyclerdemo.presenter;

import android.util.Log;

import com.open.recyclerdemo.listener.IEndlessListener;
import com.open.recyclerdemo.listener.ISampleListener;
import com.open.recyclerdemo.model.SampleDataApi;

import org.apache.commons.lang3.StringUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vivian on 2017/9/26.
 * MVP's presenter for Second Fragment
 */

public class EndlessPresenter {
    private static final String TAG = "SecondPresenter";
    private IEndlessListener mListener;
    // rx
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void setListener(IEndlessListener listener) {
        mListener = listener;
    }

    public void getData() {
        Log.d(TAG, "getData");
        // load start notify
        if (mListener != null) {
            mListener.OnLoadStart();
        }
        // load data
        Disposable dispose = Observable.fromCallable(SampleDataApi::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            Log.d(TAG, "getData Success: " + StringUtils.join(data.toArray(), ","));
                            if (mListener != null) {
                                mListener.OnLoadSuccess(data);
                            }
                        },
                        error -> {
                            Log.w(TAG, "getData error: ", error);
                            if (mListener != null) {
                                mListener.OnLoadFail(error.toString());
                            }
                        });

        compositeDisposable.add(dispose);
    }

    public void refreshData() {
        Log.d(TAG, "refreshData");
        // load start notify
        if (mListener != null) {
            mListener.OnLoadStart();
        }
        // load data
        Disposable dispose = Observable.fromCallable(SampleDataApi::refreshData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            Log.d(TAG, "refreshData Success: " + StringUtils.join(data.toArray(), ","));
                            if (mListener != null) {
                                mListener.OnRefreshSuccess(data);
                            }
                        },
                        error -> {
                            Log.w(TAG, "refreshData error: ", error);
                            if (mListener != null) {
                                mListener.OnRefreshFail(error.toString());
                            }
                        });

        compositeDisposable.add(dispose);
    }

    public void destroy() {
        // to avoid okhttp leak
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }


}
