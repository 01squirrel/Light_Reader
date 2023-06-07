package com.example.qmread.SettingModule.acquire_help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.qmread.R;
import com.example.qmread.SettingModule.user_setting_page.uploadOpinionActivity;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.databinding.ActivityChangeReadSettingBinding;
import com.google.android.material.button.MaterialButton;

public class ChangeReadSettingActivity extends AppCompatActivity {

    ActivityChangeReadSettingBinding binding;
    ImageView back;
    MaterialButton solver,master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityChangeReadSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back = binding.ivBack;
        back.setOnClickListener(v -> {
            finish();
        });
        solver = binding.mbResolver;
        master = binding.mbMaster;
        solver.setOnClickListener(v -> {
            solver.setBackgroundColor(getResources().getColor(R.color.text_color1,null));
        });
        master.setOnClickListener(v -> {
            startActivity(new Intent(this, uploadOpinionActivity.class));
        });
    }
}