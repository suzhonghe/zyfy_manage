package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.loan.LoanStatus;
import com.zhongyang.java.vo.loan.Method;

public class LoanContent {
	
	private String serial;//唯一编号
	
	private BigDecimal amount;//贷款金额
	
	private BigDecimal bidAmount;//实际投标金额
	
	private String method;//还款方式
	
	private String status;//借款状态
	
	private Date timeOpen;//开始募集时间
	
	private Date timeSettled;//结标时间
	
	private String title;//借款标标题
	
	private Integer months;//借款期限(月)

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTimeOpen() {
		return timeOpen;
	}

	public void setTimeOpen(Date timeOpen) {
		this.timeOpen = timeOpen;
	}

	public Date getTimeSettled() {
		return timeSettled;
	}

	public void setTimeSettled(Date timeSettled) {
		this.timeSettled = timeSettled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

}
