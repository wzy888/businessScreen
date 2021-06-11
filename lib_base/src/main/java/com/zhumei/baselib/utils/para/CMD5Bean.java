package com.zhumei.baselib.utils.para;

/**
 * 扫付
 * */
public class CMD5Bean {

    /**
     * cmd : 5
     * qr_code : wxp://f2f0Sh4F4bW_DXbFBpdy7WUTM3cuaDd0YLhc
     * pay_type : 1
     * pay_money : 5.34
     */
    private int cmd;
    private String qr_code;
    private int pay_type;
    private double pay_money;
    private String trade_no;


    /***** 新增V3直接支付参数 */
    private String tradeTime="";
    private String unit="";
    private String name="";
    private  double weightPcs;
    private double unitPrice;

    private String item_num="";

    public String getItem_num() {
        return item_num;
    }

    public void setItem_num(String item_num) {
        this.item_num = item_num;
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

    public double getWeightPcs() {
        return weightPcs;
    }

    public void setWeightPcs(double weightPcs) {
        this.weightPcs = weightPcs;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getQrcode() {
        return qr_code;
    }

    public void setQrcode(String qrcode) {
        this.qr_code = qrcode;
    }

    public int getPayType() {
        return pay_type;
    }

    public void setPayType(int payType) {
        this.pay_type = payType;
    }

    public double getPayMoney() {
        return pay_money;
    }

    public void setPayMoney(double payMoney) {
        this.pay_money = payMoney;
    }

    public String getTradeNo() {
        return trade_no;
    }

    public void setTradeNo(String tradeNo) {
        this.trade_no = tradeNo;
    }

    @Override
    public String toString() {
        return "BleSettingShowQrcodeBean{" +
                "cmd=" + cmd +
                ", qrcode='" + qr_code + '\'' +
                ", payType=" + pay_type +
                ", payMoney=" + pay_money +
                ", tradeNo=" + trade_no +
                '}';
    }
}
