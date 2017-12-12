package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.RepaymentBiz;
import com.zhongyang.java.dao.RepaymentDao;
import com.zhongyang.java.pojo.*;
import com.zhongyang.java.service.*;
import com.zhongyang.java.system.*;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.GlobalConstants;
import com.zhongyang.java.system.uitl.UploadExcelUtil;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.InvestRepayment;
import com.zhongyang.java.pojo.LoanRepayment;
import com.zhongyang.java.pojo.RepayIvest;
import com.zhongyang.java.pojo.RepayQuery;
import com.zhongyang.java.pojo.RepaymentByID;
import com.zhongyang.java.pojo.ReturnRepayIvest;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.InvestRepayMentService;
import com.zhongyang.java.service.LoanRepayMentService;
import com.zhongyang.java.service.RepaymentService;
import com.zhongyang.java.service.UmpTenderService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.CallBackRepay;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ProjectTransfer;
import com.zhongyang.java.vo.RepaymentInfo;
import com.zhongyang.java.vo.ReturnRepayment;
import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.loan.InvestStatus;
import com.zhongyang.java.vo.loan.LoanRepayMent;
import com.zhongyang.java.vo.loan.LoanStatus;
import com.zhongyang.java.vo.loan.Method;

import org.apache.log4j.Logger;

@Service
public class RepaymentBizImpl extends UtilBiz implements RepaymentBiz {
	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		notify_url = (String) ((Map) sysMap.get("NOTIFYURL")).get("REPAYTOP");
		zycfAccount = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
		feeNotify_url = (String) ((Map) sysMap.get("NOTIFYURL")).get("REPAYINVESTFEE");
		zycfUmpAccount = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
		notify_url_repaypto = (String) ((Map) sysMap.get("NOTIFYURL")).get("REPAYPTO");
		BORROWERFEE = (String) ((Map) sysMap.get("NOTIFYURL")).get("BORROWERFEE");
		REPAYBYPLATFORM = (String) ((Map) sysMap.get("NOTIFYURL")).get("REPAYBYPLATFORM");
	}

	private static Logger logger = Logger.getLogger(RepaymentBizImpl.class);

	private static String notify_url;

	private static String zycfAccount;

	private static String feeNotify_url;

	private static String zycfUmpAccount;

	private static String notify_url_repaypto;

	private static String BORROWERFEE;

	private static String REPAYBYPLATFORM;

	@Autowired
	private RepaymentService repaymentService;
	@Autowired
	private UserService userService;
	@Autowired
	private UmpTenderService umpTenderService;
	@Autowired
	private UserFundService userFundService;
	@Autowired
	private LoanRepayMentService loanRepayMentService;
	@Autowired
	private InvestRepayMentService investRepayMentService;
	@Autowired
	private FundRecordService fundRecordService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private InvestService investService;
	@Autowired
	private RepaymentDao repaymentDao;
	@Autowired
	private UmpTenderTransferRecordService umpTenderTransferRecordService;
	@Autowired
	private ClientFundRecordService clientFundRecordService;

	@Override
	public PagerVO repayQuery(PagerVO pagerVo) {
		Page<RepayQuery> page = new Page();
		if (pagerVo.getStart() > 0)
			page.setPageNo(pagerVo.getStart());
		if (pagerVo.getLength() > 0)
			page.setPageSize(pagerVo.getLength());

		page.setParams(Util.getPageProFileValue(pagerVo.getField(), pagerVo.getValue(), pagerVo.getSort()));
		pagerVo.setData(repaymentService.repayQuery(page));
		pagerVo.setRecordsTotal(page.getTotalRecord());
		pagerVo.setRecordsFiltered(page.getTotalPage());
		return pagerVo;
	}

	public Page<RepayQuery> repaymentQuery(Page<RepayQuery> page) {
		if ("TODAYDUE".equals(page.getParams().get("status"))) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "UNDUE");
			map.put("startTime", new Date());
			map.put("endTime", new Date());
			page.setParams(map);
		}
		if (!"".equals(page.getParams().get("name")) && page.getParams().get("name") != null) {
			// StringBuffer loanUserId = new StringBuffer("'");
			List<User> list = userService.getUserByName(page.getParams().get("name").toString());
			if (list != null && list.size() > 0) {
				page.getParams().put("loanUserId", list.get(0).getId());
			}
		}
		List<RepayQuery> list = repaymentService.repaymentQuery(page);
		page.setResults(list);
		return page;
	}

	// 未到期还款导出
	@Override
	public ResponseEntity<byte[]> exportRepayQueryData(HttpServletRequest request, Page<RepayQuery> page) {
		try {
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "到期日期", "标的名称", "借款人", "期数", "待还金额(元)", "账户余额（元）", "标的账户" });
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "date", "title", "name", "currentperiod", "amount", "available_amount",
					"amountTender" });
			List<RepayQuery> list = repaymentService.repaymentQuery(page);
			String name = "尚未到期还款信息明细";
			ResponseEntity<byte[]> responseEntity;
			responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, list, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "尚未到期还款信息明细导出失败!");
		}
	}

	/*
	 * Request for repayment. This will transfer money from debtor's account to
	 * tender's account. (non-Javadoc)
	 * 
	 * @see com.zhongyang.java.biz.RepaymentBiz#repay(java.lang.String)
	 */

	@Override
	@Transactional
	public Message repay(String id) {
		Message message = new Message();
		ApplicationBean appBean = new ApplicationBean();
		try {
			RepaymentByID repaymentByID;
			if (id != null && !"".equals(id)) {
				repaymentByID = new RepaymentByID();
				repaymentByID.setId(id);
				repaymentByID = repaymentService.repaymentById(repaymentByID);

				if (repaymentByID == null) {
					message.setCode(3003);
					message.setMessage("还款不存在");
					return message;
				}
				BigDecimal amount = repaymentByID.getMoney();
				logger.info("本期借款人还款金额：" + amount);
				// 判断借款人是否有足够的还款金额
				if (amount.compareTo(repaymentByID.getAvailable_amount()) > 0) {
					message.setCode(1);
					message.setMessage("余额不足");
					logger.info("借款人余额不足：" + repaymentByID.getAvailable_amount() + "应还金额：" + amount);
					return message;
				}
				if (getZsessionObject(repaymentByID.getLoan_id() + "repay") != null) {
					message.setCode(3003);
					message.setMessage("重复还款！");
					logger.info("重复还款" + repaymentByID.getLoan_id());
					return message;
				}
				setZsession("true", repaymentByID.getLoan_id() + "repay");

				if (amount != null && !"0.00".equals(amount.toString())) {

					String uuid = appBean.orderId();
					Map<String, String> map = new HashMap<String, String>();
					// 借款人还款冻结还款资金
					UserFund uf = new UserFund();
					uf.setUserid(repaymentByID.getUserId());
					UserFund fund = userFundService.byUserID(uf);
					
					uf.setAvailableAmount(amount.multiply(new BigDecimal(-1)));
					uf.setFrozenAmount(amount);
					userFundService.modifyUserFund(uf);

					// 发送请求前添加借款人资金记录
					FundRecord fundRecord = new FundRecord();
					fundRecord.setAmount(repaymentByID.getMoney());
					String fundRecordId = UUID.randomUUID().toString().toUpperCase();
					fundRecord.setId(fundRecordId);
					fundRecord.setOperation(FundRecordOperation.OUT);
					fundRecord.setOrderid(uuid);
					fundRecord.setStatus(FundRecordStatus.PROCESSING);
					fundRecord.setTimerecorded(new Date());
					fundRecord.setType(FundRecordType.LOAN_REPAY);
					fundRecord.setDescription("贷款还款");
					fundRecord.setRecordpriv(repaymentByID.getTitle() + "#loanid=" + repaymentByID.getLoan_id()
							+ "#period=" + repaymentByID.getCurrentPeriod());
					fundRecord.setUserId(repaymentByID.getUserId());
					fundRecord.setAviAmount(fund.getAvailableAmount().subtract(amount));
					fundRecordService.addFundRecord(fundRecord);
					logger.info("借款人还款修改账户记录完成！userid=" + repaymentByID.getUserId());
					setZsession(repaymentByID, uuid);

					SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
					String mer_date = sdf.format(new Date());

					ProjectTransfer projectTransfer = new ProjectTransfer();
					projectTransfer.setOrder_id(uuid);
					projectTransfer.setMer_date(mer_date);
					projectTransfer.setProject_id(this.getProjectId(repaymentByID.getLoan_id()));
					projectTransfer.setServ_type("03");
					projectTransfer.setTrans_action("01");
					projectTransfer.setPartic_type("02");
					projectTransfer.setPartic_acc_type("01");
					projectTransfer.setPartic_user_id(repaymentByID.getAccountName());
					projectTransfer.setPartic_account_id(repaymentByID.getAccountId());
					BigDecimal amounts = repaymentByID.getMoney().multiply(new BigDecimal(100));
					projectTransfer.setAmount(amounts.longValue());
					projectTransfer.setNotify_url(notify_url_repaypto);

					UmpTenderTransferRecord uttfr = new UmpTenderTransferRecord();
					uttfr.setAmount(amounts.divide(new BigDecimal(100)));
					uttfr.setLoanid(repaymentByID.getLoan_id());
					uttfr.setOrderid(uuid);
					uttfr.setStatus(FundRecordStatus.PROCESSING);
					uttfr.setTimecreated(new Date());
					uttfr.setUserid(repaymentByID.getUserId());
					uttfr.setUmpaccountid(repaymentByID.getAccountId());
					uttfr.setTenderaction(FundRecordOperation.IN);
					uttfr.setTransfertype(FundRecordType.LOAN_REPAY);
					umpTenderTransferRecordService.addUmpTenderTransferRecord(uttfr);
					// 调用联动优势标的免密转账接口，将资金从借款人账户转入标的账户。
					logger.info("借款人还款发送请求到联动orderid=" + uuid);
					UmpaySignature umpaySignature = new UmpaySignature("project_transfer_nopwd",
							getProjectTransfer(projectTransfer));
					Map response = umpaySignature.getSignature();
					logger.info("联动优势还款响应：" + response);

					String ret_code = "";
					if (response.containsKey("ret_code"))
						ret_code = (String) response.get("ret_code");

					if (!"0000".equals(ret_code)) {
						removeZsessionObject(repaymentByID.getLoan_id() + "repay");
						// 借款人还款资金解冻
						UserFund userFund = new UserFund();
						userFund.setUserid(repaymentByID.getUserId());
						userFund.setAvailableAmount(amount);
						userFund.setFrozenAmount(amount.multiply(new BigDecimal(-1)));
						userFundService.modifyUserFund(userFund);

						// 修改借款人资金记录
						FundRecord fr = new FundRecord();
						fr.setId(fundRecordId);
						fr.setStatus(FundRecordStatus.FAILED);
						fr.setAviAmount(fund.getAvailableAmount());
						fundRecordService.modifyFundRecord(fr);

						// 修改标的交易记录
						UmpTenderTransferRecord tfr = new UmpTenderTransferRecord();
						tfr.setOrderid(uuid);
						tfr.setStatus(FundRecordStatus.FAILED);
						umpTenderTransferRecordService.modifyUmpTenderTransferRecord(tfr);
						message.setCode(0);
						message.setMessage("还款失败，请联系技术人员查找原因");
						return message;

					}
					message.setCode(0);
					message.setMessage("交易成功");
					return message;
				}

			}
		} catch (Exception e) {
			message.setCode(3003);
			message.setMessage("调用联动优势参数错误：" + e.getMessage());
			e.printStackTrace();
			return message;

		}
		message.setCode(3005);
		message.setMessage("其他未知错误");
		return message;
	}

	/*
	 * callback for the repay request. Following will be done: 1) update the
	 * debtor's account 2) transfer money from the tender account to investors
	 * account. (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.RepaymentBiz#callBackRepay(com.zhongyang.java.
	 * vo.CallBackRepay)
	 */

	@Override
	public String callBackRepay(CallBackRepay callBackRepay) {
		try {
			logger.info("借款人还款回调订单号" + callBackRepay.getOrder_id() + "联动返回状态" + callBackRepay.getRet_code());

			String order_id = callBackRepay.getOrder_id();
			RepaymentByID repaymentByID = (RepaymentByID) getZsessionObject(order_id);

			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("order_id", order_id);
			returnMap.put("ret_code", callBackRepay.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(returnMap);
			FundRecord fr = this.getFundRecord(order_id);
			
			//保障只有一次回调
			if (FundRecordStatus.SUCCESSFUL.equals(fr.getStatus())) {
				logger.info("还款订单"+callBackRepay.getOrder_id()+"第二次回调");
				return umpaySignature.callBackSignature();
			}
			logger.info("还款订单"+callBackRepay.getOrder_id()+"第一次回调");
			if (!callBackRepay.getRet_code().equals("0000")) {

				UserFund uf = new UserFund();
				uf.setUserid(repaymentByID.getUserId());
				uf.setAvailableAmount(repaymentByID.getMoney());
				uf.setFrozenAmount(repaymentByID.getMoney().multiply(new BigDecimal(-1)));
				userFundService.modifyUserFund(uf);
				
				UserFund fund = userFundService.byUserID(uf);
				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order_id);
				fundRecord.setAviAmount(fund.getAvailableAmount().add(repaymentByID.getMoney()));
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecordService.modifyFundRecord(fundRecord);

				UmpTenderTransferRecord uttfr = new UmpTenderTransferRecord();

				uttfr.setOrderid(order_id);
				uttfr.setStatus(FundRecordStatus.FAILED);
				umpTenderTransferRecordService.modifyUmpTenderTransferRecord(uttfr);

				return umpaySignature.callBackSignature();
			}

			BigDecimal pre = (BigDecimal) getZsessionObject(repaymentByID.getLoan_id() + "pre");
			
			UmpTenderTransferRecord uttfr = umpTenderTransferRecordService.getUmpTenderTransferRecord(order_id);
			uttfr.setRetcode(callBackRepay.getRet_code());
			uttfr.setRetmsg(callBackRepay.getRet_msg());
			uttfr.setTimelastupdated(new Date());
			if ("0000".equals(callBackRepay.getRet_code())) {
				uttfr.setStatus(FundRecordStatus.SUCCESSFUL);
			}
			umpTenderTransferRecordService.modifyUmpTenderTransferRecord(uttfr);
			if (repaymentByID != null && "0000".equals(callBackRepay.getRet_code()) && fr != null
					&& !"SUCCESSFUL".equals(fr.getStatus().toString())) {
				if (!"0.00".equals(repaymentByID.getLoanInterestFee().toString())) {
					this.borrowerServiceFee(repaymentByID);// 存在借款人利息管理服务费
				}

				// update tender account.
				UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(repaymentByID.getLoan_id());
				umpTender.setAmount(umpTender.getAmount().add(repaymentByID.getMoney()));
				int num = 0;
				num = umpTenderService.updateAmound(umpTender);
				logger.info("还款回调修改标的账户金额完成:" + umpTender.getAmount() + "num:" + num);
				// 借款人用户资金账户!!!!!
				UserFund userFund = new UserFund();
				userFund.setUserid(repaymentByID.getUserId());

				// 账户余额扣除还款额
				if (pre == null) {
					userFund.setDueOutAmount((repaymentByID.getAmountPrincipal().add(repaymentByID.getAmountInterest())).multiply(new BigDecimal(-1))); // 非提前还款借款人待还金额-本期本金-本期利息
					logger.info("正常还款借款人待还金额-本期本金-本期利息=========");
				}
				userFund.setTimelastupdated(new Date());
				userFund.setFrozenAmount(repaymentByID.getMoney().multiply(new BigDecimal(-1)));
				userFundService.modifyUserFund(userFund);
				logger.info("还款回调借款人账户扣除还款金额完成！");
				// 还款计划处理
				LoanRepayment loanRepayment = new LoanRepayment();
				if (pre != null) {
					loanRepayment.setStatus(LoanRepayMent.PREPAY);
				} else {
					loanRepayment.setStatus(LoanRepayMent.REPAYED);
				}
				loanRepayment.setId(repaymentByID.getId());
				loanRepayment.setRepaydate(new Date());
				loanRepayment.setRepayamount(repaymentByID.getMoney());
				loanRepayment.setSourceId(repaymentByID.getUserId());
				//修改还款计划
				int coun=loanRepayMentService.updateStatus(loanRepayment);
				
				logger.info("还款回调修改借款人还款计划完成");
				// 借款用户资金流水
				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order_id);
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				fundRecord.setTimerecorded(new Date());
				int fcount=fundRecordService.updateFundRecordByOrderId(fundRecord);
				logger.info("还款回调修改借款人账户资金流水");
				// 修改标的状态为CLIEARED
				Loan loan = loanService.queryLoanById(repaymentByID.getLoan_id());
				if (loan != null && Method.BulletRepayment.equals(loan.getMethod())) {
					loan.setMonths(1);
				}
				if (loan != null && (loan.getMonths() == repaymentByID.getCurrentPeriod())) {
					loanService.updateLoanStatus(repaymentByID.getLoan_id(), LoanStatus.SETTLED.toString(),
							LoanStatus.CLEARED.toString());
					logger.info("最后一期借款人还款成功修改标的状态为CLEARED");
				}
				RepayIvest repayIvest = new RepayIvest();
				repayIvest.setLoanID(repaymentByID.getLoan_id());
				repayIvest.setCurrentPeriod(repaymentByID.getCurrentPeriod());
				repayIvest.setSource(repaymentByID.getUserId());
				if(coun==1&&fcount==1){
					MyThread myThread = new MyThread(repayIvest);
					myThread.run();
				}
				// 清空缓存
				removeZsessionObject(order_id);
			}
			return umpaySignature.callBackSignature();
		} catch (ReqDataException e) {
			logger.info("req+callBackRepay" + e.fillInStackTrace());
		} catch (RetDataException e) {
			logger.info("ret+callBackRepay" + e.fillInStackTrace());
		} catch (Exception e) {
			logger.info("callBackRepay" + e.fillInStackTrace());
		}
		return null;
	}

	class MyThread extends Thread {
		MyThread(RepayIvest repayIvest) {
			this.repayIvest = repayIvest;
		}

		private RepayIvest repayIvest;

		public void run() {
			repayInvest(repayIvest);
		}
	}

	public void repayInvest(RepayIvest repayIvest) {
		logger.info("投资人开始回款");

		if (repayIvest != null) {
			Loan loan = null;

			try {
				loan = loanService.queryLoanById(repayIvest.getLoanID());
				if (loan != null && Method.BulletRepayment.equals(loan.getMethod())) {
					loan.setMonths(1);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.info("查询当前标的信息失败" + e1.getMessage());
			}
			// 获取标的本期回款计划表
			List<ReturnRepayIvest> list = repaymentService.repaymentInvestByLoanId(repayIvest);
			BigDecimal pre = (BigDecimal) getZsessionObject(loan.getId() + "pre");
			setZsession(list.size(), loan.getId() + "count");

			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
			int count = list.size();
			for (ReturnRepayIvest reInvest : list) {
				Date date=new Date();
				if (LoanRepayMent.PREPAY.equals(reInvest.getStatus())
						|| LoanRepayMent.REPAYED.equals(reInvest.getStatus())) {
					count = count - 1;
					setZsession(count, loan.getId() + "count");
					continue;
				}

				UserFund uf = new UserFund();
				uf.setUserid(reInvest.getUserid());
				if (pre != null) {
					setZsession(pre, reInvest.getRepayId() + "flag"); // 针对回款计划（提前回款标志）
				}
				logger.info("投资人按回款计划回款userid" + reInvest.getUserid() + "##repayid=" + reInvest.getRepayId()
						+ "##amount=" + reInvest.getAmount());
				// ** 标的账户处理及回款计划处理
				try {
					uf = userFundService.byUserID(uf);
					UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(repayIvest.getLoanID());
					umpTender.setAmount(umpTender.getAmount().subtract(reInvest.getAmount()));
					int num = 0;
					num = umpTenderService.updateAmound(umpTender);
					
					logger.info("投资人回款修改标的账户金额！amount=" + umpTender.getAmount());
					InvestRepayment investRepayment = new InvestRepayment();
					investRepayment.setId(reInvest.getRepayId());
					investRepayment.setRepaydate(date);
					investRepayment.setRepayamount(reInvest.getAmount());
					investRepayment.setSourceId(repayIvest.getSource());
					repaymentService.updateInvestRepaymentById(investRepayment);

					/**
					 * 投资用户回款
					 */
					ApplicationBean appBean = new ApplicationBean();
					String uuid = appBean.orderId();
					setZsession(loan.getMonths(), uuid + "lastPriod");

					// 投资人回款增加资金记录
					FundRecord fundRecord = new FundRecord();
					String fundRecordId = null;

					// 还款资金信息放入缓存
					setZsession(reInvest, uuid);

					String mer_date = sdf.format(date);
					BigDecimal Amountinter = reInvest.getAmountinterest().multiply(loan.getInvestInterestFee())
							.divide(new BigDecimal(100));
					logger.info("====投资利息管理费：=====" + Amountinter + "==投资利息管理费：=="
							+ Amountinter.setScale(2, BigDecimal.ROUND_DOWN));
					BigDecimal amounts = reInvest.getAmount().subtract(Amountinter.setScale(2, BigDecimal.ROUND_DOWN))
							.multiply(new BigDecimal(100));
					logger.info("========联动优势投资用户回款金额：==========" + amounts + "==总金额：==" + reInvest.getAmount() + "=="
							+ reInvest.getAmount().subtract(Amountinter.setScale(2, BigDecimal.ROUND_DOWN)));

					fundRecord.setAmount(reInvest.getAmount());
					fundRecordId = UUID.randomUUID().toString().toUpperCase();
					fundRecord.setId(fundRecordId);
					fundRecord.setDescription("回款转入金额");
					fundRecord.setRecordpriv(
							loan.getTitle() + "#loanid=" + loan.getId() + "#period=" + reInvest.getCurrentPeriod());
					fundRecord.setOperation(FundRecordOperation.IN);
					fundRecord.setOrderid(uuid);
					fundRecord.setStatus(FundRecordStatus.PROCESSING);
					fundRecord.setTimerecorded(date);
					fundRecord.setType(FundRecordType.INVEST_REPAY);
					fundRecord.setUserId(reInvest.getUserid());
					fundRecord.setAviAmount(uf.getAvailableAmount().add(amounts.divide(new BigDecimal(100))));
					fundRecordService.addFundRecord(fundRecord);

					// 标的进出帐记录流水
					UmpTenderTransferRecord uttfr = new UmpTenderTransferRecord();
					uttfr.setAmount(amounts.divide(new BigDecimal(100)));
					uttfr.setLoanid(reInvest.getLoanid());
					uttfr.setOrderid(uuid);
					uttfr.setStatus(FundRecordStatus.PROCESSING);
					uttfr.setTimecreated(date);
					uttfr.setUserid(reInvest.getUserid());
					uttfr.setUmpaccountid(reInvest.getAccountId());
					uttfr.setTenderaction(FundRecordOperation.OUT);
					uttfr.setTransfertype(FundRecordType.LOAN_REPAY);
					umpTenderTransferRecordService.addUmpTenderTransferRecord(uttfr);
					
					logger.info("投资人回款请求发送联动orderid=" + uuid + "====amount=" + reInvest.getAmount());
					Map<String, String> response = this.umpInvestRepay(uuid, loan, reInvest, mer_date, repayIvest,
							notify_url);
					String code=(String)response.get("ret_code");
					
					if(!"0000".equals(code)){
						
						UmpTender tender = umpTenderService.queryUmpTenderByLoanId(repayIvest.getLoanID());
						umpTender.setAmount(tender.getAmount().add(reInvest.getAmount()));
						umpTenderService.updateAmound(umpTender);
						
						FundRecord record = new FundRecord();
						record.setId(fundRecordId);
						record.setStatus(FundRecordStatus.FAILED);
						fundRecordService.modifyFundRecord(record);
						
						UmpTenderTransferRecord ur = new UmpTenderTransferRecord();
						ur.setOrderid(uuid);
						ur.setStatus(FundRecordStatus.FAILED);
						umpTenderTransferRecordService.modifyUmpTenderTransferRecord(ur);
						
					}
					/**
					 * 投资用户利息费用处理
					 */
					BigDecimal inAmount = null;
					inAmount = reInvest.getAmountinterest().multiply(loan.getInvestInterestFee())
							.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);

					logger.info("======================投资利息管理费：==================" + inAmount);
					// 增加一笔手续费流水
					String orderId = appBean.orderId();
					if (inAmount != null && !"0.00".equals(inAmount.toString())) {
						setZsession(reInvest, orderId);

						// 投资人资金记录增加利息费流水
						fundRecord = new FundRecord();
						String recordId = UUID.randomUUID().toString().toUpperCase();
						fundRecord.setId(recordId);
						fundRecord.setOperation(FundRecordOperation.OUT);
						fundRecord.setOrderid(orderId);
						fundRecord.setDescription("投资人回款利息费");
						fundRecord.setStatus(FundRecordStatus.PROCESSING);
						fundRecord.setTimerecorded(date);
						fundRecord.setType(FundRecordType.FEE_INVEST_INTEREST);
						fundRecord.setUserId(reInvest.getUserid());
						fundRecord.setAmount(inAmount);
						fundRecord.setAviAmount(uf.getAvailableAmount().add(amounts.divide(new BigDecimal(100))));
						logger.info("======================投资人利息管理费：==================" + inAmount);
						fundRecordService.addFundRecord(fundRecord);

						// 平台增加一笔收入流水
						ClientFundRecord clientFundRecord = new ClientFundRecord();
						clientFundRecord.setAccount(zycfUmpAccount);
						String cfrId = UUID.randomUUID().toString().toUpperCase();
						clientFundRecord.setId(cfrId);
						clientFundRecord.setAmount(inAmount);
						clientFundRecord.setDescription("投资人利息管理费");
						clientFundRecord.setOperation(FundRecordOperation.IN);
						clientFundRecord.setOrderid(orderId);
						clientFundRecord.setStatus(FundRecordStatus.PROCESSING);
						clientFundRecord.setTimerecorded(date);
						clientFundRecord.setType(FundRecordType.FEE_INVEST_INTEREST);
						clientFundRecord.setUserid(reInvest.getUserid());
						clientFundRecord.setRecordpriv(
								loan.getTitle() + "#loanid=" + loan.getId() + "#period=" + reInvest.getCurrentPeriod());
						clientFundRecordService.addClientFundRecord(clientFundRecord);
						logger.info("投资人回款==平台增加一笔收入流水" + FundRecordStatus.PROCESSING);

						// 标的进出帐记录流水
						UmpTenderTransferRecord utt = new UmpTenderTransferRecord();
						utt.setAmount(inAmount);
						utt.setLoanid(reInvest.getLoanid());
						utt.setOrderid(orderId);
						utt.setStatus(FundRecordStatus.PROCESSING);
						utt.setTimecreated(date);
						utt.setUserid(reInvest.getUserid());
						utt.setUmpaccountid(reInvest.getAccountId());
						utt.setTenderaction(FundRecordOperation.OUT);
						utt.setTransfertype(FundRecordType.LOAN_REPAY);
						umpTenderTransferRecordService.addUmpTenderTransferRecord(utt);
						
						if("0000".equals(code)){
							Map<String, String> res = this.umpInvestFeeRepay(orderId, loan, mer_date, repayIvest, feeNotify_url, zycfAccount,
									inAmount);
							String ret_code=res.get("ret_code");
							if(!"0000".equals(ret_code)){
								FundRecord record = new FundRecord();
								recordId = UUID.randomUUID().toString().toUpperCase();
								record.setId(recordId);
								record.setStatus(FundRecordStatus.FAILED);
								fundRecordService.modifyFundRecord(record);
								
								UmpTenderTransferRecord ut = new UmpTenderTransferRecord();
								ut.setOrderid(orderId);
								ut.setStatus(FundRecordStatus.FAILED);
								umpTenderTransferRecordService.modifyUmpTenderTransferRecord(ut);
								
								ClientFundRecord clientRecord = new ClientFundRecord();
								clientRecord.setOrderid(orderId);
								clientRecord.setStatus(FundRecordStatus.FAILED);
								clientFundRecordService.updateClentFundByOrderId(clientRecord);
							}
							
						}else{
							FundRecord record = new FundRecord();
							recordId = UUID.randomUUID().toString().toUpperCase();
							record.setId(recordId);
							record.setStatus(FundRecordStatus.FAILED);
							fundRecordService.modifyFundRecord(record);
							
							UmpTenderTransferRecord ut = new UmpTenderTransferRecord();
							ut.setOrderid(orderId);
							ut.setStatus(FundRecordStatus.FAILED);
							umpTenderTransferRecordService.modifyUmpTenderTransferRecord(ut);
							
							ClientFundRecord clientRecord = new ClientFundRecord();
							clientRecord.setOrderid(orderId);
							clientRecord.setStatus(FundRecordStatus.FAILED);
							clientFundRecordService.updateClentFundByOrderId(clientRecord);
						}
						
					}

				} catch (Exception e) {
					logger.info("添加平台资金记录：" + e.fillInStackTrace());
					e.printStackTrace();
					continue;
				}

			}
			removeZsessionObject(loan.getId() + "pre");
		}
	}

	public Message adjustRepay(String loanId, int per) {
		RepayIvest repayIvest = new RepayIvest();
		repayIvest.setLoanID(loanId);
		repayIvest.setCurrentPeriod(per);
		MyThread myThread = new MyThread(repayIvest);
		myThread.run();

		Message message = new Message();
		message.setCode(0);
		message.setMessage("调账成功");
		return message;
	}

	public Map<String, String> umpInvestRepay(String uuid, Loan loan, ReturnRepayIvest reInvest, String mer_date,
			RepayIvest repayIvest, String notify_url) {
		ProjectTransfer projectTransfer = new ProjectTransfer();
		projectTransfer.setOrder_id(uuid);
		projectTransfer.setMer_date(mer_date);
		projectTransfer.setProject_id(getProjectId(repayIvest.getLoanID()));
		projectTransfer.setServ_type("54");
		projectTransfer.setTrans_action("02");
		projectTransfer.setPartic_type("01");
		projectTransfer.setPartic_acc_type("01");
		projectTransfer.setPartic_user_id(reInvest.getAccountName());
		projectTransfer.setPartic_account_id(reInvest.getAccountId());
		BigDecimal Amountinter = reInvest.getAmountinterest().multiply(loan.getInvestInterestFee())
				.divide(new BigDecimal(100));
		logger.info(
				"====投资利息管理费：=====" + Amountinter + "==投资利息管理费：==" + Amountinter.setScale(2, BigDecimal.ROUND_DOWN));
		BigDecimal amounts = reInvest.getAmount().subtract(Amountinter.setScale(2, BigDecimal.ROUND_DOWN))
				.multiply(new BigDecimal(100));
		logger.info("========联动优势投资用户回款金额：==========" + amounts + "==总金额：==" + reInvest.getAmount() + "=="
				+ reInvest.getAmount().subtract(Amountinter.setScale(2, BigDecimal.ROUND_DOWN)));
		projectTransfer.setAmount(amounts.longValue());
		projectTransfer.setNotify_url(notify_url);
		UmpaySignature umpaySignature = new UmpaySignature("project_transfer", getProjectTransfer(projectTransfer));
		Map<String, String> response = null;
		try {
			response = umpaySignature.getSignature();
			return response;
		} catch (ReqDataException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		} catch (RetDataException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		logger.info("联动优势还款响应：" + response);
		return null;
	}

	public Map<String, String> umpInvestFeeRepay(String orderId, Loan loan, String mer_date, RepayIvest repayIvest,
			String notify_url, String zycfAccount, BigDecimal inAmount) {
		logger.info("投资人回款利息管理费请求发送联动orderid=" + orderId);
		ProjectTransfer projectTransfer = new ProjectTransfer();
		projectTransfer.setOrder_id(orderId);
		projectTransfer.setMer_date(mer_date);
		projectTransfer.setProject_id(getProjectId(repayIvest.getLoanID()));
		projectTransfer.setServ_type("52");
		projectTransfer.setTrans_action("02");
		projectTransfer.setPartic_type("03");
		projectTransfer.setPartic_acc_type("02");
		projectTransfer.setPartic_user_id(zycfAccount);
		projectTransfer.setAmount(inAmount.multiply(new BigDecimal(100)).longValue());
		projectTransfer.setNotify_url(notify_url);

		UmpaySignature umpaySignatureFee = new UmpaySignature("project_transfer",
				getProjectFeeTransfer(projectTransfer));
		Map<String, String> res = null;
		try {
			res = umpaySignatureFee.getSignature();
			return res;
		} catch (ReqDataException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		} catch (RetDataException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		logger.info("联动优势还款响应：" + res);
		return null;
	}

	// 投资人回款回调
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String callBackRepayTop(CallBackRepay callBackRepay) {
		try {
			logger.info("投资人回款orderid=" + callBackRepay.getOrder_id());
			String order_id = callBackRepay.getOrder_id();
			ReturnRepayIvest returnRepayIvest = (ReturnRepayIvest) getZsessionObject(order_id);
			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("order_id", order_id);
			returnMap.put("ret_code", callBackRepay.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(returnMap);

			UserFund userFund = new UserFund();
			userFund.setUserid(returnRepayIvest.getUserid());
			userFund = userFundService.byUserID(userFund);
			if (!"0000".equals(callBackRepay.getRet_code())) {
				logger.info("投资人回款失败orderid=" + order_id);
				UmpTenderTransferRecord uttfr = umpTenderTransferRecordService.getUmpTenderTransferRecord(order_id);
				uttfr.setRetcode(callBackRepay.getRet_code());
				uttfr.setRetmsg(callBackRepay.getRet_msg());
				uttfr.setTimelastupdated(new Date());
				uttfr.setStatus(FundRecordStatus.FAILED);
				umpTenderTransferRecordService.modifyUmpTenderTransferRecord(uttfr);

				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order_id);
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecord.setTimerecorded(new Date());
				fundRecordService.updateFundRecordByOrderId(fundRecord);

				return umpaySignature.callBackSignature();
			}
			
			logger.info("投资人回款联动回调orderid=" + callBackRepay.getOrder_id());
			FundRecord fr = this.getFundRecord(order_id);
			if (FundRecordStatus.SUCCESSFUL.equals(fr.getStatus())) {
				return null;
			}
			UmpTenderTransferRecord uttfr = umpTenderTransferRecordService.getUmpTenderTransferRecord(order_id);
			uttfr.setRetcode(callBackRepay.getRet_code());
			uttfr.setRetmsg(callBackRepay.getRet_msg());
			uttfr.setTimelastupdated(new Date());
			if ("0000".equals(callBackRepay.getRet_code())) {
				uttfr.setStatus(FundRecordStatus.SUCCESSFUL);
			}
			umpTenderTransferRecordService.modifyUmpTenderTransferRecord(uttfr);
			
			if (returnRepayIvest != null && "0000".equals(callBackRepay.getRet_code()) && fr != null
					&& !"SUCCESSFUL".equals(fr.getStatus().toString())) {

				
				Loan loan = loanService.queryLoanById(returnRepayIvest.getLoanid());
				BigDecimal pre = (BigDecimal) getZsessionObject(returnRepayIvest.getRepayId() + "flag");
				BigDecimal revenue = returnRepayIvest.getAmountinterest();
				BigDecimal fee = revenue.multiply(loan.getInvestInterestFee().divide(new BigDecimal(100)));
				if (getZsessionObject(loan.getId() + "count") != null) {
					int count = (int) getZsessionObject(loan.getId() + "count");
					if (count != 1) {
						setZsession(count--, loan.getId() + "count");
					} else {
						removeZsessionObject(loan.getId() + "count");
						removeZsessionObject(loan.getId() + "repay");
					}

				}
				/*
				 * userFund.setDueInAmount(userFund.getDueInAmount().subtract(
				 * returnRepayIvest.getAmount()));
				 */
				
				InvestRepayment investpayment = new InvestRepayment();
				investpayment.setId(returnRepayIvest.getRepayId());
				// investRepayment.setRepayamount(returnRepayIvest.getAmount());
				// investRepayment.setRepaydate(new Date());
				if (pre != null) {
					investpayment.setStatus(LoanRepayMent.PREPAY);
				} else {
					investpayment.setStatus(LoanRepayMent.REPAYED);
				}
				investRepayMentService.updateStatus(investpayment);
				logger.info("更新投资人回款计划状态！userid=" + returnRepayIvest.getUserid());
				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order_id);
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				fundRecord.setTimerecorded(new Date());
				fundRecordService.updateFundRecordByOrderId(fundRecord);
				logger.info("更新投资人资金流水状态！userid=" + returnRepayIvest.getUserid());
				// 最后一期还款完成修改投资记录还款状态cleared
				int lastPriod = Integer.parseInt(String.valueOf(getZsessionObject(order_id + "lastPriod")));
				logger.info("总期数：" + lastPriod + "当前其数：" + returnRepayIvest.getCurrentPeriod());
				
				// 投资用户资金账户处理，余额增加回款资金
				//用户总收益
				userFund.setAll_revenue(revenue);
				logger.info("======================投资人回款回调--投资利息管理费：=================="
						+ fee.setScale(2, BigDecimal.ROUND_DOWN));
				revenue = returnRepayIvest.getAmount().subtract(fee.setScale(2, BigDecimal.ROUND_DOWN));
				//用户可用余额
				userFund.setAvailableAmount(revenue);
				if (pre == null) {
					// 投资人账户待收金额-本期回款金额-本期回款利息
					userFund.setDueInAmount(new BigDecimal(-1)
							.multiply(returnRepayIvest.getAmountprincipal().add(returnRepayIvest.getAmountinterest())));
				}
				logger.info("投资人账户待收金额-本期回款金额-本期回款利息=======" + userFund.getDueInAmount());
				userFund.setTimelastupdated(new Date());
				int num = userFundService.addAmountByUser(userFund);
				
				logger.info("更新投资人账户资金完成！userid=" + returnRepayIvest.getUserid());
				if (returnRepayIvest.getCurrentPeriod() == lastPriod) {
					Invest invest = new Invest();
					invest.setId(returnRepayIvest.getId());
					invest.setStatus(InvestStatus.CLEARED);
					investService.modifyInvest(invest);
					logger.info("最后一期还款完成修改投资状态CLEARED");
					removeZsessionObject(order_id + "lastPriod");
				}
				removeZsessionObject(order_id);
				removeZsessionObject(returnRepayIvest.getRepayId() + "flag");
			}
			return umpaySignature.callBackSignature();
		} catch (Exception e) {
			logger.info("callBackRepayTop投资用户资金账户处理，余额增加回款资金:" + e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public RepaymentInfo getRepaymentInfo(RepaymentInfo repaymentInfo) {
		LoanRepayment loanRepayment = new LoanRepayment();
		loanRepayment.setId(repaymentInfo.getId());
		loanRepayment = repaymentService.getLoanRepayment(loanRepayment);
		if (loanRepayment != null) {
			repaymentInfo.setAmountinterest(loanRepayment.getAmountinterest());
			repaymentInfo.setAmountprincipal(loanRepayment.getAmountprincipal());
		}
		return repaymentInfo;
	}

	/*
	 * 投资用户利息管理费扣除后，第三方回调处理 (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.RepaymentBiz#callBackRepayInvestFee(com.zhongyang
	 * .java.vo.CallBackRepay)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String callBackRepayInvestFee(CallBackRepay callBackRepay) {
		try {
			String order_id = callBackRepay.getOrder_id();
			ReturnRepayIvest returnRepayIvest = (ReturnRepayIvest) getZsessionObject(order_id);
			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("order_id", order_id);
			returnMap.put("ret_code", callBackRepay.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(returnMap);

			UserFund userFund = new UserFund();
			userFund.setUserid(returnRepayIvest.getUserid());
			userFund = userFundService.byUserID(userFund);
			if (!"0000".equals(callBackRepay.getRet_code())) {
				logger.info("投资人回款利息管理费失败orderid=" + order_id);
				UmpTenderTransferRecord uttfr = umpTenderTransferRecordService.getUmpTenderTransferRecord(order_id);
				uttfr.setRetcode(callBackRepay.getRet_code());
				uttfr.setRetmsg(callBackRepay.getRet_msg());
				uttfr.setTimelastupdated(new Date());
				uttfr.setStatus(FundRecordStatus.FAILED);
				umpTenderTransferRecordService.modifyUmpTenderTransferRecord(uttfr);

				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order_id);
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecord.setTimerecorded(new Date());
				fundRecordService.updateFundRecordByOrderId(fundRecord);

				ClientFundRecord clientFundRecord = new ClientFundRecord();
				clientFundRecord.setOrderid(order_id);
				clientFundRecord.setStatus(FundRecordStatus.FAILED);
				clientFundRecord.setTimerecorded(new Date());
				clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
				return umpaySignature.callBackSignature();
			}
			logger.info("投资人回款扣除利息管理费联动回调orderid=" + callBackRepay.getOrder_id());
			FundRecord fr = this.getFundRecord(order_id);
			if (FundRecordStatus.SUCCESSFUL.equals(fr.getStatus())) {
				return null;
			}
			UmpTenderTransferRecord uttfr = umpTenderTransferRecordService.getUmpTenderTransferRecord(order_id);
			uttfr.setRetcode(callBackRepay.getRet_code());
			uttfr.setRetmsg(callBackRepay.getRet_msg());
			uttfr.setTimelastupdated(new Date());
			if ("0000".equals(callBackRepay.getRet_code())) {
				uttfr.setStatus(FundRecordStatus.SUCCESSFUL);
			}
			umpTenderTransferRecordService.modifyUmpTenderTransferRecord(uttfr);
			if ("0000".equals(callBackRepay.getRet_code()) && (returnRepayIvest != null) && fr != null
					&& !"SUCCESSFUL".equals(fr.getStatus().toString())) {
				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order_id);
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				fundRecord.setTimerecorded(new Date());
				fundRecordService.updateFundRecordByOrderId(fundRecord);

				ClientFundRecord clientFundRecord = new ClientFundRecord();
				clientFundRecord.setOrderid(order_id);
				clientFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				clientFundRecord.setTimerecorded(new Date());
				clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
				logger.info("投资用户利息管理费扣除后，第三方回调处理更新平台流水状态");
				removeZsessionObject(order_id);
			}
			return umpaySignature.callBackSignature();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("投资用户利息管理费扣除异常" + e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public Message repayByPlatform(String id) {
		// TODO Auto-generated method stub
		Message message = new Message();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try {
			RepaymentByID repaymentByID;
			if (id != null && !"".equals(id)) {
				repaymentByID = new RepaymentByID();
				repaymentByID.setId(id);
				repaymentByID = repaymentService.repaymentById(repaymentByID);

				if (repaymentByID == null) {
					message.setCode(3003);
					message.setMessage("还款不存在");
					logger.info("还款不存在");
					return message;
				}
				if (getZsessionObject(repaymentByID.getLoan_id() + "repay") != null) {
					logger.info("重复还款" + repaymentByID.getLoan_id());
					return null;
				}
				setZsession("true", repaymentByID.getLoan_id() + "repay");
				// 垫付修改借款人账户待还金额
				UserFund userFund = new UserFund();
				userFund.setUserid(repaymentByID.getUserId());
				userFund = userFundService.byUserID(userFund);
				userFund.setTimelastupdated(new Date());
				userFund.setDueOutAmount(userFund.getDueOutAmount()
						.subtract(repaymentByID.getAmountPrincipal().add(repaymentByID.getAmountInterest())));
				userFundService.reduceAmountByUser(userFund);
				logger.info("垫付还款==修改借款人账户待还金额userid=" + repaymentByID.getUserId() + "标的ID="
						+ repaymentByID.getLoan_id() + "期数=" + repaymentByID.getCurrentPeriod());
				BigDecimal amount = repaymentByID.getMoney();

				if (amount != null && !"0.00".equals(amount.toString())) {
					ApplicationBean appBean = new ApplicationBean();
					String uuid = appBean.orderId();
					Map<String, String> map = new HashMap<String, String>();
					SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
					String mer_date = sdf.format(new Date());
					Map<String, Object> sysMap = SystemPro.getProperties();

					// 垫付增加平台资金记录
					User clientUser = userService.getUserByMobile(cipher.encrypt((String) sysMap.get("ZYCFMOBILE")));
					repaymentByID.setUserId(clientUser.getId());// 平台垫付修改
					ClientFundRecord clientFundRecord = new ClientFundRecord();
					clientFundRecord.setAccount((String) sysMap.get("ZYCF_UMP_ACCOUNT"));
					String cfrId = UUID.randomUUID().toString().toUpperCase();
					clientFundRecord.setId(cfrId);
					clientFundRecord.setAmount(repaymentByID.getMoney());
					clientFundRecord.setDescription("代偿还款");
					clientFundRecord.setOperation(FundRecordOperation.OUT);
					clientFundRecord.setOrderid(uuid);
					clientFundRecord.setStatus(FundRecordStatus.PROCESSING);
					clientFundRecord.setTimerecorded(new Date());
					clientFundRecord.setType(FundRecordType.DISBURSE);
					clientFundRecord.setUserid(clientUser.getId());
					clientFundRecordService.addClientFundRecord(clientFundRecord);
					logger.info("平台垫付增加平台资金流水 ====垫付金额=" + repaymentByID.getMoney());
					setZsession(repaymentByID, uuid);

					ProjectTransfer projectTransfer = new ProjectTransfer();
					projectTransfer.setOrder_id(uuid);
					projectTransfer.setMer_date(mer_date);
					projectTransfer.setProject_id(this.getProjectId(repaymentByID.getLoan_id()));
					// projectTransfer.setProject_id(repaymentByID.getLoan_id().replace("-",
					// ""));
					projectTransfer.setServ_type("04");
					projectTransfer.setTrans_action("01");
					projectTransfer.setPartic_type("03");
					projectTransfer.setPartic_acc_type("02");
					projectTransfer.setPartic_user_id((String) sysMap.get("ZYCF_UMP_ACCOUNT"));

					BigDecimal amounts = repaymentByID.getMoney().multiply(new BigDecimal(100));
					projectTransfer.setAmount(amounts.longValue());

					projectTransfer.setNotify_url(REPAYBYPLATFORM);
					// 垫付还款标的入账记录
					UmpTenderTransferRecord uttfr = new UmpTenderTransferRecord();
					uttfr.setAmount(repaymentByID.getMoney());
					uttfr.setLoanid(repaymentByID.getLoan_id());
					uttfr.setOrderid(uuid);
					uttfr.setStatus(FundRecordStatus.PROCESSING);
					uttfr.setTimecreated(new Date());
					uttfr.setUserid(repaymentByID.getUserId());
					uttfr.setUmpaccountid(repaymentByID.getAccountId());
					uttfr.setTenderaction(FundRecordOperation.IN);
					uttfr.setTransfertype(FundRecordType.DISBURSE);
					umpTenderTransferRecordService.addUmpTenderTransferRecord(uttfr);

					// 调用联动优势标的免密转账接口，将资金从平台账户转入标的账户。
					UmpaySignature umpaySignature = new UmpaySignature("project_transfer",
							getProjectFeeTransfer(projectTransfer));
					Map response = umpaySignature.getSignature();
					logger.info("联动优势还款响应：" + response);

					String ret_code = "";
					if (response.containsKey("ret_code"))
						ret_code = (String) response.get("ret_code");
					if (!"0000".equals(ret_code)) {
						ClientFundRecord cfr = new ClientFundRecord();
						cfr.setOrderid(uuid);
						cfr.setStatus(FundRecordStatus.FAILED);
						clientFundRecordService.updateClentFundByOrderId(cfr);

						UmpTenderTransferRecord record = new UmpTenderTransferRecord();
						record.setOrderid(uuid);
						record.setStatus(FundRecordStatus.FAILED);
						umpTenderTransferRecordService.modifyUmpTenderTransferRecord(record);

						removeZsessionObject(repaymentByID.getLoan_id() + "repay");
					}
				}

			}
		} catch (ReqDataException e) {
			message.setCode(3003);
			message.setMessage("调用联动优势参数错误：" + e.getMessage());
			return message;

		} catch (RetDataException e) {
			message.setCode(3004);
			message.setMessage("联动优势返回错误：" + e.getMessage());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		message.setCode(3005);
		message.setMessage("其他未知错误");
		return message;
	}

	@Override
	public List<ReturnRepayment> queryByLoanId(LoanRepayment loanRepayment) {
		try {
			List<ReturnRepayment> returnRepayment = new ArrayList<ReturnRepayment>();
			if (loanRepayment.getLoanId() == null) {
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未接收到标的ID");
			}
			List<LoanRepayment> queryRepayMent = repaymentService.queryByLoanId(loanRepayment);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (LoanRepayment repayMent : queryRepayMent) {
				ReturnRepayment repay = new ReturnRepayment();
				repay.setCurrentperiod(repayMent.getCurrentperiod());
				repay.setDuedate(sdf.format(repayMent.getDuedate()));
				repay.setAmount(BigDecimalAlgorithm.add(repayMent.getAmountinterest(), repayMent.getAmountprincipal()));
				if (LoanRepayMent.UNDUE.equals(repayMent.getStatus())) {
					repay.setStatus("未到期");
				}
				if (LoanRepayMent.OVERDUE.equals(repayMent.getStatus())) {
					repay.setStatus("逾期");
				}
				if (LoanRepayMent.BREACH.equals(repayMent.getStatus())) {
					repay.setStatus("违约");
				}
				if (LoanRepayMent.REPAYED.equals(repayMent.getStatus())) {
					repay.setStatus("已还请");
				}
				if (LoanRepayMent.PREPAY.equals(repayMent.getStatus())) {
					repay.setStatus("提前还款");
				}
				returnRepayment.add(repay);
			}
			return returnRepayment;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	// 平台代偿第三方回调接口
	@Override
	public String repayByPlatformCallBack(CallBackRepay callBackRepay) {
		try {
			String order_id = callBackRepay.getOrder_id();
			RepaymentByID repaymentByID = (RepaymentByID) getZsessionObject(order_id);
			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("order_id", order_id);
			returnMap.put("ret_code", callBackRepay.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(returnMap);

			if (!"0000".equals(callBackRepay.getRet_code())) {
				logger.info("平台垫付回调错误orderid=" + order_id);
				UmpTenderTransferRecord uttfr = umpTenderTransferRecordService.getUmpTenderTransferRecord(order_id);
				uttfr.setRetcode(callBackRepay.getRet_code());
				uttfr.setRetmsg(callBackRepay.getRet_msg());
				uttfr.setTimelastupdated(new Date());
				uttfr.setStatus(FundRecordStatus.FAILED);
				umpTenderTransferRecordService.modifyUmpTenderTransferRecord(uttfr);

				FundRecord fundRecord = new FundRecord();
				fundRecord.setOrderid(order_id);
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecord.setTimerecorded(new Date());
				fundRecordService.updateFundRecordByOrderId(fundRecord);

				ClientFundRecord clientFundRecord = new ClientFundRecord();
				clientFundRecord.setOrderid(order_id);
				clientFundRecord.setStatus(FundRecordStatus.FAILED);
				clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
				return umpaySignature.callBackSignature();
			}
			logger.info("平台垫付还款联动回调orderid=" + callBackRepay.getOrder_id());
			ClientFundRecord cfr = new ClientFundRecord();
			cfr.setOrderid(order_id);
			cfr = clientFundRecordService.getClientFundRecordByOrderId(cfr);
			if (FundRecordStatus.SUCCESSFUL.equals(cfr.getStatus())) {
				return null;
			}
			UmpTenderTransferRecord uttfr = umpTenderTransferRecordService.getUmpTenderTransferRecord(order_id);
			uttfr.setRetcode(callBackRepay.getRet_code());
			uttfr.setRetmsg(callBackRepay.getRet_msg());
			uttfr.setTimelastupdated(new Date());
			if ("0000".equals(callBackRepay.getRet_code())) {
				uttfr.setStatus(FundRecordStatus.SUCCESSFUL);
			}
			umpTenderTransferRecordService.modifyUmpTenderTransferRecord(uttfr);
			if (repaymentByID != null && "0000".equals(callBackRepay.getRet_code()) && cfr != null
					&& !"SUCCESSFUL".equals(cfr.getStatus().toString())) {
				// BigDecimal repayAmount = repaymentByID.getAvailable_amount();
				if (!repaymentByID.getLoanInterestFee().equals(new BigDecimal("0.00"))) {
					this.borrowerServiceFee(repaymentByID);// 存在借款人利息管理服务费
				}
				// update tender account.
				UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(repaymentByID.getLoan_id());
				umpTender.setAmount(umpTender.getAmount().add(repaymentByID.getMoney()));
				int num = 0;
				num = umpTenderService.updateAmound(umpTender);
				logger.info("平台垫付转账到标的账户：amount=" + umpTender.getAmount());
				// 修改标的状态为CLIEARED
				Loan loan = loanService.queryLoanById(repaymentByID.getLoan_id());
				if (loan != null && (loan.getMonths() == repaymentByID.getCurrentPeriod())) {
					loanService.updateLoanStatus(repaymentByID.getLoan_id(), LoanStatus.SETTLED.toString(),
							LoanStatus.CLEARED.toString());
					logger.info("最后一期借款人还款成功修改标的状态为CLEARED");
				}
				// 还款计划处理

				Map<String, Object> sysMap = SystemPro.getProperties();

				LoanRepayment loanRepayment = new LoanRepayment();
				loanRepayment.setStatus(LoanRepayMent.REPAYED);
				loanRepayment.setId(repaymentByID.getId());
				loanRepayment.setRepaydate(new Date());
				loanRepayment.setRepayamount(repaymentByID.getMoney());
				loanRepayment.setSourceId((String) sysMap.get("ZYCF_UMP_ACCOUNT"));
				loanRepayMentService.updateStatus(loanRepayment);

				// 平台资金流水
				ClientFundRecord clientFundRecord = new ClientFundRecord();
				clientFundRecord.setOrderid(order_id);
				clientFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				clientFundRecordService.updateClentFundByOrderId(clientFundRecord);

				RepayIvest repayIvest = new RepayIvest();
				repayIvest.setLoanID(repaymentByID.getLoan_id());
				repayIvest.setCurrentPeriod(repaymentByID.getCurrentPeriod());
				repayIvest.setSource((String) sysMap.get("ZYCF_UMP_ACCOUNT"));
				MyThread myThread = new MyThread(repayIvest);
				myThread.run();
				// 清空缓存
				removeZsessionObject(order_id);

			}
			return umpaySignature.callBackSignature();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		return null;
	}

	/**
	 * 提前还款
	 */

	@Transactional
	public Message repayAdvance(String id) {
		this.repayInAdvance(id);
		this.repay(id);
		return null;

	}

	@Override
	@Transactional
	public Message repayInAdvance(String id) {
		LoanRepayment loanRepayment = new LoanRepayment();
		UserFund userFund = new UserFund();
		Loan loan = new Loan();
		RepayIvest repayIvest = new RepayIvest();
		try {
			if (id != null && !"".equals(id)) {
				loanRepayment.setId(id);
				loanRepayment = repaymentService.getLoanRepayment(loanRepayment);
				loan = loanService.queryLoanById(loanRepayment.getLoanId());
			}
			LocalDate dueDate = new LocalDate(loanRepayment.getDuedate());
			LocalDate today = new LocalDate();
			if (loan != null && loanRepayment.getRepaydate() == null && !Method.BulletRepayment.equals(loan.getMethod())
					&& loan.getMonths() != 1) {
				int days = Days.daysBetween(today, dueDate).getDays();
				Map<String, Object> sysMap = SystemPro.getProperties();
				int compenDays = Integer.parseInt((String) sysMap.get("ZYCFCOMPENS_PERIOD"));
				BigDecimal interest = loanRepayment.getAmountinterest();
				if (days > compenDays && days < GlobalConstants.DAYSOFMONTH) {
					userFund.setUserid(loan.getLoanUserId());
					userFund = userFundService.byUserID(userFund);
					userFund.setDueOutAmount(userFund.getDueOutAmount()
							.subtract(loanRepayment.getAmountprincipal().add(loanRepayment.getAmountinterest())));
					userFund.setTimelastupdated(new Date());
					userFundService.reduceAmountByUser(userFund);
					repayIvest.setLoanID(loan.getId());
					repayIvest.setCurrentPeriod(loanRepayment.getCurrentperiod());
					repayIvest.setSource(loan.getLoanUserId());
					List<ReturnRepayIvest> list = repaymentService.repaymentInvestByLoanId(repayIvest);
					for (ReturnRepayIvest returnRepayIvest : list) {
						UserFund fund = new UserFund();
						fund.setUserid(returnRepayIvest.getUserid());
						fund = userFundService.byUserID(fund);
						fund.setDueInAmount(new BigDecimal(-1).multiply(
								returnRepayIvest.getAmountprincipal().add(returnRepayIvest.getAmountinterest())));
						fund.setTimelastupdated(new Date());
						int num = userFundService.addAmountByUser(fund);
						logger.info("提前还款修改投资人待还金额userID=" + fund.getUserid() + "修改后待还金额=" + fund.getDueInAmount());
					}
					logger.info("提前还款==修改借款人待还金额userid=" + loan.getLoanUserId() + "标的ID=" + loan.getId() + "期数="
							+ loanRepayment.getCurrentperiod());
					BigDecimal pre = (new BigDecimal(GlobalConstants.DAYSOFMONTH - days + compenDays)
							.divide(new BigDecimal(GlobalConstants.DAYSOFMONTH), 2, BigDecimal.ROUND_UP));
					setZsession(pre, loan.getId() + "pre");
					loanRepayment.setAmountinterest(interest.multiply(pre).setScale(2, BigDecimal.ROUND_UP));
					loanRepayment.setRepaydate(new Date()); // 只更新一次防止多次点击提前还款造成数据不准确
					loanRepayMentService.updateStatus(loanRepayment);
					investRepayMentService.updateInvestAmount(loanRepayment.getCurrentperiod(),
							loanRepayment.getLoanId(), pre);
					logger.info("提前还款：原来利息=" + interest + "+++提前利息=" + loanRepayment.getAmountinterest() + "+++利息占比="
							+ pre);
					// this.repay(id);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return null;
	}

	public static void main(String[] args) {
		LocalDate dueDate = new LocalDate("2016-07-04");
		LocalDate today = new LocalDate();
		int days = Days.daysBetween(today, dueDate).getDays();

		System.out.println(days);
	}

	// 扣除还款（借款人）利息服务费如果有
	@Transactional
	private void borrowerServiceFee(RepaymentByID repaymentByID) {
		try {
			BigDecimal amount = repaymentByID.getMoney()
					.subtract(repaymentByID.getAmountPrincipal().add(repaymentByID.getAmountInterest()));
			if (amount != null && !"0.00".equals(amount.toString())) {
				ApplicationBean appBean = new ApplicationBean();
				String orderId = appBean.orderId();
				Map<String, String> map = new HashMap<String, String>();
				SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
				String mer_date = sdf.format(new Date());
				Map<String, Object> sysMap = SystemPro.getProperties();
				ProjectTransfer projectTransfer = new ProjectTransfer();

				// 还款（借款人）利息管理费增加平台资金记录
				ClientFundRecord clientFundRecord = new ClientFundRecord();
				clientFundRecord.setAccount((String) sysMap.get("ZYCF_UMP_ACCOUNT"));
				String cfrId = UUID.randomUUID().toString().toUpperCase();
				clientFundRecord.setId(cfrId);
				clientFundRecord.setAmount(repaymentByID.getMoney()
						.subtract(repaymentByID.getAmountPrincipal().add(repaymentByID.getAmountInterest())));
				clientFundRecord.setDescription("还款（借款人）利息管理费");
				clientFundRecord.setRecordpriv(repaymentByID.getTitle() + "#loanid" + repaymentByID.getLoan_id()
						+ "#period=" + repaymentByID.getCurrentPeriod());
				clientFundRecord.setOperation(FundRecordOperation.IN);
				clientFundRecord.setOrderid(orderId);
				clientFundRecord.setStatus(FundRecordStatus.PROCESSING);
				clientFundRecord.setTimerecorded(new Date());
				clientFundRecord.setType(FundRecordType.FEE_LOAN_INTEREST);
				clientFundRecord.setUserid(repaymentByID.getUserId());
				clientFundRecordService.addClientFundRecord(clientFundRecord);
				logger.info("发送请求扣除借款人利息管理费orderid=" + orderId + "金额：" + clientFundRecord.getAmount());
				setZsession(repaymentByID, orderId);

				projectTransfer.setOrder_id(orderId);
				projectTransfer.setMer_date(mer_date);
				projectTransfer.setProject_id(this.getProjectId(repaymentByID.getLoan_id()));
				// projectTransfer.setProject_id(repaymentByID.getLoan_id().replace("-",
				// ""));
				projectTransfer.setServ_type("52");
				projectTransfer.setTrans_action("02");
				projectTransfer.setPartic_type("03");
				projectTransfer.setPartic_acc_type("02");
				projectTransfer.setPartic_user_id((String) sysMap.get("ZYCF_UMP_ACCOUNT"));
				// projectTransfer.setPartic_account_id(null);
				projectTransfer.setAmount(amount.multiply(new BigDecimal(100)).longValue());
				projectTransfer.setNotify_url(BORROWERFEE);

				UmpaySignature umpaySignatureFee = new UmpaySignature("project_transfer",
						getProjectFeeTransfer(projectTransfer));

				// umpaySignatureFee.getSignatureStrng();
				Map response = umpaySignatureFee.getSignature();
				logger.info("联动优势还款响应：" + response);
				// if ("0000".equals(response.get("ret_code"))) {
				// }
			}
		} catch (Exception e) {
			logger.info("borrowerServiceFee" + e.fillInStackTrace());
			e.printStackTrace();
		}
	}

	// 借款人利息管理费回调
	@Override
	@Transactional
	public String callBackBorrowerFee(CallBackRepay callBackRepay) {

		RepaymentByID repaymentByID = (RepaymentByID) getZsessionObject(callBackRepay.getOrder_id());
		try {
			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("order_id", callBackRepay.getOrder_id());
			returnMap.put("ret_code", callBackRepay.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(returnMap);
			umpaySignature.callBackSignature();

			ClientFundRecord cfr = new ClientFundRecord();
			cfr.setOrderid(callBackRepay.getOrder_id());
			cfr = clientFundRecordService.getClentFundByOrderId(cfr);
			if (!"0000".equals(callBackRepay.getRet_code())) {
				cfr.setStatus(FundRecordStatus.FAILED);
				clientFundRecordService.updateClentFundByOrderId(cfr);
				return null;
			}
			if (FundRecordStatus.SUCCESSFUL.equals(cfr.getStatus())) {
				return null;
			}
			if ("0000".equals(callBackRepay.getRet_code()) && repaymentByID != null
					&& !"SUCCESSFUL".equals(cfr.getStatus().toString())) {
				cfr.setStatus(FundRecordStatus.SUCCESSFUL);
				int num = clientFundRecordService.updateClentFundByOrderId(cfr);
				logger.info("借款人利息管理费回调修改状态成功orderid=" + cfr.getOrderid());
				UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(repaymentByID.getLoan_id());
				umpTender.setAmount(umpTender.getAmount().subtract(cfr.getAmount()));
				int n = 0;
				n = umpTenderService.updateAmound(umpTender);
				logger.info("扣除借款人利息管理费修改标的账户金额：amount=" + umpTender.getAmount());
				// 清空缓存
				removeZsessionObject(callBackRepay.getOrder_id());

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("借款人利息管理费回调修改状态异常：" + e.fillInStackTrace());
		}
		return null;

	}

	public String getProjectId(String loanid) {
		try {
			UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(loanid);
			if (umpTender.getUmptenderid() != null && !"".equals(umpTender.getUmptenderid())) {
				return umpTender.getUmptenderid();
			} else {
				return loanid.replace("-", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private FundRecord getFundRecord(String order_id) {
		try {
			FundRecord fr = new FundRecord();
			fr.setOrderid(order_id);
			fr = fundRecordService.queryFundRecord(fr);
			return fr;
		} catch (Exception e) {
			logger.info("查询资金记录流水信息异常" + e.fillInStackTrace());
		}
		return null;
	}

}
