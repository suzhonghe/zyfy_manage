package com.zhongyang.java.vo.fund;
/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月13日 下午5:22:23
* 类说明
*/
public class BusinessToUserTransferRVo extends UmpResultVO{
 
	private String ret_code;
	private String ret_msg;
	private String query_mer_id;
	private String balance;
	private String account_type;
	private String account_state;
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
