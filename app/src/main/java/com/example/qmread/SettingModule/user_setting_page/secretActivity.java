package com.example.qmread.SettingModule.user_setting_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.databinding.ActivitySecretBinding;

public class secretActivity extends AppCompatActivity {

    ActivitySecretBinding binding;
    SwitchCompat info,intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivitySecretBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView back = binding.ivBackToSet;
        back.setOnClickListener(v -> {
            finish();
        });
        info = binding.scMsg;
        info.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ToolKits.putBoolean(this,"hiddenInfo",isChecked);
        });
        intro = binding.scIntroduce;
        intro.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            ToolKits.putBoolean(this,"hiddenIntroduce",isChecked);
        }));
    }
}