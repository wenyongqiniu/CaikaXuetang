package com.example.caikaxuetang.responses;

public class GetPhoneResponse {

    /**
     * id : 117270465679982592
     * code : 8000
     * content : get phone success
     * exID : 1234566
     * phone : HpBLIQ/6SkFl0pAq0LMdw1aZ8RHoofgWmaY//LE+0ahkSdHC5oTCnjrR8Tj8y5naKVI03torFU+EzAQnwtVqAoQyYckT0S3Q02TKuAal3VRGiR5Lmp4g2A5Mh4/W5A4o6QFviHuBVJZE/WV0AzU5w4NGhpyQntOeF0UyovYATy4=
     */

    private long id;
    private int code;
    private String content;
    private String exID;
    private String phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExID() {
        return exID;
    }

    public void setExID(String exID) {
        this.exID = exID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
