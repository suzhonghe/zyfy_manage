package com.zhongyang.java.vo;
/**
 * 
* @Title: TranseqSearchCondition.java 
* @Package com.zhongyang.java.vo 
* @Description:账户流水查询条件类
* @author 苏忠贺   
* @date 2016年3月29日 上午10:14:22 
* @version V1.0
 */
public class TranseqSearchCondition {
	
	private String account_id;//账户号
	
	private String partic_user_id;//转入用户号
	
	private String account_type;//账户类型
	
	private String acc_category;//账户分类
	
	private String start_date;//交易起始时间
	
	private String end_date;//交易结束时间
	
	private String page_num;//起始页码

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getPartic_user_id() {
		return partic_user_id;
	}

	public void setPartic_user_id(String partic_user_id) {
		this.partic_user_id = partic_user_id;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getAcc_category() {
		return acc_category;
	}

	public void setAcc_category(String acc_category) {
		this.acc_category = acc_category;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getPage_num() {
		return page_num;
	}

	public void setPage_num(String page_num) {
		this.page_num = page_num;
	}

}
