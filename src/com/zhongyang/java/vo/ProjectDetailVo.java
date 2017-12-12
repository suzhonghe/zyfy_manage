package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.List;

public class ProjectDetailVo {
	
	private ProjectDetail projectDetail;
	
	private List<LoanContent>loans;
	
	private BigDecimal totalAmount;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public ProjectDetail getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetail projectDetail) {
		this.projectDetail = projectDetail;
	}

	public List<LoanContent> getLoans() {
		return loans;
	}

	public void setLoans(List<LoanContent> loans) {
		this.loans = loans;
	}
}
