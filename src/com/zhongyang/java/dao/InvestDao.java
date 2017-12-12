package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.InvestRecord;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.Result;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月17日 下午4:48:40
* 类说明
*/
public interface InvestDao {
    /**
     * 
     * @param invest
     * @return
     * 根据标的ID查询投资人ID
     */
	public List<Invest> getInvestByLoanId(Invest invest);
	/**
	 * 
	* @Title: selectInvest 
	* @Description:根据标的ID和投资状态查询投资记录
	* @return List<Invest>    返回类型 
	* @throws
	 */
	public List<Invest> selectInvest(Invest invest)throws Exception;
	/**
	 * 
	* @Title: updateInvest 
	* @Description:修改投资状态 
	* @return void    返回类型 
	* @throws
	 */
	public void updateInvest(Invest invest)throws Exception;
    /**
     * 
     * @param invest
     * 根据loanId更新投资人资金状态：处理中---成功|失败
     */
	public void updateInvestByLoanId(Invest invest);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public int getUsetInvestNumber(String userId);
	
	public List<InvestVo> getUserInvestList(Page<Invest> pager);
	
	public int getInvestCount(Map<String, Object> params);
	
	public List<InvestRecord> selectByParams(Page<Invest> page)throws Exception;
	
	public Result selectResultByMap(Map<String,Object>map)throws Exception;
	
	public List<InvestVo> getInvestAndMobileByLoanId(String loanId)throws Exception;
	
	public Invest selectByInvest(Invest invest)throws Exception;
	
	public Invest selectById(Invest invest)throws Exception;
	
	public void updateInvestStatus(@Param("loanid") String loanid,@Param("userid") String userid,@Param("beforStatus") String beforStatus,@Param("afterStatus") String afterStatus);
	
	public int findInvestRepayMent(String loanId);
	
}
