package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.ContractPojo;
import com.zhongyang.java.pojo.InvestRepayment;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.system.page.Page;

public interface ContractDao {

	public List<ContractPojo> getContractInfo(String loanId);
	public List<InvestRepayment> getInvestRepaymets(InvestRepayment investRepayment);
	
	public List<Loan> uncontractLoans(Page<Loan> page);
}
