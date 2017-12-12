package com.zhongyang.java.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.fund.BusinessWithdrawVo;
import com.zhongyang.java.vo.fund.SettleFundRecordVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午3:58:35
* 类说明:标的交易
*/
public interface UmpTransactionBiz {
     
	 /*
     * 标的结算----20160317--------转账给借款人
     */
	public Message settleLoanAccount(String loanId);
	 /*
     * 标的结算--打款
     */
	public String umpCallBackSettleLoan(SettleFundRecordVo settleFundRecordVo);
	/*
     * 将资金从标的账户转出至平台账户
     */
	public Message loanAmountTobusinessAccount(String loanId,String amount,String description);
	
	/*
     * 将资金从标的账户转出至平台账户--回调
     */
	public String loanAmountTobusinessAccountCallBack(BusinessWithdrawVo businessWithdrawVo);
	
	public Message allLoanAmountTobusinessAccount();
	/*
     * 查询今天结算的标的
     */
	public void findSettledloan(String type, String startTime, String endTime);
	
	/*
	 * 放款失败调账
	 */
	public Message settleLoanUpdate(HttpServletRequest request);
	
}
