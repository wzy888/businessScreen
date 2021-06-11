package com.zhumei.baselib.utils.para;

import android.util.Log;

import org.json.JSONObject;

import java.util.List;


/**
 * trade_data : {"date":"","marketId":"","payStatus":"","payType":"","tradeNo":"",
 * "userId":"","userName":"","trade_detail":[{"unitId":"","subTotal":"","goodsId":"","tradeUnit":"元/kg","id":"","goodsName":"","unitPrice":"","weightPcs":""}]}
 */


public class TradeDataBean {
    /**
     * date :
     * marketId :
     * payStatus :
     * payType :
     * tradeNo :
     * userId :
     * userName :
     * trade_detail : [{"unitId":"","subTotal":"","goodsId":"","tradeUnit":"元/kg","id":"","goodsName":"","unitPrice":"","weightPcs":""}]
     */

    private String date;
    private String marketId;
    private String payStatus;
    private String payType;
    private String tradeNo;
    private String userId;
    private String userName;
    private String total_amount;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    private List<TradeDetailBean> trade_detail;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<TradeDetailBean> getTrade_detail() {
        return trade_detail;
    }

    public void setTrade_detail(List<TradeDetailBean> trade_detail) {
        this.trade_detail = trade_detail;
    }

    public static class TradeDetailBean {
        /**
         * unitId :
         * subTotal :
         * goodsId :
         * tradeUnit : 元/kg
         * id :
         * goodsName :
         * unitPrice :
         * weightPcs :
         */

        private String unitId;
        private String subTotal;
        private String goodsId;
        private String tradeUnit;
        private String id;
        private String goodsName;
        private String unitPrice;
        private String weightPcs;

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getTradeUnit() {
            return tradeUnit;
        }

        public void setTradeUnit(String tradeUnit) {
            this.tradeUnit = tradeUnit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getWeightPcs() {
            return weightPcs;
        }

        public void setWeightPcs(String weightPcs) {
            this.weightPcs = weightPcs;
        }


        public JSONObject getParams() {
            JSONObject obj = new JSONObject();
            try {
//                    "unitId": "",
//                      "subTotal": "",
//                      "goodsId": "",
//                            "tradeUnit": "元/kg",
//                            "id": "",
//                            "goodsName": "",
//                            "unitPrice": "",
//                            "weightPcs": ""

                obj.put("unitId", unitId);
                obj.put("subTotal", subTotal);
                obj.put("goodsId", goodsId);
                obj.put("tradeUnit", tradeUnit);
                obj.put("tradeUnit", tradeUnit);
                obj.put("goodsName", goodsName);
                obj.put("unitPrice", unitPrice);
                obj.put("weightPcs", weightPcs);
            } catch (Exception e) {
                Log.i("Exception: ", e.getMessage());
            }
            return obj;
        }


    }
}



