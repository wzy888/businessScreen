package com.zhumei.baselib.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.zhumei.baselib.app.AppConstants;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * 自定义Glide的缓存路径和缓存大小 com.zhumei.baselib.glide.MyGlideModule
 */
@GlideModule
public class MyGlideModule extends AppGlideModule {

    /**
     * 设置清单解析，设置为false，避免添加相同的modules两次
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, AppConstants.DefaultSetting.SET_DISK_CACHE_IMAGE_FLAG, (int) AppConstants.DefaultSetting.SET_IMAGE_MAX_DISK_CACHE_SIZE));
        super.applyOptions(context, builder);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {


    }


    /**
     * 设置https 访问的时候对所有证书都进行信任
     *
     * @throws Exception
     */
    public OkHttpClient getSSLOkHttpClient() throws Exception {
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };
//        SSL
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return false;
                    }
                })
                .build();


    }
}
