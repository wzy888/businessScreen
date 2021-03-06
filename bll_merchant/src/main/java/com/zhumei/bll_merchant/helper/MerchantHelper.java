package com.zhumei.bll_merchant.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.adapter.PendingOrderAdapter;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.base.Event;
import com.zhumei.baselib.base.MmkvUtils;
import com.zhumei.baselib.bean.ble_setting.BleCmd2Bean;
import com.zhumei.baselib.bean.ble_setting.BleSettingAllClearBean;
import com.zhumei.baselib.bean.ble_setting.BleSettingCheckOutBean;
import com.zhumei.baselib.bean.ble_setting.BleSettingRealTimeWeightingBean;
import com.zhumei.baselib.bean.ble_setting.BleSettingRegisterBean;
import com.zhumei.baselib.bean.ble_setting.BleSettingWeightingBean;
import com.zhumei.baselib.bean.scale.ScaleGoods;
import com.zhumei.baselib.bean.scale.ScaleHotkey;
import com.zhumei.baselib.bean.scale.ScaleTicket;
import com.zhumei.baselib.bean.scale.ScaleTrade;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.dao.ScaleDaoUtil;
import com.zhumei.baselib.glide.GlideApp;
import com.zhumei.baselib.helper.UiHelper;
import com.zhumei.baselib.module.localdata.LoginLocalData;
import com.zhumei.baselib.module.response.ActivePayRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.module.response.MerchantInfo;
import com.zhumei.baselib.module.response.OrderCodeRes;
import com.zhumei.baselib.utils.HttpUtils;
import com.zhumei.baselib.utils.JsonUtils;
import com.zhumei.baselib.utils.ParamsUtils;
import com.zhumei.baselib.utils.ResourceManager;
import com.zhumei.baselib.utils.para.CMD5Bean;
import com.zhumei.baselib.utils.para.OrderPayType;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.event.EventBusUtils;
import com.zhumei.baselib.utils.useing.event.MainThreadExecutor;
import com.zhumei.baselib.utils.useing.hardware.MD5Utils;
import com.zhumei.baselib.utils.useing.hardware.SoundPoolPlayUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.MathUtils;
import com.zhumei.baselib.utils.useing.software.StringUtils;
import com.zhumei.baselib.widget.AutoScrollRecyclerView;
import com.zhumei.baselib.widget.TriangleView;
import com.zhumei.bll_merchant.BuildConfig;
import com.zhumei.bll_merchant.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class MerchantHelper {

    private final ScaleDaoUtil mScaleDaoUtil;
    private final Animation mPriceRotAnim;
    private final UiHelper uiHelper;
    private String mSavedBleMacAddr;
    //    private final BleDevice mBleDevice;
    private static String TAG = "MerchantHelper";
    private Activity mActivity;
    //    private CacheUtils mmkvUtils = new CacheUtils();
    private String mTemporaryData = "";

    private List<BleCmd2Bean> mListV1 = new ArrayList<>();
    private List<BleCmd2Bean> mListV2 = new ArrayList<>();
    private List<BleCmd2Bean> mListV4 = new ArrayList<>();
    private List<BleCmd2Bean> mListV5 = new ArrayList<>();


    private MainThreadExecutor mainThreadExecutor;


    private int mGetHotkey = 1;
    private int mGetGoods = 1;
    private int mGetTicket = 1;
    private String mTempJsonData = "";
    private double mWeightPcs;
    private double mUnitPrice;
    private boolean mIsWeightChange;

    private double mSubTotPriceV1;
    private double mTotPriceV1;
    private double mSubTotPriceV2;
    private double mTotPriceV2;
    private double mSubTotPriceV4;
    private double mTotPriceV4;
    private double mSubTotPriceV5;
    private double mTotPriceV5;

    private View mViewBg1;
    private View mViewBg2;
    private View mIvBg;
    private ImageView mIvUser;
    private TextView mTvUserInfo;
    private ConstraintLayout mClStar;
    private ImageView mIvStar1;
    private ImageView mIvStar2;
    private ImageView mIvStar3;
    private ImageView mIvStar4;
    private ImageView mIvStar5;
    private ImageView mIvSerie;
    private TextView mTvSerie;
    private ImageView mIvTemperature;
    private ImageView mIvHealthCode;
    private ImageView mIvZan;
    private TextView mTvZanCount;
    private View mViewBg3;
    private TextView mTv1;
    private ImageView mIvHot;
    private TextView mTv2;
    private ImageView mIvHot1;
    private ImageView mIvHuiyuan;
    private TextView mTv3;
    private ImageView mIvFensi;
    private TextView mTv4;
    private ImageView mIv4;
    private HorizontalScrollView mHsc;
    private TextView mTvMerchantInfo;
    private View mViewBg4;
    private AutoScrollRecyclerView mRvGoodsPrice;
    private HorizontalScrollView mHsc2;
    private TextView mTvHuodong;
    private View mViewRight;
    private ConstraintLayout mViewBg6;
    private TextView mTvWeightUnit;
    private TextView mTvWeight;
    private View mLine1;
    private TextView mTvPriceUnit;
    private TextView mTvPrice;
    private View mLine2;
    private TextView mTvRmbUnit;
    private TextView mTvSubTotal;
    private View mLine3;
    private TextView mTvTotalUnit;
    private TextView mTvCount;
    private TextView mTvTotalPay;
    private View mViewBannerBg;
    private View mViewBannerWindowBg;
    //    private Banner mStallBanner;
    private View mViewTotalOrder;
    private TriangleView mTrPay;
    private ConstraintLayout mClPay;
    private TextView mTvXp;
    private TextView mTvXpMomey;
    private TextView mTvYh;
    private TextView mTvYhMoney;
    private ImageView mIvPayCode;
    private TextView mTvPayType;
    private TextView mTvActualPay;
    private View mViewPendingOrder;
    private TriangleView mTrOrder;
    private RecyclerView mRvPendingOrder;

    private PendingOrderAdapter orderAdapter1 = new PendingOrderAdapter(new ArrayList<BleCmd2Bean>());
    private PendingOrderAdapter orderAdapter2 = new PendingOrderAdapter(new ArrayList<BleCmd2Bean>());
    private PendingOrderAdapter orderAdapter5 = new PendingOrderAdapter(new ArrayList<BleCmd2Bean>());
    private PendingOrderAdapter orderAdapter4 = new PendingOrderAdapter(new ArrayList<BleCmd2Bean>());

    private ConstraintLayout cl_pay_success;
    private ImageView iv_pay_success;
    private ImageView iv_gzh;


    public interface BlueToothCallBack {


        void onConnectFail(BleDevice bleDevice, BleException exception);

        void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status);

        void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status);
    }


    public interface BlueDataCallBack {

        void dealWithBlueData(byte[] data);
    }

    //   PendingOrderAdapter orderAdapter
    public MerchantHelper(Activity mActivity, ScaleDaoUtil mScaleDaoUtil, UiHelper uiHelper) {
        this.mActivity = mActivity;


        this.mScaleDaoUtil = mScaleDaoUtil;
        mPriceRotAnim = AnimationUtils.loadAnimation(mActivity, R.anim.ble_searching_rotate);
        mPriceRotAnim.setInterpolator(new LinearInterpolator());
//        this.uiHelper = new UiHelper();
        this.uiHelper = uiHelper;
        mainThreadExecutor = new MainThreadExecutor();
        /**
         *  ????????????Activity ??????view
         * */
        initView();
        mListV1.clear();
        mListV2.clear();
        mListV4.clear();
        mListV5.clear();


        mSavedBleMacAddr = CacheUtils.getString(AppConstants.Cache.BLE_MAC_ADDR, AppConstants.DefaultSetting.SET_MAC);
    }


    private void initView() {
        mViewBg1 = (View) mActivity.findViewById(R.id.view_bg1);
        mViewBg2 = (View) mActivity.findViewById(R.id.view_bg2);
        mIvBg = (View) mActivity.findViewById(R.id.iv_bg);
        mIvUser = mActivity.findViewById(R.id.iv_user);
        mTvUserInfo = (TextView) mActivity.findViewById(R.id.tv_user_info);
        mClStar = (ConstraintLayout) mActivity.findViewById(R.id.cl_star);
        mIvStar1 = (ImageView) mActivity.findViewById(R.id.iv_star1);
        mIvStar2 = (ImageView) mActivity.findViewById(R.id.iv_star2);
        mIvStar3 = (ImageView) mActivity.findViewById(R.id.iv_star3);
        mIvStar4 = (ImageView) mActivity.findViewById(R.id.iv_star4);
        mIvStar5 = (ImageView) mActivity.findViewById(R.id.iv_star5);
        mIvSerie = (ImageView) mActivity.findViewById(R.id.iv_serie);
        mTvSerie = (TextView) mActivity.findViewById(R.id.tv_serie);
        mIvTemperature = (ImageView) mActivity.findViewById(R.id.iv_temperature);
        mIvHealthCode = (ImageView) mActivity.findViewById(R.id.iv_health_code);
        mIvZan = (ImageView) mActivity.findViewById(R.id.iv_zan);
        mTvZanCount = (TextView) mActivity.findViewById(R.id.tv_zan_count);
        mViewBg3 = (View) mActivity.findViewById(R.id.view_bg3);
        mTv1 = (TextView) mActivity.findViewById(R.id.tv1);
        mIvHot = (ImageView) mActivity.findViewById(R.id.iv_hot);
        mTv2 = (TextView) mActivity.findViewById(R.id.tv2);
        mIvHot1 = (ImageView) mActivity.findViewById(R.id.iv_hot1);
        mIvHuiyuan = (ImageView) mActivity.findViewById(R.id.iv_huiyuan);
        mTv3 = (TextView) mActivity.findViewById(R.id.tv3);
        mIvFensi = (ImageView) mActivity.findViewById(R.id.iv_fensi);
        mTv4 = (TextView) mActivity.findViewById(R.id.tv4);
        mIv4 = (ImageView) mActivity.findViewById(R.id.iv4);
        mHsc = (HorizontalScrollView) mActivity.findViewById(R.id.hsc);
        mTvMerchantInfo = (TextView) mActivity.findViewById(R.id.tv_merchant_info);
        mViewBg4 = (View) mActivity.findViewById(R.id.view_bg4);
        mRvGoodsPrice = (AutoScrollRecyclerView) mActivity.findViewById(R.id.rv_goods_price);
        mHsc2 = (HorizontalScrollView) mActivity.findViewById(R.id.hsc2);
        mTvHuodong = (TextView) mActivity.findViewById(R.id.tv_huodong);
        mViewRight = (View) mActivity.findViewById(R.id.view_right);
        mViewBg6 = (ConstraintLayout) mActivity.findViewById(R.id.view_bg6);
        mTvWeightUnit = (TextView) mActivity.findViewById(R.id.tv_weight_unit);
        mTvWeight = (TextView) mActivity.findViewById(R.id.tv_weight);
        mLine1 = (View) mActivity.findViewById(R.id.line1);
        mTvPriceUnit = (TextView) mActivity.findViewById(R.id.tv_price_unit);
        mTvPrice = (TextView) mActivity.findViewById(R.id.tv_price);
        mLine2 = (View) mActivity.findViewById(R.id.line2);
        mTvRmbUnit = (TextView) mActivity.findViewById(R.id.tv_rmb_unit);
        mTvSubTotal = (TextView) mActivity.findViewById(R.id.tv_sub_total);
        mLine3 = (View) mActivity.findViewById(R.id.line3);
        mTvTotalUnit = (TextView) mActivity.findViewById(R.id.tv_total_unit);
        mTvCount = (TextView) mActivity.findViewById(R.id.tv_count);
        mTvTotalPay = (TextView) mActivity.findViewById(R.id.tv_total_pay);
        mViewBannerBg = (View) mActivity.findViewById(R.id.view_banner_bg);
        mViewBannerWindowBg = (View) mActivity.findViewById(R.id.view_banner_window_bg);
        mViewTotalOrder = (View) mActivity.findViewById(R.id.view_total_order);
        mTrPay = (TriangleView) mActivity.findViewById(R.id.tr_pay);
        mClPay = (ConstraintLayout) mActivity.findViewById(R.id.cl_pay);
        mTvXp = (TextView) mActivity.findViewById(R.id.tv_xp);
        mTvXpMomey = (TextView) mActivity.findViewById(R.id.tv_xp_momey);
        mTvYh = (TextView) mActivity.findViewById(R.id.tv_yh);
        mTvYhMoney = (TextView) mActivity.findViewById(R.id.tv_yh_money);
        mIvPayCode = (ImageView) mActivity.findViewById(R.id.iv_pay_code);
        mTvPayType = (TextView) mActivity.findViewById(R.id.tv_pay_type);
        mTvActualPay = (TextView) mActivity.findViewById(R.id.tv_actual_pay);
        mViewPendingOrder = (View) mActivity.findViewById(R.id.view_pending_order);
        mTrOrder = (TriangleView) mActivity.findViewById(R.id.tr_order);
        mRvPendingOrder = (RecyclerView) mActivity.findViewById(R.id.rv_pending_order);

        cl_pay_success = mActivity.findViewById(R.id.cl_pay_success);

        iv_pay_success = mActivity.findViewById(R.id.iv_pay_success);

        iv_gzh = mActivity.findViewById(R.id.iv_gzh);


    }


    public void connectBleByMac(final BlueToothCallBack callBack) {
        try {

            mSavedBleMacAddr = CacheUtils.getString(AppConstants.Cache.BLE_MAC_ADDR, AppConstants.DefaultSetting.SET_MAC);
            BleManager.getInstance().connect(mSavedBleMacAddr, new BleGattCallback() {
                @Override
                public void onStartConnect() {
                    LogUtils.e(TAG, "Bluetooth start connection!");
                }

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void onConnectFail(BleDevice bleDevice, BleException exception) {

                    try {

                        LogUtils.e(TAG, "Bluetooth connection failure!" + exception.getDescription());
                        ToastUtils.showShort(ResourceManager.getStringResource(R.string.ble_conn_fail));

                        if (callBack != null) {
                            callBack.onConnectFail(bleDevice, exception);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    LogUtils.e(TAG, "Bluetooth connection success!");
                    ToastUtils.showShort(ResourceManager.getStringResource(R.string.ble_conn_success));
                    if (callBack != null) {
                        callBack.onConnectSuccess(bleDevice, gatt, status);
                    }


                }

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {


                    LogUtils.e(TAG, "Bluetooth connection disconnection!");
                    ToastUtils.showShort(MyBaseApplication.getMyApplication().getString(R.string.ble_disconn));
                    if (callBack != null) {
                        callBack.onDisConnected(isActiveDisConnected, bleDevice, gatt, status);
                    }


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param serviceUUID
     * @param notifyUUID
     * @param gatt
     * @param bleDevice
     */
    public void getDataFromBle(UUID serviceUUID, UUID notifyUUID, final BluetoothGatt gatt, BleDevice bleDevice, final BlueDataCallBack blueDataCallBack) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                LogUtils.e("getDataFromBle", "???????????????18");
                return;
            }

            BluetoothGattService service = gatt.getService(serviceUUID);
            if (service == null) {
                LogUtils.e("getDataFromBle", "????????? BluetoothGattService");
                return;
            }

            BluetoothGattCharacteristic characteristic = service.getCharacteristic(notifyUUID);
            if (characteristic == null) {
                LogUtils.e("getDataFromBle", "????????? characteristic");
                return;
            }

            gatt.setCharacteristicNotification(characteristic, true);
            BleManager.getInstance().notify(bleDevice, service.getUuid().toString(), characteristic.getUuid().toString()
                    , new BleNotifyCallback() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onNotifySuccess() {
                            // ????????????????????????
                            LogUtils.e(TAG, "onNotifySuccess: ");
                            // ??????Ble?????????Mtu???  ??????????????????
                            // ??????4.2 ???????????????????????????244?????????
                            gatt.requestMtu(AppConstants.DefaultSetting.SET_BLE_MTU);
                        }

                        @Override
                        public void onNotifyFailure(BleException exception) {
                            // ????????????????????????
                            LogUtils.e(TAG, "onNotifyFailure: ");
                        }

                        @Override
                        public void onCharacteristicChanged(byte[] data) {

                            if (blueDataCallBack != null) {

                                blueDataCallBack.dealWithBlueData(data);
                            }

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("SimpleDateFormat")
    public void refreshScaleTimeDataByBle(BleDevice bleDevice, BluetoothGatt gatt) {
        String scaleTime = com.blankj.utilcode.util.TimeUtils.getNowString(new SimpleDateFormat("yyyyMMddHHmmss"));
        StringBuilder scaleTimeSb = new StringBuilder();
        scaleTimeSb.append("{\"").append(scaleTime).append("\"}");
        String scaleTimeContent = scaleTimeSb.toString();
        String scaleTimePrefix = dealWithScaleBlePrefix8(scaleTimeContent, AppConstants.ScaleBLEEventType.SCALE_TIME_CODE);
        writeToBle(scaleTimePrefix + scaleTimeContent + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_TIME_CODE);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param bleDevice
     * @param gatt
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void refreshScaleHotkeyDataByBle(BleDevice bleDevice, BluetoothGatt gatt) {
        try {


            if (bleDevice == null) {
                LogUtils.e("bleDevice IS NULL!");

                return;
            }

            if (gatt == null) {
                LogUtils.e("gatt IS NULL!");
                return;
            }


            // ??????????????????????????????????????? ?????? ??????????????????
            List<ScaleHotkey> scaleHotkeys = mScaleDaoUtil.queryAllScaleHotkeyData(ScaleHotkey.class);
            LogUtils.e(TAG, "The scale key be modified!");
            if (scaleHotkeys != null && scaleHotkeys.size() > 0) {
                LogUtils.e(TAG, "The scale key " + scaleHotkeys.toString());

                StringBuilder scaleHotkeysSb = new StringBuilder();
                if (scaleHotkeys.size() > 40) {
                    List<ScaleHotkey> scaleHotkeys1 = scaleHotkeys.subList(0, 40);
                    for (ScaleHotkey scaleHotkey : scaleHotkeys1) {
                        scaleHotkeysSb.append("{\"").append(scaleHotkey.getIndex()).append("\",\"").append(scaleHotkey.getPLU1()).append("\",\"").append(scaleHotkey.getPLU2()).append("\"}");
                    }
                    String scaleHotkeysContent = scaleHotkeysSb.toString();
                    String scaleHotkeysPrefix = dealWithScaleBlePrefix9(scaleHotkeysContent, AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE);
                    LogUtils.e(TAG, "scaleHotkeysContent:" + scaleHotkeysContent);
                    // ????????????????????????????????????????????? ???????????????
//                    if (scaleHotkeysContent.contains(AppConstants.ZSLF.DEFAULT_SCALE_HOTKEYS_CONTENT)) {
                    String defaultScaleHotkeysContent = AppConstants.ZSLF.DEFAULT_SCALE_HOTKEYS_CONTENT;


                    if (0 == AppConstants.UsefulSetting.SET_DS_DIFF) {

                        writeToBle(scaleHotkeysPrefix + "1" + scaleHotkeysContent + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE);
                    } else {
                        writeToBle(scaleHotkeysPrefix + defaultScaleHotkeysContent + scaleHotkeysContent + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE);
                    }

                } else {
                    for (ScaleHotkey scaleHotkey : scaleHotkeys) {
                        scaleHotkeysSb.append("{\"").append(scaleHotkey.getIndex()).append("\",\"").append(scaleHotkey.getPLU1()).append("\",\"").append(scaleHotkey.getPLU2()).append("\"}");
                    }
                    String scaleHotkeysContent = scaleHotkeysSb.toString();
                    String scaleHotkeysPrefix;
                    LogUtils.e("hotKeyContent==>", scaleHotkeysContent);

                    if (0 == AppConstants.UsefulSetting.SET_DS_DIFF) {
                        scaleHotkeysPrefix = dealWithScaleBlePrefix9(scaleHotkeysContent, AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE);
                    } else {
                        scaleHotkeysPrefix = dealWithScaleBlePrefix8(scaleHotkeysContent, AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE);
                    }
                    LogUtils.e("prefix", scaleHotkeysPrefix);
                    // ????????????????????????????????????????????? ???????????????
//                    if (scaleHotkeysContent.contains(AppConstants.ZSLF.DEFAULT_SCALE_HOTKEYS_CONTENT)) {
                    //?????? ??????
//                    String defaultScaleHotkeys1 = AppConstants.ZSLF.DEFAULT_SCALE_HOTKEYS1;
                    if (0 == AppConstants.UsefulSetting.SET_DS_DIFF) {
                        writeToBle(scaleHotkeysPrefix + "1" + scaleHotkeysContent + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE);
                    } else {
                        String defaultScaleHotkeysContent = AppConstants.ZSLF.DEFAULT_SCALE_HOTKEYS_CONTENT;
                        writeToBle(scaleHotkeysPrefix + defaultScaleHotkeysContent + scaleHotkeysContent + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE);
                    }

                }
            } else {
                writeToBle(AppConstants.ZSLF.DEFAULT_SCALE_HOTKEYS + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE);
            }
            CacheUtils.putBoolean(AppConstants.Cache.SCALE_KEY_INFAC_MODIFIED, false);
            CacheUtils.putBoolean(AppConstants.Cache.SCALE_KEY_PUSH_MODIFIED, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param bleDevice
     * @param gatt
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void refreshScaleGoodsDataByBle(BleDevice bleDevice, BluetoothGatt gatt) {
        try {

            if (bleDevice == null) {
                LogUtils.e("BleDevice  IS NULL!");
                return;
            }

            if (gatt == null) {
                LogUtils.e("gatt  IS NULL!");
                return;
            }
            // ??????????????????????????????????????? ?????? ????????????
            List<ScaleGoods> scaleGoods = mScaleDaoUtil.queryAllScaleGoodsData(ScaleGoods.class);
            LogUtils.e(TAG, "The scale goods be modified!");
            if (scaleGoods != null && !scaleGoods.isEmpty()) {
                StringBuilder scaleGoodsSb = new StringBuilder();
                for (ScaleGoods scaleGood : scaleGoods) {
                    scaleGoodsSb.append("{\"").append(scaleGood.getName()).append("\",\"").append(scaleGood.getUnit()).append("\",")
                            .append(scaleGood.getPrice()).append(",").append(scaleGood.getMemPrice()).append(",")
                            .append(scaleGood.getMinPrice()).append(",\"").append(scaleGood.getPluType()).append("\",\"")
                            .append(scaleGood.getDiscount()).append("\",\"").append(scaleGood.getChangePrice()).append("\",\"")
                            .append(scaleGood.getPLU()).append("\",\"").append(scaleGood.getSelfCode()).append("\"}");
                }
                // ?????????5????????? ???????????????2????????? ?????????1???cmd?????????
                String scaleGoodsContent = scaleGoodsSb.toString();
                String scaleGoodsPrefix;
                if (0 == AppConstants.UsefulSetting.SET_DS_DIFF) {
                    scaleGoodsPrefix = dealWithScaleBlePrefix9(scaleGoodsContent, AppConstants.ScaleBLEEventType.SCALE_GOODS_CODE);
                    writeToBle(scaleGoodsPrefix + "1" + scaleGoodsContent + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_GOODS_CODE);
                } else {
                    scaleGoodsPrefix = dealWithScaleBlePrefix8(scaleGoodsContent, AppConstants.ScaleBLEEventType.SCALE_GOODS_CODE);
                    writeToBle(scaleGoodsPrefix + scaleGoodsContent + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_GOODS_CODE);
                }
            }
            CacheUtils.putBoolean(AppConstants.Cache.SCALE_GOODS_INFAC_MODIFIED, false);
            CacheUtils.putBoolean(AppConstants.Cache.SCALE_GOODS_PUSH_MODIFIED, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param bleDevice
     * @param gatt
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void refreshScaleTicketDataByBle(BleDevice bleDevice, BluetoothGatt gatt) {
        try {

            if (bleDevice == null) {
                LogUtils.e("gatt IS NULL!");

                return;
            }

            if (gatt == null) {
                LogUtils.e("gatt IS NULL!");
                return;
            }
            // ??????????????????????????????????????? ?????? ????????????
            List<ScaleTicket> scaleTickets = mScaleDaoUtil.queryAllScaleTicketData(ScaleTicket.class);
            LogUtils.e(TAG, "The scale ticket be modified!");
            if (scaleTickets != null && !scaleTickets.isEmpty()) {
                StringBuilder scaleTicketSb = new StringBuilder();
                if (scaleTickets.size() > 10) {
                    for (int i = 0; i < 10; i++) {
                        scaleTicketSb.append("{\"").append(scaleTickets.get(i).getTicFlg()).append("\",\"").append(scaleTickets.get(i).getAliagnFlg())
                                .append("\",\"").append(scaleTickets.get(i).getPrtFlg()).append("\",\"").append(scaleTickets.get(i).getPrtData()).append("\"}");
                    }
                } else {
                    for (ScaleTicket scaleTicket : scaleTickets) {
                        String prtData = scaleTicket.getPrtData();
                        int prtDataLength = prtData.length();
                        if (prtDataLength > 16) {
                            scaleTicketSb.append("{\"").append(scaleTicket.getTicFlg()).append("\",\"").append(scaleTicket.getAliagnFlg())
                                    .append("\",\"").append(scaleTicket.getPrtFlg()).append("\",\"").append(scaleTicket.getPrtData().substring(0, 17)).append("\"}");
                        } else {
                            scaleTicketSb.append("{\"").append(scaleTicket.getTicFlg()).append("\",\"").append(scaleTicket.getAliagnFlg())
                                    .append("\",\"").append(scaleTicket.getPrtFlg()).append("\",\"").append(scaleTicket.getPrtData()).append("\"}");
                        }
                    }
                }
                String scaleTicketContent = scaleTicketSb.toString();
                String scaleTicketPrefix = dealWithScaleBlePrefix8(scaleTicketContent, AppConstants.ScaleBLEEventType.SCALE_TICKET_CODE);
                writeToBle(scaleTicketPrefix + scaleTicketContent + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_TICKET_CODE);
            } else {
                writeToBle(AppConstants.ZSLF.DEFAULT_SCALE_TICKET + AppConstants.ZSLF.LINE_BREAK, bleDevice, gatt, AppConstants.ScaleBLEEventType.SCALE_TICKET_CODE);
            }
            CacheUtils.putBoolean(AppConstants.Cache.SCALE_TICKET_INFAC_MODIFIED, false);
            CacheUtils.putBoolean(AppConstants.Cache.SCALE_TICKET_PUSH_MODIFIED, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ????????????????????????????????????
     *
     * @param scaleContent
     * @param scaleCodeFlag ???????????????????????????????????????????????????
     * @return
     */
    public String dealWithScaleBlePrefix8(String scaleContent, String scaleCodeFlag) {

        try {
            // ?????????5????????? ???????????????2????????? ?????????1???cmd?????????
            int scaleContentLength = scaleContent.length() + StringUtils.countChinese(scaleContent) + 8;
            int scaleLength = String.valueOf(scaleContentLength).length();
            String scalePrefix;
            switch (scaleLength) {

                case AppConstants.ScaleBLEEventType.SCALE_STR_LENGTH_1:
                    if (scaleContentLength <= 8) {
                        scalePrefix = AppConstants.CommonStr.EMPTY_STR;
                    } else {
                        scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_4 + scaleContentLength + scaleCodeFlag;
                    }
                    break;

                case AppConstants.ScaleBLEEventType.SCALE_STR_LENGTH_2:
                    scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_3 + scaleContentLength + scaleCodeFlag;
                    break;

                case AppConstants.ScaleBLEEventType.SCALE_STR_LENGTH_3:
                    scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_2 + scaleContentLength + scaleCodeFlag;
                    break;

                case AppConstants.ScaleBLEEventType.SCALE_STR_LENGTH_4:
                    scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_1 + scaleContentLength + scaleCodeFlag;
                    break;

                default:
                    scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_5 + scaleCodeFlag;
                    break;
            }
            return scalePrefix;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.CommonStr.NULL_STR;
        }
    }

    /**
     * ????????????????????????????????????(??????????????????????????????????????????????????????)
     */
    public String dealWithScaleBlePrefix9(String scaleContent, String scaleCodeFlag) {

        try {
            // ?????????5????????? ???????????????2????????? ?????????1???cmd????????? ?????????????????????????????????????????????
            int scaleContentLength = scaleContent.length() + StringUtils.countChinese(scaleContent) + 9;
            int scaleLength = String.valueOf(scaleContentLength).length();
            String scalePrefix;
            switch (scaleLength) {

                case AppConstants.ScaleBLEEventType.SCALE_STR_LENGTH_1:
                    if (scaleContentLength <= 8) {
                        scalePrefix = AppConstants.CommonStr.EMPTY_STR;
                    } else {
                        scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_4 + scaleContentLength + scaleCodeFlag;
                    }
                    break;

                case AppConstants.ScaleBLEEventType.SCALE_STR_LENGTH_2:
                    scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_3 + scaleContentLength + scaleCodeFlag;
                    break;

                case AppConstants.ScaleBLEEventType.SCALE_STR_LENGTH_3:
                    scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_2 + scaleContentLength + scaleCodeFlag;
                    break;

                case AppConstants.ScaleBLEEventType.SCALE_STR_LENGTH_4:
                    scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_1 + scaleContentLength + scaleCodeFlag;
                    break;

                default:
                    scalePrefix = AppConstants.ScaleBLEEventType.SCALE_PREFIX_5 + scaleCodeFlag;
                    break;
            }
            return scalePrefix;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.CommonStr.NULL_STR;
        }
    }

    /**
     * ????????????????????????
     *
     * @param screenBleData
     * @param bleDevice
     * @param gatt
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void writeToBle(String screenBleData, BleDevice bleDevice, BluetoothGatt gatt, String scaleCodeFlag) {
        try {
            LogUtils.e("screenBleData:" + screenBleData);

            if (!BleManager.getInstance().isSupportBle()) {
                LogUtils.e("ble no Support!");

                return;
            }
            BluetoothGattService service = null;

            service = gatt.getService(AppConstants.ZSLF.SERVICE_UUID);

            if (service == null) {
                LogUtils.e("???????????? BluetoothGattService !");
                return;
            }

            BluetoothGattCharacteristic characteristic = null;

            characteristic = service.getCharacteristic(AppConstants.ZSLF.WRITE_UUID);

            if (null == characteristic) {
                LogUtils.e("???????????? characteristic !");
                return;
            }

            gatt.setCharacteristicNotification(characteristic, true);
            com.blankj.utilcode.util.LogUtils.e("screenBleData::" + screenBleData);
            int index;
            int count = 0;
            // ???????????????12???????????????
            String substring;
            int length = screenBleData.length();
            for (index = 0; index < length; index += 12) {
                count++;
                if (count < Math.ceil(length / 12.0)) {
                    substring = screenBleData.substring(index, index + 12);
                } else {
                    substring = screenBleData.substring(index);
                }

                if (AppConstants.ScaleBLEEventType.SCALE_HOTKEYS_CODE.equals(scaleCodeFlag)) {
                    if (count < 8) {
                        SystemClock.sleep(AppConstants.DefaultSetting.SET_BLE_SLEEP_LOW_CYCLE);
                    } else {
                        SystemClock.sleep(AppConstants.DefaultSetting.SET_BLE_SLEEP_HIGH_CYCLE);
                    }
                } else if (AppConstants.ScaleBLEEventType.SCALE_GOODS_CODE.equals(scaleCodeFlag)
                        || AppConstants.ScaleBLEEventType.SCALE_TICKET_CODE.equals(scaleCodeFlag)) {
                    SystemClock.sleep(AppConstants.DefaultSetting.SET_BLE_SLEEP_LOW_CYCLE);
                } else if (AppConstants.ScaleBLEEventType.SCALE_TRADE_NO_CODE.equals(scaleCodeFlag)) {
                    SystemClock.sleep(AppConstants.DefaultSetting.SET_BLE_SLEEP_HIGH_CYCLE);
                } else {
                    SystemClock.sleep(AppConstants.DefaultSetting.SET_BLE_SLEEP_LOW_CYCLE);
                }
                LogUtils.e(TAG, "substring:" + substring);
                // ???????????? ???????????????????????? true???????????? false???????????????
                try {
                    BleManager.getInstance().write(bleDevice, service.getUuid().toString(),
                            characteristic.getUuid().toString(),
                            substring.getBytes(AppConstants.DefaultSetting.CHARSET_NAME)
                            , true, new BleWriteCallback() {
                                @Override
                                public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                    // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                    LogUtils.e(TAG, "onWriteSuccess: current:" + current + ",total:" + total + ",justWrite:" + MD5Utils.byteArrayToHexString(justWrite));
                                }

                                @Override
                                public void onWriteFailure(BleException exception) {
                                    // ???????????????????????????
                                    LogUtils.e(TAG, "onWriteFailure: " + exception.toString());
                                    // ?????????????????????????????? ???????????????????????????
                                    CacheUtils.putBoolean(AppConstants.Cache.NOT_FIRST_CONN_BLE, false);
                                }
                            });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dealWithJson(String jsonData) {

        // ????????????????????????  "!0053{"cmd":1,"unit_price":0,"unit":"kg","weight_pcs":0}CA\n"
        // ??????????????????????????????  "!0174{"cmd":2,"user":"V1","is_refund":0,"is_delete":0,"list_index":1,"plu":1,"unit_price":2,"unit":"kg","weight_pcs":0.065000,"tot_price":0.130000,"name":"??????","item_num":"-1"}86\n"
        // ??????????????????  "!0057{"cmd":3,"user":"V1","pay_money":3.160000,"pay_type":0}DB\n"
//        {"cmd":3,"user":"V2","pay_money":4.65,"pay_type":0,"unit":"kg",
//        "trade_no":"wzywzy20122254924","trade_time":"20201222154733","scale_num":"wzy","stall_num":"wzy"}
        // ??????????????????  "!0023{"cmd":4,"user":"V1"}29\n"
        // ??????????????????????????????????????????  "!0096{"cmd":5,"qr_code":"wxp://f2f0Sh4F4bW_DXbFBpdy7WUTM3cuaDd0YLhc","pay_type":1,"pay_money":5.34}8F\n"
        // ????????????????????? ?????????  "!0278{"cmd":5,"qr_code":"https://b2c.icbc.com.cn/servlet/WeChatQRCodeServlet?f=ICBCqr&X=1&T=3&P=6&I=e03d925776684b4d&N=b4cbb142eeafe2bddce7a7878c57f5ca&L=3f7aafc9dbc03de742513fbb73a3380064479ae2c072513270ba3a9d1073f59390c83eea52767761b10076601b8c114e5862dfec92c65ff7","pay_type":1}48\n"
        // ????????????????????? ?????????  "!0295{"cmd":5,"qr_code":"https://b2c.icbc.com.cn/servlet/WeChatQRCodeServlet?f=ICBCqr&X=1&T=3&P=6&I=e03d925776684b4d&N=b4cbb142eeafe2bddce7a7878c57f5ca&L=3f7aafc9dbc03de742513fbb73a3380064479ae2c072513270ba3a9d1073f59390c83eea52767761b10076601b8c114e5862dfec92c65ff7","pay_type":2,"pay_money":5.34}48\n"
        // ??????V1 V2????????????  "!0023{"cmd":6,"user":"V1"}29\n"

        try {
            if (TextUtils.isEmpty(jsonData)) {
                Log.e("jsonData", "????????????Data");
                return;
            }
            // ?????????  ??????
//                data = "!0060{\"cmd\":1,\"unit_price\":0,\"unit\":\"kg\",\"weight_pcs\":0.230000}79\n";
            // ????????????  ??????
//                data = "!0060{\"cmd\":1,\"unit_price\":0,\"unit\":\"kg\",\"weight_pcs\":0.230000}79\r\n";
//                data = "!0172{\"cmd\":2,\"user\":\"V1\",\"is_refund\":0,\"is_delete\":0,\"list_index\":1,\"plu\":1,\"unit_price\":2,\"unit\":\"kg\",\"weight_pcs\":0.065000,\"tot_price\":0.130000,\"name\":\"??????\",\"item_num\":\"-1\"}86\r\n";
//                data = "!0172{\"cmd\":2,\"user\":\"V1\",\"is_refund\":0,\"is_delete\":0,\"list_index\":1,\"plu\":1,\"unit_price\":2,\"unit\":\"kg\",\"weight_pcs\":0.065000,\"tot_price\":0.130000,\"name\":\"?????????\",\"item_num\":\"-1\"}86\r\n";
//                data = "!0057{\"cmd\":3,\"user\":\"V1\",\"pay_money\":3.160000,\"pay_type\":0}DB\r\n";
//                data = "!0096{\"cmd\":5,\"qr_code\":\"wxp://f2f0Sh4F4bW_DXbFBpdy7WUTM3cuaDd0YLhc\",\"pay_type\":1,\"pay_money\":5.34}8F\r\n";
//                data = "!0295{\"cmd\":5,\"qr_code\":\"https://b2c.icbc.com.cn/servlet/WeChatQRCodeServlet?f=ICBCqr&X=1&T=3&P=6&I=e03d925776684b4d&N=b4cbb142eeafe2bddce7a7878c57f5ca&L=3f7aafc9dbc03de742513fbb73a3380064479ae2c072513270ba3a9d1073f59390c83eea52767761b10076601b8c114e5862dfec92c65ff7\",\"pay_type\":2,\"pay_money\":5.34}48\r\n";

            // ????????? ?????????data ????????? ?????????????????????????????? ?????????.
//            CA!0079{"cmd":1,"unit_price":5.50,"tot_price":3.76,"unit":"kg","weight_pcs":0.685}


            // data????????????json??????
            String subStringJson = null;
            // data????????????????????????(json + 2)??????????????????
            int scaleCountLength = 0;
//            Log.e("jsonData==>", jsonData);

            try {
//                Log.e("jsonData", jsonData);


                // ??????"}00??????
                if (jsonData.length() > 5) {
                    // ???????????????????????????????????????Log 244 add???????????????

                    scaleCountLength = Integer.parseInt(jsonData.substring(1, 5));

                } else {
                    scaleCountLength = jsonData.length();
                }


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            // ???????????????gbk???1???????????????utf-8???2?????????
            int countChinese = StringUtils.countChinese(jsonData);
            // data????????????????????????(data???????????? + ????????????????????????)
            int actualCountLength = jsonData.length() + countChinese;
            // ?????? actualCountLength - scaleCountLength ?????????6???
//                com.blankj.utilcode.util.LogUtils.e("scaleCountLength:" + scaleCountLength);
//                com.blankj.utilcode.util.LogUtils.e("countChinese:" + countChinese);
//                // ???244??????????????????
//                com.blankj.utilcode.util.LogUtils.e("actualCountLength:" + actualCountLength);
            // ????????????244
            int length = actualCountLength - scaleCountLength;
            // ?????????
//            boolean isBls = AppConstants.UsefulSetting.SET_SCALE_VENDER == AppConstants.CommonInt.NO_INT;
            //?????? | ??????
            boolean isLF_DF = AppConstants.UsefulSetting.SET_SCALE_VENDER == AppConstants.CommonInt.YES_INT;
            boolean isLineBreak = jsonData.endsWith(AppConstants.BLS.LINE_BREAK);

            if (isLineBreak) {
                // ????????????244???????????????
                // TODO: 2020-07-25

                switch (length) {
                    // ?????????  6???????????????5?????????????????????1???\n  ????????????  6???????????????5?????????????????????1???\r\n
                    case 6:

                        if (isLF_DF) {
                            subStringJson = jsonData.substring(5, jsonData.length() - 4);
                        }
//                        LogUtils.e(TAG, "normal less than 244:" + subStringJson);
//                        com.blankj.utilcode.util.LogUtils.e("normal less than 244:" + subStringJson);
                        // ?????????????????????????????????JSON??????
                        containData(subStringJson);

                        break;

                    case 5:
                        // ????????????
                        subStringJson = jsonData.substring(5, jsonData.length() - 4);
//                        com.blankj.utilcode.util.LogUtils.e("normal less than 244:" + subStringJson);
                        // ?????????????????????????????????JSON??????
                        containData(subStringJson);

                        break;

                    default:

                        // ???????????? ???244???????????????
                        String addSubstringJson = null;

                        if (isLF_DF) {
                            addSubstringJson = jsonData.substring(0, jsonData.indexOf(AppConstants.ZSLF.LINE_BREAK) + 1);
                        }
//                        com.blankj.utilcode.util.LogUtils.e("addSubstringJson??????" + addSubstringJson);
                        // 244??????????????????????????????????????????
                        mTemporaryData += addSubstringJson;
//                        com.blankj.utilcode.util.LogUtils.e("mTemporaryData??????" + mTemporaryData);
                        // ?????????????????????????????????
                        // ?????????
                        // mData = "!0278{\"cmd\":5,\"qr_code\":\"https://b2c.icbc.com.cn/servlet/WeChatQRCodeServlet?f=ICBCqr&X=1&T=3&P=6&I=e03d925776684b4d&N=b4cbb142eeafe2bddce7a7878c57f5ca&L=3f7aafc9dbc03de742513fbb73a3380064479ae2c072513270ba3a9d1073f59390c83eea52767761b10076601b8c114e5862dfec92c65ff7\",\"pay_type\":2,\"pay_money\":5.34}48\n";
                        // ?????????
                        // mData = "!0278{\"cmd\":5,\"qr_code\":\"https://b2c.icbc.com.cn/servlet/WeChatQRCodeServlet?f=ICBCqr&X=1&T=3&P=6&I=e03d925776684b4d&N=b4cbb142eeafe2bddce7a7878c57f5ca&L=3f7aafc9dbc03de742513fbb73a3380064479ae2c072513270ba3a9d1073f59390c83eea52767761b10076601b8c114e5862dfec92c65ff7\",\"pay_type\":1}48\n";
                        // data????????????json??????
//                        String jsonAdd  = mTemporaryData.substring(AppConstants.ScalePay.HEAD_LEFT_CHAR, mTemporaryData.length() - AppConstants.BLS.TAIL_REDUNDANT_CHARACTERS);
//                        String jsonAdd  = mTemporaryData.substring(AppConstants.ScalePay.HEAD_LEFT_CHAR, mTemporaryData.length() - 2);
                        ;
//                        if (isBls) {
//                            jsonAdd = mTemporaryData.substring(AppConstants.ScalePay.HEAD_LEFT_CHAR, mTemporaryData.length() - AppConstants.BLS.TAIL_REDUNDANT_CHARACTERS);
//                        }
//                        if (isLF_DF) {
                        String jsonAdd = mTemporaryData.substring(AppConstants.ScalePay.HEAD_LEFT_CHAR, mTemporaryData.length() - AppConstants.BLS.TAIL_REDUNDANT_CHARACTERS);
////                            // ?????????????????????????????????  ??????????????????
////                        }
//                        com.blankj.utilcode.util.LogUtils.e("244 add:" + jsonAdd);
                        // ??????????????????????????????JSON??????
                        containData(jsonAdd);
//                        containData(jsonAdd, tvWeight, tvUnitPrice, tvSubtotal, tvBill, tvTotal, rv, ll, ivPayIcon, tvPayType, tvPay, tvVolume, ivQrcode);

                        mTemporaryData = AppConstants.CommonStr.EMPTY_STR;

                        break;
                }

            } else {
                // ????????????244???????????????
                // ?????????  6???????????????5?????????????????????1???\n  ????????????  6???????????????5?????????????????????1???\r\n
                // TODO: 2020-07-25
                // ????????????
                switch (length) {

                    case 5:
                    case 6:
                        // ????????????


                        if (isLF_DF) {
                            if (mTemporaryData.length() > AppConstants.BLS.TAIL_REDUNDANT_CHARACTERS) {
                                subStringJson = mTemporaryData.substring(AppConstants.ScalePay.HEAD_LEFT_CHAR, mTemporaryData.length() - AppConstants.BLS.TAIL_REDUNDANT_CHARACTERS);
                            } else {
                                String substring = null;


                                if (isLF_DF) {
                                    substring = jsonData.substring(0, jsonData.indexOf(AppConstants.ZSLF.LINE_BREAK) + 1);
                                }
                                subStringJson = substring.substring(AppConstants.ScalePay.HEAD_LEFT_CHAR, substring.length() - AppConstants.BLS.TAIL_REDUNDANT_CHARACTERS);
                            }
                        }
                        // ?????????????????????????????????JSON??????
                        containData(subStringJson);

                        break;

                    default:


                        if (isLF_DF) {
                            // cmd = 2  ???????????? ??????}00
                            if (!jsonData.contains(AppConstants.CommonStr.BACK_QUOTE)) {
                                mTemporaryData = jsonData;
                            } else {
                                mTemporaryData = AppConstants.CommonStr.EMPTY_STR;
                            }
                        }

                        break;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????????????????????????????JSON??????
     *
     * @param json
     */
    private void containData(String json) {
        try {
            LogUtils.E("data==>", json);
            if (json.contains(AppConstants.ScaleBLEEventType.CMD_1)) {
                // ????????????????????????
//                if (AppConstants.UsefulSetting.SET_SCALE_VENDER == AppConstants.CommonInt.NO_INT) {
//                    dealwithRealTimeWeighingDataBLS(json);
//                } else if (AppConstants.UsefulSetting.SET_SCALE_VENDER == AppConstants.CommonInt.YES_INT) {
                dealwithRealTimeWeighingDataZSLF(json);
//                }
            } else if (json.contains(AppConstants.ScaleBLEEventType.CMD_2)) {
                // ??????????????????????????????
                // ??????????????????????????????1
                // getScaleWeightAccDelData ??? getScaleWeightDelData ?????????????????????????????? 20191121

                dealWithPendingOrderData(json);
            } else if (json.contains(AppConstants.ScaleBLEEventType.CHECK_OUT)) {
                // ??????????????????  ????????????????????????
                // ??????????????????????????????2
                dealWithCheckOutDataZSLF(json);

            } else if (json.contains(AppConstants.ScaleBLEEventType.ALL_CLEAR)) {
                // ??????????????????  ?????????????????? + ????????? + 62 ?????????????????????
                // ??????????????????????????????3
                // getScaleWeightAccDelData ??? getScaleWeightDelData ?????????????????????????????? 20191121
                dealWithAllClearData(json);
            } else if (json.contains(AppConstants.ScaleBLEEventType.SHOW_QRCODE)) {

                // ?????????????????????  ???????????????????????????

                dealWithQrcodeZSLF(json);
            } else if (json.contains(AppConstants.ScaleBLEEventType.SHOW_REGISTER)) {
                // ??????????????????
                dealWithRegister(json);
            } else if (json.contains(AppConstants.ScaleBLEEventType.SHOW_WEIGHT)) {
                // ???????????????????????????
                dealWithWeight(json);
                //????????????
            } else if (json.contains(AppConstants.ScaleBLEEventType.GET_HOTKEY)) {
                // ??????????????????
                mGetHotkey = 0;
                // ????????????
            } else if (json.contains(AppConstants.ScaleBLEEventType.GET_GOODS)) {
                // ??????????????????
                mGetGoods = 0;
                // ????????????
            } else if (json.contains(AppConstants.ScaleBLEEventType.GET_TICKET)) {
                // ??????????????????
                mGetTicket = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????.
     */
    /**
     * ??????????????????  ????????????+?????????  ?????????????????? + ????????? + 62 ?????????????????????  cmd = 4
     *
     * @param json TextView tvBill, TextView tvTotal, TextView tvWeight,
     *             TextView tvUnitPrice, TextView tvSubtotal, PendingOrderAdapter orderAdapter
     */
    private void dealWithAllClearData(String json) {
        try {
            BleSettingAllClearBean bleSettingAllClearBean = JsonUtils.json2Object(json, BleSettingAllClearBean.class);
            String user = bleSettingAllClearBean.getUser();

            // V1??????
            if (AppConstants.ScalePay.USER_V1.equals(user)) {
                mTotPriceV1 = AppConstants.CommonInt.KEEP_TWO_DECIMAL_DIGITS_ZERO;

                // ???????????????????????????????????????
                getScaleWeightAccDelData(mListV1);
                mListV1.clear();
                orderAdapter1.getData().clear();
//                orderAdapter1.setNewInstance(new ArrayList<>());
                orderAdapter1.setNewData(new ArrayList<BleCmd2Bean>());

                // V2??????
                hideOrder();
            } else if (AppConstants.ScalePay.USER_V2.equals(user)) {
                mTotPriceV2 = AppConstants.CommonInt.KEEP_TWO_DECIMAL_DIGITS_ZERO;

                // ???????????????????????????????????????
                getScaleWeightAccDelData(mListV2);
                mListV2.clear();
                orderAdapter2.getData().clear();
                orderAdapter2.setNewData(new ArrayList<BleCmd2Bean>());
                hideOrder();
            } else if (AppConstants.ScalePay.USER_V4.equals(user)) {
                mTotPriceV4 = AppConstants.CommonInt.KEEP_TWO_DECIMAL_DIGITS_ZERO;

                // ???????????????????????????????????????
                getScaleWeightAccDelData(mListV4);
                mListV4.clear();
                orderAdapter4.getData().clear();
                orderAdapter4.setNewData(new ArrayList<BleCmd2Bean>());
                hideOrder();
            } else if (AppConstants.ScalePay.USER_V5.equals(user)) {
                mTotPriceV5 = AppConstants.CommonInt.KEEP_TWO_DECIMAL_DIGITS_ZERO;

                // ???????????????????????????????????????
                getScaleWeightAccDelData(mListV5);
                mListV5.clear();
                orderAdapter5.getData().clear();
                orderAdapter5.setNewData(new ArrayList<BleCmd2Bean>());
                hideOrder();
            }
            // ?????????????????????

            mTvCount.setText("0???:");
            mTvTotalPay.setText("0.00");
            mTvWeight.setText("0.000");
            mTvPrice.setText("0.00");
            mTvSubTotal.setText("0.00");


//            uploadTradeData();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getScaleWeightAccDelData(List<BleCmd2Bean> list) {

        try {
            List<ScaleTrade> scaleTrades;
            for (int i = 0; i < list.size(); i++) {
                BleCmd2Bean bleCmd2Bean = list.get(i);
                // ???????????????0.1???????????????????????????
                if (bleCmd2Bean.getTotPrice() > 0.1 && bleCmd2Bean.getWeightPcs() > AppConstants.ScalePay.MIN_SCALE_WEIGHT) {
                    scaleTrades = listAddScaleTrade(new ArrayList<ScaleTrade>(), bleCmd2Bean,
                            String.valueOf(AppConstants.ZSLF.UNPAID),
                            AppConstants.CommonInt.YES_INT);
                    // ???????????????????????????
                    mScaleDaoUtil.insertScaleTradeDataList(scaleTrades);
                    EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                } else {
                    LogUtils.e(TAG, "The ScaleWeightAccDel total price is less than 0.1!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * List<ScaleTrade>??????ScaleTrade
     *
     * @param scaleTrades
     * @param beanData
     * @param payType
     * @param iDataType
     * @return
     */
    private List<ScaleTrade> listAddScaleTrade(List<ScaleTrade> scaleTrades, BleCmd2Bean beanData
            , String payType, int iDataType) {

        try {
            LoginLocalData loginLocalData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
            LoginRes loginRes = loginLocalData.getLoginRes();
            String stall_id = loginRes.getStall_id();
            String stall_num = String.valueOf(stall_id);
            String market_id = loginRes.getMarket_id();

            MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);
            String merchant_name = merchantInfo.getMerchant_name();

            ScaleTrade scaleTrade = new ScaleTrade();
            // ???????????????
            String scaleStallNum = beanData.getStallNum();
            // ?????????????????????????????????????????????????????????????????????

            if (TextUtils.isEmpty(merchant_name)) {
                scaleTrade.setMerchantName(AppConstants.CommonStr.DEFAULT_MERCHANT_NAME);
            } else {
                scaleTrade.setMerchantName(merchant_name);
            }
            scaleTrade.setMerchantId(merchantInfo.getMerchant_id() + "");

            scaleTrade.setMarketId(String.valueOf(market_id));
            scaleTrade.setGoodsName(beanData.getName());
            scaleTrade.setUser(beanData.getUser());
            scaleTrade.setUnitPrice(beanData.getUnitPrice());
            scaleTrade.setWeightPcs(beanData.getWeightPcs());
            scaleTrade.setTotPrice(Double.parseDouble(MathUtils.keepTwoDecimalDigitsAndFourToFive(beanData.getTotPrice())));
            scaleTrade.setPlu(beanData.getPlu());
            scaleTrade.setTradeNo(beanData.getTradeNo());
            // ???????????????
            scaleTrade.setStallNum(stall_num);
            // ??????
            scaleTrade.setScaleNum(beanData.getScaleNum());
            // ???????????????
            scaleTrade.setScaleStallNum(scaleStallNum);
            scaleTrade.setTradeTime(beanData.getTradeTime());
            scaleTrade.setTradeUnit(beanData.getUnit());
            scaleTrade.setPayType(payType);
            // ????????????????????????????????????
            scaleTrade.setIDataType(iDataType);
            scaleTrade.setTradeDataSources(AppConstants.TradeDataSources.ZSLF_BLE);
            // ???????????????
            scaleTrade.setIsUpdate(AppConstants.CommonInt.NO_INT);
            LogUtils.E("???????????????", "" + scaleTrade.getIsUpdate());
            LogUtils.E("???????????????", scaleTrade.getTradeTime());

            scaleTrades.add(scaleTrade);
            return scaleTrades;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * List<ScaleTrade>??????ScaleTrade ?????????
     *
     * @param scaleTrades
     * @param bleSettingCheckOutBean
     * @param payType
     * @return
     */
    private List<ScaleTrade> listAddScaleTrade(List<ScaleTrade> scaleTrades, BleSettingCheckOutBean bleSettingCheckOutBean, String payType) {

        try {
            LoginLocalData loginLocalData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
            LoginRes loginRes = loginLocalData.getLoginRes();
            String stall_id = loginRes.getStall_id();
            String stall_num = String.valueOf(stall_id);
            String market_id = loginRes.getMarket_id();

            ScaleTrade scaleTrade = new ScaleTrade();
            // ???????????????
            String scaleStallNum = bleSettingCheckOutBean.getStallNum();
            // ?????????????????????????????????????????????????????????????????????
//            if (stall_num.equals(scaleStallNum) || stall_num.equals(scaleStallNum.toUpperCase())) {
//                scaleTrade.setMerchantName(stall_num);
//                // ???????????????????????????
//            } else {
//                scaleTrade.setMerchantName(AppConstants.CommonStr.DEFAULT_MERCHANT_NAME);
//            }
            MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);
            String merchant_name = merchantInfo.getMerchant_name();
            if (TextUtils.isEmpty(merchant_name)) {
                scaleTrade.setMerchantName(AppConstants.CommonStr.DEFAULT_MERCHANT_NAME);
            } else {
                scaleTrade.setMerchantName(merchant_name);
            }
            scaleTrade.setMerchantId(merchantInfo.getMerchant_id() + "");

            scaleTrade.setMarketId(String.valueOf(market_id));
            scaleTrade.setGoodsName(bleSettingCheckOutBean.getName());
            scaleTrade.setUser(bleSettingCheckOutBean.getUser());
            scaleTrade.setUnitPrice(bleSettingCheckOutBean.getUnitPrice());
            scaleTrade.setWeightPcs(bleSettingCheckOutBean.getWeightPcs());
            scaleTrade.setTotPrice(Double.parseDouble(MathUtils.keepTwoDecimalDigitsAndFourToFive(bleSettingCheckOutBean.getPayMoney())));
            scaleTrade.setPlu(bleSettingCheckOutBean.getPlu());
            scaleTrade.setTradeNo(bleSettingCheckOutBean.getTradeNo());
            // ???????????????
            scaleTrade.setStallNum(stall_num);
            // ??????
            scaleTrade.setScaleNum(bleSettingCheckOutBean.getScaleNum());
            // ???????????????
            scaleTrade.setScaleStallNum(scaleStallNum);
            scaleTrade.setTradeTime(bleSettingCheckOutBean.getTradeTime());
            scaleTrade.setTradeUnit(bleSettingCheckOutBean.getUnit());
            scaleTrade.setPayType(payType);
            // ??????????????????
            scaleTrade.setIDataType(AppConstants.CommonInt.NO_INT);
            scaleTrade.setTradeDataSources(AppConstants.TradeDataSources.ZSLF_BLE);
            // ???????????????
            scaleTrade.setIsUpdate(AppConstants.CommonInt.NO_INT);
            scaleTrades.add(scaleTrade);
            return scaleTrades;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //?????? ?????? ?????? v1 v2 v3  ????????????
    private int switchType = -1;

    /**
     * ?????? ????????????
     */
    private void dealWithRegister(String json) {
        try {
            BleSettingRegisterBean bleSettingRegisterBean = JSON.parseObject(json, BleSettingRegisterBean.class);
            String user = bleSettingRegisterBean.getUser();
            // V1
            if (AppConstants.ScalePay.USER_V1.equals(user)) {
                switchRegisterData(mListV1, orderAdapter1);
                switchType = 1;
                String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV1);
                mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
                // V2
            } else if (AppConstants.ScalePay.USER_V2.equals(user)) {
                switchType = 2;
                switchRegisterData(mListV2, orderAdapter2);
                String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV2);
                mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
            } else if (AppConstants.ScalePay.USER_V4.equals(user)) {
                // V4
                switchType = 4;
                switchRegisterData(mListV4, orderAdapter4);
                String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV4);
                mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
            } else if (AppConstants.ScalePay.USER_V5.equals(user)) {
                //V5
                switchType = 5;
                switchRegisterData(mListV5, orderAdapter5);
                String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV5);
                mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchRegisterData(List<BleCmd2Bean> list, PendingOrderAdapter orderAdapter) {


        try {
            if (list != null) {
                LogUtils.e("??????Data==>", list.toString());
                if (list.size() == 0) {
                    hideOrder();
                } else {
                    showOrder();
                }
                if (orderAdapter == null) {
                    orderAdapter = new PendingOrderAdapter(list);
                    mRvPendingOrder.setAdapter(orderAdapter);
                } else {
                    orderAdapter.setNewData(list);
                    mRvPendingOrder.setAdapter(orderAdapter);
                }
//                accumulativeReductionRecyclerViewAdapter = new AccumulativeReductionRecyclerViewAdapter(list);
//                rv.setAdapter(accumulativeReductionRecyclerViewAdapter);
                // ?????????????????????
                mTvCount.setText(String.format("%s???", list.size()));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dealWithWeight(String json) {
        BleSettingWeightingBean bleSettingWeightingBean = JSON.parseObject(json, BleSettingWeightingBean.class);
        double weight = Double.parseDouble(MathUtils.keepThreeDecimalDigits(bleSettingWeightingBean.getWeight()));
        if (weight > 0) {
            mTvWeight.setText(MathUtils.keepThreeDecimalDigits(weight));
        } else {
            mTvWeight.setText("0.000");
        }
    }


    /**
     * ????????????????????????  ????????? cmd = 1
     *
     * @param json
     */
    private void dealwithRealTimeWeighingDataBLS(String json) {
        try {
            BleSettingRealTimeWeightingBean bleSettingRealTimeWeightingBean = JSON.parseObject(json, BleSettingRealTimeWeightingBean.class);
            double weightPcs = bleSettingRealTimeWeightingBean.getWeightPcs().doubleValue();
            double unitPrice = bleSettingRealTimeWeightingBean.getUnitPrice().doubleValue();
            double totPrice = bleSettingRealTimeWeightingBean.getTotPrice().doubleValue();
            String unit = bleSettingRealTimeWeightingBean.getUnit();
            // ??????????????????????????????????????? ???????????????????????????????????????
            if (weightPcs != mWeightPcs || unitPrice != mUnitPrice) {
                LogUtils.e(TAG, "weightPcs and unitPrice change***!");
                if (weightPcs > 0 && unitPrice > 0) {
                    // ?????????????????????????????????????????????0.005??????????????????
                    if (Math.abs(new BigDecimal(String.valueOf(weightPcs)).subtract(new BigDecimal(String.valueOf(mWeightPcs))).doubleValue()) == 0.005) {
                        mIsWeightChange = false;
                    } else {
                        mIsWeightChange = true;
                    }
                }
                mWeightPcs = weightPcs;
                mUnitPrice = unitPrice;
                // ????????????????????????
                setRealTimeWeighingDataBLS(bleSettingRealTimeWeightingBean);
                // ????????????????????????????????????????????? ???????????? ??????????????????????????????????????????
            } else if (weightPcs != mWeightPcs && unitPrice != mUnitPrice) {
                LogUtils.e(TAG, "weightPcs or unitPrice change!");
                // ????????????????????????????????????????????????
            } else if (weightPcs == mWeightPcs || unitPrice == mUnitPrice) {
                LogUtils.e(TAG, "weightPcs or unitPrice not change***!");
                if (weightPcs > 0 && unitPrice > 0) {
                    // ???????????????1??? ???????????????0.005 ???????????????1???
                    if (Double.parseDouble(MathUtils.keepTwoDecimalDigitsAndFourToFive(weightPcs * unitPrice)) > 1 &&
                            Double.parseDouble(MathUtils.keepTwoDecimalDigitsAndFourToFive(weightPcs)) > 0.005 &&
                            Double.parseDouble(MathUtils.keepTwoDecimalDigitsAndFourToFive(unitPrice)) > 1) {

                        LogUtils.e(TAG, "weight not change and weight is greater than 0!");
                        LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
                        LoginRes loginRes = entity.getLoginRes();
                        while (mIsWeightChange) {

                            // ?????????????????????
                            blsWeightData(weightPcs, unitPrice, unit);
                            // ??????????????????????????????
                            if (!AppConstants.MarketId.SC_CD_GQBQ.equals(loginRes.getMarket_id())) {
                                // ??????????????????
//                                if (1 == AppConstants.UsefulSetting.TRADE_DATA_SWITCH) {
//                                    uploadTradeData();
//                                }
                            }
                            mIsWeightChange = false;
                        }
                    }
                }
                // ???????????????????????????????????????????????? ???????????? ??????????????????????????????????????????
            } else if (weightPcs == mWeightPcs && unitPrice == mUnitPrice) {
                LogUtils.e(TAG, "weightPcs and unitPrice not change!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ?????????????????????
     *
     * @param weightPcs
     * @param unitPrice
     * @param unit
     */
    @SuppressLint("SimpleDateFormat")
    private void blsWeightData(double weightPcs, double unitPrice, String unit) {
        try {
            String subtotal = MathUtils.keepTwoDecimalDigitsAndFourToFive(unitPrice * weightPcs);
            LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
            LoginRes loginRes = entity.getLoginRes();
            MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);

            String merchant_name = merchantInfo.getMerchant_name();
            int merchant_id = merchantInfo.getMerchant_id();
            // ????????????0.00 ??????
            if (!AppConstants.ScalePay.KEEP_TWO_DECIMAL_DIGITS_ZERO.equals(subtotal)) {
                LogUtils.e(TAG, "weight not change and weight is greater than 0 need to store!" + unitPrice + "*" + weightPcs + "=" + subtotal);
                List<ScaleTrade> scaleTrades = new ArrayList<>();
                ScaleTrade scaleTrade = new ScaleTrade();
                scaleTrade.setWeightPcs(weightPcs);
                scaleTrade.setUnitPrice(unitPrice);
                scaleTrade.setTotPrice(Double.parseDouble(subtotal));
                scaleTrade.setTradeUnit(unit);
                scaleTrade.setMarketId(String.valueOf(loginRes.getMarket_id()));
                scaleTrade.setStallNum(String.valueOf(loginRes.getStall_id()));
                scaleTrade.setMerchantName(merchant_name);

                scaleTrade.setMerchantId(merchant_id + "");

                scaleTrade.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())));
                // ???????????????
                scaleTrade.setIDataType(AppConstants.CommonInt.YES_INT);
                scaleTrade.setIsUpdate(AppConstants.CommonInt.NO_INT);
                scaleTrade.setTradeDataSources(AppConstants.TradeDataSources.BLS_BLE);
                scaleTrades.add(scaleTrade);
                mScaleDaoUtil.insertScaleTradeDataList(scaleTrades);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????  ???????????? cmd = 1
     *
     * @param json
     */
    private void dealwithRealTimeWeighingDataZSLF(String json) {
        try {
            BleSettingRealTimeWeightingBean bleData = JsonUtils.json2Object(json, BleSettingRealTimeWeightingBean.class);
            if (ObjectUtils.isNotEmpty(bleData)) {
                setRealTimeWeighingDataZSLF(bleData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ???????????????????????? ????????? cmd = 1
     *
     * @param bleSettingRealTimeWeightingBean
     */
    @SuppressLint("SetTextI18n")
    private void setRealTimeWeighingDataBLS(BleSettingRealTimeWeightingBean bleSettingRealTimeWeightingBean) {
        try {
            double unitPrice = bleSettingRealTimeWeightingBean.getUnitPrice().doubleValue();
            double weightPcs = bleSettingRealTimeWeightingBean.getWeightPcs().doubleValue();
            // ????????????????????????
            String price = MathUtils.keepTwoDecimalDigits(unitPrice);
            mTvPrice.setText(StringUtils.isNotEmpty2(price) ? price : "0.00");
            mTvWeight.setText(StringUtils.isNotEmpty2(MathUtils.keepThreeDecimalDigits(weightPcs)) ? MathUtils.keepThreeDecimalDigits(weightPcs) : "0.000");
            if (unitPrice <= 0 || weightPcs <= 0) {
                // ???????????????
                mTvSubTotal.setText("0.00");
                return;
            }
            // ???????????????
            String total = String.valueOf(Double.parseDouble(MathUtils.keepTwoDecimalDigitsAndFourToFive(unitPrice * weightPcs)));
            mTvSubTotal.setText(total);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????????????? ???????????? cmd = 1
     *
     * @param bleSettingRealTimeWeightingBean
     */
    @SuppressLint("SetTextI18n")
    private void setRealTimeWeighingDataZSLF(BleSettingRealTimeWeightingBean bleSettingRealTimeWeightingBean) {
        try {
//            LogUtils.e("weightBean==>", bleSettingRealTimeWeightingBean.toString());
            BigDecimal unitPrice1;
            BigDecimal totPrice;
            BigDecimal weightPcs1;
            if (ObjectUtils.isNotEmpty(bleSettingRealTimeWeightingBean.getUnitPrice())) {
                unitPrice1 = bleSettingRealTimeWeightingBean.getUnitPrice();
            } else {
                unitPrice1 = new BigDecimal("0.00");
            }

            if (ObjectUtils.isNotEmpty(bleSettingRealTimeWeightingBean.getWeightPcs())) {
                weightPcs1 = bleSettingRealTimeWeightingBean.getWeightPcs();
            } else {
                weightPcs1 = new BigDecimal("0.000");
            }

            if (ObjectUtils.isNotEmpty(bleSettingRealTimeWeightingBean.getTotPrice())) {
                totPrice = bleSettingRealTimeWeightingBean.getTotPrice();
            } else {
                totPrice = new BigDecimal("0.00");
            }

            // ????????????????????????

            mTvPrice.setText(MathUtils.keepTwoDecimalDigits(unitPrice1.doubleValue()));
            mTvWeight.setText(MathUtils.keepThreeDecimalDigits(weightPcs1.doubleValue()));
            if (unitPrice1.doubleValue() < 0 || weightPcs1.doubleValue() < 0) {
                // ???????????????
                mTvSubTotal.setText("0.00");
                return;
            }
            // ???????????????
            mTvSubTotal.setText(MathUtils.keepTwoDecimalDigitsAndFourToFive(totPrice.doubleValue()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ?????????????????????????????? cmd = 2
     * ??????????????????
     *
     * @param json
     * @param
     */
    @SuppressLint("SetTextI18n")
    private void dealWithPendingOrderData(String json) {
        try {
            LogUtils.e("pendingOrder", json);
            BleCmd2Bean dataBean = JSON.parseObject(json, BleCmd2Bean.class);
            String user = dataBean.getUser();
            int isDelete = dataBean.getIsDelete();

            if (TextUtils.isEmpty(user)) {
                LogUtils.e("????????????user...");
                hideOrder();
                return;
            }

            if (user.equals(" ")) {
                //?????? ??????????????????
                LogUtils.e("?????? ??????????????????...");
                hideOrder();
                // ????????????????????????????????????????????????
                // ????????? user=" " ??????????????????
                // ?????????????????????????????????????????????????????????  ???????????????
//                   isDelete -1 ?????????  1 ?????????
                if (-1 == isDelete || isDelete == 1) {
                    mTvWeight.setText("0.00");
                    mTvPrice.setText("0.00");
                    mTvSubTotal.setText("0.00");

                    // ??? ???????????????????????? ??????????????????
//                    if (1 == AppConstants.UsefulSetting.TRADE_DATA_SWITCH) {
                    getScaleWeightDelData(dataBean);
//                    }
//                    uploadTradeData();
                }


                return;
            }
            // ????????????
            showOrder();

            // ????????? user=V1 ????????????
            if (AppConstants.ScalePay.USER_V1.equals(user)) {
                // ????????????????????????
                if (dataBean.getIsRefund() == AppConstants.CommonInt.NO_INT) {
                    // ??????????????????????????????
                    if (dataBean.getIsDelete() == AppConstants.CommonInt.NO_INT) {
                        mListV1.add(dataBean.getListIndex() - 1, dataBean);
                        for (int i = 0; i < mListV1.size(); i++) {
                            mSubTotPriceV1 = mListV1.get(i).getTotPrice();
                        }
                        mTotPriceV1 += mSubTotPriceV1;
                        // ??????????????????

                        String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV1);
                        mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");

                        // ??????????????????
//                        mBaseView.showTextView(tvBill, mListV1.size() + mContext.getString(R.string.tv_commercial_info_electronic_scale_bill), R.string.tv_commercial_info_electronic_scale_top_total_form);
                        mTvCount.setText(mListV1.size() + "???");
                        // ??????????????????????????????
                    } else if (dataBean.getIsDelete() == AppConstants.CommonInt.YES_INT) {
                        int index = dataBean.getListIndex() - 1;
                        mTotPriceV1 -= mListV1.get(index).getTotPrice();
                        // ??????????????????
                        String total2 = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV1);
                        mTvTotalPay.setText(StringUtils.isNotEmpty2(total2) ? total2 : "0.00");
                        mListV1.remove(index);
                        // ??????????????????
                        mTvCount.setText(mListV1.size() + "???");
                    }

                    LogUtils.e("mList1", mListV1.toString());
                    orderAdapter1.setNewData(mListV1);
                    mRvPendingOrder.setAdapter(orderAdapter1);
                }
                // ????????? user=V2 ????????????
            } else if (AppConstants.ScalePay.USER_V2.equals(user)) {
                // ????????????????????????
                if (dataBean.getIsRefund() == AppConstants.CommonInt.NO_INT) {
                    // ??????????????????????????????
                    if (dataBean.getIsDelete() == AppConstants.CommonInt.NO_INT) {
                        mListV2.add(dataBean.getListIndex() - 1, dataBean);
                        for (int i = 0; i < mListV2.size(); i++) {
                            mSubTotPriceV2 = mListV2.get(i).getTotPrice();
                        }
                        mTotPriceV2 += mSubTotPriceV2;
                        // ??????????????????
                        String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV2);
                        mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
                        // ??????????????????
                        mTvCount.setText(mListV2.size() + "???");
                        // ??????????????????????????????
                    } else if (dataBean.getIsDelete() == AppConstants.CommonInt.YES_INT) {
                        int index = dataBean.getListIndex() - 1;
                        mTotPriceV2 -= mListV2.get(index).getTotPrice();
                        // ??????????????????
                        String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV2);
                        mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
                        mListV2.remove(index);
                        // ??????????????????
                        mTvCount.setText(mListV2.size() + "???");
                    }

                    LogUtils.e("mList2", mListV2.toString());
                    orderAdapter2.setNewData(mListV2);
                    mRvPendingOrder.setAdapter(orderAdapter2);
                }
            } else if (AppConstants.ScalePay.USER_V4.equals(user)) {
                // ????????????????????????
                if (dataBean.getIsRefund() == AppConstants.CommonInt.NO_INT) {
                    // ??????????????????????????????
                    if (dataBean.getIsDelete() == AppConstants.CommonInt.NO_INT) {
                        mListV4.add(dataBean.getListIndex() - 1, dataBean);
                        for (int i = 0; i < mListV4.size(); i++) {
                            mSubTotPriceV4 = mListV4.get(i).getTotPrice();
                        }
                        mTotPriceV4 += mSubTotPriceV4;
                        // ??????????????????
                        String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV4);
                        mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
                        // ??????????????????
                        mTvCount.setText(mListV4.size() + "???");
                        // ??????????????????????????????
                    } else if (dataBean.getIsDelete() == AppConstants.CommonInt.YES_INT) {
                        int index = dataBean.getListIndex() - 1;
                        mTotPriceV4 -= mListV4.get(index).getTotPrice();
                        // ??????????????????
                        String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV4);
                        mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
                        mListV4.remove(index);
                        // ??????????????????
                        mTvCount.setText(mListV4.size() + "???");
                    }


                    LogUtils.e("mList4", mListV4.toString());
                    orderAdapter4.setNewData(mListV4);
                    mRvPendingOrder.setAdapter(orderAdapter4);

                } else if (AppConstants.ScalePay.USER_V5.equals(user)) {
                    // ????????????????????????
                    if (dataBean.getIsRefund() == AppConstants.CommonInt.NO_INT) {
                        // ??????????????????????????????
                        if (dataBean.getIsDelete() == AppConstants.CommonInt.NO_INT) {
                            mListV5.add(dataBean.getListIndex() - 1, dataBean);
                            for (int i = 0; i < mListV5.size(); i++) {
                                mSubTotPriceV5 = mListV5.get(i).getTotPrice();
                            }
                            mTotPriceV5 += mSubTotPriceV5;
                            // ??????????????????
                            String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV5);
                            mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
                            // ??????????????????
                            mTvCount.setText(mListV5.size() + "???");

                            // ??????????????????????????????
                        } else if (dataBean.getIsDelete() == AppConstants.CommonInt.YES_INT) {
                            int index = dataBean.getListIndex() - 1;
                            mTotPriceV5 -= mListV5.get(index).getTotPrice();
                            // ??????????????????
                            String total = MathUtils.keepTwoDecimalDigitsAndFourToFive(mTotPriceV5);

                            mTvTotalPay.setText(StringUtils.isNotEmpty2(total) ? total : "0.00");
                            mListV5.remove(index);
                            // ??????????????????
                            mTvCount.setText(mListV5.size() + "???");
                        }

                        LogUtils.e("mList5", mListV5.toString());
                        orderAdapter5.setNewData(mListV5);
                        mRvPendingOrder.setAdapter(orderAdapter5);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ????????????????????????????????????????????? cmd = 2  ???????????????????????? ???  ??????  ?????????
     *
     * @param bleCmd2Bean
     */
    private void getScaleWeightDelData(BleCmd2Bean bleCmd2Bean) {
        try {
//            is_refund
//            0????????????????????????
//            1???????????????

            int isRefund = bleCmd2Bean.getIsRefund();

            if (isRefund == 1) {
                LogUtils.d("?????????");
                return;
            }
            // ????????????????????????
            // ??????????????????????????????
            if (bleCmd2Bean.getIsDelete() == AppConstants.CommonInt.YES_INT || bleCmd2Bean.getIsDelete() == -1) {
                // ???????????????0.1 ????????????0.002???????????????????????????
                if (bleCmd2Bean.getTotPrice() > 0.1 && bleCmd2Bean.getWeightPcs() > AppConstants.ScalePay.MIN_SCALE_WEIGHT) {
                    if (bleCmd2Bean.getIsDelete() == -1 || bleCmd2Bean.getIsDelete() == 1) {
//                        -1 ????????? 1 ?????????
                        LogUtils.e("insert -->");
                        mScaleDaoUtil.insertScaleTradeDataList(listAddScaleTrade(new ArrayList<ScaleTrade>(), bleCmd2Bean
                                , String.valueOf(AppConstants.ZSLF.UNPAID), AppConstants.CommonInt.YES_INT));
                        EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                    }
                } else {
                    LogUtils.e(TAG, "The ScaleWeightDel total price is less than 0.1!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ????????????????????????????????? ???????????????????????????
     * <p>
     * ?????????????????? ??????????????? ????????????.
     */
//    private void uploadTradeData() {
//        try {
//            // ???????????????????????? ??????????????????????????????????????? ????????? submit trade data return:{"status":"false"} ??????????????????????????????
//            List<ScaleTrade> tradeDataList = mScaleDaoUtil.queryScaleTradeDataList(ScaleTrade.class)
//                    .where(ScaleTradeDao.Properties.IsUpdate.eq(AppConstants.CommonInt.NO_INT))
//                    // ??????????????????????????????????????????
//                    //   .where(ScaleTradeDao.Properties.TradeTime.between(TimeUtils.getMonthAgoTime() , TimeUtils.getCurrentTime()))
//                    // 10????????????????????????????????????
////                    .where(ScaleTradeDao.Properties.TradeTime.between(TimeUtils.getDayAgoTime(10), TimeUtils.getCurrentTime()))
//                    .limit(10)
//                    .offset(10)
//                    .list();
//            LogUtils.e(TAG, "ago:" + TimeUtils.getDayAgoTime(10) + ",now:" + TimeUtils.getCurrentTime());
//            LogUtils.e(TAG,ScaleTradeDao.Properties.TradeTime.columnName);
//            LogUtils.e(TAG, "need to submit trade data count:" + tradeDataList.size());
//            // ?????????????????????
//            if (0 == AppConstants.UsefulSetting.SET_SCALE_VENDER) {
//                // ???????????????????????????
//                List<ScaleTrade> newTradeDataList = getNewList(tradeDataList, 5);
//                // ??????????????????????????? ????????????????????????????????????????????????????????????????????????????????????????????????detail???goods_weigh????????????????????????id???????????????
//                commonSubmitTradeData(newTradeDataList);
//            } else {
//                commonSubmitTradeData(tradeDataList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * ????????????????????? ????????????
     */
//    private void commonSubmitTradeData(List<ScaleTrade> tradeDataList) {
//        try {
//            if (tradeDataList == null || tradeDataList.size() <= 0) {
//                LogUtils.e("submit Trade is NULL !");
//                return;
//            }
//
////            String rowsCount = MmkvUtils.getInstance().getStringWithDefault(Constant.ROWS_COUNT, "30");
////            LogUtils.e("?????????==>", tradeDataList.toString());
////            LogUtils.e("rowsCount==>", rowsCount);
////            int count = Integer.parseInt(rowsCount);
//
//            submitTradeData(tradeDataList);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    public static List<ScaleTrade> getNewList(List<ScaleTrade> oldList, int space) {
        List<ScaleTrade> last = new ArrayList<>(); // ????????????????????????????????????
        int index = 0;
        int size = oldList.size();
        for (int i = 0; i < size; i++) {
            if (index + space < size) {
                List<ScaleTrade> newScaleTrades = oldList.subList(index, index + space);
                double maxPrice = newScaleTrades.get(0).getTotPrice();
                for (ScaleTrade scaleTrade : newScaleTrades) {
                    if (maxPrice < scaleTrade.getTotPrice()) {
                        last.add(scaleTrade);
                    }
                }

            } else {
                if (index < size) {
                    List<ScaleTrade> newScaleTrades = oldList.subList(index, oldList.size());
                    double maxPrice = newScaleTrades.get(0).getTotPrice();
                    for (ScaleTrade scaleTrade : newScaleTrades) {
                        if (maxPrice < scaleTrade.getTotPrice()) {
                            last.add(scaleTrade);
                        }
                    }
                }
            }
            index = index + space;
        }
        LogUtils.e(TAG, "=================????????????=============" + last);
        LogUtils.e(TAG, "=================????????????===??????==========" + last.size());
        return last;
    }


    /**
     * TODO
     * ????????????????????? ????????????(??????????????????????????????????????????)   cmd = 5
     *
     * @param json
     */
    private String code_url = "";
//    boolean isPlaySound =false;

    private void dealWithQrcodeZSLF(final String json) {
        try {
            LogUtils.e("cmd5111111 ", json);

            final CMD5Bean cmd5Bean = new CMD5Bean();
            CMD5Bean cmd5Bean1 = JSON.parseObject(json, CMD5Bean.class);

            String qrcode = cmd5Bean1.getQrcode();
            cmd5Bean.setItem_num(cmd5Bean1.getItem_num());
            cmd5Bean.setTradeTime(cmd5Bean1.getTradeTime());
            cmd5Bean.setUnitPrice(cmd5Bean1.getUnitPrice());
            cmd5Bean.setUnit(cmd5Bean.getUnit());
            cmd5Bean.setWeightPcs(cmd5Bean1.getWeightPcs());
            cmd5Bean.setName(cmd5Bean1.getName());
            cmd5Bean.setTradeNo(cmd5Bean1.getTradeNo());
            cmd5Bean.setQrcode(cmd5Bean1.getQrcode());
            cmd5Bean.setPayMoney(cmd5Bean1.getPayMoney());
            cmd5Bean.setCmd(cmd5Bean1.getCmd());

            int payType = cmd5Bean1.getPayType();
            switch (payType) {
                case 0:
                    cmd5Bean.setPayType(1002);
//                    orderPayType.setPayTitle("????????????");
                    break;
                case 1:
                    cmd5Bean.setPayType(1004);
//                    orderPayType.setPayTitle("??????????????????");
                    break;
                case 2:
                    cmd5Bean.setPayType(1007);
//                    orderPayType.setPayTitle("????????????");

                    break;
                case 3:
                    cmd5Bean.setPayType(1009);
//                    orderPayType.setPayTitle("????????????");
                    break;
                case 4:
                    cmd5Bean.setPayType(1006);
//                    orderPayType.setPayTitle("???????????????");
                    break;
                case 5:
                    cmd5Bean.setPayType(1010);
//                    orderPayType.setPayTitle("???????????????");
                    break;
                case 6:
                    cmd5Bean.setPayType(1005);
//                    orderPayType.setPayTitle("???????????????");
                    break;
                case 7:
                    cmd5Bean.setPayType(1004);

                    break;
                default:
                    cmd5Bean.setPayType(1002);
//                    orderPayType.setPayTitle("??????????????????");
                    break;
            }

            if (BuildConfig.DEBUG) {
                LogUtils.d("cmd5-->", cmd5Bean.toString());
            }
//            qrcode=NULL????????????????????????????????????qrcode =??????????????????????????????????????????

            if (qrcode != null && qrcode.equals("")) {
//                isPlaySound = false;
                //?????????????????????  ?????????????????????
                mListV1.clear();
                mListV2.clear();
                mListV4.clear();
                mListV5.clear();

                orderAdapter1.getData().clear();
                orderAdapter1.setNewData(new ArrayList<BleCmd2Bean>());

                orderAdapter2.getData().clear();
                orderAdapter2.setNewData(new ArrayList<BleCmd2Bean>());

                orderAdapter4.getData().clear();
                orderAdapter4.setNewData(new ArrayList<BleCmd2Bean>());

                orderAdapter5.getData().clear();
                orderAdapter5.setNewData(new ArrayList<BleCmd2Bean>());

                mTvCount.setText("");
                mTvTotalPay.setText("0.00");
                hideOrderPay();
                hideOrder();

                return;
            }


            final List<BleCmd2Bean> data = new ArrayList<>();
            data.clear();

            if (switchType == 1) {

                data.addAll(mListV1);
            } else if (switchType == 2) {
                data.addAll(mListV2);

            } else if (switchType == 4) {
                data.addAll(mListV4);

            } else if (switchType == 5) {
                data.addAll(mListV5);

            }
            //TODO ?????? ??????????????? ????????????....

            final boolean isGuaDan = data.size() > 0;
            LogUtils.e("????????????", isGuaDan + "");


            HttpUtils.getInstance().activePay(cmd5Bean, data, new HttpUtils.HttpCallBack() {


                @Override
                public void onSuccess(String jsonData) {
                    if (TextUtils.isEmpty(jsonData)) {


                        LogUtils.e("???????????? ???????????????..");

                        return;
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        int code = jsonObject.getInt("code");
                        if (code == 0) {
                            String msg = jsonObject.getString("msg");
                            ToastUtils.showShort(msg);
                            getScaleWeightAccTradeData(data, String.valueOf(cmd5Bean.getPayType()));
                            EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                            return;
                        }

                        LogUtils.e("data -> ", jsonData);

                        OrderCodeRes orderCodeRes = new Gson().fromJson(jsonData, OrderCodeRes.class);
                        if (orderCodeRes == null) {
                            LogUtils.e("orderCodeRes IS NULL!");
                            return;
                        }
                        if (orderCodeRes.getCode() == 1 && orderCodeRes.getData() != null) {
                            if (TextUtils.isEmpty(orderCodeRes.getData().getCode_url())) {
                                LogUtils.e("getCode_url IS empty!");
                                return;
                            }
                            code_url = orderCodeRes.getData().getCode_url();
                            mainThreadExecutor.execute(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {

                                    showOrderPay(isGuaDan);

                                    //?????? ??????????????????
                                    mIvPayCode.setImageBitmap(CodeUtils.createImage(code_url, AppConstants.DefaultSetting.SET_QRCODE_WIDTH,
                                            AppConstants.DefaultSetting.SET_QRCODE_HEIGHT, null));


                                    String tradeNo = cmd5Bean.getTradeNo();
                                    CacheUtils.putString(AppConstants.Cache.SCALE_PAY_TRADENO, tradeNo);
                                    OrderPayType payType = ParamsUtils.getPayType(cmd5Bean);

                                    mTvPayType.setText(payType.getPayTitle());
                                    /**
                                     *  ????????????
                                     * */
                                    mTvXpMomey.setText(cmd5Bean.getPayMoney() + "???");
                                    /**
                                     *  ?????? ?????? ???????????????0.00
                                     * */
                                    mTvYhMoney.setText("0.00???");
                                    // ???????????????????????????
                                    mTvActualPay.setText(cmd5Bean.getPayMoney() + "???");
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFail(String errorMsg) {
                    getScaleWeightAccTradeData(data, String.valueOf(cmd5Bean.getPayType()));
                    EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                }
            });


            // ??????????????????????????????
            // ??????????????????????????????


            //  ????????? ?????????????????????????????? ?????? ??????
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mainThreadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
//                            String payMoney = MathUtils.keepTwoDecimalDigitsAndFourToFive(cmd5Bean.getPayMoney());
                            LogUtils.e("cmd5", json);
//                            CMD5Bean cmd5Bean = JSON.parseObject(json, CMD5Bean.class);

//                            if (cmd5Bean.getPayMoney() > 0) {
//                                SoundPoolPlayUtils.newInstance().playNumber(AppConstants.ScaleVoice.SUCCESS_OTHERS, String.valueOf(cmd5Bean.getPayMoney()));
//                            }

                            hideOrderPay();
                            hidePaySuccess();
                            /**
                             * ??????????????? ?????? ??????UI
                             * ????????????
                             * */
                            hideOrder();
                        }
                    });
                }
            }, AppConstants.DefaultSetting.SET_QRCODE_HIDE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showOrderPay(boolean isGuaDan) {
        if (isGuaDan) {
            mViewBannerWindowBg.setVisibility(View.VISIBLE);
            mViewPendingOrder.setVisibility(View.VISIBLE);
            mTrOrder.setVisibility(View.VISIBLE);
            mRvPendingOrder.setVisibility(View.VISIBLE);
        } else {
            mViewBannerWindowBg.setVisibility(View.INVISIBLE);
            mViewPendingOrder.setVisibility(View.INVISIBLE);
            mTrOrder.setVisibility(View.INVISIBLE);
            mRvPendingOrder.setVisibility(View.INVISIBLE);
        }


        mViewTotalOrder.setVisibility(View.VISIBLE);
        mTrPay.setVisibility(View.VISIBLE);
        mClPay.setVisibility(View.VISIBLE);
    }

    public void hideOrderPay() {
        mViewBannerWindowBg.setVisibility(View.GONE);
        mViewPendingOrder.setVisibility(View.GONE);
        mTrOrder.setVisibility(View.GONE);
        mRvPendingOrder.setVisibility(View.GONE);

        mViewTotalOrder.setVisibility(View.GONE);
        mTrPay.setVisibility(View.GONE);
        mClPay.setVisibility(View.GONE);
    }


    public void hidePaySuccess() {
        mViewBannerWindowBg.setVisibility(View.GONE);
        mViewPendingOrder.setVisibility(View.GONE);
        mTrOrder.setVisibility(View.GONE);
        mRvPendingOrder.setVisibility(View.GONE);

        cl_pay_success.setVisibility(View.GONE);
        mViewTotalOrder.setVisibility(View.GONE);
    }

    public void showPaySuccess(boolean notGuaDan) {
        if (notGuaDan) {
            //????????? ??????
            mViewBannerWindowBg.setVisibility(View.INVISIBLE);
            mViewPendingOrder.setVisibility(View.INVISIBLE);
            mTrOrder.setVisibility(View.INVISIBLE);
            mRvPendingOrder.setVisibility(View.INVISIBLE);
        } else {
            // ????????? ??????
            mViewBannerWindowBg.setVisibility(View.VISIBLE);
            mViewPendingOrder.setVisibility(View.VISIBLE);
            mTrOrder.setVisibility(View.VISIBLE);
            mRvPendingOrder.setVisibility(View.VISIBLE);
        }


        mViewTotalOrder.setVisibility(View.VISIBLE);
        cl_pay_success.setVisibility(View.VISIBLE);
    }


    /**
     * ??????????????????
     */
    public void showOrder() {
        mViewBannerWindowBg.setVisibility(View.VISIBLE);
        mViewPendingOrder.setVisibility(View.VISIBLE);
        mTrOrder.setVisibility(View.VISIBLE);
        mRvPendingOrder.setVisibility(View.VISIBLE);
    }

    /**
     * ????????????
     */
    public void hideOrder() {
        mViewBannerWindowBg.setVisibility(View.GONE);
        mViewPendingOrder.setVisibility(View.GONE);
        mTrOrder.setVisibility(View.GONE);
        mRvPendingOrder.setVisibility(View.GONE);
    }


    /**
     * ?????????????????????
     *
     * @param tradeNo
     * @param payMoney
     * @param ivQrcode
     * @param ivPayIcon
     * @param tvPayType
     */


    /**
     * ?????????????????? ????????????(??????????????????????????????????????????????????????)  cmd = 3
     * ????????????.
     *
     * @param json
     */
    @SuppressLint("SetTextI18n")
    private void dealWithCheckOutDataZSLF(String json) {
        try {
//            {"cmd":3,"user":"V3","pay_money":0.10,"pay_type":0,"trade_no":"5521011200273","trade_time":"20210112091209","scale_num":"5","stall_num":"5",
//                    "unit":"kg","name":"?????????","unit_price":0.80,"weight_pcs":0.120,"plu":12886,"item_num":"12886"}
            LogUtils.e("cmd:3", json);
            final BleSettingCheckOutBean bleSettingCheckOutBean = JSON.parseObject(json, BleSettingCheckOutBean.class);
            int payType1 = bleSettingCheckOutBean.getPayType();
            switch (payType1) {
                case 0:
                    bleSettingCheckOutBean.setPayType(1002);
//                    orderPayType.setPayTitle("????????????");
                    break;
                case 1:
                    bleSettingCheckOutBean.setPayType(1004);
//                    orderPayType.setPayTitle("??????????????????");
                    break;
                case 2:
                    bleSettingCheckOutBean.setPayType(1007);
//                    orderPayType.setPayTitle("????????????");

                    break;
                case 3:
                    bleSettingCheckOutBean.setPayType(1009);
//                    orderPayType.setPayTitle("????????????");
                    break;
                case 4:
                    bleSettingCheckOutBean.setPayType(1006);
//                    orderPayType.setPayTitle("???????????????");
                    break;
                case 5:
                    bleSettingCheckOutBean.setPayType(1010);
//                    orderPayType.setPayTitle("???????????????");
                    break;
                case 6:
                    bleSettingCheckOutBean.setPayType(1005);
//                    orderPayType.setPayTitle("???????????????");
                    break;
                case 7:
                    bleSettingCheckOutBean.setPayType(1004);

                    break;


                default:
                    bleSettingCheckOutBean.setPayType(1002);
//                    orderPayType.setPayTitle("??????????????????");
                    break;
            }


            Log.e("CheckOutDataZSLF==>", bleSettingCheckOutBean.toString());
            final String user = bleSettingCheckOutBean.getUser();
            final String payMoney = MathUtils.keepTwoDecimalDigitsAndFourToFive(bleSettingCheckOutBean.getPayMoney());
            final int payType = bleSettingCheckOutBean.getPayType();
            final String tradeNo = bleSettingCheckOutBean.getTradeNo();

//            LoginLocalData entity = MmkvUtils.getInstance().getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
//            LoginRes loginRes = entity.getLoginRes();
//            int market_id = loginRes.getMarket_id();
//            {cmd=3, user='V3', payMoney=3.12, payType=0, tradeNo='wzywzy20122269554',
//            tradeTime=20201222194414, unit='kg', name='????????????', unitPrice=6.0,
//            weightPcs=0.52, plu=16807, scaleNum='wzy', stallNum='wzy'}
            // ???V3??????????????????????????????
            boolean notGuaDan = AppConstants.ScalePay.USER_V3.equals(user);

            //?????? ??????
            mTvTotalPay.setText(String.format("%s", bleSettingCheckOutBean.getPayMoney()));


            /**
             *  ????????????
             * */
            mTvXpMomey.setText(StringUtils.isNotEmpty2(payMoney) ? payMoney + "???" : "0.00???");
            /**
             *  ?????? ?????? ???????????????0.00
             * */
            mTvYhMoney.setText("0.00???");

            // ???????????????????????????
//            mBaseView.showTextView(tvPay, mContext.getString(R.string.tv_commercial_info_electronic_scale_rmb) + payMoney, R.string.tv_commercial_info_electronic_scale_top_price);
            mTvActualPay.setText(StringUtils.isNotEmpty2(payMoney) ? "???" + payMoney : "0.00");

            showPaySuccess(notGuaDan);

            // ??????????????????????????????
            // ?????????????????????????????? gif?????????????????????
            GlideApp.with(mActivity).load(R.drawable.pay_finish).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                    if (drawable instanceof GifDrawable) {
                        GifDrawable gifDrawable = (GifDrawable) drawable;
                        gifDrawable.setLoopCount(AppConstants.DefaultSetting.SET_PAY_ANIM_LOOP_COUNT);
                        iv_pay_success.setImageDrawable(drawable);
                        gifDrawable.start();
                    }
                }
            });

            if (mainThreadExecutor == null) {
                mainThreadExecutor = new MainThreadExecutor();
            }


            // 3????????????????????????????????????????????????????????????
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mainThreadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            mTvCount.setText("0???:");
                            mTvTotalPay.setText("0.00");
                            mTvWeight.setText("0.000");
                            mTvPrice.setText("0.00");
                            mTvSubTotal.setText("0.00");

                            hideOrderPay();
                            hidePaySuccess();
                            /**
                             * ??????????????? ?????? ??????UI
                             * ????????????
                             * */
                            hideOrder();
                            // V1??????
                            if (AppConstants.ScalePay.USER_V1.equals(user)) {
                                // ?????????????????????????????????????????????
//                                getScaleWeightAccTradeData(mListV1, String.valueOf(payType));
                                mTotPriceV1 = AppConstants.CommonInt.KEEP_TWO_DECIMAL_DIGITS_ZERO;
                                try {
                                    LogUtils.e("V1 ??????????????????");
                                    final CMD5Bean cmd5Bean = new CMD5Bean();
                                    cmd5Bean.setPayMoney(Double.parseDouble(payMoney));
                                    cmd5Bean.setPayType(payType);
                                    cmd5Bean.setTradeNo(tradeNo);

                                    HttpUtils.getInstance().activePay(cmd5Bean, mListV1, new HttpUtils.HttpCallBack() {
                                        @Override
                                        public void onSuccess(String jsonData) {

                                            if (TextUtils.isEmpty(jsonData)) {
                                                //??????
                                                mListV1.clear();
                                                if (orderAdapter1 != null) {
                                                    orderAdapter1.getData().clear();
                                                    orderAdapter1.notifyDataSetChanged();
                                                }
                                                getScaleWeightAccTradeData(mListV1, String.valueOf(payType));
                                                EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                                                return;
                                            }

                                            //?????? ??????...
                                            try {
                                                JSONObject jsonObject = new JSONObject(jsonData);
                                                int code = (int) jsonObject.get("code");
                                                if (code == 0) {

                                                    getScaleWeightAccTradeData(mListV2, String.valueOf(payType));
                                                    EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                                                } else if (code == 1) {

                                                    String codeUrl = Constant.Trace_Code + cmd5Bean.getTradeNo();
                                                    Bitmap imgBitmap = CodeUtils.createImage(codeUrl, AppConstants.DefaultSetting.SET_QRCODE_WIDTH,
                                                            AppConstants.DefaultSetting.SET_QRCODE_HEIGHT, null);
                                                    if (imgBitmap != null) {
                                                        iv_gzh.setImageBitmap(imgBitmap);
                                                    }

                                                }

                                                mListV2.clear();
                                                if (orderAdapter2 != null) {
                                                    orderAdapter2.getData().clear();
                                                    orderAdapter2.notifyDataSetChanged();
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            mListV1.clear();
                                            if (orderAdapter1 != null) {
                                                orderAdapter1.getData().clear();
                                                orderAdapter1.notifyDataSetChanged();
                                            }

                                        }

                                        @Override
                                        public void onFail(String errorMsg) {

                                            getScaleWeightAccTradeData(mListV1, String.valueOf(payType));
                                            EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));

                                            mListV1.clear();
                                            if (orderAdapter1 != null) {
                                                orderAdapter1.getData().clear();
                                                orderAdapter1.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                // V2??????
                            } else if (AppConstants.ScalePay.USER_V2.equals(user)) {
                                // ?????????????????????????????????????????????

                                mTotPriceV2 = AppConstants.CommonInt.KEEP_TWO_DECIMAL_DIGITS_ZERO;
                                try {
                                    LogUtils.e("V2 ??????????????????");
                                    final CMD5Bean cmd5Bean = new CMD5Bean();
                                    cmd5Bean.setPayMoney(Double.parseDouble(payMoney));
                                    cmd5Bean.setPayType(payType);
                                    cmd5Bean.setTradeNo(tradeNo);

                                    HttpUtils.getInstance().activePay(cmd5Bean, mListV2, new HttpUtils.HttpCallBack() {
                                        @Override
                                        public void onSuccess(String jsonData) {


                                            //?????? ??????...
                                            try {

                                                if (TextUtils.isEmpty(jsonData)) {
                                                    //??????
                                                    mListV2.clear();
                                                    if (orderAdapter2 != null) {
                                                        orderAdapter2.getData().clear();
                                                        orderAdapter2.notifyDataSetChanged();
                                                    }
                                                    getScaleWeightAccTradeData(mListV2, String.valueOf(payType));
                                                    EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                                                    return;
                                                }

                                                JSONObject jsonObject = new JSONObject(jsonData);
                                                int code = (int) jsonObject.get("code");
                                                if (code == 0) {

                                                    getScaleWeightAccTradeData(mListV2, String.valueOf(payType));
                                                    EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                                                } else if (code == 1) {

                                                    String codeUrl = Constant.Trace_Code + cmd5Bean.getTradeNo();
                                                    Bitmap imgBitmap = CodeUtils.createImage(codeUrl,
                                                            AppConstants.DefaultSetting.SET_QRCODE_WIDTH,
                                                            AppConstants.DefaultSetting.SET_QRCODE_HEIGHT, null);
                                                    if (imgBitmap != null) {
                                                        iv_gzh.setImageBitmap(imgBitmap);
                                                    }

                                                }


                                                mListV2.clear();
                                                if (orderAdapter2 != null) {
                                                    orderAdapter2.getData().clear();
                                                    orderAdapter2.notifyDataSetChanged();
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        }

                                        @Override
                                        public void onFail(String errorMsg) {
                                            getScaleWeightAccTradeData(mListV2, String.valueOf(payType));
                                            EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));


                                            mListV2.clear();
                                            if (orderAdapter2 != null) {
                                                orderAdapter2.getData().clear();
                                                orderAdapter2.notifyDataSetChanged();
                                            }

                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else if (AppConstants.ScalePay.USER_V3.equals(user)) {
//                              ???V3??????????????????????????????
//                           V3?????? ?????????????????????
                                try {

                                    LogUtils.e("V3 ????????????????????? type :" + payType);
                                    CMD5Bean cmd5Bean = new CMD5Bean();
                                    cmd5Bean.setPayMoney(Double.parseDouble(payMoney));
                                    cmd5Bean.setPayType(payType);
                                    cmd5Bean.setTradeNo(tradeNo);

                                    cmd5Bean.setName(bleSettingCheckOutBean.getName());
                                    cmd5Bean.setUnit(bleSettingCheckOutBean.getUnit());
                                    cmd5Bean.setWeightPcs(bleSettingCheckOutBean.getWeightPcs());
                                    cmd5Bean.setUnitPrice(bleSettingCheckOutBean.getUnitPrice());
                                    cmd5Bean.setTradeTime(bleSettingCheckOutBean.getTradeTime());
                                    cmd5Bean.setItem_num(bleSettingCheckOutBean.getItem_num());


                                    HttpUtils.getInstance().activePay(cmd5Bean, new ArrayList<BleCmd2Bean>(), new HttpUtils.HttpCallBack() {
                                        @Override
                                        public void onSuccess(String jsonData) {

                                            try {

                                                if (TextUtils.isEmpty(jsonData)) {
                                                    //??????

                                                    getScaleWeightConfirmData(bleSettingCheckOutBean, String.valueOf(payType));
                                                    EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                                                    return;
                                                }

                                                //?????? ??????...
                                                JSONObject jsonObject = new JSONObject(jsonData);
                                                int code = jsonObject.getInt("code");
                                                if (code == 0) {
                                                    getScaleWeightConfirmData(bleSettingCheckOutBean, String.valueOf(payType));
                                                    EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                                                    return;
                                                }

                                                boolean hasData = jsonObject.has("data");
                                                if (!hasData) {
                                                    return;
                                                }
                                                Object data1 = jsonObject.get("data");
                                                if (data1 instanceof String) {
                                                    LogUtils.e("data1 IS a String !!!");

                                                } else if (data1 instanceof Object) {
                                                    LogUtils.e("data1 IS a Object !!!");
                                                    // ?????? ??????data....
                                                    ActivePayRes activePayRes = new Gson().fromJson(jsonData, ActivePayRes.class);
                                                    if (activePayRes != null) {
                                                        if (activePayRes.getData() != null) {
                                                            ActivePayRes.DataBean data = activePayRes.getData();
                                                            String code_url = data.getCode_url();
                                                            Bitmap imgBitmap = CodeUtils.createImage(code_url, AppConstants.DefaultSetting.SET_QRCODE_WIDTH,
                                                                    AppConstants.DefaultSetting.SET_QRCODE_HEIGHT, null);
                                                            if (imgBitmap != null) {
                                                                iv_gzh.setImageBitmap(imgBitmap);
                                                            }
                                                        }


                                                    }
                                                } else {
                                                    LogUtils.e("data1 IS a null !!!");

                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        }

                                        @Override
                                        public void onFail(String errorMsg) {
                                            getScaleWeightConfirmData(bleSettingCheckOutBean, String.valueOf(payType));
                                            EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));


                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else if (AppConstants.ScalePay.USER_V4.equals(user)) {
                                // V4?????? ?????????????????????
                                // ?????????????????????????????????????????????


                                try {
                                    LogUtils.e("V4 ??????????????????");
                                    final CMD5Bean cmd5Bean = new CMD5Bean();
                                    cmd5Bean.setPayMoney(bleSettingCheckOutBean.getPayMoney());
                                    cmd5Bean.setPayType(bleSettingCheckOutBean.getPayType());
                                    cmd5Bean.setTradeNo(bleSettingCheckOutBean.getTradeNo());

                                    HttpUtils.getInstance().activePay(cmd5Bean, mListV4, new HttpUtils.HttpCallBack() {
                                        @Override
                                        public void onSuccess(String jsonData) {

                                            if (TextUtils.isEmpty(jsonData)) {
                                                //??????
                                                mListV4.clear();
                                                if (orderAdapter4 != null) {
                                                    orderAdapter4.getData().clear();
                                                    orderAdapter4.notifyDataSetChanged();
                                                }
                                                getScaleWeightAccTradeData(mListV4, String.valueOf(payType));
                                                EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                                                return;
                                            }

                                            try {
                                                JSONObject jsonObject = new JSONObject(jsonData);
                                                int code = (int) jsonObject.get("code");
                                                if (code == 0) {

                                                    getScaleWeightAccTradeData(mListV2, String.valueOf(payType));
                                                    EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));
                                                } else if (code == 1) {

                                                    String codeUrl = Constant.Trace_Code + cmd5Bean.getTradeNo();
                                                    Bitmap imgBitmap = CodeUtils.createImage(codeUrl, AppConstants.DefaultSetting.SET_QRCODE_WIDTH,
                                                            AppConstants.DefaultSetting.SET_QRCODE_HEIGHT, null);
                                                    if (imgBitmap != null) {
                                                        iv_gzh.setImageBitmap(imgBitmap);
                                                    }

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            mListV4.clear();
                                            if (orderAdapter4 != null) {
                                                orderAdapter4.getData().clear();
                                                orderAdapter4.notifyDataSetChanged();
                                            }

                                        }

                                        @Override
                                        public void onFail(String errorMsg) {
                                            LogUtils.e("v4????????????==>");
                                            getScaleWeightAccTradeData(mListV4, String.valueOf(payType));
                                            EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));

                                            mListV4.clear();
                                            if (orderAdapter4 != null) {
                                                orderAdapter4.getData().clear();
                                                orderAdapter4.notifyDataSetChanged();
                                            }

                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                mTotPriceV4 = AppConstants.CommonInt.KEEP_TWO_DECIMAL_DIGITS_ZERO;

                                // V5?????? ?????????????????????
                            } else if (AppConstants.ScalePay.USER_V5.equals(user)) {
                                // ?????????????????????????????????????????????
                                getScaleWeightAccTradeData(mListV5, String.valueOf(payType));
                                EventBusUtils.postSticky(new Event(Constant.OK2, Constant.PageSize));

                                mListV5.clear();
                                mTotPriceV5 = AppConstants.CommonInt.KEEP_TWO_DECIMAL_DIGITS_ZERO;
                                if (orderAdapter5 != null) {
                                    orderAdapter5.getData().clear();
                                    orderAdapter5.notifyDataSetChanged();
                                }
                            }


                        }
                    });


                }
            }, AppConstants.DefaultSetting.SET_QRCODE_DISAPPEAR_SHORT);
            // ???????????????pay_type ?????????????????????.
            SoundPoolPlayUtils.newInstance().playNumber(AppConstants.ScaleVoice.SUCCESS_OTHERS, payMoney);
            //??????????????? ??????


            // ????????????????????????????????? ????????????????????????
//            uploadTradeData();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paySuccFinsh(String payMoney, boolean notGuaDan) {
        mTvCount.setText("0???:");
        mTvTotalPay.setText("0.00");
        mTvWeight.setText("0.000");
        mTvPrice.setText("0.00");
        mTvSubTotal.setText("0.00");

        /**
         *  ????????????
         * */
        mTvXpMomey.setText(StringUtils.isNotEmpty2(payMoney) ? payMoney + "???" : "0.00???");
        /**
         *  ?????? ?????? ???????????????0.00
         * */
        mTvYhMoney.setText("0.00???");

        // ???????????????????????????
//            mBaseView.showTextView(tvPay, mContext.getString(R.string.tv_commercial_info_electronic_scale_rmb) + payMoney, R.string.tv_commercial_info_electronic_scale_top_price);
        mTvActualPay.setText(StringUtils.isNotEmpty2(payMoney) ? "???" + payMoney : "0.00");

        showPaySuccess(notGuaDan);

        // ??????????????????????????????
        // ?????????????????????????????? gif?????????????????????
        GlideApp.with(mActivity).load(R.drawable.pay_finish).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                if (drawable instanceof GifDrawable) {
                    GifDrawable gifDrawable = (GifDrawable) drawable;
                    gifDrawable.setLoopCount(AppConstants.DefaultSetting.SET_PAY_ANIM_LOOP_COUNT);
                    iv_pay_success.setImageDrawable(drawable);
                    gifDrawable.start();
                }
            }
        });

        // 3????????????????????????????????????????????????????????????
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mainThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        hideOrderPay();
                        hidePaySuccess();
                        /**
                         * ??????????????? ?????? ??????UI
                         * ????????????
                         * */
                        hideOrder();


                    }
                });
            }
        }, AppConstants.DefaultSetting.SET_QRCODE_DISAPPEAR_SHORT);
        // ???????????????pay_type ?????????????????????.
        SoundPoolPlayUtils.newInstance().playNumber(AppConstants.ScaleVoice.SUCCESS_OTHERS, payMoney);
        // ????????????????????????????????? ????????????????????????
//        uploadTradeData();
    }


    /**
     * ????????????????????????????????????????????? cmd = 3  V1 V2  ?????????  ????????????????????????
     *
     * @param list
     * @param payType
     */
    private void getScaleWeightAccTradeData(List<BleCmd2Bean> list, String payType) {

        try {
            List<ScaleTrade> scaleTrades;
            for (int i = 0; i < list.size(); i++) {
                BleCmd2Bean bleCmd2Bean = list.get(i);
                // ???????????? ??????0.1????????????
                scaleTrades = listAddScaleTrade(new ArrayList<ScaleTrade>(), bleCmd2Bean,
                        payType, AppConstants.CommonInt.NO_INT);
                // ???????????????????????????
                mScaleDaoUtil.insertScaleTradeDataList(scaleTrades);
                com.blankj.utilcode.util.LogUtils.e("cmd=3:::" + list.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????????????????????????????  cmd = 3  V3  ?????????  ????????????????????????
     *
     * @param bleSettingCheckOutBean
     * @param payType
     */
    private void getScaleWeightConfirmData(BleSettingCheckOutBean bleSettingCheckOutBean, String payType) {
        try {
            // ???????????????????????????
            // ???????????? ??????0.1????????????
            mScaleDaoUtil.insertScaleTradeDataList(listAddScaleTrade(new ArrayList<ScaleTrade>(),
                    bleSettingCheckOutBean,
                    payType));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeHandler() {
        if (mainThreadExecutor != null) {
            mainThreadExecutor.removeHandler();
        }
    }
}
