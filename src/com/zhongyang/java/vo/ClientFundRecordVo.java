package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月25日 上午11:19:12
* 类说明
*/
public class ClientFundRecordVo {
	    private String id;
   
	    private String account;

	    private BigDecimal amount;

	    private String description;

	    private FundRecordOperation operation;
	    private String operations;

	    private String orderid;

	    private String recordpriv;

	    private FundRecordStatus status;
	    private String statuss;

	    private String timerecorded;
	    
	    private Date timerecordeds;
	    
	    private Date startTime;
	    
	    private Date endTime;
	    
	    private String transactionid;

	    private FundRecordType type;
	    private String types;

	    private String userid;
	    
	    private String userName;

	    private String entityid;

	    private String realm;
        
	    private String mobile;
	    
	    private String availableAmount;//可用金额
	    private String balanceAmount;//账户余额：
	    private String frozenAmount;//冻结余额：

	    List<ClientFundRecordVo> applications = new ArrayList();

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

	    public String getTimerecorded() {
			return timerecorded;
		}

		public void setTimerecorded(String timerecorded) {
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

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public List<ClientFundRecordVo> getApplications() {
			return applications;
		}

		public void setApplications(List<ClientFundRecordVo> applications) {
			this.applications = applications;
		}

		public String getAvailableAmount() {
			return availableAmount;
		}

		public void setAvailableAmount(String availableAmount) {
			this.availableAmount = availableAmount;
		}

		public String getBalanceAmount() {
			return balanceAmount;
		}

		public void setBalanceAmount(String balanceAmount) {
			this.balanceAmount = balanceAmount;
		}

		public String getFrozenAmount() {
			return frozenAmount;
		}

		public void setFrozenAmount(String frozenAmount) {
			this.frozenAmount = frozenAmount;
		}

		public String getOperations() {
			return operations;
		}

		public void setOperations(String operations) {
			this.operations = operations;
		}

		public String getStatuss() {
			return statuss;
		}

		public void setStatuss(String statuss) {
			this.statuss = statuss;
		}

		public String getTypes() {
			return types;
		}

		public void setTypes(String types) {
			this.types = types;
		}

		public Date getTimerecordeds() {
			return timerecordeds;
		}

		public void setTimerecordeds(Date timerecordeds) {
			this.timerecordeds = timerecordeds;
		}
		
	    
}
