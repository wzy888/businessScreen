package com.zhumei.commercialscreen;

import com.zhumei.baselib.CommonInterface;

import io.github.prototypez.appjoint.core.ServiceProvider;

@ServiceProvider
public class AppCommonInterface implements CommonInterface {
    @Override
    public int getAppCount() {

        return MyApplication.getMyApplication().getAppCount();
    }

    @Override
    public MyApplication getMainApp() {

        return MyApplication.getMyApplication();
    }
}
