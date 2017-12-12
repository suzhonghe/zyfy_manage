package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;

public class FundRecord {
    private String id;

    private String dtype;

    private BigDecimal amount;
    
    private BigDecimal aviAmount;//交易后余额

    private String description;

    private FundRecordOperation operation;

    private String orderid;

    private String recordpriv;

    private FundRecordStatus status;

    private Date timerecorded;

    private String transactionid;

    private FundRecordType type;

    private String entityid;

    private String fundAccountId;

    private String realm;

    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype == null ? null : dtype.trim();
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

    public BigDecimal getAviAmount() {
		return aviAmount;
	}

	public void setAviAmount(BigDecimal aviAmount) {
		this.aviAmount = aviAmount;
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

	public void setTimerecorded(Date timerecorded) {
        this.timerecorded = timerecorded;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid == null ? null : transactionid.trim();
    }


    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid == null ? null : entityid.trim();
    }

    public String getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(String fundAccountId) {
        this.fundAccountId = fundAccountId == null ? null : fundAccountId.trim();
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm == null ? null : realm.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

	public Date getTimerecorded() {
		return timerecorded;
	}
    
}