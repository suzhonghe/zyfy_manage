package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.fund.FundRecordStatus;

public class UserFund {
    private String userid;

    private BigDecimal availableAmount;

    private BigDecimal depositAmount;

    private BigDecimal dueInAmount;

    private BigDecimal dueOutAmount;

    private BigDecimal frozenAmount;

    private Date timecreated;

    private Date timelastupdated;

    private BigDecimal transferAmount;

    private BigDecimal withdrawAmount;

    private int status;

    private BigDecimal all_revenue;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getDueInAmount() {
        return dueInAmount;
    }

    public void setDueInAmount(BigDecimal dueInAmount) {
        this.dueInAmount = dueInAmount;
    }

    public BigDecimal getDueOutAmount() {
        return dueOutAmount;
    }

    public void setDueOutAmount(BigDecimal dueOutAmount) {
        this.dueOutAmount = dueOutAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public Date getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Date timecreated) {
        this.timecreated = timecreated;
    }

    public Date getTimelastupdated() {
        return timelastupdated;
    }

    public void setTimelastupdated(Date timelastupdated) {
        this.timelastupdated = timelastupdated;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getAll_revenue() {
        return all_revenue;
    }

    public void setAll_revenue(BigDecimal all_revenue) {
        this.all_revenue = all_revenue;
    }

}