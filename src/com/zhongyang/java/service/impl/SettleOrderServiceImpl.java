package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.SettleOrderDao;
import com.zhongyang.java.pojo.SettleOrder;
import com.zhongyang.java.service.SettleOrderService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午3:58:35
* 类说明:标的交易
*/
@Service
public class SettleOrderServiceImpl implements SettleOrderService{
	@Autowired
	SettleOrderDao settleOrderDao;
    /*
     * 标的结算--扣除管理费
     */
	public int addSettleOrder(SettleOrder settleOrder){
		return settleOrderDao.addSettleOrder(settleOrder);
	}
	
	/*
     * 根据FeeID查询转账给借款人的orderID
     */
	public SettleOrder getSettleloanByOrderId(SettleOrder settleOrder) {
		
		return settleOrderDao.getSettleloanByOrderId(settleOrder);
	}

	@Override
	public SettleOrder getOrderIdByfeeIdSettle(SettleOrder settleOrder) throws Exception {
		return settleOrderDao.getOrderIdByfeeIdSettle(settleOrder);
	}

}
