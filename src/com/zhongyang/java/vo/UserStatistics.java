package com.zhongyang.java.vo;

public class UserStatistics {
	
	private int registerUserCount;//注册人数
	
	private int authenUserCount;//认证人数
	
	private int noAutherUserCount;//未认证人数
	
	private int investUserCount;//投资人数

	public int getRegisterUserCount() {
		return registerUserCount;
	}

	public void setRegisterUserCount(int registerUserCount) {
		this.registerUserCount = registerUserCount;
	}

	public int getAuthenUserCount() {
		return authenUserCount;
	}

	public void setAuthenUserCount(int authenUserCount) {
		this.authenUserCount = authenUserCount;
	}

	public int getNoAutherUserCount() {
		return noAutherUserCount;
	}

	public void setNoAutherUserCount(int noAutherUserCount) {
		this.noAutherUserCount = noAutherUserCount;
	}

	public int getInvestUserCount() {
		return investUserCount;
	}

	public void setInvestUserCount(int investUserCount) {
		this.investUserCount = investUserCount;
	}

}
