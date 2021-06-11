package com.zhumei.baselib.module.para;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 订单 参数
 */
public class OrderParams {

    private String goodsName;
    private String goodsId;
    private String subTotal;
    private String weightPcs;
    private String tradeUnit;
    private String unitPrice;
    private String unitId;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getWeightPcs() {
        return weightPcs;
    }

    public void setWeightPcs(String weightPcs) {
        this.weightPcs = weightPcs;
    }

    public String getTradeUnit() {
        return tradeUnit;
    }

    public void setTradeUnit(String tradeUnit) {
        this.tradeUnit = tradeUnit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public JSONObject  getParams() {
        JSONObject obj = new JSONObject();
        try {
//     String goodsName;
//    private String goodsId;
//    private String subTotal;
//    private String weightPcs;
//    private String tradeUnit;
//    private String unitPrice;
//    private String unitId;

            obj.put("goodsName", goodsName);
            obj.put("goodsId", goodsId);
            obj.put("subTotal", subTotal);
            obj.put("weightPcs", weightPcs);
            obj.put("tradeUnit", tradeUnit);
            obj.put("unitPrice", unitPrice);
            obj.put("unitId", unitId);
        } catch (Exception e) {
            Log.i("Exception: ", e.getMessage());
        }
        return obj;
    }


    @Override
    public String toString() {
        return "OrderParams{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", subTotal='" + subTotal + '\'' +
                ", weightPcs='" + weightPcs + '\'' +
                ", tradeUnit='" + tradeUnit + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", unitId='" + unitId + '\'' +
                '}';
    }
}
