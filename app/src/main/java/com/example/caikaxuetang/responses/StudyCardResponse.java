package com.example.caikaxuetang.responses;

public class StudyCardResponse {
    /**
     * code : 0
     * message :
     * data : https://qiniu.xicaishe.com/202257604728176105.png
     */

    private int code;
    private String message;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
