package com.zhumei.update;

import android.os.Build;
import android.os.Environment;


import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.util.UUID;

public class AppConstants {

    /**
     * 宁波本地版：V3.2.15 (目前最新版还是V3.2.14)
     * ZHUMEI_HOST + CONTROLER
     * SET_DEFAULT_TEMPLATE = 0
     * USE_HDP = 0
     * USE_INTFC_STATE = 0
     * SET_BOOT_UP_BUFFER = 1
     * SET_BOOT_UP_BUFFER_CUCLE = 20 * 1000
     */

    /**
     * 演示的时候修改的逻辑：
     * 交易数据写死传给 182、152以及24 这几个市场
     * 将成都市场的特殊逻辑注释掉
     */

    /**
     * 常用设置
     */
    public static class UsefulSetting {

        /**
         * 设置默认模版
         * 0 基本模版 可以设置武汉版
         * 1 秤屏联动模版 可以设置武汉版
         * 2 轮播模版
         * 3 真南版
         * 4 昆山版
         * 5 双屏异显(不让其推送切换模版，只在启动和登录页切换)
         * 6 昆山晨曦版
         */
        public static final int SET_DEFAULT_TEMPLATE = 0;
//        public static final int SET_DEFAULT_TEMPLATE = 1;

        /**
         * 设置对接的秤厂家
         * 0 佰伦斯   1 中商龙飞(鼎森)
         */
        public static final int SET_SCALE_VENDER = 1;

//        public static final int SET_SCALE_TYPE = 4;


        /**
         * 鼎森和龙飞不一样地方
         * 0 鼎森
         * 1 龙飞
         * 调完后 CommercialInfoElectronicScaleModelImpl 中 900行 954行还有需要调整的内容
         * <p>
         * 处理 dealWithJson()
         */
        public static final int SET_DS_DIFF = 1;

        /**
         * 蓝牙一帧发送20个字节数据 还是更多字节数据
         * 0 20个字节   1 更多字节
         */
        public static final int LIMIT_BYTE = 1;

        /**
         * 是否上传交易数据
         * 0 不上传  1 上传
         */
        public static final int TRADE_DATA_SWITCH = 1;

        /**
         * 安装HDP软件
         * 0 不安装   1 安装
         */
//        public static final int USE_HDP = 1;
        public static final int USE_HDP = 1;

        /**
         * 开启定时开关功能
         * 0 不开启   1 开启
         * 目前只有安致、龙飞的定时接好了 可以用
         * 弘卡、点坤的定时有问题,直接用自带的
         */
        public static final int OPEN_TIME_SWITCH = 0;

        /**
         * 智能秤对接的支付方式
         * 0 前端验签              成都北泉、桐乡乌镇(建行 龙支付  准备考虑替换掉)
         * 1 后端验签              芜湖天香苑、滨江山庄(兴业银行 威富通第三方支付)
         * 2 同左下角的静态二维码支付
         * 3 银行支付公调接口       盘古山市场(中国银联)
         */
        public static final int SET_SCALE_PAY_TYPE = 3;

        /**
         * 设置网络类型
         * 0表示有线网  1表示无线网  如果启动时没网 还是会刷新界面的
         * 如果是有线网 则下面的延迟启动时间短  6秒钟
         * 如果是无线网 则下面的延迟启动时间长  40秒钟
         */
        public static final int SET_NETWORK_TYPE = 0;

        /**
         * 开机后睡眠 延迟软件自启缓冲时间
         */
        public static final int SET_BOOT_UP_BUFFER_CUCLE_SHORT = 6 * 1000;
        public static final int SET_BOOT_UP_BUFFER_CUCLE_LONG = 40 * 1000;

        /**
         * 交易量为0的时候 是否显示交易量
         * 0 不显示   1 显示
         */
        public static final int SHOW_VOLUME = 1;

        /**
         * 显示铺位名称 或是铺位号
         * 0 显示铺位号  1 显示铺位名称(萍乡市场用到)
         */
        public static final int SHOW_STALL = 0;

        /**
         * 是否使用请求接口配置进行数据更新
         * 0 表示不使用，直接请求接口  1 表示使用
         */
        public static final int USE_INTFC_STATE = 1;
//        public static final int USE_INTFC_STATE = 0;

        /**
         * 是否使用测试  测试写死了模版编号，设备id，商户id，聚合码获取地址和生成地址
         * 0 表示不使用  1 表示使用
         */
        public static final int TEST = 0;

/**************************************************支付方式演示使用修改**************************************************/

        /**
         * 佰伦斯支付声音方式修改
         * 0 正常版本  1 萍乡版本(默认 因为萍乡的秤 点击支付宝或微信支付都是显示的是银行的二维码)
         */
        public static final int SET_BLS_PAY_TYPE = 1;

        /**
         * 支付方式的图片和标题设置 支付宝码 微信码 聚合码等
         */
        public static final int CHOOSE_PAY = ZSLF.WX_STATIC_QRCODE_PAY;

        /**
         * 0 表示不使用左下角二维码  1 表示使用左下角二维码  2 表示使用自定义url二维码  3 表示使用接口协议连接 wxp://xxx 或是qr.alipay.com/xxx
         * 0 表示使用支付宝接口协议连接   1 表示使用微信接口协议连接
         */
        public static final int SET_SCALE_PAY_QRCODE = 0;
        public static final int SET_SCALE_PAY_QRCODE_TYPE = 1;

        /**
         * 是否使用选定的支付方式 演示使用
         * 0 表示不使用  1 表示使用
         */
        public static final int SET_CHOOSE_PAY_TYPE = 0;

/**************************************************支付方式演示使用修改**************************************************/

        /**
         * 选择Log的等级 默认打开 不需要修改
         */

        /**
         * GreenDao Log开关
         */
        public static final boolean DEBUG_FLAG = true;
    }

    /**
     * 默认设置
     */
    public static class DefaultSetting {

        /**
         * 定时开关机 开机为6点到6点5分
         */
        public static final String SET_OPEN_DATE = "060";
        public static final String SET_OFF_DATE = "200000";

        /**
         * 是否因为网络原因需要再次请求 请求的次数
         */
        public static boolean REQUEST_AGAIN_REFRESH = true;
        public static boolean REFRESH_COUNT0 = true;

        /**
         * 默认密码
         */
        public static final String SET_PASSWORD = "883399";

        /**
         * 默认ip地址
         */
        public static final String SET_IP = "0.0.0.0";

        /**
         * 默认mac地址
         */
        public static final String SET_MAC = "02:00:00:00";

        /**
         * 默认星级
         */
        public static final int SET_RATING = 3;

        /**
         * 右下角菜价的列数
         */
        public static final int RECYCLERVIEW_SPAN_COUNT = 2;

        /**
         * GreenDao数据库名称
         */
        public static final String SCALE_DB_NAME = "scale.db";

        /**
         * 默认菜价条目数量
         */
        public static final int SET_COMMERCIAL_INFO_ITEM_COUNT = 6;
        public static final int SET_COMMERCIAL_INFO_PRICE_DISPLAY_ITEM_COUNT = 8;

        /**
         * 看电视的默认频道---浙江频道
         */
        public static final int SET_CHANNEL_NUM = 106;

        /**
         * Ble的最大Mtu值
         */
        public static final int SET_BLE_MTU = 250;

        /**
         * 支付完成动画的播放次数
         */
        public static final int SET_PAY_ANIM_LOOP_COUNT = 1;

        /**
         * 支付二维码显示出来的宽高
         */
        public static final int SET_QRCODE_WIDTH = 200;
        public static final int SET_QRCODE_HEIGHT = 200;
        public static final int SET_QRCODE_WIDTH_BIGER = 300;
        public static final int SET_QRCODE_HEIGHT_BIGER = 300;

        /**
         * ping百度的频率  3分钟
         */
        public static final int SET_PING_BAIDU_CYCLE = 3 * 60 * 1000;

        /**
         * 每隔多长时间检测一次应用程序是否在后台  2分钟
         */
//        public static final int SET_ALIVE_CYCLE = 2 * 60 * 1000;
        public static final int SET_ALIVE_CYCLE = 1 * 60 * 1000;


        /**
         * 每隔多长时间从LoginActivity跳到其他Activity  15分钟
         */
        public static final int SET_LOGIN_JUMP_CYCLE = 15 * 60 * 1000;

        /**
         * 软件升级  10分钟内
         */
        public static final int SET_RANDOM_UPDATE_CYCLE = 10 * 60 * 1000;

        /**
         * 商户营业执照等图片轮播时间  3.5秒
         */
        public static final int SET_BANNER_DELAY_CYCLE_SHORT = 35 * 100;

        /**
         * 广告图片轮播时间  5秒
         */
        public static final int SET_BANNER_DELAY_CYCLE_MID = 5 * 1000;

        /**
         * MinBanner切换动画时间 3秒
         */
        public static final int SET_MIX_BANNER_ANIM_CYCLE = 3 * 1000;

        /**
         * 右上角商户或市场广告轮播时间  8秒
         */
        public static final int SET_BANNER_DELAY_CYCLE_LONG = 8 * 1000;

        /**
         * 右上角广告的轮播切换速度  2秒
         */
        public static final int SET_VIEWPAGER_SPEED_CYCLE = 2 * 1000;

        /**
         * 隐藏支付成功的动画图片  3秒
         */
        public static final int SET_QRCODE_DISAPPEAR_SHORT = 3 * 1000;
        public static final int SET_QRCODE_DISAPPEAR_LONG = 10 * 1000;

        // 影藏 支付二维码时间 超时
        public static final int SET_QRCODE_HIDE = 60 * 1000;


        /**
         * 网络连接超时时间
         */
        public static final int SET_CONNECT_TIMEOUT_CYCLE_LONG = 20 * 1000;
        public static final int SET_CONNECT_TIMEOUT_CYCLE_SHORT = 10 * 1000;

        /**
         * 蓝牙设置
         * 设置蓝牙重连次数
         * 设置蓝牙重连时间间隔
         * 设置连接超时时间
         * 扫描超时时间
         */
        public static final int SET_RECONNECT_COUNT = 20 * 1000;

        public static final int SET_RECONNECT_INTERVAL = 5 * 1000;
        public static final int SET_RECONNECT_OVERTIME = 10 * 1000;
        public static final int SET_OPERATE_TIMEOUT = 10 * 1000;
        public static final int SET_SCAN_TIMEOUT = 60 * 1000;

        /**
         * 蓝牙连接时间间隔 5秒 ->20
         */
        public static final int SET_BLUETOOTH_CONNECT_CYCLE = 20 * 1000;

        /**
         * 蓝牙重连时间间隔 1分钟
         */
        public static final int SET_BLUETOOTH_RECONNECT_CYCLE = 60 * 1000;

        /**
         * 交易数据上传时间间隔 4小时
         */
        public static final int SET_TRANSACTION_DATA_SUBMIT_CYCLE = 4 * 60 * 60 * 1000;

        /**
         * 菜价翻滚间隔时间  10秒
         */
        public static final int SET_PRICE_ROLL_OVER_CYCLE = 10 * 1000;

        /**
         * 登录页面或启动页面 的休眠时间 2秒
         */
        public static final int SET_LOGIN_SLEEP_CYCLE = 2 * 1000;

        /**
         * 设置最小崩溃间隔时间  20秒
         */
        public static final int SET_CRASH_CYCLE = 20 * 1000;

        /**
         * 进度滑动时的休眠时间 1毫秒
         */
        public static final int SET_PROGRESS_BAR_SLEEP_CYCLE = 60;

        /**
         * 蓝牙每次数据传输的休眠时间
         * 设置这里主要是让菜品上传更平稳，正确率更高
         */
        public static final int SET_BLE_SLEEP_LOW_CYCLE = 90;
        //        public static final int SET_BLE_SLEEP_HIGH_CYCLE = 20;
        public static final int SET_BLE_SLEEP_HIGH_CYCLE = 50;

        /**
         * 交易数据上传的休眠时间 20毫秒
         */
        public static final int SET_TRADE_DATA_UPLOAD_CYCLE = 20;

        /**
         * 最大图片缓存大小  200M
         */
        public static final long SET_IMAGE_MAX_DISK_CACHE_SIZE = 200 * 1024L * 1024L;

        /**
         * 字符串最大内存缓存大小  JAVA虚拟机能使用的最大内存 / 16
         */
        public static final long SET_STRING_MAX_MEMORY_CACHE_SIZE = Runtime.getRuntime().maxMemory() / 16;

        /**
         * 图片硬盘缓存标识
         */
        public static final String SET_DISK_CACHE_IMAGE_FLAG = SHP.PRODUCT_NAME + "_DISK_CACHE_IMAGE";

        /**
         * 字符串硬盘缓存标识
         */
        public static final String SET_DISK_CACHE_STR_FLAG = SHP.PRODUCT_NAME + "_DISK_CACHE_STR";

        /**
         * 根目录下的筑美目录
         */
        public static final String SET_ZHUMEI_COMMERCIAL_SCREEN_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + SHP.PRODUCT_SUMMARY;

        /**
         * 霸屏服务
         */
        public static final String KEEP_ALIVE_SERVICE = "com.zhumei.commercialscreen.ui.base.service.KeepAliveService";

        /**
         * ping百度，判断外网网络连接
         * ping筑美，判断内网网络连接
         */
        public static final String SET_BAIDU_URL = "http://www.baidu.com";
        public static final String SET_ZHUMEI_URL = "https://n.zhumei.net";

        /**
         * 蓝牙传输数据 默认编码方式
         */
        public static final String CHARSET_NAME = "GBK";
        //3 hour
        public static long SET_UPLOAD_TIME = 3 * 60 * 60 * 1000;
        //        public static long SET_UPLOAD_TIME =  30 * 1000;
        //交易 时间间隔
        public static long UPLOAD_TRADE = 2 * 60 * 1000;
        public static long UPLOAD_TRADE2 = 30 * 1000;

    }

    /**
     * 常用字段
     */
    public static class CommonStr {

        /**
         * null
         */
        public static final String EMPTY_STR = "";
        public static final String BLANK_STR = " ";
        public static final String LONG_BLANK_STR = "       ";
        public static final String NULL_STR = "null";
        public static final String POINT_STR = ".";
        public static final String COMMA_STR = ",";
        public static final String POINT_ZERO_STR = ".00";

        /**
         * true
         */
        public static final String TRUE_STR = "true";

        /**
         * weight_pcs
         */
        public static final String WEIGHT_PCS_STR = "weight_pcs";
        public static final String DEFAULT_WEIGHT_STR = "0.000";
        public static final String BACK_QUOTE = "}00";

        /**
         * unit_price
         */
        public static final String DEFAULT_UNIT_PRICE_STR = "0.00";

        /**
         * 否定 肯定
         */
        public static final String NO_STR = "0";
        public static final String YES_STR = "1";

        /**
         * 中括号 引号
         */
        public static final String MIDDLE_BRACKETS_QUOTATION_MARKS = "[\"\"]";
        public static final String MIDDLE_BRACKETS = "[]";

        /**
         * 版本符号
         */
        public static final String VERSION_STR = "V";

        /**
         * 坐标类型
         */
        public static final String COOR_TYPE = "bd09ll";

        /**
         * 如何秤摊位号和屏摊位号不相同 传给后台的商户名
         */
        public static final String DEFAULT_MERCHANT_NAME = "未确定";
    }

    public static class CommonInt {

        /**
         * 否定 肯定
         */
        public static final int NO_INT = 0;
        public static final int YES_INT = 1;
        public static final int OTHER_INT = 2;
        public static final int OTHER3_INT = 3;

        public static final int SU_YUAN = 4;


        /**
         * 保留两位小数的零
         */
        public static final double KEEP_TWO_DECIMAL_DIGITS_ZERO = 0.00;
    }

    /**
     * 电子秤蓝牙 对接的五种数据类型
     * 实时称重 累加累减 结账 全清 显示二维码
     */
    public static class ScaleBLEEventType {

        public static final String CMD_1 = "\"cmd\":1";
        public static final String CMD_2 = "\"cmd\":2";
        public static final String CHECK_OUT = "\"cmd\":3";
        public static final String ALL_CLEAR = "\"cmd\":4";
        public static final String SHOW_QRCODE = "\"cmd\":5";
        public static final String SHOW_REGISTER = "\"cmd\":6";
        public static final String SHOW_WEIGHT = "\"cmd\":7";
        public static final String GET_HOTKEY = "\"cmd\":b";
        public static final String GET_GOODS = "\"cmd\":c";
        public static final String GET_TICKET = "\"cmd\":d";

        public static final String SCALE_HOTKEYS_CODE = "1";
        public static final String SCALE_GOODS_CODE = "2";
        public static final String SCALE_TRADE_NO_CODE = "3";
        public static final String SCALE_TICKET_CODE = "4";
        public static final String SCALE_TIME_CODE = "a";

        public static final String SCALE_PREFIX_5 = "!0000";
        public static final String SCALE_PREFIX_4 = "!000";
        public static final String SCALE_PREFIX_3 = "!00";
        public static final String SCALE_PREFIX_2 = "!0";
        public static final String SCALE_PREFIX_1 = "!";

        public static final int SCALE_STR_LENGTH_1 = 1;
        public static final int SCALE_STR_LENGTH_2 = 2;
        public static final int SCALE_STR_LENGTH_3 = 3;
        public static final int SCALE_STR_LENGTH_4 = 4;

    }

    /**
     * 推送的几种类型
     */
    public static class PushEventType {

        public static final String PUSH_COMMERCIAL_MARQUEE = "\"event_type\":\"0\"";
        public static final String PUSH_MARKET_MARQUEE = "\"event_type\":\"1\"";
        public static final String PUSH_PRICE = "\"event_type\":\"2\"";
        public static final String PUSH_BASE = "\"event_type\":\"3\"";
        public static final String PUSH_VOLUME = "\"event_type\":\"4\"";
        public static final String PUSH_THUMB = "\"event_type\":\"5\"";
        public static final String PUSH_TEMPLATE = "\"event_type\":\"6\"";
        public static final String PUSH_AD = "\"event_type\":\"7\"";
        public static final String PUSH_SETTING = "\"event_type\":\"8\"";
        public static final String PUSH_UPDATE = "\"event_type\":\"9\"";
        public static final String PUSH_SETTING_PRICE = "\"event_type\":\"10\"";
        public static final String PUSH_PAYMENT_SUCCESSFUL_CALLBACK = "\"event_type\":\"11\"";
        public static final String PUSH_TIME_SWITCH = "\"event_type\":\"12\"";
        public static final String PUSH_SCREEN_SHOT = "\"event_type\":\"13\"";
        public static final String PUSH_SCALE_HOTKEY = "\"event_type\":\"14\"";
        public static final String PUSH_SCALE_PRICE = "\"event_type\":\"15\"";
        public static final String PUSH_SCALE_TICKET = "\"event_type\":\"16\"";
        public static final String PUSH_PHOTO_ADD = "\"event_type\":\"17\"";
        public static final String PUSH_SCALE_HOTKEY_PRICE_PHONE = "\"event_type\":\"18\"";
        public static final String PUSH_SCALE_TICKET_PHONE = "\"event_type\":\"19\"";


    }

    public static class BLS {

        /**
         * 支付方式设置
         */
        public static final int CASH_PAY = 0;
        public static final int WX_PAY = 1;
        public static final int ZFB_PAY = 2;
        public static final int MEMBERSHIP_CARD_PAY = 3;
        public static final int OTHER_PAY = 99;

        /**
         * 蓝牙建立链接需要的UUID
         * 看开发文档 需要跟蓝牙厂家对接的
         */
        public static final UUID SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
        public static final UUID NOTIFY_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");

        /**
         * 换行符
         */
        public static final String LINE_BREAK_HEX = "0a";
        public static final String LINE_BREAK = "\n";

        /**
         * 尾部多余字符
         */
        public static final int TAIL_REDUNDANT_CHARACTERS = 3;
    }

    public static class ZSLF {

        /**
         * 支付方式设置
         */
        public static final int CASH_PAY = 0;
        public static final int MEMBERSHIP_CARD_PAY = 1;
        public static final int WX_STATIC_QRCODE_PAY = 2;
        public static final int WX_DYNAMIC_QRCODE_PAY = 3;
        public static final int ZFB_STATIC_QRCODE_PAY = 4;
        public static final int ZFB_DYNAMIC_QRCODE_PAY = 5;
        public static final int AGGREGATE_STATIC_QRCODE_PAY = 6;
        public static final int AGGREGATE_DYNAMIC_QRCODE_PAY = 7;
        //        public static final int UNPAID = 98;
        public static final int UNPAID = 999;

        public static final int OTHER_PAY = 99;

        /**
         * 蓝牙建立链接需要的UUID
         * 需要跟秤的蓝牙厂家对接开发文档
         */
        public static final UUID SERVICE_UUID = UUID.fromString("00001000-0000-1000-8000-00805f9b34fb");
        public static final UUID NOTIFY_UUID = UUID.fromString("00001002-0000-1000-8000-00805f9b34fb");
        public static final UUID WRITE_UUID = UUID.fromString("00001001-0000-1000-8000-00805f9b34fb");

        /**
         * 换行符
         */
        public static final String LINE_BREAK_HEX = "0d0a";
        public static final String LINE_BREAK = "\r\n";

        /**
         * 默认的热键信息
         */
        public static final String DEFAULT_SCALE_HOTKEYS_CONTENT = "{\"1\",\"16807\",\"\"}{\"2\",\"16808\",\"\"}";
        public static final String DEFAULT_SCALE_HOTKEYS = "!00401{\"1\",\"16807\",\"\"}{\"2\",\"16808\",\"\"}";

        public static final String DEFAULT_SCALE_HOTKEYS1 = "!004111{\"1\",\"16807\",\"\"}{\"2\",\"16808\",\"\"}";

        /**
         * 默认的小票信息
         */
        public static final String DEFAULT_SCALE_TICKET = "!00974{\"1\",\"0\",\"0\",\"溯源小票\"}{\"0\",\"1\",\"0\",\"技术支持：\"}{\"0\",\"1\",\"0\",\"杭州筑美信息科技有限公司\"}";

        /**
         * 尾部多余字符
         */
        public static final int TAIL_REDUNDANT_CHARACTERS = 4;
    }

    public static class SHDS {

        /**
         * 蓝牙建立链接需要的UUID
         * 需要跟秤的蓝牙厂家对接开发文档
         * SERVICE_UUID  服务UUID
         * NOTIFY_UUID   读UUID
         * WRITE_UUID    写UUID
         */
        public static final UUID SERVICE_UUID = UUID.fromString("49535343-FE7D-4AE5-8FA9-9FAFD205E455");
        public static final UUID NOTIFY_UUID = UUID.fromString("49535343-1E4D-4BD9-BA61-23C647249616");
        public static final UUID WRITE_UUID = UUID.fromString("49535343-8841-43F4-A8D4-ECBE34729BB3");
    }

    /**
     * 电子秤支付及显示
     */
    public static class ScalePay {

        /**
         * 用户V1和V2和V3
         */
        public static final String USER_V1 = "V1";
        public static final String USER_V2 = "V2";
        public static final String USER_V3 = "V3";
        public static final String USER_V4 = "V4";
        public static final String USER_V5 = "V5";

        /**
         * 称重后删除操作(一个空格)
         */
        public static final String USER_WEIGH_DEL = " ";

        /**
         * 最小的电子秤称重重量
         */
        public static final double MIN_SCALE_WEIGHT = 0.002;

        /**
         * 多余字符
         */
        public static final int LEFT_CHAR = 6;

        /**
         * 头部多余字符
         */
        public static final int HEAD_LEFT_CHAR = 5;

        /**
         * 负号
         */
        public static final String NEGATIVE_SIGN = "-";

        /**
         * 保留两位小数的零
         */
        public static final String KEEP_TWO_DECIMAL_DIGITS_ZERO = "0.00";

        /**
         * 小数点
         */
        public static final String DOT = "dot";
        public static final String DOT_POINT = ".";
        public static final int BLE_LIMIT_LENGTH = 244;
    }

    /**
     * 电子秤语音
     */
    public static class ScaleVoice {

        /**
         * 播放assets目录下的音频文件
         */
        public static final String TTS_PATH = "tts";

        /**
         * 收款成功
         */
        public static final String SUCCESS_ZFB = "success_zfb";
        public static final String SUCCESS_WX = "success_wx";
        public static final String SUCCESS_OTHERS = "success_others";
        public static final String PLEASE_PLAY = "please_pay";

        public static final String PLAY_FAIL = "pay_fail";


        /**
         * 金额拆分的数字
         */
        private static final String TEN = "ten";
        private static final String HUNDRED = "hundred";
        private static final String THOUSAND = "thousand";
        public static final String[] UNITS = {THOUSAND, HUNDRED, TEN};

        public static final String TEN_THOUSAND = "ten_thousand";
        public static final String TEN_MILLION = "ten_million";
        public static final String DOT = "dot";
        public static final String YUAN = "yuan";
    }

    /**
     * 交易数据来源
     */
    public static class TradeDataSources {

        /**
         * 中商龙飞蓝牙
         */
        public static final int ZSLF_BLE = 1;

        /**
         * 佰伦斯蓝牙
         */
        public static final int BLS_BLE = 2;
    }


    /**
     * 模版类型
     */
    public static class TemplateType {

        /**
         * 0 基本模版 可以设置武汉版
         * 1 秤屏联动模版 可以设置武汉版
         * 2 轮播模版
         * 3 真南版
         * 4 昆山版
         * 5 双屏异显(不让其推送切换模版，只在启动和登录页切换)
         * 6 昆山晨曦版
         */
        public static final int COMMERCIAL_INFO = 0;
        public static final int COMMERCIAL_INFO_ELECTRONIC_SCALE = 1;
        public static final int BANNER_AD = 2;
        public static final int COMMERCIAL_INFO_PRICE_DISPLAY = 3;
        public static final int MARKET_INFO = 4;
        public static final int COMMERCIAL_INFO_DOUBLE_SCREEN = 5;
        public static final int COMMERCIAL_INFO_CX = 6;
    }

    /**
     * 针对商户的广告类型设置
     */
    public static class AdType {

        /**
         * 0 没有广告
         * 1 全屏广告
         * 2 右上角广告
         */
        public static final String NO_AD_TYPE = "0";
        public static final String COMMERCIAL_AD_TYPE = "1";
        public static final String RIGHT_TOP_PIC_AD_TYPE = "2";
    }

    public static class DeviceType {

        /**
         * 设备类型参数：显示其他 原因是前端页面的值没有传递过去
         * 1 商户屏设备
         * 3 媒体屏
         * 4 拼接屏
         * 6 手持终端
         * 7 手持终端手机版
         */
        public static final String COMMERCIAL_SCREEN = "1";
    }

    public static class PushDeviceType {

        /**
         * 1 媒体
         * 2 商户屏
         * 3 手持终端
         * 4 电子秤
         * 5 android触摸屏
         */
        public static final String COMMERCIAL_SCREEN = "2";
    }

    /**
     * android主板类型
     */
    public static class BoradType {

        public static final String BORAD_TYPE = Build.BRAND + "_" + Build.MODEL;

        /**
         * 安致兰德
         */
        public static final String AZLD = "Allwinner_magton";

        /**
         * 弘卡
         */
        public static final String ZC_20A = "softwinners_SoftwinerEvb";
        public static final String ZC_83A = "Allwinner_ZC-83A";
        public static final String ZC_328 = "Allwinner_ZC-328";

        /**
         * 龙飞 A64
         */
        public static final String ZSLF_A64 = "Allwinner_DRCC-A64";

        /**
         * 台电平板
         */
        public static final String TECLAST_PAD = "Teclast_P10";

        /**
         * 盐城项目rk312x
         */
        public static final String YANCHENG_PROJECT = "rockchip_rk312x";

        /**
         * 台衡L10 android秤
         */
        public static final String T_SCALE_L10 = "T-Scale_P_10-10_TS100";

        /**
         * 银豹项目 朗国主板
         */
        public static final String MStar_TV = "MStar_MStar Android TV";

        /**
         * 海康威视 RK3126C
         */
        public static final String HKWS = "Android_rk3126c";
    }

    /**
     * 主板类型标志位
     * 弘卡的定时开关机代码移除
     */
    public static class BoradTypeFlag {

        public static final int UNKNOWN = 0;
        public static final int AZLD = 1;
        public static final int HK = 2;
        public static final int ZSLF = 3;
    }

    /**
     * SHP相关
     */
    public static class SHP {

        public static final String PRODUCT_NAME = "SHP";
        private static final String PRODUCT_SUMMARY = "zhumei_" + PRODUCT_NAME.toLowerCase();
    }

    /**
     * HDP相关
     */
    public static class HDP {

        public static final String PLAYER_NAME = "HDP";
        public static final String PLAYER_PACKAGE_NAME = "hdpfans.com";
        public static final String PLAYER_APP_NAME = PLAYER_NAME + ".apk";
        public static final String PLAYER_TEMP_APP_NAME = "_temp.apk";

        /**
         * HDP调起的 Intent配置
         */
        public static final String INTENT_ACTION = "com.hdpfans.live.start";
    }

    /**
     * EventBus的各种事件类型
     */
    public static final class EventCode {

        public static final int INTERNET_DISCONNECTED4ICON = 0x0001;
        public static final int INTERNET_CONNECTED4ICON = 0x0002;
        public static final int VIEWPAGER_PAGE_NEXT = 0x0003;
        public static final int GET_MARKET_NUM_FAIL = 0x0004;
        public static final int GET_MARKET_NUM_SUCCESS = 0x0005;
        public static final int PRICE_DELETE_FAIL = 0x0006;
        public static final int PRICE_DELETE_SUCCESS = 0x0007;
        public static final int PRICE_CHANGE_FAIL = 0x0008;
        public static final int PRICE_CHANGE_SUCCESS = 0x0009;
        public static final int GET_DEVICE_ID_FAIL = 0x000A;
        public static final int GET_DEVICE_ID_SUCCESS = 0x000B;
        public static final int GET_LOGO_TITLE_FAIL = 0x000C;
        public static final int GET_LOGO_TITLE_SUCCESS = 0x000D;
        public static final int GET_MARKET_NUM_QRCODE_FAIL = 0x000E;
        public static final int GET_MARKET_NUM_QRCODE_SUCCESS = 0x000F;
        public static final int GET_COMMERCIAL_INFO_BASE_DATA_FAIL = 0x0010;
        public static final int GET_COMMERCIAL_INFO_BASE_DATA_SUCCESS = 0x0011;
        public static final int GET_COMMERCIAL_INFO_REALTIME_DATA_FAIL = 0x0012;
        public static final int GET_COMMERCIAL_INFO_REALTIME_DATA_SUCCESS = 0x0013;
        public static final int GET_COMMERCIAL_INFO_VOLUME_DATA_FAIL = 0x0014;
        public static final int GET_COMMERCIAL_INFO_VOLUME_DATA_SUCCESS = 0x0015;
        public static final int GET_COMMERCIAL_INFO_ELECTRONIC_SCALE_BASE_DATA_FAIL = 0x0016;
        public static final int GET_COMMERCIAL_INFO_ELECTRONIC_SCALE_BASE_DATA_SUCCESS = 0x0017;
        public static final int GET_COMMERCIAL_INFO_ELECTRONIC_SCALE_REALTIME_DATA_FAIL = 0x0018;
        public static final int GET_COMMERCIAL_INFO_ELECTRONIC_SCALE_REALTIME_DATA_SUCCESS = 0x0019;
        public static final int GET_COMMERCIAL_INFO_ELECTRONIC_SCALE_VOLUME_DATA_FAIL = 0x001A;
        public static final int GET_COMMERCIAL_INFO_ELECTRONIC_SCALE_VOLUME_DATA_SUCCESS = 0x001B;
        public static final int GET_PASSWORD_FAIL = 0x001C;
        public static final int GET_PASSWORD_SUCCESS = 0x001D;
        public static final int GET_TEMPLATE_CODE_FAIL = 0x001E;
        public static final int GET_TEMPLATE_CODE_SUCCESS = 0x001F;
        public static final int GETUI_MSG = 0x0020;
        public static final int UPDATE_FAIL = 0x0021;
        public static final int UPDATE_SUCCESS = 0x0022;
        public static final int GET_PUBLIC_IP_FAIL = 0x0023;
        public static final int GET_PUBLIC_IP_SUCCESS = 0x0024;
        public static final int GET_BANNER_AD_DATA_FAIL = 0x0025;
        public static final int GET_BANNER_AD_DATA_SUCCESS = 0x0026;
        public static final int OPEN_BLUETOOTH_REQUEST_CODE = 0x0027;
        public static final int GET_COMMERCIAL_INFO_PRICE_DISPLAY_BASE_DATA_FAIL = 0x0028;
        public static final int GET_COMMERCIAL_INFO_PRICE_DISPLAY_BASE_DATA_SUCCESS = 0x0029;
        public static final int GET_COMMERCIAL_INFO_PRICE_DISPLAY_REALTIME_DATA_FAIL = 0x002A;
        public static final int GET_COMMERCIAL_INFO_PRICE_DISPLAY_REALTIME_DATA_SUCCESS = 0x002B;
        public static final int GET_COMMERCIAL_INFO_PRICE_DISPLAY_VOLUME_DATA_FAIL = 0x002C;
        public static final int GET_COMMERCIAL_INFO_PRICE_DISPLAY_VOLUME_DATA_SUCCESS = 0x002D;
        public static final int RECYCLERVIEW_START_SCROLL = 0x002E;
        public static final int GET_INTFC_STATE_FAIL = 0x002F;
        public static final int GET_INTFC_STATE_SUCCESS = 0x0030;
        public static final int GET_COMMERCIAL_INFO_ELECTRONIC_SCALE_MERCHANT_PAY_CONFIG_FAIL = 0x0031;
        public static final int GET_COMMERCIAL_INFO_ELECTRONIC_SCALE_MERCHANT_PAY_CONFIG_SUCCESS = 0x0032;
        public static final int BLE_DEVICE = 0x0033;
        public static final int BLE_GATT = 0x0034;
        public static final int SET_TIME_SWITCH_FAIL = 0x0035;
        public static final int SET_TIME_SWITCH_SUCCESS = 0x0036;
        public static final int SUBMIT_TRADE_DATA_FAIL = 0x0037;
        public static final int SUBMIT_TRADE_DATA_SUCCESS = 0x0038;
        public static final int MIX_BANNER = 0x0039;
        public static final int GET_MARKET_INFO_DATA_FAIL = 0x003A;
        public static final int GET_MARKET_INFO_DATA_SUCCESS = 0x003B;
        public static final int SHOW_DEFAULT_WEIGHT_PCS = 0x003C;
        public static final int SHOW_WEIGHT_PCS = 0x003D;
        public static final int SHOW_DEFAULT_UNIT_PRICE = 0x003E;
        public static final int SHOW_UNIT_PRICE = 0x003F;
        public static final int SHOW_DEFAULT_SUBTOTAL_PRICE = 0x0040;
        public static final int SHOW_SUBTOTAL_PRICE = 0x0041;
        public static final int GET_SCREEN_DATA_FAIL = 0x0042;
        public static final int GET_SCREEN_DATA_SUCCESS = 0x0043;

        public static final int NOTICE_CONNECT_BLE = 0x0044;
        public static final int NET_WORk = 2222;
    }

    /**
     * Cache的键
     */
    public static final class Cache {

        public static final String MARKET_NUM = "marketNum";
        public static final String MARKET_NUMS = "marketNums";
        public static final String STALL_NUM = "stallNum";
        public static final String QRCODE = "qrcode";
        public static final String REAL_STALL_NUM = "realStallNum";
        public static final String DEVICE_ID = "deviceId";
        public static final String MERCHANT_ID = "merchantId";
        public static final String CLIENT_ID = "clientId";
        public static final String MARKET_ID = "marketId";
        public static final String PUBLIC_IP = "publicIp";
        public static final String INTFC_STATE = "intfcState";
        public static final String TIME_SWITCH = "timeSwitch";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String TEMPLATE_CODE = "templateCode";
        public static final String PRICE_CONFIG = "priceConfig";
        public static final String MERCHANT_NAME = "merchantName";
        // 蓝牙的mac地址
        public static final String BLE_MAC_ADDR = "bleMacAddr";
        public static final String BLE_DATA = "bleData";

        public static final String BANNER_COMMERCIAL_AD_DATA = "bannerCommercialAdData";
        public static final String BANNER_MARKET_AD_DATA = "bannerMarketAdData";
        public static final String MARKET_NUM_QRCODE = "marketNumQrcode";
        public static final String LOGO_TITLE_CONTENT = "logoTitleContent";
        public static final String COME_FROM_ACTIVITY = "comeFromActivity";
        public static final String VOLUME_DATA = "volumeData";
        public static final String SOFTWARE_UPDATE = "softwareUpdate";

        public static final String SCALE_PAY_BANK_MERCHANT_ID = "bankMerchantId";
        public static final String SCALE_PAY_PUB = "pub";
        public static final String SCALE_PAY_POSID = "posId";
        public static final String SCALE_PAY_BRANCHID = "branchId";
        public static final String SCALE_PAY_CURCODE = "curCode";
        public static final String SCALE_PAY_TXCODE = "txCode";
        public static final String SCALE_PAY_RETURNTYPE = "returnType";
        public static final String SCALE_PAY_QRCODE = "qrCode";
        public static final String SCALE_PAY_REMARK1 = "remark1";
        public static final String SCALE_PAY_REMARK2 = "remark2";
        public static final String SCALE_PAY_TIMEOUT = "timeout";
        public static final String SCALE_PAY_TRADENO = "tradeNo";

        public static final String SCALE_KEY_INFAC_MODIFIED = "scaleKeyInfacModified";
        public static final String SCALE_GOODS_INFAC_MODIFIED = "scaleGoodsInfacModified";
        public static final String SCALE_TICKET_INFAC_MODIFIED = "scaleTicketInfacModified";
        public static final String SCALE_KEY_PUSH_MODIFIED = "scaleKeyPushModified";
        public static final String SCALE_GOODS_PUSH_MODIFIED = "scaleGoodsPushModified";
        public static final String SCALE_TICKET_PUSH_MODIFIED = "scaleTicketPushModified";

        public static final String COMMERCIAL_INFO_BASE_DATA = "commercialInfoBaseData";
        public static final String COMMERCIAL_INFO_REALTIME_DATA = "commercialInfoRealTimeData";
        public static final String COMMERCIAL_INFO_VOLUME_DATA = "commercialInfoVolumeData";
        public static final String MARKET_INFO_DATA = "marketInfoData";
        public static final String COMMERCIAL_INFO_ELECTRONIC_SCALE_MERCHANT_PAY_CONFIG = "commercialInfoElectronicScaleMerchantPayConfig";
        public static final String GET_SCREEN_DATA = "getScreenData";

//        public static final String EXIT_PASSWORD = "exitPassword";
//        public static final String TV_PASSWORD = "tvPassword";
//        public static final String PRICE_CHANGE_PASSWORD = "priceChangePassword";
//        public static final String BLE_SETTING_PASSWORD = "bleSettingPassword";
//        public static final String DEBUG_CAMERA_PASSWORD = "debugCameraPassword";

        public static final String ACCESS_LOGO_TITLE = "accessLogoTitle";
        public static final String ACCESS_PASSWORD = "accessPassword";
        public static final String ACCESS_DEVICE_ID = "accessDeviceId";
        public static final String ACCESS_TEMPLATE_CODE = "accessTemplateCode";
        public static final String ACCESS_UPDATE = "accessUpdate";
        public static final String ACCESS_TIME_SWITCH = "accessTimeSwitch";
        public static final String ACCESS_MARKET_NUM = "accessMarketNum";
        public static final String ACCESS_MARKET_NUM_QRCODE = "accessMarketNumQrcode";
        public static final String ACCESS_COMMERCIAL_INFO = "accessCommercialInfo";
        public static final String ACCESS_COMMERCIAL_INFO_ELECTRONIC_SCALE = "accessCommercialInfoElectronicScale";
        public static final String ACCESS_COMMERCIAL_INFO_PRICE_DISPLAY = "accessCommercialInfoPriceDisplay";

        public static final String NOT_FIRST_CONN_BLE = "notFirstConnBle";

        public static final String SCALE_COMMERCIAL_BASE_DATA = "scaleCommercialBaseData";
        public static final String SCALE_COMMERCIAL_VOLUME_DATA = "scaleCommercialVolumeData";
        public static final String SCALE_COMMERCIAL_PRICE_DATA = "scaleCommercialPriceData";
        public static final String SCALE_COMMERCIAL_DISH_DATA = "scaleCommercialDishData";
        public static final String SCALE_MARQUEE_DATA = "scaleMarqueeData";
        public static final String SCALE_AD_DATA = "scaleAdData";

        /***
         *  存入对象
         * */
//        public static final String INFO_BASE_DATA = "infoData";
    }

    /**
     * 市场id
     */
    public static final class MarketId {

        /**
         * 衢州斗谭农贸市场 市场id = 1
         */
        public static final String ZJ_QZ_DT = "1";
        /**
         * 衢州荷花农贸市场 市场id = 2
         */
        public static final String ZJ_QZ_HH = "2";
        /**
         * 衢州南湖农贸市场 市场id = 3
         */
        public static final String ZJ_QZ_NH = "3";
        /**
         * 衢州九号桥农贸市场 市场id = 4
         */
        public static final String ZJ_QZ_JHQ = "4";
        /**
         * 衢州美俗坊农贸市场 市场id = 5
         */
        public static final String ZJ_QZ_MSF = "5";
        /**
         * 衢州东门农贸市场 市场id = 6
         */
        public static final String ZJ_QZ_DM = "6";
        /**
         * 衢州安居农贸市场 市场id = 7
         */
        public static final String ZJ_QZ_AJ = "7";
        /**
         * 温州新田园农贸市场 市场id = 8
         */
        public static final String ZJ_WZ_XTY = "8";
        /**
         * 上海宁波路农贸市场 市场id = 9
         */
        public static final String SH_NBL = "9";
        /**
         * 杭州西兴共联农贸市场 市场id = 10
         */
        public static final String ZJ_HZ_XXGL = "10";
        /**
         * 温州慈湖北村农贸市场 市场id = 12
         */
        public static final String ZJ_WZ_CHBC = "12";
        /**
         * 杭州亲菜农贸市场 市场id = 13
         */
        public static final String ZJ_HZ_QC = "13";
        /**
         * 安阳安泰苑农贸市场 市场id = 14
         */
        public static final String HN_AY_ATY = "14";
        /**
         * 杭州晶都农贸市场 市场id = 15
         */
        public static final String ZJ_HZ_JD = "15";
        /**
         * 南通三里墩农贸市场 市场id = 16
         */
        public static final String JS_NT_SLD = "16";
        /**
         * 郑州四大街农贸市场 市场id = 17
         */
        public static final String HN_ZZ_SDJ = "17";
        /**
         * 南昌九龙湖农贸市场 市场id = 18
         */
        public static final String JX_NC_JNH = "18";
        /**
         * 杭州市北农贸市场 市场id = 19
         */
        public static final String ZJ_HZ_SB = "19";
        /**
         * 杭州德圣农贸市场 市场id = 20
         */
        public static final String ZJ_HZ_DS = "20";
        /**
         * 杭州农贸示范市场 市场id = 26
         */
        public static final String ZJ_HZ_SF = "26";
        /**
         * 庆元香菇农贸市场 市场id = 27
         */
        public static final String ZJ_QY_XG = "27";
        /**
         * 舟山东港阳光市场 市场id = 28
         */
        public static final String ZJ_ZS_DG = "28";
        /**
         * 杭州南门市场 市场id = 29
         */
        public static final String ZJ_HZ_NM = "29";
        /**
         * 杭州瓜沥农贸市场 市场id = 30
         */
        public static final String ZJ_HZ_GL = "30";
        /**
         * 上海三门农贸市场 市场id = 31
         */
        public static final String SH_SM = "31";
        /**
         * 徐州灯塔农贸市场 市场id = 32
         */
        public static final String JS_XZ_DT = "32";
        /**
         * 合肥康园农贸市场 市场id = 33
         */
        public static final String AH_HF_KY = "33";
        /**
         * 合肥胜利路农贸市场 市场id = 34
         */
        public static final String AH_HF_SLL = "34";
        /**
         * 益阳南洲农贸市场 市场id = 35
         */
        public static final String HN_YY_NZ = "35";
        /**
         * 南京岔路农贸市场 市场id = 37
         */
        public static final String JS_NJ_CL = "37";
        /**
         * 南通世纪花城农贸市场 市场id = 38
         */
        public static final String JS_NT_SJHC = "38";
        /**
         * 镇江御桥嘉园农贸市场 市场id = 39
         */
        public static final String JS_ZJ_YQJY = "39";
        /**
         * 杭州定海农贸市场 市场id = 40
         */
        public static final String ZJ_HZ_DH = "40";
        /**
         * 杭州玉鸟农贸市场 市场id = 41
         */
        public static final String ZJ_HZ_YN = "41";
        /**
         * 郑州文化路农贸市场 市场id = 42
         */
        public static final String HN_ZZ_WHL = "42";
        /**
         * 连云港新华苑农贸市场 市场id = 44
         */
        public static final String JS_LYG_XHY = "44";
        /**
         * 杭州万寿亭农贸市场 市场id = 45
         */
        public static final String ZJ_HZ_WST = "45";
        /**
         * 杭州刀茅巷农贸市场 市场id = 46
         */
        public static final String ZJ_HZ_DMX = "46";
        /**
         * 杭州江南豪园农贸市场 市场id = 47
         */
        public static final String ZJ_HZ_JNHY = "47";
        /**
         * 南昌桃苑农贸市场 市场id = 48
         */
        public static final String JX_NC_TY = "48";
        /**
         * 南昌丁公路农贸市场 市场id = 49
         */
        public static final String JX_NC_DGL = "49";
        /**
         * 宁波大榭农贸市场 市场id = 51
         */
        public static final String ZJ_NB_DX = "51";
        /**
         * 舟山嵊泗农贸市场 市场id = 53
         */
        public static final String ZJ_ZS_SS = "53";
        /**
         * 毕节桂花农贸市场 市场id = 54
         */
        public static final String GZ_BJ_GH = "54";
        /**
         * 芜湖南陵春谷农贸市场 市场id = 55
         */
        public static final String AH_WH_NLCG = "55";
        /**
         * 萧山城西农贸市场 市场id = 91
         */
        public static final String ZJ_HZ_XSCX = "91";
        /**
         * 郑州鲜尚万嘉农贸市场 市场id = 92
         */
        public static final String HN_ZZ_XSWJ = "92";
        /**
         * 郑州市投集市农贸市场 市场id = 97
         */
        public static final String HN_ZZ_STJS = "97";
        /**
         * 郑州丰乐农贸市场 市场id = 100
         */
        public static final String HN_ZZ_FL = "100";
        /**
         * 郑州京沙农贸市场 市场id = 101
         */
        public static final String HN_ZZ_JS = "101";
        /**
         * 广州闪壁农贸市场 市场id = 103
         */
        public static final String GD_GZ_SB = "103";
        /**
         * 衢州人保 市场id = 105
         */
        public static final String ZJ_QZ_RB = "105";
        /**
         * 宁波日湖农贸市场 市场id = 106
         */
        public static final String ZJ_NB_RH = "106";
        /**
         * 嘉兴南江生活广场农贸市场 市场id = 112
         */
        public static final String ZJ_JX_NJ = "112";
        /**
         * 义乌词林菜市场 市场id = 113
         */
        public static final String ZJ_YW_CL = "113";
        /**
         * 上海真南农贸市场 市场id = 114
         */
        public static final String SH_ZN = "114";
        /**
         * 武汉琴断口农贸市场 市场id = 115
         */
        public static final String HB_WH_QDK = "115";
        /**
         * 温州松台农贸市场 市场id = 116
         */
        public static final String ZJ_WZ_ST = "116";
        /**
         * 义乌新马路农贸市场 市场id = 117
         */
        public static final String ZJ_YW_XML = "117";
        /**
         * 义乌凌云农贸市场 市场id = 118
         */
        public static final String ZJ_YW_LY = "118";
        /**
         * 义乌商贸区农贸市场 市场id = 119
         */
        public static final String ZJ_YW_SMQ = "119";
        /**
         * 义乌经济开发区农贸市场 市场id = 120
         */
        public static final String ZJ_YW_KFQ = "120";
        /**
         * 义乌江南农贸市场 市场id = 121
         */
        public static final String ZJ_YW_JN = "121";
        /**
         * 义乌青口农贸市场 市场id = 122
         */
        public static final String ZJ_YW_QK = "122";
        /**
         * 上海红光农贸市场 市场id = 125
         */
        public static final String SH_HG = "125";
        /**
         * 山东聊城辛屯农贸市场 市场id = 127
         */
        public static final String SD_LC_XT = "127";
        /**
         * 上海浦东新区农贸市场 市场id = 128
         */
        public static final String SH_PD = "128";
        /**
         * 萧山江寺农贸市场 市场id = 130
         */
        public static final String ZJ_HZ_XSJS = "130";
        /**
         * 萧山东门菜市场 市场id = 131
         */
        public static final String ZJ_HZ_XSDM = "131";
        /**
         * 萍乡流万智慧农贸市场 市场id = 132
         */
        public static final String JX_PX_LW = "132";
        /**
         * 南京新篁农贸市场 市场id = 138
         */
        public static final String JS_NJ_XH = "138";
        /**
         * 南京横梁农贸市场 市场id = 139
         */
        public static final String JS_NJ_HL = "139";
        /**
         * 重庆映江农副商城 市场id = 141
         */
        public static final String CQ_YJ = "141";
        /**
         * 嘉兴市中山农贸市场 市场id = 142
         */
        public static final String ZJ_JX_ZS = "142";
        /**
         * 宁波惠风农贸市场 市场id = 143
         */
        public static final String ZJ_NB_HF = "143";
        /**
         * 杭州骆家庄农贸市场 市场id = 144
         */
        public static final String ZJ_HZ_LJZ = "144";
        /**
         * 嘉兴云东农贸市场 市场id = 145
         */
        public static final String ZJ_JX_YD = "145";
        /**
         * 义乌佛堂下市农贸市场 市场id = 148
         */
        public static final String ZJ_YW_FTXS = "148";
        /**
         * 重庆千集汇洋河店农贸市场 市场id = 149
         */
        public static final String CQ_QJHYHD = "149";
        /**
         * 济南东城农贸市场 市场id = 150
         */
        public static final String SD_JN_DC = "150";
        /**
         * 阜阳新华城农贸市场 市场id = 151
         */
        public static final String AN_FY_XHC = "151";
        /**
         * 成都国泉北泉农贸市场 市场id = 152
         */
        public static final String SC_CD_GQBQ = "152";
        /**
         * 义乌后宅农贸市场 市场id = 153
         */
        public static final String ZJ_YW_HZ = "153";
        /**
         * 郑州渔果市集农贸市场 市场id = 155
         */
        public static final String HN_ZZ_YG = "155";
        /**
         * 杭州下沙龙湖 市场id = 156
         */
        public static final String ZJ_HZ_XSLH = "156";
        /**
         * 浙江农联农贸品质生活馆 市场id = 157
         */
        public static final String ZJ_HZ_NL = "157";
        /**
         * 淮北金百合菜市场 市场id = 158
         */
        public static final String AH_HB_JBH = "158";
        /**
         * 郑州燕庄农贸市场 市场id = 159
         */
        public static final String HN_ZZ_YZ = "159";
        /**
         * 重庆千集汇农贸批发市场 市场id = 160
         */
        public static final String CQ_QJHNP = "160";
        /**
         * 郑州永年农贸市场 市场id = 161
         */
        public static final String HN_ZZ_YN = "161";
        /**
         * 杭州蒋村花园农贸市场 市场id = 162
         */
        public static final String ZJ_HZ_JCHY = "162";
        /**
         * 杭州古荡农贸市场 市场id = 163
         */
        public static final String ZJ_HZ_GD = "163";
        /**
         * 芜湖天香苑农贸市场 市场id = 165
         */
        public static final String AN_WH_TXY = "165";
        /**
         * 芜湖滨江山庄农贸市场 市场id = 166
         */
        public static final String AN_WH_BJSZ = "166";
        /**
         * 武汉卓刀泉农贸市场 市场id = 167
         */
        public static final String HB_WH_ZDQ = "167";
        /**
         * 武汉永青汇农贸市场 市场id = 168
         */
        public static final String HB_WH_YQH = "168";
        /**
         * 武汉兴怡农贸市场 市场id = 169
         */
        public static final String HB_WH_XY = "169";
        /**
         * 盐城新都农贸市场 市场id = 170
         */
        public static final String JS_YC_XD = "170";
        /**
         * 郑州利农农贸市场 市场id = 171
         */
        public static final String HN_ZZ_LN = "171";
        /**
         * 昆山晨曦农贸市场 市场id = 172
         */
        public static final String JS_KS_CX = "172";
        /**
         * 舟山朱家尖农贸市场 市场id = 173
         */
        public static final String ZJ_ZS_ZJJ = "173";
    }

/**************************************************** 图片视频混播配置参数 ****************************************************/
    /**
     * 默认图片的播放时长
     */
    public static final int DEFAULT_PIC_DURATION = 5 * 1000;

    /**
     * ViewPager展示View的类型
     */
    public static final int VIEWPAGER_IMAGE = 0;
    public static final int VIEWPAGER_VIDEO = 1;

    /**
     * 产品名称
     */
    public static final String PRODUCT_NAME = "ADV";

    /**
     * 最大视频文件缓存数量
     */
    public static final int DEFAULT_VIDEO_MAX_DISK_CACHE_FILES_COUNT = 50;

    /**
     * 最大视频缓存大小 2G
     */
    public static final long DEFAULT_VIDEO_MAX_DISK_CACHE_SIZE = 2L * 1024L * 1024L * 1024L;

    /**
     * 视频硬盘缓存标识
     */
    public static final String DEFAULT_DISK_CACHE_VIDEO = PRODUCT_NAME + "_DISK_CACHE_VIDEO";

    /**
     * 默认轮播次数
     */
    public static final int DEFAULT_BANNER_COUNT = 0;

/**************************************************** 图片视频混播配置参数 ****************************************************/
}
