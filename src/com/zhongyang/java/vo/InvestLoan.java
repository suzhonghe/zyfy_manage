package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class InvestLoan {
	
	private String type;//标的类型；
	
	private BigDecimal scaleAmount;//规模
	
	private BigDecimal yearAmount;
	
	private int totalPerson;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getScaleAmount() {
		return scaleAmount;
	}

	public void setScaleAmount(BigDecimal scaleAmount) {
		this.scaleAmount = scaleAmount;
	}

	public BigDecimal getYearAmount() {
		return yearAmount;
	}

	public void setYearAmount(BigDecimal yearAmount) {
		this.yearAmount = yearAmount;
	}

	public int getTotalPerson() {
		return totalPerson;
	}

	public void setTotalPerson(int totalPerson) {
		this.totalPerson = totalPerson;
	}
}
