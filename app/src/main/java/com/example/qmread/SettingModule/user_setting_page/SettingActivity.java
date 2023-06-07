package com.example.qmread.SettingModule.user_setting_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.qmread.MainViewModule.UserFragment;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    RelativeLayout out,account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView back = binding.ivBackToUser;
        back.setOnClickListener(v -> {
            finish();
        });
         out = binding.rlLoginOut;
         account = binding.rlAccount;
        loginOut();
        account.setOnClickListener(v -> {
            startActivity(new Intent(this,accountSafetyActivity.class));
        });
        out.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("退出登录").setMessage("退出后将无法同步账号的阅读进度且不能参与评论等互动")
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("确认", (dialog, which) -> {
                dialog.dismiss();
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("正在退出...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Handler().postDelayed(progressDialog::cancel,2000);
                ToolKits.putBoolean(this,"register",false);
                loginOut();
            });
            builder.setCancelable(false);
            builder.create().show();
        });
        RelativeLayout secret = binding.rlSecret;
        secret.setOnClickListener(v -> {
            startActivity(new Intent(this,secretActivity.class));
        });
        RelativeLayout about = binding.rlAbout;
        about.setOnClickListener(v -> {
            startActivity(new Intent(this,AboutActivity.class));
        });
        RelativeLayout clearData = binding.rlClearData;
        clearData.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否确认清除缓存?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        });
        RelativeLayout update = binding.rlUpdate;
        update.setOnClickListener(v -> {
            ProgressDialog undo = new ProgressDialog(this);
            undo.setMessage("正在检查更新，请稍候");
            undo.show();
            undo.setCanceledOnTouchOutside(true);
            new Handler().postDelayed(undo::cancel,2000);
            Toast.makeText(this,"已是最新版本",Toast.LENGTH_LONG).show();
        });
    }

    private void loginOut(){
        if(ToolKits.getBoolean(this,"register",false)){
            account.setVisibility(View.VISIBLE);
            out.setVisibility(View.VISIBLE);
        }else{
            account.setVisibility(View.GONE);
            out.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onResume() {
        Log.i("TAG", "onResume:oooooooooooo ");
        super.onResume();
        loginOut();
    }
}