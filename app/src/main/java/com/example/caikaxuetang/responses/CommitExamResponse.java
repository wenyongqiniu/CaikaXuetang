package com.example.caikaxuetang.responses;

import java.util.List;

public class CommitExamResponse {
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
