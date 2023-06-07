package com.example.qmread.base;

import java.lang.ref.WeakReference;

public class BasePresenter<V> {
    //弱引用View
    protected WeakReference<V> mWeakReference;
    private V view;

    public void attachView(V view){
        this.view = view;
        mWeakReference = new WeakReference<V>(view);
    }

    public void detachView(){
        this.view = null;
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }

    protected  boolean isAttachView(){
        return view != null;
    }

    protected V getMvpView(){
        if (mWeakReference != null) {
            return mWeakReference.get();
        }
        return null;
    }
}
