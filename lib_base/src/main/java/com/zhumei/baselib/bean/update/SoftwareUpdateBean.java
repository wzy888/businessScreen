package com.zhumei.baselib.bean.update;

import com.alibaba.fastjson.annotation.JSONField;

public class SoftwareUpdateBean {

    private String address;
    @JSONField(name = "package_name")
    private String packageName;
    @JSONField(name = "version_code")
    private int versionCode;
    @JSONField(name = "version_name")
    private String versionName;
    @JSONField(name = "event_type")
    private int eventType;
    @JSONField(name = "market_id")
    private String marketId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    @Override
    public String toString() {
        return "SoftwareUpdateBean{" +
                "address='" + address + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", eventType=" + eventType +
                ", marketId=" + marketId +
                '}';
    }
}
