package com.zhongyang.java.service.impl;

import java.util.List;

import com.zhongyang.java.dao.RepaymentDao;
import com.zhongyang.java.pojo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.service.RepaymentService;
import com.zhongyang.java.system.page.Page;


@Service
public class RepaymentServiceImpl implements RepaymentService{
	
	@Autowired
	private RepaymentDao repaymentDao;
	
	
	@Override
	public List<RepayQuery> repayQuery(Page<RepayQuery> page) {
        return repaymentDao.repayQuery(page);
	}


	@Override
	public RepaymentByID repaymentById(RepaymentByID repaymentByID) {
		return repaymentDao.repaymentById(repaymentByID);
	}

    @Override
    public List<ReturnRepayIvest> repaymentInvestByLoanId(RepayIvest repayIvest) {
        return repaymentDao.repaymentInvest(repayIvest);
    }

    @Override
    public LoanRepayment getLoanRepayment(LoanRepayment loanRepayment) {
        return repaymentDao.getLoanRepayment(loanRepayment);
    }


	@Override

	public List<RepayQuery> repaymentQuery(Page<RepayQuery> page) {
		// TODO Auto-generated method stub
		return repaymentDao.repaymentQuery(page);
	}
	public List<LoanRepayment> queryByLoanId(LoanRepayment loanRepayment) {
		return repaymentDao.selectByLoanId(loanRepayment);

	}


	@Override
	public int updateInvestRepaymentById(InvestRepayment investRepayment) {
		
		return repaymentDao.updateInvestRepaymentById(investRepayment);
	}
}
