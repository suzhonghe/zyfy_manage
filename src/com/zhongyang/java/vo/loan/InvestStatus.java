package com.zhongyang.java.vo.loan;

public enum InvestStatus implements InvestStatusEnum {
	    INITIALIZED("初始"),
	    AUDITING("待结算"),
	    FAILED("失败"),
	    SETTLED("已结算"),
	    CLEARED("已还清");

	    private final String key;

	    private InvestStatus(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }
	
}
