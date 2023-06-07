package com.example.qmread.SettingModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.qmread.MainViewModule.UserFragment;
import com.example.qmread.SettingModule.acquire_help.ChangeNicknameHeadActivity;
import com.example.qmread.SettingModule.acquire_help.ChangeReadSettingActivity;
import com.example.qmread.SettingModule.acquire_help.FindCatalogueActivity;
import com.example.qmread.SettingModule.acquire_help.ImportBookActivity;
import com.example.qmread.SettingModule.user_setting_page.uploadOpinionActivity;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.databinding.ActivityHelpAndFeedbackBinding;
import com.google.android.material.button.MaterialButton;

public class HelpAndFeedbackActivity extends AppCompatActivity {

    ActivityHelpAndFeedbackBinding binding;
    RelativeLayout importBook,readSet,changeName,checkCatalogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityHelpAndFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView back = binding.ivBack;
        back.setOnClickListener(v -> {
            finish();
        });
        MaterialButton feedBack = binding.btFeedback;
        feedBack.setOnClickListener(v -> {
            startActivity(new Intent(this, uploadOpinionActivity.class));
        });
        importBook = binding.rlClearData;
        readSet = binding.rlReading;
        changeName = binding.rlClearHelp;
        checkCatalogue = binding.rlCategroyHelp;
        importBook.setOnClickListener(v -> {
            startActivity(new Intent(this, ImportBookActivity.class));
        });
        readSet.setOnClickListener(v -> {
            startActivity(new Intent(this, ChangeReadSettingActivity.class));
        });
        changeName.setOnClickListener(v -> {
            startActivity(new Intent(this, ChangeNicknameHeadActivity.class));
        });
        checkCatalogue.setOnClickListener(v -> {
            startActivity(new Intent(this, FindCatalogueActivity.class));
        });
    }
}