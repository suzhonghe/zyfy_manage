package com.zhongyang.java.vo.fund;
/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 上午9:30:41
* 类说明:自己记录操作信息
*/
public enum FundRecordOperation implements BaseEnum{
	    FREEZE("冻结"),
	    RELEASE("解冻"),
	    IN("资金转入"),
	    OUT("资金转出");
           
	    private final String key;

	    private FundRecordOperation(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }
         
}
