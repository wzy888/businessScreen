package com.zhumei.commercialscreen.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.zhumei.baselib.utils.ResourceManager;
import com.zhumei.baselib.utils.useing.hardware.HexUtils;
import com.zhumei.baselib.utils.useing.software.StringUtils;
import com.zhumei.commercialscreen.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BleSettingAdapter extends RecyclerView.Adapter<BleSettingAdapter.ViewHolder> {

    private Context mContext;
    private List<BleDevice> mBleDeviceList;
//    private BaseView mBaseView;

    public BleSettingAdapter(Context context) {
        this.mContext = context;
        mBleDeviceList = new ArrayList<>();
//        mBaseView = new BaseViewImpl(context);
    }

    /**
     * 集合中添加蓝牙设备
     * @param bleDevice
     */
    public void addDevice(BleDevice bleDevice) {
        removeDevice(bleDevice);
        mBleDeviceList.add(bleDevice);
    }

    /**
     * 集合中删除蓝牙设备
     * @param bleDevice
     */
    private void removeDevice(BleDevice bleDevice) {
        for (int i = 0; i < mBleDeviceList.size(); i++) {
            BleDevice device = mBleDeviceList.get(i);
            if (device.getKey().equals(bleDevice.getKey())) {
                mBleDeviceList.remove(i);
            }
        }
    }

    /**
     * 清除已连接的蓝牙
     */
    public void clearConnectedDevice() {
        for (int i = 0; i < mBleDeviceList.size(); i++) {
            BleDevice device = mBleDeviceList.get(i);
            if (BleManager.getInstance().isConnected(device)) {
                mBleDeviceList.remove(i);
            }
        }
    }

    /**
     * 清除正在扫描的蓝牙
     */
    public void clearScanDevice() {
        for (int i = 0; i < mBleDeviceList.size(); i++) {
            BleDevice device = mBleDeviceList.get(i);
            if (BleManager.getInstance().isConnected(device)) {
                mBleDeviceList.remove(i);
            }
        }
    }

    /**
     * 清除所有蓝牙
     */
    public void clear() {
        clearConnectedDevice();
        clearScanDevice();
    }

    /**
     * 根据条目位置获取BleDevice
     * @param position
     * @return
     */
    private BleDevice getItem(int position) {
        if (position > mBleDeviceList.size()) {
            return null;
        } else {
            return mBleDeviceList.get(position);
        }
    }

    /**
     * 根据adapter布局返回ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.ble_setting_rv_item , parent, false));
    }

    /**
     * 填充item布局中的数据
     * @param holder
     * @param position
     */
    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 取消蓝牙
                    mItemClickListener.onItemClick(position, getItem(position));
                }
            });
            holder.itemView.setTag(position);
            BleDevice device = mBleDeviceList.get(position);
            String name = device.getName();

            holder.mTvBleSettingItemName.setText(TextUtils.isEmpty(name)?
                    ResourceManager.getStringResource( R.string.tv_ble_setting_item_name): name);

            holder.mTvBleSettingItemMac.setText(TextUtils.isEmpty( device.getMac())?"Mac: ": String.format("Mac: %s", device.getMac()));


            holder.mTvBleSettingItemRssi.setText("RSSI:"+device.getRssi() );
            String format = new SimpleDateFormat(mContext.getString(R.string.simple_data_format)).format(new Date(device.getTimestampNanos()));
            holder.mTvBleTimeSettingItemStampNanos.setText(StringUtils.isNotEmpty2(format)?"Time :"+format : "Time :");

            String txt = HexUtils.bytesToHex(device.getScanRecord());

            holder.mTvBleSettingItemScanRecord.setText(StringUtils.isNotEmpty2(txt)?"ScanRecord："+txt : "ScanRecord：");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取总条目数
     * @return
     */
    @Override
    public int getItemCount() {
        return mBleDeviceList == null ? 0 : mBleDeviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ble_setting_item_name)
        TextView mTvBleSettingItemName;
        @BindView(R.id.tv_ble_setting_item_mac)
        TextView mTvBleSettingItemMac;
        @BindView(R.id.tv_ble_setting_item_rssi)
        TextView mTvBleSettingItemRssi;
        @BindView(R.id.tv_ble_time_setting_item_stamp_nanos)
        TextView mTvBleTimeSettingItemStampNanos;
        @BindView(R.id.tv_ble_setting_item_scan_record)
        TextView mTvBleSettingItemScanRecord;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 条目点击监听
     */
    public interface OnItemClickListener {
        void onItemClick(int position, BleDevice bleDevice);
    }

    private OnItemClickListener mItemClickListener;

    /**
     * 设置条目点击监听
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
