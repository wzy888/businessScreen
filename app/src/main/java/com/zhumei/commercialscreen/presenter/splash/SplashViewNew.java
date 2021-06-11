package com.zhumei.commercialscreen.presenter.splash;


import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.base.BaseView;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.baselib.module.response.MerchantInfo;

public interface SplashViewNew extends BaseView {
    void commercialInfoSucc(BaseResponse<MerchantInfo> response);

    void commInfoError(String msg);

    void goodsSuccess(BaseResponse<GoodsPriceRes> response);

    void goodsError(String msg);

    void bannerSuccess(BannerRes response);

    void bannerError(String msg);

    void updateSuccess(AutoUpdateRes obj, String market_id);

    void updateError(String msg);

}
