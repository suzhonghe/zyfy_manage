package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;

public class ClientFundRecord {
    private String id;

    private String account;

    private BigDecimal amount;

    private String description;

    private FundRecordOperation operation;

    private String orderid;

    private String recordpriv;

    private FundRecordStatus status;

    private Date timerecorded;

    private String transactionid;

    private FundRecordType type;

    private String userid;

    private String entityid;

    private String realm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getRecordpriv() {
        return recordpriv;
    }

    public void setRecordpriv(String recordpriv) {
        this.recordpriv = recordpriv == null ? null : recordpriv.trim();
    }

    public Date getTimerecorded() {
        return timerecorded;
    }

    public void setTimerecorded(Date timerecorded) {
        this.timerecorded = timerecorded;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid == null ? null : transactionid.trim();
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid == null ? null : entityid.trim();
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm == null ? null : realm.trim();
    }

	public FundRecordOperation getOperation() {
		return operation;
	}

	public void setOperation(FundRecordOperation operation) {
		this.operation = operation;
	}

	public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}

	public FundRecordType getType() {
		return type;
	}

	public void setType(FundRecordType type) {
		this.type = type;
	}
    
}