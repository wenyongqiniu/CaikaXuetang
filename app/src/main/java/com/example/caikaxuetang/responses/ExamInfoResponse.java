package com.example.caikaxuetang.responses;

public class ExamInfoResponse {

    /**
     * code : 0
     * message :
     * data : {"taskId":"531487498574468007","coursePackageName":"淘宝无货源训练营","totalNum":21,"rightNum":0,"doneNum":0,"score":0}
     */

    private int code;
    private String message;
    /**
     * taskId : 531487498574468007
     * coursePackageName : 淘宝无货源训练营
     * totalNum : 21
     * rightNum : 0
     * doneNum : 0
     * score : 0
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
        private String taskId;
        private String coursePackageName;
        private int totalNum;
        private int rightNum;
        private int doneNum;
        private int score;
        private boolean canGetCard;

        public boolean isCanGetCard() {
            return canGetCard;
        }

        public void setCanGetCard(boolean canGetCard) {
            this.canGetCard = canGetCard;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getCoursePackageName() {
            return coursePackageName;
        }

        public void setCoursePackageName(String coursePackageName) {
            this.coursePackageName = coursePackageName;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getRightNum() {
            return rightNum;
        }

        public void setRightNum(int rightNum) {
            this.rightNum = rightNum;
        }

        public int getDoneNum() {
            return doneNum;
        }

        public void setDoneNum(int doneNum) {
            this.doneNum = doneNum;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
