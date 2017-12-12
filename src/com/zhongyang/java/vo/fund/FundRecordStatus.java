package com.zhongyang.java.vo.fund;
/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 上午9:53:08
* 类说明:自己状态枚举类
*/
public enum FundRecordStatus implements BaseEnum {
	    INITIALIZED("初始"),
	    PROCESSING("处理中"),
	    AUDITING("审核中"),//目前主要用于取现申请复核
	    PAY_PENDING("支付结果待查"),// 目前用于银联单笔代付没有实时返回最终成功或者失败结果的情况
	    CUT_PENDING("代扣结果待查"),// 目前用于银联单笔代扣没有实时返回最终成功或者失败结果的情况
	    SUCCESSFUL("成功"),
	    FAILED("失败"),
	    REJECTED("拒绝"),
	    CANCELED("取消"),
	    SETTLED("已结算"),
	    CLEARED("已还清");

	    private final String key;

	    private FundRecordStatus(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }
	    
}
