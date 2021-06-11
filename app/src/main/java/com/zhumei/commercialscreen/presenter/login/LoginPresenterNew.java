package com.zhumei.commercialscreen.presenter.login;

import android.util.Log;

import com.zhumei.baselib.utils.ParamsUtils;
import com.zhumei.baselib.base.BaseObserverNew;
import com.zhumei.baselib.base.BasePresenterNew;
import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.module.response.MarketCodeRes;

import java.util.HashMap;
import java.util.List;

public class LoginPresenterNew extends BasePresenterNew<LoginViewNew> {
    public LoginPresenterNew(LoginViewNew baseView) {
        super(baseView);
    }

    /**
     * 获取市场 编号
     */
    public void getMarketCode() {
        String TAG = "getMarketCode";
        try {

            HashMap parasMap = new HashMap();

            parasMap.put("random", ParamsUtils.getRandom());
            parasMap.put("sign", ParamsUtils.md5(parasMap));
            addDisposable(apiServer.getMarketCode(parasMap), new BaseObserverNew<BaseResponse<List<MarketCodeRes>>>(baseView) {
                @Override
                public void onSuccess(BaseResponse<List<MarketCodeRes>> response) {
                    try {
                        baseView.getMarketCode(response);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String msg) {
//
                    try {
                        Log.i("marketCode", msg);
                        setToast(msg);
                        baseView.marketCodeError(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取密码
     */
    public void login(String code, String stall_name) {
        try {
            HashMap params = new HashMap();
            try {
                // 市场编号
                params.put("code", code);
                // 摊位编号
                params.put("stall_name", stall_name);
                params.put("sign", ParamsUtils.md5(params));
            } catch (Exception e) {
                e.printStackTrace();
            }
//            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
            addDisposable(apiServer.login(params), new BaseObserverNew<BaseResponse<LoginRes>>(baseView) {
                @Override
                public void onSuccess(BaseResponse<LoginRes> response) {
                    try {
                        baseView.loginSuccess(response, stall_name);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String msg) {
                    Log.e("login==>", msg);
                    setToast(msg);
                    baseView.loginError(msg);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    setToast(msg);
                    baseView.updateError(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
