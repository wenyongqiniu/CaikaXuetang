package com.example.caikaxuetang.responses;

import java.io.Serializable;
import java.util.List;

public class AddressListResponse implements Serializable {

    /**
     * code : 0
     * message :
     * data : [{"id":"561194331039875074","prove":"北京市","city":"北京市","area":"朝阳区","addressDetail":"京朝大厦","realName":"java大神","mobile":"15570072668","isDefault":1,"directUse":0,"selected":0},{"id":"561903951484043265","prove":"北京市","city":"北京市","area":"朝阳区","addressDetail":"京朝大厦附近","realName":"文勇奇","mobile":"15570072668","isDefault":1,"directUse":0,"selected":0},{"id":"561904059768389634","prove":"北京市","city":"北京市","area":"朝阳区","addressDetail":"京朝大厦附近","realName":"文勇奇","mobile":"15570072668","isDefault":1,"directUse":0,"selected":0},{"id":"561904487683866627","prove":"北京市","city":"北京市","area":"朝阳区","addressDetail":"京朝大厦附近","realName":"文勇奇","mobile":"15570072668","isDefault":1,"directUse":0,"selected":0},{"id":"561904936818327556","prove":"北京市","city":"北京市","area":"朝阳区","addressDetail":"京朝大厦附近","realName":"文勇奇","mobile":"15570072668","isDefault":1,"directUse":0,"selected":0},{"id":"561905809518772230","prove":"北京市","city":"北京市","area":"朝阳区","addressDetail":"京朝大厦","realName":"文勇奇","mobile":"15570072668","isDefault":1,"directUse":0,"selected":0},{"id":"561905284308025349","prove":"北京市","city":"北京市","area":"朝阳区","addressDetail":"京朝大厦","realName":"文勇奇","mobile":"15570072668","isDefault":0,"directUse":0,"selected":0},{"id":"561652356796137473","prove":"北京","city":"北京","addressDetail":"朝阳京朝大厦","realName":"大圣","mobile":"18810528823","directUse":0,"selected":0}]
     */

    private int code;
    private String message;
    /**
     * id : 561194331039875074
     * prove : 北京市
     * city : 北京市
     * area : 朝阳区
     * addressDetail : 京朝大厦
     * realName : java大神
     * mobile : 15570072668
     * isDefault : 1
     * directUse : 0
     * selected : 0
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String id;
        private String prove;
        private String city;
        private String area;
        private String addressDetail;
        private String realName;
        private String mobile;
        private int isDefault;
        private int directUse;
        private int selected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getDirectUse() {
            return directUse;
        }

        public void setDirectUse(int directUse) {
            this.directUse = directUse;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }
    }
}
