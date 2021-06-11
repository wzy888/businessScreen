package com.zhumei.baselib.module.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhumei.baselib.bean.commercial_info.ScaleHotkeyCommInfo;

import java.util.List;

public class GuideHotKeyRes implements Parcelable {


    private List<ScaleHotkeyCommInfo> hot_key;

    public List<ScaleHotkeyCommInfo> getHot_key() {
        return hot_key;
    }

    public void setHot_key(List<ScaleHotkeyCommInfo> hot_key) {
        this.hot_key = hot_key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.hot_key);
    }

    public GuideHotKeyRes() {
    }

    protected GuideHotKeyRes(Parcel in) {
        this.hot_key = in.createTypedArrayList(ScaleHotkeyCommInfo.CREATOR);
    }

    public static final Creator<GuideHotKeyRes> CREATOR = new Creator<GuideHotKeyRes>() {
        @Override
        public GuideHotKeyRes createFromParcel(Parcel source) {
            return new GuideHotKeyRes(source);
        }

        @Override
        public GuideHotKeyRes[] newArray(int size) {
            return new GuideHotKeyRes[size];
        }
    };

    @Override
    public String toString() {
        return "GuideHotKeyRes{" +
                "hot_key=" + hot_key +
                '}';
    }
}
