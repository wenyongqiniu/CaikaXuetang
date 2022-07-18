package com.example.caikaxuetang.responses;

public class AddAddressResponse {

    /**
     * code : 0
     * message :
     * data : {"id":"561905284308025349","customerId":"558613009255645185","prove":"北京市","city":"北京市","area":"朝阳区","addressDetail":"京朝大厦","realName":"文勇奇","mobile":"15570072668","isDefault":0}
     */

    private int code;
    private String message;
    /**
     * id : 561905284308025349
     * customerId : 558613009255645185
     * prove : 北京市
     * city : 北京市
     * area : 朝阳区
     * addressDetail : 京朝大厦
     * realName : 文勇奇
     * mobile : 15570072668
     * isDefault : 0
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
        private String customerId;
        private String prove;
        private String city;
        private String area;
        private String addressDetail;
        private String realName;
        private String mobile;
        private int isDefault;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getProve() {
            return prove;
        }

        public void setProve(String prove) {
            this.prove = prove;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
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

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }
}
