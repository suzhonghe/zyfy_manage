package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.*;
import com.zhongyang.java.system.page.Page;

public interface RepaymentService {
	public List<RepayQuery> repayQuery(Page<RepayQuery> page);
	
	public RepaymentByID repaymentById(RepaymentByID repaymentByID);

    public List<ReturnRepayIvest> repaymentInvestByLoanId(RepayIvest repayIvest);

    public LoanRepayment getLoanRepayment(LoanRepayment loanRepayment);

    public List<RepayQuery> repaymentQuery(Page<RepayQuery> page);
    public int updateInvestRepaymentById(InvestRepayment investRepayment);
    
    public List<LoanRepayment> queryByLoanId(LoanRepayment loanRepayment);

}
