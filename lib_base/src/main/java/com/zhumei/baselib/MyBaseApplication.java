package com.zhumei.baselib;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.ServiceUtils;

import java.lang.ref.WeakReference;
import java.util.Stack;

import io.github.prototypez.appjoint.core.ModuleSpec;

@ModuleSpec
public class MyBaseApplication extends MultiDexApplication {

    public static Stack<WeakReference<Activity>> mActivityStack;

    private String TAG = "baseApp";
    private int mAppCount;

    private static MyBaseApplication instance;
    public static MyBaseApplication getMyApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }



    /**
     * 添加Activity到栈
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(new WeakReference<>(activity));
    }

    /**
     * 获取当前运行的app数量
     *
     * @return
     */
    public int getAppCount() {
        return mAppCount;
    }

    /**
     * 确认服务是否在运行 如果保活服务已运行就不在运行 没有运行就开始运行
     */


    public boolean isBleNormalExit = false;

    public boolean isBleNormalExit() {
        return isBleNormalExit;
    }

    public void setBleNormalExit(boolean bleNormalExit) {
        isBleNormalExit = bleNormalExit;
    }
}
