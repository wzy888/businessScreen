package com.zhumei.baselib.bean.ble_setting;

public class BleSettingAllClearBean {

    /**
     * cmd : 4
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
        return "BleSettingAllClearBean{" +
                "cmd=" + cmd +
                ", user='" + user + '\'' +
                '}';
    }
}
