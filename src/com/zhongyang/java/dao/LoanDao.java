package com.zhongyang.java.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ManagerData;
import com.zhongyang.java.vo.loan.SelectLoanResultVo;

/**
 * 
* @Title: LoanDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 标的DAO 
* @author 苏忠贺   
* @date 2015年12月3日 下午3:46:27 
* @version V1.0
 */
public interface LoanDao {
	
	/**
	 * 
	* @Title: insertOneLoan 
	* @Description:添加一条loan记录 
	* @return void    返回类型 
	* @throws
	 */
	public void insertOneLoan(Loan loan)throws Exception;
	
	/**
	 * 
	* @Title: selectLoanById 
	* @Description: 根据Id查询标的信息 
	* @return Loan    返回类型 
	* @throws
	 */
	public Loan selectLoanById(String id)throws Exception;
	
	/**
	 * 
	* @Title: selectLoanByProjectId 
	* @Description:根据项目Id查询标的信息
	* @return List<Loan>    返回类型 
	* @throws
	 */
	public List<Loan> selectLoanByProjectId(Loan loan)throws Exception;
	
	/**
	 * 
	* @Title: updateLoan 
	* @Description: 修改标的
	* @return void    返回类型 
	* @throws
	 */
	public void updateLoan(Loan loan)throws Exception;

	/**
	 * 
	* @Title: selectFinishedLoan 
	* @Description:根据标的状态查询标的列表 
	* @return List<Loan>    返回类型 
	* @throws
	 */
	public List<Loan> selectLoanByStatus(Page<Loan>page)throws Exception;
	/**
	 * 
	* @Title: selectCount 
	* @Description:根据条件查询结果数量
	* @return int    返回类型 
	* @throws
	 */
	public SelectLoanResultVo selectResult(Map<String,Object>params)throws Exception;
	/**
	 * 
	* @Title: selectPublishedLoans 
	* @Description:查询所有已发标列表
	* @return List<Loan>    返回类型 
	* @throws
	 */
	public List<Loan> selectPublishedLoans(Page<Loan>page)throws Exception;
    /**
     * 
     * @param loan
     * @return
     * @throws Exception
     * 查询已满标(状态：FINISHED)的标的
     */
	public List<Loan> getFinishedLoan(Loan loan) throws Exception;

	public int getUsetLoanNumber(String userId) throws Exception;

	public int getUserLoanCount(Map<String, Object> params) throws Exception;

	public List<Loan> getUserLoanList(Page<FundRecord> page) throws Exception;
	/**
	 * 修改标的状态
	 */
	public void updateLoanStatus(@Param("loanid") String loanid,@Param("beforStatus") String beforStatus,@Param("afterStatus") String afterStatus);

	public ManagerData selectLoanAmount(Date date);
	
	public int selectLoanPerson(Date date);
	
	public int selectInPerson(Date date);
	
	public int selectLimitDays(Date date);
	
	public double selectRate(Date date);
}
