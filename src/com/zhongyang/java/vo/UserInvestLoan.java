package com.zhongyang.java.vo;

import java.util.List;

public class UserInvestLoan {
	
	private List<InvestLoan> userInvests;
	
	public UserInvestLoan() {
		super();
	}

	public UserInvestLoan(List<InvestLoan> userInvests) {
		super();
		this.userInvests = userInvests;
	}

	public List<InvestLoan> getUserInvests() {
		return userInvests;
	}

	public void setUserInvests(List<InvestLoan> userInvests) {
		this.userInvests = userInvests;
	}
}
