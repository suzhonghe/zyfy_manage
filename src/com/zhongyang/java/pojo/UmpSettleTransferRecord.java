package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class UmpSettleTransferRecord {
	
    private String orderid;

    private Date accountdate;

    private BigDecimal amount;

    private String inaccountid;

    private Date merdate;

    private String outaccountid;

    private Date settledate;

    private Date settletime;

    private String transactionid;

	public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getInaccountid() {
        return inaccountid;
    }

    public void setInaccountid(String inaccountid) {
        this.inaccountid = inaccountid == null ? null : inaccountid.trim();
    }

    public Date getMerdate() {
        return merdate;
    }

    public void setMerdate(Date merdate) {
        this.merdate = merdate;
    }

    public String getOutaccountid() {
        return outaccountid;
    }

    public void setOutaccountid(String outaccountid) {
        this.outaccountid = outaccountid == null ? null : outaccountid.trim();
    }

    public Date getSettledate() {
        return settledate;
    }

    public void setSettledate(Date settledate) {
        this.settledate = settledate;
    }

    public Date getSettletime() {
        return settletime;
    }

    public void setSettletime(Date settletime) {
        this.settletime = settletime;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid == null ? null : transactionid.trim();
    }
}