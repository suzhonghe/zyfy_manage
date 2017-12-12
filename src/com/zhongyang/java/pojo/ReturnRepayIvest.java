package com.zhongyang.java.pojo;

import java.math.BigDecimal;

import com.zhongyang.java.vo.loan.LoanRepayMent;

/**
 * Created by Matthew on 2015/12/29.
 */
public class ReturnRepayIvest {
    private String id;
    private String userid;
    private BigDecimal amount;
    private BigDecimal amountinterest;
    private BigDecimal amountprincipal;
    private String repayId;
    private String loanid;
    private int currentPeriod;
    private LoanRepayMent status;
        
    public LoanRepayMent getStatus() {
		return status;
	}

	public void setStatus(LoanRepayMent status) {
		this.status = status;
	}

	public String getLoanid() {
		return loanid;
	}

	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}

	public int getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(int currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public String getRepayId() {
		return repayId;
	}

	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}

	public BigDecimal getAmountinterest() {
		return amountinterest;
	}

	public void setAmountinterest(BigDecimal amountinterest) {
		this.amountinterest = amountinterest;
	}

	public BigDecimal getAmountprincipal() {
		return amountprincipal;
	}

	public void setAmountprincipal(BigDecimal amountprincipal) {
		this.amountprincipal = amountprincipal;
	}
	private String accountId;
    private String accountName;

    public ReturnRepayIvest() {}

    public ReturnRepayIvest(String id, String userid, BigDecimal amount, String accountId, String accountName) {
        this.id = id;
        this.userid = userid;
        this.amount = amount;
        this.accountId = accountId;
        this.accountName = accountName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
