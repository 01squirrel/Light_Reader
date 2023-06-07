package com.example.qmread.SettingModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qmread.MainViewModule.UserFragment;
import com.example.qmread.R;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.databinding.ActivityPersonInfoBinding;
import com.example.qmread.greendao.gen.DaoUtilsStore;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class PersonInfoActivity extends AppCompatActivity {

    ActivityPersonInfoBinding binding;
    ImageView headPortrait;
    TextView name,signature,bookNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this, ContextCompat.getColor(this,R.color.green_a3));
        super.onCreate(savedInstanceState);
        binding = ActivityPersonInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView back = binding.ivBackToRead;
        back.setOnClickListener(v->{
           finish();
        });
        MaterialButton bt = binding.btEditPersonInfo;
        bt.setOnClickListener(v->{
            startActivity(new Intent(this,EditPersonInfoActivity.class));
        });
        headPortrait = binding.ivHead;
        Bitmap port = BitmapFactory.decodeResource(getResources(),R.mipmap.head);
        Bitmap source = ToolKits.getBitmap(this,"photo",port);
        headPortrait.setImageBitmap(source);
        name = binding.tvUserName;
        name.setText(ToolKits.getString(this,"nickName","努力上进的小松鼠"));
        signature = binding.tvIntroduction;
        signature.setText(ToolKits.getString(this,"signature","介绍一下自己吧！"));
        bookNum = binding.tvBookNumber;
        DaoUtilsStore store = new DaoUtilsStore();
       int num = store.getUserDaoUtils().queryAll().size();
       if(num>0){
           bookNum.setText(String.format(Locale.getDefault(),"%d本", num));
       }else{
           bookNum.setText("0本");
       }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("TAG", "onRestart: 111111111111");
    }

    @Override
    protected void onResume() {
        super.onResume();
        name.setText(ToolKits.getString(this,"nickName","努力上进的小松鼠"));
        signature.setText(ToolKits.getString(this,"signature","介绍一下自己吧！"));
        Log.i("TAG", "onResume: 22222222222222");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG", "onStop: 33333333333333");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG", "onPause: pause-pause-pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy: zaijianzaijianzaijian");
    }
}