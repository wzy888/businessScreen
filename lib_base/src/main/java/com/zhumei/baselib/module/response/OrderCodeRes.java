package com.zhumei.baselib.module.response;


public class OrderCodeRes {


    /**
     * code : 1
     * msg : 请求成功
     * data : {"code_url":"https://qr.95516.com/00010048/unifiedNative?token=727ae145cf555760f35c44d096bb5b43&target=qfs&unit=shw1"}
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
         * code_url : https://qr.95516.com/00010048/unifiedNative?token=727ae145cf555760f35c44d096bb5b43&target=qfs&unit=shw1
         */

        private String code_url;

        public String getCode_url() {
            return code_url;
        }

        public void setCode_url(String code_url) {
            this.code_url = code_url;
        }
    }
}
