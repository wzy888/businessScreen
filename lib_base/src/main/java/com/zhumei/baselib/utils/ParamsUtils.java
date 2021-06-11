package com.zhumei.baselib.utils;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.zhumei.baselib.utils.para.CMD5Bean;
import com.zhumei.baselib.utils.para.OrderPayType;
import com.zhumei.baselib.utils.useing.software.LogUtils;


import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class ParamsUtils {
    private static String SALT = "izhumei2012";

    /* 参数： {stall_name=YZ-01, code=60600611}
    E/map==>: {"stall_name":"YZ-01","code":"60600611"}
    E/加密后==>: 6f6390416cdc6c7c08edff720ff4fa08*/
    public static String md5(HashMap<String, String> commonMd5) {


        TreeMap<String, String> map = new TreeMap<>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        map.clear();
        map.putAll(commonMd5);
        String s = GsonUtils.toJson(map);

        String sign = EncryptUtils.encryptMD5ToString(
                EncryptUtils.encryptMD5ToString(s)
                        .toLowerCase()
                        + SALT).toLowerCase();

        LogUtils.E("加密前", "参数： " + commonMd5);
        LogUtils.E("加密后==>", sign);


        return sign;
    }

    public static String getRandom() {
        String random = String.valueOf((int) (100000 + Math.random() * (999999 - 100000 + 1)));
        return random;
    }

    public static String getDeviceId() {
//        String uniqueID = UUID.randomUUID().toString();

        String uniqueDeviceId = DeviceUtils.getUniqueDeviceId();
        if (TextUtils.isEmpty(uniqueDeviceId)) {
            return "000000";
        }
//1d564dcd893583a21959242af302a3247
//1d564dcd893583a21959242af302a3247

        LogUtils.E("getDeviceId", uniqueDeviceId);

        return uniqueDeviceId;
    }


    public static OrderPayType getPayType(CMD5Bean cmd5Bean) {
        int payType = cmd5Bean.getPayType();
        OrderPayType orderPayType = new OrderPayType();
        //  1会员卡（cmd5 不需要）
//2微信静态码
//3微信动态码
//4支付宝静态码
//5支付宝动态码
//6聚合静态码
//7聚合动态码
//99其他
//            1004=动态聚合支付,
//            1005=静态聚合支付,
//            1006=支付宝静态支付,
//            1007=微信静态支付,
//            1000=微信被扫,
//            1001=支付宝被扫,
//            1002=现金,
//            1003=会员,
//            1008=聚合主扫,
//            1009=微信主扫,
//            1010=支付宝主扫
        switch (payType) {
            case 0:
                orderPayType.setType("1002");
                orderPayType.setPayTitle("现金支付");
                break;
            case 1:
                orderPayType.setType("1004");
                orderPayType.setPayTitle("动态聚合支付");
                break;
            case 2:
                orderPayType.setType("1007");
                orderPayType.setPayTitle("微信支付");

                break;
            case 3:
                orderPayType.setType("1009");
                orderPayType.setPayTitle("微信支付");
                break;
            case 4:
                orderPayType.setType("1006");
                orderPayType.setPayTitle("支付宝支付");
                break;
            case 5:
                orderPayType.setType("1010");
                orderPayType.setPayTitle("支付宝支付");
                break;
            case 6:
                orderPayType.setType("1005");
                orderPayType.setPayTitle("聚合静态码");
                break;
//            default:
//                orderPayType.setType("1004");
//                orderPayType.setPayTitle("动态聚合支付");
//                break;
        }


        return orderPayType;
    }
}
