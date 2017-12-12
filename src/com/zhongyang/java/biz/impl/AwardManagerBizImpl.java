package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.AwardManagerBiz;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.vo.InvestRecord;
import com.zhongyang.java.vo.InvestRecordVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.Result;
@Service
public class AwardManagerBizImpl implements AwardManagerBiz{
	
	private static Logger logger = Logger.getLogger(AwardManagerBizImpl.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvestService investService;

	@Override
	public InvestRecordVo queryByParams(PagerVO<User> pager) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			InvestRecordVo irv=new InvestRecordVo();
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Page<Invest>page=new Page<Invest>();
			if(pager.getStart()!=0){
				page.setPageNo(pager.getStart());
				irv.setPageNumber(pager.getStart());
			}
			else{
				page.setPageNo(1);
				irv.setPageNumber(1);
			}
			if(pager.getLength()!=0){
				page.setPageSize(pager.getLength());
				irv.setPageSize(pager.getLength());
			}
			else{
				page.setPageSize(10);
				irv.setPageSize(10);
			}
			if(pager.getEndTime()==0){
				page.setEndTime(sdf.parse("2130-01-01"));
			}
			else{
				page.setEndTime(new Date(pager.getEndTime()));
			}
			if(pager.getStartTime()==0){
				page.setStartTime(sdf.parse("1970-01-01"));
			}
			else{
				page.setStartTime(new Date(pager.getStartTime()));
			}
			
			User user=new User();
			if("mobile".equals(pager.getField())){
				String mobile = cipher.encrypt(pager.getValue());
				user.setMobile(mobile);
			}
			User queryUser = userService.queryByParams(user);
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("referralId", queryUser.getId());
			map.put("startTime", page.getStartTime());
			map.put("endTime", page.getEndTime());
			map.put("status", "SETTLED,CLEARED");
			Result result = investService.queryResultByMap(map);
			User us=new User();
			us.setReferralId(queryUser.getId());
			List<User> list = userService.queryUserListByParams(us);
			if(list!=null){
				irv.setUserNumber(list.size());
			}
			page.getParams().put("referralId", queryUser.getId());
			page.getParams().put("status", "SETTLED,CLEARED");
			List<InvestRecord> queryInvestRecord = investService.queryByParams(page);
			irv.setInvestRecords(queryInvestRecord);
			if(result.getCount()%page.getPageSize()==0){
				irv.setTotalPage(result.getCount()/page.getPageSize());
			}
			else{
				irv.setTotalPage(result.getCount()/page.getPageSize()+1);
			}
			irv.setInvestNumber(result.getCount());
			irv.setTotalAmount(result.getTotalAmount());
			
			//计算年化投资额
			page.setPageSize(Integer.MAX_VALUE);
			page.setPageNo(1);
			List<InvestRecord> records = investService.queryByParams(page);
			irv.setYearAmount(new BigDecimal(0));
			for (InvestRecord investRecord : records) {
				if(investRecord.getMonths()==1){
					irv.setYearAmount(BigDecimalAlgorithm.add(irv.getYearAmount(), BigDecimalAlgorithm.mul(investRecord.getAmount(), new BigDecimal(0.1)).setScale(0,2)));
				}
				if(investRecord.getMonths()==2){
					irv.setYearAmount(BigDecimalAlgorithm.add(irv.getYearAmount(), BigDecimalAlgorithm.mul(investRecord.getAmount(), new BigDecimal(0.2)).setScale(0,2)));		
				}
				if(investRecord.getMonths()==3){
					irv.setYearAmount(BigDecimalAlgorithm.add(irv.getYearAmount(), BigDecimalAlgorithm.mul(investRecord.getAmount(), new BigDecimal(0.25)).setScale(0,2)));
				}
				if(investRecord.getMonths()==6){
					irv.setYearAmount(BigDecimalAlgorithm.add(irv.getYearAmount(), BigDecimalAlgorithm.mul(investRecord.getAmount(), new BigDecimal(0.5)).setScale(0,5)));
				}
				if(investRecord.getMonths()==12){
					irv.setYearAmount(BigDecimalAlgorithm.add(irv.getYearAmount(), BigDecimalAlgorithm.mul(investRecord.getAmount(), new BigDecimal(1)).setScale(0,2)));
				}
			}
			return irv;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败，请重试");
		}
	}
}
