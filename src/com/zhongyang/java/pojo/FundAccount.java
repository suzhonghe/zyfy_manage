package com.zhongyang.java.pojo;

import java.util.Date;

public class FundAccount {
    private String id;

    private Boolean defaultaccount;

    private Date timerecorded;

    private Boolean valid;

    private String account;

    private String bank;

    private String bankmobile;

    private String branch;

    private String city;

    private String location;

    private String name;

    private String province;

    private String userId;

    private Boolean expressaccount;

    private Boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Boolean getDefaultaccount() {
        return defaultaccount;
    }

    public void setDefaultaccount(Boolean defaultaccount) {
        this.defaultaccount = defaultaccount;
    }

    public Date getTimerecorded() {
        return timerecorded;
    }

    public void setTimerecorded(Date timerecorded) {
        this.timerecorded = timerecorded;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankmobile() {
        return bankmobile;
    }

    public void setBankmobile(String bankmobile) {
        this.bankmobile = bankmobile == null ? null : bankmobile.trim();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Boolean getExpressaccount() {
        return expressaccount;
    }

    public void setExpressaccount(Boolean expressaccount) {
        this.expressaccount = expressaccount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}