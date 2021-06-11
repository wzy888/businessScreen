package com.zhumei.baselib.base;

import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;
import com.zhumei.baselib.utils.JsonUtils;


/**
 * Created on 2018/11/28 17:28
 *
 * @author canhuah
 * <p>
 * mmkv 工具类 可缓存大量数据 ,作为本地缓存； SP的升级版.
 */
public class MmkvUtils {
    private final MMKV mMmkv;

    private static volatile MmkvUtils instance;

    //    链接：https://juejin.im/post/6844903772892692487
//     双重锁检验
    public static MmkvUtils getInstance() {
        if (instance == null) {
            synchronized (MmkvUtils.class) {
                if (instance == null) {
                    instance = new MmkvUtils();
                }
            }
        }
        return instance;
    }


    private MmkvUtils() {
        /*如果业务需要多进程访问，那么在初始化的时候加上标志位 MMKV.MULTI_PROCESS_MODE：
            MMKV* mmkv = MMKV.mmkvWithID("In   terProcessKV", MMKV.MULTI_PROCESS_MODE);
              mmkv.encode("bool", true);*/
        mMmkv = MMKV.mmkvWithID("zhumei", MMKV.MULTI_PROCESS_MODE);
    }


    public String getString(String key) {
        String string = mMmkv.getString(key, "");
        return string;
    }

    /**
     * 获取带有默认值的String
     */
    public String getStringWithDefault(String key, String defaultStr) {
        String string = mMmkv.getString(key, defaultStr);
        return string;
    }

    public void putString(String key, String value) {
        mMmkv.encode(key, value);
    }

    /**
     *  保存带有默认值 String
     * */
    public void putStringWithDefault(String key, String value, String defaultStr) {
        String mValue = TextUtils.isEmpty(value) ? defaultStr : value;
        mMmkv.encode(key, mValue);
    }


    public boolean getBoolean(String key) {
        boolean value = mMmkv.getBoolean(key, false);
        return value;
    }


    public void putBoolean(String key, boolean value) {
        mMmkv.putBoolean(key, value);
    }


    public int getInt(String key) {
        int value = mMmkv.getInt(key, 0);
        return value;

    }


    public void putInt(String key, int value) {
        mMmkv.putInt(key, value);
    }


    public void putObject(String key, Parcelable o) {
        mMmkv.encode(key, o);
    }

    public  <T extends Object> T getObject(String key, Class cls) {
        return (T) mMmkv.decodeParcelable(key, cls);
    }

    public void onExit() {
        MMKV.onExit();
    }

    /**
     * 根据key 存入一个对象
     */
    public void putEntity(String key, Object object) {
        String jsonStr = "";
        if (object != null) {
            jsonStr = new Gson().toJson(object);
        }
        putString(key, jsonStr);
    }

    /***
     *   根据key 获取对象
     *  */
    public <T extends Object> T getEntity(String key, Class<T> tClass) {
        String data = getString(key);
        T t = JsonUtils.json2Object(data, tClass);
        return t;
    }


}
