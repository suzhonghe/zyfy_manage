package com.zhongyang.java.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.UserFundBiz;
import com.zhongyang.java.dao.UmpAccountDao;
import com.zhongyang.java.dao.UserFundDao;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.FundRecordVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UserFundVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午5:27:27
* 类说明
*/
@Service
public class UserFundBizImpl implements UserFundBiz {
	
	private static Logger logger = Logger.getLogger(UserFundBizImpl.class);    
	
	@Autowired
	UserFundService userFundService;
	
	@Autowired
	UserFundDao userFundDao;
	
	@Autowired
	UmpAccountDao umpAccountDao;
	/*
	 * 订单生成
	 */
	ApplicationBean appben;
	
	@Autowired
	FundRecordService fundRecordService;
	
	@Autowired
	InvestService investService;
	
	@Autowired
	LoanService loanService;
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.biz.UserFundBiz#byUserId(com.zhongyang.java.vo.UserFundVo)
	 * 返回给页面订单号和用户Id
	 * 
	 */
	@Override
	public UserFundVo byUserId(UserFundVo userFundVo) {
//		  userFundVo.getUserid().isEmpty()
		if(userFundVo.getUserid()==null){
          throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
		}else{
			try {
				ApplicationBean appben = new  ApplicationBean();
				UserFund userFund =new UserFund();
				userFund.setUserid(userFundVo.getUserid());
				userFund = userFundService.byUserID(userFund);
				if(userFund != null){
					userFundVo.setAvailableAmount(userFund.getAvailableAmount());
					userFundVo.setDepositAmount(userFund.getDepositAmount());
					userFundVo.setDueInAmount(userFund.getDueInAmount());
					userFundVo.setDueOutAmount(userFund.getFrozenAmount());
					userFundVo.setStatus(userFund.getStatus());
					userFundVo.setTimecreated(userFund.getTimecreated());
					userFundVo.setTimelastupdated(userFund.getTimelastupdated());
					userFundVo.setUserid(userFund.getUserid());
					userFundVo.setWithdrawAmount(userFund.getWithdrawAmount());
					userFundVo.setOrderId(appben.orderId());//充值生成的订单号
				}else{
					userFundVo.setOrderId(appben.orderId());
				}
			} catch (Exception e) {
				logger.info(e,e.fillInStackTrace());
				System.out.println(e.getMessage()+"");
			}
			return userFundVo;	
		}
		
	}
	@Override
	public UserFundVo getUserFundAccount(String userId) {//用户资金余额(可用，待收，代还，冻结)
		UserFundVo userFundVo = new UserFundVo();
		try {
			UserFund userFund = new UserFund();
//			String userIdc = "3A44C499-C6A5-4685-9566-7F4A8E4F0934";
			userFund.setUserid(userId);
			userFund = userFundService.byUserID(userFund);
			if(null !=userFund){
				userFundVo.setTotalAssets(userFund.getAvailableAmount().add(userFund.getFrozenAmount()).add(userFund.getDueInAmount()));//总资产
				userFundVo.setAvailableAmount(userFund.getAvailableAmount());//可用
				userFundVo.setFrozenAmount(userFund.getFrozenAmount());//冻结
				userFundVo.setDueInAmount(userFund.getDueInAmount());//待收
				userFundVo.setDueOutAmount(userFund.getDueOutAmount());//待还
				int investNumber = this.getUsetInvestNumber(userId);//投资次数
				int loanNumber = this.getUsetLoanNumber(userId);//借款记录
				userFundVo.setInvestNumber(investNumber);//投资次数
				userFundVo.setLoanNumber(loanNumber);//借款记录
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return userFundVo;
	}
	
	private int getUsetLoanNumber(String userId) {
		int loanNumber = 0;
		try {
			loanNumber = loanService.getUsetLoanNumber(userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return loanNumber;
	}

	private int getUsetInvestNumber(String userId) {
		int investNumber = 0;
		try {
			investNumber = investService.getUsetInvestNumber(userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return investNumber;
	}
	
	
	
	@Override
	public PagerVO<FundRecordVo> getFundRecordDetaileList(PagerVO<FundRecord> pager) {
		PagerVO<FundRecordVo> fundRecordPgVo=new PagerVO<FundRecordVo>();
		List<FundRecord>  fundRecordlist =  new ArrayList<FundRecord>();
		List<FundRecordVo>  fundRecordlistvo =  new ArrayList<FundRecordVo>();
		try {
			Page<FundRecord>page=new Page<FundRecord>();
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
			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if("xxx".equals(values[i]))continue;
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
			int count = fundRecordService.getFundRecordCount(params);
			fundRecordlist = fundRecordService.getFundRecordList(page);
			if(fundRecordlist.size() > 0){
				for(int i = 0;i<fundRecordlist.size();i++){
					FundRecordVo fundRecordVo = new FundRecordVo();
					fundRecordVo.setId(fundRecordlist.get(i).getId());
					fundRecordVo.setOrderid(fundRecordlist.get(i).getOrderid());	
					fundRecordVo.setAmount(fundRecordlist.get(i).getAmount());
					fundRecordVo.setOperation(fundRecordlist.get(i).getOperation().getKey());
					fundRecordVo.setType(fundRecordlist.get(i).getType().getKey());
					fundRecordVo.setTimerecorded(fundRecordlist.get(i).getTimerecorded());
					fundRecordVo.setStatus(fundRecordlist.get(i).getStatus().getKey());
					fundRecordVo.setDescription(fundRecordlist.get(i).getDescription());
					fundRecordlistvo.add(fundRecordVo);
				}
				fundRecordPgVo.setTotalPage(count);
				fundRecordPgVo.setData(fundRecordlistvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return fundRecordPgVo;
	}
	
}
