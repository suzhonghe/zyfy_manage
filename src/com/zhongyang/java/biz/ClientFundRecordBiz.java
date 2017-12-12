package com.zhongyang.java.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.BusinessToUserTransferVo;
import com.zhongyang.java.vo.ClientFundRecordVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UserInfoVo;
import com.zhongyang.java.vo.fund.BusinessRechargeVo;
import com.zhongyang.java.vo.fund.BusinessWithdrawVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月7日 下午7:36:37
* 类说明
*/
public interface ClientFundRecordBiz {
    /*
     * 平台资金记录:充值
     */
	public  String  addClientFundRecord(HttpServletRequest request,String busrechargeAt);
    /*
     * 企业充值回调
     */
	public  String businessRechargeCallBack(BusinessRechargeVo businessRechargeVo);
	/*
     * 平台资金记录:提现
     */
	public Message addClientFundRecordWithdraw(HttpServletRequest request,String merchantWithdraw);
	/*
	 * 平台资金记录:提现回调
	 */
	public String businessWithdrawCallBack(BusinessWithdrawVo businessWithdrawVo);
	
	/*
	 * 商户向用户转账
	 */
	public BusinessToUserTransferVo businessToUserTransfer();
	/*
	 * 商户向用户转账--用户查询
	 */
	public UserInfoVo getUserTransfer(String mobile);
	/*
	 * 商户向用户转账--创建申请记录
	 */
	public Message createApplication(HttpServletRequest request,String userId,String userName, String transAmount, String description);
	/*
	 * 商户向用户转账--调用第三方
	 */
	public Message businessToUserTransferUmp(String appIdli);
	/*
	 * 商户向用户转账--调用第三方取消
	 */
	public Message deleteApplication(HttpServletRequest request, String appIdli);
	
	/*
	 * 商户向用户转账--调用第三方回调
	 */
	public String businessToUserTransferUmpCallBack(BusinessWithdrawVo businessWithdrawVo);
	
	/*
     * 将资金转入标的账户
     */
	public Message businessAmountToloanAccount(String loanId,String amount,String description);
	/*
     *  将资金转入标的账户回调
     */
	public String businessAmountToloanAccountCallBack(BusinessWithdrawVo businessWithdrawVo);
	 /*
     * 平台资金记录:查询
     */
	public ClientFundRecordVo getClientFundRecordInfo(HttpServletRequest request, ClientFundRecordVo clientFundRecordVo);
	
	 /*
     * 平台资金记录:查询
     */
	public  PagerVO<ClientFundRecordVo> getClientFundRecord(HttpServletRequest request,PagerVO<FundRecord>pager);
	/*
	 * 平台资金记录到excle
	 */
	public ResponseEntity<byte[]> clientFundRecordOutExcle(HttpServletRequest request,PagerVO<ClientFundRecordVo> pager);

	
	
}
