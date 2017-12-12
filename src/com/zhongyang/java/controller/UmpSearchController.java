package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.UmpSearchBiz;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.BindcardSearch;
import com.zhongyang.java.vo.BindcardSearchCondition;
import com.zhongyang.java.vo.PtpMerQuery;
import com.zhongyang.java.vo.PtpMerQueryConditon;
import com.zhongyang.java.vo.TranseqSearch;
import com.zhongyang.java.vo.TranseqSearchCondition;
import com.zhongyang.java.vo.TransferSearchConditon;
import com.zhongyang.java.vo.UmpLoanInfo;
import com.zhongyang.java.vo.UmpTransferSearch;
import com.zhongyang.java.vo.UmpUserFund;
import com.zhongyang.java.vo.UmpUserInfo;

@Controller
public class UmpSearchController extends BaseController{
	
	@Autowired
	private UmpSearchBiz umpSearch;
	
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/findUmpUserInfo")
	public @ResponseBody UmpUserInfo findUmpUserInfo(String userId){
		return umpSearch.umpUserSearch(userId);
	}
	
	/**
	 * 
	* @Title: searchLoan 
	* @Description:标的查询
	* @return UmpLoanInfo    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/searchUmpLoan")
	public @ResponseBody UmpLoanInfo searchLoan(String loanId){
		return umpSearch.searchLoan(loanId);
	}
	/**
	 * 
	* @Title: transferSearch 
	* @Description:交易查询
	* @return UmpTransferSearch    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/transferSearch")
	public @ResponseBody UmpTransferSearch transferSearch(TransferSearchConditon transferSearchCondition){
		return umpSearch.transferSearch(transferSearchCondition);
	}
	/**
	 * 
	* @Title: queryPtpMer 
	* @Description:商户账户查询 
	* @return PtpMerQuery    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/queryPtpMer")
	public @ResponseBody PtpMerQuery queryPtpMer(PtpMerQueryConditon ptpMerQueryCondition){
		return umpSearch.queryPtpMer(ptpMerQueryCondition);
	}
	/**
	 * 
	* @Title: queryBindcard 
	* @Description:绑卡换卡订单查询(商户à平台)
	* @return BindcardSearch    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/queryBindcard")
	public @ResponseBody BindcardSearch queryBindcard(BindcardSearchCondition bindcardSearchCondition){
		return umpSearch.queryBindcard(bindcardSearchCondition);
	}
	
	@FireAuthority(authorities=Authorities.MAINTAINQUERY)
	@RequestMapping(value="/transeq_search")
	public @ResponseBody TranseqSearch transeq_search(TranseqSearchCondition transeqSearchCondition){
		return umpSearch.transeq_search(transeqSearchCondition);
	}
	
	@RequestMapping(value="/queryUserFund")
	public @ResponseBody Page<UmpUserFund> queryUserFund(Page<UmpUserFund>page){
		return umpSearch.queryUserFund(page);
	}
}
