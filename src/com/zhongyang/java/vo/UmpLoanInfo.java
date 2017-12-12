package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class UmpLoanInfo {
	
	private String project_id;//标的ID
	
	private String project_account_id;//标的账户号
	
	private String project_state;//标的状态
	
	private String project_account_state;//标的账户状态
	
	private BigDecimal balance;//标的余额,以元为单位
	
	private String ret_code;
	
	private String ret_msg;

	public String getProject_account_state() {
		return project_account_state;
	}

	public void setProject_account_state(String project_account_state) {
		this.project_account_state = project_account_state;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getProject_account_id() {
		return project_account_id;
	}

	public void setProject_account_id(String project_account_id) {
		this.project_account_id = project_account_id;
	}

	public String getProject_state() {
		return project_state;
	}

	public void setProject_state(String project_state) {
		this.project_state = project_state;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
}
