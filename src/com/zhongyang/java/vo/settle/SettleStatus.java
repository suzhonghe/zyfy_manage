package com.zhongyang.java.vo.settle;


public enum SettleStatus implements SettleStatusEnum {
	
	SUCCESSFUL("成功"),
	FAILED("失败");


	private final String key;

	private SettleStatus(String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}
	public static String getDescription(SettleStatus status){
		String description;
		switch(status){
			case SUCCESSFUL:description= "成功";break;
			case FAILED:description= "失败";break;
			default:description= null;break;
		}
		return description;
	}

}
