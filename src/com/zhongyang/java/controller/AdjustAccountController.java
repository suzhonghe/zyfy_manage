package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.AdjustAccountBiz;
import com.zhongyang.java.biz.LoanBiz;
import com.zhongyang.java.biz.RepaymentBiz;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;

@Controller
public class AdjustAccountController extends BaseController{
	
	@Autowired
	private AdjustAccountBiz adjustAccountBiz;
	
	@Autowired
	private RepaymentBiz repaymentBiz;
	
	@Autowired
	private LoanBiz loanBiz;
	
	@FireAuthority(authorities=Authorities.MAINTAINADJ)
	@RequestMapping(value="/adjustInvestData")
	public @ResponseBody Message adjustInvestData(String orderId,String status){
		return adjustAccountBiz.adjustInvestData(orderId,status);
	}
	
	@FireAuthority(authorities=Authorities.MAINTAINADJ)
    @RequestMapping(value="/adjustRepay")
    public @ResponseBody Message adjustRepay(String loanId,int per){
    	return repaymentBiz.adjustRepay(loanId, per);
    }
	
	/*
	 * 修改标的状态
	 */
	@FireAuthority(authorities=Authorities.MAINTAINADJ)
	@RequestMapping(value="/modifyLoanStatus")
	public @ResponseBody Message modifyLoanStatus(String loanId,String status){
		return loanBiz.modifyLoanStatus(loanId,status);
	}
}
