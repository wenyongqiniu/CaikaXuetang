package com.example.caikaxuetang.responses;

public class MessageContentResponse {

    /**
     * content : 您好，这是从用户1发出的消息
     * destructTime : 0
     * isDestruct : false
     */

    private String content;
    private int destructTime;
    private boolean isDestruct;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDestructTime() {
        return destructTime;
    }

    public void setDestructTime(int destructTime) {
        this.destructTime = destructTime;
    }

    public boolean isIsDestruct() {
        return isDestruct;
    }

    public void setIsDestruct(boolean isDestruct) {
        this.isDestruct = isDestruct;
    }
}
