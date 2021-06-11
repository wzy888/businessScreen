package com.zhumei.baselib.utils.useing.event;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

public class MainThreadExecutor  implements Executor {

    private Handler mHandler;

    public MainThreadExecutor() {

    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        if (mHandler==null){
             mHandler  = new Handler(Looper.getMainLooper());
         }
        mHandler.post(runnable);
    }

    public void  removeHandler() {
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
