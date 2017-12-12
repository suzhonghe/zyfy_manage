package com.zhongyang.java.pojo;

import java.math.BigDecimal;

public class CorporationUserInfo {
    private String userId;

    private String address;

    private String background;

    private String businessscope;

    private String contactemail;

    private String contactpersion;

    private String contactphone;

    private String creditrank;

    private String description;

    private BigDecimal registeredcapital;

    private String registeredlocation;

    private String url;

    private byte[] timeestablished;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background == null ? null : background.trim();
    }

    public String getBusinessscope() {
        return businessscope;
    }

    public void setBusinessscope(String businessscope) {
        this.businessscope = businessscope == null ? null : businessscope.trim();
    }

    public String getContactemail() {
        return contactemail;
    }

    public void setContactemail(String contactemail) {
        this.contactemail = contactemail == null ? null : contactemail.trim();
    }

    public String getContactpersion() {
        return contactpersion;
    }

    public void setContactpersion(String contactpersion) {
        this.contactpersion = contactpersion == null ? null : contactpersion.trim();
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone == null ? null : contactphone.trim();
    }

    public String getCreditrank() {
        return creditrank;
    }

    public void setCreditrank(String creditrank) {
        this.creditrank = creditrank == null ? null : creditrank.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public BigDecimal getRegisteredcapital() {
        return registeredcapital;
    }

    public void setRegisteredcapital(BigDecimal registeredcapital) {
        this.registeredcapital = registeredcapital;
    }

    public String getRegisteredlocation() {
        return registeredlocation;
    }

    public void setRegisteredlocation(String registeredlocation) {
        this.registeredlocation = registeredlocation == null ? null : registeredlocation.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public byte[] getTimeestablished() {
        return timeestablished;
    }

    public void setTimeestablished(byte[] timeestablished) {
        this.timeestablished = timeestablished;
    }
}