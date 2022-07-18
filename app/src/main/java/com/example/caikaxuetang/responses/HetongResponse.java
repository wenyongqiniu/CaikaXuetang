package com.example.caikaxuetang.responses;

import java.util.List;

public class HetongResponse {
    /**
     * code : 0
     * message :
     * data : ["https://contract.xicaishe.com/C5BC00EC254A661F.pdf","https://contract.xicaishe.com/9D06C6389EA148B9.pdf"]
     */

    private int code;
    private String message;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
