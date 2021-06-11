package com.zhumei.commercialscreen.presenter.bannerpresenter;


import com.zhumei.baselib.base.BaseView;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;

public interface BannerNewView  extends BaseView {


    void bannerSuccess(BannerRes response);

    void bannerError(String msg);

    void updateSuccess(AutoUpdateRes response, String market_id);

    void updateError(String msg);
}
