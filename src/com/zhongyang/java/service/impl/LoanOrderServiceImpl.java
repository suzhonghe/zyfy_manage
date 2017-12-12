package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.LoanOrderDao;
import com.zhongyang.java.pojo.LoanOrder;
import com.zhongyang.java.service.LoanOrderService;
/**
 * 
* @Title: LoanOrderServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description: 标的订单业务接口实现 
* @author 苏忠贺   
* @date 2015年12月9日 下午2:44:37 
* @version V1.0
 */
@Service
public class LoanOrderServiceImpl implements LoanOrderService{
	
	@Autowired
	private LoanOrderDao loanOrderDao;

	@Override
	public LoanOrder queryLoanOrderByOrderId(String orderId) throws Exception {

		return loanOrderDao.selectLoanOrderByOrderId(orderId);
	}

	@Override
	public int modifyLoanOrder(LoanOrder order) throws Exception {
		return loanOrderDao.updateLoanOrder(order);
	}
}
