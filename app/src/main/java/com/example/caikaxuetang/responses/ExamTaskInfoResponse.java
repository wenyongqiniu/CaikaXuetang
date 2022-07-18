package com.example.caikaxuetang.responses;

import java.util.List;

public class ExamTaskInfoResponse {
    /**
     * code : 0
     * message :
     * data : {"nextTaskId":"202123311939466536","totalNum":20,"doneNum":0,"taskTitle":"1、以下哪种皮肤是优质肤质","taskType":1,"answerList":[{"answerId":"531835842656179005","answerCode":"A","content":"油性皮肤","rightAnswer":0,"selectStatus":0},{"answerId":"531835842668761918","answerCode":"B","content":"干性皮肤","rightAnswer":0,"selectStatus":0},{"answerId":"531835842668761919","answerCode":"C","content":"中性皮肤","rightAnswer":1,"selectStatus":0},{"answerId":"531835842698122048","answerCode":"D","content":"敏感性皮肤","rightAnswer":0,"selectStatus":0}]}
     */

    private int code;
    private String message;
    /**
     * nextTaskId : 202123311939466536
     * totalNum : 20
     * doneNum : 0
     * taskTitle : 1、以下哪种皮肤是优质肤质
     * taskType : 1
     * answerList : [{"answerId":"531835842656179005","answerCode":"A","content":"油性皮肤","rightAnswer":0,"selectStatus":0},{"answerId":"531835842668761918","answerCode":"B","content":"干性皮肤","rightAnswer":0,"selectStatus":0},{"answerId":"531835842668761919","answerCode":"C","content":"中性皮肤","rightAnswer":1,"selectStatus":0},{"answerId":"531835842698122048","answerCode":"D","content":"敏感性皮肤","rightAnswer":0,"selectStatus":0}]
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
        private String nextTaskId;
        private String prveTaskId;
        private int totalNum;
        private int doneNum;
        private String taskTitle;
        private String content;
        private int taskType;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPrveTaskId() {
            return prveTaskId;
        }

        public void setPrveTaskId(String prveTaskId) {
            this.prveTaskId = prveTaskId;
        }

        /**
         * answerId : 531835842656179005
         * answerCode : A
         * content : 油性皮肤
         * rightAnswer : 0
         * selectStatus : 0
         */

        private List<AnswerListBean> answerList;

        public String getNextTaskId() {
            return nextTaskId;
        }

        public void setNextTaskId(String nextTaskId) {
            this.nextTaskId = nextTaskId;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getDoneNum() {
            return doneNum;
        }

        public void setDoneNum(int doneNum) {
            this.doneNum = doneNum;
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
            private int rightAnswer;
            private int selectStatus;
            private boolean isChecked;
            private boolean isSelected;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
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

            public int getSelectStatus() {
                return selectStatus;
            }

            public void setSelectStatus(int selectStatus) {
                this.selectStatus = selectStatus;
            }
        }
    }
}
