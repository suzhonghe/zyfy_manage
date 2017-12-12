package com.zhongyang.java.vo;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2016年1月14日 下午3:08:31 类说明
 */
public class UserInfoVo {
	private String id;
	private String userId;
	private String idnumber;
	private String loginname;
	private String mobile;
	private String name;
	private String authentication;// 认证
	private String noRepaymentaGreement;// 无密还款协议
	private String birthDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
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

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getNoRepaymentaGreement() {
		return noRepaymentaGreement;
	}

	public void setNoRepaymentaGreement(String noRepaymentaGreement) {
		this.noRepaymentaGreement = noRepaymentaGreement;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

}
