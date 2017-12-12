package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

public class InvestRecord {
	
	private String userId;//投资人ID
	
	private String userName;//投资人
	
	private String title;//标的名称
	
	private int months;//标的期限
	
	private BigDecimal amount;//投资金额
	
	private Date date;//投资日期

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	
}
