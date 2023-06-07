package com.example.qmread.SettingModule.user_setting_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qmread.MainViewModule.UserFragment;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.databinding.ActivityAccountSafetyBinding;

public class accountSafetyActivity extends AppCompatActivity {

    ActivityAccountSafetyBinding binding;
    TextView phone;
    RelativeLayout dispose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityAccountSafetyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView back = binding.ivBackToSet;
        back.setOnClickListener(v -> {
            finish();
        });
        dispose = binding.rlAccountDispose;
        dispose.setOnClickListener(v -> {
            startActivity(new Intent(this,DisposeAccountActivity.class));
        });
        phone = binding.tvPhoneNumber;
        String num = ToolKits.getString(this,"phone","暂无");
        String newPhone = num.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        phone.setText(newPhone);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "onResume: niiiiiiiiiiiii");
        if(!ToolKits.getBoolean(this,"register",false)){
            phone.setText("暂无");
            dispose.setVisibility(View.GONE);
        }
    }
}