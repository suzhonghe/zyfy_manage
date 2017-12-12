package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class OrganizationUser {
	
	private String id;
	
	private String name;//员工姓名
	
	private String mobile;//员工手机号码
	
	private int totalNumber;//邀请总人数
	
	private BigDecimal inviteAmount;//邀请年化投资总额

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public BigDecimal getInviteAmount() {
		return inviteAmount;
	}

	public void setInviteAmount(BigDecimal inviteAmount) {
		this.inviteAmount = inviteAmount;
	}
}
