package com.zhongyang.java.biz.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.UploadExcelBiz;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpSettleWithdrawRecord;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.ClientFundRecordService;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.ProjectService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpSettleRechargeRecordService;
import com.zhongyang.java.service.UmpSettleTenderRecordService;
import com.zhongyang.java.service.UmpSettleTransferRecordService;
import com.zhongyang.java.service.UmpSettleWithdrawRecordService;
import com.zhongyang.java.service.UmpTenderService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.UploadExcelUtil;
import com.zhongyang.java.vo.FundRecordSettle;
import com.zhongyang.java.vo.LoanListVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ProjectVo;
import com.zhongyang.java.vo.RechargeRecordVO;
import com.zhongyang.java.vo.TenderRecordVO;
import com.zhongyang.java.vo.TransferRecordVO;
import com.zhongyang.java.vo.UploadLoans;
import com.zhongyang.java.vo.UploadProjects;
import com.zhongyang.java.vo.WithdrawRecordVO;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.loan.Method;
import com.zhongyang.java.vo.settle.TenderTransferAction;
import com.zhongyang.java.vo.settle.TenderTransferType;

@Service
public class UploadExcelBizImpl implements UploadExcelBiz {
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_UMP_ACCOUNT=(String) sysMap.get("ZYCF_UMP_ACCOUNT");
	}
	private static Logger logger = Logger.getLogger(UploadExcelBizImpl.class);
	
	private static String ZYCF_UMP_ACCOUNT;
	
	@Autowired
	private ClientFundRecordService clientFundRecordService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UmpAccountService umpAccountService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Autowired
	private UmpSettleRechargeRecordService umpSettleRechargeRecordService;

	@Autowired
	private UmpSettleTenderRecordService umpSettleTenderRecordService;
	
	@Autowired
	private UmpTenderService umpTenderService;

	@Autowired
	private UmpSettleTransferRecordService umpSettleTransferRecordService;

	@Autowired
	private UmpSettleWithdrawRecordService umpSettleWithdrawRecordService;
	
	@Override
	public ResponseEntity<byte[]> uploadProject(HttpServletRequest request,PagerVO<ProjectVo> pager) {
		try {
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "项目名称", "关联企业", "借款人", "总金额（元）" ,"剩余额度（元）","借款期限(月)","借款期限(天)","还款方式","发起时间"});
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "title", "guaranteeRealm","userName", "amount", "surplusAmount","months","days","method","timeSubmit"});
		
			List<UploadProjects> uploadProjects = new ArrayList<UploadProjects>();
			Page<ProjectVo>page=new Page<ProjectVo>();

			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			page.setStartTime(new Date(pager.getStartTime()));
			page.setEndTime(new Date(pager.getEndTime()));

			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				if("".equals(pager.getField())){
					page.getParams().put("timeSubmit","TIMESUBMIT");
				}
				else{
					if(!pager.getField().contains("timeSubmit")){
						page.getParams().put("timeSubmit","TIMESUBMIT");
					}
				}
				for (int i = 0; i < fileds.length; i++) {
					if("".equals(values[i]))continue;
					page.getParams().put(fileds[i],values[i]);
				}
				if(pager.getSort()!=null){
					page.getParams().put("sort",pager.getSort());
				}
				else{
					page.getParams().put("sort","desc");
				}
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm:ss");
			List<Project> queryAllProjects = projectService.queryAllProjects(page);
			User user=new User();
			for (Project project : queryAllProjects) {
				UploadProjects up=new UploadProjects();
				up.setTitle(project.getTitle());
				up.setGuaranteeRealm(project.getGuaranteeRealm());
				user.setId(project.getLoanUserId());
				User userById = userService.getUserById(user);
				up.setUserName(userById.getName());
				up.setAmount(project.getAmount());
				up.setSurplusAmount(project.getSurplusAmount());
				up.setMonths(project.getMonths());
				up.setDays(project.getDays());
				if(Method.MonthlyInterest.equals(project.getMethod())){
					up.setMethod("按月付息到期还本");
				}
				if(Method.EqualInstallment.equals(project.getMethod())){
					up.setMethod("按月等额本息");
				}
				if(Method.EqualPrincipal.equals(project.getMethod())){
					up.setMethod("按月等额本金");
				}
				if(Method.BulletRepayment.equals(project.getMethod())){
					up.setMethod("一次性还本付息");
				}
				if(Method.EqualInterest.equals(project.getMethod())){
					up.setMethod("月平息");
				}
				if(Method.EqualInterest.equals(project.getMethod())){
					up.setMethod("按年付息到期还本");
				}
				if(project.getTimeSubmit()!=null){
					up.setTimeSubmit(sdf.format(project.getTimeSubmit()));
				}
				uploadProjects.add(up);
			}
			String name="项目发布统计";
			ResponseEntity<byte[]> responseEntity= UploadExcelUtil.upload(request, headNames, fieldNames, uploadProjects, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "导出失败");
		} 
	}

	@Override
	public ResponseEntity<byte[]> uploadPublishedLoans(HttpServletRequest request,PagerVO<LoanListVo> pager) {
		try {
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "标的名称", "产品类型", "关联企业", "标的金额（元）", "状态" ,"发布时间","结算时间"});
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "loanName", "productName", "guaranteeRealm", "amount", "status","timeOpen","timeSettled"});
			List<UploadLoans> uploadLoan=new ArrayList<UploadLoans>();
			Page<Loan>page=new Page<Loan>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			page.setStartTime(new Date(pager.getStartTime()));
			page.setEndTime(new Date(pager.getEndTime()));

			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if("xxx".equals(values[i]))continue;
					page.getParams().put(fileds[i],values[i]);
				}
			}
			else{
				page.getParams().put("timeOpen","TIMEOPEN");
			}
			if(pager.getSort()!=null){
				page.getParams().put("sort",pager.getSort());
			}
			else{
				page.getParams().put("sort","desc");
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm:ss");
			List<Loan> loans = loanService.queryPublishedLoans(page);
			for (Loan loan : loans) {
				UploadLoans ul=new UploadLoans();
				ul.setLoanName(loan.getTitle());
				ul.setProductName(loan.getProductName());
				ul.setGuaranteeRealm(loan.getGuaranteeRealm());
				ul.setAmount(loan.getAmount().toString());
				ul.setStatus(loan.getStatus().toString());
				
				if(loan.getTimeOpen()!=null){
					ul.setTimeOpen(sdf.format(loan.getTimeOpen()));
				}
				if(loan.getTimeSettled()!=null){
					ul.setTimeSettled(sdf.format(loan.getTimeSettled()));
				}
				else{
					ul.setTimeSettled("未结算");
				}
				uploadLoan.add(ul);
			}
			String name="标的发布统计";
			ResponseEntity<byte[]> responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, uploadLoan, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "导出失败");
		} 
	}

	@Override
	public ResponseEntity<byte[]> uploadRechargeRecords(HttpServletRequest request,
			PagerVO<UmpSettleRechargeRecord> pager) {
		try {
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "订单号", "用户账号", "手机号码", "真实姓名", "充值金额（元）" ,"联动流水号","联动对账日期","联动对账时间"});
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "orderid", "accountname", "mobile", "realName", "amount","transactionid","settledate","settletime"});
			List<RechargeRecordVO>records=new ArrayList<RechargeRecordVO>();
			Page<UmpSettleRechargeRecord> page=new Page<UmpSettleRechargeRecord>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			if(pager.getStartTime()!=0){
				page.setStartTime(new Date(pager.getStartTime()));
			}
			if(pager.getEndTime()!=0){
				page.setEndTime(new Date(pager.getEndTime()));
			}
			if(pager.getField()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if(values[i].equals("xxx"))continue;
					page.getParams().put(fileds[i],values[i]);
				}
				if(pager.getSort()!=null){
					page.getParams().put("sort",pager.getSort());
				}
				else{
					page.getParams().put("sort","desc");
				}
			}
			List<UmpSettleRechargeRecord> queryAllRecords = umpSettleRechargeRecordService.queryAllRecords(page);
			User user=new User();
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat time=new SimpleDateFormat("HH:mm:ss");
			for (UmpSettleRechargeRecord umpSettleRechargeRecord : queryAllRecords) {
				RechargeRecordVO rv=new RechargeRecordVO();
				rv.setAccountname(umpSettleRechargeRecord.getAccountname());
				rv.setAmount(umpSettleRechargeRecord.getAmount());
				if(ZYCF_UMP_ACCOUNT.equals(umpSettleRechargeRecord.getAccountname())){
					rv.setMobile("15311340737");
					rv.setRealName("中阳财富");
				}
				else{
					FundRecord fundRecord=new FundRecord();
					fundRecord.setOrderid(umpSettleRechargeRecord.getOrderid());
					fundRecord.setType(FundRecordType.DEPOSIT);
					FundRecord record = fundRecordService.findFundRecordByOrderId(fundRecord);
					if(record==null||record.getUserId()==null){
						rv.setMobile("--");
						rv.setRealName("--");
					}
					else{
						user.setId(record.getUserId());
						User queryUser = userService.queryByParams(user);
						String mobile = cipher.decrypt(queryUser.getMobile());//解密手机号
						rv.setMobile(mobile);
						rv.setRealName(queryUser.getName());
					}
					
				}
				
				rv.setOrderid(umpSettleRechargeRecord.getOrderid());
				rv.setSettledate(sdf.format(umpSettleRechargeRecord.getSettledate()));
				rv.setSettletime(time.format(umpSettleRechargeRecord.getSettletime()));
				rv.setTransactionid(umpSettleRechargeRecord.getTransactionid());
				records.add(rv);
			}
			String name="充值对账";
			ResponseEntity<byte[]> responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, records, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"充值对账文件导出异常，请重试");
		}
	}

	@Override
	public ResponseEntity<byte[]> uploadWithdrawRecords(HttpServletRequest request,
			PagerVO<UmpSettleWithdrawRecord> pager) {
		try {
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "订单号",  "手机号码", "真实姓名", "提现金额（元）" ,"手续费（元）","交易状态","联动流水号","联动对账日期"});
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "orderid", "mobile", "realName", "amount","fee","state","transactionid","recordDate"});
			List<WithdrawRecordVO>records=new ArrayList<WithdrawRecordVO>();
			Page<UmpSettleWithdrawRecord> page=new Page<UmpSettleWithdrawRecord>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			if(pager.getStartTime()!=0){
				page.setStartTime(new Date(pager.getStartTime()));
			}
			if(pager.getEndTime()!=0){
				page.setEndTime(new Date(pager.getEndTime()));
			}
			if(pager.getField()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if(values[i].equals("xxx"))continue;
					page.getParams().put(fileds[i],values[i]);
				}
				if(pager.getSort()!=null){
					page.getParams().put("sort",pager.getSort());
				}
				else{
					page.getParams().put("sort","desc");
				}
			}
			
			List<UmpSettleWithdrawRecord> queryAllRecords = umpSettleWithdrawRecordService.queryAllRecords(page);
			User user=new User();
			FundRecord fundRecord=new FundRecord();
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			for (UmpSettleWithdrawRecord umpSettleWithdrawRecord : queryAllRecords) {
				WithdrawRecordVO rv=new WithdrawRecordVO();
				rv.setAmount(umpSettleWithdrawRecord.getAmount());
				rv.setFee(umpSettleWithdrawRecord.getFee());
				rv.setOrderid(umpSettleWithdrawRecord.getOrderid());
				rv.setRecordDate(sdf.format(umpSettleWithdrawRecord.getRecorddate()));
				rv.setState(umpSettleWithdrawRecord.getState());
				rv.setTransactionid(umpSettleWithdrawRecord.getTransactionid());				
				fundRecord.setOrderid(umpSettleWithdrawRecord.getOrderid());
				if(umpSettleWithdrawRecord.getOrderid().contains("P")){
					rv.setMobile("--");
					rv.setRealName("测试绑卡");
					records.add(rv);
					continue;
				}
				List<FundRecord> fr = fundRecordService.findFundRecordListByOrderId(fundRecord);
				if(fr.size()==0){
					ClientFundRecord clientFundRecord=new ClientFundRecord();
					clientFundRecord.setOrderid(umpSettleWithdrawRecord.getOrderid());
					ClientFundRecord queryRecord = clientFundRecordService.getClentFundByOrderId(clientFundRecord);
					if(queryRecord!=null){
						rv.setMobile("15311340737");
						rv.setRealName("中阳财富");
					}
				}
				else{
					user.setId(fr.get(0).getUserId());
					User queryUser = userService.queryByParams(user);
					String mobile = cipher.decrypt(queryUser.getMobile());//解密手机号
					rv.setMobile(mobile);
					rv.setRealName(queryUser.getName());
					
				}	
				records.add(rv);
			}
			String name="提现对账";
			ResponseEntity<byte[]> responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, records, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "提现对账文件导出异常，请重试");
		}
	}

	@Override
	public ResponseEntity<byte[]> uploadTransferRecords(HttpServletRequest request,
			PagerVO<UmpSettleTransferRecord> pager) {
		try {
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "订单号",  "转出用户", "转出账号", "转入用户" ,"转入账号","转账金额（元）","联动流水号","联动对账日期","对账时间"});
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "orderid", "outName", "outaccountid", "inName","inaccountid","amount","transactionid","settledate","settletime"});
			List<TransferRecordVO>records=new ArrayList<TransferRecordVO>();
			Page<UmpSettleTransferRecord> page=new Page<UmpSettleTransferRecord>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			if(pager.getStartTime()!=0){
				page.setStartTime(new Date(pager.getStartTime()));
			}
			if(pager.getEndTime()!=0){
				page.setEndTime(new Date(pager.getEndTime()));
			}
			if(pager.getField()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if(values[i].equals("xxx"))continue;
					page.getParams().put(fileds[i],values[i]);
				}
				if(pager.getSort()!=null){
					page.getParams().put("sort",pager.getSort());
				}
				else{
					page.getParams().put("sort","desc");
				}
			}
			List<UmpSettleTransferRecord> queryAllRecords = umpSettleTransferRecordService.queryAllRecords(page);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			for (UmpSettleTransferRecord umpSettleTransferRecord : queryAllRecords) {
				TransferRecordVO rv=new TransferRecordVO();
				if(ZYCF_UMP_ACCOUNT.equals(umpSettleTransferRecord.getInaccountid())){
					rv.setInName("平台账户");
				}
				else{
					UmpAccount umpAccount=new UmpAccount();
					umpAccount.setAccountId(umpSettleTransferRecord.getInaccountid());
					User user = umpAccountService.queryByAccount(umpAccount);
					if(user==null){
						FundRecord fundRecord=new FundRecord();
						fundRecord.setOrderid(umpSettleTransferRecord.getOrderid());
						FundRecord queryRecord = fundRecordService.findFundRecordByOrderId(fundRecord);
						user=new User();
						if(queryRecord==null){
							rv.setInName("--");
						}
						else{
							user.setId(queryRecord.getUserId());
							User inUser = userService.getUserById(user);
							rv.setInName(inUser.getName());
						}
					}
					else{
						rv.setInName(user.getName());
					}
					
					
				}
				rv.setAmount(umpSettleTransferRecord.getAmount());
				rv.setInaccountid(umpSettleTransferRecord.getInaccountid());
				rv.setOrderid(umpSettleTransferRecord.getOrderid());
				if(ZYCF_UMP_ACCOUNT.equals(umpSettleTransferRecord.getOutaccountid())){
					rv.setOutName("平台账户");
				}
				else{
					UmpAccount umpAccount=new UmpAccount();
					umpAccount.setAccountId(umpSettleTransferRecord.getOutaccountid());
					User user = umpAccountService.queryByAccount(umpAccount);
					if(user==null){
						FundRecord fundRecord=new FundRecord();
						fundRecord.setOrderid(umpSettleTransferRecord.getOrderid());
						FundRecord queryRecord = fundRecordService.findFundRecordByOrderId(fundRecord);
						user=new User();
						if(queryRecord==null){
							rv.setOutName("与技术核实");
						}
						else{
							user.setId(queryRecord.getUserId());
							User outUser = userService.getUserById(user);
							rv.setOutName(outUser.getName());
						}
						
					}
					else{
						rv.setOutName(user.getName());
					}
					
					
				}
				rv.setOutaccountid(umpSettleTransferRecord.getOutaccountid());
				rv.setSettledate(sdf.format(umpSettleTransferRecord.getSettledate()));
				rv.setSettletime(sdf.format(umpSettleTransferRecord.getSettletime()));
				rv.setTransactionid(umpSettleTransferRecord.getTransactionid());		
				records.add(rv);
			}
			String name="转账对账";
			ResponseEntity<byte[]> responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, records, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "转账对账文件导出异常，请重试");
		}
	}

	@Override
	public ResponseEntity<byte[]> uploadTenderRecords(HttpServletRequest request,
			PagerVO<UmpSettleTenderRecord> pager) {
		try {
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "订单号",  "标的名称", "转出账号","转入账号","转账方向","业务类型","金额（元）","联动流水号","联动对账时间"});
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "orderid", "title", "outaccountid", "inaccountid","action","transfertype","amount","transactionid","settledate"});
			List<TenderRecordVO>records=new ArrayList<TenderRecordVO>();
			Page<UmpSettleTenderRecord> page=new Page<UmpSettleTenderRecord>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			if(pager.getStartTime()!=0){
				page.setStartTime(new Date(pager.getStartTime()));
			}
			if(pager.getEndTime()!=0){
				page.setEndTime(new Date(pager.getEndTime()));
			}
			if(pager.getField()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if(values[i].equals("xxx"))continue;
					page.getParams().put(fileds[i],values[i]);
				}
				if(pager.getSort()!=null){
					page.getParams().put("sort",pager.getSort());
				}
				else{
					page.getParams().put("sort","desc");
				}
			}
			List<UmpSettleTenderRecord> queryAllRecords = umpSettleTenderRecordService.queryAllRecords(page);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			for (UmpSettleTenderRecord umpSettleTransferRecord : queryAllRecords) {
				TenderRecordVO rv=new TenderRecordVO();
				rv.setAction(TenderTransferAction.getDescription(umpSettleTransferRecord.getAction().getNum()));
				rv.setAmount(umpSettleTransferRecord.getAmount());
				rv.setInaccountid(umpSettleTransferRecord.getInaccountid());
				rv.setOrderid(umpSettleTransferRecord.getOrderid());
				rv.setOutaccountid(umpSettleTransferRecord.getOutaccountid());
				rv.setSettledate(sdf.format(umpSettleTransferRecord.getSettledate()));
				UmpTender umpTender=new UmpTender();
				umpTender.setUmptenderaccountid(umpSettleTransferRecord.getTenderaccountid());
				UmpTender queryUmpTender = umpTenderService.queryByParams(umpTender);
				Loan loan = loanService.queryLoanById(queryUmpTender.getLoanid());
				rv.setTitle(loan.getTitle());
				rv.setTransactionid(umpSettleTransferRecord.getTransactionid());
				rv.setTransfertype(TenderTransferType.getDescription(umpSettleTransferRecord.getTransfertype().getNum()));
				records.add(rv);
			}
			String name="标的对账";
			ResponseEntity<byte[]> responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, records, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "标的对账文件导出失败，请重试");
		}
	}

	@Override
	public ResponseEntity<byte[]> uploadPlatFormRecord(HttpServletRequest request, Page<FundRecord> page) {
		List<String[]> headNames = new ArrayList<String[]>();
		headNames.add(new String[] { "订单号","手机号","真实姓名","金额（元）","交易类型","交易状态","交易日期"});
		List<String[]> fieldNames = new ArrayList<String[]>();
		fieldNames.add(new String[] { "orderId", "mobile", "realName","amount","strType","strStatus","strDate"});
		page.setPageNo(1);
		page.setPageSize(Integer.MAX_VALUE);
		String start =(String)page.getParams().get("start");
		String end =(String)page.getParams().get("end");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(start!=null&&!"".equals(start)){
				page.setStartTime(sdf.parse(start+" 00:00:00"));
			}
			if(end!=null&&!"".equals(end)){
				page.setEndTime(sdf.parse(end+" 23:59:59"));
			}
			List<FundRecordSettle> results = fundRecordService.queryFundRecords(page);
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			for (FundRecordSettle fundRecordSettle : results) {
				fundRecordSettle.setMobile(cipher.decrypt(fundRecordSettle.getMobile()));
				fundRecordSettle.setStrStatus(fundRecordSettle.getStatus().getKey());
				fundRecordSettle.setStrType(fundRecordSettle.getType().getKey());
				fundRecordSettle.setStrDate(sdf.format(fundRecordSettle.getDate()));
			}
			String name="平台个人资金记录";
			ResponseEntity<byte[]> responseEntity;
			responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, results, name);
			return responseEntity;
		} catch (GeneralSecurityException | ParseException | IllegalArgumentException |IllegalAccessException | IOException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		return null;
	}
}
