package com.zhumei.baselib.utils.useing.software;

import android.content.Context;
import android.content.res.Configuration;

/**
 * 判断是手机还是电视  返回true 是tv  返回false 是手机
 */
public class PhoneUtils {

    public static String isTv(Context context) {

        //通过API获取屏幕宽度，高度，dp数值

        if( (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE){
            if(context.getResources().getDisplayMetrics().densityDpi == 240){
                return "Pad";
            }else{
                return "TV";
            }
        }else{
            return "Phone";
        }
    }
}
