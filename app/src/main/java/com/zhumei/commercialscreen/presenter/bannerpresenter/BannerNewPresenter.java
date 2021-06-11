package com.zhumei.commercialscreen.presenter.bannerpresenter;

import android.util.Log;

import com.zhumei.baselib.base.BaseObserverNew;
import com.zhumei.baselib.base.BasePresenterNew;
import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.utils.ParamsUtils;


import java.util.HashMap;

public class BannerNewPresenter extends BasePresenterNew<BannerNewView> {
    public BannerNewPresenter(BannerNewView baseView) {
        super(baseView);
    }

    public void getBanner(String merchant_id) {
        HashMap params = new HashMap();
        try {
            params.put("merchant_id", String.valueOf(merchant_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addDisposable(apiServer.getBanner(params), new BaseObserverNew<BaseResponse<BannerRes>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<BannerRes> o) {
                //模板类型
                try {
                    baseView.bannerSuccess(o.getObj());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(String msg) {
                try {
                    Log.i("getBanner", msg);
//                setToast(msg);
                    baseView.bannerError(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void autoUpdate(String market_id) {
        HashMap params = new HashMap();
        try {
            params.put("market_id", String.valueOf(market_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addDisposable(apiServer.autoUpdate(params), new BaseObserverNew<BaseResponse<AutoUpdateRes>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<AutoUpdateRes> o) {
                //模板类型
                try {
                    baseView.updateSuccess(o.getObj(), market_id);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(String msg) {
                try {
                    Log.i("autoUpdate", msg);
//                setToast(msg);
                    baseView.updateError(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }


}
