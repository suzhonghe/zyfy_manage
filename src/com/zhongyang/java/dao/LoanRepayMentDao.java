package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.LoanRepayment;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月28日 下午1:56:28
* 类说明
*/
public interface LoanRepayMentDao {
    
	/*
	 * 创建还款计划
	 */
	public int createLoanRepayMent(LoanRepayment loanRepayment);


    public int updateStatus(LoanRepayment loanRepayment);



	public LoanRepayment userFundRateSum(LoanRepayment loanId);
	
	/**
	 * 定时查询前一天数据修改状态
	 */
	public void updateStatusByTime(String status);


	public int findByLoanId(LoanRepayment loanRepayMent);
	

}
