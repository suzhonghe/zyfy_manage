package com.zhongyang.java.vo.fund;
/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月24日 下午1:31:31
* 类说明
*/
public class UmpResultVO {
private String service;
	
	private String  sign_type;
	private String mer_id;
	private String  version;
	private String sign;
	private String plain;
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getMer_id() {
		return mer_id;
	}

	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPlain() {
		return plain;
	}

	public void setPlain(String plain) {
		this.plain = plain;
	}
}
