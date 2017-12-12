package com.zhongyang.java.system.uitl;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.LoanBiz;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.vo.LoanVo;
@Scope("prototype")
@Service
public class TimerPublishedLoan extends TimerTask{
	
	private String loanId;
	
	@Autowired
	private LoanBiz loanBiz;
	
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	@Override
	public void run() {
		LoanVo loanVo=new LoanVo();
		Loan loan=new Loan();
		loan.setId(loanId);
		loanVo.setLoan(loan);
		loanBiz.loanPublished(loanVo);
	}

}
