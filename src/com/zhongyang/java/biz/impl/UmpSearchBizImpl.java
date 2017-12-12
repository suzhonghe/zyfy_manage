package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.UmpSearchBiz;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpTenderService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.BindcardSearch;
import com.zhongyang.java.vo.BindcardSearchCondition;
import com.zhongyang.java.vo.PtpMerQuery;
import com.zhongyang.java.vo.PtpMerQueryConditon;
import com.zhongyang.java.vo.TransDetail;
import com.zhongyang.java.vo.TranseqSearch;
import com.zhongyang.java.vo.TranseqSearchCondition;
import com.zhongyang.java.vo.TransferSearchConditon;
import com.zhongyang.java.vo.UmpLoanInfo;
import com.zhongyang.java.vo.UmpTransferSearch;
import com.zhongyang.java.vo.UmpUserFund;
import com.zhongyang.java.vo.UmpUserInfo;
@Service
public class UmpSearchBizImpl implements UmpSearchBiz {
	
	private static Logger logger = Logger.getLogger(UmpSearchBizImpl.class);
	
	@Autowired
	private UmpAccountService umpAccountService;
	
	@Autowired 
	private UmpTenderService umpTenderService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public UmpUserInfo umpUserSearch(String userId) {
		Map<String, String> withdrawResultss = null;
		UmpUserInfo umpUserInfo = new UmpUserInfo();
		try {
			UmpAccount umpAccount=new UmpAccount();
			Pattern pattern = Pattern.compile("1[0-9]*"); 
		    if(userId.length()==11&&pattern.matcher(userId).matches()){
		    	DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		    	String mobile = cipher.encrypt(userId);
		    	User user = userService.getUserByMobile(mobile);
		    	userId=user.getId();
		    }
		    umpAccount.setUserId(userId);
			UmpAccount account = umpAccountService.getUmpAccountByUserId(umpAccount);
			Map<String,String> map = new HashMap<String, String>();
			if(account!=null){
				map.put("user_id", account.getAccountName());
			}else{
				map.put("mer_cust_id", userId.replace("-", ""));
			}
			map.put("is_find_account", "01");
			map.put("is_select_agreement", "1");
			UmpaySignature umpaySignature = new UmpaySignature("user_search", map);
			withdrawResultss = umpaySignature.getSignature();
			umpUserInfo.setRetCode(withdrawResultss.get("ret_code"));
			umpUserInfo.setRetMsg(withdrawResultss.get("ret_msg"));
			System.out.println(withdrawResultss);
			if (withdrawResultss.get("ret_code").endsWith("0000")) {
				umpUserInfo.setAccountId(withdrawResultss.get("plat_user_id"));// ump账户Id
				umpUserInfo.setCardId(withdrawResultss.get("card_id"));// 提现银行卡
				String balance = withdrawResultss.get("balance");
				BigDecimal bdc = new BigDecimal(100);
				if ("0.00" != balance) {
					BigDecimal b = new BigDecimal(balance);
					umpUserInfo.setBalance(b.divide(bdc));// 账户余额
				} else {
					umpUserInfo.setBalance(new BigDecimal(withdrawResultss.get("balance")));
				}
				umpUserInfo.setAccountId(withdrawResultss.get("account_id"));
				String state = withdrawResultss.get("account_state");
				switch (state) {
				/*账户状态1-正常 2-挂失 3-冻结 4-锁定 9-销户*/
					case "1":umpUserInfo.setAccountState("正常");break;
					case "2":umpUserInfo.setAccountState("挂失");break;
					case "3":umpUserInfo.setAccountState("冻结");break;
					case "4":umpUserInfo.setAccountState("锁定");break;
					case "9":umpUserInfo.setAccountState("销户");break;
				}
				/*ZCZP0800	无密快捷协议
				ZTBB0G00	无密投资协议
				ZHKB0H01	无密还款协议
				ZKJP0700	借记卡快捷协议*/
				String str="";
				if(withdrawResultss.containsKey("user_bind_agreement_list")){
					String agreement = withdrawResultss.get("user_bind_agreement_list");
					if(agreement.contains("ZCZP0800"))str= str+"|无密快捷协议";
					if(agreement.contains("ZTBB0G00"))str= str+"|无密投资协议";
					if(agreement.contains("ZHKB0H01"))str=str+"|无密还款协议";
					if(agreement.contains("ZKJP0700"))str= str+"|借记卡快捷协议";
					str = str.substring(1);
					
				}
				umpUserInfo.setUserAgreementList(str);// 用户签约约的协议列表信息
				umpUserInfo.setPlatUserId(withdrawResultss.get("plat_user_id"));
				umpUserInfo.setContactMobile(withdrawResultss.get("contact_mobile"));// 手机
				umpUserInfo.setCustName(withdrawResultss.get("cust_name"));// 姓名
				umpUserInfo.setIdentityCode(withdrawResultss.get("identity_code"));// 证件号
			}
		} catch (Exception e) {
			logger.info("用户第三方账户查询失败：userId："+userId);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return umpUserInfo;
	}

	@Override
	public TranseqSearch transeq_search(TranseqSearchCondition transeqSearchCondition) {
		if(transeqSearchCondition.getAccount_type()==null||transeqSearchCondition.getStart_date()==null||
				transeqSearchCondition.getEnd_date()==null||transeqSearchCondition.getPage_num()==null){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"参数输入错误");
		}
		Map<String, String> map = new HashMap<String, String>();
		if(transeqSearchCondition.getAccount_id()!=null&&transeqSearchCondition.getAccount_id()!=""){
			map.put("account_id", transeqSearchCondition.getAccount_id());
		}
		if(transeqSearchCondition.getPartic_user_id()!=null&&transeqSearchCondition.getPartic_user_id()!=""){
			map.put("partic_user_id", transeqSearchCondition.getPartic_user_id());
		}
		
		map.put("account_type", transeqSearchCondition.getAccount_type());
		if(!"00".equals(transeqSearchCondition.getAcc_category())){
			map.put("acc_category", transeqSearchCondition.getAcc_category());
		}
		map.put("start_date", transeqSearchCondition.getStart_date());
		map.put("end_date", transeqSearchCondition.getEnd_date());
		map.put("page_num", transeqSearchCondition.getPage_num());
		UmpaySignature umpaySignature = new UmpaySignature("transeq_search", map);
		Map<String, String> value = null;
		TranseqSearch ts=new TranseqSearch();
		List<TransDetail>details=new ArrayList<TransDetail>();
		try {
			value=umpaySignature.getSignature();
			System.out.println(value);
			ts.setRet_code(value.get("ret_code"));
			ts.setRet_msg(value.get("ret_msg"));
			if(!"0000".equals(value.get("ret_code"))) return ts;
			if(value.containsKey("total_num")){
				ts.setTotal_num(value.get("total_num"));
			}
			SimpleDateFormat checkDate=new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat transferDate=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat checkTime=new SimpleDateFormat("HHmmss");
			SimpleDateFormat transferTime=new SimpleDateFormat("HH:mm:ss");
			if(value.containsKey("trans_detail")){
				String detail = value.get("trans_detail");
				System.out.println(detail);
				String[] transDetails = detail.split("\\|");
				for (String td : transDetails) {
					TransDetail trd=new TransDetail();
					String[] fileds = td.split(",");
					for (String filed : fileds) {
						String result= filed.substring(filed.lastIndexOf("=")+1);
						if(filed.contains("acc_check_date")){
							trd.setAcc_check_date(transferDate.format(checkDate.parse(result)));
							continue;
						}
						if(filed.contains("amount")){
							BigDecimal bd=new BigDecimal(result);
							trd.setAmount(BigDecimalAlgorithm.div(bd,new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toString());
							continue;
						}
						if(filed.contains("amt_type")){
							/*01 订单交易金额
							02 交易手续费
							99其他*/
							switch (result) {
								case "01":trd.setAmt_type("订单交易金额");break;
								case "02":trd.setAmt_type("交易手续费");break;
								case "99":trd.setAmt_type("其他");break;
							}

						}
						if(filed.contains("balance")){
							BigDecimal bd=new BigDecimal(result);
							trd.setBalance(BigDecimalAlgorithm.div(bd,new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toString());
							continue;
						}
						if(filed.contains("dc_mark")){
							/*01入账（以账户类型为基准，进入账户的资金）
							02出账（以账户类型为基准，从账户划出的资金）
							99其他*/
							switch (result) {
								case "01":trd.setDc_mark("入账");break;
								case "02":trd.setDc_mark("出账");break;
								case "99":trd.setDc_mark("其他");break;
							}

						}
						if(filed.contains("order_date")){
							trd.setOrder_date(transferDate.format(checkDate.parse(result)));
							continue;
						}
						if(filed.contains("order_id")){
							trd.setOrder_id(result);
							continue;
						}
						if(filed.contains("trans_date")){
							trd.setTrans_date(transferDate.format(checkDate.parse(result)));
							continue;
						}
						if(filed.contains("trans_state")){
							/*01成功
							02冲正
							99其他*/
							switch (result) {
								case "01":trd.setTrans_state("成功");break;
								case "02":trd.setTrans_state("冲正");break;
								case "99":trd.setTrans_state("其他");break;
							}
						}
						if(filed.contains("trans_time")){
							trd.setTrans_time(transferTime.format(checkTime.parse(result)));
							continue;
						}
						if(filed.contains("trans_type")){
							/*01充值
							02提现
							03标的转账
							04转账
							05提现失败退回
							99 退费等其他交易*/

							switch (result) {
								case "01":trd.setTrans_type("充值");break;
								case "02":trd.setTrans_type("提现");break;
								case "03":trd.setTrans_type("标的转账");break;
								case "04":trd.setTrans_type("转账");break;
								case "05":trd.setTrans_type("提现失败退回");break;
								case "99":trd.setTrans_type("退费等其他交易");break;
							}
						}
						
					}
					details.add(trd);
				}
			}
			ts.setTransDetails(details);
		} catch (ReqDataException|RetDataException|ParseException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		} 
		return ts;
	}

	@Override
	public UmpLoanInfo searchLoan(String loanId) {
		if(loanId==null||"".equals(loanId)){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"参数输入错误");
		}
		UmpLoanInfo info=new UmpLoanInfo();
		Map<String, String> value = null;
		try {
			UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(loanId);
			Map<String, String> map = new HashMap<String, String>();
			if(umpTender!=null&&umpTender.getUmptenderid()!=null&&!"".equals(umpTender.getUmptenderid())){
				map.put("project_id", umpTender.getUmptenderid());//标的号
			}
			else{
				String project_id=loanId.replace("-","");
				map.put("project_id", project_id);
			}
			
			UmpaySignature umpaySignature = new UmpaySignature("project_account_search", map);
			value = umpaySignature.getSignature();
			info.setRet_code(value.get("ret_code"));
			info.setRet_msg(value.get("ret_msg"));
			if(!"0000".equals(value.get("ret_code"))) return info;
			info.setProject_id(loanId);
			info.setBalance(BigDecimalAlgorithm.div(new BigDecimal(value.get("balance")), new BigDecimal(100)));
			info.setProject_account_id(value.get("project_account_id"));
			String project_state=value.get("project_state");
			switch (project_state) {
				case "92":info.setProject_state("建标成功");break;
				case "93":info.setProject_state("建标失败");break;
				case "0":info.setProject_state("开标");break;
				case "1":info.setProject_state("投资中");break;
				case "2":info.setProject_state("还款中");break;
				case "3":info.setProject_state("已还款");break;
				case "4":info.setProject_state("结束");break;
				case "5":info.setProject_state("满标");break;
				case "6":info.setProject_state("流标");break;
				
			}
			switch (value.get("project_account_state")) {
				case "01":info.setProject_account_state("正常");break;
				case "02":info.setProject_account_state("冻结");break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,value.get("ret_msg"));
		} 
		return info;
	}

	@Override
	public UmpTransferSearch transferSearch(TransferSearchConditon transferSearchCondition) {
		if(transferSearchCondition.getOrder_id()==null||"".equals(transferSearchCondition.getOrder_id())){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"订单号不能为空");
		}
		if(transferSearchCondition.getMer_date()==null||"".equals(transferSearchCondition.getOrder_id())){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"查询日期不能为空");
		}
		if(transferSearchCondition.getBusi_type()==null||"".equals(transferSearchCondition.getBusi_type())){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"业务类型不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", transferSearchCondition.getOrder_id());
		map.put("mer_date", transferSearchCondition.getMer_date());
		map.put("busi_type", transferSearchCondition.getBusi_type());
		UmpaySignature umpaySignature = new UmpaySignature("transfer_search", map);
		UmpTransferSearch uts=new UmpTransferSearch();
		Map<String, String> value = null;
		try {
			value=umpaySignature.getSignature();
			uts.setRet_code(value.get("ret_code"));
			uts.setRet_msg(value.get("ret_msg"));
			if(!"0000".equals(value.get("ret_code"))) return uts;
			uts.setOrder_id(value.get("order_id"));
			if(value.containsKey("mer_check_date")){
				String date = value.get("mer_check_date");
				StringBuffer strb=new StringBuffer(date);
				strb.insert(4, "-");
				strb.insert(7, "-");
				uts.setMer_check_date(strb.toString());
			}
			String mer_date = value.get("mer_date");
			if(mer_date!=null){
				StringBuffer sbu=new StringBuffer(mer_date);
				sbu.insert(4, "-");
				sbu.insert(7, "-");
				uts.setMer_date(sbu.toString());
			}
			
			uts.setTrade_no(value.get("trade_no"));
			String type = value.get("busi_type");
			String status=value.get("tran_state");
			switch (status) {
				case "0":uts.setTran_state("初始");break;
				case "2":uts.setTran_state("成功");break;
				case "3":uts.setTran_state("失败");break;
				case "4":uts.setTran_state("不明");break;
				case "5":uts.setTran_state("交易关闭");break;
				case "6":uts.setTran_state("其他");break;
			}
			if("01".equals(type)){
				uts.setBusi_type("充值");
			}
			if("02".equals(type)){
				uts.setBusi_type("提现");
				switch (status) {
					case "0":uts.setTran_state("初始");break;
					case "1":uts.setTran_state("受理中");break;
					case "4":uts.setTran_state("不明（待确认）");break;
					case "12":uts.setTran_state("已冻结");break;
					case "13":uts.setTran_state("待解冻");break;
					case "14":uts.setTran_state("财务已审核");break;
					case "6":uts.setTran_state("其他（需人工查询跟进）");break;
					case "2":uts.setTran_state("成功");break;
					case "3":uts.setTran_state("失败");break;
					case "5":uts.setTran_state("交易关闭（提现）");break;
					case "15":uts.setTran_state("财务审核失败");break;
				}
			}
			if("03".equals(type)){
				uts.setBusi_type("标的转账");				
			}
			if("04".equals(type)){
				uts.setBusi_type("转账");
			}
			if(value.containsKey("sms_num")){
				uts.setSms_num(value.get("sms_num"));
			}
			if(value.containsKey("com_amt_type")){
				String str = value.get("com_amt_type");
				if("1".equals(str)) uts.setCom_amt_type("交易方承担");
				if("2".equals(str)) uts.setCom_amt_type("平台商户（手续费账户）承担");
				if("-99".equals(str)) uts.setCom_amt_type("见商户协议里手续费相关部分。");
			}
			else{
				uts.setCom_amt_type("无");
			}
			if(value.containsKey("amount")){
				BigDecimal am=new BigDecimal(value.get("amount"));
				uts.setAmount(BigDecimalAlgorithm.div(am,new BigDecimal(100)).setScale(2,BigDecimal.ROUND_DOWN).toString());
			}
			if(value.containsKey("orig_amt")){
				BigDecimal orig_amt=new BigDecimal(value.get("orig_amt"));
				uts.setOrig_amt(BigDecimalAlgorithm.div(orig_amt, new BigDecimal(100)).setScale(2,BigDecimal.ROUND_DOWN).toString());
			}
			if(value.containsKey("com_amt")){
				BigDecimal com_amt=new BigDecimal(value.get("com_amt"));
				uts.setCom_amt(BigDecimalAlgorithm.div(com_amt, new BigDecimal(100)).setScale(2,BigDecimal.ROUND_DOWN).toString());
			}
		} catch (ReqDataException | RetDataException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
		return uts;
	}

	@Override
	public PtpMerQuery queryPtpMer(PtpMerQueryConditon ptpMerQueryCondition) {
		if(ptpMerQueryCondition.getQuery_mer_id()==null||"".equals(ptpMerQueryCondition.getQuery_mer_id())){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"商户号不能为空");
		}
		if(ptpMerQueryCondition.getAccount_type()==null||"".equals(ptpMerQueryCondition.getAccount_type())){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"被充值企业资金账户托管平台的账户类型不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("query_mer_id", ptpMerQueryCondition.getQuery_mer_id());
		map.put("account_type", ptpMerQueryCondition.getAccount_type());
		UmpaySignature umpaySignature = new UmpaySignature("ptp_mer_query", map);
		PtpMerQuery pmq=new PtpMerQuery();
		Map<String, String> value = null;
		try {
			value=umpaySignature.getSignature();
			pmq.setRet_code(value.get("ret_code"));
			pmq.setRet_msg(value.get("ret_msg"));
			if(!"0000".equals(value.get("ret_code"))) return pmq;
			pmq.setQuery_mer_id(value.get("query_mer_id"));
			if(value.containsKey("balance")){
				String balance = value.get("balance");
				BigDecimal amount=new BigDecimal(balance);
				pmq.setBalance(BigDecimalAlgorithm.div(amount, new BigDecimal(100)).setScale(2,BigDecimal.ROUND_DOWN).toString());
			}
			if("01".equals(value.get("account_type"))){
				pmq.setAccount_type("商户现金账户");
			}
			if("02".equals(value.get("account_type"))){
				pmq.setAccount_type("商户手续费账户");
			}
			//账户状态1-正常 2-挂失 3-冻结 4-锁定 9-销户
			if(value.containsKey("account_state")){
				String state=value.get("account_state");
				if("1".equals(state))pmq.setAccount_state("正常");
				if("2".equals(state))pmq.setAccount_state("挂失");
				if("3".equals(state))pmq.setAccount_state("冻结");
				if("4".equals(state))pmq.setAccount_state("锁定");
				if("9".equals(state))pmq.setAccount_state("销户");
			}
			
		} catch (ReqDataException|RetDataException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
		return pmq;
	}

	@Override
	public BindcardSearch queryBindcard(BindcardSearchCondition bindcardSearchCondition) {
		if(bindcardSearchCondition.getOrder_id()==null||"".equals(bindcardSearchCondition.getOrder_id())){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"订单号不能为空");
		}
		if(bindcardSearchCondition.getMer_date()==null||"".equals(bindcardSearchCondition.getMer_date())){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"订单日期不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", bindcardSearchCondition.getOrder_id());
		map.put("mer_date", bindcardSearchCondition.getMer_date());
		UmpaySignature umpaySignature = new UmpaySignature("bindcard_search", map);
		BindcardSearch bcs=new BindcardSearch();
		Map<String, String> value = null;
		try {
			value = umpaySignature.getSignature();
			bcs.setRet_code(value.get("ret_code"));
			bcs.setRet_msg(value.get("ret_msg"));
			if(!"0000".equals(value.get("ret_code"))) return bcs;
			bcs.setOrder_id(value.get("order_id"));
			if(value.containsKey("mer_date")){
				String date = value.get("mer_date");
				StringBuffer strb=new StringBuffer(date);
				strb.insert(4, "-");
				strb.insert(7, "-");
				bcs.setMer_date(strb.toString());
			}
			String state = value.get("tran_state");
			//1-处理中（涉及资料上传，人工审核）2-成功 3-失败 	4-不明（需要人工查询）6-其他（需要人工查询）
			if("1".equals(state)) bcs.setTran_state("处理中");
			if("2".equals(state)) bcs.setTran_state("成功");
			if("3".equals(state)) bcs.setTran_state("失败");
			if("4".equals(state)) bcs.setTran_state("不明（需要人工查询）");
			if("6".equals(state)) bcs.setTran_state("其他（需要人工查询）");
			
		} catch (ReqDataException|RetDataException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		} 
		return bcs;
	}

	@Override
	public Page<UmpUserFund> queryUserFund(Page<UmpUserFund> page) {
		String condition =(String) page.getParams().get("condition");
		Pattern pattern = Pattern.compile("[0-9]*");
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try{
			if(!"".equals(condition)&&condition.length()==11&&pattern.matcher(condition).matches()){
				page.getParams().put("mobile", cipher.encrypt(condition));
			}else{
				page.getParams().put("loginName", condition);
			}
			List<UmpUserFund> results = userService.queryUserFund(page);
			for (UmpUserFund umpUserFund : results) {
				UmpAccount ua=new UmpAccount();
				ua.setUserId(umpUserFund.getUserId());
				UmpAccount queryAccount = umpAccountService.queryByParams(ua);
				if(umpUserFund.getUserName()==null){
					umpUserFund.setUserName("未实名认证");
				}
				if(umpUserFund.getAviAmount()==null){
					umpUserFund.setAviAmount(new BigDecimal(0));
					umpUserFund.setFrozenAmount(new BigDecimal(0));
				}
				umpUserFund.setMobile(cipher.decrypt(umpUserFund.getMobile()));
				if(queryAccount==null){
					umpUserFund.setUmpAccountName("联动未开户");
					umpUserFund.setUmpAccountId("无");
					umpUserFund.setUmpAviAmount("无");
				}else{
					umpUserFund.setUmpAccountName(queryAccount.getAccountName());
					umpUserFund.setUmpAccountId(queryAccount.getAccountId());
					Map<String,String> map = new HashMap<String, String>();
					map.put("user_id", queryAccount.getAccountName());
					map.put("is_find_account", "01");
					map.put("is_select_agreement", "1");
					UmpaySignature umpaySignature = new UmpaySignature("user_search", map);
					Map<String, String> signature = umpaySignature.getSignature();
					if("0000".equals(signature.get("ret_code"))){
						String balance = signature.get("balance");
						umpUserFund.setUmpAviAmount(BigDecimalAlgorithm.div(new BigDecimal(balance), new BigDecimal(100)).toString());
					}else{
						umpUserFund.setUmpAviAmount("查询出错");
					}
				}
			}
			page.setResults(results);
			return page;
		}catch(Exception e){
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
	}
}
