package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.*;

import com.zhongyang.java.system.page.Page;



import java.util.List;

public interface RepaymentDao {

	 public List<RepayQuery> repayQuery(Page<RepayQuery> page);
	 
	 public List<RepayQuery> repaymentQuery(Page<RepayQuery> page);
	 
	 public RepaymentByID repaymentById(RepaymentByID repaymentByID);

     public List<ReturnRepayIvest> repaymentInvest(RepayIvest repayIvest);

     public LoanRepayment getLoanRepayment(LoanRepayment loanRepayment);
     

     public InvestRepayment getInvestRepaymentById(InvestRepayment investRepayment);
     public int updateInvestRepaymentById(InvestRepayment investRepayment);

     public List<LoanRepayment> selectByLoanId(LoanRepayment loanRepayment);

}
