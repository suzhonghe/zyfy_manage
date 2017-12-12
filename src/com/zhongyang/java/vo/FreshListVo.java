
package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 红包或体验金发放记录表
 * @author Administrator
 *
 */
public class FreshListVo {
	private String userid;  //用户id
	private BigDecimal amount; //体验金金额或使用红包投资金额
	private String triggerType; //触发方式
	private String getDate;  //获得时间
	private String mobile; //手机号
	private int type;  //红包： 1，体验金：2
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	
	public String getGetDate() {
		return getDate;
	}
	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
