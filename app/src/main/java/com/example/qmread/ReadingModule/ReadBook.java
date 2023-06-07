package com.example.qmread.ReadingModule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.service.autofill.Dataset;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.qmread.Adapter.CommentAdapter;
import com.example.qmread.Adapter.ReadPageAdapter;
import com.example.qmread.MainViewModule.BookShelfFragment;
import com.example.qmread.R;
import com.example.qmread.SettingModule.MoreSettingActivity;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.bean.BookEntity;
import com.example.qmread.bean.BookmarkEntity;
import com.example.qmread.bean.ChapterEntity;
import com.example.qmread.bean.CommentEntity;
import com.example.qmread.bean.CommentsEntity;
import com.example.qmread.bean.ReadingStatusEntity;
import com.example.qmread.databinding.ActivityReadBookBinding;
import com.example.qmread.databinding.CommentItemBinding;
import com.example.qmread.databinding.CommentPageBinding;
import com.example.qmread.greendao.gen.DaoUtilsStore;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ReadBook extends AppCompatActivity {
    ActivityReadBookBinding bookBinding;
    CommentPageBinding commentPageBinding;
    RecyclerView comment;
    int size = 19;
    boolean ifFull = false;
    boolean getPermission = false;
    ActionBar bar;
    Window window;
    ReadPageAdapter pageAdapter;
    private int recLen = 0;
    TextView chapter;
    ViewPager2 vp;
    RelativeLayout r ;
    MaterialButton b1,b2,b3,b4,b5;
    LinearLayout top;
    RelativeLayout bottom;
    int position,location;
    String name;
    List<CommentEntity> entities = new ArrayList<>();
    Dialog dialog;
    ImageView con;
    String inputComment;
    Bitmap port,head;
    String nick,time;
    List<String> article;
    Integer markNum = 0;
    DaoUtilsStore store;
    BookEntity bookEntity;
    CommentAdapter adapter;
    boolean isNight = false;

    ActivityResultLauncher<String> permission = registerForActivityResult(new ActivityResultContracts.RequestPermission(),result -> {
        if(result){
            getPermission = true;
        }else{
            ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_SETTINGS);
            requestPermissions(new String[] {Manifest.permission.WRITE_SETTINGS},0);
        }
    });
    ViewPager2.PageTransformer mAnimator = (page, position) -> {
        float absPos = Math.abs(position);
        float scaleX ;
        float scaleY;
        if (absPos > 1){
            scaleX=   0F;
            scaleY=   0F;

        }else{
            scaleX= 1 - absPos ;
            scaleY= 1 - absPos ;
        }
        page.setScaleX(scaleX);
        page.setScaleY(scaleY);
    };
    private int theme = R.style.AppTheme;
    private BatteryLevelReceiver receiver;
    private int backgroundColor = R.color.book;
    private final int width = R.dimen.dp_1;
    Handler handler;

    static boolean MIUISetStatusBarLightMode(Activity activity) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag;
            @SuppressLint("PrivateApi") Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkModeFlag, darkModeFlag);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static boolean FlymeSetStatusBarLightMode(Activity activity) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            value |= bit;
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = getWindow();
        backgroundColor = ToolKits.getInteger(this,"backgroundColor",R.color.book);
        if(savedInstanceState != null){
            Log.i("TAG", "onCreate: "+theme+"----"+backgroundColor);
            getPermission = savedInstanceState.getBoolean("permission");
            theme = savedInstanceState.getInt("theme");
            setTheme(theme);
            backgroundColor = savedInstanceState.getInt("background");
        }
        setStatusBarLightMode(this,backgroundColor);
        bookBinding = ActivityReadBookBinding.inflate(getLayoutInflater());
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(bookBinding.getRoot());
        store = new DaoUtilsStore();
         r = bookBinding.rlMoreSet;
         b1 = bookBinding.btBag1;
         b2 = bookBinding.btBag2;
         b3 = bookBinding.btBag3;
         b4 = bookBinding.btBag4;
         b5 = bookBinding.btBag5;
         top = bookBinding.vTop;
         bottom = bookBinding.rlBottom;
         con = bookBinding.ivCom;
         con.setOnClickListener(v -> {
             if(dialog.isShowing()){
                 dialog.dismiss();
             }else {
                 dialog.show();
             }
         });
        top.setBackgroundColor(ContextCompat.getColor(this,backgroundColor));
        bottom.setBackgroundColor(ContextCompat.getColor(this,backgroundColor));
        r.setBackgroundColor(ContextCompat.getColor(this,backgroundColor));
        b1.setStrokeWidth((int) getResources().getDimension(R.dimen.dp_1));
        b1.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.black)));
        //计时
        handler = new Handler();
        countTime();
        size = ToolKits.getInteger(this,"fontSize",19);
         vp = bookBinding.vpRead;
        RelativeLayout rl = bookBinding.rlMoreSet;
        LinearLayout layout4 = bookBinding.vTop;
        RelativeLayout r2 = bookBinding.rlBottom;
        RelativeLayout r3 = bookBinding.rlScreen;
        ImageView setButton = bookBinding.ibSet;
        ImageView back = bookBinding.ivBack;
        back.setOnClickListener(v -> {
           finish();
        });
        setButton.setOnClickListener(v ->{
           // permission.launch(Manifest.permission.WRITE_SETTINGS);
            if(rl.getVisibility() == View.GONE){
                rl.setVisibility(View.VISIBLE);
            }else if(rl.getVisibility() == View.VISIBLE){
                rl.setVisibility(View.GONE);
            }
        });
        Intent intent = getIntent();
        name = intent.getStringExtra("BookName");
        location = intent.getIntExtra("position",0);
        //List<String> article = TxtFileUtil.cutCatlog(getBookInfo(name));
        chapter = bookBinding.tvChapter;
        chapter.setText(name);
        article = getBookInfo(name);
        //Log.i("TAG", "onCreate: ---++++"+ article);
        //页面滑动
        pageAdapter = new ReadPageAdapter(article,size,this);
        position = ToolKits.getInteger(this,name,0);
        vp.setAdapter(pageAdapter);
        vp.setUserInputEnabled(true);
        vp.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vp.setCurrentItem(position,true);
        vp.setPageTransformer(mAnimator);
        vp.setBackgroundColor(ContextCompat.getColor(this,backgroundColor));
        vp.setOnLongClickListener(v -> {
            dialog.show();
            return false;
        });
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
               // Log.e("TAG", "onPageScrolled: "+position+"--->"+positionOffset+"--->"+positionOffsetPixels );
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.e("page", "onPageSelected: "+position );
                ToolKits.putInteger(ReadBook.this,name,position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                Log.e("scroll", "onPageScrollStateChanged: "+state );
            }
        });

        if(rl.getVisibility() == View.VISIBLE){
        TextView set = bookBinding.tvMoreSetting;
        set.setOnTouchListener((v, event) -> {
            startActivity(new Intent(this,MoreSettingActivity.class));
            return false;
        });
    }
        r3.setOnClickListener(v -> {
            if(!ifFull){
                layout4.setVisibility(View.INVISIBLE);
                r2.setVisibility(View.INVISIBLE);
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.setStatusBarColor(Color.TRANSPARENT);
                ifFull = true;
            }else{
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                layout4.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                ifFull = false;
            }
        });
        //转到目录
        ImageView category = bookBinding.ibCatalogue;
        category.setOnClickListener(v -> {
            Intent intent1 = new Intent(this,Catalogue.class);
            intent1.putExtra("bookName",name);
            startActivity(intent1);
        });
        //夜间,日间模式切换
        ImageView moon = bookBinding.ibMoon;
        moon.setOnClickListener(v -> {
            if(isNight){
                backgroundColor = R.color.book;
                changed(backgroundColor);
                isNight = false;
            }else {
                backgroundColor = R.color.night;
                changed(backgroundColor);
                isNight = true;
            }
//            theme = (theme == R.style.AppTheme) ? R.style.NightAppTheme : R.style.AppTheme;
//            setTheme(theme);
//            ReadBook.this.recreate();
//            bar = getSupportActionBar();
//            if(bar!= null){
//                bar.hide();
//            }
        });
         changeFontSize();//修改文本字体大小
         changeBackgroundColor();//修改阅读页面背景
         adjustLight();//屏幕亮度
        openCommentList();
        int selfPermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_SETTINGS);
        if(selfPermission == PackageManager.PERMISSION_GRANTED){
            Log.i("TAG", "onCreate: 有权限了");
        }else {
            if( !ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_SETTINGS)){
                Log.i("permission", "onCreate: ---090909");
            }
         ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_SETTINGS,},0);
        }
        ImageView menu = bookBinding.ivMenu;
        LinearLayout mark = bookBinding.llBookmark;
        menu.setOnClickListener(v -> {
            if(mark.getVisibility() == View.VISIBLE){
                mark.setVisibility(View.GONE);
            }else {
                mark.setVisibility(View.VISIBLE);
            }
        });
        TextView addMark = bookBinding.tvAddMark;
        addMark.setOnClickListener(v -> {
            Toast.makeText(this,"添加书签成功",Toast.LENGTH_SHORT).show();
            markNum++;
            ToolKits.putInteger(this,"bookmarkNum",markNum);
            saveBookmark();
        });
        //Settings.System.canWrite(MainActivity.this)检测是否拥有写入系统权限的权限
        if (!Settings.System.canWrite(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("我们的应用需要您授权\"修改系统设置\"的权限,请点击\"设置\"确认开启");

            // 拒绝, 退出应用
            builder.setNegativeButton(R.string.cancel,
                    (dialog, which) -> finish());

            builder.setPositiveButton(R.string.setting,
                    (dialog, which) -> {
                        Intent intent1 = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent1, 0);
                    });

            builder.setCancelable(false);
            builder.show();
        }
    }

    private void openCommentList() {
        port = BitmapFactory.decodeResource(getResources(),R.mipmap.head);
        head = ToolKits.getBitmap(this,"photo",port);
        nick = ToolKits.getString(this,"nickName","用户001");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault());
        time = dateFormat.format(new Date());
        CommentEntity item = new CommentEntity(head,nick,time,"不一样的故事，不一样的人");
        entities.add(item);
        dialog = new Dialog(this,R.style.BottomDialog);
        commentPageBinding = CommentPageBinding.inflate(getLayoutInflater());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.setContentView(commentPageBinding.getRoot());
        ViewGroup.LayoutParams params = commentPageBinding.getRoot().getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.height = (int) (getResources().getDisplayMetrics().heightPixels*0.8);
        commentPageBinding.getRoot().setLayoutParams(params);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        adapter = new CommentAdapter(entities, 0);
        comment = commentPageBinding.rvComment;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        comment.setLayoutManager(layoutManager);
        comment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ImageView close = commentPageBinding.ivClose;
        close.setOnClickListener(v -> {
            dialog.cancel();
        });
        TextInputEditText ed = commentPageBinding.edInput;
        ed.setOnEditorActionListener((v,actionId,event)->{
            if(actionId == EditorInfo.IME_ACTION_DONE){
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null) {
                    im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                ed.clearFocus();
                inputComment =  Objects.requireNonNull(ed.getText()).toString();
                ToolKits.putString(this,"comment",inputComment);
                CommentEntity entity = new CommentEntity(head,nick,time,inputComment);
                entities.add(entity);
//                adapter = new CommentAdapter(entities,0);
//                comment.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                saveComment();

                return true;
            }
            return false;
        });
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private List<String> getBookInfo(String name){
        //String newName;
//        if(name.equals("大奉打更人")){
//            newName = "1536"+name+".txt";
//        }else if (name.equals("《原神：开局撞晕了荧")){
//            newName = "1852"+name+".txt";
//        }else {
            //newName = name+".txt";
//        }
        List<String> novel = new ArrayList<>();
        try {
            FileInputStream stream = this.openFileInput(name);
            byte[] buffer = new byte[5040];
            while(stream.read(buffer) != -1){
                String temp = new String(buffer,0,5040);
                novel.add(temp);
                //stringBuilder.append(new String(buffer,0,5440));
            }
            stream.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        Log.i("TAG", "getBookInfo: ");
        return novel;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("TAG", "onRequestPermissionsResult: 232323232323");
            }else {
                Log.i("TAG", "onRequestPermissionsResult: 拒绝了");
            }
        }
    }

    @Override
    protected void onStart() {
        receiver = new BatteryLevelReceiver();
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
        Log.i("TAG", "onDestroy: "+recLen);
        List<BookEntity> bookEntities = store.getUserDaoUtils().queryAll();
        BookEntity changeItem = bookEntities.get(location);
        changeItem.setSecondPosition(recLen);
        store.getUserDaoUtils().update(changeItem);
        //saveStatus();
        BookShelfFragment bookShelfFragment = new BookShelfFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("readTime",recLen);
        bookShelfFragment.setArguments(bundle);
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme",theme);
        outState.putInt("background",backgroundColor);
        outState.putBoolean("permission",getPermission);
        outState.putInt("size",size);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        theme = savedInstanceState.getInt("theme");
        backgroundColor = savedInstanceState.getInt("background");
        getPermission = savedInstanceState.getBoolean("permission");
        size = savedInstanceState.getInt("size");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LinearLayout layout4 = bookBinding.vTop;
        RelativeLayout r2 = bookBinding.rlBottom;
        RelativeLayout rl = bookBinding.rlMoreSet;
        float X = ev.getX();
        float Y = ev.getY();
        float moveX = 0, moveY = 0;
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        long currentTime = 0;
        SimpleDateFormat dateformat = null;
        dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentTime = System.currentTimeMillis();
                String one = dateformat.format(currentTime);
                Log.i("tititiititi", "dispatchTouchEvent: "+one);
                break;
            case MotionEvent.ACTION_UP:
                long moveTime = System.currentTimeMillis() - currentTime;
                String dateStr = dateformat.format(moveTime);
                Log.i("tititiititi", "dispatchTouchEvent: "+dateStr);
                    if(X>0.3*width && X< 0.7*width&& Y>0.2*height && Y< 0.8*height)
                    if (!ifFull) {
                        layout4.setVisibility(View.GONE);
                        r2.setVisibility(View.GONE);
                        rl.setVisibility(View.GONE);
                        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        //window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        window.setStatusBarColor(ContextCompat.getColor(this,backgroundColor));
                        ifFull = true;
                    } else {
                        layout4.setVisibility(View.VISIBLE);
                        r2.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        window.setStatusBarColor(ContextCompat.getColor(this,backgroundColor));
                        ifFull = false;
                    }
            default:break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void countTime(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recLen ++;
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable,1000);
        Log.i("TAG", "run: "+recLen);
    }

    private void changeFontSize(){
        TextView decline = bookBinding.tvDecline;
        TextView add = bookBinding.tvGrow;
        TextView fontSize = bookBinding.tvFontSize;
                decline.setOnClickListener(v -> {
                    assert size >= 15;
                    if(size >15){
                        if(size == 41){
                            add.setTextColor(ContextCompat.getColor(this,R.color.green_4));
                        }
                        size -= 2;
                        fontSize.setText(String.valueOf(size));
                        decline.setTextColor(ContextCompat.getColor(this,R.color.green_4));
                    }
                    if(size == 15){
                        decline.setTextColor(ContextCompat.getColor(this,R.color.font1));
                    }
                    ToolKits.putInteger(this,"fontSize",size);

                    pageAdapter.notifyDataSetChanged();
                });
        add.setOnClickListener(v -> {
            assert size <=41;
                if(size==15){
                    decline.setTextColor(ContextCompat.getColor(this,R.color.green_4));
                }
                size += 2;
                if(size<= 41){
                    fontSize.setText(String.valueOf(size));
                }
                add.setTextColor(ContextCompat.getColor(this,R.color.green_4));
            if(size == 41){
                add.setTextColor(ContextCompat.getColor(this,R.color.font1));
            }
            ToolKits.putInteger(this,"fontSize",size);
            pageAdapter = new ReadPageAdapter(article,size,this);
            vp.setAdapter(pageAdapter);
            pageAdapter.notifyDataSetChanged();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeBackgroundColor(){
        b1.setOnClickListener(v -> {
            backgroundColor = R.color.book;
            changed(backgroundColor);
        });
        b2.setOnClickListener(v -> {
            backgroundColor = R.color.grey_b;
            changed(backgroundColor);
        });
        b3.setOnClickListener(v -> {
            backgroundColor = R.color.green_bk;
            changed(backgroundColor);
        });
        b4.setOnClickListener(v -> {
            backgroundColor = R.color.back_2;
            changed(backgroundColor);
        });
        b5.setOnClickListener(v -> {
            backgroundColor = R.color.back_1;
            changed(backgroundColor);
        });
    }

    private void changed(int color){
        ToolKits.putInteger(this,"backgroundColor",color);
        vp.setBackgroundColor(ContextCompat.getColor(this,backgroundColor));
        top.setBackgroundColor(ContextCompat.getColor(this,backgroundColor));
        bottom.setBackgroundColor(ContextCompat.getColor(this,backgroundColor));
        r.setBackgroundColor(ContextCompat.getColor(this,backgroundColor));
        b1.setStrokeWidth((int) getResources().getDimension(R.dimen.dp_1));
        b1.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.black)));
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(ContextCompat.getColor(this,backgroundColor));
    }
//调节亮度
    private void adjustLight() {
        AppCompatSeekBar seekBar = bookBinding.sbPro;
        int currentPro = getScreenBrightness();
        seekBar.setProgress(currentPro);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("TAG", "onProgressChanged: --"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setWindowBrightness((float) seekBar.getProgress());
            }
        });
    }

    private void saveScreenBrightness(int value) {
        Log.i("TAG", "saveScreenBrightness: "+value);
        if (value < 1) {
            value = 1;
        }
        // 异常处理
        if (value > 255) {

            value = 255;

        }
        ContentResolver contentResolver = this.getContentResolver();
        Uri uri = android.provider.Settings.System
                .getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, value);
        contentResolver.notifyChange(uri,null);
    }

    //设置屏幕亮度调节模式
    private void setScreenManualMode(){
        ContentResolver resolver = this.getContentResolver();
        try{
            int mode = Settings.System.getInt(resolver,Settings.System.SCREEN_BRIGHTNESS_MODE);
            if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC){
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE,Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        }catch (Settings.SettingNotFoundException e){
            e.printStackTrace();
        }
    }

    private int getScreenBrightness() {
        ContentResolver contentResolver = this.getContentResolver();
        int systemBrightness = 0;
                 try {
                        systemBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                return systemBrightness;
    }

    private void setWindowBrightness(float brightness) {
        setScreenManualMode();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
                         lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                    } else {
                        lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
                    }
        window.setAttributes(lp);
        saveScreenBrightness(getScreenBrightness());
    }

    public void setStatusBarLightMode(Activity activity, int color) {
        //判断是否为小米或魅族手机，如果是则将状态栏文字改为黑色
//        if (MIUISetStatusBarLightMode(activity) || FlymeSetStatusBarLightMode(activity)) {
//            //设置状态栏为指定颜色
//            //5.0
//            activity.getWindow().setStatusBarColor(color);
//        } else {
            //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(getResources().getColor(backgroundColor));
            //fitsSystemWindow 为 false, 不预留系统栏位置.
            ViewGroup mContentView = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, true);
                ViewCompat.requestApplyInsets(mChildView);
            }

    }

    private class BatteryLevelReceiver extends BroadcastReceiver{
        TextView battery = bookBinding.tvBattery;
        @Override
        public void onReceive(Context context, Intent intent) {
            //当前电量
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            //获取总电量
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            int raw = -1;
            if(level >= 0 && scale > 0){
                raw = (level*100)/scale;
            }
            battery.setText(MessageFormat.format("{0}{1}", raw, getResources().getString(R.string.percent)));
        }
    }

    private void saveBookmark(){
        BookmarkEntity bookmarkEntity = new BookmarkEntity();
       List<BookEntity> books = store.getUserDaoUtils().queryAll();
       List<ChapterEntity> chapters = store.getChapterDaoUtils().queryAll();
       bookmarkEntity.setBookmarkId(markNum);
       bookmarkEntity.setBookId(books.get(location).getId());
       bookmarkEntity.setChapterId(ToolKits.getInteger(this,name,0));
       bookmarkEntity.setBeginParagraph(name);
       bookmarkEntity.setSetTime(new Date());
       bookmarkEntity.setChangeTime(new Date());
       bookmarkEntity.setBookEntity(books.get(location));
       store.getBookmarkDaoUtils().insert(bookmarkEntity);
    }

    private void saveComment(){
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setId(entities.size());
        commentsEntity.setBookEntity(store.getUserDaoUtils().queryAll().get(location));
        commentsEntity.setBookId(store.getUserDaoUtils().queryAll().get(location).getId());
        commentsEntity.setCreatedTime(new Date());
        commentsEntity.setCurrent(0);
        commentsEntity.setDetail(inputComment);
        commentsEntity.setUserEntity(store.getRegisteredUserUtils().queryAll().get(0));
        commentsEntity.setRegisterId(store.getRegisteredUserUtils().queryAll().get(0).getRegisterUserId());
        store.getCommonDaoUtils().insert(commentsEntity);
    }

    private void saveStatus(){
        ReadingStatusEntity statusEntity = new ReadingStatusEntity();
        statusEntity.setStatusId(location);
        statusEntity.setBookEntity(store.getUserDaoUtils().queryAll().get(location));
        statusEntity.setBookId(store.getUserDaoUtils().queryAll().get(location).getId());
        statusEntity.setOrdinaryUserEntity(store.getOrdinaryUserDaoUtils().queryAll().get(0));
        statusEntity.setRegisteredUserEntity(store.getRegisteredUserUtils().queryAll().get(0));
        statusEntity.setUserId(store.getRegisteredUserUtils().queryAll().get(0).getRegisterUserId());
        statusEntity.setReadingDuration(recLen);
        statusEntity.setReadingLocation(ToolKits.getInteger(this,"position",0));
        store.getReadingStatusDaoUtils().insert(statusEntity);
    }
}