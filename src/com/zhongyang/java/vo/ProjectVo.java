package com.zhongyang.java.vo;

import java.math.BigDecimal;

import com.zhongyang.java.vo.loan.Method;

public class ProjectVo {
	
	private String id;
	
	private BigDecimal amount;
	
	private Method method;
	
	private String title;
	
	private Integer months;
	
	private String guaranteeRealm;
	
	private BigDecimal surplusAmount;
	
	private String userName;
	
	private Integer status;
	
	private Integer days;
	
	private String timeSubmit;
			
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimeSubmit() {
		return timeSubmit;
	}

	public void setTimeSubmit(String timeSubmit) {
		this.timeSubmit = timeSubmit;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;

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

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
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
}
