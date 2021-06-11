package com.zhumei.baselib.utils.useing.event;

import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {

    public static void register(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }

    public static void unRegister(Object subscriber){
        EventBus.getDefault().unregister(subscriber);
    }

    public static void post(Object event){
        EventBus.getDefault().post(event);
    }



    public static void postSticky(Object event){
        EventBus.getDefault().postSticky(event);
    }
}
