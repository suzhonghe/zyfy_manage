package com.zhongyang.java.vo.fund;
/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月22日 下午2:12:32
* 类说明:loanSettlevO，用户接受第三方返回的对象
*/
public class SettleFundRecordVo extends UmpResultVO{
  private String order_id;
  private String mer_date;
  private String trade_no;
  private String mer_check_date;
  private String ret_code;
  private String ret_msg;
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
public String getTrade_no() {
	return trade_no;
}
public void setTrade_no(String trade_no) {
	this.trade_no = trade_no;
}
public String getMer_check_date() {
	return mer_check_date;
}
public void setMer_check_date(String mer_check_date) {
	this.mer_check_date = mer_check_date;
}
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
  
  
  
}
