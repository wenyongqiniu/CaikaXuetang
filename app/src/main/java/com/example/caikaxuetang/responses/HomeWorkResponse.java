package com.example.caikaxuetang.responses;

import java.util.List;

public class HomeWorkResponse {


    /**
     * code : 0
     * message :
     * data : {"completeStatus":1,"taskRecordList":[{"sectionId":"533237262387220773","status":1,"dayDesc":"第一天","isCurrent":1},{"sectionId":"532864425285620423","status":1,"dayDesc":"第二天","isCurrent":0},{"sectionId":"532915129912103233","status":1,"dayDesc":"第三天","isCurrent":0},{"sectionId":"532930429822931475","status":1,"dayDesc":"第四天","isCurrent":0}],"taskList":[{"taskId":"534075293453226971","taskTitle":"以下哪种不属于过时且风险大收益低的无货源玩法?","taskType":1,"rightAnswerCode":"B","customerAnswerCode":"A","analysis":"ACD 几种模式都有一个共同的缺点，店铺很容易被淘宝给封掉，工作量大，收益也低!\n","answerList":[{"answerId":"534076305064825303","answerCode":"A","content":"傻瓜式铺货:用软件采集别人的货品，直接上架到自己的店铺","rightAnswer":0},{"answerId":"534076305069019608","answerCode":"B","content":"蓝海市场运营: 利用蓝海类目选品，进行精细化运营","rightAnswer":1},{"answerId":"534076305077408217","answerCode":"C","content":"裂变模式:一个货品，用不同的标题去霸屏，占据流量","rightAnswer":0},{"answerId":"534076305060630998","answerCode":"D","content":"采集模式:找一个销量特别好的店铺，把别人整个店铺全部复制过来","rightAnswer":0}]},{"taskId":"534076316473332193","taskTitle":"以下关于蓝海市场和红海市场说法正确的是？","taskType":1,"rightAnswerCode":"B","customerAnswerCode":"B","analysis":" 绝大多数固定的需求都是【红海市场】，饥饿营销、买家数量大于卖家的市场，都是蓝海市场。","answerList":[{"answerId":"534076630077248139","answerCode":"A","content":"绝大多数固定的需求都是【蓝海市场】","rightAnswer":0},{"answerId":"534076630043693704","answerCode":"B","content":"绝大多数【小而美】的需求是【蓝海市场】","rightAnswer":1},{"answerId":"534076630060470922","answerCode":"C","content":"像饥饿营销这样的市场是【红海市场】","rightAnswer":0},{"answerId":"534076630052082313","answerCode":"D","content":"买家数量大于卖家数量的市场是【红海市场】","rightAnswer":0}]},{"taskId":"534076637077541519","taskTitle":"终极蓝海市场的优势在于? ","taskType":2,"rightAnswerCode":"A,B,C,D","customerAnswerCode":"A,B","analysis":"以上都对。\n","answerList":[{"answerId":"534076949997785911","answerCode":"A","content":"竞争小","rightAnswer":1},{"answerId":"534076949976814389","answerCode":"B","content":" 需求大","rightAnswer":1},{"answerId":"534076949989397302","answerCode":"C","content":"投入小","rightAnswer":1},{"answerId":"534076950006174520","answerCode":"D","content":"易出单","rightAnswer":1}]}]}
     */

    private int code;
    private String message;
    /**
     * completeStatus : 1
     * taskRecordList : [{"sectionId":"533237262387220773","status":1,"dayDesc":"第一天","isCurrent":1},{"sectionId":"532864425285620423","status":1,"dayDesc":"第二天","isCurrent":0},{"sectionId":"532915129912103233","status":1,"dayDesc":"第三天","isCurrent":0},{"sectionId":"532930429822931475","status":1,"dayDesc":"第四天","isCurrent":0}]
     * taskList : [{"taskId":"534075293453226971","taskTitle":"以下哪种不属于过时且风险大收益低的无货源玩法?","taskType":1,"rightAnswerCode":"B","customerAnswerCode":"A","analysis":"ACD 几种模式都有一个共同的缺点，店铺很容易被淘宝给封掉，工作量大，收益也低!\n","answerList":[{"answerId":"534076305064825303","answerCode":"A","content":"傻瓜式铺货:用软件采集别人的货品，直接上架到自己的店铺","rightAnswer":0},{"answerId":"534076305069019608","answerCode":"B","content":"蓝海市场运营: 利用蓝海类目选品，进行精细化运营","rightAnswer":1},{"answerId":"534076305077408217","answerCode":"C","content":"裂变模式:一个货品，用不同的标题去霸屏，占据流量","rightAnswer":0},{"answerId":"534076305060630998","answerCode":"D","content":"采集模式:找一个销量特别好的店铺，把别人整个店铺全部复制过来","rightAnswer":0}]},{"taskId":"534076316473332193","taskTitle":"以下关于蓝海市场和红海市场说法正确的是？","taskType":1,"rightAnswerCode":"B","customerAnswerCode":"B","analysis":" 绝大多数固定的需求都是【红海市场】，饥饿营销、买家数量大于卖家的市场，都是蓝海市场。","answerList":[{"answerId":"534076630077248139","answerCode":"A","content":"绝大多数固定的需求都是【蓝海市场】","rightAnswer":0},{"answerId":"534076630043693704","answerCode":"B","content":"绝大多数【小而美】的需求是【蓝海市场】","rightAnswer":1},{"answerId":"534076630060470922","answerCode":"C","content":"像饥饿营销这样的市场是【红海市场】","rightAnswer":0},{"answerId":"534076630052082313","answerCode":"D","content":"买家数量大于卖家数量的市场是【红海市场】","rightAnswer":0}]},{"taskId":"534076637077541519","taskTitle":"终极蓝海市场的优势在于? ","taskType":2,"rightAnswerCode":"A,B,C,D","customerAnswerCode":"A,B","analysis":"以上都对。\n","answerList":[{"answerId":"534076949997785911","answerCode":"A","content":"竞争小","rightAnswer":1},{"answerId":"534076949976814389","answerCode":"B","content":" 需求大","rightAnswer":1},{"answerId":"534076949989397302","answerCode":"C","content":"投入小","rightAnswer":1},{"answerId":"534076950006174520","answerCode":"D","content":"易出单","rightAnswer":1}]}]
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
        private int completeStatus;
        private int totalNum;
        private int rightNum;

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

        /**
         * sectionId : 533237262387220773
         * status : 1
         * dayDesc : 第一天
         * isCurrent : 1
         */

        private List<TaskRecordListBean> taskRecordList;
        /**
         * taskId : 534075293453226971
         * taskTitle : 以下哪种不属于过时且风险大收益低的无货源玩法?
         * taskType : 1
         * rightAnswerCode : B
         * customerAnswerCode : A
         * analysis : ACD 几种模式都有一个共同的缺点，店铺很容易被淘宝给封掉，工作量大，收益也低!
         * answerList : [{"answerId":"534076305064825303","answerCode":"A","content":"傻瓜式铺货:用软件采集别人的货品，直接上架到自己的店铺","rightAnswer":0},{"answerId":"534076305069019608","answerCode":"B","content":"蓝海市场运营: 利用蓝海类目选品，进行精细化运营","rightAnswer":1},{"answerId":"534076305077408217","answerCode":"C","content":"裂变模式:一个货品，用不同的标题去霸屏，占据流量","rightAnswer":0},{"answerId":"534076305060630998","answerCode":"D","content":"采集模式:找一个销量特别好的店铺，把别人整个店铺全部复制过来","rightAnswer":0}]
         */

        private List<TaskListBean> taskList;

        public int getCompleteStatus() {
            return completeStatus;
        }

        public void setCompleteStatus(int completeStatus) {
            this.completeStatus = completeStatus;
        }

        public List<TaskRecordListBean> getTaskRecordList() {
            return taskRecordList;
        }

        public void setTaskRecordList(List<TaskRecordListBean> taskRecordList) {
            this.taskRecordList = taskRecordList;
        }

        public List<TaskListBean> getTaskList() {
            return taskList;
        }

        public void setTaskList(List<TaskListBean> taskList) {
            this.taskList = taskList;
        }

        public static class TaskRecordListBean {
            private String sectionId;
            private int status;
            private String dayDesc;
            private int isCurrent;

            public String getSectionId() {
                return sectionId;
            }

            public void setSectionId(String sectionId) {
                this.sectionId = sectionId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getDayDesc() {
                return dayDesc;
            }

            public void setDayDesc(String dayDesc) {
                this.dayDesc = dayDesc;
            }

            public int getIsCurrent() {
                return isCurrent;
            }

            public void setIsCurrent(int isCurrent) {
                this.isCurrent = isCurrent;
            }
        }

        public static class TaskListBean {
            private String taskId;
            private String taskTitle;
            private int taskType;
            private String rightAnswerCode;
            private String customerAnswerCode;
            private String myEd;
            private String analysis;

            public String getMyEd() {
                return myEd;
            }

            public void setMyEd(String myEd) {
                this.myEd = myEd;
            }

            /**
             * answerId : 534076305064825303
             * answerCode : A
             * content : 傻瓜式铺货:用软件采集别人的货品，直接上架到自己的店铺
             * rightAnswer : 0
             */


            private List<AnswerListBean> answerList;

            public String getTaskId() {
                return taskId;
            }

            public void setTaskId(String taskId) {
                this.taskId = taskId;
            }

            public String getTaskTitle() {
                return taskTitle;
            }

            public void setTaskTitle(String taskTitle) {
                this.taskTitle = taskTitle;
            }

            public int getTaskType() {
                return taskType;
            }

            public void setTaskType(int taskType) {
                this.taskType = taskType;
            }

            public String getRightAnswerCode() {
                return rightAnswerCode;
            }

            public void setRightAnswerCode(String rightAnswerCode) {
                this.rightAnswerCode = rightAnswerCode;
            }

            public String getCustomerAnswerCode() {
                return customerAnswerCode;
            }

            public void setCustomerAnswerCode(String customerAnswerCode) {
                this.customerAnswerCode = customerAnswerCode;
            }

            public String getAnalysis() {
                return analysis;
            }

            public void setAnalysis(String analysis) {
                this.analysis = analysis;
            }

            public List<AnswerListBean> getAnswerList() {
                return answerList;
            }

            public void setAnswerList(List<AnswerListBean> answerList) {
                this.answerList = answerList;
            }

            public static class AnswerListBean {
                private String answerId;
                private String answerCode;
                private String content;
                private String myContent;
                private int rightAnswer;
                private boolean checked;
                private boolean selected;

                public String getMyContent() {
                    return myContent;
                }

                public void setMyContent(String myContent) {
                    this.myContent = myContent;
                }

                public boolean isSelected() {
                    return selected;
                }

                public void setSelected(boolean selected) {
                    this.selected = selected;
                }

                private List<CommitAnswerResponse.TaskListBean> taskList;

                public List<CommitAnswerResponse.TaskListBean> getTaskList() {
                    return taskList;
                }

                public void setTaskList(List<CommitAnswerResponse.TaskListBean> taskList) {
                    this.taskList = taskList;
                }

                public boolean isChecked() {
                    return checked;
                }

                public void setChecked(boolean checked) {
                    this.checked = checked;
                }

                public String getAnswerId() {
                    return answerId;
                }

                public void setAnswerId(String answerId) {
                    this.answerId = answerId;
                }

                public String getAnswerCode() {
                    return answerCode;
                }

                public void setAnswerCode(String answerCode) {
                    this.answerCode = answerCode;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getRightAnswer() {
                    return rightAnswer;
                }

                public void setRightAnswer(int rightAnswer) {
                    this.rightAnswer = rightAnswer;
                }
            }
        }
    }
}
