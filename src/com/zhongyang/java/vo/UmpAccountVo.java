package com.zhongyang.java.vo;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年3月10日 下午3:10:29
* 类说明
*/
public class UmpAccountVo {
	    
	    private String accountId;//第三方账号Id
	    private String accountName;//第三方账号name:UBxxx
	    private String userid;//用户Id
	    private String contactMobile;//用户电话号码
	    private String cardId;//银行卡号
	    private String gateId;//银行编号
	    private String custName;//用户姓名
	    private String accountState;//账户状态1-正常 2-挂失 3-冻结 4-锁定 9-销户
	    private String availableAmount;//账户余额
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getContactMobile() {
			return contactMobile;
		}
		public void setContactMobile(String contactMobile) {
			this.contactMobile = contactMobile;
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
		public String getCustName() {
			return custName;
		}
		public void setCustName(String custName) {
			this.custName = custName;
		}
		public String getAccountState() {
			return accountState;
		}
		public void setAccountState(String accountState) {
			this.accountState = accountState;
		}
		public String getAvailableAmount() {
			return availableAmount;
		}
		public void setAvailableAmount(String availableAmount) {
			this.availableAmount = availableAmount;
		}
		public String getAccountId() {
			return accountId;
		}
		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}
		
	    
	    
	    
	    
}
