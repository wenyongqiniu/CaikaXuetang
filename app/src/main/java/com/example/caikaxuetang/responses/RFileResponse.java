package com.example.caikaxuetang.responses;

public class RFileResponse {

    /**
     * mSize : 5948749
     * mType : video/mp4
     * progress : 0
     * mLocalPath : {}
     * mMediaUrl : {}
     * mName : 497be338ddcc75cf44a652903a92015a.mp4
     * destructTime : 0
     * isDestruct : false
     * userInfo : {"extra":"","id":"202084339220548101","name":"windy","portraitUri":{}}
     */

    private int mSize;
    private String mType;
    private int progress;
    private String mName;
    private int destructTime;
    private boolean isDestruct;
    /**
     * extra :
     * id : 202084339220548101
     * name : windy
     * portraitUri : {}
     */

    private UserInfoBean userInfo;

    public int getMSize() {
        return mSize;
    }

    public void setMSize(int mSize) {
        this.mSize = mSize;
    }

    public String getMType() {
        return mType;
    }

    public void setMType(String mType) {
        this.mType = mType;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public int getDestructTime() {
        return destructTime;
    }

    public void setDestructTime(int destructTime) {
        this.destructTime = destructTime;
    }

    public boolean isIsDestruct() {
        return isDestruct;
    }

    public void setIsDestruct(boolean isDestruct) {
        this.isDestruct = isDestruct;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        private String extra;
        private String id;
        private String name;
        private PortraitUriBean portraitUri;

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PortraitUriBean getPortraitUri() {
            return portraitUri;
        }

        public void setPortraitUri(PortraitUriBean portraitUri) {
            this.portraitUri = portraitUri;
        }

        public static class PortraitUriBean {
        }
    }
}
