package com.zhumei.baselib.bean.ble_setting;

/**
 * 称重数据
 * */
public class BleSettingWeightingBean {

    /**
     * cmd : 7
     * weight : 1
     */
    private int cmd;
    private double weight;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "BleSettingWeightingBean{" +
                "cmd=" + cmd +
                ", weight=" + weight +
                '}';
    }
}
