package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.FreshAmount;

/**
 * 体验金、红包操作接口
 * @author Administrator
 *
 */
public interface FreshAmountService {

	public int addFreshAmount(FreshAmount freshAmount);
	
	public int addBatchFreshAmount(List<FreshAmount> list);
	
	public int modifyFreshAmount(FreshAmount freshAmount);
	
	//更改抵现券过期作废
	public int modifyFreshAmounts();
	
}
