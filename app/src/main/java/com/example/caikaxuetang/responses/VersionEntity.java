package com.example.caikaxuetang.responses;


public class VersionEntity {

    /**
     * code : 0
     * data : {"appType":0,"downloadLink":"qwertyuio","haveLatestVersion":1,"system":0,"updateContent":"\u201c更新1\u201d","updateType":0,"versionNumber":"1.1.2","versionStatus":1}
     * message :
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * appType : 0
         * downloadLink : qwertyuio
         * haveLatestVersion : 1
         * system : 0
         * updateContent : “更新1”
         * updateType : 0
         * versionNumber : 1.1.2
         * versionStatus : 1
         */

        private int appType;
        private String downloadLink;
        private int haveLatestVersion;
        private int system;
        private String updateContent;
        private int updateType;
        private String versionNumber;
        private int versionStatus;
        private int showPhoneLogin;

        public int getShowPhoneLogin() {
            return showPhoneLogin;
        }

        public void setShowPhoneLogin(int showPhoneLogin) {
            this.showPhoneLogin = showPhoneLogin;
        }

        public int getAppType() {
            return appType;
        }

        public void setAppType(int appType) {
            this.appType = appType;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }

        public int getHaveLatestVersion() {
            return haveLatestVersion;
        }

        public void setHaveLatestVersion(int haveLatestVersion) {
            this.haveLatestVersion = haveLatestVersion;
        }

        public int getSystem() {
            return system;
        }

        public void setSystem(int system) {
            this.system = system;
        }

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public int getUpdateType() {
            return updateType;
        }

        public void setUpdateType(int updateType) {
            this.updateType = updateType;
        }

        public String getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(String versionNumber) {
            this.versionNumber = versionNumber;
        }

        public int getVersionStatus() {
            return versionStatus;
        }

        public void setVersionStatus(int versionStatus) {
            this.versionStatus = versionStatus;
        }
    }
}
