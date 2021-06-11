package com.zhumei.baselib.retrofit;

import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.utils.NetUtil;
import com.zhumei.baselib.utils.useing.software.LogUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        final Request.Builder requestBuilder = request.newBuilder();
        NetUtil.isNetPingBd(new NetUtil.NetCallBack() {
            @Override
            public void isConnect(boolean connect) {
                if (!connect) {
                    LogUtils.e("intercept==>" , String.format("%s", connect));
                    requestBuilder.cacheControl(CacheControl.FORCE_CACHE); // 直接使用缓存

                }
            }
        }, Constant.PING_BAIDU_IP);
        return chain.proceed(requestBuilder.build());
    }
}
