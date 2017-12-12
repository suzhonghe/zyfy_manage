package com.zhongyang.java.vo;
/**
 * 
* @Title: TransferSearchConditon.java 
* @Package com.zhongyang.java.vo 
* @Description: 交易查询条件类
* @author 苏忠贺   
* @date 2016年3月28日 上午11:13:29 
* @version V1.0
 */
public class TransferSearchConditon {

	private String order_id;//查询订单号
	
	private String mer_date;//订单生成日期，格式YYYYMMDD
	
	private String busi_type;//取值范围：01充值、02提现、03标的转账、04转账

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

	public String getBusi_type() {
		return busi_type;
	}

	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
}
