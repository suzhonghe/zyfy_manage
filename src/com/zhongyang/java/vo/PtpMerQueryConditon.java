package com.zhongyang.java.vo;
/**
 * 
* @Title: PtpMerQueryConditon.java 
* @Package com.zhongyang.java.vo 
* @Description:商户账户查询条件类
* @author 苏忠贺   
* @date 2016年3月28日 下午4:10:50 
* @version V1.0
 */
public class PtpMerQueryConditon {
	
	private String query_mer_id;//联动开立的商户号
	
	private String account_type;//取值范围：	01：商户现金账户 	02：商户手续费账户

	public String getQuery_mer_id() {
		return query_mer_id;
	}

	public void setQuery_mer_id(String query_mer_id) {
		this.query_mer_id = query_mer_id;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

}
