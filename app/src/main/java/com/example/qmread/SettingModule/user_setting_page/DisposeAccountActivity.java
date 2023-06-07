package com.example.qmread.SettingModule.user_setting_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.databinding.ActivityDisposeAccountBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

public class DisposeAccountActivity extends AppCompatActivity {

    ActivityDisposeAccountBinding binding;
    ImageView back;
    MaterialCheckBox checkBox;
    MaterialButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityDisposeAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back = binding.ivBackToAccount;
        back.setOnClickListener(v -> {
            finish();
        });
        checkBox = binding.cbAgree;
        button = binding.mbDispose;
        button.setOnClickListener(v -> {
            if (checkBox.isChecked()){
                Toast.makeText(this,"注销成功",Toast.LENGTH_SHORT).show();
                ToolKits.putBoolean(this,"register",false);
                ToolKits.putBoolean(this,"phoneCode",false);
                new Handler().postDelayed(this::finish,1500);
            }else {
                Toast.makeText(this,"请先勾选上述协议",Toast.LENGTH_SHORT).show();
            }
        });
    }
}