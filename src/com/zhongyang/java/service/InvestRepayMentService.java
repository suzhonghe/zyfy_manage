package com.zhongyang.java.service;

import java.math.BigDecimal;


import com.zhongyang.java.pojo.InvestRepayment;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月28日 下午1:56:16
* 类说明
*/
public interface InvestRepayMentService {
	/*
	 * 创建投资汇款计划
	 */
    public int createInvestRepayMent(InvestRepayment investRepayment);


    public int updateStatus(InvestRepayment investRepayment);
    
    public void updateInvestAmount(int currentPriod,String loanid,BigDecimal pre);
    
    public InvestRepayment getInvestAllRate(String investId) throws Exception;
}
