package com.zhumei.baselib.bean.ble_setting;
/**
 * 切换 挂单
 * */
public class BleSettingRegisterBean {

    /**
     * cmd : 6
     * user : V1
     */
    private int cmd;
    private String user;

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

    @Override
    public String toString() {
        return "BleSettingRegisterBean{" +
                "cmd=" + cmd +
                ", user='" + user + '\'' +
                '}';
    }
}
