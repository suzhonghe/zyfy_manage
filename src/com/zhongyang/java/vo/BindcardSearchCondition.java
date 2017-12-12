package com.zhongyang.java.vo;
/**
 * 
* @Title: BindcardSearchCondition.java 
* @Package com.zhongyang.java.vo 
* @Description:绑卡查询条件类
* @author 苏忠贺   
* @date 2016年3月28日 下午5:05:36 
* @version V1.0
 */
public class BindcardSearchCondition {
	
	private String order_id;//商户订单号
	
	private String mer_date;//商户订单日期   格式：YYYYMMDD

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
}
