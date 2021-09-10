package com.zhumei.baselib.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.zhumei.baselib.aroute.RouterManager;

//import com.zhumei.commercialscreen.ui.SplashActivity;

public class BootupReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent it = new Intent(context , SplashActivity.class);
        // 如果是无线网
//        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(it);


        if (intent != null) {
            /**
             * 创众屏幕 开机自启动我们的应用进程 + 开机广播 出现重新启动页面的情况
             * */
            boolean appForeground = AppUtils.isAppForeground();
            LogUtils.d("前台进程: " + appForeground);
            if (!appForeground) {

                ARouter.getInstance().build(RouterManager.SPLASH)
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();

            }
        }
    }
}
