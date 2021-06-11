package com.zhumei.baselib.bean.commercial_info;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * CommercialInfoScaleGoods
 */
public class ScaleGoods implements Parcelable {

    /**
     * name : 菠菜
     * unit : kg
     * price : 5
     * mem_price : 15
     * plu_type : 0
     * min_price : 4
     * disCount : 1
     * change_price : 1
     * PLU : 1111
     * self_code : 1111
     */
    /**
     * 商品编码
     */
    private String PLU;
    private String name;
    private String unit;
    private double price;
    /**
     * 会员价
     */
    @JSONField(name = "mem_price")
    private double mem_price;
    /**
     * 最低售价
     */
    @JSONField(name = "min_price")
    private double min_price;
    /**
     * 计量方式 0计重 1计数 2计数取重
     */
    @JSONField(name = "plu_type")
    private String plu_type;
    /**
     * 是否允许折扣 0不予许折扣 1允许折扣
     */
    private String discount;
    /**
     * 是否允许改价 0不允许改价 1允许改价
     */
    @JSONField(name = "change_price")
    private String change_price;

    /**
     * 自编码
     */
    @JSONField(name = "self_code")
    private String self_code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMemPrice() {
        return mem_price;
    }

    public void setMemPrice(double memPrice) {
        this.mem_price = memPrice;
    }

    public String getPluType() {
        return plu_type;
    }

    public void setPluType(String pluType) {
        this.plu_type = pluType;
    }

    public double getMinPrice() {
        return min_price;
    }

    public void setMinPrice(double minPrice) {
        this.min_price = minPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getChangePrice() {
        return change_price;
    }

    public void setChangePrice(String changePrice) {
        this.change_price = changePrice;
    }

    public String getPLU() {
        return PLU;
    }

    public void setPLU(String PLU) {
        this.PLU = PLU;
    }

    public String getSelfCode() {
        return self_code;
    }

    public void setSelfCode(String selfCode) {
        this.self_code = selfCode;
    }

    @Override
    public String toString() {
        return "ScaleGoods{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", memPrice=" + mem_price +
                ", minPrice=" + min_price +
                ", pluType='" + plu_type + '\'' +
                ", discount='" + discount + '\'' +
                ", changePrice='" + change_price + '\'' +
                ", PLU='" + PLU + '\'' +
                ", selfCode='" + self_code + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PLU);
        dest.writeString(this.name);
        dest.writeString(this.unit);
        dest.writeDouble(this.price);
        dest.writeDouble(this.mem_price);
        dest.writeDouble(this.min_price);
        dest.writeString(this.plu_type);
        dest.writeString(this.discount);
        dest.writeString(this.change_price);
        dest.writeString(this.self_code);
    }

    public ScaleGoods() {
    }

    protected ScaleGoods(Parcel in) {
        this.PLU = in.readString();
        this.name = in.readString();
        this.unit = in.readString();
        this.price = in.readDouble();
        this.mem_price = in.readDouble();
        this.min_price = in.readDouble();
        this.plu_type = in.readString();
        this.discount = in.readString();
        this.change_price = in.readString();
        this.self_code = in.readString();
    }

    public static final Parcelable.Creator<ScaleGoods> CREATOR = new Parcelable.Creator<ScaleGoods>() {
        @Override
        public ScaleGoods createFromParcel(Parcel source) {
            return new ScaleGoods(source);
        }

        @Override
        public ScaleGoods[] newArray(int size) {
            return new ScaleGoods[size];
        }
    };
}
