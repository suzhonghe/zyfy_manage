package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

public class UserFundVo {
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
    private int investNumber;//投资次数
    private int loanNumber;//借款次数
    private String  fundStatus;//资金状态
    private String fundType;//资金类型
    private BigDecimal totalAssets;//资产总额
    //订单Id
    private String orderId;
    
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

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

	public String getFundStatus() {
		return fundStatus;
	}

	public void setFundStatus(String fundStatus) {
		this.fundStatus = fundStatus;
	}

	public int getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(int loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public int getInvestNumber() {
		return investNumber;
	}

	public void setInvestNumber(int investNumber) {
		this.investNumber = investNumber;
	}

	public BigDecimal getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

	

	
    
}