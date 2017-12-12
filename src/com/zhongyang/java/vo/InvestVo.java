package com.zhongyang.java.vo;

import java.math.BigDecimal;

import com.zhongyang.java.vo.loan.InvestStatus;
import com.zhongyang.java.vo.loan.LoanStatus;
import com.zhongyang.java.vo.loan.Method;
/**
* @author 作者:zhaofq
* @version 创建时间：2016年3月8日 下午5:24:33
* 类说明
*/
public class InvestVo {
    
	private BigDecimal amount;//金额
	private String submittime;//投资时间
	private String id;//标的Id
	private String loanId;//标的Id
	private String title;//标题
	private BigDecimal rate;//利率
	private String Method;//还款方式Vo
	private int months;//期限
	private String status;//标的状态Vo
	private String userMobile;//标的状态
	private Method Methods;//还款方式
	private LoanStatus statuss;//标的状态
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getSubmittime() {
		return submittime;
	}
	public void setSubmittime(String submittime) {
		this.submittime = submittime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getMethod() {
		return Method;
	}
	public void setMethod(String method) {
		Method = method;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public Method getMethods() {
		return Methods;
	}
	public void setMethods(Method methods) {
		Methods = methods;
	}
	public LoanStatus getStatuss() {
		return statuss;
	}
	public void setStatuss(LoanStatus statuss) {
		this.statuss = statuss;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	
	
	
  
 
}
