package com.zhongyang.java.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.InvestBiz;
import com.zhongyang.java.biz.UserFundBiz;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.vo.FundRecordVo;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UserFundVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午4:17:46
* 类说明:用户账户信息
*/
@Controller
public class AccountController  extends BaseController{

	
	@Autowired
	private UserFundBiz userFundBiz;
	
	@Autowired
	private InvestBiz investBiz;
	/* 
	 * @param req
	 * @param res
	 * @return Obiect
	 * 通过userId获得UserFund
	 */
	@FireAuthority(authorities=Authorities.ACCLIST)
	@RequestMapping(value= "/userFund")
	public @ResponseBody UserFundVo ByUserId(HttpServletRequest req,String userId) {
		UserFundVo userFundVo = new UserFundVo();
		userFundVo.setUserid(userId);
		return userFundBiz.byUserId(userFundVo);
		
	}
	/**
	 * 用户资金账户余额
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/getUserFundAccount")
	public @ResponseBody UserFundVo getUserFundAccount(String userId){
		return userFundBiz.getUserFundAccount(userId);
	}
	
	/**
	 * 用户资金明细列表
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/getUserFundDetaileList")
	public @ResponseBody PagerVO<FundRecordVo> getUserFundDetaileList(PagerVO<FundRecord>pager){
		return userFundBiz.getFundRecordDetaileList(pager);
	}
	/**
	 * 用户投资记录(借出历史)
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/getUserInvestList")
	public @ResponseBody PagerVO<InvestVo> getgetUserInvestList(PagerVO<Invest>pager){
		return investBiz.getUserInvestList(pager);
	}
}
