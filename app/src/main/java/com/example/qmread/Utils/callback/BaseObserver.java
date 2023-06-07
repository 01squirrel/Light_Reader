package com.example.qmread.Utils.callback;

import android.accounts.NetworkErrorException;
import android.content.Context;

import androidx.lifecycle.Observer;

import com.example.qmread.Utils.BaseResponse;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/* 请求回调,主要实现Observer类
           * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {


        private Context mContext;

        public BaseObserver(Context mContext) {
            this.mContext = mContext;
        }


        public void onCompleted() {

        }


        public void onError(Throwable e) {
            if (e instanceof ConnectException ||
                    e instanceof TimeoutException ||
                    e instanceof NetworkErrorException ||
                    e instanceof UnknownHostException) {
                try {
                    onFailure(e, false);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    onFailure(e, true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void onNext(BaseResponse<T> tBaseReponse) {
            if (tBaseReponse.isSuccess()) {
                onSuccess(tBaseReponse);
            } else {
                onCodeError(tBaseReponse);
            }
        }
        //请求成功且返回码为200的回调方法,这里抽象方法申明
        public abstract void onSuccess(BaseResponse<T> tBaseReponse);

        //请求成功但返回的code码不是200的回调方法,这里抽象方法申明
        public abstract void onCodeError(BaseResponse<T> tBaseReponse);

        //请求失败回调方法,这里抽象方法申明
        public abstract void onFailure(Throwable e, boolean netWork) throws Exception;

    public abstract void onFinish(InputStream in);
    public abstract void onFinish(String response);


}
