package com.zhongyang.java.pojo;

public class Client {
    private String id;

    private String code;

    private String internalemailindicators;

    private String logo;

    private String mobile;

    private String name;

    private Boolean secure;

    private String shortname;

    private String supportemail;

    private String supportphone;

    private String title;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getInternalemailindicators() {
        return internalemailindicators;
    }

    public void setInternalemailindicators(String internalemailindicators) {
        this.internalemailindicators = internalemailindicators == null ? null : internalemailindicators.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname == null ? null : shortname.trim();
    }

    public String getSupportemail() {
        return supportemail;
    }

    public void setSupportemail(String supportemail) {
        this.supportemail = supportemail == null ? null : supportemail.trim();
    }

    public String getSupportphone() {
        return supportphone;
    }

    public void setSupportphone(String supportphone) {
        this.supportphone = supportphone == null ? null : supportphone.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}