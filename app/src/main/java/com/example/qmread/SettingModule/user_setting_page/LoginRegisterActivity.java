package com.example.qmread.SettingModule.user_setting_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qmread.R;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.bean.RegisteredUserEntity;
import com.example.qmread.databinding.ActivityLoginRegisterBinding;
import com.example.qmread.greendao.gen.DaoUtilsStore;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class LoginRegisterActivity extends AppCompatActivity {

    ActivityLoginRegisterBinding binding;
    ImageView back;
    TextInputLayout til_phone,til_password;
    TextInputEditText inputEditText;
    String phone,password;
    MaterialButton login;
    boolean loginOnce;
    String mobile,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityLoginRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginOnce = ToolKits.getBoolean(this,"phoneCode",false);
        mobile = ToolKits.getString(this,"phone","");
        code = ToolKits.getString(this,"password","");
        back = binding.ivBackToFirst;
        back.setOnClickListener(v -> {
            finish();
        });
        til_phone = binding.tilPhone;
        til_password = binding.tilPwd;
        inputEditText = binding.tvPwd;

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = Objects.requireNonNull(til_phone.getEditText()).getText().toString();
                password = Objects.requireNonNull(inputEditText.getText()).toString();
                if(!phone.isEmpty() && !password.isEmpty()){
                    Log.i("TAG", "afterTextChanged: true-true-true");
                    login.setBackgroundColor(ContextCompat.getColor(LoginRegisterActivity.this, R.color.blue_register));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = Objects.requireNonNull(til_phone.getEditText()).getText().toString();
                password = Objects.requireNonNull(inputEditText.getText()).toString();
                if(!phone.isEmpty() && !password.isEmpty()){
                    Log.i("TAG", "afterTextChanged: true-true-true");
                    login.setBackgroundColor(ContextCompat.getColor(LoginRegisterActivity.this, R.color.blue_register));
                }
            }
        });
        login = binding.mbRegister;
        if(loginOnce){
            login.setText("登录");
            login.setOnClickListener(v -> {
                til_phone.setErrorEnabled(false);
                til_password.setErrorEnabled(false);
                if(validateAccount(phone)&&validatePassword(password) && phone.equals(mobile)&&password.equals(code)){
                    Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                    ToolKits.putBoolean(this,"register",true);
                    new Handler().postDelayed(this::finish,1500);
                }else {
                    Toast.makeText(this,"输入信息有误，请重新输入",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            login.setOnClickListener(v -> {
                til_phone.setErrorEnabled(false);
                til_password.setErrorEnabled(false);
                if(validateAccount(phone)&&validatePassword(password)){
                    saveUserInfo();
                    Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                    ToolKits.putBoolean(this,"register",true);
                    ToolKits.putBoolean(this,"phoneCode",true);
                    ToolKits.putString(this,"phone",phone);
                    ToolKits.putString(this,"password",password);
                }
                new Handler().postDelayed(this::finish,1500);
            });
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if(isShouldHideKeyboard(v,ev)){
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 验证用户名
     * @param account phone
     * @return boolean
     */
    private boolean validateAccount(String account){
        if(account.isEmpty()){
            showError(til_phone,"手机号不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     * @param password text
     * @return boolean
     */
    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            showError(til_password,"密码不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() > 12) {
            showError(til_password,"密码长度为6-12位");
            return false;
        }

        return true;
    }
    /**
     * 显示错误提示，并获取焦点
     * @param textInputLayout text
     * @param error error
     */
    private void showError(TextInputLayout textInputLayout,String error){
        textInputLayout.setError(error);
        Objects.requireNonNull(textInputLayout.getEditText()).setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

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
    private void hideKeyboard(IBinder token) {
        til_phone.clearFocus();
        til_password.clearFocus();
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
        Bitmap port = BitmapFactory.decodeResource(getResources(),R.mipmap.head);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        port.compress(Bitmap.CompressFormat.PNG,100,os);
        String imageBase64 = new String(Base64.encode(os.toByteArray(),
                Base64.DEFAULT));
        userEntity.setRegisterUserId(1);
        userEntity.setCreatedTime(new Date());
        userEntity.setHead(imageBase64);
        userEntity.setNickName("努力上进的小松鼠");
        userEntity.setSex("无");
        userEntity.setUserName("用户001");
        userEntity.setPhone(phone);
        userEntity.setPassword(password);
        DaoUtilsStore store = new DaoUtilsStore();
        store.getRegisteredUserUtils().insert(userEntity);
    }
}