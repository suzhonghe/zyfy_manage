package com.zhongyang.java.pojo;

public class LoanRequestWithBLOBs extends Project {
    private String description;

    private String guaranteeinfo;

    private String mortgageinfo;

    private String riskinfo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getGuaranteeinfo() {
        return guaranteeinfo;
    }

    public void setGuaranteeinfo(String guaranteeinfo) {
        this.guaranteeinfo = guaranteeinfo == null ? null : guaranteeinfo.trim();
    }

    public String getMortgageinfo() {
        return mortgageinfo;
    }

    public void setMortgageinfo(String mortgageinfo) {
        this.mortgageinfo = mortgageinfo == null ? null : mortgageinfo.trim();
    }

    public String getRiskinfo() {
        return riskinfo;
    }

    public void setRiskinfo(String riskinfo) {
        this.riskinfo = riskinfo == null ? null : riskinfo.trim();
    }
}