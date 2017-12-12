package com.zhongyang.java.service;

import com.zhongyang.java.pojo.LoanRepayment;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月28日 下午1:56:16
* 类说明
*/
public interface LoanRepayMentService {
	/*
	 * 创建还款计划
	 */

   public int createLoanRepayMent(LoanRepayment loanRepayment) throws Exception;
   
   public LoanRepayment userFundRateSum(LoanRepayment loanId) throws Exception;



    public int updateStatus(LoanRepayment loanRepayment);
    
	/**
	 * 定时查询前一天数据修改状态
	 */
	public void updateStatusByTime(String status);

	public int findByLoanId(LoanRepayment loanRepayMent);

}
