package com.example.qmread.Utils.callback;

import com.example.qmread.Utils.entity.JsonModel;

public interface JsonCallBack {

    void onFinish(JsonModel jsonModel);

    void onError(Exception e);

}
