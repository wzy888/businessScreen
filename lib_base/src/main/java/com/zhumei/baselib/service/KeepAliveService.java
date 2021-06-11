package com.zhumei.baselib.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.receiver.KeepAliveTimedReceiver;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.ServiceUtils;

public class KeepAliveService extends Service {

    private static final String TAG = KeepAliveService.class.toString();
    private static final String CHANNEL_ID_STRING = "zhumei2012";

    /**
     * 选择保活服务是否有效
     */
    private boolean mIsEffective = false;

    @Override
    public void onCreate() {
        super.onCreate();

        //适配8.0service
        NotificationManager notificationManager = (NotificationManager) MyBaseApplication.getMyApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, "电子屏", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
            startForeground(1, notification);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 判断应用程序是否在后台，在后台则打开
        if (ServiceUtils.isForeground()) {
            // 前台
            LogUtils.e(TAG, "the application runs in the foreground!");
            mIsEffective = true;
        } else {
            // 后台
            if (mIsEffective) {
                LogUtils.e(TAG, "the application runs in the background, need to return to foreground!");
                // 打开android应用程序
                for (ApplicationInfo info : getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES)) {
                    // 匹配商户屏的包名 如果设备中安装了商户屏软件
                    if (info.packageName.equals(getPackageName())) {
                        // 就打开该应用程序
                        Intent resolveIntent = getPackageManager().getLaunchIntentForPackage(info.packageName);
                        startActivity(resolveIntent);
                    }
                }
            } else {
                LogUtils.e(TAG, "the application runs in the background, two minutes later return to foreground!");
                mIsEffective = true;
            }
        }
        // 两分钟通过广播执行一次前台后台判断
        long triggerAtTime = SystemClock.elapsedRealtime() + AppConstants.DefaultSetting.SET_ALIVE_CYCLE;
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, KeepAliveTimedReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        // 设置定时
        if (am != null) {
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
