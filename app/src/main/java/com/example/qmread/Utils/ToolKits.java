package com.example.qmread.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ToolKits {
    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences("com.example.guide",Context.MODE_PRIVATE);

    }
    //设置共享参数-Boolean
    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sp = getSharedPreferences(context);
    //获取共享参数的编辑器
        SharedPreferences.Editor editor = sp.edit();
    //提交参数
    editor.putBoolean(key,value);
    editor.apply();
    }
    //获取共享参数-Boolean
    public static boolean getBoolean(Context context, String key, boolean defValue){
        return getSharedPreferences(context).getBoolean(key,defValue);
    }
    //保存共享参数-string
    public static void putString(Context context, String key, String value){
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.apply();
    }
    //获取共享参数-string
    public static String getString(Context context, String key, String defValue){
        return getSharedPreferences(context).getString(key,defValue);
    }
    //设置共享参数-int
    public static void putInteger(Context context,String key,Integer value){
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key,value);
        editor.apply();
    }
    //获取共享参数-int
    public static Integer getInteger(Context context, String key, Integer defValue){
        return getSharedPreferences(context).getInt(key,defValue);
    }

    public static void putLong(Context context,String key,Long value){
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key,value);
        editor.apply();
    }

    public static Long getLong(Context context,String key,Long defValue){
        return getSharedPreferences(context).getLong(key,defValue);
    }
    //设置共享参数-bitmap
    public static void putBitmap(Context context, String key, Bitmap value){
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            value.compress(Bitmap.CompressFormat.PNG, 100, bao);
            String imageBase64 = new String(Base64.encode(bao.toByteArray(),
                    Base64.DEFAULT));
            editor.putString(key, imageBase64);
            editor.apply();
    }
    //获取共享参数-Bitmap
    public static Bitmap getBitmap(Context context, String key, Bitmap defValue){
        String imageBase64 = getSharedPreferences(context).getString(key, "");
        if (TextUtils.isEmpty(imageBase64)) {
            return defValue;
        }
        byte[] base64Bytes = Base64.decode(imageBase64.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream bai = new ByteArrayInputStream(base64Bytes);
        Bitmap ret = BitmapFactory.decodeStream(bai);
        if (ret != null) {
            return ret;
        } else {
            return defValue;
        }
    }
    public static void putObject(Context ctx, String key, Object obj) {
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bao);
            oos.writeObject(obj);
            String objBase64 = new String(Base64.encode(bao.toByteArray(),Base64.DEFAULT));
            SharedPreferences sp = getSharedPreferences(ctx);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objBase64);
            editor.apply();
        } catch (Exception e) {
            Log.e("test", "saveObject error", e);
        }
    }

    public static Object getObject(Context ctx, String key) {
        try {
            String objBase64 = getSharedPreferences(ctx).getString(key, "");
            if (TextUtils.isEmpty(objBase64)) {
                return null;
            }
            byte[] base64 = Base64.decode(objBase64,Base64.DEFAULT);
            ByteArrayInputStream bai = new ByteArrayInputStream(base64);
            ObjectInputStream bis = new ObjectInputStream(bai);
            return bis.readObject();
        } catch (Exception e) {
            Log.e("test", "readObject error", e);
        }
        return null;
    }
}
