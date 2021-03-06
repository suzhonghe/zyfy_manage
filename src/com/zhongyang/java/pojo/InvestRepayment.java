package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.loan.LoanRepayMent;

public class InvestRepayment {
    private String id;

    private Integer currentperiod;

    private Integer relativeperiod;

    private BigDecimal repayamount;

    private Date repaydate;

    private LoanRepayMent status;

    private String sourceId;

    private String sourceRealm;

    private BigDecimal amountinterest;

    private BigDecimal amountoutstanding;

    private BigDecimal amountprincipal;

    private String duedate;

    private String investId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getCurrentperiod() {
        return currentperiod;
    }

    public void setCurrentperiod(Integer currentperiod) {
        this.currentperiod = currentperiod;
    }

    public Integer getRelativeperiod() {
        return relativeperiod;
    }

    public void setRelativeperiod(Integer relativeperiod) {
        this.relativeperiod = relativeperiod;
    }

    public BigDecimal getRepayamount() {
        return repayamount;
    }

    public void setRepayamount(BigDecimal repayamount) {
        this.repayamount = repayamount;
    }

    public Date getRepaydate() {
        return repaydate;
    }

    public void setRepaydate(Date repaydate) {
        this.repaydate = repaydate;
    }

   

    public LoanRepayMent getStatus() {
		return status;
	}

	public void setStatus(LoanRepayMent status) {
		this.status = status;
	}

	public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getSourceRealm() {
        return sourceRealm;
    }

    public void setSourceRealm(String sourceRealm) {
        this.sourceRealm = sourceRealm == null ? null : sourceRealm.trim();
    }

    public BigDecimal getAmountinterest() {
        return amountinterest;
    }

    public void setAmountinterest(BigDecimal amountinterest) {
        this.amountinterest = amountinterest;
    }

    public BigDecimal getAmountoutstanding() {
        return amountoutstanding;
    }

    public void setAmountoutstanding(BigDecimal amountoutstanding) {
        this.amountoutstanding = amountoutstanding;
    }

    public BigDecimal getAmountprincipal() {
        return amountprincipal;
    }

    public void setAmountprincipal(BigDecimal amountprincipal) {
        this.amountprincipal = amountprincipal;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate == null ? null : duedate.trim();
    }

    public String getInvestId() {
        return investId;
    }

    public void setInvestId(String investId) {
        this.investId = investId == null ? null : investId.trim();
    }
}