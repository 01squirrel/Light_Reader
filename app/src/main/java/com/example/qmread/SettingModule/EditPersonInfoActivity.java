package com.example.qmread.SettingModule;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qmread.R;
import com.example.qmread.Utils.FileUtil;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.bean.RegisteredUserEntity;
import com.example.qmread.databinding.ActivityEditPersonInfoBinding;
import com.example.qmread.databinding.DialogBinding;
import com.example.qmread.databinding.SexDialogBinding;
import com.example.qmread.greendao.gen.DaoUtilsStore;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class EditPersonInfoActivity extends AppCompatActivity {

    ActivityEditPersonInfoBinding binding;
    String gender;
    String nickName;
    String signature;
    String birthDay;
    TextInputEditText editName, editSign;
    MaterialButton button;
    ImageView back;
    RoundedImageView head;
    DialogBinding dialogBinding;
    boolean showOrDismiss = false;
    TextView editText,editBirth;
    SexDialogBinding sexDialogBinding;
    Bitmap bitmap;
    boolean changeHead = false;
    String imageBase64;

    ActivityResultLauncher<Void> getHeadUrl = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
        if(result!=null){
            Log.i("TAG", ": "+result);
            head.setImageBitmap(result);
            ToolKits.putBitmap(this,"photo",result);
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"已取消选择",Toast.LENGTH_SHORT).show();
        }
    });

    ActivityResultLauncher<String> openAlbum = registerForActivityResult(new ActivityResultContracts.GetContent(),result -> {
        if(result != null){
          String path = FileUtil.getRealPath(result,this);
          bitmap = FileUtil.loadLocalPicture(path);
          head.setImageBitmap(bitmap);
            assert bitmap != null;
            ToolKits.putBitmap(this,"photo",bitmap);
            changeHead = true;
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"已取消选择",Toast.LENGTH_SHORT).show();
        }
    });

    ActivityResultLauncher<String> getPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(),result -> {
       if(result){
           showOrDismiss = true;
       }else{
           Log.i("TAG", ": not access!");
        }
    });

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityEditPersonInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editText = binding.tvSex;
        editText.setText(ToolKits.getString(this,"gender",""));
        RelativeLayout sex = binding.rlSexSelect;
        sex.setOnClickListener(v -> {
            showPopMenu();
        });
        editBirth = binding.tvBirth;
        editBirth.setText(ToolKits.getString(this,"birth",""));
        RelativeLayout birth = binding.rlBirth;
        birth.setOnClickListener(v -> {
            Calendar calender = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) ->{
                String newMonth,newDay;
                if(month<9){
                    newMonth = "0"+(month+1);
                }else {
                    newMonth = String.valueOf(month+1);
                }
                if(dayOfMonth <10){
                    newDay = "0" + dayOfMonth;
                }else{
                    newDay = String.valueOf(dayOfMonth);
                }
                birthDay = newMonth  + "-" + newDay ;
                editBirth.setText(birthDay);
                ToolKits.putString(this,"birth",birthDay);
            },
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });
        editName = binding.editName;
        editName.setText(ToolKits.getString(this,"nickName",""));
        editName.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                nickName = Objects.requireNonNull(editName.getText()).toString();
                ToolKits.putString(this,"nickName",nickName);
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null) {
                    im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            return false;
        });
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nickName = Objects.requireNonNull(editName.getText()).toString();
                ToolKits.putString(EditPersonInfoActivity.this,"nickName",nickName);
            }
        });
        editSign = binding.editSign;
        editSign.setOnEditorActionListener((v,actionId,event)->{
            if(actionId == EditorInfo.IME_ACTION_DONE){
                signature =  Objects.requireNonNull(editSign.getText()).toString();
                ToolKits.putString(this,"signature",signature);
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null) {
                    im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            return false;
        });
        button = binding.btSave;
        button.setOnClickListener(v -> {

            finish();
        });
        back = binding.ivBackOnce;
        back.setOnClickListener(v -> {
            finish();
        });
        head = binding.rivHead;
        Bitmap port = BitmapFactory.decodeResource(getResources(),R.mipmap.head);
        Bitmap source = ToolKits.getBitmap(this,"photo",port);
        head.setImageBitmap(source);
        Dialog bottomDialog = new Dialog(this,R.style.BottomDialog);
        dialogBinding = DialogBinding.inflate(getLayoutInflater());
        bottomDialog.setContentView(dialogBinding.getRoot());
        ViewGroup.LayoutParams params = dialogBinding.getRoot().getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels;
        dialogBinding.getRoot().setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        head.setOnClickListener(v -> {
            if(!showOrDismiss){
                bottomDialog.show();
            }
        });
        dialogBinding.tvPhoto.setOnClickListener(v -> {
            getHeadUrl.launch(null);
            bottomDialog.dismiss();
        });
        dialogBinding.tvAlbum.setOnClickListener(v -> {
            openAlbum.launch("image/*");
            bottomDialog.dismiss();
        });
        dialogBinding.tvCancel.setOnClickListener(v -> {
            bottomDialog.dismiss();
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.CAMERA) != PackageManager
//                    .PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new
//                        String[]{Manifest.permission.CAMERA }, 1);
//            } else {
//
//            }
//        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //如果是点击事件，获取点击的view，并判断是否要收起键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //获取目前得到焦点的view
            View v = getCurrentFocus();
            //判断是否要收起并进行处理
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy: bye-bye-bye-bye");
    }

    private void showPopMenu() {
        Dialog sexDialog = new Dialog(this,R.style.BottomDialog);
        sexDialogBinding = SexDialogBinding.inflate(getLayoutInflater());
        sexDialog.setContentView(sexDialogBinding.getRoot());
        ViewGroup.LayoutParams params = sexDialogBinding.getRoot().getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels;
        sexDialogBinding.getRoot().setLayoutParams(params);
        sexDialog.getWindow().setGravity(Gravity.BOTTOM);
        sexDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        sexDialog.show();
        sexDialogBinding.tvMan.setOnClickListener(v -> {
            editText.setText("男");
            gender = editText.getText().toString();
            ToolKits.putString(this,"gender",gender);
            sexDialog.dismiss();
        });
        sexDialogBinding.tvWomen.setOnClickListener(v -> {
            editText.setText("女");
            gender = editText.getText().toString();
            ToolKits.putString(this,"gender",gender);
            sexDialog.dismiss();
        });
        sexDialogBinding.tvCancel.setOnClickListener(v -> {
            sexDialog.dismiss();
        });
    }
    //判断是否要收起键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        //如果目前得到焦点的这个view是editText的话进行判断点击的位置
        if (v instanceof TextInputEditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            Log.i("TAG", "isShouldHideKeyboard: "+ Arrays.toString(l));
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上
        return false;
    }

    //隐藏软键盘并让editText失去焦点
    private void hideKeyboard(IBinder token) {
        editName.clearFocus();
        editSign.clearFocus();
        if (token != null) {
            //这里先获取InputMethodManager再调用他的方法来关闭软键盘
            //InputMethodManager就是一个管理窗口输入的manager
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void saveUserInfo(){
        RegisteredUserEntity userEntity = new RegisteredUserEntity();
        //Bitmap port = BitmapFactory.decodeResource(getResources(),R.mipmap.head);
        if(changeHead){
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
            imageBase64 = new String(Base64.encode(os.toByteArray(),
                    Base64.DEFAULT));
            userEntity.setHead(imageBase64);
        }
        userEntity.setRegisterUserId(1);
        userEntity.setCreatedTime(new Date());
        userEntity.setNickName(nickName);
        userEntity.setSex(gender);
        DaoUtilsStore store = new DaoUtilsStore();
        store.getRegisteredUserUtils().update(userEntity);
    }
}