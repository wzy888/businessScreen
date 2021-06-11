package com.zhumei.commercialscreen.presenter.splash;

import android.util.Log;

import com.zhumei.baselib.utils.ParamsUtils;
import com.zhumei.baselib.base.BaseObserverNew;
import com.zhumei.baselib.base.BasePresenterNew;
import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.baselib.module.response.MerchantInfo;

import java.util.HashMap;

public class SplashPresenterNew extends BasePresenterNew<SplashViewNew> {
    public SplashPresenterNew(SplashViewNew baseView) {
        super(baseView);
    }

    // splash 页面预请求 数据
    public void getCommercialInfo(String merchant_id) {
        HashMap params = new HashMap();
        try {
            params.put("merchant_id",String.valueOf(merchant_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MediaType.parse("application/json; charset=utf-8")
//        RequestBody requestBody = RequestBody.create( MediaType.parse("application/x-www-form-urlencoded"), params.toString());
        addDisposable(apiServer.getCommercialInfo(params), new BaseObserverNew<BaseResponse<MerchantInfo>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<MerchantInfo> o) {
                //模板类型
                baseView.commercialInfoSucc(o);
            }



            @Override
            public void onError(String msg) {
                Log.i("getCommercialInfo", msg);
//                setToast(msg);
                baseView.commInfoError(msg);
            }
        });
    }

    public void getGoods(String merchant_id) {
        HashMap params = new HashMap();
        try {
            params.put("merchant_id",String.valueOf(merchant_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MediaType.parse("application/json; charset=utf-8")
//        RequestBody requestBody = RequestBody.create( MediaType.parse("application/x-www-form-urlencoded"), params.toString());
        addDisposable(apiServer.getGoods(params), new BaseObserverNew<BaseResponse<GoodsPriceRes>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<GoodsPriceRes> o) {
                //模板类型
                baseView.goodsSuccess(o);
            }



            @Override
            public void onError(String msg) {
                Log.i("getGoods", msg);
//                setToast(msg);
                baseView.goodsError(msg);
            }
        });
    }

    public void getBanner(String merchant_id) {
        HashMap params = new HashMap();
        try {
            params.put("merchant_id",String.valueOf(merchant_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addDisposable(apiServer.getBanner(params), new BaseObserverNew<BaseResponse<BannerRes>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<BannerRes> o) {
                //模板类型
                baseView.bannerSuccess(o.getObj());
            }



            @Override
            public void onError(String msg) {
                Log.i("getBanner", msg);
//                setToast(msg);
                baseView.bannerError(msg);
            }
        });
    }

    public void autoUpdate(String market_id) {
        HashMap params = new HashMap();
        try {
            params.put("market_id",String.valueOf(market_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addDisposable(apiServer.autoUpdate(params), new BaseObserverNew<BaseResponse<AutoUpdateRes>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<AutoUpdateRes> o) {
                //模板类型
                baseView.updateSuccess(o.getObj(),market_id);
            }



            @Override
            public void onError(String msg) {
                Log.i("getBanner", msg);
//                setToast(msg);
                baseView.updateError(msg);
            }
        });
    }


}
