package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.LoanRepayMentDao;
import com.zhongyang.java.pojo.LoanRepayment;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.LoanRepayMentService;
import com.zhongyang.java.vo.loan.LoanRepayMent;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月28日 下午1:56:28
* 类说明
*/
@Service
public class LoanRepayMentServiceImpl implements LoanRepayMentService{
    @Autowired
	private LoanRepayMentDao loanRepayMentDao;
    /*
	 * 创建还款计划
	 */
	public int createLoanRepayMent(LoanRepayment loanRepayment) {
		
		return loanRepayMentDao.createLoanRepayMent(loanRepayment);
	}
	
	@Override
	public LoanRepayment userFundRateSum(LoanRepayment loanId) throws Exception {
		
		return loanRepayMentDao.userFundRateSum(loanId);
	}

    @Override
    public int updateStatus(LoanRepayment loanRepayment) {
        return loanRepayMentDao.updateStatus(loanRepayment);
    }

	@Override
	public void updateStatusByTime(String status) {
		loanRepayMentDao.updateStatusByTime(status);
	}

	@Override
	public int findByLoanId(LoanRepayment loanRepayMent) {
		return loanRepayMentDao.findByLoanId(loanRepayMent);
	}

}
