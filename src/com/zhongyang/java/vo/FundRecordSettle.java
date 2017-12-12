package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;

public class FundRecordSettle {
	
	private String orderId;
	
	private String accountName;
	
	private String mobile;
	
	private String realName;
	
	private BigDecimal amount;
	
	private FundRecordType type;
	
	private String strType;
	
	private FundRecordStatus status;
	
	private String strStatus;
	
	private Date date;
	
	private String strDate;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public FundRecordType getType() {
		return type;
	}

	public void setType(FundRecordType type) {
		this.type = type;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}
}
