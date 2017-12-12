package com.zhongyang.java.service;

import com.zhongyang.java.pojo.SettleOrder;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午3:58:35
* 类说明:标的交易
*/
public interface SettleOrderService {
    /*
     * 标的结算--oderid记录
     */
	public int addSettleOrder(SettleOrder settleOrder) throws Exception;
    /*
     * 根据FeeID查询转账给借款人的orderID
     */
	public SettleOrder getSettleloanByOrderId(SettleOrder settleOrder) throws Exception;
	
	public SettleOrder getOrderIdByfeeIdSettle(SettleOrder settleOrder)throws Exception;

}
