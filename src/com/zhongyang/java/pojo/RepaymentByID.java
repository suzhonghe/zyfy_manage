package com.zhongyang.java.pojo;

import java.math.BigDecimal;

/**
 * Created by Matthew on 2015/12/29.
 */
public class RepaymentByID {
    private String id;

    private String title;
    private String name;
    private int currentPeriod;
    private BigDecimal money;
    private BigDecimal available_amount;
    private BigDecimal amountInterest;
    private BigDecimal amountPrincipal;
    public BigDecimal getAmountInterest() {
		return amountInterest;
	}

	public void setAmountInterest(BigDecimal amountInterest) {
		this.amountInterest = amountInterest;
	}

	public BigDecimal getAmountPrincipal() {
		return amountPrincipal;
	}

	public void setAmountPrincipal(BigDecimal amountPrincipal) {
		this.amountPrincipal = amountPrincipal;
	}

	private BigDecimal amount;
    private String loan_id;
    private String userId;
    private String accountId;
    private String accountName;
    private BigDecimal loanInterestFee; //借款人利息管理费

    public RepaymentByID() {
    }

    public RepaymentByID(String id, String title, String name, int currentPeriod, BigDecimal money, BigDecimal available_amount, BigDecimal amount, String loan_id, String userId, String accountId, String accountName) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.currentPeriod = currentPeriod;
        this.money = money;
        this.available_amount = available_amount;
        this.amount = amount;
        this.loan_id = loan_id;
        this.userId = userId;
        this.accountId = accountId;
        this.accountName = accountName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(int currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAvailable_amount() {
        return available_amount;
    }

    public void setAvailable_amount(BigDecimal available_amount) {
        this.available_amount = available_amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

	public BigDecimal getLoanInterestFee() {
		return loanInterestFee;
	}

	public void setLoanInterestFee(BigDecimal loanInterestFee) {
		this.loanInterestFee = loanInterestFee;
	}
    
    
}
