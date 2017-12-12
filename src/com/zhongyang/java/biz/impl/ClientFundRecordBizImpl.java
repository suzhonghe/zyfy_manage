package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.ClientFundRecordBiz;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpAgreement;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;
import com.zhongyang.java.pojo.UntredtedApplication;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.ClientFundRecordService;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpAgreementService;
import com.zhongyang.java.service.UmpTenderService;
import com.zhongyang.java.service.UmpTenderTransferRecordService;
import com.zhongyang.java.service.UntredtedApplicationService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.ZSession;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.uitl.UploadExcelUtil;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.BusinessToUserTransferVo;
import com.zhongyang.java.vo.ClientFundRecordVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UntredtedApplicationVo;
import com.zhongyang.java.vo.UserInfoVo;
import com.zhongyang.java.vo.fund.BusinessRechargeVo;
import com.zhongyang.java.vo.fund.BusinessWithdrawVo;
import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.fund.OrderTime;
import com.zhongyang.java.vo.fund.PayType;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月7日 下午7:36:56
* 类说明
*/
@Service
public class ClientFundRecordBizImpl implements ClientFundRecordBiz{
	private static Logger logger = Logger.getLogger(ClientFundRecordBizImpl.class);
	@Autowired
	UserService userService;
	
	@Autowired
	ClientFundRecordService clientFundRecordService;
	
	@Autowired
	UmpAgreementService umpAgreementService;
    
	@Autowired
	UmpAccountService  umpAccountService;
	
	@Autowired
	UntredtedApplicationService untredtedApplicationService;
	
	@Autowired
	FundRecordService fundRecordService;
    
	@Autowired
	UserFundService userFundService;
	
	@Autowired
	UmpTenderTransferRecordService umpSTRService;
	
	
	@Autowired
	UmpTenderService umpTenderService;
	
	@Transactional
	public String addClientFundRecord(HttpServletRequest request,String busrechargeAt){
		    ApplicationBean appBean = new ApplicationBean();
			Map<String, Object> sysMap = SystemPro.getProperties();
		    String zycfurlIp = (String) sysMap.get("ZYCFMANAGER_IP");
		    String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
		    String backCode = (String) sysMap.get("ZYCF_BACK_CODE");
		    String orderId = appBean.orderId();
		    this.addClientFundRecordVo(request,busrechargeAt,accountUmp,orderId);
			String umpRechargeUrl = this.businessRechargeUmp(orderId,busrechargeAt,zycfurlIp,backCode,accountUmp);
		return umpRechargeUrl;
	}
	
	private String businessRechargeUmp(String orderId,String busrechargeAt,String zycfurlIp,String backCode,String accountUmp) {
		String withdrawResults = null;
		try {
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			BigDecimal amountfen = bd.yuanTofen(busrechargeAt);//实际要扣除的管理费
			Map map = new HashMap<String, String>();
			OrderTime ot = new OrderTime();
			PayType pType = PayType.B2BBANK;
			map.put("ret_url",   zycfurlIp+"/manage/html/umpreturnMsg/umpReturnMsg.html");
			map.put("notify_url", zycfurlIp+"/businessRechargeCallBack");
			map.put("order_id", orderId);
			map.put("mer_date", ot.getTmeo());
			map.put("pay_type", pType);
			map.put("recharge_mer_id",accountUmp);
			map.put("account_type","01");
			map.put("amount", amountfen);
			map.put("gate_id", backCode);
			map.put("com_amt_type", "2");
			UmpaySignature umpaySignature = new UmpaySignature("mer_recharge", map);
			withdrawResults = umpaySignature.getSignatureStrng();
		} catch (ReqDataException e) {
			logger.info("调用第三方企业充值失败"+orderId);
			e.printStackTrace();
		}
		return withdrawResults;
		
	}
	//平台资金充值记录
	@Transactional
	private int addClientFundRecordVo(HttpServletRequest request,String busrechargeAt,String accountUmp,String orderId) {
		int clientF = 0;
		try {
			Employee emp = (Employee) request.getSession().getAttribute("zycfLoginEmp");
			ClientFundRecord clientFundR = new ClientFundRecord();
			clientFundR.setAmount(new BigDecimal(busrechargeAt));
			ClientFundRecord clientFundRecord = new ClientFundRecord();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			clientFundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			clientFundRecord.setAccount(accountUmp);
			clientFundRecord.setAmount(new BigDecimal(busrechargeAt));
			clientFundRecord.setOrderid(orderId);
			clientFundRecord.setStatus(FundRecordStatus.PROCESSING);
			clientFundRecord.setTimerecorded(new Date());
			clientFundRecord.setType(FundRecordType.DEPOSIT);
			clientFundRecord.setUserid(emp.getId());
			clientFundRecord.setOperation(FundRecordOperation.IN);
			clientFundRecord.setDescription("企业充值");
			clientF = clientFundRecordService.addClientFundRecord(clientFundRecord);
		} catch (Exception e) {
			logger.info("添加平台充值资金记录失败orderId:"+orderId);
			e.printStackTrace();
		}
		return clientF;
		
	}
	@Override
	public String businessRechargeCallBack(BusinessRechargeVo businessRechargeVo) {
		String rechargeResults = null;
		try {
			Map map = new HashMap<String, String>();
			map.put("order_id", businessRechargeVo.getOrder_id());
			map.put("mer_date", businessRechargeVo.getMer_date());
			map.put("ret_code", businessRechargeVo.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(businessRechargeVo.getService(), map);
			rechargeResults = umpaySignature.callBackSignature();
			int up = this.updateClentFundByOrderId(businessRechargeVo);//更新平台充值资金记录状态：
		} catch (ReqDataException | RetDataException e) {
			logger.info("回调第三方的失败"+businessRechargeVo.getOrder_id());
			e.printStackTrace();
		}
		return rechargeResults;
	}
	
	@Transactional
	private int updateClentFundByOrderId(BusinessRechargeVo businessRechargeVo) {
		ClientFundRecord clientFundRecord =new ClientFundRecord();
		int op = 0;
		try {
			if("0000".endsWith(businessRechargeVo.getRet_code())){
				clientFundRecord.setTransactionid(businessRechargeVo.getTrade_no());
				clientFundRecord.setOrderid(businessRechargeVo.getOrder_id());
				clientFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
			}else{
				clientFundRecord.setOrderid(businessRechargeVo.getOrder_id());
				clientFundRecord.setTransactionid(businessRechargeVo.getTrade_no());
				clientFundRecord.setStatus(FundRecordStatus.FAILED);
			}
			op = clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
		} catch (Exception e) {
			logger.info("更新失败平台充值记录状态失败orderId:"+businessRechargeVo.getOrder_id());
			e.printStackTrace();
		}
		return op;
	}
	
	@Transactional
	public Message addClientFundRecordWithdraw(HttpServletRequest request,String merchantWithdraw) {
		Message message = new Message();
		    ApplicationBean appBean = new ApplicationBean();
			Map<String, Object> sysMap = SystemPro.getProperties();
		    String zycfurlIp = (String) sysMap.get("ZYCFMANAGER_IP");
		    String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
		    String backCode = (String) sysMap.get("ZYCF_BACK_CODE");
		    String orderId = appBean.orderId();
			int cc = this.addClientFundRecordWithdrawVo(request,merchantWithdraw,accountUmp,orderId);
			Map<String, String> umpUrl= this.businessWithdrawUmp(orderId,merchantWithdraw,zycfurlIp,backCode,accountUmp);
			String reCode = umpUrl.get("ret_code");
			if("0000" == reCode){
				message.setCode(0000);
				message.setMessage("提现申请成功");
				logger.info(umpUrl+"orderId "+orderId);
			}else{
				message.setCode(0000);
				message.setMessage("提现申请失败");
				logger.info(umpUrl+"orderId "+orderId);
			}
			
		    
		return message;
	}
	//平台资金提现记录
	@Transactional
	private int addClientFundRecordWithdrawVo(HttpServletRequest request,String merchantWithdraw,String accountUmp,String orderId) {
		int clf=0;
		try {
			Employee emp = (Employee) request.getSession().getAttribute("zycfLoginEmp");
			ClientFundRecord clientFundR = new ClientFundRecord();
			clientFundR.setAmount(new BigDecimal(merchantWithdraw));
			ClientFundRecord clientFundRecord = new ClientFundRecord();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			clientFundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			clientFundRecord.setAccount(accountUmp);
			clientFundRecord.setAmount(new BigDecimal(merchantWithdraw));
			clientFundRecord.setOrderid(orderId);
			clientFundRecord.setStatus(FundRecordStatus.PROCESSING);
			clientFundRecord.setTimerecorded(new Date());
			clientFundRecord.setType(FundRecordType.WITHDRAW);
			clientFundRecord.setUserid(emp.getId());
			clientFundRecord.setOperation(FundRecordOperation.OUT);
			clientFundRecord.setDescription("企业提现");
			clf =clientFundRecordService.addClientFundRecord(clientFundRecord);
		} catch (Exception e) {
			logger.info("添加平台充值资金记录失败orderId:"+orderId);
			e.printStackTrace();
		}
		return clf;
		
	}
	//商户向用户转账：//可用余额：￥169.91           账户余额：￥169.91  冻结余额：￥0.00
	public BusinessToUserTransferVo businessToUserTransfer() {
		//1,调用第三方查询账户余额
		BusinessToUserTransferVo bToUTferVo = new BusinessToUserTransferVo();
		try {
			Map<String, String> withdrawResultss = null;
			Map<String, Object> sysMap = SystemPro.getProperties();
		    String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
		    String backCode = (String) sysMap.get("ZYCF_BACK_CODE");
			Map map = new HashMap<String, String>();
			map.put("query_mer_id",accountUmp);
			map.put("account_type", "01");
			UmpaySignature umpaySignature = new UmpaySignature("ptp_mer_query", map);
			withdrawResultss = umpaySignature.getSignature();
	
			ZSession zsession = ZSession.getzSession();
			Map<String,Object> sessionMap= (Map<String, Object>) zsession.getzSessionMap();
			sessionMap.put("umpBalance",withdrawResultss.get("balance"));
			zsession.setzSessionMap(sessionMap);
			BigDecimalAlgorithm algorithm = new BigDecimalAlgorithm();
			BigDecimal balance = new BigDecimal(0.00);
			if(null !=withdrawResultss.get("balance")){
			    bToUTferVo.setAvailableAmount(algorithm.fenToyuan(withdrawResultss.get("balance")).toString());//可用户余额
			}else{
			  bToUTferVo.setAvailableAmount("0");
			}
			BigDecimal auditingAmount = this.getClientFundRecordByStatus();////审核中(为冻结的额度)
			BigDecimal accountAmount = auditingAmount.add(algorithm.fenToyuan(withdrawResultss.get("balance")));//账户余额
			bToUTferVo.setBalanceAmount(accountAmount.toString());
			bToUTferVo.setFrozenAmount(auditingAmount.toString());
			List<UntredtedApplicationVo> listApp =this.getBusinessApplication();//申请记录
			bToUTferVo.setApplications(listApp);
		} catch (ReqDataException | RetDataException e) {
			logger.info("查询企业账户余额失败:"+e);
			e.printStackTrace();
		}
		return bToUTferVo;
		}
	private List<UntredtedApplicationVo> getBusinessApplication() {
		List<UntredtedApplicationVo> untredtedApplicationList= new ArrayList<>();
		
		try {
			UntredtedApplication untredteda = new UntredtedApplication();
			untredteda.setStatus(FundRecordStatus.AUDITING);
			List<UntredtedApplication> untredtedApplicationListObg= new ArrayList<>();
			untredtedApplicationListObg = untredtedApplicationService.getUntredtedApplicationByStutas(untredteda);
            if(untredtedApplicationListObg.size() >0) {
            	for(int i = 0;i < untredtedApplicationListObg.size();i++){
            		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
            		UntredtedApplicationVo untredtedApplicationVo = new UntredtedApplicationVo();
            		String telephones = untredtedApplicationListObg.get(i).getTelephone();
            		String telephone = cipher.decrypt(telephones);
            		untredtedApplicationVo.setTelephone(telephone);
            		untredtedApplicationVo.setAccount(untredtedApplicationListObg.get(i).getAccount());
            		Date dd = untredtedApplicationListObg.get(i).getCreateTime();
            		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            		String cc = df.format(dd);
            		untredtedApplicationVo.setCreateTime(cc);
            		untredtedApplicationVo.setOrdreId(untredtedApplicationListObg.get(i).getOrdreId());
            		untredtedApplicationVo.setDescription(untredtedApplicationListObg.get(i).getDescription());
            		untredtedApplicationVo.setId(untredtedApplicationListObg.get(i).getId());
            		untredtedApplicationVo.setOperationEmplyee(untredtedApplicationListObg.get(i).getOperationEmplyee());
            		untredtedApplicationVo.setStatus(untredtedApplicationListObg.get(i).getStatus().getKey());
            		untredtedApplicationVo.setTradeAmount(untredtedApplicationListObg.get(i).getTradeAmount());
            		untredtedApplicationVo.setUserName(untredtedApplicationListObg.get(i).getUserName());
            		untredtedApplicationList.add(untredtedApplicationVo);
            	}
            }
		} catch (Exception e) {
			logger.info("查询企业申请失败:"+e);
			e.printStackTrace();
		}
		return untredtedApplicationList;
	}
	private BigDecimal getClientFundRecordByStatus() {
		BigDecimal auditingAmount = new BigDecimal(0.00);//审核中(为冻结的额度)
		ClientFundRecord clientFundRecord = new ClientFundRecord();
		try {
			clientFundRecord.setStatus(FundRecordStatus.AUDITING);
			ClientFundRecord cfr = clientFundRecordService.getClientFundRecordByStatusAmountSum(clientFundRecord);
			if(null !=cfr){
				auditingAmount = cfr.getAmount();
			}
		} catch (Exception e) {
			logger.info("查询状态为：审核中(AUDITING)失败"+e);
			e.printStackTrace();
		}
		return auditingAmount;
	}
	
	public UserInfoVo getUserTransfer(String mobile) {
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		UserInfoVo  userInfoVo= new UserInfoVo();
		UmpAccount umpAccount = new UmpAccount();
		try {
			String mobiles = cipher.encrypt(mobile);
			User users= userService.getUserByMobile(mobiles);
			if(null != users){
				umpAccount.setUserId(users.getId());
				umpAccount = umpAccountService.getUmpAccountByUserId(umpAccount);
				if(null != umpAccount.getAccountId()){
					userInfoVo.setAuthentication("1");//未认证0;已认证1
				}else{
					userInfoVo.setAuthentication("0");//未认证0;已认证
				}
				UmpAgreement umpAgreement= new UmpAgreement();
				umpAgreement.setUserid(users.getId());
				UmpAgreement umpAgreementobj = umpAgreementService.byUserUmpAgreement(umpAgreement);
				
				String idnumber = cipher.decrypt(users.getIdnumber());//身份证号
				userInfoVo.setIdnumber(idnumber);
				userInfoVo.setId(users.getId());//userId
				userInfoVo.setLoginname(users.getName());//登录名
				userInfoVo.setMobile(mobile);//电话号码
				userInfoVo.setName(users.getName());//姓名
				if(true ==umpAgreementobj.getRepay()){
					userInfoVo.setNoRepaymentaGreement("1");
				}else{
					userInfoVo.setNoRepaymentaGreement("0");
				}
				
			}
		} catch (Exception e) {
			logger.info("查询用户失败mobile:"+mobile+"--"+e);
			e.printStackTrace();
		}
		return userInfoVo;
	}
	@Override
	@Transactional
	public Message createApplication(HttpServletRequest request,String userId, String userName,String transAmount, String description) {
		Message message = new Message();
		try {
			if(userId==null){
				message.setMessage("userId为空");
				return message;
			}
			Employee emp = (Employee) request.getSession().getAttribute("zycfLoginEmp");
		
			Map<String, Object> sysMap = SystemPro.getProperties();
		    String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");//平台账户号
			UntredtedApplication untredtedApplication = new UntredtedApplication();
			OrderTime or = new OrderTime();
			ApplicationBean appBean = new ApplicationBean();
			String orderId = appBean.orderId();
			User user  = this.getUserByUserId(userId);//查询用户信息
			untredtedApplication.setId(UUID.randomUUID().toString().toUpperCase());
			untredtedApplication.setTradeAmount(new BigDecimal(transAmount));
			untredtedApplication.setDescription(description);
			untredtedApplication.setAccount(accountUmp);
			untredtedApplication.setUserName(user.getName());
			untredtedApplication.setTelephone(user.getMobile());
			untredtedApplication.setCreateTime(or.getLastUpdateTime());
			untredtedApplication.setStatus(FundRecordStatus.AUDITING);
			untredtedApplication.setOperationEmplyee(emp.getName());//当前登录用户------------------------
			untredtedApplication.setUserId(userId);
			untredtedApplication.setOrdreId(orderId);
			int cc = untredtedApplicationService.addUntredtedApplication(untredtedApplication);
			if(cc !=0){
				message.setCode(0);//0失败
			}else{
				message.setCode(1);//1成功
			}
		} catch (Exception e) {
			logger.info("创建转账申请失败:"+e);
			e.printStackTrace();
		}
		return message;
	}
	private User getUserByUserId(String userId) {
		User user = new User();
		try {
			user.setId(userId);
			user= userService.getUserById(user);
			return user;
		} catch (Exception e) {
			logger.info("查询用户失败userId"+userId);
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "账户查询失败");
		}
		
	}
	
	@SuppressWarnings("unused")
	public Message businessToUserTransferUmp(String appIdli) {
		ZSession zsession = ZSession.getzSession();
        Map<String,Object> sessionMap= (Map<String, Object>) zsession.getzSessionMap();
		Message message = new Message();
		UntredtedApplication  uApplication = this.getUntredtedApplicationById(appIdli);//1，获得申请记录；2,创建一条平台资金记录；3,调用联动转账；4,修改资金状态；5,删除当前申请记录
		UmpAccount umpAccount = this.getUmpAccountByUserId(uApplication.getUserId());//获得用户UMP账户信息
		
		BigDecimal umpBalance = new BigDecimal((String)sessionMap.get("umpBalance"));
		BigDecimal umpBalancef = umpBalance.divide(new BigDecimal(100));
		sessionMap.remove(sessionMap.get("umpBalance"));
		
		if(uApplication==null){
			message.setCode(1075);
			message.setMessage("未获得申请记录");
			return message;
		}
		if(umpBalancef.compareTo(uApplication.getTradeAmount())<1){
			message.setCode(1074);
			message.setMessage("转账额度不能大于平台可用额度");
			return message;
		}
        String status = (String)sessionMap.get(uApplication.getId());
		if("AUDITING".equals(status)){
			message.setCode(1076);
			message.setMessage("该笔转款正在审核中，请勿重复操作");
			return message;
		}
		else{
			sessionMap.put(uApplication.getId(), "AUDITING");
			zsession.setzSessionMap(sessionMap);
		}
		int cfd =  this.addClientFundRecordOutVo(uApplication);//创建一条平台资金记录
		
		uApplication.setUserUmpAccount(umpAccount.getAccountName());
		Map<String, String>  reInfo= toUserTransferUmp(uApplication);//调用联动转账
		String recode = reInfo.get("ret_code");
		if(recode !="0000"){
			message.setMessage("转账申请失败");
		}else{
			message.setCode(0000);
			message.setMessage("转账申请成功");
		}
		return message;
	}
	private UmpAccount getUmpAccountByUserId(String userId) {
		UmpAccount umpAccount = new UmpAccount();
		try {umpAccount.setUserId(userId);
			umpAccount = umpAccountService.getUmpAccountByUserId(umpAccount);
		} catch (Exception e) {
			logger.info("查询用户ump账户信息失败userId："+userId);
			e.printStackTrace();
		}
		return umpAccount;
		
	}
	private Map<String, String>  toUserTransferUmp(UntredtedApplication  uApplication) {
		Map<String, String> withdrawResultss = null;
			try {
				BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
				BigDecimal amountfen = bd.yuanTofen(uApplication.getTradeAmount().toString());//实际要扣除的管理费
				Map map = new HashMap<String, String>();
				OrderTime ot = new OrderTime();
				Map<String, Object> sysMap = SystemPro.getProperties();
			    String zycfurlIp = (String) sysMap.get("ZYCFMANAGER_IP");
			    String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
			    String backCode = (String) sysMap.get("ZYCF_BACK_CODE");
				map.put("notify_url", zycfurlIp+"/businessToUserTransferUmpCallBack");
				map.put("order_id", uApplication.getOrdreId());
				map.put("mer_date", ot.getTmeo());
				map.put("mer_account_id",accountUmp);
				map.put("partic_acc_type", "01");
				map.put("trans_action","02");
				map.put("partic_user_id", uApplication.getUserUmpAccount());
				map.put("amount", amountfen);
				UmpaySignature umpaySignature = new UmpaySignature("transfer", map);
				withdrawResultss = umpaySignature.getSignature();
			} catch (ReqDataException | RetDataException e) {
				logger.info("调用第三方给用户转账失败");
				e.printStackTrace();
			}
			return withdrawResultss;

		
	}
	@Transactional
	private int addClientFundRecordOutVo(UntredtedApplication uApplication) {
		ClientFundRecord clientFundRecord = new ClientFundRecord();
		int clf = 0;
		try {
			clientFundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			clientFundRecord.setAccount(uApplication.getAccount());
			clientFundRecord.setAmount(uApplication.getTradeAmount());
			clientFundRecord.setDescription(uApplication.getDescription());
			clientFundRecord.setOperation(FundRecordOperation.OUT);
			clientFundRecord.setStatus(FundRecordStatus.AUDITING);
			clientFundRecord.setTimerecorded(uApplication.getCreateTime());
			clientFundRecord.setType(FundRecordType.TRANSFER);
			clientFundRecord.setOrderid(uApplication.getOrdreId());
			clientFundRecord.setUserid(uApplication.getUserId());
			clf =clientFundRecordService.addClientFundRecord(clientFundRecord);
		} catch (Exception e) {
			logger.info("把申请记录复制到平台资金记录失败"+uApplication.getOrdreId());
			e.printStackTrace();
		}
		return clf;
	}
	private UntredtedApplication getUntredtedApplicationById(String appId) {
		UntredtedApplication untredtedApplication = new UntredtedApplication();
		try {
			
			Pattern pattern = Pattern.compile("[0-9]*"); 
			Matcher isNum = pattern.matcher(appId);
                if(isNum.matches()){
            	    untredtedApplication.setOrdreId(appId);
				}else{
					untredtedApplication.setId(appId);
				} 
			untredtedApplication = untredtedApplicationService.getUntredtedApplicationById(untredtedApplication);
			
		} catch (Exception e) {
			logger.info("通过id查询申请记录失败appId："+appId);
			e.printStackTrace();
		}
		return untredtedApplication;
		
	}
	@Override
	public String businessToUserTransferUmpCallBack(BusinessWithdrawVo businessWithdrawVo) {
		String rechargeResults = null;
		try {
			
			Map map = new HashMap<String, String>();
			map.put("order_id", businessWithdrawVo.getOrder_id());
			map.put("mer_date", businessWithdrawVo.getMer_date());
			map.put("ret_code", businessWithdrawVo.getRet_code());
			map.put("trade_no", businessWithdrawVo.getTrade_no());
			UmpaySignature umpaySignature = new UmpaySignature(businessWithdrawVo.getService(), map);
			rechargeResults = umpaySignature.callBackSignature();
	        this.updateClentFundByOrderIdWithdrawVo(businessWithdrawVo);//更新平台资金记录状态：
		} catch (ReqDataException | RetDataException e) {
			logger.info("回调第三方失败");
			e.printStackTrace();
		}
		return rechargeResults;
		
	}

		
	private Map<String, String> businessWithdrawUmp(String orderId,String merchantWithdraw,String zycfurlIp,String backCode,String accountUmp) {
		Map<String, String> withdrawResultss = null;
		try {
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			 BigDecimal amountfen = bd.yuanTofen(merchantWithdraw);//实际要扣除的管理费
			Map map = new HashMap<String, String>();
			OrderTime ot = new OrderTime();
			map.put("notify_url", zycfurlIp+"/manage/businessWithdrawCallBack");
			map.put("apply_notify_flag", 1);
			map.put("order_id", orderId);
			map.put("mer_date", ot.getTmeo());
			map.put("withdraw_mer_id",accountUmp);
			map.put("amount", amountfen);
			UmpaySignature umpaySignature = new UmpaySignature("mer_withdrawals", map);
			withdrawResultss = umpaySignature.getSignature();
		} catch (ReqDataException | RetDataException e) {
			logger.info("调用第三方企业充值失败"+orderId);
			e.printStackTrace();
		}
		return withdrawResultss;
	}
	//企业提现回调
	public String businessWithdrawCallBack(BusinessWithdrawVo businessWithdrawVo) {
		String rechargeResults = null;
		try {
			Map map = new HashMap<String, String>();
			map.put("order_id", businessWithdrawVo.getOrder_id());
			map.put("mer_date", businessWithdrawVo.getMer_date());
			map.put("ret_code", businessWithdrawVo.getRet_code());
			UmpaySignature umpaySignature = new UmpaySignature(businessWithdrawVo.getService(), map);
			rechargeResults = umpaySignature.callBackSignature();
			int up = this.updateClentFundWithdrawVo(businessWithdrawVo);//更新平台资金记录：
		} catch (ReqDataException | RetDataException e) {
			e.printStackTrace();
		}
		return rechargeResults;
		
	}
	@Transactional
	private int updateClentFundWithdrawVo(BusinessWithdrawVo businessWithdrawVo) {
		ClientFundRecord clientFundRecord =new ClientFundRecord();
		int op = 0;
		try {
			clientFundRecord.setOrderid(businessWithdrawVo.getOrder_id());
			clientFundRecord = clientFundRecordService.getClentFundByOrderId(clientFundRecord);
			if("0000".endsWith(businessWithdrawVo.getRet_code())){
				if(FundRecordStatus.PROCESSING.equals(clientFundRecord.getStatus())){
					clientFundRecord.setStatus(FundRecordStatus.AUDITING);
				}else{
					clientFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				}
				clientFundRecord.setOrderid(businessWithdrawVo.getOrder_id());
				clientFundRecord.setTransactionid(businessWithdrawVo.getTrade_no());
			}else{
				clientFundRecord.setOrderid(businessWithdrawVo.getOrder_id());
				clientFundRecord.setTransactionid(businessWithdrawVo.getTrade_no());
				clientFundRecord.setStatus(FundRecordStatus.FAILED);
			}
			op = clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
		} catch (Exception e) {
			logger.info("更新失败平台充值记录状态失败orderId:"+businessWithdrawVo.getOrder_id());
			e.printStackTrace();
		}
		return op;
	}
	
	@Transactional
	private void updateClentFundByOrderIdWithdrawVo(BusinessWithdrawVo businessWithdrawVo) {
		ClientFundRecord clientFundRecord =new ClientFundRecord();
		int op = 0;
		try {
			clientFundRecord.setOrderid(businessWithdrawVo.getOrder_id());
			clientFundRecord = clientFundRecordService.getClentFundByOrderId(clientFundRecord);
			if(FundRecordStatus.SUCCESSFUL.equals(clientFundRecord.getStatus())){
				return;
			}else{
				UntredtedApplication untredtedApplication = new UntredtedApplication();
				if("0000".endsWith(businessWithdrawVo.getRet_code())){
					if(FundRecordStatus.AUDITING.equals(clientFundRecord.getStatus())){
						clientFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
						this.addFundRecord(businessWithdrawVo.getOrder_id());//添加用户资金记录
						UntredtedApplication uApplication= this.getUntredtedApplicationById(businessWithdrawVo.getOrder_id());
						this.updateUserFund(uApplication);//更改用户账户
						String orderId = businessWithdrawVo.getOrder_id();
						untredtedApplication.setStatus(FundRecordStatus.SUCCESSFUL);
						untredtedApplication.setOrdreId(orderId);
						this.deleteUntredtedApplicationBy(untredtedApplication);//物理删除已经批准的申请记录
					}
					clientFundRecord.setOrderid(businessWithdrawVo.getOrder_id());
					clientFundRecord.setTransactionid(businessWithdrawVo.getTrade_no());
					op = clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
				}else{
					clientFundRecord.setOrderid(businessWithdrawVo.getOrder_id());
					clientFundRecord.setTransactionid(businessWithdrawVo.getTrade_no());
					clientFundRecord.setStatus(FundRecordStatus.FAILED);
					op = clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
					untredtedApplication.setStatus(FundRecordStatus.FAILED);
					untredtedApplication.setOrdreId(businessWithdrawVo.getOrder_id());
					this.deleteUntredtedApplicationBy(untredtedApplication);//物理删除已经批准的申请记录
				}
				ZSession zsession = ZSession.getzSession();
				Map<String, Object> sessionMap = zsession.getzSessionMap();
				sessionMap.remove(untredtedApplication.getId());
			}
			
		} catch (Exception e) {
			logger.info("更新失败平台充值记录状态失败orderId:"+businessWithdrawVo.getOrder_id());
			e.printStackTrace();
		}
	}
	
	@Transactional
	private void deleteUntredtedApplicationBy(UntredtedApplication untredtedApplication) {
		try {
			Pattern pattern = Pattern.compile("[0-9]*"); 
			Matcher isNum = pattern.matcher(untredtedApplication.getOrdreId());
                if(isNum.matches()){
            	    untredtedApplication.setOrdreId(untredtedApplication.getOrdreId());
				}else{
					untredtedApplication.setId(untredtedApplication.getOrdreId());
				} 
			untredtedApplicationService.deleteUntredtedApplicationByOrderId(untredtedApplication);
		} catch (Exception e) {
			logger.info("删除平台转账审批");
			e.printStackTrace();
		}
			
		}
    @Transactional
	private void updateUserFund(UntredtedApplication uApplication) {
		UserFund userFund = new UserFund();
		try {
			userFund.setUserid(uApplication.getUserId());
			UserFund userFunds = userFundService.byUserID(userFund);
			if(null !=userFunds){
				userFund.setUserid(userFunds.getUserid());
				userFund.setAvailableAmount(uApplication.getTradeAmount());
			}else{
				userFund.setUserid(uApplication.getUserId());
				userFund.setAvailableAmount(uApplication.getTradeAmount());
			}
			userFundService.modifyUserFund(userFund);
		} catch (Exception e) {
			logger.info("查询用户失败||修改用户资金账户余额失败userId："+uApplication.getUserId());
			e.printStackTrace();
		}
		
	}
    @Transactional
	private void addFundRecord(String orderId) {
		FundRecord fundRecord = new FundRecord();
		ClientFundRecord cf = new ClientFundRecord();
		UntredtedApplication uApplication= this.getUntredtedApplicationById(orderId);
		try {
			fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			fundRecord.setFundAccountId(uApplication.getAccount());
			fundRecord.setAmount(uApplication.getTradeAmount());
			fundRecord.setDescription(uApplication.getDescription());
			fundRecord.setOperation(FundRecordOperation.IN);
			fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
			fundRecord.setTimerecorded(uApplication.getCreateTime());
			fundRecord.setType(FundRecordType.TRANSFERIN);
			fundRecord.setOrderid(uApplication.getOrdreId());
			fundRecord.setUserId(uApplication.getUserId());
			fundRecordService.addFundRecord(fundRecord);
		} catch (Exception e) {
			logger.info("把申请记录复制到个人资金记录失败"+uApplication.getOrdreId());
			e.printStackTrace();
		}	
		
	}
	
	public Message businessAmountToloanAccount(String loanId,String amount,String description) {
		Message message = new Message();
		BigDecimal trAmount = new BigDecimal(amount);
		ApplicationBean appBean = new ApplicationBean();
		String orderId = appBean.orderId();
		this.addClientFundRecordToLaonAccount(orderId, trAmount,loanId,description);
		Map<String, String> recode = this.businessAmountTOloanAccountUMP(loanId, orderId, trAmount);
		String code = recode.get("ret_code");
			if("0000".endsWith(code)){
				message.setCode(0000);
				message.setMessage("转账成功");
			}
		return message;
	}
	
	private Map<String, String> businessAmountTOloanAccountUMP(String loanId,String orderId,BigDecimal amount) {
	    Map<String, String> withdrawResults = null;
		try {
			 BigDecimal dj = new BigDecimal(100.00);
			 BigDecimal amounttr = amount.multiply(dj).setScale(0, BigDecimal.ROUND_UP);
			 OrderTime ot = new OrderTime();
			 BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			 Map<String, Object> sysMap = SystemPro.getProperties();
		     String zycfurlIp = (String) sysMap.get("ZYCFMANAGER_IP");
		     String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
			 String projectId = loanId.replace("-", "");
				Map map = new HashMap<String, String>();
				map.put("notify_url", zycfurlIp+"/businessAmountToloanAccountCallBack");
				map.put("order_id", orderId);
				map.put("mer_date", ot.getTmeo());
				map.put("project_id", projectId);
				map.put("serv_type","04");
				map.put("trans_action", "01");
				map.put("partic_type", "03");
				map.put("partic_acc_type", "02");
				map.put("partic_user_id", accountUmp);
				map.put("amount", amounttr);
				UmpaySignature umpaySignature = new UmpaySignature("project_transfer", map);
			withdrawResults = umpaySignature.getSignature();
		} catch (ReqDataException | RetDataException e) {
			logger.info("调用第三方扣除管理费接口失败orderId："+orderId);
			e.printStackTrace();
		}
		return withdrawResults;
}
	@Transactional
	private void addClientFundRecordToLaonAccount(String orderId,BigDecimal amount,String loanId,String description) {
		try {
			Map<String, Object> sysMap = SystemPro.getProperties();
		    String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
			ClientFundRecord clientFundRecord = new ClientFundRecord();
			BigDecimalAlgorithm bd = new BigDecimalAlgorithm();
			OrderTime ot = new OrderTime();
			clientFundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			clientFundRecord.setAccount(accountUmp);
			clientFundRecord.setAmount(amount);
			clientFundRecord.setOrderid(orderId);
			clientFundRecord.setStatus(FundRecordStatus.PROCESSING);
			clientFundRecord.setTimerecorded(ot.getLastUpdateTime());
			clientFundRecord.setType(FundRecordType.TRANSFER);
			clientFundRecord.setOperation(FundRecordOperation.OUT);
			clientFundRecord.setDescription("转账到标的账户");
			clientFundRecordService.addClientFundRecord(clientFundRecord);
			this.addUmpTenderTransferRecord(clientFundRecord,loanId);
		} catch (Exception e) {
			logger.info("添加平台资金记录失败"+orderId);
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加平台资金记录失败");
		}
	}

	public String businessAmountToloanAccountCallBack(BusinessWithdrawVo businessWithdrawVo) {
		String rechargeResults = null;
		try {
			
			Map map = new HashMap<String, String>();
			map.put("order_id", businessWithdrawVo.getOrder_id());
			map.put("mer_date", businessWithdrawVo.getMer_date());
			map.put("ret_code", businessWithdrawVo.getRet_code());
			map.put("trade_no", businessWithdrawVo.getTrade_no());
			UmpaySignature umpaySignature = new UmpaySignature(businessWithdrawVo.getService(), map);
			rechargeResults = umpaySignature.callBackSignature();
	         this.updateClentFundByOrderId(businessWithdrawVo);//更新平台充值资金记录状态：
		} catch (ReqDataException | RetDataException e) {
			logger.info("更新平台充值资金记录状态");
			e.printStackTrace();
		}
		return rechargeResults;
		
	}
    
	@Transactional
	private void updateClentFundByOrderId(BusinessWithdrawVo businessWithdrawVo) {
		ClientFundRecord clientFundRecord = new ClientFundRecord();
		int op = 0;
		try {
			clientFundRecord.setOrderid(businessWithdrawVo.getOrder_id());
			String orderId = businessWithdrawVo.getOrder_id();
			UmpTenderTransferRecord  umpTenderTransferRecord = this.getUmpTenderTransferRecord(orderId);//查询UmpTenderTransferRecord
			
			clientFundRecord = clientFundRecordService.getClentFundByOrderId(clientFundRecord);
			String status = umpTenderTransferRecord.getStatus().toString();
			if("0000".equals(businessWithdrawVo.getRet_code())){
				if("SUCCESSFUL".endsWith(status)){
					return;
				}else{
					clientFundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
					op = clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
					umpTenderTransferRecord.setStatus(FundRecordStatus.SUCCESSFUL);
					this.updateUmpTenderTransferRecord(umpTenderTransferRecord);
					this.updateUmpTender(umpTenderTransferRecord);
				}
			}else{
				clientFundRecord.setStatus(FundRecordStatus.FAILED);
				op = clientFundRecordService.updateClentFundByOrderId(clientFundRecord);
			}
		} catch (Exception e) {
			logger.info("修改平台账户记录失败"+clientFundRecord.getOrderid());
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "添加平台资金记录失败");
		}
	}
	
	@Transactional
	private void updateUmpTender(UmpTenderTransferRecord  umpTenderTransferRecord) {
		UmpTender umpTender = new UmpTender();
		 try {
			 String loanId = umpTenderTransferRecord.getLoanid();
			 umpTender = umpTenderService.getUmpTender(loanId);
			 umpTender.setAmount(umpTender.getAmount().add(umpTenderTransferRecord.getAmount()));
			 umpTender.setLoanid(umpTenderTransferRecord.getLoanid());
			 umpTenderService.updateAmound(umpTender);
		} catch (Exception e) {
			logger.info("查询标的账户信息失败"+umpTender.getLoanid());
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未知异常，请重试");
		}
		
	}

	private UmpTenderTransferRecord getUmpTenderTransferRecord(String orderId) {
		UmpTenderTransferRecord utfr = new UmpTenderTransferRecord();
		try {
			utfr = umpSTRService.getUmpTenderTransferRecord(orderId);
		} catch (Exception e) {
			logger.info("查询标的账户资金记录状态失败"+orderId);
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未知异常，请重试");
		}
		return utfr;
	}
	
	@Transactional
	private void updateUmpTenderTransferRecord(UmpTenderTransferRecord  umpTenderTransferRecord) {
		UmpTenderTransferRecord utdf = new UmpTenderTransferRecord();
		try {
			utdf.setStatus(FundRecordStatus.SUCCESSFUL);
			utdf.setLoanid(umpTenderTransferRecord.getOrderid());
			umpSTRService.modifyUmpTenderTransferRecord(utdf);
		} catch (Exception e) {
			logger.info("修改标的账户资金记录状态失败"+umpTenderTransferRecord.getOrderid());
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未知异常，请重试");
		}
	}
    @Transactional
	private void addUmpTenderTransferRecord(ClientFundRecord clientFundRecord,String loanId) {
		UmpTenderTransferRecord  umpTenderTransferRecord= new UmpTenderTransferRecord();
		try {
			OrderTime or = new OrderTime();
			umpTenderTransferRecord.setAmount(clientFundRecord.getAmount());
			umpTenderTransferRecord.setLoanid(loanId);
			umpTenderTransferRecord.setOrderid(clientFundRecord.getOrderid());
			umpTenderTransferRecord.setTransfertype(FundRecordType.TRANSFERIN);
			umpTenderTransferRecord.setMerdate(new Date());
			umpTenderTransferRecord.setTenderaction(FundRecordOperation.IN);
			umpTenderTransferRecord.setStatus(FundRecordStatus.PROCESSING);
			umpTenderTransferRecord.setTimecreated(or.getLastUpdateTime());
			umpTenderTransferRecord.setTimelastupdated(or.getLastUpdateTime());
			umpSTRService.addUmpTenderTransferRecord(umpTenderTransferRecord);
		} catch (Exception e) {
			logger.info("平台转账到标的账户失败"+loanId);
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未知异常，请重试");
		}
	}
	@Override
	public ClientFundRecordVo getClientFundRecordInfo(HttpServletRequest request, ClientFundRecordVo clientFundRecordVo) {
		ClientFundRecordVo clientFundRecordVoc = new ClientFundRecordVo();
		ClientFundRecordVo clientFundRecord = new ClientFundRecordVo();
		try {
			clientFundRecord = this.getTransferAmount();//平台资金：可用，冻结，账户余额
			clientFundRecordVoc.setAvailableAmount(clientFundRecord.getAvailableAmount());
			clientFundRecordVoc.setBalanceAmount(clientFundRecord.getBalanceAmount());
			clientFundRecordVoc.setFrozenAmount(clientFundRecord.getFrozenAmount());
		} catch (Exception e) {
			logger.info("查询平台可以用资金失败");
			e.printStackTrace();
		}
		return clientFundRecordVoc;
	}

	private ClientFundRecordVo getTransferAmount() {
		//1,调用第三方查询账户余额
		ClientFundRecordVo clientFundRecordVoc = new ClientFundRecordVo();
			try {
				Map<String, String> withdrawResultss = null;
				Map<String, Object> sysMap = SystemPro.getProperties();
			    String accountUmp = (String) sysMap.get("ZYCF_UMP_ACCOUNT");
				Map map = new HashMap<String, String>();
				map.put("query_mer_id",accountUmp);
				map.put("account_type", "01");
				UmpaySignature umpaySignature = new UmpaySignature("ptp_mer_query", map);
				withdrawResultss = umpaySignature.getSignature();
				BigDecimalAlgorithm algorithm = new BigDecimalAlgorithm();
				BigDecimal balance = new BigDecimal(0.00);
				if(null !=withdrawResultss.get("balance")){
					clientFundRecordVoc.setAvailableAmount(algorithm.fenToyuan(withdrawResultss.get("balance")).toString());//可用户余额
				}else{
					clientFundRecordVoc.setAvailableAmount("0");
				}
				BigDecimal auditingAmount = this.getClientFundRecordByStatus();////审核中(为冻结的额度)
				BigDecimal accountAmount = auditingAmount.add(algorithm.fenToyuan(withdrawResultss.get("balance")));//账户余额
				clientFundRecordVoc.setBalanceAmount(accountAmount.toString());
				clientFundRecordVoc.setFrozenAmount(auditingAmount.toString());
			} catch (ReqDataException | RetDataException e) {
				e.printStackTrace();
			}
			return clientFundRecordVoc;

	}

	private User getUser(String mobile) {
		User user = new User();
		return user = userService.getUserByMobile(mobile);
	}
    @Transactional
	public Message deleteApplication(HttpServletRequest request, String appIdli) {
		    Message message = new Message();
			int cc = 0;
			try {
				cc = untredtedApplicationService.deleteApplicationId(appIdli);
				if(cc == 0){
					message.setCode(0000);
				}else{
					message.setMessage("删除失败");
				}
			} catch (Exception e) {
				logger.info(e);
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未知异常，请重试");
			}
		return message;
	}
	@Override
	public PagerVO<ClientFundRecordVo> getClientFundRecord(HttpServletRequest request, PagerVO<FundRecord>pager) {
		System.out.println(System.currentTimeMillis()+"--开始时间");
		PagerVO<ClientFundRecordVo> clientFundRecordVoc=new PagerVO<ClientFundRecordVo>();
		List<ClientFundRecord>  fundRecordlist =  new ArrayList<ClientFundRecord>();
		List<ClientFundRecordVo>  clientFundRecordVo =  new ArrayList<ClientFundRecordVo>();
			try {
				Page<ClientFundRecord>page=new Page<ClientFundRecord>();
				if(pager.getStart()!=0){
					page.setPageNo(pager.getStart());
				}
				else{
					page.setPageNo(1);
				}
				if(pager.getLength()!=0){
					page.setPageSize(pager.getLength());
				}
				else{
					page.setPageSize(5);
				}
				 DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		        Pattern pattern = Pattern.compile("[0-9]*"); 
				if(pager.getField()!=null&&pager.getValue()!=null){
					String[] fileds = pager.getField().split(",");
					String[] values = pager.getValue().split(",");
					for (int i = 0; i < fileds.length; i++) {
						 if("xxx".equals(values[i]))continue;
						 if("all".equals(values[i]))continue;
		                   Matcher isNum = pattern.matcher(values[i]);
		                   if(isNum.matches()){
		                	   User user = this.getUser(cipher.encrypt(values[i]));
								 page.getParams().put("userId",user.getId());
							} 
							else{
								page.getParams().put("type",values[i]);
							}
						page.getParams().put(fileds[i],values[i]);
					}
				}
				if(pager.getSort()!=null){
					page.getParams().put("sort",pager.getSort());
				}
				else{
					page.getParams().put("sort","desc");
				}
				Map<String, Object> params = page.getParams();
				if(pager.getStartTime()!=0){
					params.put("startTime", new Date(pager.getStartTime()));
					page.setStartTime(new Date(pager.getStartTime()));
				}
				if(pager.getEndTime()!=0){
					params.put("endTime", new Date(pager.getEndTime()));
					page.setEndTime(new Date(pager.getEndTime()));
				}
				
				int count = clientFundRecordService.getClientFundRecordCount(params);
				int totalPage;
				if (count % page.getPageSize() == 0) {
					totalPage = count / page.getPageSize();
				} else {
					totalPage = (count / page.getPageSize()) + 1;
				}
				
				fundRecordlist = clientFundRecordService.getClientFundRecord(page);	
				OrderTime od = new OrderTime();
				Map<String, Object> sysMap = SystemPro.getProperties();
			    String zycfmobile = (String) sysMap.get("ZYCFMOBILE");
				if(fundRecordlist.size() > 0){
					 for(ClientFundRecord clientFundRecord : fundRecordlist){
						 ClientFundRecordVo cvo = new ClientFundRecordVo();
						 //if(null != clientFundRecord.getUserid()){
							 if("DEPOSIT".equals(clientFundRecord.getType().name()) ||
							    "WITHDRAW".equals(clientFundRecord.getType().name()))
							 {//充值,提现,资金转出，资金转让
								 cvo.setAmount(clientFundRecord.getAmount());
								 cvo.setUserName("中阳财富");
								 cvo.setMobile("15311340737");
								 cvo.setAccount(clientFundRecord.getAccount());
								 cvo.setOperations(clientFundRecord.getOperation().getKey());
								 cvo.setOrderid(clientFundRecord.getOrderid());
								 cvo.setStatuss(clientFundRecord.getStatus().getKey());
								 cvo.setTypes(clientFundRecord.getType().getKey());
								 cvo.setDescription(clientFundRecord.getDescription());
								 cvo.setTimerecorded(od.formatTmie(clientFundRecord.getTimerecorded()));
								 cvo.setDescription(clientFundRecord.getDescription());
								 clientFundRecordVo.add(cvo);  
							 }else{
								 User user = this.getUserByUserId(clientFundRecord.getUserid());
								 cvo.setAccount(clientFundRecord.getAccount());
								 cvo.setAmount(clientFundRecord.getAmount());
								 if(null != user.getMobile()){
									 String userMobile = user.getMobile();
									 String mobile = cipher.decrypt(userMobile);
									 String mobiles = mobile.substring(0,mobile.length()-(mobile.substring(3)).length())+"****"+mobile.substring(7);
									 cvo.setMobile(mobiles);
									 cvo.setUserName(user.getName());
								 }else{
									 cvo.setMobile("");
									 cvo.setUserName("");
								 }
								 cvo.setOperations(clientFundRecord.getOperation().getKey());
								 cvo.setOrderid(clientFundRecord.getOrderid());
								 cvo.setStatuss(clientFundRecord.getStatus().getKey());
								 cvo.setTypes(clientFundRecord.getType().getKey());
								 cvo.setDescription(clientFundRecord.getDescription());
								 cvo.setTimerecorded(od.formatTmie(clientFundRecord.getTimerecorded()));
								 cvo.setDescription(clientFundRecord.getDescription());
								 clientFundRecordVo.add(cvo); 
							 }
							 
						 }
					 //}
					 clientFundRecordVoc.setTotalPage(totalPage);
					 clientFundRecordVoc.setRecordsTotal(count);
					 clientFundRecordVoc.setData(clientFundRecordVo);
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(System.currentTimeMillis()+"--结束时间");
		return clientFundRecordVoc;
	}

	//ClientFundRecord导出excle
	public ResponseEntity<byte[]> clientFundRecordOutExcle(HttpServletRequest request,
			PagerVO<ClientFundRecordVo> pager) {
		List<String[]> headNames = new ArrayList<String[]>();
		
		headNames.add(new String[] {"订单号", "账户","真实姓名","类型","操作","金额(元)","状态","日期","备注"});
		List<String[]> fieldNames = new ArrayList<String[]>();
		fieldNames.add(new String[] {"orderid","account","userName","types","operations","amount","statuss","timerecorded","description"});
	
		List<ClientFundRecordVo> clientFundRecordVos = new ArrayList<ClientFundRecordVo>();
		List<ClientFundRecordVo> clientFundRecordVosv = new ArrayList<ClientFundRecordVo>();
		Page<ClientFundRecord>page=new Page<ClientFundRecord>();
		page.setPageNo(1);
		page.setPageSize(Integer.MAX_VALUE);
		try {
			 DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		     Pattern pattern = Pattern.compile("[0-9]*"); 
			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
			String[] values = pager.getValue().split(",");
			for (int i = 0; i < fileds.length; i++) {
				 if("xxx".equals(values[i]))continue;
			     if("all".equals(values[i]))continue;
			     Matcher isNum = pattern.matcher(values[i]);
			     if(isNum.matches()){
			 User user = this.getUser(cipher.encrypt(values[i]));
			 page.getParams().put("userId",user.getId());
			}
			else{
				page.getParams().put("type",values[i]);
						}
					page.getParams().put(fileds[i],values[i]);
				}
			}
			if(pager.getSort()!=null){
				page.getParams().put("sort",pager.getSort());
			}
			else{
				page.getParams().put("sort","desc");
			}
			Map<String, Object> params = page.getParams();
			if(pager.getStartTime()!=0){
				params.put("startTime", new Date(pager.getStartTime()));
				page.setStartTime(new Date(pager.getStartTime()));
			}
			if(pager.getEndTime()!=0){
				params.put("endTime", new Date(pager.getEndTime()));
				page.setEndTime(new Date(pager.getEndTime()));
			}
			System.out.println("开始查询------"+System.currentTimeMillis());
			clientFundRecordVos = clientFundRecordService.ClientFundRecordOutExcle(page);
			OrderTime od = new OrderTime();
			System.out.println("开始迭代-------------"+System.currentTimeMillis());
			for( int i=0;i < clientFundRecordVos.size();i++){
				   ClientFundRecordVo cvo = new ClientFundRecordVo();
				   if(null !=clientFundRecordVos.get(i).getUserName()){
					     cvo.setUserName(clientFundRecordVos.get(i).getUserName());
						 String mobile = cipher.decrypt(clientFundRecordVos.get(i).getMobile());
						 cvo.setMobile(mobile);
					}else{
						 cvo.setMobile("15311340737");
	                     cvo.setUserName("中阳财富");
					}
					 cvo.setAmount(clientFundRecordVos.get(i).getAmount());
					 cvo.setAccount(clientFundRecordVos.get(i).getAccount());
					 if(null != clientFundRecordVos.get(i).getOperation()){
						 cvo.setOperations(clientFundRecordVos.get(i).getOperation().getKey());
					 }
					 cvo.setOrderid(clientFundRecordVos.get(i).getOrderid());
					 cvo.setStatuss(clientFundRecordVos.get(i).getStatus().getKey());
					 cvo.setTypes(clientFundRecordVos.get(i).getType().getKey());
					 cvo.setDescription(clientFundRecordVos.get(i).getDescription());
					 cvo.setTimerecorded(od.formatTmie(clientFundRecordVos.get(i).getTimerecordeds()));
					 cvo.setDescription(clientFundRecordVos.get(i).getDescription());
					 clientFundRecordVosv.add(cvo); 
					 System.out.println(clientFundRecordVosv.size());
				}
			String name="平台资金记录统计";
			System.out.println("开始导出excle-------"+System.currentTimeMillis());
			ResponseEntity<byte[]> responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, clientFundRecordVosv, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "平台资金记录文件导出失败，请重试");
		}
	}
}
