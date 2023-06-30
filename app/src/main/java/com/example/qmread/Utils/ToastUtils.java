package com.example.qmread.Utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;

    private CustomToast customToast;

    /**
     * 自定义CustomToast的显示
     * @param context   上下文
     * @param message   提示内容
     * @param playAnimate   是否显示动画  true，显示     false，不显示
     */
    public void showToast(Context context, String message, boolean playAnimate){

        if (customToast == null){
            customToast = new CustomToast(context);
        }
        customToast.show(message, playAnimate);
    }


    /**
     * Android原生Toast的显示，主要解决点多少就提示多少次的问题
     */
    public static void showToast(Context context, String content){

        if (toast == null){
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
