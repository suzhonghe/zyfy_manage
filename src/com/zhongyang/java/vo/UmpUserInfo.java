package com.zhongyang.java.vo;

import java.math.BigDecimal;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年3月16日 上午11:09:07
* 类说明：接收第三方用户信息
*/
public class UmpUserInfo {
	
	private String retCode;
	private String retMsg;
	private String platUserId;
	private String accountId;
	private String custName;
	private String identityType;
	private String identityCode;
	private String contactMobile;
	private String mailAddr;
	private String accountState;
	private BigDecimal balance;
	private String cardId;
	private String gateId;
	private String userAgreementList;
	
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getPlatUserId() {
		return platUserId;
	}
	public void setPlatUserId(String platUserId) {
		this.platUserId = platUserId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityCode() {
		return identityCode;
	}
	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	public String getAccountState() {
		return accountState;
	}
	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getUserAgreementList() {
		return userAgreementList;
	}
	public void setUserAgreementList(String userAgreementList) {
		this.userAgreementList = userAgreementList;
	}
    
	
}
