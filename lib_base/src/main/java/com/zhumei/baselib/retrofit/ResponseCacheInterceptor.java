package com.zhumei.baselib.retrofit;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ResponseCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        String cache = CacheControl.FORCE_CACHE.toString();
//        Response response = chain.proceed(chain.request()).newBuilder()
//                .removeHeader("Pragma") //移除影响
//                .removeHeader("Cache-Control") //移除影响
//                .addHeader("Cache-Control", cache).build();
//        LogUtils.e("ResponseCacheInterceptor",cache);
//        return response;
        Request request = chain.request();
        Response response = chain.proceed(request);
        Response response1 = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //cache for 30 days
                .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
                .build();
        return response1;

    }
}
