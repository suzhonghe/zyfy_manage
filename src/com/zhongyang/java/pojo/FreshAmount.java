package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class FreshAmount {
	
	private String id;//`ID` varchar(36) NOT NULL COMMENT '主键'
	
	private Date createTime;//  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间'
	
	private BigDecimal amount;//  `AMOUNT` decimal(11,2) DEFAULT NULL COMMENT '金额'
	
	private int status;//  `STATUS` tinyint(1) DEFAULT '0' COMMENT '是否使用：1：已使用，0：未使用'
	
	private int type;//  `TYPE` int(1) DEFAULT NULL COMMENT '类型：1：红包，2 : 体验金利息'
	
	private String name;//  `NAME` varchar(36) DEFAULT NULL
	
	private int effectDay;//  `EFFECTDAY` int(10) DEFAULT NULL COMMENT '有效期（天数）'
	
	private Date endTime;//有效截止时间
	
	private Date useTime;//  `USETIME` datetime DEFAULT NULL COMMENT '使用时间'
	
	private BigDecimal amountLimit;//  `AMOUNTLIMIT` decimal(11,2) DEFAULT NULL COMMENT '激活投资金额限制'
	
	private int months;
	
	private String userId;//  `USERID` varchar(36) NOT NULL

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEffectDay() {
		return effectDay;
	}

	public void setEffectDay(int effectDay) {
		this.effectDay = effectDay;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public BigDecimal getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(BigDecimal amountLimit) {
		this.amountLimit = amountLimit;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	
}
