package com.zhumei.bll_merchant2;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.bll_merchant.MerchantService;
import com.zhumei.bll_merchant2.ui.MerchantElecActivity2;


@Route(path = RouterManager.MERCHANTSERVICE2)
public class MerchantImplService implements MerchantService {

    private Context contxt;
    private MerchantElecActivity2 activity  = MerchantElecActivity2.instance;

//    public MerchantImplService(Context contxt, MerchantElecActivity activity) {
//        this.contxt = contxt;
//        this.activity = activity;
//    }
//
//    public Context getContxt() {
//        return contxt;
//    }
//
//    public void setContxt(Context contxt) {
//        this.contxt = contxt;
//    }
//
//    public MerchantElecActivity getActivity() {
//        return activity;
//    }
//
//    public void setActivity(MerchantElecActivity activity) {
//        this.activity = activity;
//    }

    @Override
    public void startMerchantActivity(Context context) {
        try {
            MerchantElecActivity2.startActivity(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectBle() {
        try {
            if (ObjectUtils.isNotEmpty(activity)){
                activity. enableBleToConnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Context context) {
        LogUtils.e("MerchantImpl  init() 22...");
//        this.activity = (MerchantElecActivity) context;
    }


}
