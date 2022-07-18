package com.example.caikaxuetang.responses;

public class GroupNoticeBean {

    private Integer code;
    private String message;
    private DataBean data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
        private Boolean haveViewed;
        private Boolean noAnnouncement;
        private String announcementContent;
        private String announcementId;

        public Boolean getHaveViewed() {
            return haveViewed;
        }

        public void setHaveViewed(Boolean haveViewed) {
            this.haveViewed = haveViewed;
        }

        public Boolean getNoAnnouncement() {
            return noAnnouncement;
        }

        public void setNoAnnouncement(Boolean noAnnouncement) {
            this.noAnnouncement = noAnnouncement;
        }

        public String getAnnouncementContent() {
            return announcementContent;
        }

        public void setAnnouncementContent(String announcementContent) {
            this.announcementContent = announcementContent;
        }

        public String getAnnouncementId() {
            return announcementId;
        }

        public void setAnnouncementId(String announcementId) {
            this.announcementId = announcementId;
        }
    }
}
