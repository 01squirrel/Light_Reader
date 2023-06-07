package com.example.qmread.MainViewModule;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.qmread.Adapter.BookAdapter;
import com.example.qmread.R;
import com.example.qmread.ReadingModule.ReadBook;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.bean.BookEntity;
import com.example.qmread.bean.OrdinaryUserEntity;
import com.example.qmread.databinding.ReadNoticeBinding;
import com.example.qmread.databinding.UpdateRemindDialogBinding;
import com.example.qmread.greendao.gen.BookEntityDao;
import com.example.qmread.greendao.gen.DaoManager;
import com.example.qmread.greendao.gen.DaoUtilsStore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements BookShelfFragment.onGetFragmentValueListener{

    BottomNavigationView btm_Nav;
    private Fragment[] fragments;
    private int lastfragment;
    private long touchTime = 0;
    OnBackPressedDispatcher dispatcher;
    OnBackPressedCallback callback;
    private long clickTime =0L;
    LinearLayout bottom;
    BookShelfFragment bookShelfFragment;
    Set<BookEntity> bookEntitySet = new LinkedHashSet<>();
    Dialog dialog;
    UpdateRemindDialogBinding binding;
    private OrdinaryUserEntity ordinaryUserEntity;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white_f6));
        setContentView(R.layout.activity_main);
        boolean isFirstOpen = ToolKits.getBoolean(this,"FIRST",true);
        if(isFirstOpen){
            ordinaryUserEntity = new OrdinaryUserEntity();
            ordinaryUserEntity.setOrdinaryUserId(1);
            ordinaryUserEntity.setName("用户001");
            ordinaryUserEntity.setCreateTime(new Date());
            ordinaryUserEntity.setNickName("努力上进的小松鼠");
            DaoUtilsStore.getInstance().getOrdinaryUserDaoUtils().insert(ordinaryUserEntity);
        }
        int selfPermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_SETTINGS);
        if(selfPermission == PackageManager.PERMISSION_GRANTED){
            Log.i("TAG", "onCreate: 有权限了");
        }else {
            if( !ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_SETTINGS)){
                Log.i("permission", "onCreate: ---090909");
            }
            requestPermissions(new String[] {Manifest.permission.WRITE_SETTINGS},0);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_SETTINGS,},0);
        }
        File info = new File(this.getFilesDir().getAbsolutePath());
        Log.i("TAG", "onCreate:-- "+ Arrays.toString(info.listFiles()));
       // /data/user/0/com.example.guide/files/《原神：开局撞晕了荧》.txt
        initUI();
        initListener();
        dispatcher = new OnBackPressedDispatcher();
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                long currentTime = System.currentTimeMillis();
                long waitTime = 2000;
                if ((currentTime - touchTime) > waitTime) {
                    Toast.makeText(MainActivity.this, "请再按一次退出", Toast.LENGTH_SHORT).show();
                    touchTime = currentTime;
                } else {
                    finish();
                    //System.exit(0);
                }
            }
        };
        dispatcher.addCallback(this,callback);
        bookShelfFragment = new BookShelfFragment();
        //deleteBook();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initUI() {
        //BookCityFragment bookCityFragment = new BookCityFragment();
        BookShelfFragment bookShelfFragment = new BookShelfFragment();
        FileManageFragment fileManageFragment = new FileManageFragment();
        UserFragment userFragment = new UserFragment();
        fragments = new Fragment[]{ bookShelfFragment, fileManageFragment, userFragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.frg, bookShelfFragment).show(bookShelfFragment).commit();
    }

    private void initListener() {
        btm_Nav = findViewById(R.id.bottom_nav);
        bottom = findViewById(R.id.ll_delete);
        btm_Nav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bookshelf){
                    switchFragment(lastfragment,0);
                    lastfragment = 0;
            } else if(id == R.id.filemanage) {
                    switchFragment(lastfragment, 1);
                    lastfragment = 1;
            }else if (id == R.id.user) {
                    switchFragment(lastfragment, 2);
                    lastfragment = 2;
            }
            return true;
        });
    }

    private void switchFragment(int lastfragment,int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if (!fragments[index].isAdded()){
            transaction.add(R.id.frg,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
            }

    private void deleteBook(){
        LinearLayout delete = findViewById(R.id.ll_delete);
        delete.setOnClickListener(v -> {
            for(BookEntity book : bookEntitySet){
                DaoUtilsStore.getInstance().getUserDaoUtils().delete(book);
            }
            Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
            //bookShelfFragment.refreshData(true);
        });
            }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - clickTime > 2000){
                Toast.makeText(this,"请再按一次退出",Toast.LENGTH_SHORT).show();
                clickTime = System.currentTimeMillis();
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void choseNumber(Set<BookEntity> total) {
        if(!total.isEmpty()){
            bookEntitySet = total;
            }
    }

    @Override
    public void show(boolean hidden) {
        Log.i("TAG", "show: "+hidden);
        if(!hidden){
            btm_Nav.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
        }else {
            btm_Nav.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
        }
    }
    public void refresh() {
        onCreate(null);
    }
    @Override
    protected void onResume() {
        super.onResume();
        deleteBook();
       // onCreate(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(this,"青梦阅读已进入后台",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"byebye",Toast.LENGTH_SHORT).show();
        DaoManager manager =  DaoManager.getInstance();
        manager.closeConnection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_book:
                return true;
            case R.id.import_book:
                switchFragment(lastfragment, 1);
                lastfragment = 1;
                break;
            case R.id.update_warn:
                 dialog = new Dialog(this, R.style.BottomDialog);
                 binding = UpdateRemindDialogBinding.inflate(getLayoutInflater());
                 dialog.setContentView(binding.getRoot());
                ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
                params.width = (int) (getResources().getDisplayMetrics().widthPixels*0.8);
                binding.getRoot().setLayoutParams(params);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                dialog.show();
                TextView del = binding.tvNo;
                del.setOnClickListener(v -> dialog.dismiss());
        }
        return super.onOptionsItemSelected(item);
    }
}