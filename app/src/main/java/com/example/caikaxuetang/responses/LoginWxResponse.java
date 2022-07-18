package com.example.caikaxuetang.responses;

public class LoginWxResponse {


    /**
     * code : 0
     * message :
     * data : {"hasbindWechat":0,"token":"5bda501f27d8789bd7625ef87888c02a","nickname":"在哪跌倒こ就在哪躺下","headImage":"https://thirdwx.qlogo.cn/mmopen/vi_32/4VlQFZeVNJby1QgmGjVG5AVayuUib5m5ibOhbjPalvV2iagEH68pYdyZ2TdP1ia6XaQTrglv6yAiahp7b5LByMQ8bTQ/132","ryToken":"miwnLd7jBC9rTMxLWwUQg+u2Fbzq6XVprv2DcGk1G3ukEfvo64ctvA==@x7t2.cn.rongnav.com;x7t2.cn.rongcfg.com"}
     */

    private int code;
    private String message;
    /**
     * hasbindWechat : 0
     * token : 5bda501f27d8789bd7625ef87888c02a
     * nickname : 在哪跌倒こ就在哪躺下
     * headImage : https://thirdwx.qlogo.cn/mmopen/vi_32/4VlQFZeVNJby1QgmGjVG5AVayuUib5m5ibOhbjPalvV2iagEH68pYdyZ2TdP1ia6XaQTrglv6yAiahp7b5LByMQ8bTQ/132
     * ryToken : miwnLd7jBC9rTMxLWwUQg+u2Fbzq6XVprv2DcGk1G3ukEfvo64ctvA==@x7t2.cn.rongnav.com;x7t2.cn.rongcfg.com
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
        private String nickname;
        private String headImage;
        private String ryToken;
        private String studyNo;

        public String getStudyNo() {
            return studyNo;
        }

        public void setStudyNo(String studyNo) {
            this.studyNo = studyNo;
        }

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getRyToken() {
            return ryToken;
        }

        public void setRyToken(String ryToken) {
            this.ryToken = ryToken;
        }
    }
}
