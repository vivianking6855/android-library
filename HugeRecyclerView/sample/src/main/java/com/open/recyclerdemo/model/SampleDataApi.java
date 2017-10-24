package com.open.recyclerdemo.model;

import android.os.SystemClock;

import com.open.utislib.net.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/9/26.
 * Second Fragment Data API, will load data and deal with all logic here
 */

public class SampleDataApi {

    public static List<SampleModel> getData() {
        // do your consume work here
        SystemClock.sleep(2000);

        return mock();
    }

    private static List<SampleModel> mock() {
        List<SampleModel> list = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            SampleModel model = new SampleModel("" + (char) i);
            list.add(model);
        }

        return list;
    }

    public static List<SampleModel> refreshData() {
        // do your consume work here
        SystemClock.sleep(3000);

        return mock2();
    }

    private static List<SampleModel> mock2() {
        List<SampleModel> list = new ArrayList<>();
        for (int i = '0'; i <= '9'; i++) {
            SampleModel model = new SampleModel("" + (char) i);
            list.add(model);
        }

        return list;
    }
}
