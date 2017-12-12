package com.zhongyang.java.vo;

import java.math.BigDecimal;

public class TenderRecordVO {
   
	private String orderid;
	
	private String title;
	
	private String outaccountid;
	
	private String inaccountid;

    private String action;
    
    private String transfertype;

    private BigDecimal amount;

    private String transactionid;

    private String settledate;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTransfertype() {
		return transfertype;
	}

	public void setTransfertype(String transfertype) {
		this.transfertype = transfertype;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getSettledate() {
		return settledate;
	}

	public void setSettledate(String settledate) {
		this.settledate = settledate;
	}
}
