package com.zhumei.bll_merchant.ui;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.ZoomOutTranformer;
import com.zhumei.baselib.MyBaseApplication;
import com.zhumei.baselib.adapter.PendingOrderAdapter;
import com.zhumei.baselib.adapter.VegetablePricesAdapter;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.base.BaseActivity;
import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.base.Event;
import com.zhumei.baselib.base.MmkvUtils;
import com.zhumei.baselib.bean.ble_setting.BleCmd2Bean;
import com.zhumei.baselib.bean.commercial_info.ScaleGoods;
import com.zhumei.baselib.bean.commercial_info.ScaleHotkeyCommInfo;
import com.zhumei.baselib.bean.commercial_info.ScaleTicket;
import com.zhumei.baselib.bean.scale.ScaleHotkey;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.dao.ScaleDaoManager;
import com.zhumei.baselib.dao.ScaleDaoUtil;
import com.zhumei.baselib.glide.GlideApp;
import com.zhumei.baselib.helper.PasswordDialogCallBack;
import com.zhumei.baselib.helper.SplashHelper;
import com.zhumei.baselib.helper.UiHelper;
import com.zhumei.baselib.module.localdata.GuidePrice;
import com.zhumei.baselib.module.localdata.LoginLocalData;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.module.response.GTPayRes;
import com.zhumei.baselib.module.response.GeTuiRes;
import com.zhumei.baselib.module.response.GoodsInfoRes;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.baselib.module.response.GuideHotKeyRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.module.response.MerchantInfo;
import com.zhumei.baselib.service.CommitTradeService;
import com.zhumei.baselib.service.KeepAliveService;
import com.zhumei.baselib.utils.ActivityUtil;
import com.zhumei.baselib.utils.CommercialInfoActivityGlideImageLoader;
import com.zhumei.baselib.utils.HttpUtils;
import com.zhumei.baselib.utils.JsonUtils;
import com.zhumei.baselib.utils.ListUtils;
import com.zhumei.baselib.utils.NetUtil;
import com.zhumei.baselib.utils.ResourceManager;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.event.ThreadManager;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.StringUtils;
import com.zhumei.baselib.widget.AutoScrollRecyclerView;
import com.zhumei.baselib.widget.TriangleView;
import com.zhumei.baselib.widget.imageview.ZQImageViewRoundOval;
import com.zhumei.baselib.widget.toast.Tt;
import com.zhumei.bll_merchant.BuildConfig;
import com.zhumei.bll_merchant.R;
import com.zhumei.bll_merchant.helper.MerchantHelper;
import com.zhumei.bll_merchant.presenter.ElecMerchantPresenter;
import com.zhumei.bll_merchant.presenter.ElecMerchantsView;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * ????????? ????????????
 */
@Route(path = RouterManager.MERCHANT)
public class MerchantElecActivity extends BaseActivity<ElecMerchantPresenter> implements ElecMerchantsView {


    private static final int DELAY_TIME = 8000;
    public static MerchantElecActivity instance;

    private VegetablePricesAdapter goodsPriceAdapter;


    private UiHelper uiHelper;
//    private BannerInfoAdapter adapter;


    private static String TAG = "MerchantElecActivity";

    private boolean netConnected;
    private Activity mActivity = MerchantElecActivity.this;
    private SplashHelper splashHelper;
    private PendingOrderAdapter orderAdapter;


    private ScaleDaoUtil mScaleDaoUtil;
    private int mScaleTicketsModifyFlag = AppConstants.CommonInt.NO_INT;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private static String mSavedBleMacAddr;
    private MerchantHelper merchantHelper;
    private ThreadUtils.SimpleTask<Object> mBleReconnTask;
    private Animation mPriceRotAnim;


    private String mTempJsonData = "";
    //    private ConstraintLayout elecRoot;
    private Guideline guideLineTop;
    private Guideline guideLineLeft;
    private View viewBg1;
    private View viewBg2;
    private View ivBg;
    private ZQImageViewRoundOval ivUser;
    private TextView tvUserInfo;
    private ConstraintLayout clStar;
    private ImageView ivStar1;
    private ImageView ivStar2;
    private ImageView ivStar3;
    private ImageView ivStar4;
    private ImageView ivStar5;
    private ImageView ivSerie;
    private TextView tvSerie;
    private ImageView ivTemperature;
    private ImageView ivHealthCode;
    private ImageView ivZan;
    private TextView tvZanCount;
    private View viewBg3;
    private TextView tv1;
    private ImageView ivHot;
    private TextView tv2;
    private ImageView ivHot1;
    private ImageView ivHuiyuan;
    private TextView tv3;
    private ImageView ivFensi;
    private TextView tv4;
    private ImageView iv4;
    private Guideline guideLinePj;
    private HorizontalScrollView hsc;
    private TextView tvMerchantInfo;
    private View viewBg4;
    private AutoScrollRecyclerView rvGoodsPrice;
    private HorizontalScrollView hsc2;
    private TextView tvHuodong;
    private TextView tvHd1;
    private Guideline guideLineRight;
    private View viewRight;
    private ConstraintLayout viewBg6;
    private TextView tvWeightUnit;
    private TextView tvWeight;
    private View line1;
    private TextView tvPriceUnit;
    private TextView tvPrice;
    private View line2;
    private TextView tvRmbUnit;
    private TextView tvSubTotal;
    private View line3;
    private TextView tvTotalUnit;
    private TextView tvCount;
    private TextView tvTotalPay;
    private View viewBannerBg;
    private View viewBannerWindowBg;
    private Banner bannerStall;
    private View viewTotalOrder;
    private TriangleView trPay;
    private ConstraintLayout clPay;
    private TextView tvXp;
    private TextView tvXpMomey;
    private TextView tvYh;
    private TextView tvYhMoney;
    private ImageView ivPayCode;
    private TextView tvPayType;
    private TextView tvActualPay;
    private ConstraintLayout clPaySuccess;
    private ImageView ivPaySuccess;
    private TextView tvT1;
    private ImageView ivGzh;
    private View viewPendingOrder;
    private TriangleView trOrder;
    private RecyclerView rvPendingOrder;
    private ImageView ivZfbCode;
    private ImageView ivWxCode;
    private ImageView ivCommentCode;
    private HorizontalScrollView hsc3;
    private TextView tvNewsInfo;
    private Guideline guideLineBottom;
    private ImageView ivInfoInternet;
    private ImageView ivBleClose;
    private ImageView ivTransferring;
    private static final int MSG_SCAN_DEVICE = 222;

    private BleDevice mBleDevice;
    private BluetoothGatt mGatt;
    private Handler mHandler;
    private int payCount = 0;
    private String mTradeNo = "";
    private ImageView ivRootBg;
    private int freshCount;
    private long successTime = 0;
    // ??????????????????????????????3S
    private long REFRESH_TIME = 3000;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MerchantElecActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_merchant_elec;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected ElecMerchantPresenter createPresenter() {
        return new ElecMerchantPresenter(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {


        try {
            instance = this;
            ivRootBg = findViewById(R.id.iv_root_bg);
            guideLineTop = (Guideline) findViewById(R.id.guide_line_top);
            guideLineLeft = (Guideline) findViewById(R.id.guide_line_left);
            guideLinePj = (Guideline) findViewById(R.id.guide_line_pj);
            tvHd1 = (TextView) findViewById(R.id.tv_hd1);
            guideLineRight = (Guideline) findViewById(R.id.guide_line_right);
            tvSubTotal = (TextView) findViewById(R.id.tv_sub_total);
            tvTotalPay = (TextView) findViewById(R.id.tv_total_pay);
            bannerStall = (Banner) findViewById(R.id.banner_stall);
            tvActualPay = (TextView) findViewById(R.id.tv_actual_pay);
            clPaySuccess = (ConstraintLayout) findViewById(R.id.cl_pay_success);
            ivPaySuccess = (ImageView) findViewById(R.id.iv_pay_success);
            tvT1 = (TextView) findViewById(R.id.tv_t1);
            ivGzh = (ImageView) findViewById(R.id.iv_gzh);

            guideLineBottom = (Guideline) findViewById(R.id.guide_line_bottom);
            ivInfoInternet = (ImageView) findViewById(R.id.iv_info_internet);
            guideLineTop = (Guideline) findViewById(R.id.guide_line_top);
            guideLineLeft = (Guideline) findViewById(R.id.guide_line_left);
            viewBg1 = (View) findViewById(R.id.view_bg1);
            viewBg2 = (View) findViewById(R.id.view_bg2);
            ivBg = (View) findViewById(R.id.iv_bg);
            ivUser = findViewById(R.id.iv_user);
            tvUserInfo = (TextView) findViewById(R.id.tv_user_info);
            clStar = (ConstraintLayout) findViewById(R.id.cl_star);
            ivStar1 = (ImageView) findViewById(R.id.iv_star1);
            ivStar2 = (ImageView) findViewById(R.id.iv_star2);
            ivStar3 = (ImageView) findViewById(R.id.iv_star3);
            ivStar4 = (ImageView) findViewById(R.id.iv_star4);
            ivStar5 = (ImageView) findViewById(R.id.iv_star5);
            ivSerie = (ImageView) findViewById(R.id.iv_serie);
            tvSerie = (TextView) findViewById(R.id.tv_serie);
            ivTemperature = (ImageView) findViewById(R.id.iv_temperature);
            ivHealthCode = (ImageView) findViewById(R.id.iv_health_code);
            ivZan = (ImageView) findViewById(R.id.iv_zan);
            tvZanCount = (TextView) findViewById(R.id.tv_zan_count);
            viewBg3 = (View) findViewById(R.id.view_bg3);
            tv1 = (TextView) findViewById(R.id.tv1);
            ivHot = (ImageView) findViewById(R.id.iv_hot);
            tv2 = (TextView) findViewById(R.id.tv2);
            ivHot1 = (ImageView) findViewById(R.id.iv_hot1);
            ivHuiyuan = (ImageView) findViewById(R.id.iv_huiyuan);
            tv3 = (TextView) findViewById(R.id.tv3);
            ivFensi = (ImageView) findViewById(R.id.iv_fensi);
            tv4 = (TextView) findViewById(R.id.tv4);
            iv4 = (ImageView) findViewById(R.id.iv4);
            guideLinePj = (Guideline) findViewById(R.id.guide_line_pj);
            hsc = (HorizontalScrollView) findViewById(R.id.hsc);
            tvMerchantInfo = (TextView) findViewById(R.id.tv_merchant_info);
            viewBg4 = (View) findViewById(R.id.view_bg4);
            rvGoodsPrice = (AutoScrollRecyclerView) findViewById(R.id.rv_goods_price);
            hsc2 = (HorizontalScrollView) findViewById(R.id.hsc2);
            tvHuodong = (TextView) findViewById(R.id.tv_huodong);
            tvHd1 = (TextView) findViewById(R.id.tv_hd1);
            guideLineRight = (Guideline) findViewById(R.id.guide_line_right);
            viewRight = (View) findViewById(R.id.view_right);
            viewBg6 = (ConstraintLayout) findViewById(R.id.view_bg6);
            tvWeightUnit = (TextView) findViewById(R.id.tv_weight_unit);
            tvWeight = (TextView) findViewById(R.id.tv_weight);
            line1 = (View) findViewById(R.id.line1);
            tvPriceUnit = (TextView) findViewById(R.id.tv_price_unit);
            tvPrice = (TextView) findViewById(R.id.tv_price);
            line2 = (View) findViewById(R.id.line2);
            tvRmbUnit = (TextView) findViewById(R.id.tv_rmb_unit);
            tvSubTotal = (TextView) findViewById(R.id.tv_sub_total);
            line3 = (View) findViewById(R.id.line3);
            tvTotalUnit = (TextView) findViewById(R.id.tv_total_unit);
            tvCount = (TextView) findViewById(R.id.tv_count);
            tvTotalPay = (TextView) findViewById(R.id.tv_total_pay);
            viewBannerBg = (View) findViewById(R.id.view_banner_bg);
            viewBannerWindowBg = (View) findViewById(R.id.view_banner_window_bg);
            viewTotalOrder = (View) findViewById(R.id.view_total_order);
            trPay = (TriangleView) findViewById(R.id.tr_pay);
            clPay = (ConstraintLayout) findViewById(R.id.cl_pay);
            tvXp = (TextView) findViewById(R.id.tv_xp);
            tvXpMomey = (TextView) findViewById(R.id.tv_xp_momey);
            tvYh = (TextView) findViewById(R.id.tv_yh);
            tvYhMoney = (TextView) findViewById(R.id.tv_yh_money);
            ivPayCode = (ImageView) findViewById(R.id.iv_pay_code);
            tvPayType = (TextView) findViewById(R.id.tv_pay_type);
            tvActualPay = (TextView) findViewById(R.id.tv_actual_pay);
            clPaySuccess = (ConstraintLayout) findViewById(R.id.cl_pay_success);
            ivPaySuccess = (ImageView) findViewById(R.id.iv_pay_success);
            tvT1 = (TextView) findViewById(R.id.tv_t1);
            ivGzh = (ImageView) findViewById(R.id.iv_gzh);
            viewPendingOrder = (View) findViewById(R.id.view_pending_order);
            trOrder = (TriangleView) findViewById(R.id.tr_order);
            rvPendingOrder = (RecyclerView) findViewById(R.id.rv_pending_order);
            ivZfbCode = (ImageView) findViewById(R.id.iv_zfb_code);
            ivWxCode = (ImageView) findViewById(R.id.iv_wx_code);
            ivCommentCode = (ImageView) findViewById(R.id.iv_comment_code);
            hsc3 = (HorizontalScrollView) findViewById(R.id.hsc3);
            tvNewsInfo = (TextView) findViewById(R.id.tv_news_info);
            guideLineBottom = (Guideline) findViewById(R.id.guide_line_bottom);
            ivInfoInternet = (ImageView) findViewById(R.id.iv_info_internet);
            ivBleClose = (ImageView) findViewById(R.id.iv_ble_close);
            ivTransferring = (ImageView) findViewById(R.id.iv_transferring);

            /***  ???????????????bg   */
            viewBg1.setBackground(ResourceManager.getDrawableResource(R.drawable.shape_ffff_10_0dp));
//            android:background="@drawable/shape_ffff_10dp"
            viewBg2.setBackground(ResourceManager.getDrawableResource(R.drawable.shape_ffff_10dp));
            ivBg.setBackground(ResourceManager.getDrawableResource(R.drawable.shape_circle_ffff));
//            android:background="@drawable/shape_0000_10dp"

            viewBg3.setBackground(ResourceManager.getDrawableResource(R.drawable.shape_0000_10dp));
//                   android:background="@drawable/shape_0171_10_0dp"
            hsc.setBackground(ResourceManager.getDrawableResource(R.drawable.shape_0171_10_0dp));
//            android:background="@drawable/shape_0000_10dp_right"

            hsc2.setBackground(ResourceManager.getDrawableResource(R.drawable.shape_0000_10dp_right));
//                    android:background="@drawable/shape_0000_10dp"
            viewBg6.setBackground(ResourceManager.getDrawableResource(R.drawable.shape_0000_10dp));

//            ivUser.setRoundRadius(0);
//            ivUser.setType(ZQImageViewRoundOval.TYPE_ROUND);
            ivUser.setType(ZQImageViewRoundOval.TYPE_CIRCLE);

            List<GoodsPriceRes.ContentRes> contentRes = getContentResDefault();

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
            // ????????????Rv
            rvGoodsPrice.setLayoutManager(gridLayoutManager);

            goodsPriceAdapter = new VegetablePricesAdapter(contentRes);
            rvGoodsPrice.setAdapter(goodsPriceAdapter);
            // ??????Rv
            rvPendingOrder.setLayoutManager(new LinearLayoutManager(this));
            orderAdapter = new PendingOrderAdapter(new ArrayList<BleCmd2Bean>());
            rvPendingOrder.setAdapter(orderAdapter);


            ivUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBlePassword();
                }
            });


            // ???????????? Wifi
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            int wifiState = wifiManager.getWifiState();
            if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                wifiManager.setWifiEnabled(true);
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @NotNull
    private List<GoodsPriceRes.ContentRes> getContentResDefault() {
        List<GoodsPriceRes.ContentRes> contentRes = new ArrayList<>();
        contentRes.clear();
        GoodsPriceRes.ContentRes contentRes1 = new GoodsPriceRes.ContentRes();
        contentRes1.setUnit_name("???/kg");
        contentRes1.setPrice("0.00");
        contentRes1.setGoods_name("??????");
        contentRes1.setGoods_id(111);
        contentRes1.setImage("");
        contentRes.add(contentRes1);
        return contentRes;
    }


    private void setBlePassword() {
        MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);

        String blePsd = Constant.BLE_PSD;
        if (merchantInfo != null) {
            blePsd = merchantInfo.getBluetooth();
        }
        uiHelper.showPsdDialog(mActivity, getResources().getString(R.string.psd_dialog_title_ble_setting), blePsd
                , getResources().getString(R.string.dialog_positive_button_text), new PasswordDialogCallBack() {
                    @Override
                    public void onPasswordCorrect() {
                        ARouter.getInstance()
                                .build(RouterManager.BLESET)
//                    .withString(com.yy.baselib.app.Constant.DETAIL_TO_REVIEW, "2")
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                .navigation();
                        LogUtils.e(TAG, "????????????????????????!");
                    }

                    @Override
                    public void onPasswordIncorrect() {
                        Tt.showAnimErrorToast(mActivity, getResources().getString(R.string.psd_error));

                    }
                }, getResources().getString(R.string.dialog_negative_button_text));
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initData() {

        if (uiHelper == null) {
            uiHelper = new UiHelper();
        }
        if (splashHelper == null) {
            splashHelper = new SplashHelper();
        }
        mScaleDaoUtil = new ScaleDaoUtil(mActivity);
        merchantHelper = new MerchantHelper(mActivity, mScaleDaoUtil, uiHelper);


        Intent intent = new Intent(this, CommitTradeService.class);
        startService(intent);


        String time = "20210400112000";
        mScaleDaoUtil.deleteScaleTradeDataByTime(time);

        try {


            rvGoodsPrice.setVisibility(View.VISIBLE);


            NetUtil.isNetPingBd(new NetUtil.NetCallBack() {
                @Override
                public void isConnect(final boolean connect) {
                    if (BuildConfig.DEBUG) {
                        Log.e("ping", String.valueOf(connect));
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            netConnected = connect;
                            ivInfoInternet.setVisibility(connect ? View.GONE : View.VISIBLE);

                        }
                    });

                }
            }, Constant.PING_BAIDU_IP);
            freshData();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void receiveMainThreadEvent(Event event) {
        super.receiveMainThreadEvent(event);
        if (event == null) {
            return;
        }
        switch (event.getCode()) {
            // ????????????
            case AppConstants.EventCode.GETUI_MSG:
                if (event.getData() != null) {
                    getGeTuiMsg((String) event.getData());
                }
                break;
            case AppConstants.EventCode.NET_WORk:

                try {
                    if (BuildConfig.DEBUG) {
                        Log.e("onNetChange", event.getData() + "");
                    }
                    netConnected = (boolean) event.getData();
                    ivInfoInternet.setVisibility(netConnected ? View.GONE : View.VISIBLE);

                    if (netConnected) {
                        netWorkSuccess();
                    } else {
                        netWorkError();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    /**
     * ??????????????????
     *
     * @param geTuiMsg
     */
    private void getGeTuiMsg(String geTuiMsg) {
        try {

            if (TextUtils.isEmpty(geTuiMsg)) {
                LogUtils.e(TAG, "?????????????????????");
                return;
            }

            if (BuildConfig.DEBUG) {
                LogUtils.e(TAG, "geTuiMsg:" + geTuiMsg);
            }
            GeTuiRes geTuiRes = JsonUtils.json2Object(geTuiMsg, GeTuiRes.class);
            String event_type = geTuiRes.getEvent_type();
            LoginLocalData localData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);

            switch (event_type) {
                case Constant.UPDATE_APP:
                    //???????????? ????????????
                    if (localData != null && localData.getLoginRes() != null) {
                        LoginRes loginRes = localData.getLoginRes();
                        presenter.autoUpdate(loginRes.getMarket_id());

                    }

                    break;
                case Constant.FRESH_PAGE:
                    //????????????
                    if (localData != null && localData.getLoginRes() != null) {
                        LoginRes loginRes = localData.getLoginRes();
                        presenter.getCommercialInfo(loginRes.getMerchant_id());
//                        presenter.getGoods(loginRes.getMerchant_id());

                        if (mHandler == null) {
                            mHandler = new Handler();
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                presenter.getBanner(loginRes.getMerchant_id());

                                presenter.getMerchantGoodsInfo(loginRes.getMerchant_id());

                            }
                        }, 3000);

                    }
                    break;
                case Constant.FRESH_PAY_SUCCESS:
                    //?????? ????????????????????? ??????????????????????????????
                    GTPayRes gtPayRes = new Gson().fromJson(geTuiMsg, GTPayRes.class);
                    payCount = 0;

                    if (gtPayRes != null && !TextUtils.isEmpty(gtPayRes.getAmount()) && gtPayRes.getPay_status() == 1) {
                        if (!TextUtils.isEmpty(gtPayRes.getTrade_no())) {
                            String trade_no = gtPayRes.getTrade_no();

                            if (BuildConfig.DEBUG) {
                                LogUtils.e("????????????2s???->");
                            }

                            if (!mTradeNo.equals(trade_no)) {
                                merchantHelper.paySuccFinsh(gtPayRes.getAmount(), true);
                            }
                            mTradeNo = trade_no;

                        }


                    }

                    break;
                case Constant.FRSH_GOODS_PRICE:
                    if (localData != null && localData.getLoginRes() != null) {
                        LoginRes loginRes = localData.getLoginRes();
                        presenter.getGoods(loginRes.getMerchant_id());
                    }
                    break;
                case Constant.FRESH_INFO:
//                    //?????? ??????
                    if (localData != null && localData.getLoginRes() != null) {
                        LoginRes loginRes = localData.getLoginRes();
                        presenter.getCommercialInfo(loginRes.getMerchant_id());
                    }
                    break;
                case Constant.CLEAR_DAO:

                    delCache();
                    break;
                default:

                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delCache() {
        try {
            if (ObjectUtils.isNotEmpty(mScaleDaoUtil)) {
                com.blankj.utilcode.util.LogUtils.d("??????????????????????????????: ");
                mScaleDaoUtil.clearScaleGoodsDbData();
                mScaleDaoUtil.clearScaleKeyDbData();
            }
            presenter.getGoods(presenter.getMerchantId());

            presenter.getBanner(presenter.getMerchantId());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void freshData() {
        LoginLocalData localData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
        if (localData != null && localData.getLoginRes() != null) {
            freshCount++;
            successTime = System.currentTimeMillis();

            LoginRes loginRes = localData.getLoginRes();
            //  ??????????????????.
            presenter.getCommercialInfo(loginRes.getMerchant_id());
            //  ?????? ??????
            presenter.getGoods(loginRes.getMerchant_id());
            //  ?????? ????????????
            updateDeviceInfo(loginRes);

            if (mHandler == null) {
                mHandler = new Handler();
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // ????????? ??????
                    presenter.getMerchantGoodsInfo(loginRes.getMerchant_id());
                    //   ?????? apk
                    presenter.autoUpdate(loginRes.getMarket_id());
                    //   ?????? Banner
                    presenter.getBanner(loginRes.getMerchant_id());
                }
            }, 4000);
        }
        com.blankj.utilcode.util.LogUtils.d("???????????????count " + freshCount);
    }



    private void updateDeviceInfo(LoginRes loginRes) {
        /**
         *  ??????????????????
         * */
        String mSavedClientId = CacheUtils.getString(AppConstants.Cache.CLIENT_ID);
        String mSavedPublicIp = CacheUtils.getString(AppConstants.Cache.PUBLIC_IP);
        String mSavedLongitude = CacheUtils.getString(AppConstants.Cache.LONGITUDE);
        String mSavedLatitude = CacheUtils.getString(AppConstants.Cache.LATITUDE);
        HttpUtils.getInstance().updeviceinfomation(mSavedClientId, mSavedPublicIp,
                mSavedLongitude, mSavedLatitude,
                loginRes, new HttpUtils.HttpCallBack() {
                    @Override
                    public void onSuccess(String jsonData) {
                        if (BuildConfig.DEBUG) {
                            LogUtils.e("device", jsonData);
                        }
                        HttpUtils.getInstance().deviceOnline();
                    }

                    @Override
                    public void onFail(String errorMsg) {
                        if (BuildConfig.DEBUG) {
                            LogUtils.e("updeviceinfomation", errorMsg);
                        }
                    }

                });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void showUiData(final MerchantInfo merchantInfo) {
        if (merchantInfo != null) {


            ListUtils listUtils = new ListUtils();
            uiHelper.loadImageView(ivUser, merchantInfo.getAvator(), R.drawable.default_avator);
            String userInfo = "";
            if (StringUtils.isNotEmpty2(merchantInfo.getMerchant_name()) && StringUtils.isNotEmpty2(merchantInfo.getStall())) {
                userInfo = merchantInfo.getMerchant_name() + " - " + merchantInfo.getStall();
                tvUserInfo.setText(userInfo);
            } else if (StringUtils.isEmpty(merchantInfo.getMerchant_name()) && StringUtils.isNotEmpty2(merchantInfo.getStall())) {
                userInfo = merchantInfo.getStall();
                tvUserInfo.setText(userInfo);
            } else if (StringUtils.isNotEmpty2(merchantInfo.getMerchant_name()) && StringUtils.isEmpty(merchantInfo.getStall())) {
                userInfo = merchantInfo.getMerchant_name();
                tvUserInfo.setText(userInfo);
            } else {
                tvUserInfo.setText("??????");
            }

            if (TextUtils.isEmpty(merchantInfo.getBackground_image())) {
                GlideApp.with(mActivity).load(R.drawable.shanghu_bg_2)
                        .error(R.drawable.shanghu_bg_2)
                        .skipMemoryCache(true)
                        .into(ivRootBg);
            } else {
                String background_image = merchantInfo.getBackground_image();
                if ("1".equals(background_image)) {
                    GlideApp.with(mActivity).load(R.drawable.shanghu_bg_1)
                            .error(R.drawable.shanghu_bg_1)
                            .skipMemoryCache(true)
                            .into(ivRootBg);
                } else if ("2".equals(background_image)) {
                    GlideApp.with(mActivity).load(R.drawable.shanghu_bg_2)
                            .error(R.drawable.shanghu_bg_2)
                            .skipMemoryCache(true)
                            .into(ivRootBg);
                } else {
                    GlideApp.with(mActivity).load(R.drawable.shanghu_bg_2)
                            .error(R.drawable.shanghu_bg_2)
                            .skipMemoryCache(true)
                            .into(ivRootBg);
                }
            }

            jumpTemplete(merchantInfo);


            int star = merchantInfo.getStar();
            int defaultStar = AppConstants.DefaultSetting.SET_RATING;
            uiHelper.showStarNew(defaultStar, star, ivStar1, ivStar2, ivStar3, ivStar4, ivStar5);
            uiHelper.loadImageView(ivSerie, merchantInfo.getRed_black(), R.drawable.icon_hb_d);
            uiHelper.loadImageView(ivTemperature, merchantInfo.getTemperature(), R.drawable.icon_tw_zc);
            uiHelper.loadImageView(ivHealthCode, merchantInfo.getHealth_img(), R.drawable.icon_health_code);


            tvZanCount.setText(String.format("%s", merchantInfo.getThumb()));

            String defaultStr = listUtils.fillEntireLine("???????????????????????????");
            String text = "???????????????????????????";
            if (merchantInfo.getDiscount_content() == null) {
                defaultStr = listUtils.fillEntireLine("???????????????????????????");
            } else {
                if (merchantInfo.getDiscount_content().size() > 0) {

                    String s = "";
                    List<String> discount_content = merchantInfo.getDiscount_content();
                    for (int i = 0; i < discount_content.size(); i++) {
                        s += discount_content.get(i) + "  ";
                    }

                    text = listUtils.fillEntireLine(s);
                } else {
                    defaultStr = listUtils.fillEntireLine("???????????????????????????");

                }

            }


            tvHuodong.setText(TextUtils.isEmpty(text) ? defaultStr : text);
            // ??????????????????
            uiHelper.initMarqueeTextView(tvHuodong, hsc2
                    , getResources().getString(R.string.marquee_speed_slow),
                    getResources().getString(R.string.marquee_speed_slow));

            //???????????? ?????????
            uiHelper.loadImageView(ivWxCode, merchantInfo.getWxpay_img(), R.drawable.default_qrcode);
            // ????????? ?????????
            uiHelper.loadImageView(ivZfbCode, merchantInfo.getAlipay_img(), R.drawable.default_qrcode);
            //?????? ???????????????
            if (!TextUtils.isEmpty(merchantInfo.getEvaluation_qr())) {
                Bitmap bitmap = CodeUtils.createImage(merchantInfo.getEvaluation_qr(),
                        AppConstants.DefaultSetting.SET_QRCODE_WIDTH,
                        AppConstants.DefaultSetting.SET_QRCODE_HEIGHT, null);
                ivCommentCode.setImageBitmap(bitmap);
            }

/************************* ?????????????????? *****************************************/
            String defaultStr2 = listUtils.fillEntireLine("???????????????????????????");
            String merchantNews = "????????????????????????!";
            if (merchantInfo.getNews() == null) {
                defaultStr2 = listUtils.fillEntireLine("???????????????????????????");
            } else {
                List<String> news1 = merchantInfo.getNews();
                LogUtils.e("news ==> ", news1.toString());
                if (merchantInfo.getNews().size() > 0) {

                    String s = "";
                    List<String> news = merchantInfo.getNews();
                    for (int i = 0; i < news.size(); i++) {
                        s += news.get(i) + "   ";
                    }

                    merchantNews = listUtils.fillEntireLine(s);

                } else {
                    defaultStr2 = listUtils.fillEntireLine("???????????????????????????");

                }

            }


            tvNewsInfo.setText(TextUtils.isEmpty(text) ? defaultStr2 : merchantNews);
            // ??????????????????
            uiHelper.initMarqueeTextView(tvNewsInfo, hsc3
                    , MyBaseApplication.getMyApplication().getString(R.string.marquee_speed_slow), getResources().getString(R.string.marquee_speed_slow));


            if (mHandler == null) {
                mHandler = new Handler();
            }


            ThreadManager.executeSingleTask(new Runnable() {
                @Override
                public void run() {


                    if (merchantInfo.getTicket_info() != null && merchantInfo.getTicket_info().size() > 0) {
                        List<ScaleTicket> ticket_info = merchantInfo.getTicket_info();
                        LogUtils.e("?????????");

                        dealWithScaleTickets(ticket_info);
                    } else {
                        LogUtils.e("????????? null");

                        dealWithScaleTickets(null);

                    }
                }
            });


        }

    }


    private void showDefaultBanner() {

        try {
            List<Integer> imageList = new ArrayList<>();
            imageList.clear();

            imageList.add(R.drawable.default_hbanner1);
            imageList.add(R.drawable.default_hbanner2);
            imageList.add(R.drawable.default_hbanner3);
            imageList.add(R.drawable.default_hbanner4);

            bannerStall
                    .setBannerStyle(BannerConfig.NOT_INDICATOR)
                    .setImageLoader(new CommercialInfoActivityGlideImageLoader())
                    .setPageTransformer(true, new ZoomOutTranformer())
                    .isAutoPlay(true)
                    .setDelayTime(DELAY_TIME)
                    .setViewPagerIsScroll(false);
            bannerStall.update(imageList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * ?????? ????????????
     */
    public void netWorkError() {
        try {
            LogUtils.e(TAG, "internet connected fail for icon!");
            ivInfoInternet.setVisibility(View.VISIBLE);
            ivInfoInternet.setImageResource(R.drawable.iv_cha_internet);

            if (AppConstants.DefaultSetting.REFRESH_COUNT0) {
                AppConstants.DefaultSetting.REQUEST_AGAIN_REFRESH = true;
//                mIsPageRefreshed = false;
                LogUtils.e(TAG, "refresh count0!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void netWorkSuccess() {
        try {

            LogUtils.e(TAG, "internet connected success for icon!");
            ivInfoInternet.setVisibility(View.GONE);
            // ??????????????????????????????
//            if (AppConstants.DefaultSetting.REQUEST_AGAIN_REFRESH) {
//                // ???????????????????????????????????? ??????
//                AppConstants.DefaultSetting.REFRESH_COUNT0 = false;
//                LogUtils.e(TAG, "need to request again refresh!");
//                // ????????????????????????
//                CacheUtils.putBoolean(AppConstants.Cache.SOFTWARE_UPDATE, true);
//                // ????????????
//                if (freshCount < 2) {
//                    freshData();
//                }
//
//
//                AppConstants.DefaultSetting.REQUEST_AGAIN_REFRESH = false;
//            } else {
//                LogUtils.e(TAG, "need not to request again refresh!");
//            } 16:48:15.141
            freshCount++;
            long currentTimeMillis = System.currentTimeMillis();
            // ????????????????????????WiFi???????????? ??????  ??????????????? ???????????????3000
            boolean needFresh = (currentTimeMillis - successTime) > REFRESH_TIME;
//         2021-07-27 16:59:10     2021-07-27 16:59:09
            com.blankj.utilcode.util.LogUtils.d(currentTimeMillis + " - " + successTime + " needFresh: " + needFresh + " freshCount : " + freshCount);
            if (freshCount <= 2 && needFresh) {
                com.blankj.utilcode.util.LogUtils.d("??????????????????--> data" + freshCount + " ??????");
                freshData();
            } else {
                LogUtils.e(TAG, "need not to request again refresh!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void backPress() {
//        activityUtil.toOtherActivity(MerchantElecActivity.this, LoginActivity.class);
        ARouter.getInstance()
                .build(RouterManager.LOGIN)
//                    .withString(com.yy.baselib.app.Constant.DETAIL_TO_REVIEW, "2")
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
    }

    @Override
    protected void clearData() {

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void commercialInfoSucc(BaseResponse<MerchantInfo> o) {

        try {
            if (o != null && o.getCode() == 1) {
                if (BuildConfig.DEBUG) {
                    Log.e("??????????????????==>", o.toString());
                }

                MerchantInfo merchantInfo = o.getObj();
                MmkvUtils.getInstance().putObject(Constant.MERCHANT_INFO, merchantInfo);


                showUiData(merchantInfo);
            } else {
                ToastUtils.showShort(o.getMsg());
                MerchantInfo entity = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);
                if (entity != null) {
                    //?????????????????????
                    showUiData(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void commInfoError(String msg) {
        try {
            Log.e("commInfoError==>", msg);
            ToastUtils.showShort(msg);
            MerchantInfo entity = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);

            //????????? ??????????????? ??????
            if (entity != null) {
                //?????????????????????
                showUiData(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void goodsSuccess(BaseResponse<GoodsPriceRes> o) {

        if (o == null) {
            rvGoodsPrice.stop();
            rvGoodsPrice.setVisibility(View.GONE);
            return;
        }
        if (o.getCode() == 0) {
            ToastUtils.showShort(o.getMsg());
            rvGoodsPrice.stop();
            rvGoodsPrice.setVisibility(View.GONE);
            return;
        }
        try {
            List<GoodsPriceRes.ContentRes> contentResList = new ArrayList<>();
            contentResList.clear();
            if (o.getObj() != null) {
                GoodsPriceRes obj = o.getObj();
                CacheUtils.putEntity(Constant.GOODS_PRICE, obj);
                if (obj.getContent() != null && obj.getContent().size() > 0) {
                    rvGoodsPrice.setVisibility(View.VISIBLE);
                    List<GoodsPriceRes.ContentRes> content = obj.getContent();
                    goodsPriceAdapter.setNewData(content);

                    rvGoodsPrice.start();

                } else {

                    rvGoodsPrice.stop();
                    rvGoodsPrice.setVisibility(View.GONE);
                }

                if (BuildConfig.DEBUG) {
                    Log.e("goodsSuccess :", o.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void goodsError(String msg) {

        Log.e("goodsError :", msg);
        try {
            GoodsPriceRes entity = CacheUtils.getEntity(Constant.GOODS_PRICE, GoodsPriceRes.class);
            if (entity == null) {
                rvGoodsPrice.stop();
                rvGoodsPrice.setVisibility(View.GONE);
                LogUtils.E(TAG, "goodsError ????????? ?????????....");
                return;
            }

            if (entity != null && goodsPriceAdapter != null) {
                if (entity.getContent() != null && entity.getContent().size() > 0) {
                    rvGoodsPrice.setVisibility(View.VISIBLE);
                    goodsPriceAdapter.setNewData(entity.getContent());
                    rvGoodsPrice.start();

                } else {


                    rvGoodsPrice.stop();
                    rvGoodsPrice.setVisibility(View.GONE);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateSuccess(AutoUpdateRes response, String market_id) {
        try {
            if (response == null) {
                LogUtils.e(TAG, "????????????????????? ???");
                return;
            }
            CacheUtils.putEntity(Constant.UPDATE_DATA, response);
            if (BuildConfig.DEBUG) {
                LogUtils.e(TAG, "software update success:" + response.toString());
            }
            splashHelper.dealAutoUpdate(MerchantElecActivity.this, response, market_id);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateError(String msg) {
        LogUtils.e(TAG, "updateError ???" + msg);
    }

    @Override
    public void bannerSuccess(BannerRes response) {
        if (response == null) {
            showDefaultBanner();
            return;
        }


        if (BuildConfig.DEBUG) {
            LogUtils.e("banner", response.toString());
        }
        CacheUtils.putEntity(Constant.BANNER_DATA, response);
        // ????????????Banner


        if (response.getRight().size() == 0) {
            // ??????Banner ?????????
            showDefaultBanner();
            return;
        }

        showBannerData(response);


    }

    @Override
    public void bannerError(String msg) {

        LogUtils.e("banner error", msg);
        ToastUtils.showShort(msg);
        BannerRes entity = CacheUtils.getEntity(Constant.BANNER_DATA, BannerRes.class);
        if (entity != null && entity.getRight() != null && entity.getRight().size() > 0) {

            showBannerData(entity);
        } else {
            showDefaultBanner();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void hotSuccess(BaseResponse<GuideHotKeyRes> response) {
        if (response == null) {
            return;
        }
        int code = response.getCode();
        if (code == 0) {
            ToastUtils.showShort(response.getMsg());
            return;
        }
        if (code == 1) {
            GuideHotKeyRes obj = response.getObj();
            if (ObjectUtils.isEmpty(obj)) {
                return;
            }

            freshGuidePrice(obj);

        }
    }


    /**
     * ??????????????????
     */
    private void jumpTemplete(MerchantInfo merchantInfo) {
        if (!TextUtils.isEmpty(merchantInfo.getTemplate_id())) {
            String template_id = merchantInfo.getTemplate_id();
            if (template_id.equals("0")) {
                viewBg6.setVisibility(View.VISIBLE);
                com.blankj.utilcode.util.LogUtils.d("????????????" + template_id);

            } else if (template_id.equals("1")) {
                //??????1 ?????????
                viewBg6.setVisibility(View.GONE);
                ivBleClose.setVisibility(View.GONE);

                stopTimer();
                cancelTask();
            } else if (template_id.equals("2")) {
                ARouter.getInstance()
                        .build(RouterManager.banner)
//                    .withString(com.yy.baselib.app.Constant.DETAIL_TO_REVIEW, "2")
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
            } else if (template_id.equals("4")) {
                ARouter.getInstance()
                        .build(RouterManager.MERCHANT2)
//                    .withString(com.yy.baselib.app.Constant.DETAIL_TO_REVIEW, "2")
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();

            } else {
                ARouter.getInstance()
                        .build(RouterManager.banner)
//                    .withString(com.yy.baselib.app.Constant.DETAIL_TO_REVIEW, "2")
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
            }
        } else {
            ARouter.getInstance()
                    .build(RouterManager.banner)
//                    .withString(com.yy.baselib.app.Constant.DETAIL_TO_REVIEW, "2")
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                    .navigation();

        }
    }


    //????????????   ????????? && ????????????????????? ?????????
    private void freshGuidePrice(GuideHotKeyRes obj) {
        try {
            List<ScaleHotkeyCommInfo> hot_key = obj.getHot_key();
            MmkvUtils.getInstance().putObject(Constant.HOT_KEY, obj);
            com.blankj.utilcode.util.LogUtils.d(hot_key.toString());

            GuidePrice object = MmkvUtils.getInstance().getObject(Constant.GOODSINFORES, GuidePrice.class);
            List<GoodsInfoRes> goodsInfoResList = object.getGoodsInfoResList();
            if (ObjectUtils.isEmpty(goodsInfoResList)) {
                return;
            }
            int size = goodsInfoResList.size();
            List<ScaleGoods> scaleGoods = new ArrayList<>();
            scaleGoods.clear();
            for (int i = 0; i < size; i++) {
                ScaleGoods scaleGoods1 = new ScaleGoods();
                GoodsInfoRes goodsInfoRes = goodsInfoResList.get(i);
                scaleGoods1.setChangePrice(String.format("%s", goodsInfoRes.getChange_price()));
                scaleGoods1.setDiscount(String.format("%s", goodsInfoRes.getDiscount()));
                scaleGoods1.setMemPrice(Double.parseDouble(goodsInfoRes.getMem_price()));
                scaleGoods1.setMinPrice(goodsInfoRes.getMin_price());
                scaleGoods1.setPrice(goodsInfoRes.getPrice());
                scaleGoods1.setName(goodsInfoRes.getName());
                scaleGoods1.setPLU(String.valueOf(goodsInfoRes.getPLU()));
                scaleGoods1.setPluType(String.valueOf(goodsInfoRes.getPlu_type()));
                scaleGoods1.setUnit(goodsInfoRes.getUnit());
                scaleGoods1.setSelfCode(String.valueOf(goodsInfoRes.getSelf_code()));
                scaleGoods.add(scaleGoods1);
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                dealWithScaleHotkeys(hot_key, scaleGoods);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void hotError(String msg) {
        com.blankj.utilcode.util.LogUtils.d("error " + msg);
        GuideHotKeyRes obj = MmkvUtils.getInstance().getObject(Constant.HOT_KEY, GuideHotKeyRes.class);
        if (ObjectUtils.isEmpty(obj)) {
            com.blankj.utilcode.util.LogUtils.d("hotError : ?????????.");
            return;
        }

        freshGuidePrice(obj);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void goodsInfoSuceess(BaseResponse<List<GoodsInfoRes>> response) {
        if (ObjectUtils.isEmpty(response)) {
            com.blankj.utilcode.util.LogUtils.d("res is null");
            return;
        }
        int code = response.getCode();
        if (code == 0) {
            com.blankj.utilcode.util.LogUtils.d("msg : " + response.getMsg());
            return;
        }

        if (ObjectUtils.isEmpty(response.getObj())) {
            com.blankj.utilcode.util.LogUtils.d(" ?????????data ");
            //???????????? ????????????.
            dealWithScaleHotkeys(null, null);
            return;
        }

        List<GoodsInfoRes> obj = response.getObj();
        if (ObjectUtils.isEmpty(obj)) {
            com.blankj.utilcode.util.LogUtils.d(" data is NUll ");
            //???????????? ????????????.
            dealWithScaleHotkeys(null, null);
            return;
        }

        if (obj.isEmpty()) {
            com.blankj.utilcode.util.LogUtils.d(" list is NUll ");
            //???????????? ????????????.
            dealWithScaleHotkeys(null, null);
            return;
        }
        GuidePrice guidePrice = new GuidePrice();
        guidePrice.setGoodsInfoResList(obj);
        MmkvUtils.getInstance().putObject(Constant.GOODSINFORES, guidePrice);

        LoginLocalData localData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
        LoginRes loginRes = localData.getLoginRes();
        presenter.getHot(loginRes.getMerchant_id());


    }

    @Override
    public void goodsInfoError(String msg) {
        try {
            com.blankj.utilcode.util.LogUtils.d("goodsInfoError==>", msg);


            LoginLocalData localData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
            LoginRes loginRes = localData.getLoginRes();
            presenter.getHot(loginRes.getMerchant_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showBannerData(BannerRes response) {

        List<String> rightBanners = new ArrayList<>();
        rightBanners.clear();
        List<String> right = response.getRight();
        rightBanners.addAll(right);
        if (BuildConfig.DEBUG) {
            LogUtils.e("rightBanner", rightBanners.toString());
        }

        bannerStall
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setImageLoader(new CommercialInfoActivityGlideImageLoader())
                .setPageTransformer(true, new ZoomOutTranformer())
                .isAutoPlay(true)
                .setDelayTime(DELAY_TIME)
                .setViewPagerIsScroll(false);
        bannerStall.update(right);

    }

    /**
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            // ???????????????home???
            if (keyCode == KeyEvent.KEYCODE_MUTE || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ??????????????? ??????????????????
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        try {
            if (!Build.MODEL.equals("TT358K512")) {
                if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
                    stopService(new Intent(mActivity, KeepAliveService.class));
//                    PushManager.getInstance().stopService(mActivity);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ???????????????????????????
     *
     * @param commercialInfoScaleHotkeys
     * @param goods
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void dealWithScaleHotkeys(List<ScaleHotkeyCommInfo> commercialInfoScaleHotkeys, List<ScaleGoods> goods) {
        // ????????????????????????????????????
        if (commercialInfoScaleHotkeys == null || commercialInfoScaleHotkeys.isEmpty()) {
            // ????????????????????? ?????????????????????
            LogUtils.e(TAG, "Clear scale key data!");
            mScaleDaoUtil.clearScaleKeyDbData();
            SystemClock.sleep(300);
            merchantHelper.refreshScaleHotkeyDataByBle(mBleDevice, mGatt);
            return;
        }


        // ????????????????????????
        List<ScaleHotkey> scaleHotkeys = mScaleDaoUtil.queryAllScaleHotkeyData(ScaleHotkey.class);
        // ??????????????????????????????
        if (scaleHotkeys != null && !scaleHotkeys.isEmpty()) {
            if (BuildConfig.DEBUG) {
                LogUtils.e(TAG, "commercialInfoScaleHotkeys.toString():" + commercialInfoScaleHotkeys.toString());
                LogUtils.e(TAG, "scaleHotkeys.toString():" + scaleHotkeys.toString());
            }


            // ????????????????????????????????? ????????????
            if (commercialInfoScaleHotkeys.size() == scaleHotkeys.size()) {
//             ??????????????????????????????????????? ???????????????????????????


                if (commercialInfoScaleHotkeys.toString().equals(scaleHotkeys.toString())) {
                    List<com.zhumei.baselib.bean.scale.ScaleGoods> scaleGoods = mScaleDaoUtil.queryAllScaleGoodsData(com.zhumei.baselib.bean.scale.ScaleGoods.class);
                    if (goods.toString().equals(scaleGoods.toString())) {
                        // ???????????? ???????????? ??????.... ??????
                        CacheUtils.putBoolean(AppConstants.Cache.SCALE_KEY_INFAC_MODIFIED, false);
                        LogUtils.e(TAG, "The scale key need not be modified!");
                    } else {
                        com.blankj.utilcode.util.LogUtils.d(TAG, "The scale key need be modified---goods!");
                        // ????????????????????? ?????????????????????
                        mScaleDaoUtil.changeScaleHotkeyDbData(commercialInfoScaleHotkeys);
                        SystemClock.sleep(500);
                        merchantHelper.refreshScaleHotkeyDataByBle(mBleDevice, mGatt);


                        SystemClock.sleep(1000);
                        dealWithScaleGoods(goods);
                    }

                    // ??????????????????????????????????????? ???????????????????????????????????????
                } else {
                    com.blankj.utilcode.util.LogUtils.d(TAG, "The scale key need be modified---contain!");
                    // ????????????????????? ?????????????????????
                    mScaleDaoUtil.changeScaleHotkeyDbData(commercialInfoScaleHotkeys);
                    SystemClock.sleep(500);
                    merchantHelper.refreshScaleHotkeyDataByBle(mBleDevice, mGatt);

                    SystemClock.sleep(1000);
                    dealWithScaleGoods(goods);

                }


//             ????????????????????????????????? ????????????
            } else {
                LogUtils.e(TAG, "The scale key need be modified---size!");
//                mScaleDaoUtil.clearScaleKeyDbData();

                // ????????????????????? ?????????????????????
                mScaleDaoUtil.changeScaleHotkeyDbData(commercialInfoScaleHotkeys);
                SystemClock.sleep(500);

                merchantHelper.refreshScaleHotkeyDataByBle(mBleDevice, mGatt);

                SystemClock.sleep(1000);
                dealWithScaleGoods(goods);

            }
//             ??????????????????????????????????????? ??????????????????
        } else {
            LogUtils.e(TAG, "The scale key need be added!");
            // ????????????????????? ?????????????????????
            mScaleDaoUtil.changeScaleHotkeyDbData(commercialInfoScaleHotkeys);
            SystemClock.sleep(500);

            merchantHelper.refreshScaleHotkeyDataByBle(mBleDevice, mGatt);

            SystemClock.sleep(1000);
            dealWithScaleGoods(goods);
//
        }


    }


    /**
     * ???????????????????????????
     *
     * @param commercialInfoScaleGoods
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void dealWithScaleGoods(List<ScaleGoods> commercialInfoScaleGoods) {
        // ????????????????????????????????????
        if (commercialInfoScaleGoods == null || commercialInfoScaleGoods.isEmpty()) {
            // ????????????????????? ?????????????????????
            LogUtils.e(TAG, "Clear scale goods data!");
            mScaleDaoUtil.clearScaleGoodsDbData();
            return;
        }
        // ????????????????????????
        List<com.zhumei.baselib.bean.scale.ScaleGoods> scaleGoods = mScaleDaoUtil.queryAllScaleGoodsData(com.zhumei.baselib.bean.scale.ScaleGoods.class);
        // ??????????????????????????????
        if (scaleGoods == null || scaleGoods.isEmpty()) {
            // ????????????????????? ?????????????????????
            LogUtils.e(TAG, "The scale goods need be added!");
            mScaleDaoUtil.changeScaleGoodsDbData(commercialInfoScaleGoods);
            SystemClock.sleep(300);
            merchantHelper.refreshScaleGoodsDataByBle(mBleDevice, mGatt);
            return;
        }

//        // ??????????????????????????????????????? ??????????????????
        if (BuildConfig.DEBUG) {
            LogUtils.e(TAG, "commercialInfoScaleGoods.toString():" + commercialInfoScaleGoods.toString());
            LogUtils.e(TAG, "scaleGoods.toString():" + scaleGoods.toString());
        }
        // ????????????????????????????????? ????????????
        if (commercialInfoScaleGoods.size() == scaleGoods.size()) {
            if (commercialInfoScaleGoods.toString().equals(scaleGoods.toString())) {
                // ???????????? ???????????? ??????.... ??????
                CacheUtils.putBoolean(AppConstants.Cache.SCALE_GOODS_INFAC_MODIFIED, false);
                LogUtils.e(TAG, "The scale key need not be modified!");
            } else {
                mScaleDaoUtil.changeScaleGoodsDbData(commercialInfoScaleGoods);
                SystemClock.sleep(600);
                merchantHelper.refreshScaleGoodsDataByBle(mBleDevice, mGatt);
            }
        } else {
            mScaleDaoUtil.changeScaleGoodsDbData(commercialInfoScaleGoods);
            SystemClock.sleep(600);
            merchantHelper.refreshScaleGoodsDataByBle(mBleDevice, mGatt);
        }


    }

    /**
     * ???????????????????????????
     *
     * @param commercialInfoScaleTickets
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void dealWithScaleTickets(List<ScaleTicket> commercialInfoScaleTickets) {
        try {
            // ????????????????????????????????????
            if (commercialInfoScaleTickets == null || commercialInfoScaleTickets.isEmpty()) {
                // ??????????????????????????????????????????
                LogUtils.e(TAG, "Clear scale ticket data!");
                mScaleDaoUtil.clearScaleTicketDbData();
                mScaleTicketsModifyFlag = AppConstants.CommonInt.OTHER_INT;
                SystemClock.sleep(300);
                merchantHelper.refreshScaleTicketDataByBle(mBleDevice, mGatt);
                return;
            }
            // ????????????????????????
            List<com.zhumei.baselib.bean.scale.ScaleTicket> scaleTickets = mScaleDaoUtil.queryAllScaleTicketData(com.zhumei.baselib.bean.scale.ScaleTicket.class);
            // ??????????????????????????????????????? ??????????????????????????????
            if (scaleTickets == null && scaleTickets.isEmpty()) {
                // ??????????????????????????????????????????
                LogUtils.e(TAG, "The scale ticket need be added!");
                mScaleDaoUtil.changeScaleTicketDbData(commercialInfoScaleTickets);
                mScaleTicketsModifyFlag = AppConstants.CommonInt.YES_INT;
                SystemClock.sleep(600);
                merchantHelper.refreshScaleTicketDataByBle(mBleDevice, mGatt);
                return;
            }
            // ???????????????????????????
            if (BuildConfig.DEBUG) {
                LogUtils.e(TAG, "commercialInfoScaleTickets.toString():" + commercialInfoScaleTickets.toString());
                LogUtils.e(TAG, "scaleTickets.toString():" + scaleTickets.toString());
            }
            // ????????????????????????????????? ????????????
//
            if (commercialInfoScaleTickets.size() == scaleTickets.size() && commercialInfoScaleTickets.toString().equals(scaleTickets.toString())) {
                CacheUtils.putBoolean(AppConstants.Cache.SCALE_TICKET_INFAC_MODIFIED, false);
                LogUtils.e(TAG, "The scale ticket need not be modified!");
                mScaleTicketsModifyFlag = AppConstants.CommonInt.NO_INT;


            } else {
                // ????????????????????????????????? ????????????
                // ??????????????????????????????????????????
                LogUtils.e(TAG, "The scale ticket need be modified---size!");
                mScaleDaoUtil.changeScaleTicketDbData(commercialInfoScaleTickets);
                mScaleTicketsModifyFlag = AppConstants.CommonInt.YES_INT;
                SystemClock.sleep(300);
                merchantHelper.refreshScaleTicketDataByBle(mBleDevice, mGatt);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        // ????????????
        freshCount = 0;
        MyBaseApplication.getMyApplication().setBleNormalExit(true);
        BleManager.getInstance().disconnectAllDevice();
//        BleManager.getInstance().destroy();
        LogUtils.e("onpauase==>", "1111111111111");
        /***
         * ????????? ???????????????HOME???
         * */
        finishActivity();
        LogUtils.e("onpauase==>", "1111111111111");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rvGoodsPrice != null) {
            rvGoodsPrice.stop();
        }

        freshCount = 0;

        stopTimer();
        cancelTask();
        if (merchantHelper != null) {
            merchantHelper.removeHandler();
        }

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        // ???????????????
        new ScaleDaoManager().getInstance().closeDataBase();
        stopService(new Intent(this, CommitTradeService.class));


    }

    @Override
    protected void onStart() {
        try {
            // ????????????????????? ???????????? ???30??????????????????????????????
            startTimer();

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }


    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimer != null && mTimerTask != null) {
            mTimerTask.cancel();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    // ????????????????????? ???????????? ???20??????????????????????????????
                    enableBleToConnect();
                }
            };
        }

        if (mTimer != null) {
            mTimer.schedule(mTimerTask, AppConstants.DefaultSetting.SET_BLUETOOTH_CONNECT_CYCLE);
        }

    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }


    public void enableBleToConnect() {
        try {
            mSavedBleMacAddr = CacheUtils.getString(AppConstants.Cache.BLE_MAC_ADDR, AppConstants.DefaultSetting.SET_MAC);

            if (!BleManager.getInstance().isSupportBle()) {
                LogUtils.e(TAG, "Bluetooth device does not support Bluetooth!");
                return;
            }
            LogUtils.e(TAG, "Device support Bluetooth!");
            if (!BleManager.getInstance().isBlueEnable()) {
                LogUtils.e(TAG, "Bluetooth has not been opened!");
                // ????????????   ????????????????????????.  ??????Dialog ?????????????????????.
                //???????????????????????????

                return;
            }

            LogUtils.e(TAG, "Bluetooth has been opened!");
            Log.e("saveAddr==>", mSavedBleMacAddr);
            if (TextUtils.isEmpty(mSavedBleMacAddr) || AppConstants.CommonStr.NULL_STR.equals(mSavedBleMacAddr) || mSavedBleMacAddr.equals(AppConstants.DefaultSetting.SET_MAC)) {
                // ??????????????????00???02:00:00 ??????
                // ?????? ????????????mac ???????????????
                // ????????? ???????????????????????? ??????????????? ??? ????????????????????? ??????....
                LogUtils.e("???????????????????????????", mSavedBleMacAddr);
                return;
            }
            // ???????????? ??????????????????
            if (BleManager.getInstance().isConnected(mSavedBleMacAddr)) {
                LogUtils.e(TAG, "Bluetooth has been connected!");
                return;
            }
            //     ?????? BleDevice ??????
            mPriceRotAnim = AnimationUtils.loadAnimation(mActivity, R.anim.ble_searching_rotate);
            mPriceRotAnim.setInterpolator(new LinearInterpolator());

            // ????????????????????????

            connectBleByMac();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void bleReconnect() {
        try {

            Log.e("bleReconnect==>", "222222222");

            mBleReconnTask = new ThreadUtils.SimpleTask<Object>() {
                @Override
                public Object doInBackground() throws Throwable {
                    connectBleByMac();
                    return null;
                }

                @Override
                public void onSuccess(Object result) {
                }
            };
            ThreadUtils.executeByCachedWithDelay(mBleReconnTask, AppConstants.DefaultSetting.SET_BLUETOOTH_RECONNECT_CYCLE, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectBleByMac() {
        if (merchantHelper == null) {
            merchantHelper = new MerchantHelper(mActivity, mScaleDaoUtil, uiHelper);
        }
        merchantHelper.connectBleByMac(new MerchantHelper.BlueToothCallBack() {
            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                try {
                    ivBleClose.setVisibility(View.VISIBLE);
                    boolean bleNormalExit = MyBaseApplication.getMyApplication().isBleNormalExit();
                    LogUtils.e("????????????", bleNormalExit + "");

                    mSavedBleMacAddr = CacheUtils.getString(AppConstants.Cache.BLE_MAC_ADDR, AppConstants.DefaultSetting.SET_MAC);
                    boolean connected = BleManager.getInstance().isConnected(mSavedBleMacAddr);
                    LogUtils.e("????????????", mSavedBleMacAddr + " ==>" + connected);

                    Log.e("onConnectFail", "????????????....");
                    bleReconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                try {

                    ivBleClose.setVisibility(View.INVISIBLE);
                    cancelTask();
                    // ????????????????????????bleDevice??????

                    mBleDevice = bleDevice;
                    mGatt = gatt;

                    // ?????????????????????
                    if (!CacheUtils.getBoolean(AppConstants.Cache.NOT_FIRST_CONN_BLE)) {
                        ThreadManager.executeSingleTask(() -> {
                            // ????????????????????????????????????????????????????????????
                            SystemClock.sleep(3000);
                            runOnUiThread(() -> {
                                ivTransferring.startAnimation(mPriceRotAnim);
                                ivTransferring.setVisibility(View.VISIBLE);
                            });

                            merchantHelper.refreshScaleTimeDataByBle(bleDevice, gatt);

                            SystemClock.sleep(1000);

                            merchantHelper.refreshScaleTicketDataByBle(bleDevice, gatt);
                            // {"cmd":d,"frame_no":0}

                            SystemClock.sleep(2 * 1000);

                            merchantHelper.refreshScaleHotkeyDataByBle(bleDevice, gatt);

                            SystemClock.sleep(2 * 1000);

                            merchantHelper.refreshScaleGoodsDataByBle(bleDevice, gatt);


                            runOnUiThread(() -> {
                                ivTransferring.clearAnimation();
                                ivTransferring.setVisibility(View.INVISIBLE);
                            });
                        });
                        CacheUtils.putBoolean(AppConstants.Cache.NOT_FIRST_CONN_BLE, true);
                    } else {
                        ThreadManager.executeSingleTask(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                            @Override
                            public void run() {
                                // ??????????????????????????????????????????
                                SystemClock.sleep(3000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivTransferring.startAnimation(mPriceRotAnim);
                                        ivTransferring.setVisibility(View.VISIBLE);
                                    }
                                });


                                if (0 == AppConstants.UsefulSetting.SET_DS_DIFF) {
                                    merchantHelper.refreshScaleTimeDataByBle(bleDevice, gatt);

                                    SystemClock.sleep(1000);

                                    // ???????????????????????????
                                    merchantHelper.refreshScaleTicketDataByBle(bleDevice, gatt);
//
                                    SystemClock.sleep(2 * 1000);

                                    // ???????????????????????????
                                    if (CacheUtils.getBoolean(AppConstants.Cache.SCALE_KEY_INFAC_MODIFIED)) {
                                        merchantHelper.refreshScaleHotkeyDataByBle(bleDevice, gatt);
                                        SystemClock.sleep(2 * 1000);
                                    }


                                    // ???????????????????????????
                                    if (CacheUtils.getBoolean(AppConstants.Cache.SCALE_GOODS_INFAC_MODIFIED)) {
                                        merchantHelper.refreshScaleGoodsDataByBle(bleDevice, gatt);
                                    }
//
                                } else {

                                    // ???????????????????????????
                                    merchantHelper.refreshScaleTicketDataByBle(bleDevice, gatt);
                                    SystemClock.sleep(2 * 1000);

                                    // ???????????????????????????
                                    if (CacheUtils.getBoolean(AppConstants.Cache.SCALE_KEY_INFAC_MODIFIED)) {
                                        merchantHelper.refreshScaleHotkeyDataByBle(bleDevice, gatt);
                                        SystemClock.sleep(2 * 1000);
                                    }

                                    if (CacheUtils.getBoolean(AppConstants.Cache.SCALE_GOODS_INFAC_MODIFIED)) {
                                        // ???????????????????????????
                                        merchantHelper.refreshScaleGoodsDataByBle(bleDevice, gatt);

                                    }


                                }


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivTransferring.clearAnimation();
                                        ivTransferring.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        });
                    }

                    UUID serviceUUID = null;
                    UUID notifyUUID = null;
                    // ????????????????????????????????????????????????  ?????????

                    serviceUUID = AppConstants.ZSLF.SERVICE_UUID;
                    notifyUUID = AppConstants.ZSLF.NOTIFY_UUID;

                    merchantHelper.getDataFromBle(serviceUUID, notifyUUID, gatt, bleDevice, new MerchantHelper.BlueDataCallBack() {
                        @Override
                        public void dealWithBlueData(byte[] data) {

                            try {
                                // Hex???Str
//                               LogUtils.e(TAG , "Hex???Str:" + HexUtils.bytesToHex(data));
                                String jsonData = new String(data, AppConstants.DefaultSetting.CHARSET_NAME);
//                                LogUtils.e("dealWithBlueData==>", jsonData);
                                if (0 == AppConstants.UsefulSetting.LIMIT_BYTE) {
                                    if (!jsonData.contains("\r\n")) {
                                        mTempJsonData = mTempJsonData + jsonData;
                                    } else {
                                        try {
                                            mTempJsonData = mTempJsonData + jsonData.split("\r\n")[0] + "\r\n";
                                            // ?????? ???????????????????????? ???????????? ????????????
                                            merchantHelper.dealWithJson(mTempJsonData);
                                            mTempJsonData = jsonData.split("\r\n")[1];
                                        } catch (Exception e) {
                                            mTempJsonData = "";
                                            e.printStackTrace();
                                        }


                                    }
                                } else {
                                    merchantHelper.dealWithJson(jsonData);
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

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                try {
                    ivBleClose.setVisibility(View.VISIBLE);
                    mSavedBleMacAddr = CacheUtils.getString(AppConstants.Cache.BLE_MAC_ADDR, AppConstants.DefaultSetting.SET_MAC);
                    boolean connected = BleManager.getInstance().isConnected(mSavedBleMacAddr);
                    LogUtils.e("????????????", mSavedBleMacAddr + " ==>" + connected);
                    boolean bleNormalExit = MyBaseApplication.getMyApplication().isBleNormalExit();
                    LogUtils.e("onDisConnected", bleNormalExit + "");
                    bleReconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cancelTask() {
        if (null != mBleReconnTask) {
            ThreadUtils.cancel(mBleReconnTask);
            mBleReconnTask = null;
        }
    }


//    public void openBle() {
//        try {
//            // ??????????????????
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, AppConstants.EventCode.OPEN_BLUETOOTH_REQUEST_CODE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}