package com.example.qmread.MainViewModule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qmread.R;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG ="ddddddd";
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        boolean isFirstOpen = ToolKits.getBoolean(this,"FIRST",true);

        if (isFirstOpen){
            Intent intent = new Intent(this, yinDaoActivity.class);
            startActivity(intent);
            ToolKits.putBoolean(this,"FIRST",false);
            finish();
        }else {
            jumpToMainPage();
        }
    }

    private void jumpToMainPage() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        },3000);
    }
}