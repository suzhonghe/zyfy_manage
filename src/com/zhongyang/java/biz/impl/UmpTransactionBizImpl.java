package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.UmpTransactionBiz;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.InvestRepayment;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.LoanRepayment;
import com.zhongyang.java.pojo.SettleOrder;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.ClientFundRecordService;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.InvestRepayMentService;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.service.LoanRepayMentService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.SettleOrderService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpTenderService;
import com.zhongyang.java.service.UmpTenderTransferRecordService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.service.impl.LoanServiceImpl;
import com.zhongyang.java.sys.uitl.FormatUtils;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.ShortMessage;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.ZSession;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.uitl.ArithmeticInterest;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.uitl.RepaymentCalendar;
import com.zhongyang.java.system.umpay.UmpLoan;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.LoanModify;
import com.zhongyang.java.vo.UmpTransferSearch;
import com.zhongyang.java.vo.fund.BusinessWithdrawVo;
import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.fund.OrderTime;
import com.zhongyang.java.vo.fund.SettleFundRecordVo;
import com.zhongyang.java.vo.loan.InvestStatus;
import com.zhongyang.java.vo.loan.LoanRepayMent;
import com.zhongyang.java.vo.loan.LoanStatus;

import net.sf.json.util.NewBeanInstanceStrategy;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月1日 下午3:58:35 类说明标的结算实现类
 */
@Service
public class UmpTransactionBizImpl extends UtilBiz implements UmpTransactionBiz {

	private static Logger logger = Logger.getLogger(UmpTransactionBizImpl.class);

	@Autowired
	ClientFundRecordService clientFundRecordService;

	@Autowired
	LoanServiceImpl loanServiceImpl;

	@Autowired
	FundRecordService fundRecordService;

	@Autowired
	UmpAccountService umpAccountService;

	@Autowired
	SettleOrderService settleOrderService;

	@Autowired
	LoanService loanService;

	@Autowired
	UserFundService userFundService;

	@Autowired
	InvestService investService;

	@Autowired
	LoanRepayMentService loanRepayMentService;

	@Autowired
	InvestRepayMentService investRepayMentService;

	@Autowired
	UmpTenderService umpTenderService;

	@Autowired
	UmpTenderTransferRecordService umpSTRService;

	// 标的结算----20160317--------转账给借款人
	public Message settleLoanAccount(String loanId) {
		Message ms = new Message();
		Map<String, String> result = null;
		Loan loan = this.queryLoanById(loanId);// 查询标的信息
		if (loan != null) {
			BigDecimal allFeeAmount = this.getAllFee(loan);// 管理费算法
			BigDecimal amount = loan.getAmount().subtract(allFeeAmount);// 实际放款金额=标的金额-平台收取的费用
			ApplicationBean appBean = new ApplicationBean();
			String loanOrderId = appBean.orderId();// 放款orderId
			
			UmpTender umpTender = this.getUmpTender(loan.getId());// 更新标的账户Tender--查询
			UmpAccount umpAccount = this.getUmpAccountByUserId(loan.getLoanUserId());// 查询用户ump账户
			setZsession(umpTender, loan.getId().toString()+"te");//标的账户信息缓存
			setZsession(umpAccount, loan.getId().toString());//标的umpAccount账户信息缓存
			FundRecord fd = new FundRecord();
			fd.setType(FundRecordType.LOAN);
			int k = this.addUmpTenderTransferRecord(loanOrderId,loan,fd,amount,umpTender,umpAccount);//添加标的账户出账到借款人放款记录--标的出账
			result = settleLoantoUser(loan, amount, loanOrderId, umpAccount);// 放款转账(标的账户to借款人账户)
				if ("0000".endsWith(result.get("ret_code"))) {
					ms.setCode(0);
					ms.setMessage("结算成功");
				} else {
					ms.setCode(1);
					ms.setMessage("结算失败");
				}
		}
		return ms;
	}


    @Transactional
	private int addUmpTenderTransferRecord(String loanOrderId, Loan loan, FundRecord fd,BigDecimal amount,UmpTender umpTender,UmpAccount umpAccount) {
			UmpTenderTransferRecord  umpTenderTransferRecord= new UmpTenderTransferRecord();
			int i = 0;
			try {
				OrderTime or = new OrderTime();
				umpTenderTransferRecord.setAmount(amount);
				umpTenderTransferRecord.setLoanid(loan.getId());
				umpTenderTransferRecord.setOrderid(loanOrderId);
				umpTenderTransferRecord.setTransfertype(fd.getType());
				umpTenderTransferRecord.setTenderaction(FundRecordOperation.OUT);
				umpTenderTransferRecord.setStatus(FundRecordStatus.PROCESSING);
				umpTenderTransferRecord.setTimecreated(or.getLastUpdateTime());
				umpTenderTransferRecord.setTimelastupdated(or.getLastUpdateTime());
				umpTenderTransferRecord.setUmpaccountid(umpAccount.getAccountId());
				umpTenderTransferRecord.setUmpaccountname(umpAccount.getAccountName());
				umpTenderTransferRecord.setUmptenderaccountid(umpTender.getUmptenderaccountid());
				umpTenderTransferRecord.setUserid(loan.getLoanUserId());//借款人ID
				i = umpSTRService.addUmpTenderTransferRecord(umpTenderTransferRecord);
			} catch (Exception e) {
				System.out.println("添加放款UmpTenderTransferRecord失败"+loanOrderId);
				e.printStackTrace();
				logger.info(e,e.fillInStackTrace());
			}
			return i;
		}

	// 查询标的信息
	public Loan queryLoanById(String loanId) {
		try {
			return loanServiceImpl.queryLoanById(loanId);
		} catch (Exception e) {
			logger.info("查询标的信息失败 loanId :" + loanId);
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询标的信息失败");
		}
	}

	// 放款操作之前添加给借款人的实际放款记录
	@Transactional
	private int addUserFundRecordLoan(Loan loan,UmpTenderTransferRecord umpTRecord) {
		int i = 0;
		try {
			FundRecord fundRecord = new FundRecord();
			fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			fundRecord.setAmount(loan.getAmount());// 放款总额记录的是标的的总额，最终在调用第三方时放款的额度=标的额度-管理费
			fundRecord.setOrderid(umpTRecord.getOrderid());
			fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
			fundRecord.setTimerecorded(new Date());
			fundRecord.setType(FundRecordType.LOAN);
			fundRecord.setUserId(loan.getLoanUserId());
			fundRecord.setOperation(FundRecordOperation.IN);
			fundRecord.setDescription("借款放款");
			i = fundRecordService.addFundRecord(fundRecord);
		} catch (Exception e) {
			logger.info("放款总额记录的是标的的总额添加失败：orderId=" + umpTRecord.getOrderid());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加用户资金记录失败");
		}
		return i;
	}

	private UmpAccount getUmpAccountByUserId(String loanUserId) {
		UmpAccount umpAccount = new UmpAccount();
		umpAccount.setUserId(loanUserId);
		try {
			return umpAccountService.getUmpAccountByUserId(umpAccount);
		} catch (Exception e) {
			logger.info("查询借款人ump账户失败loanUserId: " + loanUserId);
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询借款人ump账户失败");
		}

	}
    @Transactional
	private int addSettleOrder(String loanOrderId, String feeOrderId, String loanId, String accountName) {
		int i = 0;
		try {
			SettleOrder so = new SettleOrder();
			so.setId(UUID.randomUUID().toString().toUpperCase());
			so.setSettleId(loanOrderId);
			so.setOrderIdFee(feeOrderId);
			so.setLoanId(loanId);
			so.setUmpAccountName(accountName);
			i = settleOrderService.addSettleOrder(so);
		} catch (Exception e) {
			logger.info("添加结算时实际打款和管理费关联失败loanId：" + loanId);
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加orderid失败");
		}
		return i;

	}

	// 放款转账(标的账户to借款人账户)
	private Map<String, String> settleLoantoUser(Loan loan, BigDecimal amount, String orderId, UmpAccount umpAccount) {
		Map<String, String> withdrawResults = null;
		try {
			UmpTender umpTender = (UmpTender) getZsessionObject(loan.getId().toString()+"te");
			
			OrderTime ot = new OrderTime();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			BigDecimal dj = new BigDecimal(100.00);
			BigDecimal transAmount = amount.multiply(dj).setScale(0, BigDecimal.ROUND_UP);
			String projectId = loan.getId().replace("-", "");
			Map map = new HashMap<String, String>();
			Map<String, Object> sysMap = SystemPro.getProperties();
			String zycfurlIp = (String) sysMap.get("ZYCFMANAGER_IP");
			map.put("notify_url", zycfurlIp + "/umpCallBackSettleLoan");
			map.put("order_id", orderId);
			map.put("mer_date", ot.getTmeo());
            if(null== umpTender.getUmptenderid()){//如果Umptenderid是null说明是旧数据传的是纯数字的32为id，如果不为null说是新标的则传生成的uuid
            	map.put("project_id", projectId);
			}else{
				map.put("project_id", umpTender.getUmptenderid());
			}
			map.put("serv_type", "53");
			map.put("trans_action", "02");
			map.put("partic_type", "02");
			map.put("partic_acc_type", "01");
			map.put("partic_user_id", umpAccount.getAccountName());
			map.put("amount", transAmount);
			UmpaySignature umpaySignature = new UmpaySignature("project_transfer", map);
			withdrawResults = umpaySignature.getSignature();
		} catch (Exception e) {
			logger.info("调用第三方接口失败转账给借款人失败orderId ：" + orderId);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return withdrawResults;
	}

	public String umpCallBackSettleLoan(SettleFundRecordVo settleFundRecordVo) {
		String result = null;
		try {
			UmpTenderTransferRecord umpTenderTransferRecord = this.getUmpTenderTransferRecord(settleFundRecordVo.getOrder_id());// 查询UmpTenderTransferRecord
			String loanId =umpTenderTransferRecord.getLoanid();
			Loan loan = queryLoanById(loanId);// 标的信息
			if(loan.getAddRate() !=0){
				loan.setRate(loan.getRate().add(new BigDecimal(loan.getAddRate())));
			}
		    UmpTender umpTender = (UmpTender) getZsessionObject(loan.getId().toString()+"te");
			System.out.println("umpTender--------"+umpTender.getLoanid());
			BigDecimal allFeeAmount = this.getAllFee(loan);// 管理费算法
			if("FINISHED".endsWith(loan.getStatus().toString())){
				if ("0000".equals(settleFundRecordVo.getRet_code())) {
					String projectId = loanId.replace("-", "");
					result = firstCallBackSignature(settleFundRecordVo);// 转账给借款人的第一次回调
					logger.info("projectId--------"+projectId);
					System.out.println("projectId--------"+projectId);
					LoanModify md = new LoanModify();
					 if(null== umpTender.getUmptenderid()){//如果Umptenderid是null说明是旧数据传的是纯数字的32为id，如果不为null说是新标的则传生成的uuid
			            	md.setProject_id(projectId);
						}else{
							md.setProject_id(umpTender.getUmptenderid());
						}
					md.setProject_id(projectId);
					md.setProject_state("2");
					md.setChange_type("01");
					this.umpModifyLoanStatusTOUMP(md);// 更新标的在第三方的状态
					this.updateLoanStatusByLoanId(loanId);// 更新标的状态为:已经算

					this.addUserFundRecordLoan(loan, umpTenderTransferRecord);//为借款人添加放款入账记录
					
					int i =0;
					if(allFeeAmount.equals(new BigDecimal("0.00"))){
						//修改UmpTenderTransferRecord记录状态
						this.updateUmpTenderTransferRecord(settleFundRecordVo.getOrder_id(),settleFundRecordVo);//更新UmpSettleTransferRecord---SUCCESSFUL
						//修改UpdateUmpTender更新标的账户额度
						this.updateUmpTender(umpTenderTransferRecord);
						//更新投资人账户和投资记录状态
						int r = this.investRepayment(loan);// 个人投资回款计划
						this.loanRepayment(loan);// 生成标的还款计划
						this.updateUserFundAndRecordTZR(umpTenderTransferRecord,loan);// 更新投资人投资记录和账户
						this.updateUserFundAndRecordJKR(umpTenderTransferRecord,loan,allFeeAmount);//更新账户
						this.sendInfoToUser(loan);//发送短信给投标用户，提示标的开始起息
						
					}else{
						ApplicationBean appBean = new ApplicationBean();
						String orderIdFee = appBean.orderId();
						FundRecord fd = new FundRecord();
						fd.setType(FundRecordType.FEE_LOAN_MANAGE);
						UmpAccount umpAccount = (UmpAccount) getZsessionObject(loan.getId().toString());
						// 清空缓存
						removeZsessionObject(loan.getId().toString());
						this.updateUmpTenderTransferRecord(settleFundRecordVo.getOrder_id(),settleFundRecordVo);//更新UmpSettleTransferRecord---SUCCESSFUL
						this.addUmpTenderTransferRecord(orderIdFee,loan,fd,allFeeAmount,umpTender,umpAccount);//添加标的账户出账到平台管理费记录
						setZsession(orderIdFee, orderIdFee);
						i = this.addFundRecordFee(orderIdFee,loan,allFeeAmount);//借款人出账管理费资金记录JKR
						this.addClientFundRecord(loan, orderIdFee,allFeeAmount);// 添加平台管理费进账记录
						this.updateUserFundAndRecordJKR(umpTenderTransferRecord,loan,allFeeAmount);//更新账户
						int r = this.investRepayment(loan);// 投资人投资回款计划
						this.loanRepayment(loan);// 生成标的还款计划
						this.updateUserFundAndRecordTZR(umpTenderTransferRecord,loan);// 更新投资人投资记录和账户
						this.updateUmpTender(umpTenderTransferRecord);//更新标的账户余额
						if (i != 0) {
							SettleFundRecordVo res = transfersLoanFee(loan, orderIdFee,allFeeAmount);// 实际转管理费
							if ("0000".endsWith(res.getRet_code())) {
								String  orderIdF= (String) getZsessionObject(orderIdFee);
								removeZsessionObject(orderIdFee);
								this.updateUmpTenderTransferRecord(orderIdF,settleFundRecordVo);//更新UmpSettleTransferRecord---SUCCESSFUL
								settleFundRecordVo.setOrder_id(orderIdF);
								this.userFundRecord(settleFundRecordVo);// 更新借款人用户资金记录状态:成功---扣除管理费
								this.updateClentFund(settleFundRecordVo,allFeeAmount);//更新平台资金记录状态
								this.sendInfoToUser(loan);//发送短信给投标用户，提示标的开始起息
								umpTenderTransferRecord.setAmount(allFeeAmount);
								this.updateUmpTender(umpTenderTransferRecord);//更新标的账户余额
								removeZsessionObject(loan.getId().toString()+"te");
							} else {
								removeZsessionObject(orderIdFee);
								this.updateUmpTenderTransferRecord(orderIdFee,settleFundRecordVo);//更新UmpSettleTransferRecord---FAILED
								this.userFundRecord(settleFundRecordVo);//更新借款人人用户资金记录状态:失败---扣除管理费
								this.updateClentFund(settleFundRecordVo,allFeeAmount);//更新平台资金记录状态
								logger.info(""+settleFundRecordVo.getRet_msg()+"--扣除管理费-Order_id:"+settleFundRecordVo.getOrder_id());
							}
						}
					}
				}else{
					//更新借款人账户放款资金记录状态--失败
					//this.userFundRecord(settleFundRecordVo);// 更新借款人用户资金记录状态为：--FAILED
					//更新标的账户流水资金记录状态--失败
					this.updateUmpTenderTransferRecord(settleFundRecordVo.getOrder_id(),settleFundRecordVo);//更新UmpSettleTransferRecord---FAILED
					logger.info(""+settleFundRecordVo.getRet_msg()+"---Order_id:"+settleFundRecordVo.getOrder_id());
				}
			}
		} catch (Exception e) {
			logger.info("放款回调出错: " + settleFundRecordVo.getOrder_id());
			logger.info(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		return result;
	}
    @Transactional
	private void updateUmpTenderTransferRecord(String orderId,SettleFundRecordVo settleFundRecordVo) {
		try {
			UmpTenderTransferRecord uTRecord = new UmpTenderTransferRecord();
			uTRecord.setOrderid(orderId);
			if ("0000".endsWith(settleFundRecordVo.getRet_code())) {
				uTRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				uTRecord.setTimelastupdated(new Date());
				uTRecord.setRetcode(settleFundRecordVo.getRet_code());
				uTRecord.setRetmsg(settleFundRecordVo.getRet_msg());
				uTRecord.setTradeno(settleFundRecordVo.getTrade_no());
			} else {
				uTRecord.setRetcode(settleFundRecordVo.getRet_code());
				uTRecord.setRetmsg(settleFundRecordVo.getRet_msg());
				uTRecord.setTradeno(settleFundRecordVo.getTrade_no());
				uTRecord.setStatus(FundRecordStatus.FAILED);
				uTRecord.setTimelastupdated(new Date());
			}
			umpSTRService.modifyUmpTenderTransferRecord(uTRecord);
		} catch (Exception e) {
			logger.info("更新用户管理费状态失败orderId: " + settleFundRecordVo.getOrder_id());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "更新个人资状态失败");
		}
		
	}

	private void updateClentFund(SettleFundRecordVo settleFundRecordVo,BigDecimal allFeeAmount) {
		try {
			ClientFundRecord clientFundRecord = new ClientFundRecord();
			clientFundRecord.setOrderid(settleFundRecordVo.getOrder_id());
			if("0000".endsWith(settleFundRecordVo.getRet_code())){
				clientFundRecord.setAmount(allFeeAmount);
				clientFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
			}else{
				clientFundRecord.setAmount(allFeeAmount);
				clientFundRecord.setStatus(FundRecordStatus.FAILED);
			}
			clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
		} catch (Exception e) {
			logger.info("更新平台资金管理费失败orderId："+settleFundRecordVo.getOrder_id());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "更新平台资金管理费失败");
		}
		
	}

	private void sendInfoToUser(Loan loan) {
		List<InvestVo> invests = new ArrayList<InvestVo>();
		String codeMsg = null;
		try {
			
			Map map = new HashMap<String, String>();
			invests = investService.getInvestAndMobileByLoanId(loan.getId());
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Date now = new Date(); 
            SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
            String date = sd.format(now);
			StringBuffer sb = new StringBuffer();
			if(invests.size()>0){
			int i = 0;
               for(InvestVo invest:invests){
            	   BigDecimal investAmount = invest.getAmount();
            	   String mobile = invest.getUserMobile();
            	   String mobiles = cipher.decrypt(mobile);
            	   if(i != invests.size()-1){
            		   sb.append(mobiles+",");
            	   }else{
            		   sb.append(mobiles);
            	   }
            	  
               }
               map.put("info", "“"+loan.getTitle()+"”"+"项目已于"+date+"开始记息");
               map.put("mobiles", sb.toString());
			}
			codeMsg=ShortMessage.getShortMessage().getSendToUserMsg(map);
		} catch (Exception e) {
			logger.info("发送结算短信给用户失败loanId："+loan.getId()+"codeMsg :"+codeMsg);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		
		
	}
	private void updateLoanStatusByLoanId(String loanId) {
		Loan loan = new Loan();
		try {
			OrderTime orderTime = new OrderTime();
			loan.setId(loanId);
			loan.setTimeSettled(orderTime.getLastUpdateTime());
			loan.setStatus(LoanStatus.SETTLED);
			loanService.modifyLoan(loan);
		} catch (Exception e) {
			System.out.println("更新标的状态为：settled失败");
			logger.info(e,e.fillInStackTrace());
		}
	}

	private FundRecord findFundRecordByOrderId(String order_id) {
		try {
			FundRecord fundRecord = new FundRecord();
			fundRecord.setOrderid(order_id);
			return fundRecordService.findFundRecordByOrderId(fundRecord);
		} catch (Exception e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "根据orderId查询转账给借款人资金记录失败");
		}
	}

	private SettleOrder getSettleloanByorderId(String orderId) {
		try {
			SettleOrder settleOrder = new SettleOrder();
			settleOrder.setSettleId(orderId);
			return settleOrderService.getSettleloanByOrderId(settleOrder);
		} catch (Exception e) {
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "根据FeeID查询转账给借款人的orderID失败");
		}
	}

	// 更新个人用户资金记录状态---扣除管理费||实际转账
	private void userFundRecord(SettleFundRecordVo settleFundRecordVo) {
		try {
			FundRecord fundRecord = new FundRecord();
			fundRecord.setOrderid(settleFundRecordVo.getOrder_id());
			if ("0000".endsWith(settleFundRecordVo.getRet_code())) {
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
			} else {
				fundRecord.setStatus(FundRecordStatus.FAILED);
			}
			fundRecordService.updateFundRecordByOrderId(fundRecord);
		} catch (Exception e) {
			logger.info("更新用户管理费状态失败orderId: " + settleFundRecordVo.getOrder_id());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "更新个人资状态失败");
		}
	}

	// 扣除手续费回调响应；告诉第三方已经本次交易成功
	private String firstCallBackSignature(SettleFundRecordVo settleFundRecordVo) {
		String rechargeResults = null;
		try {
			Map map = new HashMap<String, String>();
			map.put("order_id", settleFundRecordVo.getOrder_id());
			map.put("mer_date", settleFundRecordVo.getMer_date());
			map.put("ret_code", settleFundRecordVo.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(settleFundRecordVo.getService(), map);
			rechargeResults = umpaySignature.callBackSignature();
		} catch (ReqDataException | RetDataException e) {
			logger.info("响应消息失败");
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return rechargeResults;

	}
	private void updateUserFundAndRecordTZR(UmpTenderTransferRecord umpTRecord,Loan loanInfo) {
		try {
			Invest invest = new Invest();
			invest.setLoanid(umpTRecord.getLoanid());
			invest.setStatus(InvestStatus.SETTLED);
			this.updateInvestByLoanId(invest);//更新投资人投标记录状态从审核中到成功:AUDITING--SETTLED
			this.updateTzrUserFund(loanInfo);//更新投资人冻结账户的金额
		} catch (Exception e) {
			logger.info("根据orderId更新借款人的账户总资金失败"+umpTRecord.getOrderid());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "根据orderId更新借款人的账户总资金失败" + Invest.class);
		}

	}
	private void updateUserFundAndRecordJKR(UmpTenderTransferRecord umpTRecord,Loan loan,BigDecimal allFeeAmount) {
		try {
			UserFund userFund = new UserFund();
			userFund.setUserid(umpTRecord.getUserid());
			UserFund userFundOld = userFundService.byUserID(userFund);
			BigDecimal rateSumAmount = this.loanRateSum(loan);//根据LoanId查询标的利息
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			OrderTime orderTime = new OrderTime();
			List<Invest> investcc = new ArrayList<>();
			Invest invest = new Invest();
			invest.setLoanid(loan.getId());
			investcc = investService.getInvestByLoanId(invest);
			if(investcc.size() > 0){
				for(int i = 0;i < investcc.size();i++){
					String investId = investcc.get(i).getId();//投资人userid
					InvestRepayment investRepayment = new InvestRepayment();
					investRepayment.setInvestId(investId);
					
			    }
			}
			userFund.setAvailableAmount(bd.add(umpTRecord.getAmount(), userFundOld.getAvailableAmount()));// 剩余可用金额=本次实际转账金额+之前可以用余额
			userFund.setUserid(userFund.getUserid());
			userFund.setDueOutAmount(loan.getAmount().add(rateSumAmount).add(userFundOld.getDueOutAmount()));// 代还金额=本次借贷金额+本次借款利息+之前的代还金额
			userFund.setTimelastupdated(orderTime.getLastUpdateTime());
			this.updateUserFundByUserID(userFund);// 更新借款人用户资金总账户
		} catch (Exception e) {
			logger.info("根据orderId更新借款人的账户总资金失败"+umpTRecord.getOrderid());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "根据orderId更新借款人的账户总资金失败" + Invest.class);
		}

	}

	private void updateTzrUserFund(Loan loanInfo) {
		try {
			List<Invest> investcc = new ArrayList<>();
			Invest invest = new Invest();
			invest.setLoanid(loanInfo.getId());
			invest.setStatus(InvestStatus.SETTLED);
			investcc = investService.getInvestByLoanId(invest);
			UserFund userFund = new UserFund();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			OrderTime orderTime = new OrderTime();
			if(investcc.size() > 0){
				for(int i = 0;i < investcc.size();i++){
					String userId = investcc.get(i).getUserid();//投资人userid	
					userFund.setUserid(userId);
					BigDecimal amount = investcc.get(i).getAmount();//此次投资金额
					userFund = userFundService.byUserID(userFund);
					InvestRepayment investRepayment = this.getInvestAllRate(investcc.get(i).getId());//根据还款计划从新代待收利息总和
					UserFund userFundobj = new UserFund();
					userFundobj.setFrozenAmount(bd.sub(userFund.getFrozenAmount(), amount));//用户投资人的冻结总额：=账户冻结额度-本次投资额度
					BigDecimal amountinterest = investRepayment.getAmountinterest();
					userFundobj.setDueInAmount(amountinterest.add(userFund.getDueInAmount()).add(amount));//待收金额=回款计划利息之和+本次投资金额+原先待收金额
					userFundobj.setTimelastupdated(orderTime.getLastUpdateTime());
					userFundobj.setUserid(userFund.getUserid());
					this.updateUserFundByUserID(userFundobj);
				}
				
			}
		} catch (Exception e) {
			logger.info("更新投资人冻结账户金额失败loanId :"+loanInfo.getId()+e);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		
	}


	private InvestRepayment getInvestAllRate(String investId) {
		InvestRepayment investRepayment = null;
		try {
			investRepayment = investRepayMentService.getInvestAllRate(investId);
		} catch (Exception e) {
			logger.info("查询投资人回款计划利息总额失败"+investId);
			logger.info(e,e.fillInStackTrace());
		}
		return investRepayment;
	}



	// //更新投资人投标记录状态从审核中到成功:AUDITING--SETTLED
	private void updateInvestByLoanId(Invest invest) {
		try {
			investService.updateInvestByLoanId(invest);
		} catch (Exception e) {
			logger.info("更新投资人资金记录失败"+invest.getOrderId());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "更新投资人资金记录失败");
		}

	}

	// 更新个人用户资金总账户
	private void updateUserFundByUserID(UserFund userFund) {
		try {
			userFundService.updateUserFundByUserID(userFund);
		} catch (Exception e) {
			logger.info("更新个人资金账户失败:"+userFund.getUserid());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "更新个人资金账户失败");
		}

	}

	// 根据LoanId查询标的利息
	private BigDecimal loanRateSum(Loan loanInfo) {
		BigDecimal kk = new BigDecimal(0.00);
		try {
			ArithmeticInterest arithmeticInterestTotal = new ArithmeticInterest();// 标的总利息
			kk = arithmeticInterestTotal.getArithmeticTotal(loanInfo);
		} catch (Exception e) {
			logger.info(" 根据LoanId查询标的利计算失败 loanId:"+loanInfo.getId());
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return kk;
	}

	private int investRepayment(Loan loanInfo) {
		int i = 0;
		// 个人投资回款计划
		if (loanInfo != null) {
			try {

					String kk = loanInfo.getMonths().toString();// 还款期数
					int kkc = Integer.parseInt(kk);
					Invest invest = new Invest();
					invest.setStatus(InvestStatus.AUDITING);
					invest.setLoanid(loanInfo.getId());
					List<Invest> investcc = new ArrayList<>();
					investcc = investService.getInvestByLoanId(invest);
				

				

					SimpleDateFormat sdf = new SimpleDateFormat("dd");// 完整年
					String newTime = sdf.format(new Date());
					int newDate = Integer.parseInt(newTime);
					RepaymentCalendar re = new RepaymentCalendar();
					for (int k = 0; k < investcc.size(); k++) {
						Invest in = new Invest();
						in.setUserid(investcc.get(k).getUserid());
						in.setAmount(investcc.get(k).getAmount());
						in.setStatus(investcc.get(k).getStatus());
						in.setId(investcc.get(k).getId());
						InvestRepayment investRepayment =  new InvestRepayment();
						if("MonthlyInterest".endsWith(loanInfo.getMethod().toString())){
							for (int j = 1; j <= kkc; j++) {
								String String[] = re.getNextmonths(kkc, newDate);// 调用还款日期生成控件
								String date = String[j];
								investRepayment = makeInvestRepaymentOl(in, loanInfo, j, date, kkc);
								i = investRepayMentService.createInvestRepayMent(investRepayment);// 用户投资汇款计划
							}
						}else{
							String date = re.getBulletmonths(kkc, newDate);// 调用还款日期生成控件
							investRepayment = makeInvestRepaymentOlBulletmonths(in, loanInfo, date);
							i = investRepayMentService.createInvestRepayMent(investRepayment);// 用户投资汇款计划
						}
						
					}
				
			} catch (Exception e) {
				logger.info("个人投资回款计划生成失败"+e);
				e.printStackTrace();
				logger.info(e,e.fillInStackTrace());
			}
		}
		return i;

	}



	// 生成标的还款计划
	private int loanRepayment(Loan loanInfo) {
		// 生成标的还款计划
		if (loanInfo != null) {
			loanInfo.getMonths();// 期限月
			String kk = loanInfo.getMonths().toString();// 还款期数
			int kkc = Integer.parseInt(kk);
			int i =0;
			try {
				LoanRepayment loanRepayment = new LoanRepayment();
				RepaymentCalendar re = new RepaymentCalendar();
				SimpleDateFormat sdf = new SimpleDateFormat("dd");// 完整年
				String newTime = sdf.format(new Date());
				int newDate = Integer.parseInt(newTime);
				if("MonthlyInterest".endsWith(loanInfo.getMethod().toString())){
					for (int j = 1; j <= kkc; j++) {
						String String[] = re.getNextmonths(kkc, newDate);// 调用还款日期生成控件
						String date = String[j];
						loanRepayment = makeLoanRepaymentOl(loanInfo, j, kkc, date);
						i = loanRepayMentService.createLoanRepayMent(loanRepayment);// 标的还款计划
					}
				}else{
					String date = re.getBulletmonths(kkc, newDate);// 调用还款日期生成控件
					loanRepayment = makeLoanRepaymentBullet(loanInfo,date);//一次性还本息
					i =  loanRepayMentService.createLoanRepayMent(loanRepayment);// 标的还款计划
				}
				
			} catch (Exception e) {
				logger.info("生成标的还款计划失败"+e+"loanId :"+loanInfo.getId());
				System.out.println("生成标的还款几乎失败");
				e.printStackTrace();
				logger.info(e,e.fillInStackTrace());
			}
		}
		return 0;
	}

	private LoanRepayment makeLoanRepaymentOl(Loan loanInfo, int i, int kkc, String date) {
		LoanRepayment loanRepaymentobg = new LoanRepayment();
		ArithmeticInterest arithmeticInterest = new ArithmeticInterest();// 利息算法
		String loanstr = "loanstr";
			try {
				BigDecimal reteAmount = arithmeticInterest.getArithmeticMonthInterest(loanInfo.getRate(),
						loanInfo.getAmount(), loanInfo.getMonths(),loanstr);
				loanRepaymentobg.setId(UUID.randomUUID().toString().toUpperCase());
				loanRepaymentobg.setCurrentperiod(i);
				loanRepaymentobg.setLoanId(loanInfo.getId());
				loanRepaymentobg.setStatus(LoanRepayMent.UNDUE);
				loanRepaymentobg.setAmountinterest(reteAmount);// 还款利息
				if (i != kkc) {
					loanRepaymentobg.setAmountoutstanding(loanInfo.getAmount());
				} else {
					loanRepaymentobg.setAmountoutstanding(new BigDecimal(0.00));
				}
				if (i != kkc) {
					loanRepaymentobg.setAmountprincipal(new BigDecimal(0.00));
				} else {
					loanRepaymentobg.setAmountprincipal(loanInfo.getAmount());
				}
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
				loanRepaymentobg.setDuedate(sdfs.parse(date));
			} catch (Exception e) {
				logger.info("按月付息到期还本生成数据储出错 :"+e);
				e.printStackTrace();
		}
		return loanRepaymentobg;
	}
	//一次新付本息标的还款计划生成数据
	private LoanRepayment makeLoanRepaymentBullet(Loan loanInfo,String date) {
		LoanRepayment loanRepaymentobg = new LoanRepayment();
		ArithmeticInterest arithmeticInterest = new ArithmeticInterest();// 利息算法
		String loanstr = "loanstr";
			try {
				BigDecimal reteAmount = arithmeticInterest.getArithmeticInterest(loanInfo.getRate(),
						loanInfo.getAmount(), loanInfo.getMonths(),loanstr);
				loanRepaymentobg.setId(UUID.randomUUID().toString().toUpperCase());
				loanRepaymentobg.setCurrentperiod(1);
				loanRepaymentobg.setLoanId(loanInfo.getId());
				loanRepaymentobg.setStatus(LoanRepayMent.UNDUE);
				loanRepaymentobg.setAmountinterest(reteAmount);// 还款利息
				loanRepaymentobg.setAmountoutstanding(loanInfo.getAmount());
				loanRepaymentobg.setAmountprincipal(loanInfo.getAmount());
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
				loanRepaymentobg.setDuedate(sdfs.parse(date));
			} catch (Exception e) {
				logger.info("按月付息到期还本生成数据储出错 :"+e);
				e.printStackTrace();
			}
		return loanRepaymentobg;
	}

	private InvestRepayment makeInvestRepaymentOl(Invest in, Loan loanInfo, int j, String date, int kkc) {
		InvestRepayment investRepaymentobg = new InvestRepayment();
		ArithmeticInterest arithmeticInterest = new ArithmeticInterest();// 利息算法
		String investstr="invtr";//投资人利息算法
			try {
				BigDecimal reteAmount = arithmeticInterest.getArithmeticMonthInterest(loanInfo.getRate(), in.getAmount(),
						loanInfo.getMonths(),investstr);
				investRepaymentobg.setId(UUID.randomUUID().toString().toUpperCase());
				investRepaymentobg.setCurrentperiod(j);
				investRepaymentobg.setStatus(LoanRepayMent.UNDUE);
				investRepaymentobg.setAmountinterest(reteAmount);// 还款利息
				investRepaymentobg.setInvestId(in.getId());// 投资主键
				if (j != kkc) {
					investRepaymentobg.setAmountoutstanding(in.getAmount());
				} else {
					investRepaymentobg.setAmountoutstanding(new BigDecimal(0.00));
				}
				if (j != kkc) {
					investRepaymentobg.setAmountprincipal(new BigDecimal(0.00));
				} else {
					investRepaymentobg.setAmountprincipal(in.getAmount());
				}
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
				investRepaymentobg.setDuedate(date);// 到期时间
			} catch (Exception e) {
				logger.info("按月付息到期还本生成数据储出错 :"+e);
				e.printStackTrace();
			}
		return investRepaymentobg;
	}
	private InvestRepayment makeInvestRepaymentOlBulletmonths(Invest in, Loan loanInfo, String date) {
		InvestRepayment investRepaymentobg = new InvestRepayment();
		ArithmeticInterest arithmeticInterest = new ArithmeticInterest();// 利息算法
		String investstr="invtr";//投资人利息算法
			try {
				BigDecimal reteAmount = arithmeticInterest.getArithmeticInterest(loanInfo.getRate(), in.getAmount(),
						loanInfo.getMonths(),investstr);
				investRepaymentobg.setId(UUID.randomUUID().toString().toUpperCase());
				investRepaymentobg.setCurrentperiod(1);
				investRepaymentobg.setStatus(LoanRepayMent.UNDUE);
				investRepaymentobg.setAmountinterest(reteAmount);// 还款利息
				investRepaymentobg.setInvestId(in.getId());// 投资主键
				investRepaymentobg.setAmountoutstanding(in.getAmount());
				investRepaymentobg.setAmountprincipal(in.getAmount());
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
				investRepaymentobg.setDuedate(date);// 到期时间
			} catch (Exception e) {
				logger.info("按月付息到期还本生成数据储出错 :"+e);
				e.printStackTrace();
			}

		return investRepaymentobg;
	}
    @Transactional
	private void umpModifyLoanStatusTOUMP(LoanModify md) {
		// 发送更新标的请求
		try {
			Map<String, String> umpModifyLoanFisrst = UmpLoan.umpModifyLoan(md);
		} catch (ReqDataException | RetDataException e) {
			logger.info("更新标的在第三方的状态事失败Project_id：" + md.getProject_id() + "----" + e);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
	}

	// 平台添加资金记录
    @Transactional
	private void addClientFundRecord(Loan loan, String orderId,BigDecimal allFeeAmount) {

		try {
			Map<String, Object> sysMap = SystemPro.getProperties();
			String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");// 平台账户号
			ClientFundRecord clientFundRecord = new ClientFundRecord();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			clientFundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			clientFundRecord.setAccount(accountUmp);
			clientFundRecord.setAmount(allFeeAmount);// 借贷管理费=借贷总额*管理费率
			clientFundRecord.setOrderid(orderId);
			clientFundRecord.setStatus(FundRecordStatus.INITIALIZED);
			clientFundRecord.setTimerecorded(new Date());
			clientFundRecord.setType(FundRecordType.FEE_LOAN_SERVICE);
			clientFundRecord.setUserid(loan.getLoanUserId());
			clientFundRecord.setOperation(FundRecordOperation.IN);
			clientFundRecord.setDescription("借款服务费");
			clientFundRecordService.addClientFundRecord(clientFundRecord);
		} catch (Exception e) {
			logger.info("添加平台资金记录失败orderId : " + orderId);
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加平台资金记录失败");
		}

	}
    
	@Transactional
	private void updateUmpTender(UmpTenderTransferRecord umpTenderTransferRecord) {
		try {
			UmpTender umpTender = this.getUmpTender(umpTenderTransferRecord.getLoanid());//标的账户Tender--查询
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			UmpTender umpTenderObj = new UmpTender();
			if(null !=umpTenderTransferRecord){
				BigDecimal trAmount = umpTenderTransferRecord.getAmount();//本次交易额度
				BigDecimal loanAmount = umpTender.getAmount();//标的额度
				umpTenderObj.setAmount(bd.sub(loanAmount, trAmount));
			}else{
				umpTenderObj.setAmount(umpTender.getAmount());
			}
			umpTenderObj.setLoanid(umpTender.getLoanid());
			umpTenderService.updateAmound(umpTenderObj);
		} catch (Exception e) {
			logger.info("更新标的账户额度" + umpTenderTransferRecord.getLoanid());
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
	}

	// 平台添加资金记录--Fee
	@Transactional
	private int addFundRecordFee(String orderIdFee,Loan loan,BigDecimal allFeeAmount) {
		int i = 0;
		try {
//			BigDecimal amountFee = this.getAllFee(loan);
			FundRecord fundRecord = new FundRecord();
			fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			fundRecord.setAmount(allFeeAmount);// 借贷管理费=借贷总额*管理费率
			fundRecord.setOrderid(orderIdFee);
			fundRecord.setStatus(FundRecordStatus.PROCESSING);
			fundRecord.setTimerecorded(new Date());
			fundRecord.setType(FundRecordType.FEE_LOAN_SERVICE);
			fundRecord.setUserId(loan.getLoanUserId());
			fundRecord.setOperation(FundRecordOperation.OUT);
			fundRecord.setDescription("放款服务费");
			i = fundRecordService.addFundRecord(fundRecord);
		} catch (Exception e) {
			logger.info("添加用户管理费用失败orderIdFee ：" +orderIdFee);
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加用户资金记录失败");
		}
		return i;

	}

	// 管理费转账(借款标的to平台账户)
	private SettleFundRecordVo transfersLoanFee(Loan loan,String orderId,BigDecimal allFeeAmount) {
		SettleFundRecordVo settleFundRecordVo = new SettleFundRecordVo();
		try {
			UmpTender umpTender = (UmpTender) getZsessionObject(loan.getId().toString()+"te");
//			BigDecimal amount = this.getAllFee(loan);
			OrderTime ot = new OrderTime();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			BigDecimal dj = new BigDecimal(100.00);
			BigDecimal transAmount = allFeeAmount.multiply(dj).setScale(0, BigDecimal.ROUND_UP);
			Map<String, Object> sysMap = SystemPro.getProperties();
			String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
			String projectId = loan.getId().replace("-", "");
			Map<String, String> map = new HashMap<String, String>();
			map.put("order_id", orderId);
			map.put("mer_date", ot.getTmeo());
		    if(null== umpTender.getUmptenderid()){//如果Umptenderid是null说明是旧数据传的是纯数字的32为id，如果不为null说是新标的则传生成的uuid
            	map.put("project_id", projectId);
			}else{
				map.put("project_id", umpTender.getUmptenderid());
			}
//			map.put("project_id", projectId);
			map.put("serv_type", "52");
			map.put("trans_action", "02");
			map.put("partic_type", "03");
			map.put("partic_acc_type", "02");
			map.put("partic_user_id", accountUmp);
			map.put("amount", transAmount.toString());
			UmpaySignature umpaySignature = new UmpaySignature("project_transfer", map);
			Map<String, String> withdrawResults = umpaySignature.getSignature();
			settleFundRecordVo.setOrder_id(withdrawResults.get("order_id"));
			settleFundRecordVo.setMer_date(withdrawResults.get("mer_date"));
			settleFundRecordVo.setRet_code(withdrawResults.get("ret_code"));
			firstCallBackSignature(settleFundRecordVo);
		} catch (ReqDataException | RetDataException e) {
			logger.info("调用第三方扣除管理费接口失败orderId " + orderId);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return settleFundRecordVo;

	}

	private BigDecimal getAllFee(Loan loan) {
		BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
		BigDecimal allFeeAmount = new BigDecimal(0.00);
		try {
			BigDecimal loanAmount = loan.getAmount();// 标的总额度
			BigDecimal c = new BigDecimal(100.00);
			/*BigDecimal loanGuaranteeFee = (bd.mul(loanAmount, loan.getLoanGuaranteeFee())).divide(c).setScale(2,
					BigDecimal.ROUND_HALF_DOWN);// 担保费率LOANGUARANTEEFEE
			BigDecimal loanRis = (bd.mul(loanAmount, loan.getLoanRiskFee())).divide(c).setScale(2,
					BigDecimal.ROUND_HALF_DOWN);// 风险管理费率LOANRISKFEE
			BigDecimal loanManageFee = (bd.mul(loanAmount, loan.getLoanManageFee())).divide(c).setScale(2,
					BigDecimal.ROUND_HALF_DOWN);// 借款管理费率LOANMANAGEFEE
			BigDecimal loanServiceFee = (bd.mul(loanAmount, loan.getLoanServiceFee())).divide(c).setScale(2,
					BigDecimal.ROUND_HALF_DOWN);// 服务费率LOANSERVICEFEE
			allFeeAmount = loanGuaranteeFee.add(loanRis).add(loanManageFee).add(loanServiceFee);*/
			BigDecimal loanGuaranteeFee = (bd.mul(loanAmount, loan.getLoanGuaranteeFee())).divide(c);// 担保费率LOANGUARANTEEFEE
			BigDecimal loanRis = (bd.mul(loanAmount, loan.getLoanRiskFee())).divide(c);// 风险管理费率LOANRISKFEE
			BigDecimal loanManageFee = (bd.mul(loanAmount, loan.getLoanManageFee())).divide(c);// 借款管理费率LOANMANAGEFEE
			BigDecimal loanServiceFee = (bd.mul(loanAmount, loan.getLoanServiceFee())).divide(c);// 服务费率LOANSERVICEFEE
			allFeeAmount = (loanGuaranteeFee.add(loanRis).add(loanManageFee).add(loanServiceFee)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
		} catch (Exception e) {
			logger.info("计算结算(放款)时扣去的费用异常loanId：" + loan.getId());
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return allFeeAmount;
	}

	public Message loanAmountTobusinessAccount(String loanId, String amount, String description) {
		Message message = new Message();
		UmpTender umpTender = this.getUmpTender(loanId);// 更新标的账户Tender--查询
		umpTender.getUmptenderaccountid();// 标的账户号
		BigDecimal loanAmount = new BigDecimal(amount);
		ApplicationBean appBean = new ApplicationBean();
		String orderId = appBean.orderId();
		this.addUmpTenderTransferRecord(umpTender, orderId, loanAmount, description);// 添加UmpTenderTransferRecord记录
		Map<String, String> withdrawResults = this.loanAmountTobusinessAccountUMP(loanId, orderId, loanAmount);// 调用第三方从标的账户转钱到平台账户
		String code = withdrawResults.get("ret_code");
		if("0000".endsWith(code)){
			message.setCode(0000);
			message.setMessage("转账成功");
		}
		return message;
	}

	@Override
	public Message allLoanAmountTobusinessAccount() {
		Message message = new Message();
		List<UmpTender> tenders = umpTenderService.queryAll();
		for (UmpTender umpTender : tenders) {
			umpTender.getUmptenderaccountid();// 标的账户号
			BigDecimal loanAmount = umpTender.getAmount();
			ApplicationBean appBean = new ApplicationBean();
			String orderId = appBean.orderId();
			this.addUmpTenderTransferRecord(umpTender, orderId, loanAmount, null);// 添加UmpTenderTransferRecord记录
			Map<String, String> withdrawResults = this.loanAmountTobusinessAccountUMP(umpTender.getLoanid(), orderId, loanAmount);// 调用第三方从标的账户转钱到平台账户
			
		}
		
		
			message.setCode(0000);
			message.setMessage("转账成功");
		
		return message;
	}


	private UmpTender getUmpTender(String loanId) {
		UmpTender umpTender = new UmpTender();
		try {
			umpTender = umpTenderService.getUmpTender(loanId);
		} catch (Exception e) {
			logger.info("根据loanId查询UmpTender失败："+loanId);
			e.printStackTrace();
		}
		return umpTender;
	}

	private Map<String, String> loanAmountTobusinessAccountUMP(String loanId, String orderId, BigDecimal loanAmount) {
		Map<String, String> withdrawResults = null;
		try {
			Loan loan = new Loan();
			loan.setId(loanId);
			UmpTender umpTender = this.getUmpTender(loan.getId());// 更新标的账户Tender--查询
			BigDecimal dj = new BigDecimal(100.00);
			BigDecimal amount = loanAmount.multiply(dj).setScale(0, BigDecimal.ROUND_HALF_EVEN);
			OrderTime ot = new OrderTime();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			Map<String, Object> sysMap = SystemPro.getProperties();
			String zycfurlIp = (String) sysMap.get("ZYCFMANAGER_IP");
			String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
			String projectId = loanId.replace("-", "");
			Map map = new HashMap<String, String>();
			map.put("notify_url", zycfurlIp + "/manage/loanAmountTobusinessAccountCallBack");
			map.put("order_id", orderId);
			map.put("mer_date", ot.getTmeo());
			 if(null== umpTender.getUmptenderid()){//如果Umptenderid是null说明是旧数据传的是纯数字的32为id，如果不为null说是新标的则传生成的uuid
	            	map.put("project_id", projectId);
				}else{
					map.put("project_id", umpTender.getUmptenderid());
				}
//			map.put("project_id", projectId);
			map.put("serv_type", "52");
			map.put("trans_action", "02");
			map.put("partic_type", "03");
			map.put("partic_acc_type", "02");
			map.put("partic_user_id", accountUmp);
			map.put("amount", amount);
			UmpaySignature umpaySignature = new UmpaySignature("project_transfer", map);
			withdrawResults = umpaySignature.getSignature();
		} catch (ReqDataException | RetDataException e) {
			logger.info("调用第三方扣除管理费接口失败orderId：" + orderId);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return withdrawResults;
	}

	@Transactional
	private void addUmpTenderTransferRecord(UmpTender umpTender, String orderId, BigDecimal loanAmount,
			String description) {
		UmpTenderTransferRecord umpTenderTransferRecord = new UmpTenderTransferRecord();
		try {
			OrderTime or = new OrderTime();
			umpTenderTransferRecord.setAmount(loanAmount);
			umpTenderTransferRecord.setLoanid(umpTender.getLoanid());
			umpTenderTransferRecord.setOrderid(orderId);
			umpTenderTransferRecord.setTransfertype(FundRecordType.LOAN_AMOUNT_REPAYTOB);
			umpTenderTransferRecord.setMerdate(new Date());
			umpTenderTransferRecord.setTenderaction(FundRecordOperation.OUT);
			umpTenderTransferRecord.setStatus(FundRecordStatus.PROCESSING);
			umpTenderTransferRecord.setTimecreated(or.getLastUpdateTime());
			umpTenderTransferRecord.setTimelastupdated(or.getLastUpdateTime());
			//umpTenderTransferRecord.setUmptenderid(or.getLastUpdateTime());
			umpSTRService.addUmpTenderTransferRecord(umpTenderTransferRecord);
		} catch (Exception e) {
			logger.info("UmpTenderTransferRecord失败"+orderId);
			System.out.println("UmpTenderTransferRecord失败" + orderId);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
	}

	@Override
	public String loanAmountTobusinessAccountCallBack(BusinessWithdrawVo businessWithdrawVo) {
		String rechargeResults = null;
		String orderId = businessWithdrawVo.getOrder_id();
		UmpTenderTransferRecord umpTenderTransferRecord = this.getUmpTenderTransferRecord(orderId);// 查询UmpTenderTransferRecord
		UmpTenderTransferRecord uuRecord = new UmpTenderTransferRecord();
		SettleFundRecordVo settleFundRecordVo = new SettleFundRecordVo();
		FundRecord fundRecord = new FundRecord();
		fundRecord.setOrderid(businessWithdrawVo.getOrder_id());
		settleFundRecordVo.setRet_code(businessWithdrawVo.getRet_code());
		String status = umpTenderTransferRecord.getStatus().toString();
		if ("0000".endsWith(businessWithdrawVo.getRet_code())) {
			if ("SUCCESSFUL".endsWith(status)) {
				return null;
			} else {
				rechargeResults = this.seconeCallBackSignature(fundRecord, settleFundRecordVo);// 回调
				umpTenderTransferRecord.setUserid("F2683775-060B-441B-AEBD-41DF9806C0FC");//平台在TB_USER表里的用户ID
				this.addClientFundRecord(orderId, umpTenderTransferRecord);// 平台资金添加记录
				uuRecord.setOrderid(orderId);
				uuRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				this.modifyUmpTenderTransferRecordSt(uuRecord);// 修改UmpTenderTransferRecord记录状态
				this.TenderUpdateAmound(umpTenderTransferRecord);// 修改UmpTender记录金额
			}

		} else {
			rechargeResults = this.seconeCallBackSignature(fundRecord, settleFundRecordVo);// 回调
			uuRecord.setOrderid(orderId);
			uuRecord.setStatus(FundRecordStatus.FAILED);
			this.modifyUmpTenderTransferRecordSt(uuRecord);// 修改UmpTenderTransferRecord记录状态
		}
		return rechargeResults;
	}
     
	@Transactional
	private void addClientFundRecord(String orderId, UmpTenderTransferRecord umpTenderTransferRecord) {
		try {
			Map<String, Object> sysMap = SystemPro.getProperties();
			String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");// 平台账户号
			ClientFundRecord clientFundRecord = new ClientFundRecord();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			clientFundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			clientFundRecord.setAccount(accountUmp);
			clientFundRecord.setAmount(umpTenderTransferRecord.getAmount());
			clientFundRecord.setOrderid(umpTenderTransferRecord.getOrderid());
			clientFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
			clientFundRecord.setTimerecorded(umpTenderTransferRecord.getTimecreated());
			clientFundRecord.setType(FundRecordType.LOAN_AMOUNT_REPAYTOB);
			clientFundRecord.setOperation(FundRecordOperation.IN);
			clientFundRecord.setDescription("从标的转到平台账户");
			if(umpTenderTransferRecord.getUserid()!=null){
				clientFundRecord.setUserid(umpTenderTransferRecord.getUserid());
			}
			clientFundRecordService.addClientFundRecord(clientFundRecord);
		} catch (Exception e) {
			logger.info("从标的转到平台账户添加平台资金记录失败orderId:"+orderId);
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加平台资金记录失败");
		}
	}

	// 转账回调响应；告诉第三方已经本次转账交易成功
	private String seconeCallBackSignature(FundRecord fundRecord, SettleFundRecordVo settleFundRecordVo) {
		String rechargeResults = null;
		try {
			
			OrderTime ot = new OrderTime();
			Map map = new HashMap<String, String>();
			map.put("order_id", fundRecord.getOrderid());
			map.put("mer_date", ot.getTmeo());
			map.put("ret_code", settleFundRecordVo.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(settleFundRecordVo.getService(), map);
			rechargeResults = umpaySignature.callBackSignature();
		} catch (ReqDataException | RetDataException e) {
			logger.info("回调第三方失败"+settleFundRecordVo.getOrder_id());
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return rechargeResults;

	}
   @Transactional
	private void TenderUpdateAmound(UmpTenderTransferRecord umpTenderTransferRecord) {
		UmpTender umpTender = new UmpTender();
		try {
			String loanId = umpTenderTransferRecord.getLoanid();
			umpTender = umpTenderService.getUmpTender(loanId);
			BigDecimalAlgorithm bda = new BigDecimalAlgorithm();
			BigDecimal amount = bda.sub(umpTender.getAmount(), umpTenderTransferRecord.getAmount());
			umpTender.setAmount(amount);
			umpTender.setLoanid(umpTenderTransferRecord.getLoanid());
			umpTenderService.updateAmound(umpTender);
		} catch (Exception e) {
			logger.info("根据Loanid更新umptender失败" + umpTender.getLoanid());
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
	}

	private UmpTenderTransferRecord getUmpTenderTransferRecord(String orderId) {
		UmpTenderTransferRecord utfr = new UmpTenderTransferRecord();
		try {
			utfr = umpSTRService.getUmpTenderTransferRecord(orderId);
		} catch (Exception e) {
			logger.info("根据查询orderId失败orderId:" + orderId);
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return utfr;
	}
    @Transactional
	private void modifyUmpTenderTransferRecordSt(UmpTenderTransferRecord uttRecord) {
		try {
			umpSTRService.modifyUmpTenderTransferRecord(uttRecord);
		} catch (Exception e) {
			logger.info("更新UmpTenderTransferRecord失败" + uttRecord.getOrderid());
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
	}


	//查询今天结算的标的是然后判断是否有未生成划款计划的数据
	public void findSettledloan(String type, String startTime, String endTime) {
		try {
			List<UmpTenderTransferRecord> transferRecord = umpSTRService.findSettledloan(type,startTime,endTime);
			if(transferRecord.size()>0){
				for (int i = 0; i < transferRecord.size(); i++) {
					UmpSettleTransferRecord utf = new  UmpSettleTransferRecord();
					LoanRepayment  loanRepayMent = new LoanRepayment();
					loanRepayMent.setLoanId(transferRecord.get(i).getLoanid());
					int lorpcount = loanRepayMentService.findByLoanId(loanRepayMent);//查询划款计划是否为空
					
					int rpCount = investService.findInvestRepayMent(transferRecord.get(i).getLoanid());//查询回款计划是否为空
					Loan loan = queryLoanById(transferRecord.get(i).getLoanid());// 标的信息
						if(lorpcount != 0){
							System.out.println(transferRecord.get(i).getLoanid()+"还款计划生成");
						}else{
							int r = this.loanRepayment(loan);// 生成标的还款计划
							if (r !=0) {
								logger.info("个人投资回款计划成功"+loan.getId());
								System.out.println("生成标的还款几乎成功");
							} else {
								logger.info("个人投资回款计划成功"+loan.getId());
								System.out.println("生成标的还款几乎失败");
							}
						}
						if(rpCount != 0){
							System.out.println(transferRecord.get(i).getLoanid()+"回款计划生成");
						}else{
							int r = this.investRepayment(loan);// 个人投资回款计划
							if (r !=0) {
								logger.info("个人投资回款计划成功"+loan.getId());
								System.out.println("生成标的还款几乎成功");
							} else {
								logger.info("个人投资回款计划成功"+loan.getId());
								System.out.println("生成标的还款几乎失败");
							}
						}

				}
			}
			
		} catch (Exception e) {
			logger.info("生成标的还款计划=：回款计划失败"+e+"loanId :");
			System.out.println("生成标的还款几乎失败");
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		
	}


	@Override
	public Message settleLoanUpdate(HttpServletRequest request) {
		Message message = new Message();
		String  loanId = request.getParameter("loanId");
//		loanId = "A1265557-EFA9-482F-8286-8F4F7F8067D6";
		Loan loan = this.queryLoanById(loanId);// 查询标的信息
		UmpTenderTransferRecord uTransferRecord = new UmpTenderTransferRecord();
		Map<String, String> maprs = new HashMap<String, String>();
		
//		uTransferRecord.setTransfertype(transfertype);//sql拼：放款，和垫付两种
		try {
			List<UmpTenderTransferRecord> transferRecord = umpSTRService.findSettledloanByLoanId(loanId);
			if (transferRecord.size()> 0 || null == loan) {
				for (int i = 0; i < transferRecord.size(); i++) {
					UmpTenderTransferRecord uTransferRecords = new UmpTenderTransferRecord();
					uTransferRecords.setOrderid(transferRecord.get(i).getOrderid());
					uTransferRecords.setTimecreated(transferRecord.get(i).getTimecreated());
					maprs = this.findUmpTransferStatusByOrderId(uTransferRecords);
					String transferStatus = maprs.get("tran_state");
					if ("2".endsWith(transferStatus)){
						SettleFundRecordVo settleFundRecordVo = new SettleFundRecordVo();
						settleFundRecordVo.setOrder_id(maprs.get("order_id"));
						settleFundRecordVo.setRet_code(maprs.get("ret_code"));
						settleFundRecordVo.setRet_msg(maprs.get("ret_msg"));
						settleFundRecordVo.setMer_date(maprs.get("mer_date"));
						UmpTender umpTender = this.getUmpTender(loan.getId());// 更新标的账户Tender--查询
						UmpAccount umpAccount = this.getUmpAccountByUserId(loan.getLoanUserId());// 查询用户ump账户
						setZsession(umpTender, loan.getId().toString()+"te");//标的账户信息缓存
						setZsession(umpAccount, loan.getId().toString());//标的umpAccount账户信息缓存
						this.umpCallBackSettleLoan(settleFundRecordVo);
						message.setCode(0000);
						message.setMessage("调账完成");
					} else {
						message.setCode(1111);
						message.setMessage("当前状态是："+transferStatus+"不能调账，查交易详情看详情");
					}
				}
			} else {
				message.setCode(1111);
				message.setMessage("当前标的暂无交易记录");
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private Map<String, String>  findUmpTransferStatusByOrderId(UmpTenderTransferRecord uTransferRecords) {
		SettleFundRecordVo settleFundRecordVo = new SettleFundRecordVo();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> maprs = new HashMap<String, String>();
		map.put("order_id", uTransferRecords.getOrderid());
		map.put("mer_date", FormatUtils.simpleFormats(uTransferRecords.getTimecreated()).toString());
		map.put("busi_type", "03");
		
		UmpaySignature umpaySignature = new UmpaySignature("transfer_search", map);
		UmpTransferSearch uts=new UmpTransferSearch();
		Map<String, String> value = null;
			try {
				value=umpaySignature.getSignature();
				//交易状态03标的转账：0初始、2成功、3失败、4不明、5、交易关闭、6其他
				maprs.put("order_id", value.get("order_id"));
				maprs.put("ret_msg", value.get("ret_msg"));
				maprs.put("ret_code", value.get("ret_code"));
				maprs.put("mer_date", value.get("mer_date"));
				maprs.put("tran_state", value.get("tran_state"));
//				//响应给第三方
				settleFundRecordVo.setRet_code(value.get("ret_code"));
				settleFundRecordVo.setRet_msg(value.get("ret_msg"));
				settleFundRecordVo.setOrder_id(value.get("order_id"));
				settleFundRecordVo.setMer_date(FormatUtils.simpleFormats(uTransferRecords.getTimecreated()).toString());
				firstCallBackSignature(settleFundRecordVo);// 转账给借款人的第一次回调
			} catch (ReqDataException | RetDataException e) {
				e.printStackTrace();
			}
		return maprs;
	}

}
