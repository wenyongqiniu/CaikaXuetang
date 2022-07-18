package com.example.caikaxuetang.responses;

import java.util.List;

public class LearningCenterResponse {

    /**
     * code : 0
     * message :
     * data : {"liveCourseList":[],"courseTypeList":[{"name":"军队文职","type":6},{"name":"淘宝课","type":5},{"name":"家庭教育","type":4}]}
     */

    private int code;
    private String message;
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
        private List<?> liveCourseList;
        /**
         * name : 军队文职
         * type : 6
         */

        private List<CourseTypeListBean> courseTypeList;

        public List<?> getLiveCourseList() {
            return liveCourseList;
        }

        public void setLiveCourseList(List<?> liveCourseList) {
            this.liveCourseList = liveCourseList;
        }

        public List<CourseTypeListBean> getCourseTypeList() {
            return courseTypeList;
        }

        public void setCourseTypeList(List<CourseTypeListBean> courseTypeList) {
            this.courseTypeList = courseTypeList;
        }

        public static class CourseTypeListBean {
            private String name;
            private int type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
