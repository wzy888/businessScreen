package com.zhumei.baselib.bean.commercial_info;

import com.alibaba.fastjson.annotation.JSONField;

public class CommercialInfoRealTimeWelcomeNewsBean{
    /**
     * post_title : 市场交易须知
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
        return "CommercialInfoRealTimeWelcomeNewsBean{" +
                "postTitle='" + postTitle + '\'' +
                '}';
    }
}
