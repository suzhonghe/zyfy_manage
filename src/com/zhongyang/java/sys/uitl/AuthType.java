package com.zhongyang.java.sys.uitl;

public enum AuthType {
	EMP(1,"员工管理"),
	USERMAN(2,"用户管理"),
	PROD(3,"产品管理"),
	PROJ(4,"项目管理"),
	BID(5,"标的管理"),
	REPAY(6,"还款管理"),
	BILLING(7,"对账管理"),
	FUND(8,"资金管理"),
	CREDITIAL(9,"债权管理"),
	BONUS(10,"奖励管理"),
	CMS(11,"CMS管理"),
	STATICS(12,"数据统计"),
	MESSAGE(13,"消息管理"),
	SYSTEM(14,"系统设置"),
	
	;
	
	
	private AuthType(int type, String description) {
		this.type = type;
		this.description = description;
	}
	int type;
	String description;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
