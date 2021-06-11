package com.zhumei.commercialscreen.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;

import com.youth.banner.Banner2;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.ZoomOutTranformer;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.base.BaseActivity;
import com.zhumei.baselib.base.Event;
import com.zhumei.baselib.config.Constant;
import com.zhumei.baselib.module.localdata.LoginLocalData;
import com.zhumei.baselib.module.response.AutoUpdateRes;
import com.zhumei.baselib.module.response.BannerRes;
import com.zhumei.baselib.module.response.GeTuiRes;
import com.zhumei.baselib.module.response.LoginRes;
import com.zhumei.baselib.utils.ActivityUtil;
import com.zhumei.baselib.utils.BannerActivityGlideImageLoader;
import com.zhumei.baselib.utils.CommercialInfoActivityGlideImageLoader;
import com.zhumei.baselib.utils.JsonUtils;
import com.zhumei.baselib.utils.NetUtil;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.hardware.HardwareUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.widget.viewpager.HorizontalStackTransformer;
import com.zhumei.commercialscreen.R;
import com.zhumei.baselib.helper.SplashHelper;
import com.zhumei.commercialscreen.presenter.bannerpresenter.BannerNewPresenter;
import com.zhumei.commercialscreen.presenter.bannerpresenter.BannerNewView;

import java.util.ArrayList;
import java.util.List;

/**
 * Base 待修改完全 替换
 */
@Route(path = RouterManager.banner)
public class BannerActivity extends BaseActivity<BannerNewPresenter> implements BannerNewView {




    private static final String TAG = "BannerActivity";
    private static final int DELAY_TIME = 8000;


    private int mBannerFlag = 0; // 0 默认表示恒频   1竖屏
    private int mIsGeTuiMsgInUse = 88;

    private ActivityUtil activityUtil = new ActivityUtil();
    //    private UiHelper uiHelper = new UiHelper();
    private SplashHelper splashHelper = new SplashHelper();

    /**
     * Banner广告图字符串
     * //
     */

    private boolean netConnect = false;
    private Banner2 bannerImageRotate;
    private ImageView ivInfoInternet;
    private ImageView ivInfoServernet;


    @Override
    protected int getLayoutId() {

        if (HardwareUtils.getScreenResolution(BannerActivity.this).x > HardwareUtils.getScreenResolution(BannerActivity.this).y) {
            mBannerFlag = 0;
            return R.layout.activity_banner_horizontal;
        } else {
            mBannerFlag = 1;
            return R.layout.activity_banner_vertical;
        }
    }

    @Override
    public void initView() {
        bannerImageRotate = (Banner2) findViewById(R.id.banner_image_rotate);
        ivInfoInternet = (ImageView) findViewById(R.id.iv_info_internet);
        ivInfoServernet = (ImageView) findViewById(R.id.iv_info_servernet);

        NetUtil.isNetPingBd(new NetUtil.NetCallBack() {
            @Override
            public void isConnect(final boolean connect) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        netConnect = connect;
                        if (netConnect) {
                            ivInfoInternet.setVisibility(View.GONE);
                        } else {
                            ivInfoInternet.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        },Constant.PING_BAIDU_IP);


    }


    @Override
    public void onMainThreadEventPost(Event event) {
        switch (event.getCode()) {
            // 个推推送
            case AppConstants.EventCode.GETUI_MSG:
                getGeTuiMsg((String) event.getData());
                break;
            case AppConstants.EventCode.NET_WORk:
                netConnect = (boolean) event.getData();
                if (netConnect) {
                    netWorkSuccess();
                } else {
                    netWorkError();
                }
                break;
        }
    }


    /**
     * 访问 网络失败
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
            // 判断是否需要再次刷新
            LoginLocalData entity = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
            if (AppConstants.DefaultSetting.REQUEST_AGAIN_REFRESH) {
                // 判断是否是从断网到有网的 刷新
//
                AppConstants.DefaultSetting.REFRESH_COUNT0 = false;
                LogUtils.e(TAG, "need to request again refresh!");
                // 需要进行软件升级
//                mmkvUtils.putBoolean(AppConstants.Cache.SOFTWARE_UPDATE, true);
                // 页面刷新


                if (entity != null && entity.getLoginRes() != null) {
                    String market_id = entity.getLoginRes().getMarket_id();
                    presenter.getBanner(market_id);
                }

                AppConstants.DefaultSetting.REQUEST_AGAIN_REFRESH = false;
            } else {
                LogUtils.e(TAG, "need not to request again refresh!");
            }
            // 判断软件升级
            if (entity != null && entity.getLoginRes() != null) {
                LoginRes loginRes = entity.getLoginRes();
                presenter.autoUpdate(loginRes.getMarket_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initData() {

        try {

            // 获取缓存数据
            // 横屏的轮播Banner图片  可能从网络获取也可能为默认图片
//            bannerData = DataBean.getDefaultData();
//            adapter = new BannerInfoAdapter(DataBean.getDefaultData(), BannerActivity.this);



            BannerRes bannerRes = CacheUtils.getEntity(Constant.BANNER_DATA, BannerRes.class);
            if (mBannerFlag == 0) {
                // 显示数据
                if (bannerRes ==null || null == bannerRes.getFull()  ) {
                    showDefaultBanner();

                }else {
                    if (bannerRes.getFull() != null && bannerRes.getFull().size() > 0) {
                        showBannerData(bannerRes);
                    }
                }





            } else {
                // 竖屏写死的Banner图片 没从网络获取
                showDefaultVBanner();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginLocalData loginLocalData = CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);
        if (loginLocalData != null) {
            LoginRes loginRes = loginLocalData.getLoginRes();
            String merchant_id = loginRes.getMerchant_id();
            String market_id = loginRes.getMarket_id();
            presenter.getBanner(merchant_id);
            presenter.autoUpdate(market_id);
        }


    }


    /**
     * 显示默认竖屏Banner
     */
    private void showDefaultVBanner() {
        try {


                List<Integer> imageList = new ArrayList<>();

                imageList.add(R.drawable.default_vbanner1);
                imageList.add(R.drawable.default_vbanner2);
                imageList.add(R.drawable.default_vbanner3);
                imageList.add(R.drawable.default_vbanner4);


                bannerImageRotate.setImages(imageList)
                        .setBannerStyle(BannerConfig.NOT_INDICATOR)
                        .setImageLoader(new CommercialInfoActivityGlideImageLoader())
                        .setPageTransformer(true, new ZoomOutTranformer())
                        .isAutoPlay(true)
                        .setDelayTime(DELAY_TIME)
                        .setViewPagerIsScroll(false)
                        .start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击菜单键进入菜价修改界面，点击退格键进入观看电视设置界面
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            // 静音键就是home键
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


    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected BannerNewPresenter createPresenter() {
        return new BannerNewPresenter(this);
    }


    @Override
    protected void backPress() {
        activityUtil.toOtherActivity(BannerActivity.this, LoginActivity.class);
    }

    @Override
    protected void clearData() {
    }


    /**
     * 处理个推数据
     *
     * @param geTuiMsg
     */
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
            LoginLocalData localData =CacheUtils.getEntity(Constant.LOGIN_LOCAL, LoginLocalData.class);

            switch (event_type) {
                case Constant.UPDATE_APP:
                    //请求升级 是否更新
                    if (localData != null) {
                        LoginRes loginRes = localData.getLoginRes();
                        presenter.autoUpdate(loginRes.getMarket_id());

                    }

                    break;
                case Constant.FRESH_PAGE:
                    //刷新页面
                    if (localData != null) {
                        LoginRes loginRes = localData.getLoginRes();
                        presenter.getBanner(loginRes.getMerchant_id());


                    }
                    break;

                default:

                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bannerSuccess(BannerRes response) {
        if (response == null) {
            showDefaultBanner();
            return;
        }
        LogUtils.e("banner", response.toString());
        CacheUtils.putEntity(Constant.BANNER_DATA, response);



        if (response == null || response.getFull().size() == 0) {
            // 处理Banner 空数据
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
        if (entity != null && entity.getFull() != null && entity.getFull().size() > 0) {

            showBannerData(entity);
        } else {
            showDefaultBanner();
        }
    }

    private void showBannerData(BannerRes response) {
         List<String> mFullBanner = new ArrayList<>();
         mFullBanner.clear();
         List<String> full1 = response.getFull();
         mFullBanner.addAll(full1);

        bannerImageRotate
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setImageLoader(new CommercialInfoActivityGlideImageLoader())
                .setPageTransformer(true, new HorizontalStackTransformer())
                .isAutoPlay(true)
                .setDelayTime(DELAY_TIME)
                .setViewPagerIsScroll(false);
        bannerImageRotate.update(mFullBanner);
    }

    private void showDefaultBanner() {


            List<Integer> imageList = new ArrayList<>();
            imageList.clear();
            imageList.add(R.drawable.default_hbanner1);
            imageList.add(R.drawable.default_hbanner2);
            imageList.add(R.drawable.default_hbanner3);
            imageList.add(R.drawable.default_hbanner4);


            bannerImageRotate
                    .setBannerStyle(BannerConfig.NOT_INDICATOR)
                    .setImageLoader(new BannerActivityGlideImageLoader())
                    .setPageTransformer(true, new HorizontalStackTransformer())
                    .isAutoPlay(true)
                    .setDelayTime(DELAY_TIME)
                    .setViewPagerIsScroll(false);
        bannerImageRotate.update(imageList);

    }



    @Override
    public void updateSuccess(AutoUpdateRes response, String market_id) {
        if (response == null) {
            LogUtils.e(TAG, "未获取升级数据 ！");
            return;
        }

        CacheUtils.putEntity(Constant.UPDATE_DATA, response);
        LogUtils.e(TAG, "software update success:" + response.toString());
        splashHelper.dealAutoUpdate(BannerActivity.this, response, market_id);


    }

    @Override
    public void updateError(String msg) {
        ToastUtils.showShort(msg);
    }
}