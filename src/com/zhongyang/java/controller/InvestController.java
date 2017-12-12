package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.InvestBiz;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.vo.InvestDetail;

/**
 * 
* @Title: InvestController.java 
* @Package com.zhongyang.java.controller 
* @Description:投资控制器 
* @author 苏忠贺   
* @date 2015年12月10日 下午6:43:04 
* @version V1.0
 */
@Controller()
public class InvestController extends BaseController{
	
	@Autowired
	private InvestBiz investBiz;
		
	@RequestMapping(value="/queryInvestRecord")
	public @ResponseBody List<InvestDetail> queryInvestRecord(Invest invest){
		return investBiz.queryInvestByLoanId(invest);
	}
}
