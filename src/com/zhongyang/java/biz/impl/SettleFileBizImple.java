package com.zhongyang.java.biz.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.SettleFileBiz;
import com.zhongyang.java.pojo.SettleFileRecord;
import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpSettleWithdrawRecord;
import com.zhongyang.java.service.SettleFileRecordService;
import com.zhongyang.java.service.UmpSettleRechargeRecordService;
import com.zhongyang.java.service.UmpSettleTenderRecordService;
import com.zhongyang.java.service.UmpSettleTransferRecordService;
import com.zhongyang.java.service.UmpSettleWithdrawRecordService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.settle.SettleStatus;
import com.zhongyang.java.vo.settle.SettleType;
import com.zhongyang.java.vo.settle.TenderTransferAction;
import com.zhongyang.java.vo.settle.TenderTransferType;

/**
 * 
 * @Title: SettleFileBizImple.java
 * @Package com.zhongyang.java.biz.impl
 * @Description:对账业务实现
 * @author 苏忠贺
 * @date 2016年1月7日 下午1:19:51
 * @version V1.0
 */
@Service
public class SettleFileBizImple implements SettleFileBiz {
	
	private static Logger logger = Logger.getLogger(SettleFileBizImple.class);

	@Autowired
	private UmpSettleRechargeRecordService umpSettleRechargeRecordService;

	@Autowired
	private UmpSettleTenderRecordService umpSettleTenderRecordService;

	@Autowired
	private UmpSettleTransferRecordService umpSettleTransferRecordService;

	@Autowired
	private UmpSettleWithdrawRecordService umpSettleWithdrawRecordService;
	
	@Autowired
	private SettleFileRecordService settleFileRecordService;

	/**
	 * 联动充值对账记录
	 * @throws Exception 
	 */
	@Override
	public Message settleRecharge(String number, String startDate,String endDate){
		Message message=new Message();
		long days=1;
		if(startDate==null||endDate==null||number==null){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收异常");
		}
		SimpleDateFormat paramTime=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat hours = new SimpleDateFormat("HHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date start=null;
		Date end=null;
		
		try {
			start = paramTime.parse(startDate);
			end = paramTime.parse(endDate);
		} catch (ParseException e2) {
			logger.info(e2,e2.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "日期转换异常");
		}
		if(end.getTime()>start.getTime()){
			days=(end.getTime()-start.getTime())/(1000*60*60*24);
		}

		if(days>31){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "对账时间不可以超过31天");
		}
		long date;
		
		for (long i = 0; i <=days; i++) {
			date=start.getTime()+1000*60*60*24*i;
			try {
				List<UmpSettleRechargeRecord>umpSettleRechargeRecords=new ArrayList<UmpSettleRechargeRecord>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("settle_type_p2p", number);
				map.put("settle_date_p2p", time.format(new Date(date)));

				// 发送到联动优势
				UmpaySignature umpaySignature = new UmpaySignature("download_settle_file_p", map);
				String valueMap = umpaySignature.getSignatureStrng();
				// 对账获得联动返回的文件流
				URL url = new URL(valueMap);
				HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();

				httpUrl.connect();
				InputStream input = new BufferedInputStream(httpUrl.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(input,"gbk"));
				String str;
				while ((str = br.readLine()) != null) {
					if (str.startsWith("TRADEDETAIL")) {
						continue;
					}
					str=str+",";
					String[] split = str.split(",");
					UmpSettleRechargeRecord record = new UmpSettleRechargeRecord();
					record.setOrderid(split[0]);
					record.setMerdate(time.parse(split[1]));// 订单日期对应联动交易日期
					record.setAccountid(split[2]);// 账户号
					record.setAccountname(split[3]);// 资金账户托管平台用户号
					record.setAmount(BigDecimalAlgorithm.div(new BigDecimal(split[4]), new BigDecimal(100)));// 金额
					record.setSettledate(time.parse(split[5]));// 对账日期
					record.setSettletime(sdf.parse(split[5]+split[6]));// 对账时间
					record.setTransactionid(split[7]);// 交易流水号
					umpSettleRechargeRecords.add(record);
				}
				if(umpSettleRechargeRecords.size()!=0){
					umpSettleRechargeRecordService.batchDeleteRecord(umpSettleRechargeRecords);
					umpSettleRechargeRecordService.batchAddRecord(umpSettleRechargeRecords);
				}
							
				br.close();
				//充值对账记录
				addSettleRecord(date,SettleType.RECHARGE,SettleStatus.SUCCESSFUL);
				message.setCode(1);
				message.setMessage("充值对账文件下载成功");
			} catch (Exception e) {
				try {
					addSettleRecord(date,SettleType.RECHARGE,SettleStatus.FAILED);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				logger.info(e,e.fillInStackTrace());
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "充值对账异常，请重试");
			}
		}
		
		return message;
	}

	/**
	 * 联动提现对账记录
	 */
	@Override
	public Message settleWithdraw(String number, String startDate,String endDate) {
		Message message=new Message();
		long days=1;
		if(startDate==null||endDate==null||number==null){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收异常");
		}
		SimpleDateFormat paramTime=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
		Date start=null;
		Date end=null;
		
		try {
			start = paramTime.parse(startDate);
			end = paramTime.parse(endDate);
		} catch (ParseException e2) {
			logger.info(e2,e2.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "日期转换异常");
		}
		if(end.getTime()>start.getTime()){
			days=(end.getTime()-start.getTime())/(1000*60*60*24);
		}

		if(days>31){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "对账时间不可以超过31天");
		}
		long date;
		for (long i = 0; i <= days; i++) {
			date=start.getTime()+1000*60*60*24*i;
			try {
				List<UmpSettleWithdrawRecord>umpSettleWithdrawRecords=new ArrayList<UmpSettleWithdrawRecord>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("settle_type_p2p", number);
				map.put("settle_date_p2p", time.format(new Date(date)));

				// 发送到联动优势
				UmpaySignature umpaySignature = new UmpaySignature("download_settle_file_p", map);
				String valueMap = umpaySignature.getSignatureStrng();
				// 对账获得联动返回的文件流
				URL url = new URL(valueMap);
				
				HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();

				httpUrl.connect();
				InputStream input = new BufferedInputStream(httpUrl.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(input,"gbk"));
				
				String str;
				while ((str = br.readLine()) != null) {
					if (str.startsWith("TRADEDETAIL")) {
						continue;
					}
					str=str+",";
					String[] split = str.split(",");
					UmpSettleWithdrawRecord record = new UmpSettleWithdrawRecord();
					/*
					 * 商户号 ,交易类型（可忽略）,商户订单号,订单日期,交易金额,手续费,对账日期,记账日期,交易状态（中文描述）, 
					 * 联动内部机构交易流水,手续费承担方类型
					 */
					record.setOrderid(split[2]);//订单号
					record.setAmount(BigDecimalAlgorithm.div(new BigDecimal(split[4]), new BigDecimal(100)));// 金额
					record.setFee(new BigDecimal(split[5]));//手续费
					record.setMerdate(time.parse(split[3]));// 订单日期
					record.setMerid(split[0]);//商户号
					record.setRecorddate(time.parse(split[7]));//联动记账日期
					record.setSettledate(time.parse(split[6]));//对账日期
					record.setState(split[8]);
					record.setTransactionid(split[9]);// 交易流水号
					record.setType(split[1]);
					umpSettleWithdrawRecords.add(record);
				}
				if(umpSettleWithdrawRecords.size()!=0){
					umpSettleWithdrawRecordService.batchDeleteRecord(umpSettleWithdrawRecords);
					umpSettleWithdrawRecordService.batchAddRecord(umpSettleWithdrawRecords);
				}
				br.close();
				
				//提现对账记录
				addSettleRecord(date,SettleType.WITHDRAW,SettleStatus.SUCCESSFUL);
				message.setCode(1);
				message.setMessage("提现对账文件下载成功");
			} catch (Exception e) {
				try {
					addSettleRecord(date,SettleType.WITHDRAW,SettleStatus.FAILED);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				logger.info(e,e.fillInStackTrace());
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "提现对账失败，请重试");
			}
		}
		return message;
		
	}

	/**
	 * 转账对账记录
	 */
	@Override
	public Message settleTransfer(String number, String startDate,String endDate) {
		Message message=new Message();
		long days=1;
		if(startDate==null||endDate==null||number==null){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收异常");
		}
		SimpleDateFormat paramTime=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date start=null;
		Date end=null;
		
		try {
			start = paramTime.parse(startDate);
			end = paramTime.parse(endDate);
		} catch (ParseException e2) {
			logger.info(e2,e2.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "日期转换异常");
		}
		if(end.getTime()>start.getTime()){
			days=(end.getTime()-start.getTime())/(1000*60*60*24);
		}

		if(days>31){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "对账时间不可以超过31天");
		}
		long date;
		for (long i = 0; i <= days; i++) {
			date=start.getTime()+1000*60*60*24*i;
			try {
				List<UmpSettleTransferRecord>umpSettleTransferRecords=new ArrayList<UmpSettleTransferRecord>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("settle_type_p2p", number);
				map.put("settle_date_p2p", time.format(new Date(date)));

				// 发送到联动优势
				UmpaySignature umpaySignature = new UmpaySignature("download_settle_file_p", map);
				String valueMap = umpaySignature.getSignatureStrng();
				// 对账获得联动返回的文件流
				URL url = new URL(valueMap);
				HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();

				httpUrl.connect();
				InputStream input = new BufferedInputStream(httpUrl.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(input,"gbk"));

				String str;
				while ((str = br.readLine()) != null) {
					if (str.startsWith("TRADEDETAIL")) {
						continue;
					}
					str=str+",";
					String[] split = str.split(",");
					UmpSettleTransferRecord record = new UmpSettleTransferRecord();
					/**
					 * P2P平台请求流水号,P2P平台交易日期,转出方账户号,转入方账户号,金额,支付平台日期,
					 * 支付平台时间, 联动内部机构交易流水,账户日期
					 */
					record.setOrderid(split[0]);
					record.setAccountdate(time.parse(split[8]));//账户日期
					record.setAmount(BigDecimalAlgorithm.div(new BigDecimal(split[4]), new BigDecimal(100)));// 金额
					record.setInaccountid(split[3]);//转入账户号
					record.setMerdate(time.parse(split[1]));//商户订单日期
					record.setOutaccountid(split[2]);//转出账户号
					record.setSettledate(time.parse(split[5]));//对账日期
					record.setSettletime(sdf.parse(split[5]+split[6]));
					record.setTransactionid(split[7]);// 交易流水号
					umpSettleTransferRecords.add(record);
				}
				if(umpSettleTransferRecords.size()!=0){
					umpSettleTransferRecordService.batchDeleteRecord(umpSettleTransferRecords);
					umpSettleTransferRecordService.batchAddRecord(umpSettleTransferRecords);
				}
				br.close();
				//转账对账记录
				addSettleRecord(date,SettleType.TRANSFER,SettleStatus.SUCCESSFUL);
				message.setCode(1);
				message.setMessage("转账对账文件下载成功");
			} catch (Exception e) {
				try {
					addSettleRecord(date,SettleType.TRANSFER,SettleStatus.FAILED);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				logger.info(e,e.fillInStackTrace());
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "转账对账失败，请重试");
			}
		}
		return message;
	}

	/**
	 * 标的转账对账记录
	 */
	@Override
	public Message settleTender(String number, String startDate,String endDate) {
		Message message=new Message();
		long days=1;
		if(startDate==null||endDate==null||number==null){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收异常");
		}
		SimpleDateFormat paramTime=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		Date start=null;
		Date end=null;
		
		try {
			start = paramTime.parse(startDate);
			end = paramTime.parse(endDate);
		} catch (ParseException e2) {
			logger.info(e2,e2.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "日期转换异常");
		}
		if(end.getTime()>start.getTime()){
			days=(end.getTime()-start.getTime())/(1000*60*60*24);
		}

		if(days>31){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "对账时间不可以超过31天");
		}
		long date;
		for (long i = 0; i <= days; i++) {
			date=start.getTime()+1000*60*60*24*i;
			try {
				List<UmpSettleTenderRecord>umpSettleTenderRecords=new ArrayList<UmpSettleTenderRecord>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("settle_type_p2p", number);
				map.put("settle_date_p2p", sdf.format(new Date(date)));

				// 发送到联动优势
				UmpaySignature umpaySignature = new UmpaySignature("download_settle_file_p", map);
				String valueMap = umpaySignature.getSignatureStrng();
				// 对账获得联动返回的文件流
				URL url = new URL(valueMap);
				HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
				
				httpUrl.connect();
				InputStream input = new BufferedInputStream(httpUrl.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(input,"gbk"));

				String str;
				while ((str = br.readLine()) != null) {
					if (str.startsWith("TRADEDETAIL")) {
						continue;
					}
					str=str+",";
					String[] split = str.split(",");
					UmpSettleTenderRecord record = new UmpSettleTenderRecord();
					/**
					 * P2P平台请求流水号,P2P平台交易日期,标的号,付款方账户号,收款方账户号,标的账户号,金额,转账方向（标的转出,标的转入）,
					 * 业务类型（投标,还款等）,支付平台日期,支付平台时间, 联动内部机构交易流水,账户日期
					 */
					record.setOrderid(split[0]);
					record.setAccountdate(sdf.parse(split[12]));//账户日期split[7]
					switch(split[7]){
						case "1":record.setAction(TenderTransferAction.INFLOW);break;//转账方向
						case "2":record.setAction(TenderTransferAction.OUTFLOW);break;//转账方向
					}
					record.setAmount(BigDecimalAlgorithm.div(new BigDecimal(split[6]), new BigDecimal(100)));// 金额
					record.setInaccountid(split[4]);//转入账户号
					record.setMerdate(sdf.parse(split[1]));//商户订单日期
					record.setOutaccountid(split[3]);//转出账户号
					record.setSettledate(sdf.parse(split[9]));//对账日期
					record.setSettletime(time.parse(split[9]+split[10]));//对账时间
					record.setTenderaccountid(split[5]);//标的账户号
					record.setTenderid(split[2]);//标的号
					record.setTransactionid(split[11]);// 交易流水号
					switch(split[8]){
						case "01":record.setTransfertype(TenderTransferType.INVEST);break;
						case "02":record.setTransfertype(TenderTransferType.CREDIT_ASSIGN);break;
						case "03":record.setTransfertype(TenderTransferType.REPAY);break;
						case "04":record.setTransfertype(TenderTransferType.DISBURSE);break;
						case "05":record.setTransfertype(TenderTransferType.DISCOUNT);break;
						case "51":record.setTransfertype(TenderTransferType.FAILED_REFUND);break;
						case "52":record.setTransfertype(TenderTransferType.PLATFORM_CHARGE);break;
						case "53":record.setTransfertype(TenderTransferType.LOAN);break;
						case "54":record.setTransfertype(TenderTransferType.REPAY_REFUND);break;
						case "55":record.setTransfertype(TenderTransferType.DISBURSE_REFUND);break;
						case "56":record.setTransfertype(TenderTransferType.CREDIT_REFUND);break;
						case "57":record.setTransfertype(TenderTransferType.DINVEST_REFUND);break;
					}
					umpSettleTenderRecords.add(record);
				}
				if(umpSettleTenderRecords.size()!=0){
					umpSettleTenderRecordService.batchDeleteRecord(umpSettleTenderRecords);
					umpSettleTenderRecordService.batchAddRecord(umpSettleTenderRecords);
				}
				br.close();
				//标的转账对账记录
				addSettleRecord(date,SettleType.TENDER_TRANSFER,SettleStatus.SUCCESSFUL);
				message.setCode(1);
				message.setMessage("标的转账对账成功");
			} catch (Exception e) {
				try {
					addSettleRecord(date,SettleType.TENDER_TRANSFER,SettleStatus.FAILED);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				logger.info(e,e.fillInStackTrace());
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "标的转账对账失败，请重试");
			}
		}
		return message;	
	}
	
	public void addSettleRecord(long date,SettleType type,SettleStatus status) throws Exception{
		SettleFileRecord settleRecord=new SettleFileRecord();	
		settleRecord.setId(UUID.randomUUID().toString().toUpperCase());
		settleRecord.setApplyTime(new Date());
		settleRecord.setSettleDate(new Date(date));
		settleRecord.setSettleType(type);
		settleRecord.setStatus(status);
		settleFileRecordService.addSettleFileRecord(settleRecord);
	}
		
}
