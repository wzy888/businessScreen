package com.zhumei.baselib.retrofit;

import android.os.Environment;
import android.util.Log;


import com.zhumei.baselib.BuildConfig;
import com.zhumei.baselib.config.ConstantApi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiRetrofitNew {
    private static ApiRetrofitNew apiRetrofit;
    private Retrofit retrofit;
    private OkHttpClient client;
    private ApiServerNew apiServer;
    private String TAG = "ApiRetrofit";
   private int TIME_OUT=60;

    /**
     * 请求访问quest
     * response拦截器
     */
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();


            // 参数
            String para = bodyToString(request.body());
            if (BuildConfig.DEBUG){
                Log.e(TAG, "----------Request Start----------------");
                Log.e(TAG, "| " + request.toString() + request.headers().toString());
                Log.e(TAG, "| params:" + para);

                Log.e(TAG, "| Response: | " + content + " | 响应内容");
                Log.e(TAG, "----------Request End:" + duration + "毫秒----------");
            }



            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    };

    private String bodyToString(final RequestBody request) throws IOException {
        final Buffer buffer = new Buffer();
        if (request != null) request.writeTo(buffer);
        else return "";
        return buffer.readUtf8();
    }

    public ApiRetrofitNew() {
        try {
            String cacheDirectory = Environment.getExternalStorageDirectory() + "/okttpcaches"; // 设置缓存文件路径
            long cacheSize = 40 * 1024 * 1024;
            Cache cache = new Cache(new File(cacheDirectory), cacheSize);
            client = new OkHttpClient.Builder()
                    //添加log拦截器
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .addInterceptor(new RequestCacheInterceptor())
                    .addNetworkInterceptor(new ResponseCacheInterceptor())
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            String host = ConstantApi.ZHUMEI_BASE_URL;
            retrofit = new Retrofit.Builder()
                    .baseUrl(host)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //支持RxJava2
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();

            apiServer = retrofit.create(ApiServerNew.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ApiRetrofitNew getInstance() {
        if (apiRetrofit == null) {
            synchronized (Object.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new ApiRetrofitNew();
                }
            }
        }
        return apiRetrofit;
    }

    public ApiServerNew getApiService() {
        return apiServer;
    }
}

