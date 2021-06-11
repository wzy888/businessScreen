package com.zhumei.baselib.utils;

public class MyClickUtils {
    private final static int SPACE_TIME = 500;//2次点击的间隔时间，单位ms
    private static long lastClickTime;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClick = false;
        } else {
            isClick = true;
        }
        lastClickTime = currentTime;
        return isClick;
    }
}
