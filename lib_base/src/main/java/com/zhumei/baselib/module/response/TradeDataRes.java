package com.zhumei.baselib.module.response;

import java.util.List;

public class TradeDataRes {


    /**
     * code : 1
     * msg : 查询成功
     * data : {"search":["1","2","3","4","5","6","7","8","9"]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> search;

        public List<String> getSearch() {
            return search;
        }

        public void setSearch(List<String> search) {
            this.search = search;
        }
    }
}
