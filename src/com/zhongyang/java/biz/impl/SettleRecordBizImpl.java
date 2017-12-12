package com.zhongyang.java.biz.impl;

import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.SettleRecordBiz;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Loan;
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
import com.zhongyang.java.vo.FundRecordSettle;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.RechargeRecordVO;
import com.zhongyang.java.vo.TenderRecordVO;
import com.zhongyang.java.vo.TransferRecordVO;
import com.zhongyang.java.vo.WithdrawRecordVO;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.settle.TenderTransferAction;
import com.zhongyang.java.vo.settle.TenderTransferType;

/**
 * 
* @Title: SettleRecordBizImpl.java 
* @Package com.zhongyang.java.biz.impl 
* @Description:对账记录业务处理接口实现 
* @author 苏忠贺   
* @date 2016年1月13日 下午4:07:14 
* @version V1.0
 */
@Service
public class SettleRecordBizImpl implements SettleRecordBiz{
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_UMP_ACCOUNT=(String) sysMap.get("ZYCF_UMP_ACCOUNT");
	}
	
	private static String ZYCF_UMP_ACCOUNT;
	
	private static Logger logger = Logger.getLogger(SettleRecordBizImpl.class);
	
	@Autowired
	private UmpAccountService umpAccountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private UmpTenderService umpTenderService;
	
	@Autowired
	private ClientFundRecordService clientFundRecordService;
	
	@Autowired
	private UmpSettleRechargeRecordService umpSettleRechargeRecordService;

	@Autowired
	private UmpSettleTenderRecordService umpSettleTenderRecordService;

	@Autowired
	private UmpSettleTransferRecordService umpSettleTransferRecordService;

	@Autowired
	private UmpSettleWithdrawRecordService umpSettleWithdrawRecordService;
	
	@Override
	public PagerVO<RechargeRecordVO> queryRechargeRecord(PagerVO<UmpSettleRechargeRecord> pager) {
		try {
			PagerVO<RechargeRecordVO> pagerVo=new PagerVO<RechargeRecordVO>();
			List<RechargeRecordVO>records=new ArrayList<RechargeRecordVO>();
			Page<UmpSettleRechargeRecord> page=new Page<UmpSettleRechargeRecord>();
			if(pager.getStart()==0){
				page.setPageNo(1);
				pagerVo.setStart(1);
			}
			else{
				page.setPageNo(pager.getStart());
				pagerVo.setStart(pager.getStart());
			}
			page.setPageSize(pager.getLength());
			pagerVo.setLength(pager.getLength());
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
			Map<String,Object> map=page.getParams();
			SimpleDateFormat simp=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			page.setStartTime(format.parse(simp.format(page.getStartTime())+" 00:00:00"));
			page.setEndTime(format.parse(simp.format(page.getEndTime())+" 23:59:59"));
			map.put("startTime",page.getStartTime() );
			map.put("endTime",page.getEndTime() );
			int count = umpSettleRechargeRecordService.queryCount(map);
			int totalPage=count%pager.getLength();
			if(totalPage==0){
				pagerVo.setTotalPage(count/pager.getLength());
			}
			else{
				pagerVo.setTotalPage(count/pager.getLength()+1);
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
			pagerVo.setData(records);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"数据查询失败");
		}
	}

	@Override
	public PagerVO<WithdrawRecordVO> queryWithdrawRecord(PagerVO<UmpSettleWithdrawRecord> pager) {
		try {
			PagerVO<WithdrawRecordVO> pagerVo=new PagerVO<WithdrawRecordVO>();
			List<WithdrawRecordVO>records=new ArrayList<WithdrawRecordVO>();
			Page<UmpSettleWithdrawRecord> page=new Page<UmpSettleWithdrawRecord>();
			if(pager.getStart()==0){
				page.setPageNo(1);
				pagerVo.setStart(1);
			}
			else{
				page.setPageNo(pager.getStart());
				pagerVo.setStart(pager.getStart());
			}
			page.setPageSize(pager.getLength());
			pagerVo.setLength(pager.getLength());
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
			Map<String,Object> map=page.getParams();
			SimpleDateFormat simp=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			page.setStartTime(format.parse(simp.format(page.getStartTime())+" 00:00:00"));
			page.setEndTime(format.parse(simp.format(page.getEndTime())+" 23:59:59"));
			map.put("startTime",page.getStartTime() );
			map.put("endTime",page.getEndTime() );
			
			int count = umpSettleWithdrawRecordService.queryCount(map);
			int totalPage=count%page.getPageSize();
			if(totalPage==0){
				pagerVo.setTotalPage(count/pager.getLength());
			}
			else{
				pagerVo.setTotalPage(count/pager.getLength()+1);
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
			pagerVo.setData(records);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败");
		}
	}

	@Override
	public PagerVO<TransferRecordVO> queryTransferRecord(PagerVO<UmpSettleTransferRecord> pager) {
		try {
			PagerVO<TransferRecordVO> pagerVo=new PagerVO<TransferRecordVO>();
			List<TransferRecordVO>records=new ArrayList<TransferRecordVO>();
			Page<UmpSettleTransferRecord> page=new Page<UmpSettleTransferRecord>();
			if(pager.getStart()==0){
				page.setPageNo(1);
				pagerVo.setStart(1);
			}
			else{
				page.setPageNo(pager.getStart());
				pagerVo.setStart(pager.getStart());
			}
			page.setPageSize(pager.getLength());
			pagerVo.setLength(pager.getLength());
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
			
			Map<String,Object> map=page.getParams();
			SimpleDateFormat simp=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			page.setStartTime(format.parse(simp.format(page.getStartTime())+" 00:00:00"));
			page.setEndTime(format.parse(simp.format(page.getEndTime())+" 23:59:59"));
			map.put("startTime",page.getStartTime() );
			map.put("endTime",page.getEndTime() );
			
			int count = umpSettleTransferRecordService.queryCount(map);
			int totalPage=count%page.getPageSize();
			if(totalPage==0){
				pagerVo.setTotalPage(count/pager.getLength());
			}
			else{
				pagerVo.setTotalPage(count/pager.getLength()+1);
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
			pagerVo.setData(records);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败");
		}
	}

	@Override
	public PagerVO<TenderRecordVO> queryTenderRecord(PagerVO<UmpSettleTenderRecord> pager) {
		try {
			PagerVO<TenderRecordVO> pagerVo=new PagerVO<TenderRecordVO>();
			List<TenderRecordVO>records=new ArrayList<TenderRecordVO>();
			Page<UmpSettleTenderRecord> page=new Page<UmpSettleTenderRecord>();
			if(pager.getStart()==0){
				page.setPageNo(1);
				pagerVo.setStart(1);
			}
			else{
				page.setPageNo(pager.getStart());
				pagerVo.setStart(pager.getStart());
			}
			page.setPageSize(pager.getLength());
			pagerVo.setLength(pager.getLength());
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
			
			Map<String,Object> map=page.getParams();
			SimpleDateFormat simp=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			page.setStartTime(format.parse(simp.format(page.getStartTime())+" 00:00:00"));
			page.setEndTime(format.parse(simp.format(page.getEndTime())+" 23:59:59"));
			map.put("startTime",page.getStartTime() );
			map.put("endTime",page.getEndTime() );
			
			int count = umpSettleTenderRecordService.queryCount(map);
			int totalPage=count%page.getPageSize();
			if(totalPage==0){
				pagerVo.setTotalPage(count/pager.getLength());
			}
			else{
				pagerVo.setTotalPage(count/pager.getLength()+1);
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
			pagerVo.setData(records);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败");
		}
	}

	@Override
	public Page<FundRecordSettle> queryPlatFormRecord(Page<FundRecord> page) {
		Page<FundRecordSettle> pager=new Page<FundRecordSettle>();
		pager.setPageNo(page.getPageNo());
		pager.setPageSize(page.getPageSize());
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
			pager.setResults(results);
			pager.setTotalRecord(page.getTotalRecord());
			int totalPage=page.getTotalRecord()%page.getPageSize();
			if(totalPage==0){
				pager.setTotalPage(page.getTotalRecord()/page.getPageSize());
			}
			else{
				pager.setTotalPage(page.getTotalRecord()/page.getPageSize()+1);
			}
			
		} catch (GeneralSecurityException | ParseException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		return pager;
	}
}
