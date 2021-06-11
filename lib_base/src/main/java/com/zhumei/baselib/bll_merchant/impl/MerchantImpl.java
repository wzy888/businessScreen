package com.zhumei.baselib.bll_merchant.impl;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.bll_merchant.MerchantService;

public class MerchantImpl {


    private static MerchantImpl merchantImpl = null;

    @Autowired(name = RouterManager.MERCHANTSERVICE)
    protected MerchantService mMerchantService;

    public static MerchantImpl getInstance() {
        if (merchantImpl == null) {
            synchronized (MerchantImpl.class) {
                if (merchantImpl == null) {
                    merchantImpl = new MerchantImpl();
                }
            }
        }
        return merchantImpl;
    }

    private MerchantImpl() {
        ARouter.getInstance().inject(this);
    }

    public void startMerchantActivity(Context context) {
        mMerchantService.startMerchantActivity(context);
    }

    public void connectBle(){
        mMerchantService.connectBle();
    }
}
