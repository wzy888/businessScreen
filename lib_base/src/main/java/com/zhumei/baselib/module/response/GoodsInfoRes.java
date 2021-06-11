package com.zhumei.baselib.module.response;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodsInfoRes implements Parcelable {


    /**
     * PLU : 1
     * price : 13.00
     * name : 油菜
     * unit : kg
     * mem_price : 13.00
     * min_price : 0
     * plu_type : 0
     * discount : 0
     * change_price : 1
     * self_code : 1
     */

    private int PLU;
    private double price;
    private String name;
    private String unit;
    private String mem_price;
    private int min_price;
    private int plu_type;
    private int discount;
    private int change_price;
    private int self_code;

    public int getPLU() {
        return PLU;
    }

    public void setPLU(int PLU) {
        this.PLU = PLU;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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

    public String getMem_price() {
        return mem_price;
    }

    public void setMem_price(String mem_price) {
        this.mem_price = mem_price;
    }

    public int getMin_price() {
        return min_price;
    }

    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

    public int getPlu_type() {
        return plu_type;
    }

    public void setPlu_type(int plu_type) {
        this.plu_type = plu_type;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getChange_price() {
        return change_price;
    }

    public void setChange_price(int change_price) {
        this.change_price = change_price;
    }

    public int getSelf_code() {
        return self_code;
    }

    public void setSelf_code(int self_code) {
        this.self_code = self_code;
    }

    @Override
    public String toString() {
        return "GoodsInfoRes{" +
                "PLU=" + PLU +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", mem_price='" + mem_price + '\'' +
                ", min_price=" + min_price +
                ", plu_type=" + plu_type +
                ", discount=" + discount +
                ", change_price=" + change_price +
                ", self_code=" + self_code +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.PLU);
        dest.writeDouble(this.price);
        dest.writeString(this.name);
        dest.writeString(this.unit);
        dest.writeString(this.mem_price);
        dest.writeInt(this.min_price);
        dest.writeInt(this.plu_type);
        dest.writeInt(this.discount);
        dest.writeInt(this.change_price);
        dest.writeInt(this.self_code);
    }

    public GoodsInfoRes() {
    }

    protected GoodsInfoRes(Parcel in) {
        this.PLU = in.readInt();
        this.price = in.readDouble();
        this.name = in.readString();
        this.unit = in.readString();
        this.mem_price = in.readString();
        this.min_price = in.readInt();
        this.plu_type = in.readInt();
        this.discount = in.readInt();
        this.change_price = in.readInt();
        this.self_code = in.readInt();
    }

    public static final Creator<GoodsInfoRes> CREATOR = new Creator<GoodsInfoRes>() {
        @Override
        public GoodsInfoRes createFromParcel(Parcel source) {
            return new GoodsInfoRes(source);
        }

        @Override
        public GoodsInfoRes[] newArray(int size) {
            return new GoodsInfoRes[size];
        }
    };
}
