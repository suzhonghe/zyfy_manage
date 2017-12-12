package com.zhongyang.java.vo;

import com.zhongyang.java.system.Message;

public class RegisterUser {
	
	private Message message;
	
	private RegisterUserInfo registerUserInfo;//注册列表
	
	private UserStatistics UserStatistics;//综合统计表1
	
	private UserInvestLoan userInvestLoan;//综合统计表2
	
	private FreshInfoStatistics FreshInfoStatistics;//综合统计表3
	
	private InvestLevel investLevel;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public RegisterUserInfo getRegisterUserInfo() {
		return registerUserInfo;
	}

	public void setRegisterUserInfo(RegisterUserInfo registerUserInfo) {
		this.registerUserInfo = registerUserInfo;
	}

	public UserStatistics getUserStatistics() {
		return UserStatistics;
	}

	public void setUserStatistics(UserStatistics userStatistics) {
		UserStatistics = userStatistics;
	}

	public UserInvestLoan getUserInvestLoan() {
		return userInvestLoan;
	}

	public void setUserInvestLoan(UserInvestLoan userInvestLoan) {
		this.userInvestLoan = userInvestLoan;
	}

	public FreshInfoStatistics getFreshInfoStatistics() {
		return FreshInfoStatistics;
	}

	public void setFreshInfoStatistics(FreshInfoStatistics freshInfoStatistics) {
		FreshInfoStatistics = freshInfoStatistics;
	}

	public InvestLevel getInvestLevel() {
		return investLevel;
	}

	public void setInvestLevel(InvestLevel investLevel) {
		this.investLevel = investLevel;
	}
}
