package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.fund.FundRecordStatus;

public class LoanOrder {
	
	private String id;

    private String investId;

    private String loanId;

    private Date orderDate;

    private String orderId;

    private FundRecordStatus status;

    private Date timeRecorded;
    
    private BigDecimal amount;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus stat) {
		this.status = stat;
	}

	public Date getTimeRecorded() {
		return timeRecorded;
	}

	public void setTimeRecorded(Date timeRecorded) {
		this.timeRecorded = timeRecorded;
	}
}