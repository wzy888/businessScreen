package com.zhumei.baselib.utils.para;

public class OrderPayType {

    private String type;

    private String payTitle;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayTitle() {
        return payTitle;
    }

    public void setPayTitle(String payTitle) {
        this.payTitle = payTitle;
    }

    @Override
    public String toString() {
        return "OrderPayType{" +
                "type='" + type + '\'' +
                ", payTitle='" + payTitle + '\'' +
                '}';
    }
}
