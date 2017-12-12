package com.zhongyang.java.dao;


import java.util.List;

import com.zhongyang.java.pojo.FreshAmount;

public interface FreshAmountDao {
	
	public int insertFreshAmount(FreshAmount freshAmount);
	
	public int insertBatchFreshAmount(List<FreshAmount> list);
	
	public int updateFreshAmount(FreshAmount freshAmount);
	
	//更改抵现券过期作废
	public int updateFreshAmounts();
	
}
