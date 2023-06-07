package com.example.qmread.Utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.qmread.R;

public class StatusBarUtil {
    public static void makeStatusBarTransparent(Activity activity,int name) {

        Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //window.setStatusBarColor(getResources().getColor(R.color.white_f6));
        if(name!=0){
            window.setStatusBarColor( name);
        }else {
            window.setStatusBarColor( Color.TRANSPARENT);
        }
    }

}
