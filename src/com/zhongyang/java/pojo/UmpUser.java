package com.zhongyang.java.pojo;

public class UmpUser {
	
    private String merchantcode;//MERCHANTCODE

    private String userId;//USERID//旧平台记录方式和新平台不一样

    private String accountName;//ACCOUNTNAME//UB...

    private String accountId;//ACCOUNTID

    private Boolean mobile;//MOBILE

    private Boolean idnumber;//IDNUMBER

	public String getMerchantcode() {
		return merchantcode;
	}

	public void setMerchantcode(String merchantcode) {
		this.merchantcode = merchantcode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Boolean getMobile() {
		return mobile;
	}

	public void setMobile(Boolean mobile) {
		this.mobile = mobile;
	}

	public Boolean getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(Boolean idnumber) {
		this.idnumber = idnumber;
	}
    
    
    
}