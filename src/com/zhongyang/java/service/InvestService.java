package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.InvestRecord;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.Result;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月17日 下午4:42:04
* 类说明 :投标信息
*/
public interface InvestService {
	/**
	 * 
	 * @param invest
	 * @return
	 * @throws Exception
	 * 根据标的ID查询投资人ID
	 */
	public List<Invest> getInvestByLoanId(Invest invest) throws Exception;
	/**
	 * 
	 * @param invest
	 * @return
	 * @throws Exception
	 * 根据标的ID查询投资人ID，同时查询投资人电话号码
	 */
	public List<InvestVo> getInvestAndMobileByLoanId(String loanId) throws Exception;
	/**
	 * 

	* @Title: queryInvest 
	* @Description:根据标的ID和状态查询投资记录 
	* @return List<Invest>    返回类型 
	* @throws
	 */
	public List<Invest> queryInvest(Invest invest) throws Exception;
	/**
	 * 
	* @Title: modifyInvest 
	* @Description:修改投资记录
	* @return void    返回类型 
	* @throws
	 */
	public void modifyInvest(Invest invest)throws Exception;
	
	public Invest queryByInvest(Invest invest)throws Exception;

	 /* @param invest
	 * @return
	 * @throws Exception
	 */
	public void updateInvestByLoanId(Invest invest) throws Exception;
	/**
	 * 
	 * @param userId 
	 * @return
	 * @throws Exception
	 */
	public int getUsetInvestNumber(String userId)throws Exception;
	
	public List<InvestVo> getUserInvestList(Page<Invest> pager) throws Exception;
	
	public int getInvestCount(Map<String, Object> params) throws Exception;
	
	public Result queryResultByMap(Map<String,Object>map)throws Exception;
	
	public List<InvestRecord> queryByParams(Page<Invest> page)throws Exception;
	
	public Invest queryById(Invest invest)throws Exception;
	/**
	 * 修改投资状态
	 * @param loanid
	 * @param beforStatus
	 * @param afterStatus
	 */
	public void updateInvestStatus(String loanid,String beforStatus, String afterStatus,String userid);
	
	public int findInvestRepayMent(String loanId)throws Exception;

}
