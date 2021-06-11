package com.zhumei.baselib;

import android.app.Application;
import android.content.Context;


public class BaseHelper {

    private static Application mContext;
    private  static volatile BaseHelper singleton;


    private BaseHelper() {
    }

    public static void  init(Application context){
        mContext = context;
    }

    public  Context getContext() {
        return mContext;
    }

    public static BaseHelper getInstance() {
        if (singleton == null) {
            synchronized (BaseHelper.class) {
                if (singleton == null) {
                    singleton = new BaseHelper();
                }
            }
        }
        return singleton;
    }
}
