package com.zhumei.baselib.module.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MarketCodeRes implements Parcelable {


    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
    }

    public MarketCodeRes() {
    }

    protected MarketCodeRes(Parcel in) {
        this.code = in.readString();
    }

    public static final Parcelable.Creator<MarketCodeRes> CREATOR = new Parcelable.Creator<MarketCodeRes>() {
        @Override
        public MarketCodeRes createFromParcel(Parcel source) {
            return new MarketCodeRes(source);
        }

        @Override
        public MarketCodeRes[] newArray(int size) {
            return new MarketCodeRes[size];
        }
    };
}
