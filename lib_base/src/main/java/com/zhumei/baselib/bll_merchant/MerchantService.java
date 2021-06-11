package com.zhumei.baselib.bll_merchant;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 *  对外暴露接口
 * */
public interface MerchantService  extends IProvider {
    void startMerchantActivity(Context context);


    void connectBle();
}
