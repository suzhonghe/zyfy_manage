package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class ReturnRepayment {
   
    private Integer currentperiod;
    
    private String status;

    private BigDecimal amount;

    private String duedate;

	public Integer getCurrentperiod() {
		return currentperiod;
	}

	public void setCurrentperiod(Integer currentperiod) {
		this.currentperiod = currentperiod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	
}