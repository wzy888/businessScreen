package com.zhumei.baselib.interceptor;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhumei.baselib.aroute.RouterManager;

/**
 * 路由跳转的回调
 *
 * @author lishide
 * @date 2020/5/30
 */
public class LoginNavigationCallbackImpl implements NavigationCallback {

    /**
     * 找到了
     */

    public void onFound(Postcard postcard) {

    }

    /**
     * 找不到了
     */

    public void onLost(Postcard postcard) {

    }

    /**
     * 跳转成功了
     */

    public void onArrival(Postcard postcard) {

    }


    public void onInterrupt(Postcard postcard) {
        String path = postcard.getPath();
        Bundle bundle = postcard.getExtras();
        // 被登录拦截了下来了
        // 需要调转到登录页面，把参数跟被登录拦截下来的路径传递给登录页面，登录成功后再进行跳转被拦截的页面
        ARouter.getInstance().build(RouterManager.banner)
                .with(bundle)
                .withString(RouterManager.PATH, path)
                .navigation();
    }
}