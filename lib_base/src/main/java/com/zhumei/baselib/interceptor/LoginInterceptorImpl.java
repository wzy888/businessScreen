package com.zhumei.baselib.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.base.MmkvUtils;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.module.localdata.LoginLocalData;
import com.zhumei.baselib.module.response.MerchantInfo;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;


@Interceptor(priority = 2)
public class LoginInterceptorImpl implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        LogUtils.d(path);
//        boolean isLogin = SPUtils.getInstance().getBoolean(Constant.SP_IS_LOGIN, false);
//        MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);
        LoginLocalData loginLocalData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);

        boolean isLogin = ObjectUtils.isNotEmpty(loginLocalData);
        LogUtils.d("Login  is "+ isLogin );
        if (isLogin) {
            callback.onContinue(postcard);
        } else {
            switch (path) {
                // 需要登录的直接进入这个页面
                case RouterManager.MERCHANT2:
                case RouterManager.MERCHANT:
                    callback.onInterrupt(null);
                    break;
                // 不需要登录的直接拦截下来
                default:
                    callback.onContinue(postcard);
                    break;
            }
        }
    }

    @Override
    public void init(Context context) {

    }
}
