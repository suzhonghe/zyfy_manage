package com.zhongyang.java.vo;

public class TransDetail {
	
	private String acc_check_date;//交易记账日期，格式：YYYYMMDD
	
	private String amount;//金额，以元为单位
	
	private String amt_type;//资金类型
	
	private String balance;//该笔交易后余额
	
	private String dc_mark;//借贷方向
	
	private String order_date;//商户订单日期
	
	private String order_id;//商户订单号
	
	private String trans_date;//交易日期
	
	private String trans_state;//交易状态
	
	private String trans_time;//交易时间
	
	private String trans_type;//交易类型

	public String getAcc_check_date() {
		return acc_check_date;
	}

	public void setAcc_check_date(String acc_check_date) {
		this.acc_check_date = acc_check_date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmt_type() {
		return amt_type;
	}

	public void setAmt_type(String amt_type) {
		this.amt_type = amt_type;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getDc_mark() {
		return dc_mark;
	}

	public void setDc_mark(String dc_mark) {
		this.dc_mark = dc_mark;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}

	public String getTrans_state() {
		return trans_state;
	}

	public void setTrans_state(String trans_state) {
		this.trans_state = trans_state;
	}

	public String getTrans_time() {
		return trans_time;
	}

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}

	public String getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
}
