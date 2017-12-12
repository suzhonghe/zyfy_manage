package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ExperienceAmount {
	
	private String id;
	
	private String name;
	
	private BigDecimal amount;
	
	private Date createTime;
	
	private int limitDays;
	
	private Date endTime;
	
	private Date useTime;

	private Boolean enable;

	private Boolean enableUse;
	
	private String userId;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(int limitDays) {
		this.limitDays = limitDays;
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

	public Boolean isEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean isEnableUse() {
		return enableUse;
	}

	public void setEnableUse(Boolean enableUse) {
		this.enableUse = enableUse;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
