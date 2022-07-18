package com.example.caikaxuetang.responses;

import java.io.Serializable;
import java.util.List;

public class ShowCourseListResponse implements Serializable {


    /**
     * code : 0
     * message :
     * data : {"xbCourseList":[{"packageId":"202008201946481588","courseId":"531847770749244411","courseName":"淘宝无货源训练营","courseType":0,"currentDay":4,"totalDay":5,"customerNum":92832,"headImages":["http://qiniu.xicaishe_test.com/45fa6a16-17f4-4678-874a-a61f9089cbbe.jpeg","http://qiniu.xicaishe_test.com/b4d14733-f360-48e8-9890-c4769744910a.jpeg","http://qiniu.xicaishe_test.com/11d58f4d-2be2-4905-adc1-faead4f8629a.jpeg","http://qiniu.xicaishe_test.com/619896bf-f4d3-4d6b-b19b-bf33bb0bfb53.jpeg"]}],"bigCourse":{"packageId":"533305006155600543","contractStatus":1,"selectClassStatus":1,"addressStatus":1,"showTeacherStatus":1,"startTime":"2022-03-26","packageName":"淘宝无货源赚钱技能课","className":"2期1班","teacherQrCode":"https://image.xicaishe.com/20220218151823EmWE41pu.jpg","bigCourseList":[{"courseId":"533305521744614302","courseName":"淘宝无货源赚钱技能课","contentType":2,"studyStatus":0,"coverImage":"https://image.xicaishe.com/20220111175303cXnn5ERQ.png"}]}}
     */

    private Integer code;
    private String message;
    /**
     * xbCourseList : [{"packageId":"202008201946481588","courseId":"531847770749244411","courseName":"淘宝无货源训练营","courseType":0,"currentDay":4,"totalDay":5,"customerNum":92832,"headImages":["http://qiniu.xicaishe_test.com/45fa6a16-17f4-4678-874a-a61f9089cbbe.jpeg","http://qiniu.xicaishe_test.com/b4d14733-f360-48e8-9890-c4769744910a.jpeg","http://qiniu.xicaishe_test.com/11d58f4d-2be2-4905-adc1-faead4f8629a.jpeg","http://qiniu.xicaishe_test.com/619896bf-f4d3-4d6b-b19b-bf33bb0bfb53.jpeg"]}]
     * bigCourse : {"packageId":"533305006155600543","contractStatus":1,"selectClassStatus":1,"addressStatus":1,"showTeacherStatus":1,"startTime":"2022-03-26","packageName":"淘宝无货源赚钱技能课","className":"2期1班","teacherQrCode":"https://image.xicaishe.com/20220218151823EmWE41pu.jpg","bigCourseList":[{"courseId":"533305521744614302","courseName":"淘宝无货源赚钱技能课","contentType":2,"studyStatus":0,"coverImage":"https://image.xicaishe.com/20220111175303cXnn5ERQ.png"}]}
     */

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

    public static class DataBean implements Serializable{
        /**
         * packageId : 533305006155600543
         * contractStatus : 1
         * selectClassStatus : 1
         * addressStatus : 1
         * showTeacherStatus : 1
         * startTime : 2022-03-26
         * packageName : 淘宝无货源赚钱技能课
         * className : 2期1班
         * teacherQrCode : https://image.xicaishe.com/20220218151823EmWE41pu.jpg
         * bigCourseList : [{"courseId":"533305521744614302","courseName":"淘宝无货源赚钱技能课","contentType":2,"studyStatus":0,"coverImage":"https://image.xicaishe.com/20220111175303cXnn5ERQ.png"}]
         */

        private BigCourseBean bigCourse;
        /**
         * packageId : 202008201946481588
         * courseId : 531847770749244411
         * courseName : 淘宝无货源训练营
         * courseType : 0
         * currentDay : 4
         * totalDay : 5
         * customerNum : 92832
         * headImages : ["http://qiniu.xicaishe_test.com/45fa6a16-17f4-4678-874a-a61f9089cbbe.jpeg","http://qiniu.xicaishe_test.com/b4d14733-f360-48e8-9890-c4769744910a.jpeg","http://qiniu.xicaishe_test.com/11d58f4d-2be2-4905-adc1-faead4f8629a.jpeg","http://qiniu.xicaishe_test.com/619896bf-f4d3-4d6b-b19b-bf33bb0bfb53.jpeg"]
         */

        private List<XbCourseListBean> xbCourseList;

        public BigCourseBean getBigCourse() {
            return bigCourse;
        }

        public void setBigCourse(BigCourseBean bigCourse) {
            this.bigCourse = bigCourse;
        }

        public List<XbCourseListBean> getXbCourseList() {
            return xbCourseList;
        }

        public void setXbCourseList(List<XbCourseListBean> xbCourseList) {
            this.xbCourseList = xbCourseList;
        }

        public static class BigCourseBean implements Serializable{
            private String packageId;
            private Integer contractStatus;
            private Integer selectClassStatus;
            private Integer addressStatus;
            private Integer showTeacherStatus;
            private String startTime;
            private String packageName;
            private String className;
            private String teacherQrCode;
            /**
             * courseId : 533305521744614302
             * courseName : 淘宝无货源赚钱技能课
             * contentType : 2
             * studyStatus : 0
             * coverImage : https://image.xicaishe.com/20220111175303cXnn5ERQ.png
             */

            private List<BigCourseListBean> bigCourseList;

            public String getPackageId() {
                return packageId;
            }

            public void setPackageId(String packageId) {
                this.packageId = packageId;
            }

            public Integer getContractStatus() {
                return contractStatus;
            }

            public void setContractStatus(Integer contractStatus) {
                this.contractStatus = contractStatus;
            }

            public Integer getSelectClassStatus() {
                return selectClassStatus;
            }

            public void setSelectClassStatus(Integer selectClassStatus) {
                this.selectClassStatus = selectClassStatus;
            }

            public Integer getAddressStatus() {
                return addressStatus;
            }

            public void setAddressStatus(Integer addressStatus) {
                this.addressStatus = addressStatus;
            }

            public Integer getShowTeacherStatus() {
                return showTeacherStatus;
            }

            public void setShowTeacherStatus(Integer showTeacherStatus) {
                this.showTeacherStatus = showTeacherStatus;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getTeacherQrCode() {
                return teacherQrCode;
            }

            public void setTeacherQrCode(String teacherQrCode) {
                this.teacherQrCode = teacherQrCode;
            }

            public List<BigCourseListBean> getBigCourseList() {
                return bigCourseList;
            }

            public void setBigCourseList(List<BigCourseListBean> bigCourseList) {
                this.bigCourseList = bigCourseList;
            }

            public static class BigCourseListBean {
                private String courseId;
                private String courseName;
                private Integer contentType;
                private Integer studyStatus;
                private String coverImage;

                public String getCourseId() {
                    return courseId;
                }

                public void setCourseId(String courseId) {
                    this.courseId = courseId;
                }

                public String getCourseName() {
                    return courseName;
                }

                public void setCourseName(String courseName) {
                    this.courseName = courseName;
                }

                public Integer getContentType() {
                    return contentType;
                }

                public void setContentType(Integer contentType) {
                    this.contentType = contentType;
                }

                public Integer getStudyStatus() {
                    return studyStatus;
                }

                public void setStudyStatus(Integer studyStatus) {
                    this.studyStatus = studyStatus;
                }

                public String getCoverImage() {
                    return coverImage;
                }

                public void setCoverImage(String coverImage) {
                    this.coverImage = coverImage;
                }
            }
        }

        public static class XbCourseListBean implements Serializable{
            private String packageId;
            private String courseId;
            private String courseName;
            private Integer courseType;
            private Integer currentDay;
            private Integer totalDay;
            private Integer customerNum;
            private List<String> headImages;

            public String getPackageId() {
                return packageId;
            }

            public void setPackageId(String packageId) {
                this.packageId = packageId;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public Integer getCourseType() {
                return courseType;
            }

            public void setCourseType(Integer courseType) {
                this.courseType = courseType;
            }

            public Integer getCurrentDay() {
                return currentDay;
            }

            public void setCurrentDay(Integer currentDay) {
                this.currentDay = currentDay;
            }

            public Integer getTotalDay() {
                return totalDay;
            }

            public void setTotalDay(Integer totalDay) {
                this.totalDay = totalDay;
            }

            public Integer getCustomerNum() {
                return customerNum;
            }

            public void setCustomerNum(Integer customerNum) {
                this.customerNum = customerNum;
            }

            public List<String> getHeadImages() {
                return headImages;
            }

            public void setHeadImages(List<String> headImages) {
                this.headImages = headImages;
            }
        }
    }
}
