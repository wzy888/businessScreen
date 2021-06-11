package com.zhumei.baselib.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ImgBean {


    /**
     * status : 1
     * img_url : ["http://bbb.zhumei.net/data/upload/market_26/20190728/1564314788.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564314824.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564314915.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564314948.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564314965.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564314982.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564315109.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564318073.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564318112.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564318147.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564318261.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564318354.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564319676.jpeg","http://bbb.zhumei.net/data/upload/market_26/20190728/1564319703.jpeg"]
     */
    private int status;
    @JSONField(name = "img_url")
    private List<String> imgUrl;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "ImgBean{" +
                "status=" + status +
                ", imgUrl=" + imgUrl +
                '}';
    }
}


