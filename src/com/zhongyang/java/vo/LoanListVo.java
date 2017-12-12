package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.loan.LoanStatus;
import com.zhongyang.java.vo.loan.Method;

/**
 * 
* @Title: LoanListVo.java 
* @Package com.zhongyang.java.vo 
* @Description:标的列表类
* @author 苏忠贺   
* @date 2015年12月23日 下午7:19:39 
* @version V1.0
 */
public class LoanListVo {
	
	private String id;
	
	private String loanUserId;
	
	private BigDecimal amount;
	
	private Method method;
	
	private String title;
	
	private Integer months;
	
	private String guaranteeRealm;
	
	private String userName; 
	
	private LoanStatus status;
	
	private BigDecimal rate;
	
	private double addRate;
	
	private BigDecimal planing;
	
	private String timeOpen;
	
	private Date nowTime;
	
	private Long divTime;
	
	private String timeFailed;
	
	private String productName;
	
	private String timeSettled;
	
	private String reason;
	
	private String timeScheduled;
	
	private String umpAvailableAmount;
	
	private BigDecimal zycfAvailableAmount;
											
	public double getAddRate() {
		return addRate;
	}

	public void setAddRate(double addRate) {
		this.addRate = addRate;
	}

	public String getUmpAvailableAmount() {
		return umpAvailableAmount;
	}

	public void setUmpAvailableAmount(String umpAvailableAmount) {
		this.umpAvailableAmount = umpAvailableAmount;
	}

	public BigDecimal getZycfAvailableAmount() {
		return zycfAvailableAmount;
	}

	public void setZycfAvailableAmount(BigDecimal zycfAvailableAmount) {
		this.zycfAvailableAmount = zycfAvailableAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTimeScheduled() {
		return timeScheduled;
	}

	public void setTimeScheduled(String timeScheduled) {
		this.timeScheduled = timeScheduled;
	}
	
	public String getTimeSettled() {
		return timeSettled;
	}

	public void setTimeSettled(String timeSettled) {
		this.timeSettled = timeSettled;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTimeFailed() {
		return timeFailed;
	}

	public void setTimeFailed(String timeFailed) {
		this.timeFailed = timeFailed;
	}

	public Long getDivTime() {
		return divTime;
	}

	public void setDivTime(Long divTime) {
		this.divTime = divTime;
	}

	public String getTimeOpen() {
		return timeOpen;
	}

	public void setTimeOpen(String timeOpen) {
		this.timeOpen = timeOpen;
	}

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public BigDecimal getPlaning() {
		return planing;
	}

	public void setPlaning(BigDecimal planing) {
		this.planing = planing;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanUserId() {
		return loanUserId;
	}

	public void setLoanUserId(String loanUserId) {
		this.loanUserId = loanUserId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
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

	public String getGuaranteeRealm() {
		return guaranteeRealm;
	}

	public void setGuaranteeRealm(String guaranteeRealm) {
		this.guaranteeRealm = guaranteeRealm;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}
}
