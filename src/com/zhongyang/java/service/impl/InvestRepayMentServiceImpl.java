package com.zhongyang.java.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.InvestRepaymentDao;
import com.zhongyang.java.pojo.InvestRepayment;
import com.zhongyang.java.service.InvestRepayMentService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月28日 下午7:31:43
* 类说明
*/
@Service
public class InvestRepayMentServiceImpl implements InvestRepayMentService{

    @Autowired
	InvestRepaymentDao investRepaymentDao;

	public int createInvestRepayMent(InvestRepayment investRepayment) {
		return investRepaymentDao.createInvestRepayMent(investRepayment);
	}

    @Override
    public int updateStatus(InvestRepayment investRepayment) {
        return investRepaymentDao.updateStatus(investRepayment);
    }

	@Override
	public void updateInvestAmount(int currentPriod, String loanid, BigDecimal pre) {
		investRepaymentDao.updateInvestAmount(currentPriod, loanid, pre);
		
	}

	@Override
	public InvestRepayment getInvestAllRate(String investId) throws Exception {
		
		return investRepaymentDao.getInvestAllRate(investId);
	}

}
