package com.example.qmread.SettingModule.user_setting_page;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qmread.Adapter.ImageAdapter;
import com.example.qmread.R;
import com.example.qmread.Utils.FileUtil;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToastUtils;
import com.example.qmread.databinding.ActivityUploadOpinionBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class uploadOpinionActivity extends AppCompatActivity {

    ActivityUploadOpinionBinding binding;
    private final int maxNum = 300;
    private final int CHOOSE_PHOTO = 2;
    ImageView album,back;
    MaterialButton save;
    private final ArrayList<Drawable> images = new ArrayList<>();
    RecyclerView addImage;
    ImageAdapter adapter;
    TextView count;
    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
        if (result != null) {
            String path = FileUtil.getRealPath(result,this);
            Bitmap bitmap = FileUtil.loadLocalPicture(path);
            Drawable drawable = new BitmapDrawable(getResources(),bitmap);
            images.add(images.size()-1,drawable);
            adapter.notifyDataSetChanged();
            count.setText(String.format(Locale.getDefault(),"%d/4",images.size()-1));
        } else {
            ToastUtils.showToast(this, "已取消选择");
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityUploadOpinionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        count = binding.tvCount;
        AppCompatEditText editText = binding.etInput;
        editText.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = maxNum - s.length();
                TextView maxWord = binding.tvMaxWord;
                maxWord.setText(String.format(getResources().getString(R.string.record), number));
                int selectionStart = editText.getSelectionStart();
                int selectionEnd = editText.getSelectionEnd();
                if (wordNum.length() > maxNum) {
                    //删除多余字符
                    s.delete(selectionStart - 1, selectionEnd);
                    editText.setText(s);
                    editText.setSelection(selectionEnd);//设置光标在最后
                }
            }
        });
        images.add(ResourcesCompat.getDrawable(getResources(),R.mipmap.insert_image,null));
        addImage = binding.rvImage;
        GridLayoutManager manager = new GridLayoutManager(this,3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        addImage.setLayoutManager(manager);
        adapter = new ImageAdapter(this,images);
        addImage.setAdapter(adapter);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (ContextCompat.checkSelfPermission(uploadOpinionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(uploadOpinionActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, CHOOSE_PHOTO);
                } else {
                    if(images.size()>=5){
                        Toast.makeText(uploadOpinionActivity.this,"已达上限",Toast.LENGTH_SHORT).show();
                    }else {
                        launcher.launch("image/*");
                    }
                }
            }

            @Override
            public void delete(boolean act,int position) {
                if(act && images.size()>1){
                    images.remove(position);
                    adapter.notifyDataSetChanged();
                    count.setText(String.format(Locale.getDefault(),"%d/4",images.size()-1));
                }
            }
        });
        back = binding.ivBackToSet;
        back.setOnClickListener(v -> {
            finish();
        });
        save = binding.btSave;
        save.setOnClickListener(v -> {
            if(Objects.requireNonNull(editText.getText()).length()>10){
                Toast.makeText(this,"提交成功，感谢反馈",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(this::finish,1500);
            }else {
                Toast.makeText(this,"未填写完整，无法提交",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "onResume: up=up=up=up=up");
    }
}