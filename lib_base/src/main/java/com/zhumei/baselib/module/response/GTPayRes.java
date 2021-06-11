package com.zhumei.baselib.module.response;

public class GTPayRes {


    /**
     * event_type : 4
     * trade_no : 12312321010665892
     * amount : 0.01
     * pay_status : 1
     */

    private String event_type;
    private String trade_no;
    private String amount;
    private int pay_status;

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }
}
