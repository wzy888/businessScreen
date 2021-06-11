package com.zhumei.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.utils.useing.hardware.TimeSwitchUtils;


public class PoweroffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TimeSwitchUtils.powerOff(context , AppConstants.BoradTypeFlag.HK);
    }
}
