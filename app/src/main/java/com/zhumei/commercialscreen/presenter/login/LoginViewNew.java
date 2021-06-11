package com.zhumei.commercialscreen.presenter.login;

import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.base.BaseView;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.module.response.MarketCodeRes;


import java.util.List;

public interface LoginViewNew  extends BaseView {
    void loginSuccess(BaseResponse<LoginRes> loginRes, String stall_name);

    void loginError(String msg);

    void getMarketCode(BaseResponse<List<MarketCodeRes>>  marketCodeRes);

    void marketCodeError(String msg);

    void updateSuccess(AutoUpdateRes updateRes, String market_id);

    void updateError(String msg);
}
