package com.zhongyang.java.biz;

import com.zhongyang.java.system.Message;

public interface TimerBiz {
	/**
	 * 
	* @Title: timerBidPublished 
	* @Description:定时发标 
	* @return Message    返回类型 
	* @throws
	 */
	public Message timerBidPublished(String loanId,String publishedTime);
}
