package com.zhumei.baselib.bean.ble_setting;

//import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
/**
 * 实时称重 数据
 * */
public class BleSettingRealTimeWeightingBean {

    /**
     * cmd : 1
     * unit_price : 0
     * unit : kg
     * weight_pcs : 0
     * trade_no : A2A218121600001
     * trade_time : 2019/3/5 7:18:45
     */
    private int cmd;
//    @JSONField(name = "unit_price")
    private BigDecimal unit_price;
//    @JSONField(name = "weight_pcs")
    private BigDecimal weight_pcs;
//    @JSONField(name = "tot_price")
    private BigDecimal tot_price;
    private String unit;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public BigDecimal getUnitPrice() {
        return unit_price;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unit_price = unitPrice;
    }

    public BigDecimal getWeightPcs() {
        return weight_pcs;
    }

    public void setWeightPcs(BigDecimal weightPcs) {
        this.weight_pcs = weightPcs;
    }

    public BigDecimal getTotPrice() {
        return tot_price;
    }

    public void setTotPrice(BigDecimal totPrice) {
        this.tot_price = totPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "BleSettingRealTimeWeightingBean{" +
                "cmd=" + cmd +
                ", unitPrice=" + unit_price +
                ", weightPcs=" + weight_pcs +
                ", totPrice=" + tot_price +
                ", unit='" + unit + '\'' +
                '}';
    }
}
