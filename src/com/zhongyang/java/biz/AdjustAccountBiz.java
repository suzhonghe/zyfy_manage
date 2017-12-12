package com.zhongyang.java.biz;

import com.zhongyang.java.system.Message;

public interface AdjustAccountBiz {
	
	public Message adjustInvestData(String orderId,String status);
}
