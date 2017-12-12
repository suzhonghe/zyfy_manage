package com.zhongyang.java.vo;
/**
 * 
* @Title: BindcardSearch.java 
* @Package com.zhongyang.java.vo 
* @Description: 绑卡查询结果
* @author 苏忠贺   
* @date 2016年3月28日 下午5:06:19 
* @version V1.0
 */
public class BindcardSearch {
	
	private String ret_code;//返回码
	
	private String ret_msg;//返回信息
	
	private String order_id;//订单号
	
	private String mer_date;//订单日期
	
	private String tran_state;//交易状态女

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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMer_date() {
		return mer_date;
	}

	public void setMer_date(String mer_date) {
		this.mer_date = mer_date;
	}

	public String getTran_state() {
		return tran_state;
	}

	public void setTran_state(String tran_state) {
		this.tran_state = tran_state;
	}
}
