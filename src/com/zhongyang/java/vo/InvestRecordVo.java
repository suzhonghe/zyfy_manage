package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.List;

public class InvestRecordVo {
	
	private int userNumber;//邀请人数
	
	private int investNumber;//邀请投资书
	
	private BigDecimal totalAmount;//投资总额
	
	private BigDecimal yearAmount;//年化投资额
	
	private List<InvestRecord>investRecords;//分页记录数
	
	private int pageNumber;//当前页码
	
	private int pageSize;//当前页显示条数
	
	private int totalPage;//总页数

	public BigDecimal getYearAmount() {
		return yearAmount;
	}

	public void setYearAmount(BigDecimal yearAmount) {
		this.yearAmount = yearAmount;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public int getInvestNumber() {
		return investNumber;
	}

	public void setInvestNumber(int investNumber) {
		this.investNumber = investNumber;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<InvestRecord> getInvestRecords() {
		return investRecords;
	}

	public void setInvestRecords(List<InvestRecord> investRecords) {
		this.investRecords = investRecords;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totlaPage) {
		this.totalPage = totlaPage;
	}
}
