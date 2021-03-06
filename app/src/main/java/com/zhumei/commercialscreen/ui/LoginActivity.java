package com.zhumei.commercialscreen.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.base.BaseActivity;
import com.zhumei.baselib.base.BaseResponse;
import com.zhumei.baselib.base.Event;
import com.zhumei.baselib.base.MmkvUtils;
import com.zhumei.baselib.bll_merchant.impl.MerchantImpl;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.dao.ScaleDaoManager;
import com.zhumei.baselib.module.localdata.LoginLocalData;
import com.zhumei.baselib.module.localdata.MarketCodeLocalData;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.GeTuiRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.module.response.MarketCodeRes;
import com.zhumei.baselib.module.response.MerchantInfo;
import com.zhumei.baselib.utils.ActivityUtil;
import com.zhumei.baselib.utils.HttpUtils;
import com.zhumei.baselib.utils.JsonUtils;
import com.zhumei.baselib.utils.MyClickUtils;
import com.zhumei.baselib.utils.NetUtil;
import com.zhumei.baselib.utils.ParamsUtils;
import com.zhumei.baselib.utils.ResourceManager;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.event.ThreadManager;
import com.zhumei.baselib.utils.useing.hardware.HardwareUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.utils.useing.software.SoftwareUtils;
import com.zhumei.baselib.widget.percent_support_extends.PercentRelativeLayout;
import com.zhumei.baselib.widget.toast.Tt;
import com.zhumei.bll_merchant.ui.MerchantElecActivity;
import com.zhumei.commercialscreen.BuildConfig;
import com.zhumei.commercialscreen.R;
import com.zhumei.commercialscreen.help.LoginHelper;
import com.zhumei.commercialscreen.help.PasswordDialogCallBack;
import com.zhumei.baselib.helper.SplashHelper;
import com.zhumei.commercialscreen.help.UiHelper;
import com.zhumei.commercialscreen.presenter.login.LoginPresenterNew;
import com.zhumei.commercialscreen.presenter.login.LoginViewNew;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

@Route(path = RouterManager.LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenterNew>
        implements LoginViewNew, EasyPermissions.PermissionCallbacks {


    private String TAG = "LoginActivity";
    private LoginHelper loginHelper = new LoginHelper();
    private UiHelper uiHelper = new UiHelper();
    private ActivityUtil activityUtil = new ActivityUtil();

    /**
     * ???????????????
     */
    private String[] requestPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE

    };
    /**
     * progressBar ?????????????????????
     */

    /**
     * ????????????????????????????????????
     */
    private boolean mInternetConnected = true;
    private boolean mInternetDisConnected = true;
    /**
     * ????????????????????????
     */
    private PopupWindow mPopupWindow;
    private ListView mLvPopWindow;
    /**
     * ??????????????????
     */
    private String mSavedMarketNum;
    private String mSavedStallNum;
    private String mSavedMerchantId;
    private String mSavedClientId;
    private String mSavedPublicIp;
    private String mSavedMarketId;
    private String mSavedLongitude;
    private String mSavedLatitude;
    private String mSavedDeviceId;
    private boolean netConnect;
    private Activity mActivity = LoginActivity.this;
    //    private SplashHelper splashHelper = new SplashHelper();
    private Timer mTimer;
    private TimerTask mTimerTask;
    private ImageView ivLoginTopLogo;
    private TextView tvLoginTopTitle;
    private TextView tvLoginTopAppVersion;
    private PercentRelativeLayout llLoginBottom;
    private EditText etLoginBottomMarketNumber;
    private ImageButton ibLoginBottomPulldown;
    private EditText etLoginBottomStallNumber;
    private Button btnLogin;
    private Button btnLoginOut;
    private TextView tvLoginDeviceId;
    private TextView tvLoginWiredMac;
    private TextView tvLoginWifiMac;
    private ImageView ivLoginQrcode;
    private android.widget.ProgressBar progressbarLoginCenter;
    private ImageView ivInfoInternet;
    private ImageView ivInfoServernet;


    @Override
    protected int getLayoutId() {
        if (HardwareUtils.getScreenResolution(LoginActivity.this).x >
                HardwareUtils.getScreenResolution(LoginActivity.this).y) {
            return R.layout.activity_login_horizontal;
        } else {
            return R.layout.activity_login_vertical;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //?????????????????????EasyPermission?????????
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //??????????????????
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        //Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();

        LogUtils.e("onPermissionsGranted", perms.toString());

    }

    //?????????????????????
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
        LogUtils.E("onPermissionsDenied", perms.toString());
    }


    @Override
    public void initView() {
        ivLoginTopLogo = (ImageView) findViewById(R.id.iv_login_top_logo);
        tvLoginTopTitle = (TextView) findViewById(R.id.tv_login_top_title);
        tvLoginTopAppVersion = (TextView) findViewById(R.id.tv_login_top_app_version);
        llLoginBottom = (PercentRelativeLayout) findViewById(R.id.ll_login_bottom);
        etLoginBottomMarketNumber = (EditText) findViewById(R.id.et_login_bottom_market_number);
        ibLoginBottomPulldown = (ImageButton) findViewById(R.id.ib_login_bottom_pulldown);
        etLoginBottomStallNumber = (EditText) findViewById(R.id.et_login_bottom_stall_number);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLoginOut = (Button) findViewById(R.id.btn_login_out);
        tvLoginDeviceId = (TextView) findViewById(R.id.tv_login_device_id);
        tvLoginWiredMac = (TextView) findViewById(R.id.tv_login_wired_mac);
        tvLoginWifiMac = (TextView) findViewById(R.id.tv_login_wifi_mac);
        ivLoginQrcode = (ImageView) findViewById(R.id.iv_login_qrcode);
        progressbarLoginCenter = (ProgressBar) findViewById(R.id.progressbar_login_center);
        ivInfoInternet = (ImageView) findViewById(R.id.iv_info_internet);
        ivInfoServernet = (ImageView) findViewById(R.id.iv_info_servernet);

        //???????????????????????????
        NetUtil.isNetPingBd(new NetUtil.NetCallBack() {
            @Override
            public void isConnect(boolean connect) {
                LogUtils.e("ping bd " + connect);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        netConnect = connect;

                        if (netConnect) {

                            ivInfoInternet.setVisibility(View.GONE);

                            onNetConnectSuccess();
                        } else {
                            ivInfoInternet.setVisibility(View.VISIBLE);
                            onNetConnectError();
                        }

                    }
                });

            }
        },Constant.PING_BAIDU_IP);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ??????????????????
                if (MyClickUtils.isDoubleClick()) {
                    ToastUtils.showShort("????????????");
                    return;
                }
                login();
            }
        });


        btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyClickUtils.isDoubleClick()) {
                    ToastUtils.showShort("????????????");
                    return;
                }
                loginOut();
            }
        });
        presenter.getMarketCode();
        LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);

        if (entity != null && entity.getLoginRes() != null) {
            LoginRes loginRes = entity.getLoginRes();
            presenter.autoUpdate(loginRes.getMarket_id());
        }

        // ???????????? Wifi
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int wifiState = wifiManager.getWifiState();
        if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
            wifiManager.setWifiEnabled(true);
        }

    }

    @Override
    protected void receiveMainThreadEvent(Event event) {
        super.receiveMainThreadEvent(event);
        switch (event.getCode()) {
            case AppConstants.EventCode.NET_WORk:
                LogUtils.e("GETUI_MSG", event.getData().toString());
                //??????????????????????????????
                netConnect = (boolean) event.getData();
                LogUtils.e(TAG, netConnect + "");
                //            ToastMessage(R.mipmap.toast_succee, Toast.LENGTH_SHORT, getString(R.string.net_success));
                //??????????????????????????????
                LogUtils.e("onNetChange ==>", netConnect + "");
                if (netConnect) {

//            ToastMessage(R.mipmap.toast_succee, Toast.LENGTH_SHORT, getString(R.string.net_success));
                    Tt.showAnimSuccessToast(mActivity, getString(R.string.net_success));
                    ivInfoInternet.setVisibility(View.GONE);
//            mIvInfoServernet.setVisibility(View.GONE);
                    onNetConnectSuccess();

                } else {
//            ToastMessage(R.mipmap.toast_x, Toast.LENGTH_SHORT, getString(R.string.net_exception));
                    Tt.showAnimErrorToast(mActivity, getString(R.string.net_exception));

                    ivInfoInternet.setVisibility(View.VISIBLE);
//            mIvInfoServernet.setVisibility(View.VISIBLE);
                    onNetConnectError();

                }
                break;
            // ????????????
            case AppConstants.EventCode.GETUI_MSG:
                getGeTuiMsg((String) event.getData());
                break;

        }

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

            LogUtils.e(TAG, "geTuiMsg:" + geTuiMsg);
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

                default:

                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onNetConnectError() {

        try {
            LogUtils.e(TAG, "internet connected fail for icon!");
            ivInfoInternet.setVisibility(View.VISIBLE);
            ivInfoInternet.setImageResource(R.drawable.iv_cha_internet);

            if (mInternetDisConnected) {
                // ????????????????????????
                uiHelper.showDialog(LoginActivity.this, getResources().getString(R.string.psd_dialog_title_net), getResources().getString(R.string.dialog_msg_net)
                        , getResources().getString(R.string.dialog_positive_button_text)
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // ?????????????????????
//                                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                        }
                        , getResources().getString(R.string.dialog_negative_button_text)
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                mInternetDisConnected = false;
                mInternetConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onNetConnectSuccess() {
        try {
            LogUtils.e(TAG, "internet connected success for icon!");
            ivInfoInternet.setVisibility(View.GONE);
            // ????????????????????????????????? ????????????????????????id????????????
            if (mInternetConnected) {
                uiHelper.hideDialog();
                mInternetDisConnected = true;
                mInternetConnected = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMainThreadEventPost(Event event) {
        super.onMainThreadEventPost(event);
    }

    @Override
    public void initData() {


        //?????? ??????IP
        HttpUtils.getPublicIp();
        initPopupWindow();
        getCacheData();


        getPermission();

        // ????????????
        showData();
        // ?????????????????????????????????(????????????????????????)
        refreshPage();
        // 15?????????????????????commercialInfo??????
        autoToLoading();
    }

    //????????????
    private void getPermission() {
        ThreadManager.executeSingleTask(() -> {
            if (EasyPermissions.hasPermissions(mActivity, requestPermissions)) {
                //??????????????????
                LogUtils.e("getPermission", "????????????????????????");
            } else {
                //???????????????????????????????????????
                EasyPermissions.requestPermissions(mActivity, "??????????????????????????????",
                        1, requestPermissions);
            }

        });

    }


    /**
     * ?????????PopWindow??????????????????ListView
     */
    @SuppressLint("InflateParams")
    private void initPopupWindow() {
        try {
            View view;
            if (HardwareUtils.getScreenResolution(LoginActivity.this).x > HardwareUtils.getScreenResolution(LoginActivity.this).y) {
                view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.custome_droped_listview_horizontal, null);
            } else {
                view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.custome_droped_listview_vertical, null);
            }
            mLvPopWindow = view.findViewById(R.id.lv_login_bottom_pulldown);
            // ???????????????
            mLvPopWindow.setVerticalScrollBarEnabled(false);
            // ??????PopWindow
            mPopupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setOutsideTouchable(true);
            // ?????????????????????????????????????????????????????????
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            mPopupWindow.setFocusable(true);
            // ??????ImageButton??????????????????
            loginHelper.imageButtonSetOnFocusChangeListener(ibLoginBottomPulldown, R.drawable.ib_login_bottom_pulldown_focused, R.drawable.ib_login_bottom_pulldown_normal);
            // ??????ImageButton???????????? ??????PopupWindow
            loginHelper.imageButtonSetOnClickListener(ibLoginBottomPulldown, mPopupWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????CacheUtils??????????????????
     */
    private void getCacheData() {


//        mSavedMarketNum = mmkvUtils.getString(AppConstants.Cache.MARKET_NUM);
        mSavedMarketNum = CacheUtils.getString(Constant.LAST_MARKET_CODE, "");
//        mSavedStallNum = mmkvUtils.getString(AppConstants.Cache.STALL_NUM);
        mSavedStallNum = CacheUtils.getString(Constant.LAST_STALL_NAME);


        mSavedMerchantId = CacheUtils.getString(AppConstants.Cache.MERCHANT_ID);
        mSavedDeviceId = CacheUtils.getString(AppConstants.Cache.DEVICE_ID);


        mSavedClientId = CacheUtils.getString(AppConstants.Cache.CLIENT_ID);
        mSavedPublicIp = CacheUtils.getString(AppConstants.Cache.PUBLIC_IP);
        mSavedMarketId = CacheUtils.getString(AppConstants.Cache.MARKET_ID);
        mSavedLongitude = CacheUtils.getString(AppConstants.Cache.LONGITUDE);
        mSavedLatitude = CacheUtils.getString(AppConstants.Cache.LATITUDE);
    }

    /**
     * ????????????
     */
    private void showData() {
        try {
            // ??????????????????
            uiHelper.showEditText(etLoginBottomMarketNumber, mSavedMarketNum, R.string.default_null);
            uiHelper.editTextMoveLast(etLoginBottomMarketNumber);
            // ??????????????????
            uiHelper.showEditText(etLoginBottomStallNumber, mSavedStallNum, R.string.default_null);
            uiHelper.editTextMoveLast(etLoginBottomStallNumber);
            // ????????????????????????????????????
            uiHelper.showTextView(tvLoginTopAppVersion, AppConstants.CommonStr.VERSION_STR + SoftwareUtils.getAppVersionName(LoginActivity.this), AppConstants.CommonStr.VERSION_STR + SoftwareUtils.getAppVersionName(LoginActivity.this));
            // ????????????mac??????
            uiHelper.showTextView(tvLoginWiredMac, getString(R.string.tv_login_wired_mac) + HardwareUtils.getEthernetMacAddr(), getString(R.string.tv_login_wired_mac) + HardwareUtils.getEthernetMacAddr());

            uiHelper.showTextView(tvLoginWifiMac, getString(R.string.tv_login_wifi_mac) + HardwareUtils.getWIFIMacAddr(), getString(R.string.tv_login_wifi_mac) + HardwareUtils.getWIFIMacAddr());
            //??????ID ?????????
//            uiHelper.showTextView(tvLoginHardwareId, getString(R.string.tv_login_hardware_id) + HardwareUtils.getHardwareId(LoginActivity.this), getString(R.string.tv_login_hardware_id) + HardwareUtils.getHardwareId(LoginActivity.this));

            String deviceId = ParamsUtils.getDeviceId();
            tvLoginDeviceId.setText(String.format("??????id???%s", deviceId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????????????????????????????(????????????????????????)
     */
    private void refreshPage() {
        AppConstants.DefaultSetting.REQUEST_AGAIN_REFRESH = true;
        AppConstants.DefaultSetting.REFRESH_COUNT0 = true;
    }

    /**
     * 15???????????????????????????????????????
     */
    private void autoToLoading() {
        try {
            // ???????????? ??????new ????????? ??????????????????????????????
            startTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    login();
                }
            };
        }

        if (mTimer != null && mTimerTask != null) {
            mTimer.schedule(mTimerTask, AppConstants.DefaultSetting.SET_LOGIN_JUMP_CYCLE);
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

    /**
     * ??????ProgressBar??????????????????????????? ????????????????????????CommercialInfoActivity
     * ??????
     */
    private void login() {
        try {

            String marketNum = etLoginBottomMarketNumber.getText().toString().trim();
            String stallNum = etLoginBottomStallNumber.getText().toString().trim();

            LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);

            MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);

            if (entity == null || entity.getLoginRes() == null) {
                /** ?????????????????????????????? ??????????????????.
                 * */
                if (TextUtils.isEmpty(marketNum)) {
                    runOnUiThread(() -> ToastUtils.showShort(getResources().getString(R.string.et_login_bottom_market_number)));
                    return;
                }

                if (TextUtils.isEmpty(stallNum)) {
                    runOnUiThread(() -> ToastUtils.showShort(getResources().getString(R.string.et_login_bottom_stall_number)));
                    return;
                }


                presenter.login(marketNum, stallNum);
            } else {

                if (merchantInfo != null) {
                    if (TextUtils.isEmpty(merchantInfo.getStall())) {
                        presenter.login(marketNum, stallNum);
                    } else {
                        if (merchantInfo.getStall().equals(stallNum)) {
                            loginTemplete(entity.getLoginRes());
                        } else {
                            presenter.login(marketNum, stallNum);

                        }
                    }

                } else {
                    presenter.login(marketNum, stallNum);

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ???????????????????????????
     */
    @Override
    protected void backPress() {
        // ??????ProgressBar??????????????????????????? ????????????????????????MarketInfoActivity
        if (MyClickUtils.isDoubleClick()) {
            return;
        }
        login();
    }


    /**
     * ???????????? ???CommercialInfoActivity
     *
     * @param view
     */


    /**
     * ??????????????????
     *
     * @param
     */
    public void loginOut() {
        try {
//            MerchantInfo entity = CacheUtils.getEntity(Constant.MERCHANT_INFO, MerchantInfo.class);
            MerchantInfo entity = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);

            String quitPsw = Constant.QUIT_PSD;
            if (entity != null) {
                quitPsw = entity.getQuit();
            }
            // ???????????????????????? ???????????????????????????
            uiHelper.showPsdDialog(mActivity, getResources().getString(R.string.psd_dialog_title_exit),
                    quitPsw,
                    getResources().getString(R.string.dialog_positive_button_text)
                    , new PasswordDialogCallBack() {
                        @Override
                        public void onPasswordCorrect() {


                            // ???????????????
                            new ScaleDaoManager().getInstance().closeDataBase();
                            // ??????App
                            exitApp();
                        }

                        @Override
                        public void onPasswordIncorrect() {
                            Tt.showAnimErrorToast(mActivity, getResources().getString(R.string.psd_error));
                        }
                    }
                    , getResources().getString(R.string.dialog_negative_button_text)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected LoginPresenterNew createPresenter() {
        return new LoginPresenterNew(this);
    }


    @Override
    protected void clearData() {
        // ???????????????Activity????????????
//        MyApplication.addActivity(mActivity);
    }


    /**
     * ?????????????????? ????????????????????????????????????????????????
     *
     * @param
     */
    private void showMarketNum(List<MarketCodeRes> marketCodeRes) {
        try {
            // ???????????????????????????
            uiHelper.listViewSetAdapter(mActivity, mLvPopWindow, marketCodeRes);
            // listView?????????????????????

            String lastMarketCode = CacheUtils.getString(Constant.LAST_MARKET_CODE, "");

            if (!TextUtils.isEmpty(lastMarketCode)) {
                //????????????????????? code
                etLoginBottomMarketNumber.setText(lastMarketCode);
            }
            mLvPopWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    /**  ?????? ???????????????Item ??????code.
                     * */
                    if (!TextUtils.isEmpty(marketCodeRes.get(position).getCode()) && !AppConstants.CommonStr.NULL_STR.equals(marketCodeRes.get(position).getCode())) {
                        String code = marketCodeRes.get(position).getCode();
                        etLoginBottomMarketNumber.setText(code);
                        CacheUtils.putString(Constant.LAST_MARKET_CODE, code);
                    } else {
                        etLoginBottomMarketNumber.setText(ResourceManager.getStringResource(R.string.default_null));
                    }

                }
            });
            // ??????EditText???????????? ??????????????????
            String etMarketNum = etLoginBottomMarketNumber.getText().toString().trim();
            if (TextUtils.isEmpty(etMarketNum) || AppConstants.CommonStr.NULL_STR.equals(etMarketNum)) {
                if (marketCodeRes != null && marketCodeRes.size() > 0) {
                    String marketNum = marketCodeRes.get(0).getCode();
                    LogUtils.e(TAG, "marketNum:" + marketNum);
                    etLoginBottomMarketNumber.setText(TextUtils.isEmpty(marketNum) ? "" : marketNum);
                    etLoginBottomMarketNumber.setSelection(etLoginBottomMarketNumber.getText().length());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        if (uiHelper != null) {
            uiHelper.hideDialog();
        }
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
                Log.e("loginSuccess", loginRes.toString());
            }
            // ?????? ???????????????????????? ??????.
            LoginLocalData loginLocalData = new LoginLocalData();
            loginLocalData.setLoginRes(loginRes);
            CacheUtils.putEntity(Constant.LOGIN_LOCAL, loginLocalData);
            //?????? ????????????????????????
            CacheUtils.putString(Constant.LAST_STALL_NAME, stall_name);

//            /**
//             *  ??????????????????
//             * */


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
                            LogUtils.e("updeviceinfomation", errorMsg);
                        }
                    });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loginTemplete(loginRes);
                }
            }, 1500);


        }
    }

    /***
     *  ?????? ????????????
     * */
    private void loginTemplete(LoginRes loginRes) {
        switch (loginRes.getTemplate_id()) {
            // ???????????? ??????

            case "0":
                // ?????????????????????
            case "1":
                // ????????????
                        ARouter.getInstance().build(RouterManager.MERCHANT)
                        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                       .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
//                MerchantImpl.getInstance().startMerchantActivity(LoginActivity.this);

                break;


            case "2":
                activityUtil.toOtherActivity(LoginActivity.this, BannerActivity.class);
                break;

            case "4":
                com.blankj.utilcode.util.LogUtils.d("????????????4");
                ARouter.getInstance().build(RouterManager.MERCHANT2)
//                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
                break;

            default:
                activityUtil.toOtherActivity(LoginActivity.this, BannerActivity.class);
                break;
        }
    }

    @Override
    public void loginError(String msg) {
        Log.e("loginError", msg);
        // ?????? ????????????????????????
        LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
        if (entity != null) {
            LoginRes loginRes = entity.getLoginRes();
            loginTemplete(loginRes);
        }
    }

    @Override
    public void getMarketCode(BaseResponse<List<MarketCodeRes>> response) {
        if (response == null) {
            return;
        }
        int code = response.getCode();
        if (code == 0) {
            ToastUtils.showShort(response.getMsg());
            return;
        }
        if (code == 1) {
            List<MarketCodeRes> marketCodeRes = response.getObj();
            if (marketCodeRes != null && marketCodeRes.size() > 0) {
                showMarketNum(marketCodeRes);
                MarketCodeLocalData marketCodeLocalData = new MarketCodeLocalData();
                marketCodeLocalData.setMarketCodeRes(marketCodeRes);
                CacheUtils.putEntity(Constant.MARKET_LOCAL_DATA, marketCodeLocalData);
            }
        }


    }

    @Override
    public void marketCodeError(String msg) {
        LogUtils.e(TAG, msg);
    }

    @Override
    public void updateSuccess(AutoUpdateRes response, String market_id) {
        if (response == null) {
            LogUtils.e(TAG, "????????????????????? ???");
            return;
        }

        SplashHelper splashHelper = new SplashHelper();

        CacheUtils.putEntity(Constant.UPDATE_DATA, response);
        if (BuildConfig.DEBUG) {
            LogUtils.e(TAG, "software update success:" + response.toString());
        }
        splashHelper.dealAutoUpdate(LoginActivity.this, response, market_id);


    }

    @Override
    public void updateError(String msg) {
        LogUtils.e(TAG, "updateError ???" + msg);
    }

}