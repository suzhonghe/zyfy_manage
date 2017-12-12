package com.zhongyang.java.pojo;

public class SettleOrder {
    private String Id;

    private String orderIdFee;

    private String settleId;
    
    private String loanId;
    
    private String umpAccountName;
    
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getOrderIdFee() {
		return orderIdFee;
	}

	public void setOrderIdFee(String orderIdFee) {
		this.orderIdFee = orderIdFee;
	}

	public String getSettleId() {
		return settleId;
	}

	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getUmpAccountName() {
		return umpAccountName;
	}

	public void setUmpAccountName(String umpAccountName) {
		this.umpAccountName = umpAccountName;
	}


    
   
}