package listener;

import com.open.recyclerdemo.model.SampleModel;

import java.util.List;

/**
 * Created by vivian on 2017/9/12.
 * Listener for First Fragment
 */

public interface ISampleListener {
    void OnLoadStart();

    void OnLoadSuccess(List<SampleModel> data);

    void OnLoadFail(String error);
}
