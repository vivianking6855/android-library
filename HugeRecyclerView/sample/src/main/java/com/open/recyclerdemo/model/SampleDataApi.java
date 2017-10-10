package com.open.recyclerdemo.model;

import android.os.SystemClock;

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
        for (int i = 'A'; i <= 'C'; i++) {
            SampleModel model = new SampleModel("" + (char) i);
            list.add(model);
        }

        return list;
    }

    public static List<SampleModel> refreshData() {
        // do your consume work here
        SystemClock.sleep(1000);

        return mock2();
    }

    private static List<SampleModel> mock2() {
        List<SampleModel> list = new ArrayList<>();
        for (int i = 'A'; i <= 'D'; i++) {
            SampleModel model = new SampleModel("" + (char) i);
            list.add(model);
        }

        return list;
    }
}
