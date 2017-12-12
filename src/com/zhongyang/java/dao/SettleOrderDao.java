package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.SettleOrder;

/**
 * 
* @Title: UmpAccountDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 联动账户信息 
* @author 苏忠贺   
* @date 2015年12月7日 下午5:04:52 
* @version V1.0
 */
public interface SettleOrderDao {
	
	/*
	 * 根据UmpAccount查询联动信息
	 */

	public int addSettleOrder(SettleOrder settleOrder);
	/*
     * 根据FeeID查询转账给借款人的orderID
     */
	public SettleOrder getSettleloanByOrderId(SettleOrder settleOrder);
	
	
	public SettleOrder getOrderIdByfeeIdSettle(SettleOrder settleOrder);
}
