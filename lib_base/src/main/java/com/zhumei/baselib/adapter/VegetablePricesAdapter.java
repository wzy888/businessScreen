package com.zhumei.baselib.adapter;


import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhumei.baselib.R;
import com.zhumei.baselib.helper.UiHelper;
import com.zhumei.baselib.module.response.GoodsPriceRes;
import com.zhumei.baselib.utils.useing.software.StringUtils;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

public class VegetablePricesAdapter extends BaseQuickAdapter<GoodsPriceRes.ContentRes, BaseViewHolder> {
    private UiHelper uiHelper;

    public VegetablePricesAdapter(@Nullable List<GoodsPriceRes.ContentRes> data) {
        super(R.layout.vegetable_prices_item, data);
        uiHelper = new UiHelper();
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, GoodsPriceRes.ContentRes item) {


        String price = setPrice(item);


        helper.setText(R.id.tv_name, item.getGoods_name())
                .setText(R.id.tv_price, StringUtils.isNotEmpty2(price) ? price : "");
        ImageView iv_goods = helper.getView(R.id.iv_goods);

        uiHelper.loadImageView(iv_goods, item.getImage(), R.drawable.shucai);


    }

    @NotNull
    private String setPrice(GoodsPriceRes.ContentRes item) {
        String price = "";

        if (TextUtils.isEmpty(item.getPrice())) {
            price = "时价";
            return price;
        }


        try {

            boolean numeric = StringUtils.isDecimal(item.getPrice());
            if (numeric) {
                double v = Double.parseDouble(item.getPrice());
                if (v <= 0) {
                    price = "时价";
                } else {
                    price = item.getPrice() + item.getUnit_name();
                }
            } else {
                price = item.getPrice();
            }


        } catch (Exception e) {
            price = "时价";
            e.printStackTrace();
        }


        return price;
    }


    @Nullable
    @Override
    public GoodsPriceRes.ContentRes getItem(int position) {
        int newPosition = position % getData().size();
        return getData().get(newPosition);
    }

    @Override
    public int getItemViewType(int position) {
        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
        int count = getHeaderLayoutCount() + getData().size();
        if (count <= 0) {
            count = 1;
        }
        int newPosition = position % count;
        return super.getItemViewType(newPosition);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
