package com.zhongyang.java.vo;

public class PtpMerQuery {
	
	private String ret_code;//返回码
	
	private String ret_msg;//返回信息
	
	private String query_mer_id;//联动开立的商户号
	
	private String balance;//商户结算账户的可用余额，单位为元
	
	private String account_type;//取值范围：	01：商户现金账户 	02：商户手续费账户

	private String account_state;//账户状态1-正常 2-挂失 3-冻结 4-锁定 9-销户

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

	public String getQuery_mer_id() {
		return query_mer_id;
	}

	public void setQuery_mer_id(String query_mer_id) {
		this.query_mer_id = query_mer_id;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getAccount_state() {
		return account_state;
	}

	public void setAccount_state(String account_state) {
		this.account_state = account_state;
	}
}
