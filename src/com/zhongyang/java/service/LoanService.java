package com.zhongyang.java.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ManagerData;
import com.zhongyang.java.vo.loan.SelectLoanResultVo;

/**
 * 
* @Title: Loan.java 
* @Package com.zhongyang.java.service 
* @Description:标的业务接口 
* @author 苏忠贺   
* @date 2015年12月3日 下午5:22:23 
* @version V1.0
 */
public interface LoanService {
	/**
	 * 
	* @Title: addOneLoan 
	* @Description:添加一条loan记录 
	* @return void    返回类型 
	* @throws
	 */
	public void addOneLoan(Loan loan)throws Exception;
	
	/**
	 * 
	* @Title: queryLoanById 
	* @Description: 根据Id查询标的信息 
	* @return Loan    返回类型 
	* @throws
	 */
	public Loan queryLoanById(String id)throws Exception;
	
	/**
	 * 
	* @Title: queryLoanByProjectId 
	* @Description: 根据项目Id查询标的 
	* @return List<Loan>    返回类型 
	* @throws
	 */
	public List<Loan> queryLoanByProjectId(Loan loan)throws Exception;
	
	/**
	 * 
	* @Title: modifyLoan 
	* @Description: 修改标的状态 
	* @return void    返回类型 
	* @throws
	 */
	public void modifyLoan(Loan loan)throws Exception;
	/**
	 * 
	* @Title: queryFinishedLoan 
	* @Description: 查询满标列表
	* @return List<Loan>    返回类型 
	* @throws
	 */
	public List<Loan> queryLoanByStatus(Page<Loan>page)throws Exception;
    /**
     * 
     * @param loan
     * @return
     * 查询已满标(状态：FINISHED)的标的
     * @throws Exception 
     */
	public List<Loan> getFinishedLoan(Loan loan) throws Exception;
	/**
	 * 
	* @Title: queryPublishedLoans 
	* @Description:查询已发标列表
	* @return List<Loan>    返回类型 
	* @throws
	 */
	public List<Loan> queryPublishedLoans(Page<Loan>page)throws Exception;
	/**
	 * 
	* @Title: queryCount 
	* @Description:根据条件查询结果数量
	* @return int    返回类型 
	* @throws
	 */
	public SelectLoanResultVo queryResult(Map<String,Object>params)throws Exception;
    
	public int getUsetLoanNumber(String userId)throws Exception;

	public int getUserLoanCount(Map<String, Object> params) throws Exception;

	public List<Loan> getUserLoanList(Page<FundRecord> page) throws Exception;
	/**
	 * 修改标的状态
	 * @param loanid
	 * @param beforStatus
	 * @param afterStatus
	 */
	public void updateLoanStatus(String loanid,String beforStatus,String afterStatus);
	
	public ManagerData queryLoanAmount(Date date);
	
	public int queryLoanPerson(Date date);
	
	public int queryInPerson(Date date);
	
	public int queryLimitDays(Date date);
	
	public double queryRate(Date date);
}
