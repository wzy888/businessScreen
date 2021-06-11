package com.zhumei.baselib.bean.push;

import com.alibaba.fastjson.annotation.JSONField;

public class ScreenShotPushBean {

    /**
     * event_type : 13
     * screen_shot : 1
     */
    @JSONField(name = "event_type")
    private String eventType;
    @JSONField(name = "screen_shot")
    private String screenShot;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(String screenShot) {
        this.screenShot = screenShot;
    }

    @Override
    public String toString() {
        return "ScreenShotPushBean{" +
                "eventType='" + eventType + '\'' +
                ", screenShot='" + screenShot + '\'' +
                '}';
    }
}
