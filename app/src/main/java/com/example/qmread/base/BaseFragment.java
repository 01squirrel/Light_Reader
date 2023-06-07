package com.example.qmread.base;

import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


import org.jetbrains.annotations.Nullable;


public abstract class BaseFragment<V extends BasePresenter> extends Fragment {
    //该Fragment对应的Presenter
    protected V mPresenter;

    //获取TAG的fragment名称
    protected final String TAG = this.getClass().getSimpleName();
    public Context context;
    /**
     * 封装toast对象
     */
    private Toast toast;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        if (isRegisterEventBus()) {
            //EventBusUtil.register(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doInOnCreate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);

        initView(view);
        initData(context);
        return view;
    }


    /**
     * 保证同一按钮在1秒内只响应一次点击事件
     */
    public abstract static class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮的最小间隔，目前为1000
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(v);
            }
        }
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    public abstract static class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    /**
     * 显示提示  toast
     *
     * @param msg 提示信息
     */
    @SuppressLint("ShowToast")
    public void showToast(String msg) {
        try {
            if (null == toast) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        isRegisterEventBus();// EventBusUtil.unregister(this);
    }

    /**
     * 获取fragment布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 在进行初始化布局前初始化相关数据
     */
    protected abstract void initData(Context context);

    /**
     * 初始化视图
     */
    protected abstract void initView(View view);

    /**
     * 在onCreate方法中执行的操作
     */
    protected abstract void doInOnCreate();

    /**
     * 获取Presenter实例
     *
     * @return 相应的Presenter实例，没有则返回 null
     */
    protected abstract V getPresenter();


    /**
     * 是否注册EventBus，注册后才可以订阅事件
     *
     * @return 若需要注册 EventBus，则返回 true；否则返回 false
     */
    protected abstract boolean isRegisterEventBus();


    /**
     * 跳转到活动
     *
     * @param activity 新活动.class
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void jump2Activity(Class activity) {
        startActivity(new Intent(getContext(), activity));
    }

    /**
     * 带Bundle的跳转活动
     *
     * @param activity 新活动.class
     * @param bundle
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void jump2ActivityWithBundle(Class activity, Bundle bundle) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent, bundle);
    }

}
