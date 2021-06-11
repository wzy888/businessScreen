package com.zhumei.baselib.utils.useing.hardware;

import android.content.Context;
import android.content.Intent;


import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.utils.useing.software.LogUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 定时开关工具类
 */
public class TimeSwitchUtils {

    private static final String TAG = "TimeSwitchUtils";

    /**
     * 清除定时开关
     * @param context
     * @param devicePath
     * @param boardFlag
     */
    public static void removeTimeSwitch(Context context  , int boardFlag){
//        if(boardFlag == AppConstants.BoradTypeFlag.AZLD){
//            LogUtils.e(TAG , "AZLD remove time switch !");
//            Intent intent = new Intent("android.intent.action.setpoweronoff");
//            intent.putExtra("timeon", new int[6]);
//            intent.putExtra("timeoff", new int[6]);
//            intent.putExtra("enable", false);
//            context.sendBroadcast(intent);
//        }
//        }else
        if(boardFlag == AppConstants.BoradTypeFlag.ZSLF){
            LogUtils.e(TAG , "ZSLF remove time switch !");
            context.sendBroadcast(new Intent("com.szdrcc.cancle.timeoffon"));
        }
        else {
            LogUtils.e(TAG , "BORAD_FLAG_UNKNOWN");
        }
    }

    /**
     * 立即关机
     * @param context
     * @param boardFlag
     */
    public static void powerOff(Context context , int boardFlag){
//        if(boardFlag == AppConstants.BoradTypeFlag.AZLD){
//            LogUtils.e(TAG , "AZLD power off !");
//            context.sendBroadcast(new Intent("android.intent.action.shutdown"));
//        }else
        if(boardFlag == AppConstants.BoradTypeFlag.ZSLF){
            LogUtils.e(TAG , "ZSLF power off !");
            context.sendBroadcast(new Intent("com.android.drcc.poweroff"));
        }
        else {
            LogUtils.e(TAG , "BORAD_FLAG_UNKNOWN");
        }
    }

    /**
     * 设置定时开关
     * @param context
     * @param openCalendar
     * @param offCalendar
     * @param calendar
     * @param boardFlag
     */
    public static void setTimeSwitch(Context context , Calendar openCalendar , Calendar offCalendar , Calendar calendar , int boardFlag){
        try {
            // 取设置的时分秒
            int openHour = openCalendar.get(Calendar.HOUR_OF_DAY);
            int openMin = openCalendar.get(Calendar.MINUTE);
            int openSec = openCalendar.get(Calendar.SECOND);
            int offHour = offCalendar.get(Calendar.HOUR_OF_DAY);
            int offMin = offCalendar.get(Calendar.MINUTE);
            int offSec = offCalendar.get(Calendar.SECOND);
            // 取本地的年月日
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            // 判断五分钟以内
            Calendar calendar1 = new GregorianCalendar();
            Calendar calendar2 = new GregorianCalendar();
            Calendar calendar3 = new GregorianCalendar();
            Calendar calendar4 = new GregorianCalendar();
            calendar1.set(year, month, day, offHour, offMin, offSec);
            calendar2.set(year, month, day, openHour, openMin, openSec);
            calendar3.set(year, month, day, openHour, openMin, openSec);
            calendar4.set(year, month, day, openHour, openMin, openSec);
            calendar3.add(Calendar.MINUTE, -6);
            calendar4.add(Calendar.MINUTE, 6);
            if (calendar1.after(calendar3) && calendar1.before(calendar4)) {
                LogUtils.e(TAG, "within five minutes !");
                // 先关机 五分钟以后再开机
                openMin = offMin + 5;
            }
//            if(boardFlag == AppConstants.BoradTypeFlag.AZLD){
//                LogUtils.e(TAG, "AZLD open--->" + openHour + ":" + openMin + ":" + openSec + ",off--->" + offHour + ":" + offMin + ":" + offSec);
//                // 安致兰德定时开关
//                Intent intent = new Intent("android.intent.action.setpoweronoff");
//                int[] timeon = new int[]{year, month, day, openHour, openMin, openSec};
//                int[] timeoff = new int[]{year, month, day, offHour, offMin, offSec};
//                intent.putExtra("timeon", timeon);
//                intent.putExtra("timeoff", timeoff);
//                intent.putExtra("enable", true);
//                context.sendBroadcast(intent);
//            }
//            }else
            if(boardFlag == AppConstants.BoradTypeFlag.ZSLF){
                LogUtils.e(TAG, "ZSLF open--->" + openHour + ":" + openMin + ":" + openSec + ",off--->" + offHour + ":" + offMin + ":" + offSec);
                Intent mIntent = new Intent("android.56iq.intent.action.setpoweronoff");
                int[] timeon = new int[]{year, month, day, openHour, openMin};
                int[] timeoff = new int[]{year, month, day, offHour, offMin};
                mIntent.putExtra("timeon", timeon);
                mIntent.putExtra("timeoff", timeoff);
                mIntent.putExtra("enable", true);
                context.sendBroadcast(mIntent);
            }
            else {
                LogUtils.e(TAG , "BORAD_FLAG_UNKNOWN");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
