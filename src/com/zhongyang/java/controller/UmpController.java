package com.zhongyang.java.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ClientFundRecordBiz;
import com.zhongyang.java.biz.UmpTransactionBiz;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.BusinessToUserTransferVo;
import com.zhongyang.java.vo.ClientFundRecordVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UserInfoVo;
import com.zhongyang.java.vo.fund.BusinessRechargeVo;
import com.zhongyang.java.vo.fund.BusinessWithdrawVo;
import com.zhongyang.java.vo.fund.SettleFundRecordVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午2:50:22
* 类说明:平台用户与联动账户相关操作类
*/
@Controller
public class UmpController extends BaseController{
	
	@Autowired
	UmpTransactionBiz  umpTransactionBiz;
	
	
	@Autowired
	ClientFundRecordBiz clientFundRecordBiz;
	
	/*
	 * 标的结算----20160317--------转账给借款人
	 * 1,查询标的账户信息,计算该标的需要扣除的管理费,给借款人实际放款金额,生成标的
	 */
	@FireAuthority(authorities=Authorities.ACCAUDITLIST)
	@RequestMapping(value="/settleLoan")
	public @ResponseBody Message settleLoanAccount(String loanId){
		return umpTransactionBiz.settleLoanAccount(loanId);
	}
	
	 /*标的结算回调
     * 
     */
	@RequestMapping(value = "/umpCallBackSettleLoan")
	public @ResponseBody String umpSavereSettle(Model model, HttpServletRequest request, SettleFundRecordVo settleFundRecordVo) {
		// 更新资金记录状态
		return umpTransactionBiz.umpCallBackSettleLoan(settleFundRecordVo);
	}
	
	/*
	 * 企业充值
	 */
	@FireAuthority(authorities=Authorities.ENTERPRECHARGE)
	@RequestMapping(value="/businessRecharge")
	public @ResponseBody Map<String, String>  businessRecharge(HttpServletRequest rep,String busrechargeAt){
		String url = clientFundRecordBiz.addClientFundRecord(rep,busrechargeAt);
		Map<String, String> prepareUmp = new HashMap<String,String>();
		prepareUmp.put("prepareUmp", url);
		return prepareUmp;
	}
	
	/*
     * 企业充值回调
     */
	@RequestMapping(value = "/businessRechargeCallBack")
	public @ResponseBody String businessRechargeCallBack(Model model, HttpServletRequest request, BusinessRechargeVo businessRechargeVo) {
		// 更新资金记录状态
		return clientFundRecordBiz.businessRechargeCallBack(businessRechargeVo);
	}
	
	/*
	 * 企业提现
	 */
	@FireAuthority(authorities=Authorities.ENTERPREWITHDRAW)
	@RequestMapping(value="/businessWithdraw")
	public @ResponseBody Message  businessWithdraw(HttpServletRequest rep,String merchantWithdraw){
		return clientFundRecordBiz.addClientFundRecordWithdraw(rep,merchantWithdraw);
	}
	
	/*
     * 企业提现回调
     */
	@RequestMapping(value = "/businessWithdrawCallBack")
	public @ResponseBody String businessWithdrawCallBack(Model model, HttpServletRequest request, BusinessWithdrawVo businessWithdrawVo) {
		// 更新资金记录状态
		return clientFundRecordBiz.businessWithdrawCallBack(businessWithdrawVo);
	}
	
	/*
     * 商户向用户转账--从第三方获取账户余额和申请记录
     */
	@FireAuthority(authorities=Authorities.ENTERPTRANSFER)
	@RequestMapping(value = "/businessToUserTransfer")
	public @ResponseBody BusinessToUserTransferVo businessToUserTransfer(Model model, HttpServletRequest request) {
		return clientFundRecordBiz.businessToUserTransfer();
	}
	
	/*
     * 商户向用户转账--用户信息查询
     */
	@RequestMapping(value = "/getUserTransfer")
	public @ResponseBody UserInfoVo getUserTransfer(Model model, HttpServletRequest request,String mobile) {
		return clientFundRecordBiz.getUserTransfer(mobile);
	}

	/*
     * 商户向用户转账--创建申请记录
     */
	@FireAuthority(authorities=Authorities.B2CTRANSAPPLY)
	@RequestMapping(value = "/createApplication")
	public @ResponseBody Message createApplication(HttpServletRequest request,String userId,String userName,String transAmount,String description) {
		return clientFundRecordBiz.createApplication(request,userId,userName,transAmount,description);
	}
	
	/*
     * 商户向用户转账--删除取消申请记录
     */
    @FireAuthority(authorities=Authorities.B2CTRANSCANCEL)
	@RequestMapping(value = "/deleteApplication")
	public @ResponseBody Message deleteApplication(HttpServletRequest request,String id) {
		return clientFundRecordBiz.deleteApplication(request,id);
	}
	
	/*
     * 商户向用户转账--同第三方转账给用户
     */
    @FireAuthority(authorities=Authorities.B2CTRANSAGGREE)
	@RequestMapping(value = "/businessToUserTransferUmp")
	public @ResponseBody Message businessToUserTransferUmp(HttpServletRequest request,String appIdli) {
		return clientFundRecordBiz.businessToUserTransferUmp(appIdli);
	}
	
	/*
     * 商户向用户转账--同第三方转账给用户
     */
	@RequestMapping(value = "/businessToUserTransferUmpCallBack")
	public @ResponseBody void businessToUserTransferUmpCallBack(HttpServletRequest request, BusinessWithdrawVo businessWithdrawVo) {
		clientFundRecordBiz.businessToUserTransferUmpCallBack(businessWithdrawVo);
	}
	
	
	/*
     * 将资金从标的账户转出至平台账户
     */
	@FireAuthority(authorities=Authorities.B2CADJ)
	@RequestMapping(value = "/loanAmountTobusinessAccount")
	public @ResponseBody Message loanAmountTobusinessAccount(HttpServletRequest request,String loanId,String amount,String description) {
		return umpTransactionBiz.loanAmountTobusinessAccount(loanId,amount,description);
	}
	
	@RequestMapping(value = "/allLoanAmountTobusinessAccount")
	public @ResponseBody Message allLoanAmountTobusinessAccount() {
		return umpTransactionBiz.allLoanAmountTobusinessAccount();
	}
	
	/*
     *  将资金从标的账户转出至平台账户回调
     */
	@RequestMapping(value = "/loanAmountTobusinessAccountCallBack")
	public @ResponseBody String  loanAmountTobusinessAccountCallBack(HttpServletRequest request,BusinessWithdrawVo businessWithdrawVo) {
		return umpTransactionBiz.loanAmountTobusinessAccountCallBack(businessWithdrawVo);
	}
	
	
	/*
     * 将资金转入标的账户
     */
	@FireAuthority(authorities=Authorities.P2BADJ)
	@RequestMapping(value = "/businessAmountToloanAccount")
	public @ResponseBody Message businessAmountToloanAccount(HttpServletRequest request,String loanId,String amount,String description) {
		return clientFundRecordBiz.businessAmountToloanAccount(loanId,amount,description);
	}
	
	/*
     *  将资金转入标的账户回调
     */
	@RequestMapping(value = "/businessAmountToloanAccountCallBack")
	public @ResponseBody String businessAmountToloanAccountCallBack(HttpServletRequest request,BusinessWithdrawVo businessWithdrawVo) {
		return clientFundRecordBiz.businessAmountToloanAccountCallBack(businessWithdrawVo);
	}
	
	
	/*
     *  平台资金记录//托管方查询
     */
	@FireAuthority(authorities=Authorities.PLATCAPITALRECORDS)
	@RequestMapping(value = "/getClientFundRecordInfo")
	public @ResponseBody ClientFundRecordVo getClientFundRecordInfo(HttpServletRequest request,ClientFundRecordVo clientFundRecordVo) {
		return clientFundRecordBiz.getClientFundRecordInfo(request,clientFundRecordVo);
	}
	/*
     *  平台资金记录
     */
	@FireAuthority(authorities=Authorities.PLATCAPITALRECORDS)
	@RequestMapping(value = "/getClientFundRecord")
	public @ResponseBody PagerVO<ClientFundRecordVo> getClientFundRecord(HttpServletRequest request,PagerVO<FundRecord>pager) {
		return clientFundRecordBiz.getClientFundRecord(request,pager);
	}
	
	//平台资金记录导出
//	@FireAuthority(authorities=Authorities.DERIVEFILE)
	@RequestMapping(value="/clientFundRecordOutExcle")
	public ResponseEntity<byte[]> clientFundRecordOutExcle(HttpServletRequest request,PagerVO<ClientFundRecordVo>pager){
		return clientFundRecordBiz.clientFundRecordOutExcle(request, pager);
	}
	
	
	//放款调账
	@FireAuthority(authorities=Authorities.MAINTAINADJ)
	@RequestMapping(value="/settleLoanUpdate")
	public @ResponseBody Message settleLoanUpdate(HttpServletRequest request){
		return umpTransactionBiz.settleLoanUpdate(request);
	}
	
}
