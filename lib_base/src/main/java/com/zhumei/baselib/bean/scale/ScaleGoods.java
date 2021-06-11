package com.zhumei.baselib.bean.scale;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 电子秤商品
 */
@Entity
public class ScaleGoods {

    /**
     * name : 菠菜
     * unit : kg
     * price : 5
     * mem_price : 15
     * plu_type : 0
     * min_price : 4
     * enable_disCount : 1
     * change_price : 1
     * PLU : 1111
     * self_code : 1111
     */

    private String name;
    private String unit;
    private double price;
    /**
     * 会员价
     */
    @JSONField(name = "mem_price")
    private double memPrice;
    /**
     * 最低售价
     */
    @JSONField(name = "min_price")
    private double minPrice;
    /**
     * 计量方式 0计重 1计数 2计数取重
     */
    @JSONField(name = "plu_type")
    private String pluType;
    /**
     * 是否允许折扣 0不予许折扣 1允许折扣
     */
    private String discount;
    /**
     * 是否允许改价 0不允许改价 1允许改价
     */
    @JSONField(name = "change_price")
    private String changePrice;
    /**
     * 商品编码
     */
    @Unique
    private String PLU;
    /**
     * 自编码
     */
    @JSONField(name = "self_code")
    private String selfCode;
    @Keep
    public ScaleGoods(String name, String unit, double price, double memPrice,
                      double minPrice, String pluType, String discount, String changePrice,
                      String PLU, String selfCode) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.memPrice = memPrice;
        this.minPrice = minPrice;
        this.pluType = pluType;
        this.discount = discount;
        this.changePrice = changePrice;
        this.PLU = PLU;
        this.selfCode = selfCode;
    }
    public ScaleGoods() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getMemPrice() {
        return this.memPrice;
    }
    public void setMemPrice(double memPrice) {
        this.memPrice = memPrice;
    }
    public String getPluType() {
        return this.pluType;
    }
    public void setPluType(String pluType) {
        this.pluType = pluType;
    }
    public double getMinPrice() {
        return this.minPrice;
    }
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
    public String getDiscount() {
        return this.discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getChangePrice() {
        return this.changePrice;
    }
    public void setChangePrice(String changePrice) {
        this.changePrice = changePrice;
    }
    public String getPLU() {
        return this.PLU;
    }
    public void setPLU(String PLU) {
        this.PLU = PLU;
    }
    public String getSelfCode() {
        return this.selfCode;
    }
    public void setSelfCode(String selfCode) {
        this.selfCode = selfCode;
    }

    @Override
    public String toString() {
        return "ScaleGoods{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", memPrice=" + memPrice +
                ", minPrice=" + minPrice +
                ", pluType='" + pluType + '\'' +
                ", discount='" + discount + '\'' +
                ", changePrice='" + changePrice + '\'' +
                ", PLU='" + PLU + '\'' +
                ", selfCode='" + selfCode + '\'' +
                '}';
    }
}