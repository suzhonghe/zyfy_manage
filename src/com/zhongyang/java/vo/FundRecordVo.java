package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年3月8日 上午11:18:32
* 类说明
*/
public class FundRecordVo {
	    private String id;

	    private String dtype;

	    private BigDecimal amount;

	    private String description;

	    private String  operation;

	    private String orderid;

	    private String recordpriv;

	    private String status;

	    private Date timerecorded;

	    private String transactionid;

	    private String type;

	    private String entityid;

	    private String fundAccountId;

	    private String realm;

	    private String userId;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDtype() {
			return dtype;
		}

		public void setDtype(String dtype) {
			this.dtype = dtype;
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
			this.description = description;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public String getOrderid() {
			return orderid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}

		public String getRecordpriv() {
			return recordpriv;
		}

		public void setRecordpriv(String recordpriv) {
			this.recordpriv = recordpriv;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
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
			this.transactionid = transactionid;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getEntityid() {
			return entityid;
		}

		public void setEntityid(String entityid) {
			this.entityid = entityid;
		}

		public String getFundAccountId() {
			return fundAccountId;
		}

		public void setFundAccountId(String fundAccountId) {
			this.fundAccountId = fundAccountId;
		}

		public String getRealm() {
			return realm;
		}

		public void setRealm(String realm) {
			this.realm = realm;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
	    
	    
}
