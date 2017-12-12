package com.zhongyang.java.vo.loan;

/**
 * 
 * @Title: FundRecordStatus.java
 * @Package com.zhongyang.java.vo
 * @Description: 偿还方式枚举
 * @author 苏忠贺
 * @date 2015年12月17日 下午2:45:47
 * @version V1.0
 */
public enum Method implements LoanEnum {
	
	MonthlyInterest("按月付息到期还本", "还款压力小", true, "按期付息到期还本"),
    EqualInstallment("按月等额本息", "还款便捷", true, "等额本息"),
    EqualPrincipal("按月等额本金", "总利息最低", true, "等额本金"),
    BulletRepayment("一次性还本付息", "短期首选", true, "一次性还本付息"),
    EqualInterest("月平息", "实际利率最高", true, "平息"), 
    YearlyInterest("按年付息到期还本", "还款压力小", false, null);

	private final String key;

	private Method(String key,String description,Boolean bo,String str) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

}
