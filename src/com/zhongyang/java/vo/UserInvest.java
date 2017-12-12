package com.zhongyang.java.vo;

public class UserInvest {
	
	private String id;
	
	private String mobile;
	
	private String name;
	
	private String lastLoginTime;
	
	private boolean ifInvest;
	
	private int count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public boolean getIfInvest() {
		return ifInvest;
	}

	public void setIfInvest(boolean ifInvest) {
		this.ifInvest = ifInvest;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
