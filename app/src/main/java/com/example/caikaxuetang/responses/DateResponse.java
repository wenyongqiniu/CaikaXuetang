package com.example.caikaxuetang.responses;

import java.util.ArrayList;


public class DateResponse {
    private String date;
    private ArrayList<dayBean> day;

    public ArrayList<dayBean> getDay() {
        return day;
    }

    public void setDay(ArrayList<dayBean> day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class dayBean {
       private int every_day;
       private boolean haveMessage;
       private String targetId;
       private Long taspTime;
       private  boolean isToday;

        public boolean isToday() {
            return isToday;
        }

        public void setToday(boolean today) {
            isToday = today;
        }

        public Long getTaspTime() {
            return taspTime;
        }

        public void setTaspTime(Long taspTime) {
            this.taspTime = taspTime;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public boolean isHaveMessage() {
            return haveMessage;
        }

        public void setHaveMessage(boolean haveMessage) {
            this.haveMessage = haveMessage;
        }

        public int getEvery_day() {
            return every_day;
        }

        public void setEvery_day(int every_day) {
            this.every_day = every_day;
        }
    }
}
