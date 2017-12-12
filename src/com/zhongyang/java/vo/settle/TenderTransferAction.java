package com.zhongyang.java.vo.settle;


public enum TenderTransferAction implements TenderTransferActionEnum {
	
	INFLOW("标的进账","1"),
	OUTFLOW("标的出账","2");

	
	private final String key;
	
	private final String num;

	private TenderTransferAction(String key,String num) {
		this.key = key;
		this.num = num;
	}

	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public String getNum(){
		return num;
	}
	public static String getDescription(String num){
		String description;
		switch(num){
			case "1":description= "标的进账";break;
			case "2":description= "标的出账";break;
			default:description= null;break;
		}
		return description;
	}
}
