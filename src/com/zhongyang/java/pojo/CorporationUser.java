package com.zhongyang.java.pojo;

public class CorporationUser {
    private String userId;

    private String banklicense;

    private String busicode;

    private String category;

    private String contractsealcode;

    private String legalpersonid;

    private String name;

    private String orgcode;

    private Boolean rtpo;

    private String shortname;

    private String taxcode;

    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getBanklicense() {
        return banklicense;
    }

    public void setBanklicense(String banklicense) {
        this.banklicense = banklicense == null ? null : banklicense.trim();
    }

    public String getBusicode() {
        return busicode;
    }

    public void setBusicode(String busicode) {
        this.busicode = busicode == null ? null : busicode.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getContractsealcode() {
        return contractsealcode;
    }

    public void setContractsealcode(String contractsealcode) {
        this.contractsealcode = contractsealcode == null ? null : contractsealcode.trim();
    }

    public String getLegalpersonid() {
        return legalpersonid;
    }

    public void setLegalpersonid(String legalpersonid) {
        this.legalpersonid = legalpersonid == null ? null : legalpersonid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode == null ? null : orgcode.trim();
    }

    public Boolean getRtpo() {
        return rtpo;
    }

    public void setRtpo(Boolean rtpo) {
        this.rtpo = rtpo;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname == null ? null : shortname.trim();
    }

    public String getTaxcode() {
        return taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode == null ? null : taxcode.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}