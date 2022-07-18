package com.example.caikaxuetang.responses;


public class VcLoginResponse {


    /**
     * code : 0
     * message :
     * data : {"hasbindWechat":0,"token":"e5faaaa0d86229bd82c57df1723fbae2"}
     */

    private int code;
    private String message;
    /**
     * hasbindWechat : 0
     * token : e5faaaa0d86229bd82c57df1723fbae2
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int hasbindWechat;
        private String token;

        public int getHasbindWechat() {
            return hasbindWechat;
        }

        public void setHasbindWechat(int hasbindWechat) {
            this.hasbindWechat = hasbindWechat;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
