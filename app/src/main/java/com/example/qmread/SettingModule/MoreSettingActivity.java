package com.example.qmread.SettingModule;

import androidx.appcompat.app.AppCompatActivity;
import com.example.qmread.R;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.databinding.ActivityMoreSettingBinding;
import com.example.qmread.greendao.gen.DaoUtilsStore;

import android.os.Bundle;
import android.widget.ImageView;

public class MoreSettingActivity extends AppCompatActivity {

    ActivityMoreSettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityMoreSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView back  = binding.ivBackToRead;
        back.setOnClickListener(v -> {
            finish();
        });
    }
}