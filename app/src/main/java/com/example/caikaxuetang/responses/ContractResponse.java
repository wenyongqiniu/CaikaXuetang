package com.example.caikaxuetang.responses;

public class ContractResponse {

    /**
     * code : 0
     * message :
     * data : {"id":"123","company":"财咖世纪信息技术（北京）有限公司","companyAddress":"北京市朝阳区农展馆南路13号瑞辰国际中心1102","content":""}
     */

    private int code;
    private String message;
    /**
     * id : 123
     * company : 财咖世纪信息技术（北京）有限公司
     * companyAddress : 北京市朝阳区农展馆南路13号瑞辰国际中心1102
     * content :
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
        private String id;
        private String company;
        private String companyAddress;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
