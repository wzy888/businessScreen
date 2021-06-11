package com.zhumei.bll_merchant.presenter;

import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.base.BaseView;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.module.response.GoodsInfoRes;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.baselib.module.response.GuideHotKeyRes;
import com.zhumei.baselib.module.response.MerchantInfo;

import java.util.List;


public interface ElecMerchantsView extends BaseView {
    void commercialInfoSucc(BaseResponse<MerchantInfo> response);

    void commInfoError(String msg);

    void goodsSuccess(BaseResponse<GoodsPriceRes> response);

    void goodsError(String msg);

    void updateSuccess(AutoUpdateRes autoUpdateRes, String market_id);

    void updateError(String msg);

    void bannerSuccess(BannerRes bannerRes);

    void bannerError(String msg);

    void hotSuccess(BaseResponse<GuideHotKeyRes> response);

    void hotError(String msg);

    void goodsInfoSuceess(BaseResponse<List<GoodsInfoRes>> response);

    void goodsInfoError(String msg);
}
