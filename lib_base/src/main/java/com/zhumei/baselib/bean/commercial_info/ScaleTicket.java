package com.zhumei.baselib.bean.commercial_info;

import android.os.Parcel;
import android.os.Parcelable;

public class ScaleTicket implements Parcelable {

    /**
     * ticFlg : 0
     * aliagnFlg : 0
     * prtFlg : 0
     * prtData : 乌镇农贸市场
     */

    /**
     * 题头/题尾标志  0题尾 1题头
     */
    private String ticFlg;
    /**
     * 对齐标志  0居中 1居左 2居右
     */
    private String aliagnFlg;
    /**
     * 打印标志  0:正常;1:倍高;2:倍宽;3倍高倍宽
     */
    private String prtFlg;
    /**
     * 打印数据  打印内容，不超过32个字节
     */
    private String prtData;

    public String getTicFlg() {
        return ticFlg;
    }

    public void setTicFlg(String ticFlg) {
        this.ticFlg = ticFlg;
    }

    public String getAliagnFlg() {
        return aliagnFlg;
    }

    public void setAliagnFlg(String aliagnFlg) {
        this.aliagnFlg = aliagnFlg;
    }

    public String getPrtFlg() {
        return prtFlg;
    }

    public void setPrtFlg(String prtFlg) {
        this.prtFlg = prtFlg;
    }

    public String getPrtData() {
        return prtData;
    }

    public void setPrtData(String prtData) {
        this.prtData = prtData;
    }

    @Override
    public String toString() {
        return "ScaleTicket{" +
                "ticFlg='" + ticFlg + '\'' +
                ", aliagnFlg='" + aliagnFlg + '\'' +
                ", prtFlg='" + prtFlg + '\'' +
                ", prtData='" + prtData + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ticFlg);
        dest.writeString(this.aliagnFlg);
        dest.writeString(this.prtFlg);
        dest.writeString(this.prtData);
    }

    public ScaleTicket() {
    }

    protected ScaleTicket(Parcel in) {
        this.ticFlg = in.readString();
        this.aliagnFlg = in.readString();
        this.prtFlg = in.readString();
        this.prtData = in.readString();
    }

    public static final Parcelable.Creator<ScaleTicket> CREATOR = new Parcelable.Creator<ScaleTicket>() {
        @Override
        public ScaleTicket createFromParcel(Parcel source) {
            return new ScaleTicket(source);
        }

        @Override
        public ScaleTicket[] newArray(int size) {
            return new ScaleTicket[size];
        }
    };
}
