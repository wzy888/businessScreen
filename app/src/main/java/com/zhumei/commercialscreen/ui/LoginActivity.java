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
     * 需要的权限
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
     * progressBar 是否正在使用中
     */

    /**
     * 每次启动需要访问一次网络
     */
    private boolean mInternetConnected = true;
    private boolean mInternetDisConnected = true;
    /**
     * 下拉列表的数据源
     */
    private PopupWindow mPopupWindow;
    private ListView mLvPopWindow;
    /**
     * 获取缓存数据
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
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        //Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();

        LogUtils.e("onPermissionsGranted", perms.toString());

    }

    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
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

        //启动时判断网络状态
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
                // 防止快速点击
                if (MyClickUtils.isDoubleClick()) {
                    ToastUtils.showShort("过快点击");
                    return;
                }
                login();
            }
        });


        btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyClickUtils.isDoubleClick()) {
                    ToastUtils.showShort("过快点击");
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

        // 默认打开 Wifi
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
                //网络状态变化时的操作
                netConnect = (boolean) event.getData();
                LogUtils.e(TAG, netConnect + "");
                //            ToastMessage(R.mipmap.toast_succee, Toast.LENGTH_SHORT, getString(R.string.net_success));
                //网络状态变化时的操作
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
            // 个推推送
            case AppConstants.EventCode.GETUI_MSG:
                getGeTuiMsg((String) event.getData());
                break;

        }

    }


    /**
     * 处理个推数据
     *
     * @param geTuiMsg
     */
    private void getGeTuiMsg(String geTuiMsg) {
        try {

            if (TextUtils.isEmpty(geTuiMsg)) {
                LogUtils.e(TAG, "暂未收到推送！");
                return;
            }

            LogUtils.e(TAG, "geTuiMsg:" + geTuiMsg);
            GeTuiRes geTuiRes = JsonUtils.json2Object(geTuiMsg, GeTuiRes.class);
            String event_type = geTuiRes.getEvent_type();
            LoginLocalData localData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);

            switch (event_type) {
                case Constant.UPDATE_APP:
                    //请求升级 是否更新
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
                // 显示没网提示弹框
                uiHelper.showDialog(LoginActivity.this, getResources().getString(R.string.psd_dialog_title_net), getResources().getString(R.string.dialog_msg_net)
                        , getResources().getString(R.string.dialog_positive_button_text)
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 跳转到设置界面
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
            // 有网络关闭网络设置弹窗 并且重新生成设备id和二维码
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


        //获取 公网IP
        HttpUtils.getPublicIp();
        initPopupWindow();
        getCacheData();


        getPermission();

        // 显示数据
        showData();
        // 登录页重置页面数据请求(应用退出也会重置)
        refreshPage();
        // 15分钟后自动跳入commercialInfo界面
        autoToLoading();
    }

    //获取权限
    private void getPermission() {
        ThreadManager.executeSingleTask(() -> {
            if (EasyPermissions.hasPermissions(mActivity, requestPermissions)) {
                //已经打开权限
                LogUtils.e("getPermission", "已经申请相关权限");
            } else {
                //没有打开相关权限、申请权限
                EasyPermissions.requestPermissions(mActivity, "需要获取您的使用权限",
                        1, requestPermissions);
            }

        });

    }


    /**
     * 初始化PopWindow，给弹窗设置ListView
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
            // 隐藏侧滑栏
            mLvPopWindow.setVerticalScrollBarEnabled(false);
            // 生成PopWindow
            mPopupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setOutsideTouchable(true);
            // 为其设置背景，使得其内外焦点都可以获得
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            mPopupWindow.setFocusable(true);
            // 设置ImageButton获取焦点监听
            loginHelper.imageButtonSetOnFocusChangeListener(ibLoginBottomPulldown, R.drawable.ib_login_bottom_pulldown_focused, R.drawable.ib_login_bottom_pulldown_normal);
            // 设置ImageButton点击事件 打开PopupWindow
            loginHelper.imageButtonSetOnClickListener(ibLoginBottomPulldown, mPopupWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取CacheUtils中缓存的数据
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
     * 显示数据
     */
    private void showData() {
        try {
            // 显示市场编号
            uiHelper.showEditText(etLoginBottomMarketNumber, mSavedMarketNum, R.string.default_null);
            uiHelper.editTextMoveLast(etLoginBottomMarketNumber);
            // 显示摊位编号
            uiHelper.showEditText(etLoginBottomStallNumber, mSavedStallNum, R.string.default_null);
            uiHelper.editTextMoveLast(etLoginBottomStallNumber);
            // 显示登录页面的标题版本号
            uiHelper.showTextView(tvLoginTopAppVersion, AppConstants.CommonStr.VERSION_STR + SoftwareUtils.getAppVersionName(LoginActivity.this), AppConstants.CommonStr.VERSION_STR + SoftwareUtils.getAppVersionName(LoginActivity.this));
            // 显示设备mac地址
            uiHelper.showTextView(tvLoginWiredMac, getString(R.string.tv_login_wired_mac) + HardwareUtils.getEthernetMacAddr(), getString(R.string.tv_login_wired_mac) + HardwareUtils.getEthernetMacAddr());

            uiHelper.showTextView(tvLoginWifiMac, getString(R.string.tv_login_wifi_mac) + HardwareUtils.getWIFIMacAddr(), getString(R.string.tv_login_wifi_mac) + HardwareUtils.getWIFIMacAddr());
            //硬件ID 不展示
//            uiHelper.showTextView(tvLoginHardwareId, getString(R.string.tv_login_hardware_id) + HardwareUtils.getHardwareId(LoginActivity.this), getString(R.string.tv_login_hardware_id) + HardwareUtils.getHardwareId(LoginActivity.this));

            String deviceId = ParamsUtils.getDeviceId();
            tvLoginDeviceId.setText(String.format("设备id：%s", deviceId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录页重置页面数据请求(应用退出也会重置)
     */
    private void refreshPage() {
        AppConstants.DefaultSetting.REQUEST_AGAIN_REFRESH = true;
        AppConstants.DefaultSetting.REFRESH_COUNT0 = true;
    }

    /**
     * 15分钟后自动跳入商户信息界面
     */
    private void autoToLoading() {
        try {
            // 修改内部 直接new 的方式 减少不必要的内存泄漏
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
     * 确保ProgressBar加载中不再继续加载 加载完成后跳转至CommercialInfoActivity
     * 登录
     */
    private void login() {
        try {

            String marketNum = etLoginBottomMarketNumber.getText().toString().trim();
            String stallNum = etLoginBottomStallNumber.getText().toString().trim();

            LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);

            MerchantInfo merchantInfo = MmkvUtils.getInstance().getObject(Constant.MERCHANT_INFO, MerchantInfo.class);

            if (entity == null || entity.getLoginRes() == null) {
                /** 市场编号，摊位号编码 没有输入拦截.
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
     * 点击返回键做的操作
     */
    @Override
    protected void backPress() {
        // 确保ProgressBar加载中不再继续加载 加载完成后跳转至MarketInfoActivity
        if (MyClickUtils.isDoubleClick()) {
            return;
        }
        login();
    }


    /**
     * 按登陆键 到CommercialInfoActivity
     *
     * @param view
     */


    /**
     * 点击退出按钮
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
            // 显示退出提示弹窗 密码输入正确就退出
            uiHelper.showPsdDialog(mActivity, getResources().getString(R.string.psd_dialog_title_exit),
                    quitPsw,
                    getResources().getString(R.string.dialog_positive_button_text)
                    , new PasswordDialogCallBack() {
                        @Override
                        public void onPasswordCorrect() {


                            // 关闭数据库
                            new ScaleDaoManager().getInstance().closeDataBase();
                            // 退出App
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
        // 将要退出的Activity加到集合
//        MyApplication.addActivity(mActivity);
    }


    /**
     * 显示市场编号 将数组中第一个编号确定为市场编号
     *
     * @param
     */
    private void showMarketNum(List<MarketCodeRes> marketCodeRes) {
        try {
            // 设置下拉弹框的数据
            uiHelper.listViewSetAdapter(mActivity, mLvPopWindow, marketCodeRes);
            // listView的条目点击监听

            String lastMarketCode = CacheUtils.getString(Constant.LAST_MARKET_CODE, "");

            if (!TextUtils.isEmpty(lastMarketCode)) {
                //记录上次选择的 code
                etLoginBottomMarketNumber.setText(lastMarketCode);
            }
            mLvPopWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    /**  记录 上次点击的Item 位置code.
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
            // 如果EditText为空的话 从网络上获取
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
            // 登录 成功根据后端返回 跳转.
            LoginLocalData loginLocalData = new LoginLocalData();
            loginLocalData.setLoginRes(loginRes);
            CacheUtils.putEntity(Constant.LOGIN_LOCAL, loginLocalData);
            //保存 登录正确的摊位号
            CacheUtils.putString(Constant.LAST_STALL_NAME, stall_name);

//            /**
//             *  上传设备信息
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
     *  登录 不同模板
     * */
    private void loginTemplete(LoginRes loginRes) {
        switch (loginRes.getTemplate_id()) {
            // 模板编号 跳转

            case "0":
                // 有秤屏联动版本
            case "1":
                // 无秤版本
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
                com.blankj.utilcode.util.LogUtils.d("进入模板4");
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
        // 登录 失败根据缓存跳转
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
            LogUtils.e(TAG, "未获取升级数据 ！");
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
        LogUtils.e(TAG, "updateError ：" + msg);
    }

}