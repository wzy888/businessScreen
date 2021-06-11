package com.zhumei.baselib.config;

public interface Constant {
    /**
     * 是否登录
     */
//    String IS_LOGIN = "is_login";
//    String LOGIN_USER = "login_user";
//    String FIRST = "is_first";
//     int TEMPLATECODE =-1; //  模板

    /**
     * ping百度，判断外网网络连接
     * ping筑美，判断内网网络连接
     */
    String PING_BAIDU = "http://www.baidu.com";
    String PING_ZHUMEI = "https://n.zhumei.net";

    // 获取接口状态
    String INTFC_STATE = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_intfc_state";

    //预加载Banner
    String MARKET_AD_DATA = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_market_ad_data";
    //  https://nm.zhumei.net/index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=index"
//    获取商户基本信息
    String COMMERCIA_LINFO = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=index";
    //电子秤 模板信息
//    https://nm.zhumei.net/index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=index
    String COMMERCIA_LINFO_ELEC = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=index";
    //获取密码
    String PASS_WORD = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_pwd";
    // 获取模板  https://nm.zhumei.net/index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_screen_template
    String TEMPLATE_CODE = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_screen_template";
    //
    String LOGO_TITLE = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_splash_content";
    //设置 定时开关机
    String TIME_SWITCH = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=set_time_switch";
    //
    String UPDATE = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_package_data";
    // 设备ID
    String DEVICE_ID = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_info_id";

    //    https://nm.zhumei.net/index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarchNew&a=index
    // 模板3
    String MARKET_INFO = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarchNew&a=index";

    //    https://nm.zhumei.net/index.php?g=Nm&m=MerchantScreenAppKS&a=get_screen_data
    /*晨曦模板*/
    String COMMERCIAL_INFO_CX = "index.php?g=Nm&m=MerchantScreenAppKS&a=get_screen_data";

    //    https://nm.zhumei.net/index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_real_time_data
    /**
     * 获取商户交易量接口
     */
    String REALTIME_VOLUME = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_real_time_data";

    /**
     * 获取商户交易量接口
     */
    String VOLUME_DATA = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_volume";

    //   商户支付 数据
    String MERCHANT_PAY_CONFIG = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_merchant_pay_config";
    // 市场 ID
    String MARKET_NUM = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_market_code";

    String MARKET_NUM_QRCODE = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=get_code_img";

//    提交 交易数据
    String SUBMIT_TRADE_DATA = "index.php?g=Nm&m=MerchantScreenAppScaleNineteenMarch&a=running_account";

    /***************************** 缓存的一些Key ***************************************************************/
    // 市场编号
    String MARKET_LOCAL_DATA = "market_code_local";
    // 登录
    String LOGIN_LOCAL = "login_local";
    // 记录上次 选择登录的市场code
    String LAST_MARKET_CODE = "last_market_code";
    //记录上次登录成功的 摊位号
    String LAST_STALL_NAME = "last_stall_name";
    //商户基本信息
    String MERCHANT_INFO = "merchant_info";
    // 菜价信息
    String GOODS_PRICE = "goods_price";
    // Banner 数据
    String BANNER_DATA = "banner_data";
    //更新 升级的data
    String UPDATE_DATA = "UPDATE_DATA";
    // 默认 退出登录密码
    String QUIT_PSD = "883399";
    String BLE_PSD = "993388";
    // 刷新App 升级
    String UPDATE_APP = "2";
    // 刷新页面
    String FRESH_PAGE = "3";
    // 刷新 支付成功
    String FRESH_PAY_SUCCESS = "4";
    // 刷新菜价
    String FRSH_GOODS_PRICE = "5";

    String FRESH_INFO= "6";
    //清除 本地数据库
    String CLEAR_DAO = "7";
    String CODE_URL = "code_url";
    String ROWS_COUNT = "rows_count";
    int OK =100 ;
    Integer PageSize = 0;
    // 生成索源 二维码地址
    String Trace_Code = "http://pt.zhihuinongmao.net/api/doublescreen/home/trace?code=";
    int OK2 = 101;
    String IS_FRIST_KEY = "is_first_key";
    String SCALE_DB_NAME = "scale.db";
    String MERCHANT_PAY_NO = "pay_no";
    String PING_BAIDU_IP = "180.101.49.12";
    String GOODSINFORES = "goodsinfores";
    String HOT_KEY = "hotkey";
    String DEL_TRADE_TIME = "20210400112000";
}
