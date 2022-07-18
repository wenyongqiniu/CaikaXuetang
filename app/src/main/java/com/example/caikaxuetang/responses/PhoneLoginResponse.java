package com.example.caikaxuetang.responses;

public class PhoneLoginResponse {
    /**
     * code : 0
     * message :
     * data : {"hasbindWechat":0,"token":"558295054055653377","nickname":"哈利路亚","headImage":"http://qiniu.xicaishe.com/aacdd95e-4c37-457f-976c-c808a6b3ea07.jpeg","studyNo":"BC8900"}
     */

    private int code;
    private String message;
    /**
     * hasbindWechat : 0
     * token : 558295054055653377
     * nickname : 哈利路亚
     * headImage : http://qiniu.xicaishe.com/aacdd95e-4c37-457f-976c-c808a6b3ea07.jpeg
     * studyNo : BC8900
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
        private String studyNo;

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

        public String getStudyNo() {
            return studyNo;
        }

        public void setStudyNo(String studyNo) {
            this.studyNo = studyNo;
        }
    }
}
