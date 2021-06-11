package com.zhumei.baselib.utils.useing.cache;

import android.text.TextUtils;
import android.util.LruCache;

import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.utils.useing.software.LogUtils;


public class MemoryCacheImpl implements MemoryCache {


    private static final String TAG = "MemoryCacheImpl";
    private LruCache<String, String> lruCache;
    private static MemoryCacheImpl mInstance;

    public static MemoryCacheImpl getInstance(){
        if(mInstance == null){
            synchronized (MemoryCacheImpl.class){
                if (mInstance == null){
                    mInstance = new MemoryCacheImpl();
                }
            }
        }
        return mInstance;
    }
    private MemoryCacheImpl(){
        init();
    }

    private void init() {
        lruCache = new LruCache<String, String>((int) AppConstants.DefaultSetting.SET_STRING_MAX_MEMORY_CACHE_SIZE){
            @Override
            protected int sizeOf(String key, String value) {
                return value.length()/1024;
            }
        };
    }

    @Override
    public void put(String key, String value) {
        if (TextUtils.isEmpty(key)|| TextUtils.isEmpty(value)){
            LogUtils.E(TAG , "can't put null to cache because key or value is null");
            return;
        }
        lruCache.put(key, value);
    }

    @Override
    public String getString(String key) {
        if (TextUtils.isEmpty(key)){
            LogUtils.E(TAG , "can't get value because key is null");
            return null;
        }
        return lruCache.get(key);
    }

    public void clearCache(){
        lruCache.evictAll();
    }
}
