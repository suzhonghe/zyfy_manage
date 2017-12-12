package com.zhongyang.java.vo;

import java.util.List;

public class RegisterUserInfo {
	
	private List<UserInvest> userInvest;
	
	public RegisterUserInfo() {
		super();
	}

	public RegisterUserInfo(List<UserInvest> userInvest) {
		super();
		this.userInvest = userInvest;
	}

	public List<UserInvest> getUserInvest() {
		return userInvest;
	}

	public void setUserInvest(List<UserInvest> userInvest) {
		this.userInvest = userInvest;
	}

}
