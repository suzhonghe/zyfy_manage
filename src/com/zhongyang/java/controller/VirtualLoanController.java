package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.VirtualLoanBiz;
import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;

@Controller
public class VirtualLoanController extends BaseController {

	@Autowired
	private VirtualLoanBiz virtualLoanBiz;
	
	//创建体验标
	@FireAuthority(authorities=Authorities.VIRTUALADD)
	@RequestMapping("/saveVirtualLoan")
	public @ResponseBody Message saveVirtualLoan(VirtualLoan vl){
		return virtualLoanBiz.saveVirtualLoan(vl);
	}
	
	//更新体验标状态（结束、发布）
	@FireAuthority(authorities=Authorities.VIRTUALUPD)
	@RequestMapping("/updateVirtualLoan")
	public @ResponseBody Message updateVirtualLoan(VirtualLoan vl){
		return virtualLoanBiz.updateVirtualLoan(vl);
	}
	
	//查询所有体验标列表信息
	@FireAuthority(authorities=Authorities.EXPBIDLIST)
	@RequestMapping("/getAllVirtualLoan")
	public @ResponseBody Page<VirtualLoan> getAllVirtualLoan(Page<VirtualLoan> page){
		return virtualLoanBiz.getAllVirtualLoan(page);
	}
	
}
