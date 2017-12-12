package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ContractPojo {
	String serial;
	String signDate;
	Date settleDate;
	String CJRName;
	String CJRIdNumber;
	String CJRloginName;
	String JKRName;
	String JKRIdNumber;
	String JKRloginName;
	String guaranteeCorporationName;
	String guaranteeCorporationAddress;
	String guaranteeCorporationUserName;
	String loanPurpose;
	BigDecimal amount;
	String cxrDate;
	String endDate;
	String investId;
	String amountUpper;
	String repaymentNo;
	public String getRepaymentNo() {
		return repaymentNo;
	}
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}
	public String getAmountUpper() {
		return amountUpper;
	}
	public void setAmountUpper(String amountUpper) {
		this.amountUpper = amountUpper;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getCxrDate() {
		return cxrDate;
	}
	public void setCxrDate(String cxrDate) {
		this.cxrDate = cxrDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public String getCJRName() {
		return CJRName;
	}
	public void setCJRName(String cJRName) {
		CJRName = cJRName;
	}
	public String getCJRIdNumber() {
		return CJRIdNumber;
	}
	public void setCJRIdNumber(String cJRIdNumber) {
		CJRIdNumber = cJRIdNumber;
	}
	public String getCJRloginName() {
		return CJRloginName;
	}
	public void setCJRloginName(String cJRloginName) {
		CJRloginName = cJRloginName;
	}
	public String getJKRName() {
		return JKRName;
	}
	public void setJKRName(String jKRName) {
		JKRName = jKRName;
	}
	public String getJKRIdNumber() {
		return JKRIdNumber;
	}
	public void setJKRIdNumber(String jKRIdNumber) {
		JKRIdNumber = jKRIdNumber;
	}
	public String getJKRloginName() {
		return JKRloginName;
	}
	public void setJKRloginName(String jKRloginName) {
		JKRloginName = jKRloginName;
	}
	public String getGuaranteeCorporationName() {
		return guaranteeCorporationName;
	}
	public void setGuaranteeCorporationName(String guaranteeCorporationName) {
		this.guaranteeCorporationName = guaranteeCorporationName;
	}
	public String getGuaranteeCorporationAddress() {
		return guaranteeCorporationAddress;
	}
	public void setGuaranteeCorporationAddress(String guaranteeCorporationAddress) {
		this.guaranteeCorporationAddress = guaranteeCorporationAddress;
	}
	public String getGuaranteeCorporationUserName() {
		return guaranteeCorporationUserName;
	}
	public void setGuaranteeCorporationUserName(String guaranteeCorporationUserName) {
		this.guaranteeCorporationUserName = guaranteeCorporationUserName;
	}
	public String getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}
	BigDecimal loanRate;
}
