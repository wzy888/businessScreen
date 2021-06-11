package com.zhumei.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.zhumei.baselib.service.KeepAliveService;


public class KeepAliveTimedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent(context , KeepAliveService.class);
//        context.startService(it);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            context.startForegroundService(it);
        }else {
            context.startService(it);

        }
    }
}
