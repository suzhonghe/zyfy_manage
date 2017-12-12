package com.zhongyang.java.biz;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.LoanDetail;
import com.zhongyang.java.vo.LoanInvestVo;
import com.zhongyang.java.vo.LoanListVo;
import com.zhongyang.java.vo.LoanVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UmpBidTransferVo;
import com.zhongyang.java.vo.UmpLoanInfo;

/**
 * 
* @Title: LoanBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:标的业务处理接口 
* @author 苏忠贺   
* @date 2015年12月4日 上午9:08:03 
* @version V1.0
 */
public interface LoanBiz {
	
	/**
	 * 
	* @Title: addOneLoan 
	* @Description:添加一条loan记录
	* @return Message    返回类型 
	* @throws
	 */
	public Message addOneLoan(LoanVo loanVo);
	
	/**
	 * 
	* @Title: queryLoanById 
	* @Description:根据Id查询标的信息
	* @return Loan    返回类型 
	* @throws
	 */
	public LoanDetail queryLoanById(String id);
	
	/**
	 * 
	* @Title: queryLoanByProjectId 
	* @Description: 根据项目Id查询标的信息 
	* @return List<Loan>    返回类型 
	* @throws
	 */
	public List<Loan> queryLoanByProjectId(String projectId);
	
	/**
	 * 
	* @Title: loanPublished 
	* @Description:发标 
	* @return Message    返回类型 
	* @throws
	 */
	public Message loanPublished(LoanVo loanVo);	
	/**
	 * 	
	* @Title: queryLoanList 
	* @Description: 根据标的状态查询标的列表
	* @return List<Loan>    返回类型 
	* @throws
	 */
	public PagerVO<LoanListVo> queryLoanList(PagerVO<LoanListVo>pager);
    /**
     * 
     * @param request
     * @return:Loan
     * 查询已满标(状态：FINISHED)的标的
     */
	public List<LoanInvestVo> getFinishedLoan();
	/**
	 * 
	* @Title: modifyLoan 
	* @Description:修改标的
	* @return Message    返回类型 
	* @throws
	 */
	public Message modifyUnpublishedLoan(Loan loan);
	/**
	 * 
	* @Title: cancleLoan 
	* @Description:取消标的 
	* @return Message    返回类型 
	* @throws
	 */
	public Message cancleLoan(Loan loan,String reason);
	/**
	 * 
	* @Title: failedLoan 
	* @Description: 流标业务 
	* @return Message    返回类型 
	* @throws
	 */
	public Message failedLoan(Loan loan,String reason);
	/**
	 * 
	* @Title: failedCallBack 
	* @Description:流标回调 
	* @return Message    返回类型 
	* @throws
	 */
	public void failedCallBack(UmpBidTransferVo umpBidTransferVo);
    /**
     * FinishedLoan投标用用户
     * @param longId
     * @return
     */
	public List<LoanInvestVo> getFinishedLoanInvesrUser(String longId);
	/**
	 * 
	* @Title: queryPublishedLoans 
	* @Description: 查询所有已发标列表
	* @return List<LoanListVo>    返回类型 
	* @throws
	 */
	public PagerVO<LoanListVo> queryPublishedLoans(PagerVO<LoanListVo>pager);
    
	public PagerVO<LoanVo> getUserLoanList(PagerVO<Loan> pager);
	
	public Message modifyLoanStatus(String loanId,String status);
}
