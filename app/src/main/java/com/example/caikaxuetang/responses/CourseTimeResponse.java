package com.example.caikaxuetang.responses;

import java.util.List;

public class CourseTimeResponse {
    /**
     * code : 0
     * message :
     * data : [{"classId":"561207641617482714","date":"2022-04-30","fullStatus":0},{"classId":"561206644832747844","date":"2022-04-07","fullStatus":0},{"classId":"559041082908229970","date":"2022-03-26","fullStatus":0},{"classId":"561207417947833137","date":"2022-03-31","fullStatus":0},{"classId":"561206756631921080","date":"2022-04-01","fullStatus":0}]
     */

    private int code;
    private String message;
    /**
     * classId : 561207641617482714
     * date : 2022-04-30
     * fullStatus : 0
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

    public static class DataBean {
        private String classId;
        private String date;
        private int fullStatus;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getFullStatus() {
            return fullStatus;
        }

        public void setFullStatus(int fullStatus) {
            this.fullStatus = fullStatus;
        }
    }
}
