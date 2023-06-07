package com.example.qmread.MainViewModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.qmread.Adapter.GuideAdapter;
import com.example.qmread.R;
import com.example.qmread.databinding.ActivityYinDaoBinding;

import java.util.ArrayList;
import java.util.List;

public class yinDaoActivity extends AppCompatActivity {
    private ViewPager pager;
    private Button btnstart;
    private ActivityYinDaoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityYinDaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pager = findViewById(R.id.vp_guide);
        btnstart = findViewById(R.id.bt_guide);
        btnstart.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
                });
        initViewPate();
    }

    private void initViewPate() {
        List<View> list = new ArrayList<>();
        ImageView image1 = new ImageView(this);
        image1.setImageResource(R.mipmap.splash_1);
        list.add(image1);
        ImageView image2 = new ImageView(this);
        image2.setImageResource(R.mipmap.splash_2);
        list.add(image2);
        ImageView image3 = new ImageView(this);
        image3.setImageResource(R.mipmap.splash_3);
        list.add(image3);

        GuideAdapter myAdapter = new GuideAdapter(list);
        pager.setAdapter(myAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnstart.setVisibility(View.GONE);
                } else if(position == 1){
                    btnstart.setVisibility(View.GONE);
                }else if(position == 2){
                    btnstart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }
}