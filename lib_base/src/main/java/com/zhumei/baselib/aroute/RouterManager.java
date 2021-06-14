package com.zhumei.baselib.aroute;

public interface RouterManager {
    /**
     * 1、页面跳转路由path
     */
    String LOGIN = "/login/loginActivity";

    String MERCHANT = "/merchant/merchantActivity";

    String SPLASH = "/splash/splashActivity";

    String banner = "/banner/bannerActivity";

    String BLESET = "/ble/bleseting";

    String MERCHANT2 = "/merchantnew/merchantActivity";


    /***
     *
     * 2、对外Service 暴露接口  路由path
     */


    String MERCHANTSERVICE = "/merchant/merchant_service";
    String MERCHANTSERVICE2 = "/merchantnew/merchant_service2";
}
