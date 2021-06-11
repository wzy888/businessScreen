package com.zhumei.baselib.bean.commercial_info;

import com.alibaba.fastjson.annotation.JSONField;

public class CommercialInfoRealTimeDiscountNewsBean{

    /**
     * post_title : 食品安全知识宣传资料
     */
    @JSONField(name = "post_title")
    private String postTitle;

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    @Override
    public String toString() {
        return "CommercialInfoRealTimeDiscountNewsBean{" +
                "postTitle='" + postTitle + '\'' +
                '}';
    }
}