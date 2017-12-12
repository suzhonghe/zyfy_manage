package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class TransferRecordVO {
    
	private String orderid;
		
	private String outaccountid;//转出账号
	
	private String outName;//转出用户名
	
	private String inName;//转入用户名
		
	private String inaccountid;//转入账号

    private BigDecimal amount;

    private String settledate;

    private String settletime;

    private String transactionid;
    
	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOutaccountid() {
		return outaccountid;
	}

	public void setOutaccountid(String outaccountid) {
		this.outaccountid = outaccountid;
	}

	public String getInaccountid() {
		return inaccountid;
	}

	public void setInaccountid(String inaccountid) {
		this.inaccountid = inaccountid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSettledate() {
		return settledate;
	}

	public void setSettledate(String settledate) {
		this.settledate = settledate;
	}

	public String getSettletime() {
		return settletime;
	}

	public void setSettletime(String settletime) {
		this.settletime = settletime;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
}
