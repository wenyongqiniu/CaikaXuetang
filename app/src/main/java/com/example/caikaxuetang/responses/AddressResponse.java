package com.example.caikaxuetang.responses;

public class AddressResponse {

    /**
     * code : 0
     * message :
     * data : {"id":"560897089871568897","address":"朝阳京朝大厦","realName":"潘兴武","mobile":"18810528823"}
     */

    private int code;
    private String message;
    /**
     * id : 560897089871568897
     * address : 朝阳京朝大厦
     * realName : 潘兴武
     * mobile : 18810528823
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
        private String id;
        private String address;
        private String realName;
        private String mobile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
