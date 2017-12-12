package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class UploadProjects {
	
	private BigDecimal amount; 
	
	private String method; 
	
	private String title; 
	
	private Integer months; 
	
	private String guaranteeRealm; 
	
	private BigDecimal surplusAmount; 
	
	private String userName; 
	
	private Integer days; 
	
	private String timeSubmit;

	public String getTimeSubmit() {
		return timeSubmit;
	}

	public void setTimeSubmit(String timeSubmit) {
		this.timeSubmit = timeSubmit;
	}
	
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public String getGuaranteeRealm() {
		return guaranteeRealm;
	}

	public void setGuaranteeRealm(String guaranteeRealm) {
		this.guaranteeRealm = guaranteeRealm;
	}

	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
