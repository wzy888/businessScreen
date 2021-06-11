package com.zhumei.baselib.adapter;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhumei.baselib.R;
import com.zhumei.baselib.bean.ble_setting.BleCmd2Bean;


import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

public class PendingOrderAdapter extends BaseQuickAdapter<BleCmd2Bean, BaseViewHolder> {


    public PendingOrderAdapter( List<BleCmd2Bean> data) {
        super(R.layout.pending_order_item, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, BleCmd2Bean item) {

        int position = viewHolder.getBindingAdapterPosition();
        int index = position + 1;
        String orderPrice = item.getUnitPrice() + "/" + item.getUnit();
        String weight = item.getWeightPcs() + item.getUnit();

        viewHolder.setText(R.id.tv_order_name, TextUtils.isEmpty(item.getName()) ? index + "" : index + "." + item.getName())
                .setText(R.id.tv_order_price, orderPrice)
                .setText(R.id.tv_order_weight, "x" + weight)
                .setText(R.id.tv_money, item.getTotPrice() + "元");
    }


}
