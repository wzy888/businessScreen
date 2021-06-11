package com.zhumei.baselib.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.ObjectUtils;
import com.zhumei.baselib.IMyAidlInterface;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.ServiceUtils;

/**
 * 主进程 双进程通讯
 *
 * @author LiGuangMin
 * @time Created by 2018/8/17 11:26
 */
public class StepService extends Service {

    private final static String TAG = StepService.class.getSimpleName();
    private static final String CHANNEL_ID_STRING = "step";
    private String commitTradeService = "com.zhumei.baselib.service.CommitTradeService";
    //保活Service.
    private String keepLiveService = "com.zhumei.baselib.service.KeepAliveService";
    private String clazzName = "com.zhumei.baselib.service.StepService";

    @Override
    public void onCreate() {
        super.onCreate();
        setNotifi();

    }

    private void setNotifi() {
        //适配8.0service
        NotificationManager notificationManager = (NotificationManager) MyBaseApplication.getMyApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, "商户屏", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
            startForeground(1, notification);
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.d(TAG, "StepService:建立链接");
            boolean isServiceRunning = ServiceUtils.isServiceRunning(MyBaseApplication.getMyApplication(), commitTradeService);
            boolean isKeepService = ServiceUtils.isServiceRunning(MyBaseApplication.getMyApplication(), keepLiveService);

            if (!isServiceRunning) {
                Intent i = new Intent(StepService.this, CommitTradeService.class);
                startService(i);
            }

            if (!isKeepService){
                Intent i = new Intent(StepService.this, KeepAliveService.class);
                startService(i);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // 断开链接
            startService(new Intent(StepService.this, GuardService.class));
            // 重新绑定
            bindService(new Intent(StepService.this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return  new IMyAidlInterface.Stub() {
            @Override
            public void keepLive() throws RemoteException {
                com.blankj.utilcode.util.LogUtils.d(TAG,"onBind-->");
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 绑定建立链接
        try {
//            setNotifi();
            boolean serviceRunning = ServiceUtils.isServiceRunning(StepService.this, clazzName);
            if (serviceRunning){

                return START_STICKY;
            }

            bindService(new Intent(this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);

            PendingIntent contentIntent = PendingIntent.getService(this, 0, intent, 0);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setTicker("zhumei")
                    .setContentIntent(contentIntent)
                    .setContentTitle("筑美商户屏")
                    .setAutoCancel(true)
                    .setContentText("zhumei")
                    .setWhen( System.currentTimeMillis());

            //把service设置为前台运行，避免手机系统自动杀掉改服务。
            startForeground(startId, builder.build());
        }catch (Exception e){
            e.printStackTrace();
        }

        return START_STICKY;
    }

}