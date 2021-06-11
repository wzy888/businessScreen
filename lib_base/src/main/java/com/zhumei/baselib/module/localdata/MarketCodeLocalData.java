package com.zhumei.baselib.module.localdata;

import android.os.Parcel;
import android.os.Parcelable;


import com.zhumei.baselib.module.response.MarketCodeRes;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地缓存 市场编号
 * */
public class MarketCodeLocalData implements Parcelable {
  private    List<MarketCodeRes> marketCodeRes;

    public MarketCodeLocalData() {
    }

    public List<MarketCodeRes> getMarketCodeRes() {
        return marketCodeRes;
    }

    public void setMarketCodeRes(List<MarketCodeRes> marketCodeRes) {
        this.marketCodeRes = marketCodeRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.marketCodeRes);
    }

    protected MarketCodeLocalData(Parcel in) {
        this.marketCodeRes = new ArrayList<MarketCodeRes>();
        in.readList(this.marketCodeRes, MarketCodeRes.class.getClassLoader());
    }

    public static final Parcelable.Creator<MarketCodeLocalData> CREATOR = new Parcelable.Creator<MarketCodeLocalData>() {
        @Override
        public MarketCodeLocalData createFromParcel(Parcel source) {
            return new MarketCodeLocalData(source);
        }

        @Override
        public MarketCodeLocalData[] newArray(int size) {
            return new MarketCodeLocalData[size];
        }
    };
}
