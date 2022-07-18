package com.example.caikaxuetang.responses;

public class NoteDetailResponse {

    /**
     * code : 0
     * message :
     * data : {"id":"589542875542884355","content":"哈哈哈哈哈哈","sectionName":"晚课：一招帮你找到淘宝挖金秘诀","createdTime":"2022-06-15T19:52:55"}
     */

    private int code;
    private String message;
    /**
     * id : 589542875542884355
     * content : 哈哈哈哈哈哈
     * sectionName : 晚课：一招帮你找到淘宝挖金秘诀
     * createdTime : 2022-06-15T19:52:55
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
        private String content;
        private String sectionName;
        private String createdTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }
    }
}
