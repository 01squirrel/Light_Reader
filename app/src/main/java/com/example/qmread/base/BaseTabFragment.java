package com.example.qmread.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;


public abstract class BaseTabFragment<V extends BasePresenter> extends BaseFragment<V>{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);  //第三个参数一定要设为false;
        view.setTag(getPosition());     // 将当前 Fragment 的 position 设置为该 View 的 tag
        return view;
    }

    /**
     * 返回当前 Fragment 的 position
     */
    protected abstract int getPosition();
}
