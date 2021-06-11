package com.zhumei.commercialscreen.help;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.zhumei.baselib.app.AppConstants;
import com.zhumei.baselib.utils.useing.software.LogUtils;
import com.zhumei.commercialscreen.ui.adapter.BleSettingAdapter;

import java.util.List;

public class BleSettingHelper {

    private static final String TAG = "BleSettingModelImpl";

    /**
     * 评估蓝牙可用性
     *
     * @param activity
     * @param adapter
     * @param ivBleSettingLoading
     * @param anim
     */
    public void evaluateBle(Activity activity, BleSettingAdapter adapter, ImageView ivBleSettingLoading, Animation anim) {
        try {
            if (!BleManager.getInstance().isSupportBle()) {
                LogUtils.e(TAG, "Bluetooth device does not support Bluetooth!");
                ToastUtils.showShort("请检查蓝牙是否开启！");
                return;
            }

            LogUtils.e(TAG, "Device support Bluetooth!");
            boolean blueEnable = BleManager.getInstance().isBlueEnable();
            if (blueEnable) {
                LogUtils.e(TAG, "Bluetooth has been opened!");
                // 指定扫描规则
                setScanRule();
                // 开始扫描
                startScan(adapter, ivBleSettingLoading, anim);
            } else {
                LogUtils.e(TAG, "Bluetooth has not been opened!");
                // 通过Intent引导用户打开蓝牙
                openBluetooth(activity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示蓝牙连接的设备
     *
     * @param adapter
     */
    public void showConnectedDevice(BleSettingAdapter adapter) {
        try {
            List<BleDevice> allConnectedDevice = BleManager.getInstance().getAllConnectedDevice();
            adapter.clearConnectedDevice();
            if (allConnectedDevice != null) {
                if (!allConnectedDevice.isEmpty()) {
                    for (BleDevice bleDevice : allConnectedDevice) {
                        adapter.addDevice(bleDevice);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定扫描规则
     */
    private void setScanRule() {
        try {
//            UUID[] serviceUuids = new UUID[1];
//            serviceUuids[0] = AppConstants.ZSLF.SERVICE_UUID;

//            String[] names = new String[10];


            BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                    // 扫描超时时间，可选，默认10秒
                    .setScanTimeOut(AppConstants.DefaultSetting.SET_SCAN_TIMEOUT)
                    .setAutoConnect(true)
//                    .setServiceUuids(serviceUuids)
//                    .setDeviceName(true,"LF_F1-18110130")
                    .build();
            BleManager.getInstance().initScanRule(scanRuleConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始扫描
     *
     * @param adapter
     * @param ivBleSettingLoading
     * @param anim
     */
    private void startScan(final BleSettingAdapter adapter, final ImageView ivBleSettingLoading, final Animation anim) {
        try {
            BleScanCallback call = new BleScanCallback() {
                @Override
                public void onScanFinished(List<BleDevice> scanResultList) {
                    LogUtils.e(TAG, "onScanFinished:" + scanResultList.toString());
                    ivBleSettingLoading.clearAnimation();
                    ivBleSettingLoading.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onScanStarted(boolean success) {
                    LogUtils.e(TAG, "onScanStarted:" + success + "!");
                    adapter.clearScanDevice();
                    adapter.notifyDataSetChanged();
                    ivBleSettingLoading.startAnimation(anim);
                    ivBleSettingLoading.setVisibility(View.VISIBLE);
                }

                @Override
                public void onScanning(BleDevice bleDevice) {
                    LogUtils.e(TAG, "onScanning!");
                    adapter.addDevice(bleDevice);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onLeScan(BleDevice bleDevice) {
                    super.onLeScan(bleDevice);
                    LogUtils.e(TAG, "onLeScan!");
                }
            };
            BleManager.getInstance().scan(call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Intent引导用户打开蓝牙
     *
     * @param activity
     */
    private void openBluetooth(Activity activity) {
        try {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, AppConstants.EventCode.OPEN_BLUETOOTH_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
