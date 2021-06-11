package com.zhumei.baselib.module.response;


public class ActivePayRes {


    /**
     * code : 0
     * msg : 网络正忙，请稍后再试
     * data :
     */

    private int code;
    private String msg;
//    private String data;
    /**
     * data : {"code_url":"https://ibsbjstar.ccb.com.cn/CCBIS/QR?QRCODE=CCB9980009639700051615020","code_status":1,"type":1,"qr_type":2}
     */

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
         * code_url : https://ibsbjstar.ccb.com.cn/CCBIS/QR?QRCODE=CCB9980009639700051615020
         * code_status : 1
         * type : 1
         * qr_type : 2
         */

        private String code_url;
        private int code_status;
        private int type;
        private int qr_type;

        public String getCode_url() {
            return code_url;
        }

        public void setCode_url(String code_url) {
            this.code_url = code_url;
        }

        public int getCode_status() {
            return code_status;
        }

        public void setCode_status(int code_status) {
            this.code_status = code_status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getQr_type() {
            return qr_type;
        }

        public void setQr_type(int qr_type) {
            this.qr_type = qr_type;
        }
    }
}
