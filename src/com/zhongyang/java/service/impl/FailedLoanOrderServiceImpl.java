package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.FailedLoanOrderDao;
import com.zhongyang.java.pojo.FailedLoanOrder;
import com.zhongyang.java.service.FailedLoanOrderService;

/**
 * 
* @Title: FailedLoanOrderServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:业务接口实现 
* @author 苏忠贺   
* @date 2015年12月28日 上午9:34:10 
* @version V1.0
 */
@Service
public class FailedLoanOrderServiceImpl implements FailedLoanOrderService{
	
	@Autowired
	private FailedLoanOrderDao failedLoanOrderDao;
	
	@Override
	public void addFailedLoanOrder(FailedLoanOrder failedLoanOrder) throws Exception {
		failedLoanOrderDao.insertFailedLoanOrder(failedLoanOrder);
	}

	@Override
	public FailedLoanOrder queryByOrderId(String orderId) throws Exception {
		return failedLoanOrderDao.selectByOrderId(orderId);
	}

	@Override
	public List<FailedLoanOrder> queryByLoanId(String loanId) throws Exception {
		return failedLoanOrderDao.selectByLoanId(loanId);
	}

	@Override
	public void modifyFailedLoanOrder(FailedLoanOrder failedLoanOrder) throws Exception {
		failedLoanOrderDao.updateFailedLoanOrder(failedLoanOrder);
	}

}
