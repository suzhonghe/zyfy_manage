package com.zhongyang.java.vo;

public class UploadLoans {

	private String loanName;
	
	private String productName;
	
	private String guaranteeRealm;
	
	private String amount;
	
	private String status;
	
	private String timeOpen;
	
	private String timeSettled;
	
	public String getTimeSettled() {
		return timeSettled;
	}

	public void setTimeSettled(String timeSettled) {
		this.timeSettled = timeSettled;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getGuaranteeRealm() {
		return guaranteeRealm;
	}

	public void setGuaranteeRealm(String guaranteeRealm) {
		this.guaranteeRealm = guaranteeRealm;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeOpen() {
		return timeOpen;
	}

	public void setTimeOpen(String timeOpen) {
		this.timeOpen = timeOpen;
	}
}
