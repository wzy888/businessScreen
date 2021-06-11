package com.zhumei.update;

import android.content.Context;

public class UpdateHelper {

    private static Context mContext;
    private  static volatile UpdateHelper singleton;


    private UpdateHelper() {
    }

    public static void  init(Context context){
       mContext = context;
    }

    public  Context getContext() {
        return mContext;
    }

    public static UpdateHelper getInstance() {
        if (singleton == null) {
            synchronized (UpdateHelper.class) {
                if (singleton == null) {
                    singleton = new UpdateHelper();
                }
            }
        }
        return singleton;
    }

}
