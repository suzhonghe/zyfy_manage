package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.AdjustAccountBiz;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.LoanOrder;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.service.LoanOrderService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.UmpTenderService;
import com.zhongyang.java.service.UmpTenderTransferRecordService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.loan.InvestStatus;
import com.zhongyang.java.vo.loan.LoanStatus;
@Service
public class AdjustAccountBizImpl implements AdjustAccountBiz {
	
	private static Logger logger = Logger.getLogger(AdjustAccountBizImpl.class);
	
	@Autowired
	private LoanOrderService loanOrderService;
	
	@Autowired
	private InvestService investService;
	
	@Autowired
	private UmpTenderTransferRecordService uttrService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private UmpTenderService umpTenderService;

	@Override
	public Message adjustInvestData(String orderId,String status) {
		Message message=new Message();
		//status说明，01代表投资请求已发送到联动，但回调过程掉单，联动已成功处理；02代表投资请求联动没有受理或者处理失败回调发生掉单异常
		try{
			if(orderId==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"订单号不能为空");
			}
			//根据订单号查询订单信息
			LoanOrder loanOrder = loanOrderService.queryLoanOrderByOrderId(orderId);
			
			//获得所投标的信息
			Loan loan = loanService.queryLoanById(loanOrder.getLoanId());
			
			if("01".equals(status)){
				
				//修改投资订单信息
				LoanOrder order=new LoanOrder();
				order.setOrderId(orderId);
				order.setStatus(FundRecordStatus.SUCCESSFUL);//订单成功
				loanOrderService.modifyLoanOrder(order);
				
				//修改投资信息TB_INVEST
				Invest invest=new Invest();
				invest.setOrderId(orderId);
				invest.setStatus(InvestStatus.AUDITING);//审核中
				investService.modifyInvest(invest);
				
				//修改标的交易记录TB_UMP_TENDER_TRANSFER_RECORD
				UmpTenderTransferRecord uttRecord=new UmpTenderTransferRecord();
				uttRecord.setOrderid(loanOrder.getOrderId());//订单号
				uttRecord.setMerdate(loanOrder.getOrderDate());//交易日期
				uttRecord.setRetcode("0000");//返回码
				uttRecord.setRetmsg("交易成功");//返回信息
				uttRecord.setStatus(FundRecordStatus.SUCCESSFUL);//状态
				uttRecord.setTimelastupdated(new Date());//最后修改日期
				uttrService.modifyUmpTenderTransferRecord(uttRecord);
				
				//修改资金流水记录 TB_FUND_RECORD
				FundRecord fundRecord=new FundRecord();
				fundRecord.setDescription("投资成功");
				fundRecord.setOrderid(loanOrder.getOrderId());//订单号
				fundRecord.setRecordpriv("投资"+loan.getTitle()+"资金完成处理");//扩展信息
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);//成功
				fundRecordService.modifyFundRecord(fundRecord);
				
				//冻结投资用户资金TB_USER_FUND
				Invest qi=new Invest();
				qi.setId(loanOrder.getInvestId());
				Invest queryInvest = investService.queryById(qi);
				UserFund uf=new UserFund();
				uf.setUserid(queryInvest.getUserid());
				uf.setAvailableAmount(new BigDecimal(-1).multiply(loanOrder.getAmount()));//修改可用余额
				uf.setFrozenAmount(loanOrder.getAmount());//修改冻结资金
				uf.setTimelastupdated(new Date());//最后提交日期
				userFundService.modifyUserFund(uf);
				
				//修改标的信息
				Loan modifyLoan=new Loan();
				modifyLoan.setId(loan.getId());
				modifyLoan.setBidAmount((BigDecimalAlgorithm.add(loan.getBidAmount(), loanOrder.getAmount())).setScale(2,BigDecimal.ROUND_DOWN));//修改实际投资金额
				modifyLoan.setBidNumber(loan.getBidNumber()+1);//修改投资人数
				Date date=new Date();
				if(loan.getAmount().equals(modifyLoan.getBidAmount())){
					modifyLoan.setStatus(LoanStatus.FINISHED);
					Timestamp timeStamp = new Timestamp(date.getTime());
					modifyLoan.setTimeFinished(timeStamp);
				}
				loanService.modifyLoan(modifyLoan);
				
				UmpTender tender=new UmpTender();
				tender.setLoanid(loanOrder.getLoanId());
				UmpTender queryTender = umpTenderService.queryUmpTenderByLoanId(loanOrder.getLoanId());
				tender.setAmount(BigDecimalAlgorithm.add(queryTender.getAmount(), loanOrder.getAmount()));
				umpTenderService.modifyUmpTender(tender);
				
				message.setCode(1);
				message.setMessage("调账成功");
				return message;
			}
			if("02".equals(status)){
				//修改投资订单信息
				LoanOrder order=new LoanOrder();
				order.setOrderId(orderId);
				order.setStatus(FundRecordStatus.FAILED);//订单成功
				loanOrderService.modifyLoanOrder(order);
				
				//修改投资信息TB_INVEST
				Invest invest=new Invest();
				invest.setOrderId(orderId);
				invest.setStatus(InvestStatus.FAILED);//审核中
				investService.modifyInvest(invest);
				
				//修改标的交易记录TB_UMP_TENDER_TRANSFER_RECORD
				UmpTenderTransferRecord uttRecord=new UmpTenderTransferRecord();
				uttRecord.setOrderid(loanOrder.getOrderId());//订单号
				uttRecord.setMerdate(loanOrder.getOrderDate());//交易日期
				uttRecord.setRetcode("00240000");//返回码
				uttRecord.setRetmsg("交易失败");//返回信息
				uttRecord.setStatus(FundRecordStatus.FAILED);//状态
				uttRecord.setTimelastupdated(new Date());//最后修改日期
				uttrService.modifyUmpTenderTransferRecord(uttRecord);
				
				//修改资金流水记录 TB_FUND_RECORD
				FundRecord fundRecord=new FundRecord();
				fundRecord.setDescription("投资成功");
				fundRecord.setOrderid(loanOrder.getOrderId());//订单号
				fundRecord.setRecordpriv("投资"+loan.getTitle()+"资金完成处理");//扩展信息
				fundRecord.setStatus(FundRecordStatus.FAILED);//成功
				fundRecordService.modifyFundRecord(fundRecord);
				
			}
			message.setCode(1);
			message.setMessage("调账成功");
			return message;
		}catch(Exception e){
			e.printStackTrace();
			message.setCode(0);
			message.setMessage(e.getMessage());
			logger.info("投资调账出现异常");
			logger.info(e,e.fillInStackTrace());
			return message;
		}
	}

}
