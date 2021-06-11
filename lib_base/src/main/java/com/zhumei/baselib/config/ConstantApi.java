package com.zhumei.baselib.config;

/**
 * 存放API 接口类
 */
public interface ConstantApi {
    // 线上带Https
//http://www.zmcmf.top:8010/api/merchantscreen/index/login

//    String ZHUMEI_BASE_URL = "http://192.168.1.12:8010/";
    String TEST ="test";
    String TEST1="test11111111111";


    public static final String PUBLIC_IP_POST = "http://pv.sohu.com/cityjson?ie=utf-8";
    // 正式服
    String ZHUMEI_BASE_URL = "http://pt.zhihuinongmao.net/";


    //登录
    String LOGIN = "api/merchantscreen/index/login";


    //市场号码
    String MARKET_CODE = "api/merchantscreen/index/get_market_code";
    //商户基本信息
    String COMMERCIAL_INFO = "api/merchantscreen/index/merchant_info";
    // 获取菜价 信息
    String GET_GOODS = "api/merchantscreen/index/get_goods";
    //上传device info
    String DEVICE_INFO = "api/merchantscreen/index/device_information";

    String DEVICE_ONLINE = "api/merchantscreen/index/device_online";
    //获取Banner
    String GET_BANNER = "api/merchantscreen/index/get_banner";
    // 软件升级 更新.
    String AUTO_UPDATE = "api/merchantscreen/index/auto_updata";
    //支付 通用接口
    String ACTIVEPAY = "api/doublescreen/orderpay/activepay";
    // 称重数据上传
    String CHECKORDER = "api/doublescreen/cash/check_order_new";
    //上传 数据条数
    String TRADES_COUNT = "api/merchantscreen/index/get_rowcount_trades";

    // 获取菜价 信息
    String HOT= "api/merchantscreen/index/hot";

    String MERCHANTGOODSINFO = "api/merchantscreen/index/merchantGoodsInfo";
}
