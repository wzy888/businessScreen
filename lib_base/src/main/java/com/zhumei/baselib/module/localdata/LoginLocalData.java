package com.zhumei.baselib.module.localdata;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhumei.baselib.module.response.LoginRes;


public class LoginLocalData implements Parcelable {

    private LoginRes loginRes;

    public LoginLocalData() {

    }

    public LoginRes getLoginRes() {
        return loginRes;
    }

    public void setLoginRes(LoginRes loginRes) {
        this.loginRes = loginRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.loginRes, flags);
    }

    protected LoginLocalData(Parcel in) {
        this.loginRes = in.readParcelable(LoginRes.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginLocalData> CREATOR = new Parcelable.Creator<LoginLocalData>() {
        @Override
        public LoginLocalData createFromParcel(Parcel source) {
            return new LoginLocalData(source);
        }

        @Override
        public LoginLocalData[] newArray(int size) {
            return new LoginLocalData[size];
        }
    };
}
