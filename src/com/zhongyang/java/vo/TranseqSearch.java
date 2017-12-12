package com.zhongyang.java.vo;

import java.util.List;

/**
 * 
* @Title: TranseqSearch.java 
* @Package com.zhongyang.java.vo 
* @Description:账户流水查询结果
* @author 苏忠贺   
* @date 2016年3月29日 上午10:17:28 
* @version V1.0
 */
public class TranseqSearch {
	
	private String ret_code;
	
	private String ret_msg;
	
	private String total_num;
	
	private List<TransDetail>transDetails;

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}

	public List<TransDetail> getTransDetails() {
		return transDetails;
	}

	public void setTransDetails(List<TransDetail> transDetails) {
		this.transDetails = transDetails;
	}
}
