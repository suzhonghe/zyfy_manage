package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

public class InviteStatisticVo {

	private String inviteName;//邀请人姓名
	private String mobile;//手机号码
	private BigDecimal investAmount;//投资金额
	private String title;//标的名称
	private String timeSettled;//结算时间
	private String status;//投标状态
//	private BigDecimal loanAmount;//标的金额
	private String rate;//利率
	private String months;//周期
	private String submitTime;//投资时间
	private BigDecimal yearAmount;//年化投资金额
	
	private int addRate;//年化投资金额
	

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getInviteName() {
		return inviteName;
	}
	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}
	public BigDecimal getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTimeSettled() {
		return timeSettled;
	}
	public void setTimeSettled(String timeSettled) {
		this.timeSettled = timeSettled;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public BigDecimal getYearAmount() {
		return yearAmount;
	}
	public void setYearAmount(BigDecimal yearAmount) {
		this.yearAmount = yearAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAddRate() {
		return addRate;
	}
	public void setAddRate(int addRate) {
		this.addRate = addRate;
	}
	
	
	
	
}
