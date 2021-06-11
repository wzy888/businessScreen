package com.zhumei.baselib.bean.ble_setting;


public class BleCmd2Bean {

    /**
     * cmd : 2
     * user : V1
     * is_refund : 0
     * is_delete : 0
     * list_index : 4
     * plu : 1
     * unit_price : 2
     * unit : kg
     * weight_pcs : 0.065
     * tot_price : 0.13
     * name : 青菜
     * item_num : -1
     * trade_no : A2A218121600001
     * trade_time : 2019/3/5 7:18:45
     * scale_num : dm011
     * stall_num : dm011
     */
    private int cmd;
    private String user;
//    @JSONField(name = "is_refund")
    private int is_refund;
//    @JSONField(name = "is_delete")
    private int is_delete;
//    @JSONField(name = "list_index")
    private int list_index;
    private int plu;
//    @JSONField(name = "unit_price")
    private double unit_price;
    private String unit;
//    @JSONField(name = "weight_pcs")
    private double weight_pcs;
//    @JSONField(name = "tot_price")
    private double tot_price;
    private String name;
//    @JSONField(name = "item_num")
    private String item_num;
//    @JSONField(name = "trade_no")
    private String trade_no;
//    @JSONField(name = "trade_time")
    private String trade_time;
//    @JSONField(name = "scale_num")
    private String scale_num;
//    @JSONField(name = "stall_num")
    private String stall_num;

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

    public int getIsRefund() {
        return is_refund;
    }

    public void setIsRefund(int isRefund) {
        this.is_refund = isRefund;
    }

    public int getIsDelete() {
        return is_delete;
    }

    public void setIsDelete(int isDelete) {
        this.is_delete = isDelete;
    }

    public int getListIndex() {
        return list_index;
    }

    public void setListIndex(int listIndex) {
        this.list_index = listIndex;
    }

    public int getPlu() {
        return plu;
    }

    public void setPlu(int plu) {
        this.plu = plu;
    }

    public double getUnitPrice() {
        return unit_price;
    }

    public void setUnitPrice(double unitPrice) {
        this.unit_price = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getWeightPcs() {
        return weight_pcs;
    }

    public void setWeightPcs(double weightPcs) {
        this.weight_pcs = weightPcs;
    }

    public double getTotPrice() {
        return tot_price;
    }

    public void setTotPrice(double totPrice) {
        this.tot_price = totPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemNum() {
        return item_num;
    }

    public void setItemNum(String itemNum) {
        this.item_num = itemNum;
    }

    public String getTradeNo() {
        return trade_no;
    }

    public void setTradeNo(String tradeNo) {
        this.trade_no = tradeNo;
    }

    public String getTradeTime() {
        return trade_time;
    }

    public void setTradeTime(String tradeTime) {
        this.trade_time = tradeTime;
    }

    public String getScaleNum() {
        return scale_num;
    }

    public void setScaleNum(String scaleNum) {
        this.scale_num = scaleNum;
    }

    public String getStallNum() {
        return stall_num;
    }

    public void setStallNum(String stallNum) {
        this.stall_num = stallNum;
    }

    @Override
    public String toString() {
        return "BleSettingAccumulativeReductionCommodityDataBean{" +
                "cmd=" + cmd +
                ", user='" + user + '\'' +
                ", isRefund=" + is_refund +
                ", isDelete=" + is_delete +
                ", listIndex=" + list_index +
                ", plu=" + plu +
                ", unitPrice=" + unit_price +
                ", unit='" + unit + '\'' +
                ", weightPcs=" + weight_pcs +
                ", totPrice=" + tot_price +
                ", name='" + name + '\'' +
                ", itemNum='" + item_num + '\'' +
                ", tradeNo='" + trade_no + '\'' +
                ", tradeTime=" + trade_time +
                ", scaleNum='" + scale_num + '\'' +
                ", stallNum='" + stall_num + '\'' +
                '}';
    }
}
