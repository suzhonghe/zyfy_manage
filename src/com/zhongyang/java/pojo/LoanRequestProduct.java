package com.zhongyang.java.pojo;

public class LoanRequestProduct {
    private String id;

    private String description;

    private Integer maxamount;

    private Integer maxrate;

    private Integer minamount;

    private Integer minrate;

    private String name;

    private String productkey;

    private String repaymethod;

    private Integer maxInvestAmount;

    private Integer maxtimes;

    private Integer maxtotalamount;

    private Integer minInvestAmount;

    private Integer stepamount;

    private Integer maxDurationDays;

    private Integer maxDurationMonths;

    private Integer maxDurationYears;

    private Integer minDurationDays;

    private Integer minDurationMonths;

    private Integer minDurationYears;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(Integer maxamount) {
        this.maxamount = maxamount;
    }

    public Integer getMaxrate() {
        return maxrate;
    }

    public void setMaxrate(Integer maxrate) {
        this.maxrate = maxrate;
    }

    public Integer getMinamount() {
        return minamount;
    }

    public void setMinamount(Integer minamount) {
        this.minamount = minamount;
    }

    public Integer getMinrate() {
        return minrate;
    }

    public void setMinrate(Integer minrate) {
        this.minrate = minrate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProductkey() {
        return productkey;
    }

    public void setProductkey(String productkey) {
        this.productkey = productkey == null ? null : productkey.trim();
    }

    public String getRepaymethod() {
        return repaymethod;
    }

    public void setRepaymethod(String repaymethod) {
        this.repaymethod = repaymethod == null ? null : repaymethod.trim();
    }

    public Integer getMaxInvestAmount() {
        return maxInvestAmount;
    }

    public void setMaxInvestAmount(Integer maxInvestAmount) {
        this.maxInvestAmount = maxInvestAmount;
    }

    public Integer getMaxtimes() {
        return maxtimes;
    }

    public void setMaxtimes(Integer maxtimes) {
        this.maxtimes = maxtimes;
    }

    public Integer getMaxtotalamount() {
        return maxtotalamount;
    }

    public void setMaxtotalamount(Integer maxtotalamount) {
        this.maxtotalamount = maxtotalamount;
    }

    public Integer getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(Integer minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public Integer getStepamount() {
        return stepamount;
    }

    public void setStepamount(Integer stepamount) {
        this.stepamount = stepamount;
    }

    public Integer getMaxDurationDays() {
        return maxDurationDays;
    }

    public void setMaxDurationDays(Integer maxDurationDays) {
        this.maxDurationDays = maxDurationDays;
    }

    public Integer getMaxDurationMonths() {
        return maxDurationMonths;
    }

    public void setMaxDurationMonths(Integer maxDurationMonths) {
        this.maxDurationMonths = maxDurationMonths;
    }

    public Integer getMaxDurationYears() {
        return maxDurationYears;
    }

    public void setMaxDurationYears(Integer maxDurationYears) {
        this.maxDurationYears = maxDurationYears;
    }

    public Integer getMinDurationDays() {
        return minDurationDays;
    }

    public void setMinDurationDays(Integer minDurationDays) {
        this.minDurationDays = minDurationDays;
    }

    public Integer getMinDurationMonths() {
        return minDurationMonths;
    }

    public void setMinDurationMonths(Integer minDurationMonths) {
        this.minDurationMonths = minDurationMonths;
    }

    public Integer getMinDurationYears() {
        return minDurationYears;
    }

    public void setMinDurationYears(Integer minDurationYears) {
        this.minDurationYears = minDurationYears;
    }
}