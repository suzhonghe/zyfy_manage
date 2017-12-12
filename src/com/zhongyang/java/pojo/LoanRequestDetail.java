package com.zhongyang.java.pojo;

public class LoanRequestDetail {
    private String requestid;

    private String industry;

    private String location;

    private Integer mincapitalratio;

    private String providerprojectcode;

    private String riskgrade;

    private String coborrowerId;

    private String coborrowerRealm;

    private String requestCreatorId;

    private String requestCreatorRealm;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid == null ? null : requestid.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getMincapitalratio() {
        return mincapitalratio;
    }

    public void setMincapitalratio(Integer mincapitalratio) {
        this.mincapitalratio = mincapitalratio;
    }

    public String getProviderprojectcode() {
        return providerprojectcode;
    }

    public void setProviderprojectcode(String providerprojectcode) {
        this.providerprojectcode = providerprojectcode == null ? null : providerprojectcode.trim();
    }

    public String getRiskgrade() {
        return riskgrade;
    }

    public void setRiskgrade(String riskgrade) {
        this.riskgrade = riskgrade == null ? null : riskgrade.trim();
    }

    public String getCoborrowerId() {
        return coborrowerId;
    }

    public void setCoborrowerId(String coborrowerId) {
        this.coborrowerId = coborrowerId == null ? null : coborrowerId.trim();
    }

    public String getCoborrowerRealm() {
        return coborrowerRealm;
    }

    public void setCoborrowerRealm(String coborrowerRealm) {
        this.coborrowerRealm = coborrowerRealm == null ? null : coborrowerRealm.trim();
    }

    public String getRequestCreatorId() {
        return requestCreatorId;
    }

    public void setRequestCreatorId(String requestCreatorId) {
        this.requestCreatorId = requestCreatorId == null ? null : requestCreatorId.trim();
    }

    public String getRequestCreatorRealm() {
        return requestCreatorRealm;
    }

    public void setRequestCreatorRealm(String requestCreatorRealm) {
        this.requestCreatorRealm = requestCreatorRealm == null ? null : requestCreatorRealm.trim();
    }
}