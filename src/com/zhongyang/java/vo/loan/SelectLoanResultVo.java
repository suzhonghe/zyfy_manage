package com.zhongyang.java.vo.loan;

import java.math.BigDecimal;

public class SelectLoanResultVo {
	
	private int count;
	
	private BigDecimal totalAmount;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
