package com.example.caikaxuetang.responses;

import java.util.List;

public class CommitAnswerResponse {

    /**
     * sectionId : 533237262387220773
     * taskList : [{"taskId":"534075293453226971","content":"","answerCodeList":["A"]},{"taskId":"534076316473332193","content":"","answerCodeList":["B"]},{"taskId":"534076637077541519","content":"","answerCodeList":["A","B"]}]
     */

    private String sectionId;
    /**
     * taskId : 534075293453226971
     * content :
     * answerCodeList : ["A"]
     */

    private List<TaskListBean> taskList;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public List<TaskListBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskListBean> taskList) {
        this.taskList = taskList;
    }

    public static class TaskListBean {
        private String taskId;
        private String content;
        private List<String> answerCodeList;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getAnswerCodeList() {
            return answerCodeList;
        }

        public void setAnswerCodeList(List<String> answerCodeList) {
            this.answerCodeList = answerCodeList;
        }
    }
}
