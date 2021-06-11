package com.zhumei.baselib.module.response;

public class GoodsPriceBean {
    private String goodsName;

    private String price;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GoodsPriceBean{" +
                "goodsName='" + goodsName + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
