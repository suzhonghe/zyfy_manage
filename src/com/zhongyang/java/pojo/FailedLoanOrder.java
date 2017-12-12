package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.fund.FundRecordStatus;

/**
 * 
* @Title: FailedLoanOrder.java 
* @Package com.zhongyang.java.pojo 
* @Description:流标订单实体
* @author 苏忠贺   
* @date 2015年12月28日 上午9:11:17 
* @version V1.0
 */
public class FailedLoanOrder {
	
	private String id;
	
	private String investId;//INVESTID 投资者ID
	
	private String loanId;//  LOANID  标的ID
	
	private String orderDate;// ORDERDATE 订单创建日期
	
	private String orderId;// ORDERID 订单号
	
	private FundRecordStatus status;// STATUS 订单状态
	
	private Date timeRecorded;// TIMERECORDED 记录日期
	
	private BigDecimal amount;// AMOUNT 金额

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}

	public Date getTimeRecorded() {
		return timeRecorded;
	}

	public void setTimeRecorded(Date timeRecorded) {
		this.timeRecorded = timeRecorded;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
