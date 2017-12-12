package com.zhongyang.java.vo.settle;


public enum TenderTransferType implements TenderTransferTypeEnum {
	
	INVEST("投标","01"),//01
	CREDIT_ASSIGN("债权购买","02"),//02
	REPAY("还款","03"),//03
	DISBURSE("偿付","04"),//04
	DISCOUNT("贴现","05"),//05
	FAILED_REFUND("流标后返款","51"),//51
	PLATFORM_CHARGE("平台收费","52"),//52
	LOAN("放款","53"),//53
	REPAY_REFUND("还款后返款","54"),//54
	DISBURSE_REFUND("偿付后返款","55"),//55
	CREDIT_REFUND("债权转让的返款","56"),//56
	DINVEST_REFUND("撤资后的返款","57");//57
	
	private final String key;
	
	private final String num;

	private TenderTransferType(String key,String num) {
		this.key = key;
		this.num=num;
	}

	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public String getNum() {
		return num;
	}
	public static String getDescription(String num){
		String description;
		switch(num){
			case "01":description= "投标";break;
			case "02":description= "债权购买";break;
			case "03":description= "还款";break;
			case "04":description= "偿付";break;
			case "05":description= "贴现";break;
			case "51":description= "流标后返款";break;
			case "52":description= "平台收费";break;
			case "53":description= "放款";break;
			case "54":description= "还款后返款";break;
			case "55":description= "偿付后返款";break;
			case "56":description= "债权转让的返款";break;
			case "57":description= "撤资后的返款";break;
			default:description= null;break;
		}
		return description;
	}
}
