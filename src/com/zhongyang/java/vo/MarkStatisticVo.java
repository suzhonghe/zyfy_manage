package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class MarkStatisticVo {
	private String staffName; //员工姓名
	private String mobile;  //员工手机号
	private String orgName; //机构名称
	private BigDecimal totalAmount;//总投资金额
	private BigDecimal yearAmount;//年化总投资金额
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getYearAmount() {
		return yearAmount;
	}
	public void setYearAmount(BigDecimal yearAmount) {
		this.yearAmount = yearAmount;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	
}
