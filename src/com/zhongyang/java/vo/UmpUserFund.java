package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class UmpUserFund {
	
	private String userId;//用户ID
	
	private String userName;//用户名
	
	private String mobile;//手机号
	
	private BigDecimal aviAmount;//平台余额
	
	private BigDecimal frozenAmount;//冻结金额
	
	private String umpAviAmount;//联动余额
	
	private String umpAccountName;//联动用户名
	
	private String umpAccountId;//联动账户ID

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getAviAmount() {
		return aviAmount;
	}

	public void setAviAmount(BigDecimal aviAmount) {
		this.aviAmount = aviAmount;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public String getUmpAviAmount() {
		return umpAviAmount;
	}

	public void setUmpAviAmount(String umpAviAmount) {
		this.umpAviAmount = umpAviAmount;
	}

	public String getUmpAccountName() {
		return umpAccountName;
	}

	public void setUmpAccountName(String umpAccountName) {
		this.umpAccountName = umpAccountName;
	}

	public String getUmpAccountId() {
		return umpAccountId;
	}

	public void setUmpAccountId(String umpAccountId) {
		this.umpAccountId = umpAccountId;
	}

}
