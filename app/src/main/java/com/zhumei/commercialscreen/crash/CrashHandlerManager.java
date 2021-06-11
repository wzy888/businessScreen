package com.zhumei.commercialscreen.crash;

import android.content.Context;

import com.zhumei.baselib.utils.useing.software.LogUtils;


/**
 * https://juejin.cn/post/6844903945769320455
 * 应用不崩溃.
 * */

public class CrashHandlerManager {
    private static CrashHandlerManager instance;
    private static Context mContext;
    private CrashHandlerManager(){}

    public static CrashHandlerManager getInstance(Context context){
        if(instance==null){
            mContext=context.getApplicationContext();
            instance=new CrashHandlerManager();
        }
        return  instance;
    }
    public void init(){
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                handleException(e);
                //判断当前线程是否是主线程
//                if(t== Looper.getMainLooper().getThread()){
//                    handleMainThread(e);
//                }
            }
        });
    }

    private void handleMainThread(Throwable e) {
        while (true) {
            try {
//                Looper.loop();
                // 主线程 直接卡死
                handleException(e);
            } catch (Throwable throwable) {
                handleException(throwable);
            }
        }
    }

    private void handleException(Throwable e) {
        LogUtils.e("crashManager",e.getMessage());
        //写入错误日志
        //上传日志
        // 退出后重启.

//        if (mContext!=null){
//            Intent it = new Intent(mContext , KeepAliveService.class);
//            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
//                mContext.startForegroundService(it);
//            }else {
//                mContext.startService(it);
//
//            }
//        }

    }
}

