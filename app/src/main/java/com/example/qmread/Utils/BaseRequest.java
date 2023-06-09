package com.example.qmread.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRequest {
    //初始化Okhttp,绑定拦截器事件
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)  //设置请求超时时间
            .readTimeout(20, TimeUnit.SECONDS)//设置读取数据超时时间
            .writeTimeout(20, TimeUnit.SECONDS)//设置写入数据超时时间
            .addInterceptor(HttpUtil.InterceptorUtil.LogInterceptor()) //绑定日志拦截器
            .addNetworkInterceptor(HttpUtil.InterceptorUtil.HeaderInterceptor())       //绑定header拦截器
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())//设置gson转换器,将返回的json数据转为实体
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//设置CallAdapter
            .baseUrl(HttpUtil.ApiService.HOST)
            .client(client)                                                  //设置客户端okhttp相关参数
            .build();


    public static BaseRequest instance;
    public HttpUtil.ApiService apiService = retrofit.create(HttpUtil.ApiService.class);         //经过retrofit的实例,获取ApiService接口的实例

    private BaseRequest() {

    }

    public static BaseRequest getInstance() {
        if (instance == null) {
            synchronized (BaseRequest.class) {
                if (instance == null) {
                    instance = new BaseRequest();
                }
            }
        }
        return instance;
    }

    public HttpUtil.ApiService getApiService() {
        return apiService;
    }
}
