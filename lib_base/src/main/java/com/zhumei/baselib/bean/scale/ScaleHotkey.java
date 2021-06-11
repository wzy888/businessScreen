package com.zhumei.baselib.bean.scale;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 电子秤热键
 * 热键的索引 默认从3开始 1是其他 PLU1=000 价格为0  2是计件 PLU1=999
 */
@Entity
public class ScaleHotkey {

    /**
     * index : 1
     * PLU1 : 1111
     * PLU2 : 2222
     */

    /**
     * 热键索引
     */
    @Unique
    private String index;
    /**
     * 一个热键对应两个菜品id
     */
    private String PLU1;
    private String PLU2;
    @Keep
    public ScaleHotkey(String index, String PLU1, String PLU2) {
        this.index = index;
        this.PLU1 = PLU1;
        this.PLU2 = PLU2;
    }
    public ScaleHotkey() {
    }
    public String getIndex() {
        return this.index;
    }
    public void setIndex(String index) {
        this.index = index;
    }
    public String getPLU1() {
        return this.PLU1;
    }
    public void setPLU1(String PLU1) {
        this.PLU1 = PLU1;
    }
    public String getPLU2() {
        return this.PLU2;
    }
    public void setPLU2(String PLU2) {
        this.PLU2 = PLU2;
    }

    @Override
    public String toString() {
        return "ScaleHotkey{" +
                "index='" + index + '\'' +
                ", PLU1='" + PLU1 + '\'' +
                ", PLU2='" + PLU2 + '\'' +
                '}';
    }
}
