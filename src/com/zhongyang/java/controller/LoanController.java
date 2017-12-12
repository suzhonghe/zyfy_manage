package com.zhongyang.java.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.LoanBiz;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.LoanDetail;
import com.zhongyang.java.vo.LoanInvestVo;
import com.zhongyang.java.vo.LoanListVo;
import com.zhongyang.java.vo.LoanVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UmpBidTransferVo;
import com.zhongyang.java.vo.loan.LoanStatus;

/**
 * 
* @Title: LoanController.java 
* @Package com.zhongyang.java.controller 
* @Description:标的控制器 
* @author 苏忠贺   
* @date 2015年12月4日 上午9:58:10 
* @version V1.0
 */
@Controller
public class LoanController extends BaseController{

	@Autowired
	private LoanBiz loanBiz;
	
	/**
	 * 
	* @Title: bidApply 
	* @Description:标的申请
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDAPPLY)
	@RequestMapping(value="/bidApply", method = RequestMethod.POST)
	public @ResponseBody Message bidApply(LoanVo loanVo){
		return loanBiz.addOneLoan(loanVo);
	}
	/**
	 * 
	* @Title: queryLoanById 
	* @Description:根据标的ID查询标的详细信息
	* @return Loan    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDCAT)
	@RequestMapping(value="/queryLoanById", method = RequestMethod.POST)
	public @ResponseBody LoanDetail queryLoanById(String id){
		return loanBiz.queryLoanById(id);
	}
	/**
	 * 
	* @Title: bidPublished 
	* @Description: 标的发布 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDRELEASE)
	@RequestMapping(value="/bidPublished", method = RequestMethod.POST)
	public @ResponseBody Message bidPublished(LoanVo loanVo){
		return loanBiz.loanPublished(loanVo);
	}
	/**
	 * 
	* @Title: cancleLoan 
	* @Description:取消标的 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDCANCEL)
	@RequestMapping(value="/cancleLoan", method = RequestMethod.POST)
	public @ResponseBody Message cancleLoan(Loan loan,String reason){
		return loanBiz.cancleLoan(loan,reason);
	}
	
	/**
	 * 
	* @Title: failedBid 
	* @Description:流标处理
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.INBCANCEL)
	@RequestMapping(value="/failedBid", method = RequestMethod.POST)
	public @ResponseBody Message failedBid(Loan loan,String reason){
		return loanBiz.failedLoan(loan,reason);
	}
	/**
	 * 
	* @Title: failedCallBack   
	* @Description:流标回调
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value="/failedCallBack")
	public @ResponseBody void failedCallBack(UmpBidTransferVo umpBidTransferVo){
		loanBiz.failedCallBack(umpBidTransferVo);
	}
	
	/**
	 * 
	* @Title: finishedLoan 
	* @Description: 满标列表
	* @return List<Loan>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.ACCAUDITLIST)
	@RequestMapping(value="/finishedLoans", method = RequestMethod.POST)
	public @ResponseBody PagerVO<LoanListVo> finishedLoan(PagerVO<LoanListVo>pager){
		return loanBiz.queryLoanList(pager);
	}
	/**
	 * 
	* @Title: openedLoan 
	* @Description:开标列表 
	* @return List<Loan>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.INBLIST)
	@RequestMapping(value="/opendLoans", method = RequestMethod.POST)
	public @ResponseBody PagerVO<LoanListVo> openedLoan(PagerVO<LoanListVo>pager){
		return loanBiz.queryLoanList(pager);
	}
	/**
	 * 
	* @Title: inititedLoan 
	* @Description:申请发标列表 
	* @return List<Loan>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDAPPLY)
	@RequestMapping(value="/inititedLoans", method = RequestMethod.POST)
	public @ResponseBody PagerVO<LoanListVo> inititedLoan(PagerVO<LoanListVo>pager){
		return loanBiz.queryLoanList(pager);
	}
	/**
	 * 
	* @Title: unplaned 
	* @Description:需调度发标列表
	* @return List<Loan>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDUNARBLIST)
	@RequestMapping(value="/unplanedLoans", method = RequestMethod.POST)
	public @ResponseBody PagerVO<LoanListVo> unplanedLoans(PagerVO<LoanListVo>pager){
		return loanBiz.queryLoanList(pager);
	}
	/**
	 * 
	* @Title: publishedLoans 
	* @Description:查询已发标列表
	* @return List<LoanListVo>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDLIST)
	@RequestMapping(value="/publishedLoans", method = RequestMethod.POST)
	public @ResponseBody PagerVO<LoanListVo> publishedLoans(PagerVO<LoanListVo>pager){
		return loanBiz.queryPublishedLoans(pager);
	}
	/**
	 * 
	* @Title: scheduledLoan 
	* @Description:已调度发标列表
	* @return List<Loan>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.ARBLIST)
	@RequestMapping(value="/scheduledLoans", method = RequestMethod.POST)
	public @ResponseBody PagerVO<LoanListVo> scheduledLoan(PagerVO<LoanListVo>pager){
		return loanBiz.queryLoanList(pager);
	}
	/**
	 * 
	* @Title: failedLoan 
	* @Description:流标列表
	* @return List<Loan>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDFAILEDLIST)
	@RequestMapping(value="/failedLoans", method = RequestMethod.POST)
	public @ResponseBody PagerVO<LoanListVo> failedLoan(PagerVO<LoanListVo>pager){
		return loanBiz.queryLoanList(pager);
	}
	/**
	 * 
	* @Title: cancledLoan 
	* @Description:取消标的列表 
	* @return List<Loan>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDCANCELLIST)
	@RequestMapping(value="/cancledLoans", method = RequestMethod.POST)
	public @ResponseBody PagerVO<LoanListVo> cancledLoan(PagerVO<LoanListVo>pager){
		return loanBiz.queryLoanList(pager);
	}
	/**
	 * 
	* @Title: modifyLoan 
	* @Description:修改标的
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.BIDUPD)
	@RequestMapping(value="/modifyUnpublishedLoan", method = RequestMethod.POST)
	public @ResponseBody Message modifyLoan(Loan loan){
		return loanBiz.modifyUnpublishedLoan(loan);
	}
	/**
	 * 
	* @Title: modifyLoanStatus 
	* @Description:修改标的状态为需调度
	* @return Message    返回类型 
	* @throws
	 */
	@RequestMapping(value="/modifyLoanUnplaned", method = RequestMethod.POST)
	public @ResponseBody Message modifyLoanUnplaned(Loan loan){
		loan.setStatus(LoanStatus.UNPLANED);
		return loanBiz.modifyUnpublishedLoan(loan);
	}
	
	/*
	 * 查询已满标(状态：FINISHED)的标的
	 */
	@RequestMapping(value="/finishedLoan")
	public @ResponseBody List<LoanInvestVo> getFinishedLoan(HttpServletRequest request){
		return loanBiz.getFinishedLoan();
	}
	
	/*
	 * 查询已满标(状态：FINISHED)的标的
	 */
	@RequestMapping(value="/finishedLoanInvestUser")
	public @ResponseBody List<LoanInvestVo> getFinishedLoanInvesrUser(HttpServletRequest request,String longId){
		return loanBiz.getFinishedLoanInvesrUser(longId);
	}
	
	/*
	 * 查询已满标(状态：FINISHED)的标的
	 */
	@RequestMapping(value="/getUserLoanList")
	public @ResponseBody PagerVO<LoanVo> getUserLoanList(PagerVO<Loan>pager){
		return loanBiz.getUserLoanList(pager);
	}
}
