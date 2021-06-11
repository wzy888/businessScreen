package com.zhumei.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhumei.baselib.service.CommitTradeService;


/**
 *  触发 订单交易表 上传数据.
 * */
public class CommitTradeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context!=null){
            Intent i = new Intent(context, CommitTradeService.class);
            context.startService(i);
        }
    }
}
