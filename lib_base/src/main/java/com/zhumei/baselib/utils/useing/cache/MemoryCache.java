package com.zhumei.baselib.utils.useing.cache;

public interface MemoryCache {

    void put(String key, String value);

    String getString(String key);

}
