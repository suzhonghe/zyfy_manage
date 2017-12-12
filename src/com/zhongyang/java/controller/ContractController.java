package com.zhongyang.java.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ContractBiz;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;

@Controller
public class ContractController extends BaseController{

	@Autowired
	ContractBiz contractBiz;
	
	@FireAuthority(authorities=Authorities.CONTRACTGEN)
	@RequestMapping(value="/generateContracts")
	public @ResponseBody synchronized Message generateContracts(HttpServletRequest request, String loanId){
		return contractBiz.generateContracts( request,loanId);
	}
	
	@RequestMapping(value="/getuncontractLoans")
	public @ResponseBody Page<Loan> getuncontractLoans(Page<Loan> page){
		
		return contractBiz.uncontractLoans(page);
	}
}
