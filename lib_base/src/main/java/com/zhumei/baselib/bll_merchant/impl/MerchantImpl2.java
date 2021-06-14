package com.zhumei.baselib.bll_merchant.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.bll_merchant.MerchantService;

public class MerchantImpl2 {


    private static MerchantImpl2 merchantImpl = null;

    @Autowired(name = RouterManager.MERCHANTSERVICE2)
    protected MerchantService mMerchantService;

    public static MerchantImpl2 getInstance() {
        if (merchantImpl == null) {
            synchronized (MerchantImpl2.class) {
                if (merchantImpl == null) {
                    merchantImpl = new MerchantImpl2();
                }
            }
        }
        return merchantImpl;
    }

    private MerchantImpl2() {
        ARouter.getInstance().inject(this);
    }

    public void startMerchantActivity(Context context) {
        mMerchantService.startMerchantActivity(context);
    }

    public void connectBle(){
        mMerchantService.connectBle();
    }
}
