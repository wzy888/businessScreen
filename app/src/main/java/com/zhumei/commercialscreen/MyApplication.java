package com.zhumei.commercialscreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.clj.fastble.BleManager;
import com.igexin.sdk.PushManager;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhumei.baselib.BaseHelper;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.base.MmkvUtils;
import com.zhumei.baselib.service.GeTuiIntentService;
import com.zhumei.baselib.service.GeTuiPushService;
import com.zhumei.baselib.service.KeepAliveService;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.ServiceUtils;
import com.zhumei.bll_merchant.BuildConfig;
import com.zhumei.commercialscreen.ui.SplashActivity;
import com.zhumei.lib_network.NetWorkHelper;
import com.zhumei.update.UpdateHelper;

import java.net.Proxy;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;




public class MyApplication extends MyBaseApplication {
    public static boolean isExit = false;//是否退出登录

    private static MyApplication instance;

//    public static Stack<WeakReference<Activity>> mActivityStack;


    private int mAppCount = 0;
    private String TAG = "MyApplication";
//    private RefWatcher refWatcher;
//    private RefWatcher mRefWatcher;

//    public RefWatcher getRefWatcher() {
//        return refWatcher;
//    }

    // 获取Application
    public static MyApplication getMyApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {

            instance = this;

            activityLifeCycle();
            // 初始化Bugly异常上报
            // 保活服务的状态
            keepAliveServiceState();
            //屏幕适配
//            AutoSizeConfig.getInstance().setCustomFragment(true);
//            AutoSize.initCompatMultiProcess(this);

            if (BuildConfig.DEBUG){
                ARouter.openLog();
                ARouter.openDebug();
            }

            ARouter.init(this);

            MMKV.initialize(this);
//             refWatcher = LeakCanary.install(this);
            // 初始化个推推送
            initGeTuiPush();
            // 初始化fileDownLoader
            initFileDownLoader();
            // 初始化蓝牙
            initBle();
            // 初始化ZXing
            initZXing();
            // 初始化异常捕获
            initTryCrash();
//            initCrash();
//            BlockCanary.install (this, new AppBlockCanaryContext()).start ();
            initBugly();

            /**
             *  注入模块  初始化
             * */
            BaseHelper.init(getMyApplication());
            UpdateHelper.init(getMyApplication());
            NetWorkHelper.init(getMyApplication());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }







//    private void initCrash() {
//        // 防止应用 不退出，会出现 黑屏
////        CrashHandlerManager.getInstance(MyApplication.getMyApplication()).init();
//    }

    /**
     * MultiDex解决方法数超过65535问题
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    /**
     * activity的生命周期回调
     * 判断应用程序是否在前台运行
     */
    private void activityLifeCycle() {
        try {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                    if (BuildConfig.DEBUG){
                        Log.e("onCreate==>", activity.getLocalClassName());
                    }
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    mAppCount++;
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    mAppCount--;
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                   if (BuildConfig.DEBUG){
                       Log.e("onDestroy==>", activity.getLocalClassName());
                   }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 添加Activity到栈
     *
     * @param activity
     */
//    public static void addActivity(Activity activity) {
//        if (mActivityStack == null) {
//            mActivityStack = new Stack<>();
//        }
//        mActivityStack.add(new WeakReference<>(activity));
//    }

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
    private void keepAliveServiceState() {
        try {
            if (ServiceUtils.isServiceRunning(this, AppConstants.DefaultSetting.KEEP_ALIVE_SERVICE)) {
                LogUtils.e(TAG, "KeepAliveService prohibit duplication of creation!");
            } else {
                LogUtils.e(TAG, "KeepAliveService not created!");
                Intent intent = new Intent(this, KeepAliveService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Bugly异常上报
     * 第三个参数为打开Log开关,调试模式设置为true,上线之后可以设置为false
     */
    private void initBugly() {
        try {
            /******************************** Bugly异常上报的密钥********************************/
            CrashReport.initCrashReport(getApplicationContext(), "6dd92a4435", false);
            /******************************** Bugly异常上报的密钥********************************/
            String deviceId = MmkvUtils.getInstance().getString(AppConstants.Cache.DEVICE_ID);
            CrashReport.setUserId(this, deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化个推推送
     */
    private void initGeTuiPush() {
        try {
            PushManager.getInstance().initialize(getApplicationContext(), GeTuiPushService.class);
            PushManager.getInstance().registerPushIntentService(getApplicationContext(), GeTuiIntentService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化fileDownLoader
     */
    private void initFileDownLoader() {
        try {
            FileDownloader.setupOnApplicationOnCreate(this)
                    .connectionCreator(new FileDownloadUrlConnection
                            .Creator(new FileDownloadUrlConnection.Configuration()
                            .connectTimeout(AppConstants.DefaultSetting.SET_CONNECT_TIMEOUT_CYCLE_LONG)
                            .readTimeout(AppConstants.DefaultSetting.SET_CONNECT_TIMEOUT_CYCLE_LONG)
                            .proxy(Proxy.NO_PROXY)
                    ))
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化蓝牙
     */
    private void initBle() {
        try {
            BleManager.getInstance().init(MyApplication.instance);
            BleManager.getInstance()
                    .enableLog(true)
                    // 设置重连次数
                    .setReConnectCount(AppConstants.DefaultSetting.SET_RECONNECT_COUNT, AppConstants.DefaultSetting.SET_RECONNECT_INTERVAL)
                    // 设置重连时间间隔
                    .setConnectOverTime(AppConstants.DefaultSetting.SET_RECONNECT_OVERTIME)
                    // 设置连接超时时间
                    .setOperateTimeout(AppConstants.DefaultSetting.SET_OPERATE_TIMEOUT)
                    ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化ZXing
     */
    private void initZXing() {
        ZXingLibrary.initDisplayOpinion(this);
    }


    /**
     * 初始化异常捕获
     */
    @SuppressLint("RestrictedApi")
    public void initTryCrash() {
        CaocConfig.Builder.create()
                // 当应用程序后台崩溃时，默默关闭程序
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
                // 重启显示页面
                .restartActivity(SplashActivity.class)
                // 程序崩溃后显示页面
                .errorActivity(SplashActivity.class)
                // 在两次崩溃时间内，框架将不处理，让系统处理
                .minTimeBetweenCrashesMs(AppConstants.DefaultSetting.SET_CRASH_CYCLE)
                .apply();
        CustomActivityOnCrash.install(this);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
