package com.zhumei.baselib.utils.useing.cache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.BaseHelper;
import com.zhumei.baselib.utils.JsonUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;


/**
 * SharedPreferences的二级缓存的工具类，推荐存string
 */
public class CacheUtils {

    private static final String SP_CONFIG = AppConstants.DefaultSetting.SET_DISK_CACHE_STR_FLAG;
    private static final String TAG = "CacheUtils";

    /**
     *  存缓存和sp字符串值
     */
    @SuppressLint("ApplySharedPref")
    public static void putString(String key, String value) {
        SharedPreferences sp = BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        MemoryCacheImpl.getInstance().put(key, value);
        editor.commit();
    }

    /**
     *  取缓存或sp字符串值
     */
    public static String getString(String key) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.E(TAG , "can't get value because key is empty");
            return null;
        }
        String value;
        value = MemoryCacheImpl.getInstance().getString(key);
        if (TextUtils.isEmpty(value)) {
            SharedPreferences sp =BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
            String string = sp.getString(key, "");
            if (TextUtils.isEmpty(string)) {
                LogUtils.e(TAG ,"value is empty");

            } else {
                MemoryCacheImpl.getInstance().put(key, string);
            }
            return string;
        } else {
            return value;
        }
    }

    /**
     * 取缓存或sp字符串值
     * 如果设置默认值则取默认值
     */
    public static String getString(String key , String defValue) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG ,"can't get value because key is empty");
            return null;
        }
        String value;
        value = MemoryCacheImpl.getInstance().getString(key);
        if (TextUtils.isEmpty(value)) {
            SharedPreferences sp =BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
            String string = sp.getString(key, defValue);
            if (TextUtils.isEmpty(string)) {
                LogUtils.e(TAG ,"value is empty");

            } else {
                MemoryCacheImpl.getInstance().put(key, string);
            }
            return string;
        } else {
            return value;
        }
    }

    /**
     *  存sp和缓存 int值
     */
    @SuppressLint("ApplySharedPref")
    public static void putInt(String key , int value){
        SharedPreferences sp = BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        MemoryCacheImpl.getInstance().put(key, String.valueOf(value));
        editor.commit();
    }

    /**
     *  取缓存或者sp的int值
     */
    public static int getInt(String key){
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG ,"can't get value because key is empty");
            return 0;
        }
        int value;
        String valueStr = MemoryCacheImpl.getInstance().getString(key);
        if (TextUtils.isEmpty(valueStr)){
            SharedPreferences sp = BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
            value = sp.getInt(key,0);
            MemoryCacheImpl.getInstance().put(key, String.valueOf(value));
            return value;
        }else {
            value = Integer.parseInt(valueStr);
            return value;
        }
    }

    /**
     *  取缓存或者sp的int值
     *  如果设置默认值则取默认值
     */
    public static int getInt(String key , int defValue){
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG ,"can't get value because key is empty");
            return 0;
        }
        int value;
        String valueStr = MemoryCacheImpl.getInstance().getString(key);
        if (TextUtils.isEmpty(valueStr)){
            SharedPreferences sp = BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
            value = sp.getInt(key , defValue);
            MemoryCacheImpl.getInstance().put(key, String.valueOf(value));
            return value;
        }else {
            value = Integer.parseInt(valueStr);
            return value;
        }
    }

    /**
     *  存缓存和sp 布尔值
     */
    @SuppressLint("ApplySharedPref")
    public static void putBoolean(String key, boolean value){
        SharedPreferences sp =BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key,value);
        MemoryCacheImpl.getInstance().put(key, String.valueOf(value));
        edit.commit();
    }

    /**
     *  取缓存或者sp 布尔值
     */
    public static boolean getBoolean(String key){
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG ,"can't get value because key is empty");
            return false;
        }
        String string = MemoryCacheImpl.getInstance().getString(key);
        if (TextUtils.isEmpty(string)){
            SharedPreferences sp = BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
            boolean value = sp.getBoolean(key, false);
            MemoryCacheImpl.getInstance().put(key, String.valueOf(value));
            return value;
        }else {
            return Boolean.parseBoolean(string);
        }
    }

    /**
     *  取缓存或者sp 布尔值
     *  如果设置默认值则取默认值
     */
    public static boolean getBoolean(String key , boolean defValue){
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG ,"can't get value because key is empty");
            return false;
        }
        String string = MemoryCacheImpl.getInstance().getString(key);
        if (TextUtils.isEmpty(string)){
            SharedPreferences sp =BaseHelper.getInstance().getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
            boolean value = sp.getBoolean(key, defValue);
            MemoryCacheImpl.getInstance().put(key, String.valueOf(value));
            return value;
        }else {
            return Boolean.parseBoolean(string);
        }
    }

    /**
     * 清除缓存
     */
    public static void clearCache(){
        MemoryCacheImpl.getInstance().clearCache();
        LogUtils.e(TAG ,"clear cache");
    }


    /**
     * 根据key 存入一个对象
     */
    public static void putEntity(String key, Object object) {
        String jsonStr = "";
        if (object != null) {
            jsonStr = new Gson().toJson(object);
        }
        putString(key, jsonStr);
    }

    /***
     *   根据key 获取对象
     *  */
    public static  <T extends Object> T getEntity(String key, Class<T> tClass) {
        String data = getString(key);
        T t = JsonUtils.json2Object(data, tClass);
        return t;
    }
}
