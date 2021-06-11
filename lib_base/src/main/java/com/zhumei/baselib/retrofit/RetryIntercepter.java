package com.zhumei.baselib.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 重试拦截器
 */
public class RetryIntercepter implements Interceptor {

    public int maxRetry;//最大重试次数
//    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）




    /**
     * 最大重试次数
     */

   public   RetryIntercepter(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        int count = 0;
        while (count < maxRetry) {
           System.out.println("retryNum=" + count);

            try {
                //发起网络请求
                response = chain.proceed(request);
                // 得到结果跳出循环
                break;
            } catch (Exception e) {
                count ++;
                response = null;
            }
        }
        if(response == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
