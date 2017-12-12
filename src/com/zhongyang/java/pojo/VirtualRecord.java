
package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 体验金或者红包交易信息记录表
 * @author Administrator
 *
 */
public class VirtualRecord {
	
	private String id;
	
	private BigDecimal amount;
	
	private BigDecimal realEarning;
	
	private Date createTime;
	
	private int loanDay;
	
	private Date repayTime;
	
	private String vloanId;
	
	private String status;
	
	private boolean flag;
	
	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRealEarning() {
		return realEarning;
	}

	public void setRealEarning(BigDecimal realEarning) {
		this.realEarning = realEarning;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getLoanDay() {
		return loanDay;
	}

	public void setLoanDay(int loanDay) {
		this.loanDay = loanDay;
	}

	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

	public String getVloanId() {
		return vloanId;
	}

	public void setVloanId(String vloanId) {
		this.vloanId = vloanId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
