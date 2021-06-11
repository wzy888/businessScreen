package com.zhumei.baselib.bean.guide;

import java.io.Serializable;

public class GuidePicBean implements Serializable {
    private int pic;

    public GuidePicBean(int pic) {
        this.pic = pic;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "GuidePicBean{" +
                "pic=" + pic +
                '}';
    }
}
