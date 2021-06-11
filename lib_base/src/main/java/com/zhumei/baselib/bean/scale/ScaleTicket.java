package com.zhumei.baselib.bean.scale;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * 电子秤小票数据
 */
@Entity
public class ScaleTicket {

    /**
     * 题头/题尾标志  0题尾 1题头
     */
    private String ticFlg;
    /**
     * 对齐标志  0居中 1居左 2居右
     */
    private String aliagnFlg;
    /**
     * 打印标志  0:正常;1:倍高;2:倍宽;3倍高倍宽
     */
    private String prtFlg;
    /**
     * 打印数据  打印内容，不超过32个字节
     */
    private String prtData;
    @Keep
    public ScaleTicket(String ticFlg, String aliagnFlg, String prtFlg,
                       String prtData) {
        this.ticFlg = ticFlg;
        this.aliagnFlg = aliagnFlg;
        this.prtFlg = prtFlg;
        this.prtData = prtData;
    }
    public ScaleTicket() {
    }
    public String getTicFlg() {
        return this.ticFlg;
    }
    public void setTicFlg(String ticFlg) {
        this.ticFlg = ticFlg;
    }
    public String getAliagnFlg() {
        return this.aliagnFlg;
    }
    public void setAliagnFlg(String aliagnFlg) {
        this.aliagnFlg = aliagnFlg;
    }
    public String getPrtFlg() {
        return this.prtFlg;
    }
    public void setPrtFlg(String prtFlg) {
        this.prtFlg = prtFlg;
    }
    public String getPrtData() {
        return this.prtData;
    }
    public void setPrtData(String prtData) {
        this.prtData = prtData;
    }

    @Override
    public String toString() {
        return "ScaleTicket{" +
                "ticFlg='" + ticFlg + '\'' +
                ", aliagnFlg='" + aliagnFlg + '\'' +
                ", prtFlg='" + prtFlg + '\'' +
                ", prtData='" + prtData + '\'' +
                '}';
    }
}
