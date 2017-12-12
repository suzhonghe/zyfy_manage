package com.zhongyang.java.vo.settle;


public enum SettleType implements SettleTypeEnum {
	
	RECHARGE("充值对账"),
	TENDER_TRANSFER("标的转账对账"),
	TRANSFER("转账对账"),
	WITHDRAW("提现对账");


	private final String key;

	private SettleType(String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}
	public static String getDescription(SettleType type){
		String description;
		switch(type){
			case RECHARGE:description= "充值对账";break;
			case TENDER_TRANSFER:description= "标的转账对账";break;
			case TRANSFER:description= "转账对账";break;
			case WITHDRAW:description= "提现对账";break;
			default:description= null;break;
		}
		return description;
	}
}
