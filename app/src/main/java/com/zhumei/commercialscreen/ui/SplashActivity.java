package com.zhumei.commercialscreen.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.Guideline;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hikvision.dmb.display.InfoDisplayApi;
import com.hikvision.dmb.util.InfoUtilApi;

import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.base.BaseActivity;
import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.base.Event;
import com.zhumei.baselib.base.MmkvUtils;
import com.zhumei.baselib.bll_merchant.impl.MerchantImpl;
import com.zhumei.baselib.interceptor.LoginNavigationCallbackImpl;
import com.zhumei.baselib.module.localdata.LoginLocalData;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.module.response.MerchantInfo;
import com.zhumei.baselib.service.GuardService;
import com.zhumei.baselib.service.StepService;
import com.zhumei.baselib.utils.ActivityUtil;
import com.zhumei.baselib.utils.HttpUtils;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.hardware.HardwareUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.bll_merchant.ui.MerchantElecActivity;
import com.zhumei.commercialscreen.BuildConfig;
import com.zhumei.commercialscreen.R;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.helper.SplashHelper;
import com.zhumei.commercialscreen.map.MyLocationListener;
import com.zhumei.commercialscreen.presenter.splash.SplashPresenterNew;
import com.zhumei.commercialscreen.presenter.splash.SplashViewNew;

import java.util.concurrent.TimeUnit;


public class SplashActivity extends BaseActivity<SplashPresenterNew> implements SplashViewNew {

    private static final String TAG = "splashActivity";

    //    private LocationClient mLocationClient;
//    private MyLocationListener mLocationListener;
    private int mBoardFlag;

    private ActivityUtil activityUtil = new ActivityUtil();

    /**
     * 模版编号
     */
//    private int mTemplateCode = AppConstants.UsefulSetting.SET_DEFAULT_TEMPLATE;

    private SplashHelper splashHelper = new SplashHelper();
    //    private UiHelper uiHelper = new UiHelper();
    private int templateType = 0; // 模板Type
    //    private boolean netConnect;
    private Activity mActivity = SplashActivity.this;
    //    private SplashPresenterNew splashPresenterNew;
    private TextView tvSplashTitle;
    private ImageView ivSplashLogo;
    private ProgressBar pbSplashBottom;
    private Guideline bottomLine;
    private ImageView ivInfoInternet;
    private ImageView ivInfoServernet;
    private String mSavedMarketNum;
    private String mSavedStallNum;
    private ThreadUtils.SimpleTask<String> jumpTask;


    @Override
    protected int getLayoutId() {

        if (HardwareUtils.getScreenResolution(SplashActivity.this).x > HardwareUtils.getScreenResolution(SplashActivity.this).y) {
            return R.layout.activity_splash_horizontal;
        } else {
            return R.layout.activity_splash_vertical;
        }

    }
//    330781A001

    @Override
    public void initView() {
        tvSplashTitle = (TextView) findViewById(R.id.tv_splash_title);
        ivSplashLogo = (ImageView) findViewById(R.id.iv_splash_logo);
        pbSplashBottom = (ProgressBar) findViewById(R.id.pb_splash_bottom);
        bottomLine = (Guideline) findViewById(R.id.bottom_line);
        ivInfoInternet = (ImageView) findViewById(R.id.iv_info_internet);
        ivInfoServernet = (ImageView) findViewById(R.id.iv_info_servernet);


        LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
        if (entity != null && entity.getLoginRes() != null) {
            LoginRes loginRes = entity.getLoginRes();
            String merchant_id = loginRes.getMerchant_id();
            presenter.getBanner(merchant_id);
            /**
             *  更新设备信息.
             * */
            updateDeviceInfo(loginRes);

            presenter.autoUpdate(loginRes.getMarket_id());

        }


        startAllServcie();


    }


    private void startAllServcie() {
        startService(new Intent(this, StepService.class));
        startService(new Intent(this, GuardService.class));
    }

    private void updateDeviceInfo(LoginRes loginRes) {
        /**
         *  上传设备信息
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

                        LogUtils.E("device", jsonData);

                        HttpUtils.getInstance().deviceOnline();
                    }

                    @Override
                    public void onFail(String errorMsg) {

                        LogUtils.E("updeviceinfomation", errorMsg);

                    }

                });
    }


    @Override
    public void initData() {
        //获取 公网IP
        // 百度地图
        initBaiduMap();
        // 初始化定时开关机
        // 设置海康屏幕的全屏
        setFullScreen();

        // 创建筑美商户屏文件夹
        splashHelper.createZhumeiShpDir();
    }


    @Override
    public void setFullScreen() {
        // 如果海康的jar可以使用
        if (InfoUtilApi.isAvailable()) {
            // 取消顶边栏和底边栏
            InfoDisplayApi.setStatusBarEnable(false);
            InfoDisplayApi.setNavigationBarEnable(false);
            // 添加应用保活和使能com.zhumei.commercialscreen.ui.SplashActivity
            int i = InfoUtilApi.addProtection(getPackageName(), getPackageName(), true, "com.zhumei.commercialscreen.ui.SplashActivity"
                    , "android.intent.action.MAIN");
            InfoUtilApi.enableProtection(getPackageName(), true);

            LogUtils.E(" InfoUtilApi==> ", String.valueOf(i));

        }
        // 默认打开 Wifi
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int wifiState = wifiManager.getWifiState();
        if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
            wifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
        try {


            String longitude = "120.19";
            String latitude = "30.26";
//      杭州市经纬度  经度：120.19 ， 纬度：30.26

            CacheUtils.putString(AppConstants.Cache.LONGITUDE, longitude);
            CacheUtils.putString(AppConstants.Cache.LATITUDE, latitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected SplashPresenterNew createPresenter() {
        return new SplashPresenterNew(this);
    }


    @Override
    protected void receiveMainThreadEvent(Event event) {
    }

    @Override
    protected void backPress() {
        activityUtil.toOtherActivity(SplashActivity.this, BannerActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();


        mSavedMarketNum = CacheUtils.getString(Constant.LAST_MARKET_CODE, "");
        mSavedStallNum = CacheUtils.getString(Constant.LAST_STALL_NAME);

        com.blankj.utilcode.util.LogUtils.d(TAG, mSavedMarketNum + " ---> " + mSavedStallNum);
        presenter.login(mSavedMarketNum, mSavedStallNum);
    }

    @Override
    protected void clearData() {
        try {
            // 将要退出的Activity加到集合
//            MyApplicati.addActivity(SplashActivity.this);
            // 在页面关闭时将定位关闭掉

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void commercialInfoSucc(BaseResponse<MerchantInfo> o) {
        if (o == null) {
            return;
        }
        if (o.getCode() == 0) {
            ToastUtils.showShort(o.getMsg());
            return;
        }

        try {
            LogUtils.E("商户基本信息==>", o.toString());
            if (o.getObj() != null) {


                MerchantInfo merchantInfo = o.getObj();
                MmkvUtils.getInstance().putObject(Constant.MERCHANT_INFO, merchantInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void commInfoError(String msg) {

    }

    @Override
    public void goodsSuccess(BaseResponse<GoodsPriceRes> response) {

        if (response == null) {
            return;
        }
        if (response.getCode() == 0) {
            ToastUtils.showShort(response.getMsg());
            return;
        }
        if (response.getObj() != null) {
            try {
                GoodsPriceRes obj = response.getObj();
                CacheUtils.putEntity(Constant.GOODS_PRICE, obj);
                LogUtils.E("goodsSuccess :", response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void goodsError(String msg) {

    }

    @Override
    public void bannerSuccess(BannerRes response) {

        if (response == null) {
            return;
        }
        LogUtils.E("banner", response.toString());
        CacheUtils.putEntity(Constant.BANNER_DATA, response);


    }

    @Override
    public void bannerError(String msg) {
        LogUtils.e("banner", msg);
    }

    @Override
    public void updateSuccess(AutoUpdateRes response, String marketId) {
        if (response == null) {
            LogUtils.e(TAG, "未获取升级数据 ！");
            return;
        }

        CacheUtils.putEntity(Constant.UPDATE_DATA, response);
        LogUtils.E(TAG, "software update success:" + response.toString());
        splashHelper.dealAutoUpdate(SplashActivity.this, response, marketId);


    }

    @Override
    public void updateError(String msg) {
        LogUtils.e(TAG, msg);
    }

    @Override
    public void loginSuccess(BaseResponse<LoginRes> response, String stall_name) {
        if (response == null) {
            return;
        }
        if (response.getCode() == 0) {
            ToastUtils.showShort(response.getMsg());
            return;
        }

        LoginRes loginRes = response.getObj();

        if (loginRes != null) {
            if (BuildConfig.DEBUG) {
                com.blankj.utilcode.util.LogUtils.e("loginSuccess", loginRes.toString());
            }
            // 登录 成功根据后端返回 跳转.
            LoginLocalData loginLocalData = new LoginLocalData();
            loginLocalData.setLoginRes(loginRes);
            CacheUtils.putEntity(Constant.LOGIN_LOCAL, loginLocalData);
            //保存 登录正确的摊位号
            CacheUtils.putString(Constant.LAST_STALL_NAME, stall_name);

//            /**
//             *  上传设备信息
//             * */
            updateDeviceInfo(loginRes);
            presenter.autoUpdate(loginRes.getMarket_id());


            jumpTask = new ThreadUtils.SimpleTask<String>() {
                @Override
                public String doInBackground() throws Throwable {
                    return "jump";
                }

                @Override
                public void onSuccess(String result) {
                    loginTemplete(loginRes);
                    com.blankj.utilcode.util.LogUtils.d(TAG + result);
                }
            };

            ThreadUtils.executeByIoWithDelay(jumpTask, 2, TimeUnit.SECONDS);

        }
    }


    /***
     *  登录 不同模板
     * */
    private void loginTemplete(LoginRes loginRes) {
        com.blankj.utilcode.util.LogUtils.d("tempId:" + loginRes.getTemplate_id());
        String template_id = loginRes.getTemplate_id();
        String merchant_id = loginRes.getMerchant_id();
        //获取商户 基本信息
        presenter.getCommercialInfo(merchant_id);
        // 获取菜价信息
        presenter.getGoods(merchant_id);

        // 获取缓存 模板类型 跳转不同页面
        switch (template_id) {
            case "0":
                com.blankj.utilcode.util.LogUtils.d("进入模板0000");

                // 默认 秤屏联动版本
//                    activityUtil.toOtherActivity(mActivity, MerchantElecActivity.class);
                ARouter.getInstance().build(RouterManager.MERCHANT)

//                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
                // 修改成Banner 页面测试
//                    MerchantImpl.getInstance().startMerchantActivity(SplashActivity.this);


                break;
            case "1":
                com.blankj.utilcode.util.LogUtils.d("进入模板111111111");
                ARouter.getInstance().build(RouterManager.MERCHANT)
//                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
//                    MerchantImpl.getInstance().startMerchantActivity(SplashActivity.this);

                break;

            case "2":
                // Banner
                activityUtil.toOtherActivity(mActivity, BannerActivity.class);
                break;
            case "4":
                com.blankj.utilcode.util.LogUtils.d("进入模板4");
                ARouter.getInstance().build(RouterManager.MERCHANT2)
//                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
                break;
        }
    }

    @Override
    public void loginError(String msg) {
        com.blankj.utilcode.util.LogUtils.d("loginError", msg);
        // 登录 失败根据缓存跳转
        LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
        if (isNotLogin(entity)) {
            activityUtil.toOtherActivity(SplashActivity.this, BannerActivity.class);
        } else {
            LoginRes loginRes = entity.getLoginRes();
            loginTemplete(loginRes);
        }
    }

    private boolean isNotLogin(LoginLocalData entity) {
        return entity == null || entity.getLoginRes() == null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (ObjectUtils.isNotEmpty(jumpTask)) {
            ThreadUtils.cancel(jumpTask);
            jumpTask = null;
        }
    }


}