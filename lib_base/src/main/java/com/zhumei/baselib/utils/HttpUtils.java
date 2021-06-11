package com.zhumei.baselib.utils;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhumei.baselib.BuildConfig;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.base.MmkvUtils;
import com.zhumei.baselib.bean.ble_setting.BleCmd2Bean;
import com.zhumei.baselib.bean.scale.ScaleTrade;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.config.ConstantApi;
import com.zhumei.baselib.module.localdata.LoginLocalData;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.module.response.MerchantInfo;
import com.zhumei.baselib.utils.para.CMD5Bean;
import com.zhumei.baselib.utils.para.OrderParams;
import com.zhumei.baselib.utils.para.TradeParas;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.event.ThreadManager;
import com.zhumei.baselib.utils.useing.hardware.HardwareUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.SoftwareUtils;
import com.zhumei.baselib.utils.useing.software.TimeUtils;
import com.zhumei.lib_network.okhttp.CallBackUtil;
import com.zhumei.lib_network.okhttp.OkhttpUtil;
//import com.zhumei.commercialscreen.MyApplication;
//import com.zhumei.commercialscreen.base.MmkvUtils;
//import com.zhumei.commercialscreen.bean.ble_setting.BleCmd2Bean;
//import com.zhumei.commercialscreen.bean.scale.ScaleTrade;
//import com.zhumei.commercialscreen.config.Constant;
//import com.zhumei.commercialscreen.config.ConstantApi;
//import com.zhumei.commercialscreen.entity.localdata.LoginLocalData;
//import com.zhumei.commercialscreen.entity.para.OrderParams;
//import com.zhumei.commercialscreen.entity.para.TradeParas;
//import com.zhumei.commercialscreen.entity.response.LoginRes;
//import com.zhumei.commercialscreen.entity.response.MerchantInfo;
//import com.zhumei.lib_network.okhttp.CallBackUtil;
//import com.zhumei.lib_network.okhttp.OkhttpUtil;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

public class HttpUtils {

    private static volatile HttpUtils singleton;

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (singleton == null) {
            synchronized (HttpUtils.class) {
                if (singleton == null) {
                    singleton = new HttpUtils();
                }
            }
        }
        return singleton;
    }


    public interface HttpCallBack {
        void onSuccess(String jsonData);

        void onFail(String errorMsg);
    }


    /**
     * 上传设备信息
     */
    public void updeviceinfomation(String clientId, String publicIp,
                                   String longitude, String latitude,
                                   LoginRes loginRes, final HttpCallBack callBack) {
        final String TAG = "deviceId";


        Context mContext = MyBaseApplication.getMyApplication();

        String boardType = AppConstants.BoradType.BORAD_TYPE;
        String bluetoothAddress = AppConstants.DefaultSetting.SET_MAC;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null && !TextUtils.isEmpty(bluetoothAdapter.getAddress())) {
            bluetoothAddress = bluetoothAdapter.getAddress();
        }
        if (AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.ZC_83A) || AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.ZC_20A) || AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.ZC_328)) {
            boardType = HardwareUtils.getPropStr("ro.pinhe.version");
        } else if (AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.AZLD)) {
            boardType = HardwareUtils.getPropStr("ro.product.firmware");
        } else if (AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.ZSLF_A64)) {
            boardType = HardwareUtils.getPropStr("ro.sys.cputype");
        } else if (AppConstants.BoradType.BORAD_TYPE.equals(AppConstants.BoradType.YANCHENG_PROJECT)) {
            boardType = AppConstants.BoradType.YANCHENG_PROJECT;
        }


        HashMap<String, String> params = new HashMap();
        try {
            params.put("wired_mac", HardwareUtils.getEthernetMacAddr());

            params.put("wifi_mac", HardwareUtils.getWIFIMacAddr());
            params.put("ble_mac", bluetoothAddress);

            params.put("reso_ratio", HardwareUtils.getScreenResolution(mContext).x
                    + "*" + HardwareUtils.getScreenResolution(mContext).y);

            params.put("hardware_id", HardwareUtils.getHardwareId(mContext));
            params.put("product_type", AppConstants.DeviceType.COMMERCIAL_SCREEN);
            params.put("soft_version", "V" + SoftwareUtils.getAppVersionName(mContext));
            params.put("device_version", Build.VERSION.RELEASE);
            params.put("display", Build.DISPLAY);
            params.put("board_type", boardType);
            params.put("client_id", clientId);
            params.put("publicIp", publicIp);
            params.put("longitude", longitude);
            params.put("latitude", latitude);
            params.put("uuid", ParamsUtils.getDeviceId());
            params.put("market_id", loginRes.getMarket_id() + "");
            params.put("merchant_id", loginRes.getMerchant_id() + "");
            params.put("stall_id", loginRes.getStall_id() + "");
            params.put("default_template", loginRes.getTemplate_id() + "");
            String sign = ParamsUtils.md5(params);
            params.put("sign", sign);
//            打印输出参数
            if (BuildConfig.DEBUG) {
                printParams(params);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        String url = ConstantApi.ZHUMEI_BASE_URL + ConstantApi.DEVICE_INFO;
        OkhttpUtil.okHttpPost(url,
                params,
                new CallBackUtil.CallBackDefault() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        if (callBack != null) {
                            callBack.onFail(e.getMessage());
                        }
                        LogUtils.e(TAG, "getDeviceId: ERROR!");
                    }

                    @Override
                    public void onResponse(Response response) {
                        if (callBack != null) {
                            try {
                                if (BuildConfig.DEBUG) {
                                    LogUtils.e(TAG, "getDeviceId: SUCCESS:" + response);
                                }

                                callBack.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });


    }

    private void printParams(HashMap<String, String> params) {
        //获取Map中的所有key
        Set<String> keySet = params.keySet();
        //遍历存放所有key的Set集合
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            //得到每一个key
            String key = it.next();
            //通过key获取对应的value
            String value = (String) params.get(key);
            System.out.println(key + "=" + value);
        }
    }


    public void deviceOnline() {
        try {

            String url = ConstantApi.ZHUMEI_BASE_URL + ConstantApi.DEVICE_ONLINE;
            if (BuildConfig.DEBUG) {
                LogUtils.e("device", url);
            }
            HashMap hashMap = new HashMap();
            hashMap.put("uuid", ParamsUtils.getDeviceId());
            hashMap.put("sign", ParamsUtils.md5(hashMap));
            if (BuildConfig.DEBUG) {
                LogUtils.e("paras", hashMap.toString());
            }
            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    try {
                        LogUtils.E("deviceOnline", e.getMessage());
                        SystemClock.sleep(500);
                        deviceOnline();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                @Override
                public void onResponse(String response) {
                    LogUtils.e("deviceOnline", response);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //支付 通用接口/api/orderpay/activepay
    public void activePay(CMD5Bean cmd5Bean, List<BleCmd2Bean> data, final HttpCallBack httpCallBack) {
        try {
            String url = ConstantApi.ZHUMEI_BASE_URL + ConstantApi.ACTIVEPAY;

            LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
            LoginRes loginRes = entity.getLoginRes();

//            MerchantInfo merchantInfo = CacheUtils.getEntity(Constant.MERCHANT_INFO, MerchantInfo.class);
            MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);


            String mSavedClientId = CacheUtils.getString(AppConstants.Cache.CLIENT_ID);

//            OrderPayType payType = ParamsUtils.getPayType(cmd5Bean);


            JSONArray jsonArray = new JSONArray();
            /**
             *  挂单数据
             * */
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    OrderParams orderParams = new OrderParams();
                    BleCmd2Bean bleCmd2Bean = data.get(i);
                    orderParams.setGoodsId(bleCmd2Bean.getItemNum());
                    orderParams.setGoodsName(bleCmd2Bean.getName());
                    orderParams.setSubTotal(bleCmd2Bean.getTotPrice() + "");
                    orderParams.setTradeUnit(bleCmd2Bean.getUnit());
                    orderParams.setUnitPrice(bleCmd2Bean.getUnitPrice() + "");
                    orderParams.setWeightPcs(bleCmd2Bean.getWeightPcs() + "");

                    if (TextUtils.isEmpty(bleCmd2Bean.getUnit())) {
                        orderParams.setUnitId("1053");
                    } else {
                        if (bleCmd2Bean.getUnit().contains("kg")) {
                            orderParams.setUnitId("1053");
                        } else {
                            orderParams.setUnitId("1057");

                        }
                    }
                    jsonArray.put(orderParams.getParams());
                }
            } else {
                /**
                 * 未挂单的单条数据
                 * */
                OrderParams orderParams = new OrderParams();
                orderParams.setGoodsId(cmd5Bean.getItem_num());
                orderParams.setGoodsName(cmd5Bean.getName());
                orderParams.setSubTotal(cmd5Bean.getPayMoney() + "");
                orderParams.setTradeUnit(cmd5Bean.getUnit());
                orderParams.setUnitPrice(cmd5Bean.getUnitPrice() + "");
                orderParams.setWeightPcs(cmd5Bean.getWeightPcs() + "");
                if (TextUtils.isEmpty(cmd5Bean.getUnit())) {
                    orderParams.setUnitId("1053");
                } else {
                    if (cmd5Bean.getUnit().contains("kg")) {
                        orderParams.setUnitId("1053");
                    } else {
                        orderParams.setUnitId("1057");
                    }
                }

                jsonArray.put(orderParams.getParams());
            }


            HashMap<String, String> hashMap = new HashMap();

            hashMap.put("merchant_id", String.valueOf(loginRes.getMerchant_id()));
            hashMap.put("tradeNo", cmd5Bean.getTradeNo());
            hashMap.put("amount", cmd5Bean.getPayMoney() + "");


            hashMap.put("pay_type", cmd5Bean.getPayType() + "");


//
            hashMap.put("detail", String.valueOf(jsonArray));

            hashMap.put("date", String.valueOf(TimeUtils.getCurrentSeconds()));
            hashMap.put("user_name", merchantInfo.getMerchant_name());
            hashMap.put("market_id", String.valueOf(loginRes.getMarket_id()));
            hashMap.put("client_sn", mSavedClientId);
            // 商户自己的二维码
            hashMap.put("authcode", "");
            hashMap.put("sign", ParamsUtils.md5(hashMap));

//            if (BuildConfig.DEBUG) {
//                LogUtils.e("activePay Url=> ", url);
//                printParams(hashMap);
//            }


            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    LogUtils.e(" activePay :" + e.getMessage());
                    if (httpCallBack != null) {
                        httpCallBack.onFail(e.getMessage());
                    }
                }

                @Override
                public void onResponse(String response) {
                    try {
                        if (TextUtils.isEmpty(response)) {
                            return;
                        }
                        if (BuildConfig.DEBUG) {
                            LogUtils.e(" activePay :" + response);
                        }

                        if (httpCallBack != null) {
                            httpCallBack.onSuccess(response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    ////api/doublescreen/cash/check_order_new
//    称重数据上传
    public void checkOrder(List<String> tradeNos, List<List<ScaleTrade>> tradeDataList, final HttpCallBack httpCallBack) {
        try {
            if (tradeDataList == null || tradeDataList.size() <= 0) {

                LogUtils.e("checkorder is NULL!");
                return;
            }

            String url = ConstantApi.ZHUMEI_BASE_URL + ConstantApi.CHECKORDER;

            LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
            LoginRes loginRes = entity.getLoginRes();
            MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);


            ArrayList<TradeParas.TradeDataBean> tradeDataBeans = new ArrayList<>();
            tradeDataBeans.clear();
            for (int i = 0; i < tradeDataList.size(); i++) {

// 多个订单
                TradeParas.TradeDataBean dataBean = new TradeParas.TradeDataBean();
                //查找 单个订单
                List<ScaleTrade> scaleTrades = tradeDataList.get(i);
                if (scaleTrades == null || scaleTrades.size() <= 0) {
                    return;
                }
                ArrayList<TradeParas.TradeDataBean.TradeDetailBean> tradeDetailBeans = new ArrayList<>();
                tradeDetailBeans.clear();


                String tradeNo = tradeNos.get(i);
                dataBean.setDate(String.valueOf(TimeUtils.getCurrentSeconds()));
                dataBean.setMarketId(String.valueOf(loginRes.getMarket_id()));
                dataBean.setUserId(String.valueOf(loginRes.getMerchant_id()));
                dataBean.setUserName(merchantInfo.getMerchant_name());
                dataBean.setPayStatus("100");
//                dataBean.setPayType("999");
                String payType = "999";

                double amount = 0;
                for (int j = 0; j < scaleTrades.size(); j++) {
                    // 查找单个订单
                    ScaleTrade trade = scaleTrades.get(j);

                    payType = trade.getPayType();
//                    String tradeNo1 = trade.getTradeNo();
                    //总计
                    amount += trade.getTotPrice();

                    TradeParas.TradeDataBean.TradeDetailBean detailBean
                            = new TradeParas.TradeDataBean.TradeDetailBean();
                    ScaleTrade scaleTrade = scaleTrades.get(j);
                    detailBean.setGoodsId(String.valueOf(scaleTrade.getPlu()));
                    detailBean.setGoodsName(scaleTrade.getGoodsName());
                    detailBean.setId(scaleTrade.getId().toString());
                    detailBean.setSubTotal(String.valueOf(scaleTrade.getTotPrice()));
                    detailBean.setTradeUnit(scaleTrade.getTradeUnit());

                    if (TextUtils.isEmpty(scaleTrade.getTradeUnit())) {
                        detailBean.setUnitId("1053");
                    } else {
                        if (scaleTrade.getTradeUnit().contains("kg")) {
                            detailBean.setUnitId("1053");
                        } else {
                            detailBean.setUnitId("1057");
                        }
                    }

                    /***
                     *  价格处理
                     * */
                    detailBean.setUnitPrice(String.valueOf(scaleTrade.getUnitPrice()));

                    detailBean.setWeightPcs(String.valueOf(scaleTrade.getWeightPcs()));

                    tradeDetailBeans.add(detailBean);
                }
                dataBean.setPayType(payType);

                dataBean.setTrade_detail(tradeDetailBeans);
                dataBean.setTradeNo(tradeNo);
                dataBean.setTotal_amount(String.valueOf(amount));

                tradeDataBeans.add(dataBean);
            }


            // 去除转义字符的GSon
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String trades = gson.toJson(tradeDataBeans);


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("trade_data", trades);
            hashMap.put("sign", ParamsUtils.md5(hashMap));
            if (BuildConfig.DEBUG) {
                LogUtils.e("checkOrder para=> ", trades);
                LogUtils.e("checkOrder Url=> ", url);
            }


            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    if (httpCallBack != null) {
                        httpCallBack.onFail(e.getMessage());
                    }
                }

                @Override
                public void onResponse(String response) {
                    if (httpCallBack != null) {
                        httpCallBack.onSuccess(response);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    ///merchantscreen/index/get_rowcount_trades
//    public void getRowcountTrades(HttpCallBack httpCallBack) {
//
//        try {
//            String url = ConstantApi.ZHUMEI_BASE_URL + ConstantApi.TRADES_COUNT;
//          if (BuildConfig.DEBUG){
//              LogUtils.e("rowscount", url);
//          }
//            OkhttpUtil.okHttpPost(url, null, new CallBackUtil.CallBackString() {
//                @Override
//                public void onFailure(Call call, Exception e) {
//                    if (httpCallBack != null) {
//                        httpCallBack.onFail(e.getMessage());
//                    }
//                }
//
//                @Override
//                public void onResponse(String response) {
//                    if (httpCallBack != null) {
//                        httpCallBack.onSuccess(response);
//                    }
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    public static void getPublicIp() {

        ThreadManager.executeSingleTask(new Runnable() {
            @Override
            public void run() {
                OkhttpUtil.okHttpPost(ConstantApi.PUBLIC_IP_POST, new CallBackUtil.CallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        Log.e("公网IP=2222=》", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        String ipAddress = dealWithPublicIpJson(response);
                        if (BuildConfig.DEBUG) {
                            LogUtils.e("公网IP==》", ipAddress);
                        }
                        CacheUtils.putString(AppConstants.Cache.PUBLIC_IP, ipAddress);


                    }
                });

            }
        });
    }


    public static String dealWithPublicIpJson(String publicIpJson) {
        try {
            if (!TextUtils.isEmpty(publicIpJson) && !AppConstants.CommonStr.NULL_STR.equals(publicIpJson)) {
                int start = publicIpJson.indexOf("{");
                int end = publicIpJson.indexOf("}");
                String json = publicIpJson.substring(start, end + 1);
                if (!TextUtils.isEmpty(json) && !AppConstants.CommonStr.NULL_STR.equals(json)) {
                    JSONObject jsonObject = JSON.parseObject(json);
                    return jsonObject.getString("cip");
                }
                return AppConstants.DefaultSetting.SET_IP;
            } else {
                return AppConstants.DefaultSetting.SET_IP;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.DefaultSetting.SET_IP;
        }
    }
}
