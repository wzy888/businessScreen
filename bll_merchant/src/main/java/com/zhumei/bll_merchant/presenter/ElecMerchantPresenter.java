package com.zhumei.bll_merchant.presenter;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.zhumei.baselib.base.BaseObserverNew;
import com.zhumei.baselib.base.BasePresenterNew;
import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.module.response.GoodsInfoRes;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.baselib.module.response.GuideHotKeyRes;
import com.zhumei.baselib.module.response.MerchantInfo;
import com.zhumei.baselib.utils.ParamsUtils;

import java.util.HashMap;
import java.util.List;

public class ElecMerchantPresenter extends BasePresenterNew<ElecMerchantsView> {
    public ElecMerchantPresenter(ElecMerchantsView baseView) {
        super(baseView);
    }


    public void getCommercialInfo(String merchant_id) {
        HashMap params = new HashMap();
        try {
            params.put("merchant_id", String.valueOf(merchant_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MediaType.parse("application/json; charset=utf-8")
//        RequestBody requestBody = RequestBody.create( MediaType.parse("application/x-www-form-urlencoded"), params.toString());
        addDisposable(apiServer.getCommercialInfo(params), new BaseObserverNew<BaseResponse<MerchantInfo>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<MerchantInfo> o) {
                try {
                    baseView.commercialInfoSucc(o);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(String msg) {
                try {
                    Log.i("getCommercialInfo", msg);
                    setToast(msg);
                    baseView.commInfoError(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void getGoods(String merchant_id) {
        HashMap params = new HashMap();
        try {
            params.put("merchant_id", String.valueOf(merchant_id));
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
                try {
                    baseView.goodsSuccess(o);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(String msg) {
                try {
                    Log.i("getGoods", msg);
//                setToast(msg);
                    baseView.goodsError(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void autoUpdate(final String market_id) {
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
                    setToast(msg);
                    baseView.updateError(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
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


    public void getHot(String merchant_id) {
        HashMap params = new HashMap();
        try {
            params.put("merchant_id", String.valueOf(merchant_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addDisposable(apiServer.getHot(params), new BaseObserverNew<BaseResponse<GuideHotKeyRes>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<GuideHotKeyRes> response) {
                //模板类型
                try {
                    LogUtils.i("getHot : ", response.getObj());

                    baseView.hotSuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onError(String msg) {
                try {
                    LogUtils.i("getHot", msg);
//                setToast(msg);
                    baseView.hotError(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void getMerchantGoodsInfo(String merchant_id) {
        HashMap params = new HashMap();
        try {
            params.put("merchant_id", String.valueOf(merchant_id));
            params.put("sign", ParamsUtils.md5(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addDisposable(apiServer.getMerchantGoodsInfo(params), new BaseObserverNew<BaseResponse<List<GoodsInfoRes>>>(baseView) {
            @Override
            public void onSuccess(BaseResponse<List<GoodsInfoRes>> response) {
                //模板类型
                try {
                    LogUtils.i("getMerchantGoodsInfo : ", response.getObj());

                    baseView.goodsInfoSuceess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onError(String msg) {
                try {
                    LogUtils.i("getMerchantGoodsInfo", msg);
//                setToast(msg);
                    baseView.goodsInfoError(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
