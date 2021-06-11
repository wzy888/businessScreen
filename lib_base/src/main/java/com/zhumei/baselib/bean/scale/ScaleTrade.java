package com.zhumei.baselib.bean.scale;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 电子秤交易数据
 */
@Entity
public class ScaleTrade {

    /**
     * 自增id
     */
    @Id(autoincrement = true)
    private Long id;
    /**
     * 市场id
     */
    private String marketId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * V1 V2(表示的是挂单称重数据)
     * V3(表示的是直接称重数据 没有挂单)
     * “ ”(表示的是暗存数据 没有交易成功)
     */
    private String user;
    /**
     * 商品单价
     */
    private double unitPrice;
    /**
     * 商品重量
     */
    private double weightPcs;
    /**
     * 商品总价
     */
    private double totPrice;
    /**
     * PLU
     */
    private int plu;
    /**
     * 交易单号
     */
    private String tradeNo;
    /**
     * 秤摊位号(如果没有设置 则为空)
     */
    private String scaleStallNum;
    /**
     * 屏摊位号(从屏上获取的 屏正常显示的话 屏摊位号数据是有的)
     */
    private String stallNum;
    /**
     * 秤号(如果没有设置 则为空)
     */
    private String scaleNum;
    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户Id
     */
    private String merchantId;


    /**
     * 交易时间
     */
    private String tradeTime;
    /**
     * 称重单位
     */
    private String tradeUnit;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 为正常销售数据0或是暗存数据1
     */
    private int iDataType;
    /**
     * 未上传0或是上传1
     */
    private int isUpdate;
    /**
     * 数据来源0表示龙飞秤蓝牙上传数据
     */
    private int tradeDataSources;

//    @Generated(hash = 450887769)

    @Keep
    public ScaleTrade(Long id, String marketId, String goodsName, String user,
                      double unitPrice, double weightPcs, double totPrice, int plu,
                      String tradeNo, String scaleStallNum, String stallNum, String scaleNum,
                      String merchantName, String tradeTime, String tradeUnit, String payType,
                      int iDataType, int isUpdate, int tradeDataSources,String merchantId) {
        this.id = id;
        this.marketId = marketId;
        this.goodsName = goodsName;
        this.user = user;
        this.unitPrice = unitPrice;
        this.weightPcs = weightPcs;
        this.totPrice = totPrice;
        this.plu = plu;
        this.tradeNo = tradeNo;
        this.scaleStallNum = scaleStallNum;
        this.stallNum = stallNum;
        this.scaleNum = scaleNum;
        this.merchantName = merchantName;
        this.tradeTime = tradeTime;
        this.tradeUnit = tradeUnit;
        this.payType = payType;
        this.iDataType = iDataType;
        this.isUpdate = isUpdate;
        this.tradeDataSources = tradeDataSources;
        this.merchantId = merchantId;
    }

//    @Generated(hash = 1380686175)
    @Keep
    public ScaleTrade() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMarketId() {
        return this.marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getWeightPcs() {
        return this.weightPcs;
    }

    public void setWeightPcs(double weightPcs) {
        this.weightPcs = weightPcs;
    }

    public double getTotPrice() {
        return this.totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }

    public int getPlu() {
        return this.plu;
    }

    public void setPlu(int plu) {
        this.plu = plu;
    }

    public String getTradeNo() {
        return this.tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getScaleStallNum() {
        return this.scaleStallNum;
    }

    public void setScaleStallNum(String scaleStallNum) {
        this.scaleStallNum = scaleStallNum;
    }

    public String getStallNum() {
        return this.stallNum;
    }

    public void setStallNum(String stallNum) {
        this.stallNum = stallNum;
    }

    public String getScaleNum() {
        return this.scaleNum;
    }

    public void setScaleNum(String scaleNum) {
        this.scaleNum = scaleNum;
    }

    public String getMerchantName() {
        return this.merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTradeTime() {
        return this.tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeUnit() {
        return this.tradeUnit;
    }

    public void setTradeUnit(String tradeUnit) {
        this.tradeUnit = tradeUnit;
    }

    public String getPayType() {
        return this.payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getIDataType() {
        return this.iDataType;
    }

    public void setIDataType(int iDataType) {
        this.iDataType = iDataType;
    }

    public int getIsUpdate() {
        return this.isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public int getTradeDataSources() {
        return this.tradeDataSources;
    }

    public void setTradeDataSources(int tradeDataSources) {
        this.tradeDataSources = tradeDataSources;
    }

    @Override
    public String toString() {
        return "ScaleTrade{" +
                "id=" + id +
                ", marketId='" + marketId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", user='" + user + '\'' +
                ", unitPrice=" + unitPrice +
                ", weightPcs=" + weightPcs +
                ", totPrice=" + totPrice +
                ", plu=" + plu +
                ", tradeNo='" + tradeNo + '\'' +
                ", scaleStallNum='" + scaleStallNum + '\'' +
                ", stallNum='" + stallNum + '\'' +
                ", scaleNum='" + scaleNum + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", tradeTime='" + tradeTime + '\'' +
                ", tradeUnit='" + tradeUnit + '\'' +
                ", payType='" + payType + '\'' +
                ", iDataType=" + iDataType +
                ", isUpdate=" + isUpdate +
                ", tradeDataSources=" + tradeDataSources +
                '}';
    }
}
