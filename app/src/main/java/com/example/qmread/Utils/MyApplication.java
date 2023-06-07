package com.example.qmread.Utils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.qmread.Utils.callback.LifeCycleCallback;

import java.util.concurrent.ExecutorService;

public class MyApplication extends Application {
    private static Handler handler = new Handler();
    private static MyApplication application;
    private ExecutorService mFixedThreadPool;

    public static MyApplication getApplication() {
        return application;
    }
    public static Context getmContext(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        LifeCycleCallback lifecycleCallbacks = new LifeCycleCallback();
        registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }
}
