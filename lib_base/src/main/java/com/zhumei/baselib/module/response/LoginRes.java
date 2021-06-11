package com.zhumei.baselib.module.response;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginRes implements Parcelable {


    /**
     * market_id : 49
     * merchant_id : 878
     * template_id : 0
     */

    private String market_id;
    private String merchant_id;
    private String template_id;
    private String stall_id;

    public String getStall_id() {
        return stall_id;
    }

    public void setStall_id(String stall_id) {
        this.stall_id = stall_id;
    }

    public String getMarket_id() {
        return market_id;
    }

    public void setMarket_id(String market_id) {
        this.market_id = market_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    @Override
    public String toString() {
        return "LoginRes{" +
                "market_id=" + market_id +
                ", merchant_id=" + merchant_id +
                ", template_id=" + template_id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.market_id);
        dest.writeString(this.merchant_id);
        dest.writeString(this.template_id);
        dest.writeString(this.stall_id);
    }

    public LoginRes() {
    }

    protected LoginRes(Parcel in) {
        this.market_id = in.readString();
        this.merchant_id = in.readString();
        this.template_id = in.readString();
        this.stall_id = in.readString();
    }

    public static final Parcelable.Creator<LoginRes> CREATOR = new Parcelable.Creator<LoginRes>() {
        @Override
        public LoginRes createFromParcel(Parcel source) {
            return new LoginRes(source);
        }

        @Override
        public LoginRes[] newArray(int size) {
            return new LoginRes[size];
        }
    };
}
