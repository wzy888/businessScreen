package com.zhumei.baselib.bean.commercial_info;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Objects;

/**
 * CommercialInfoScaleHotkeys
 */
public class ScaleHotkeyCommInfo implements Parcelable {

    /**
     * 热键的索引
     */
    private String index;
    /**
     * 菜品id 一个热键对应两个菜品id 使用切换键切换
     */
    private String PLU1;
    private String PLU2;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPLU1() {
        return PLU1;
    }

    public void setPLU1(String PLU1) {
        this.PLU1 = PLU1;
    }

    public String getPLU2() {
        return PLU2;
    }

    public void setPLU2(String PLU2) {
        this.PLU2 = PLU2;
    }

    @Override
    public String toString() {
        return "ScaleHotkey{" +
                "index='" + index + '\'' +
                ", PLU1='" + PLU1 + '\'' +
                ", PLU2='" + PLU2 + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.index);
        dest.writeString(this.PLU1);
        dest.writeString(this.PLU2);
    }

    public ScaleHotkeyCommInfo() {
    }

    protected ScaleHotkeyCommInfo(Parcel in) {
        this.index = in.readString();
        this.PLU1 = in.readString();
        this.PLU2 = in.readString();
    }

    public static final Parcelable.Creator<ScaleHotkeyCommInfo> CREATOR = new Parcelable.Creator<ScaleHotkeyCommInfo>() {
        @Override
        public ScaleHotkeyCommInfo createFromParcel(Parcel source) {
            return new ScaleHotkeyCommInfo(source);
        }

        @Override
        public ScaleHotkeyCommInfo[] newArray(int size) {
            return new ScaleHotkeyCommInfo[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaleHotkeyCommInfo that = (ScaleHotkeyCommInfo) o;
        return Objects.equals(index, that.index) &&
                Objects.equals(PLU1, that.PLU1) &&
                Objects.equals(PLU2, that.PLU2);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(index, PLU1, PLU2);
    }
}
