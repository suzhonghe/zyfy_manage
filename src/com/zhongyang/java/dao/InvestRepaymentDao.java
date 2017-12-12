package com.zhongyang.java.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.zhongyang.java.pojo.InvestRepayment;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月17日 下午4:48:40
* 类说明
*/
public interface InvestRepaymentDao {
    /*
     * 
     */
	public int createInvestRepayMent(InvestRepayment investRepayment);


    public int updateStatus(InvestRepayment investRepayment);


	public InvestRepayment getUserRepayMentRate(String userid) throws Exception;
	
	/**
	 * 提前还款修改利息
	 * @param currentPriod
	 * @param loanid
	 * @param pre
	 */
	public void updateInvestAmount(@Param("currentPriod")int currentPriod,@Param("loanid")String loanid, @Param("pre")BigDecimal pre);


	public InvestRepayment getInvestAllRate(String investId)throws Exception;

}
