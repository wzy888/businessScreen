package com.zhumei.lib_network;

import android.app.Application;
import android.content.Context;

public class NetWorkHelper {
    private static Application mContext;
    private  static volatile NetWorkHelper netWorkHelper;


    private NetWorkHelper() {
    }

    public static void  init(Application context){
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public static NetWorkHelper getInstance() {
        if (netWorkHelper == null) {
            synchronized (NetWorkHelper.class) {
                if (netWorkHelper == null) {
                    netWorkHelper = new NetWorkHelper();
                }
            }
        }
        return netWorkHelper;
    }
}
