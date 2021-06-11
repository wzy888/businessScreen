package com.zhumei.baselib.utils.useing.software;

import android.app.ActivityManager;
import android.content.Context;

import com.zhumei.baselib.CommonInterface;

import java.util.List;

import io.github.prototypez.appjoint.AppJoint;

public class ServiceUtils {

    public static boolean getApplicationValue() {
        CommonInterface service = AppJoint.service(CommonInterface.class);
        return service.getAppCount() > 0;
    }

    public static boolean isForeground() {
        return getApplicationValue();
    }

    /**
     * 判断服务是否运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}