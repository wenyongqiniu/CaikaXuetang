package com.example.caikaxuetang.responses;

import java.util.List;

public class DakeStepResponse {

    /**
     * code : 0
     * message :
     * data : {"processList":[{"name":"签署合同","processType":"HT","status":0},{"name":"填写地址","processType":"DZ","status":0},{"name":"选择时间","processType":"SJ","status":0},{"name":"开始上课","processType":"SK","status":0}]}
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
        /**
         * name : 签署合同
         * processType : HT
         * status : 0
         */

        private List<ProcessListBean> processList;

        public List<ProcessListBean> getProcessList() {
            return processList;
        }

        public void setProcessList(List<ProcessListBean> processList) {
            this.processList = processList;
        }

        public static class ProcessListBean {
            private String name;
            private String processType;
            private int status;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProcessType() {
                return processType;
            }

            public void setProcessType(String processType) {
                this.processType = processType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
