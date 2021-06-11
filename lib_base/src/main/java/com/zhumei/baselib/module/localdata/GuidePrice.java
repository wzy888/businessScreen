package com.zhumei.baselib.module.localdata;

import android.os.Parcel;
import android.os.Parcelable;


import com.zhumei.baselib.module.response.GoodsInfoRes;

import java.util.List;

/**
 * 本地缓存 指导菜价
 */
public class GuidePrice implements Parcelable {

    private List<GoodsInfoRes> goodsInfoResList;

    public List<GoodsInfoRes> getGoodsInfoResList() {
        return goodsInfoResList;
    }

    public void setGoodsInfoResList(List<GoodsInfoRes> goodsInfoResList) {
        this.goodsInfoResList = goodsInfoResList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.goodsInfoResList);
    }

    public GuidePrice() {
    }

    protected GuidePrice(Parcel in) {
        this.goodsInfoResList = in.createTypedArrayList(GoodsInfoRes.CREATOR);
    }

    public static final Creator<GuidePrice> CREATOR = new Creator<GuidePrice>() {
        @Override
        public GuidePrice createFromParcel(Parcel source) {
            return new GuidePrice(source);
        }

        @Override
        public GuidePrice[] newArray(int size) {
            return new GuidePrice[size];
        }
    };
}
