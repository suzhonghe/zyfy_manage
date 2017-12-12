package com.zhongyang.java.vo;

import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.vo
 *@filename ManagerData.java
 *@date 2017年9月6日上午9:17:20
 *@author suzh
 */
public class ManagerData {
	
	private BigDecimal loanAmount;//借贷余额
	
	private BigDecimal feeAmount;//未偿还利息
	
	private int loanPerson;//借款人数
	
	private int inPerson;//出借人数
	
	private double limitDays;//平均借款期限；
	
	private BigDecimal freedomAmount=new BigDecimal(0);//自然人平均借款额度
	
	private BigDecimal legonAmount;//法人及其他组织平均借款额度
	
	private double rate;//平均借款利率
	
	private BigDecimal overAmount=new BigDecimal(0);//逾期金额
	
	private BigDecimal bidAmount=new BigDecimal(0);//不良金额
	
	private double overRate=0;//逾期率
	
	private double bidRate=0;//坏账率

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public int getLoanPerson() {
		return loanPerson;
	}

	public void setLoanPerson(int loanPerson) {
		this.loanPerson = loanPerson;
	}

	public int getInPerson() {
		return inPerson;
	}

	public void setInPerson(int inPerson) {
		this.inPerson = inPerson;
	}

	public double getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(double limitDays) {
		this.limitDays = limitDays;
	}

	public BigDecimal getFreedomAmount() {
		return freedomAmount;
	}

	public void setFreedomAmount(BigDecimal freedomAmount) {
		this.freedomAmount = freedomAmount;
	}

	public BigDecimal getLegonAmount() {
		return legonAmount;
	}

	public void setLegonAmount(BigDecimal legonAmount) {
		this.legonAmount = legonAmount;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public BigDecimal getOverAmount() {
		return overAmount;
	}

	public void setOverAmount(BigDecimal overAmount) {
		this.overAmount = overAmount;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public double getOverRate() {
		return overRate;
	}

	public void setOverRate(double overRate) {
		this.overRate = overRate;
	}

	public double getBidRate() {
		return bidRate;
	}

	public void setBidRate(double bidRate) {
		this.bidRate = bidRate;
	}
	
}
