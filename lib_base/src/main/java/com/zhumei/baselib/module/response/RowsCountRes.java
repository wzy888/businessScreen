package com.zhumei.baselib.module.response;

public class RowsCountRes {


    /**
     * code : 1
     * msg : 成功
     * data : {"rowcount":"50"}
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
        /**
         * rowcount : 50
         */

        private String rowcount;

        public String getRowcount() {
            return rowcount;
        }

        public void setRowcount(String rowcount) {
            this.rowcount = rowcount;
        }
    }
}
