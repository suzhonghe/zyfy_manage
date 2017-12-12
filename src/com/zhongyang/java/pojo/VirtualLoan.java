package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Administrator
 *体验标信息表
 */
public class VirtualLoan {

	private String id;
	
	private BigDecimal amount;
	
	private double rate;
	
	private Date createTime;
	
	private int loanDay;
	
	private Integer status;
	
	private String method;
	
	private String title;

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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getLoanDay() {
		return loanDay;
	}

	public void setLoanDay(int loanDay) {
		this.loanDay = loanDay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
