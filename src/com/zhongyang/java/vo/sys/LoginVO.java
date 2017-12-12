package com.zhongyang.java.vo.sys;

public class LoginVO {
	
	private String loginname;
	private String passphrase;
	private int loginCounter;
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	public int getLoginCounter() {
		return loginCounter;
	}
	public void setLoginCounter(int loginCounter) {
		this.loginCounter = loginCounter;
	}
	public LoginVO() {
	}
	public LoginVO(String loginname, String passphrase, long loginFirstTryTime, long loginLastTryTime,
			int loginCounter) {
		this.loginname = loginname;
		this.passphrase = passphrase;
		this.loginCounter = loginCounter;
	}
	@Override
	public String toString() {
		return "LoginVO [loginname=" + loginname + ", passphrase=" + passphrase + ", loginCounter=" + loginCounter + "]";
	}
	
	
	
}
