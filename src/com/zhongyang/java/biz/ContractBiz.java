package com.zhongyang.java.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;

public interface ContractBiz {

	public Message generateContracts(HttpServletRequest request,String loanId);
	public Page<Loan> uncontractLoans(Page<Loan> page);
}
