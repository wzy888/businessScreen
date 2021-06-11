package com.zhumei.baselib.bean.ble_setting;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 结账
 * */
public class BleSettingCheckOutBean {

    /**
     * cmd : 3
     * user : V1
     * pay_money : 3.16
     * pay_type : 0
     * trade_no : A2A218121600001
     * trade_time : 2019/3/5 7:18:45
     * unit : kg
     * name : 青菜
     * unit_price : 2
     * weight_pcs : 0.065
     * plu : 1
     * scale_num : dm011
     * stall_num : dm011
     */
    private int cmd;
    private String user;
    @JSONField(name = "pay_money")
    private double payMoney;
    @JSONField(name = "pay_type")
    private int payType;
    @JSONField(name = "trade_no")
    private String tradeNo;
    @JSONField(name = "trade_time")
    private String tradeTime;
    @JSONField(name = "unit")
    private String unit;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "unit_price")
    private double unitPrice;
    @JSONField(name = "weight_pcs")
    private double weightPcs;
    private int plu;
    @JSONField(name = "scale_num")
    private String scaleNum;
    @JSONField(name = "stall_num")
    private String stallNum;

    @JSONField(name = "item_num")
    private String item_num;

    public String getItem_num() {
        return item_num;
    }

    public void setItem_num(String item_num) {
        this.item_num = item_num;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getWeightPcs() {
        return weightPcs;
    }

    public void setWeightPcs(double weightPcs) {
        this.weightPcs = weightPcs;
    }

    public int getPlu() {
        return plu;
    }

    public void setPlu(int plu) {
        this.plu = plu;
    }

    public String getScaleNum() {
        return scaleNum;
    }

    public void setScaleNum(String scaleNum) {
        this.scaleNum = scaleNum;
    }

    public String getStallNum() {
        return stallNum;
    }

    public void setStallNum(String stallNum) {
        this.stallNum = stallNum;
    }

    @Override
    public String toString() {
        return "BleSettingCheckOutBean{" +
                "cmd=" + cmd +
                ", user='" + user + '\'' +
                ", payMoney=" + payMoney +
                ", payType=" + payType +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeTime=" + tradeTime +
                ", unit='" + unit + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", weightPcs=" + weightPcs +
                ", plu=" + plu +
                ", scaleNum='" + scaleNum + '\'' +
                ", stallNum='" + stallNum + '\'' +
                '}';
    }
}
