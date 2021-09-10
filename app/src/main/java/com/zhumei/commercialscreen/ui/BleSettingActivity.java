package com.zhumei.commercialscreen.ui;

import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.aroute.RouterManager;
import com.zhumei.baselib.base.BaseActivity;
import com.zhumei.baselib.base.BasePresenterNew;
import com.zhumei.baselib.bll_merchant.impl.MerchantImpl;
import com.zhumei.baselib.utils.ActivityUtil;
import com.zhumei.baselib.utils.useing.cache.CacheUtils;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.baselib.widget.percent_support_extends.PercentLinearLayout;
import com.zhumei.baselib.widget.recyclreview.RecycleViewDivider;
import com.zhumei.commercialscreen.MyApplication;
import com.zhumei.commercialscreen.R;
import com.zhumei.commercialscreen.help.BleSettingHelper;
import com.zhumei.commercialscreen.ui.adapter.BleSettingAdapter;

@Route(path = RouterManager.BLESET)
public class BleSettingActivity extends BaseActivity {


    private static final String TAG = "BleSettingActivity";
    private Context mContext = BleSettingActivity.this;
    private Activity mActivity = BleSettingActivity.this;
//    private BaseModel mBaseModel;
//    private BleSettingModel mBleSettingModel;

    /**
     * 蓝牙设置
     */
    private BleSettingAdapter mBleSettingAdapter;
    private Animation mSearchRotAnim;
    private ActivityUtil activityUtil = new ActivityUtil();
    private BleSettingHelper bleSettingHelper = new BleSettingHelper();
    private PercentLinearLayout mLlBleSettingTitle;
    private TextView mTvBleSettingTitle;
    private RecyclerView mRvBleSetting;
    private ImageView mIvBleSettingLoading;



    @Override
    public void initView() {
        mLlBleSettingTitle = (PercentLinearLayout) findViewById(R.id.ll_ble_setting_title);
        mTvBleSettingTitle = (TextView) findViewById(R.id.tv_ble_setting_title);
        mRvBleSetting = (RecyclerView) findViewById(R.id.rv_ble_setting);
        mIvBleSettingLoading = (ImageView) findViewById(R.id.iv_ble_setting_loading);
        mSearchRotAnim = AnimationUtils.loadAnimation(mContext, R.anim.ble_searching_rotate);
        mSearchRotAnim.setInterpolator(new LinearInterpolator());

        LogUtils.e(TAG,"init---");
//


    }

    @Override
        public void initData() {
        try {
            mBleSettingAdapter = new BleSettingAdapter(mContext);
            mRvBleSetting.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            mRvBleSetting.setAdapter(mBleSettingAdapter);
            mRvBleSetting.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.ble_setting_item_divide_black));
            mBleSettingAdapter.setOnItemClickListener(new BleSettingAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, BleDevice bleDevice) {
                    if (!BleManager.getInstance().isConnected(bleDevice)) {
                        // 点击搜索 列表链接蓝牙.
                        BleManager.getInstance().cancelScan();
                        CacheUtils.putString(AppConstants.Cache.BLE_MAC_ADDR, bleDevice.getMac());

                        ARouter.getInstance().build(RouterManager.MERCHANT)
//                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                .navigation();
//                        MerchantImpl.getInstance().startMerchantActivity(BleSettingActivity.this);

                    } else {
                        LogUtils.e(TAG, "BLE isnot connected!");
                    }
                }
            });

            // 评估蓝牙可用性
            bleSettingHelper.evaluateBle(mActivity, mBleSettingAdapter, mIvBleSettingLoading, mSearchRotAnim);
            // 显示蓝牙连接的设备
            bleSettingHelper.showConnectedDevice(mBleSettingAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ble_setting;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected BasePresenterNew createPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        BleManager.getInstance().cancelScan();


    }

    @Override
    protected void backPress() {
        activityUtil.toOtherActivity(mActivity, LoginActivity.class);
    }

    @Override
    protected void clearData() {
        try {
            // 关闭蓝牙搜索动画
            mIvBleSettingLoading.clearAnimation();
            // 将要退出的Activity加到集合
            MyApplication.getMyApplication().addActivity(mActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}