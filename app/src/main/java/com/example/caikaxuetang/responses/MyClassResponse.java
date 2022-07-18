package com.example.caikaxuetang.responses;

import java.util.List;

public class MyClassResponse {
    /**
     * code : 0
     * message :
     * data : [{"classId":"587308491548962998","campName":"测试营期","className":"测试班级","teacherName":"windy(小白经理)","teacherQrCode":"http://qiniu.xicaishe.com/a9393bb383075e0f3d95491535d401a2.png","showTeacherQrCode":true,"packageName":"淘宝无货源训练营","startTime":"2022-06-17T08:00:00"},{"classId":"597798796068958209","campName":"100期","className":"1班","teacherName":"大课经理","teacherQrCode":"https://image.xicaishe.com/20220708142454hxJ4unIw.png","showTeacherQrCode":true,"packageName":"淘宝无货源赚钱技能课","startTime":"2022-09-10T23:59:59"}]
     */

    private int code;
    private String message;
    /**
     * classId : 587308491548962998
     * campName : 测试营期
     * className : 测试班级
     * teacherName : windy(小白经理)
     * teacherQrCode : http://qiniu.xicaishe.com/a9393bb383075e0f3d95491535d401a2.png
     * showTeacherQrCode : true
     * packageName : 淘宝无货源训练营
     * startTime : 2022-06-17T08:00:00
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
        private String campName;
        private String className;
        private String teacherName;
        private String teacherQrCode;
        private boolean showTeacherQrCode;
        private String packageName;
        private String startTime;
        private String endTime;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getCampName() {
            return campName;
        }

        public void setCampName(String campName) {
            this.campName = campName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherQrCode() {
            return teacherQrCode;
        }

        public void setTeacherQrCode(String teacherQrCode) {
            this.teacherQrCode = teacherQrCode;
        }

        public boolean isShowTeacherQrCode() {
            return showTeacherQrCode;
        }

        public void setShowTeacherQrCode(boolean showTeacherQrCode) {
            this.showTeacherQrCode = showTeacherQrCode;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }
}
