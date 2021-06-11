package com.zhumei.baselib.utils.useing.software;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {


    /**
     * 获取当前时间戳
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
        return Long.parseLong(currentTime);
    }

    public static long getCurrentSeconds() {

        long seconds = 1;
        try {
            seconds = System.currentTimeMillis() / 1000;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return seconds;
    }

    /**
     * 获取此时一月前的时间戳
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getMonthAgoTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
        return Long.parseLong(currentTime);
    }

    /**
     * 获取此时days 日前的时间戳
     *
     * @param days
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getDayAgoTime(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -days + 2);
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
        return Long.parseLong(currentTime);
    }
}
