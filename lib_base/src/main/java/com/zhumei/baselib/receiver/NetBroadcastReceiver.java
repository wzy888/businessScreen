package com.zhumei.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.base.Event;
import com.zhumei.baselib.utils.NetUtil;
import com.zhumei.baselib.utils.useing.event.EventBusUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;

public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        String action = intent.getAction();
        LogUtils.e("NetBroadcastReceiver", action);
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            boolean netEnabled = NetUtil.isNetEnabled();
            LogUtils.e("netenable", netEnabled + "");
//            evetBus 替代 接口回调..
            EventBusUtils.post(new Event<>(AppConstants.EventCode.NET_WORk, netEnabled));


        }
    }


}