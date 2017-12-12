package com.zhongyang.java.vo;

public class Registered {
	
	private String mobile;
	private String passphrase;
	private String referral_id;
	private String source;
	private String regcode;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	public String getReferral_id() {
		return referral_id;
	}
	public void setReferral_id(String referral_id) {
		this.referral_id = referral_id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRegcode() {
		return regcode;
	}
	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	public Registered() {}
	
	public Registered(String mobile, String passphrase, String referral_id, String source, String regcode) {
		this.mobile = mobile;
		this.passphrase = passphrase;
		this.referral_id = referral_id;
		this.source = source;
		this.regcode = regcode;
	}
	@Override
	public String toString() {
		return "Registered [mobile=" + mobile + ", passphrase=" + passphrase + ", referral_id=" + referral_id
				+ ", source=" + source + ", regcode=" + regcode + "]";
	}
		
}
