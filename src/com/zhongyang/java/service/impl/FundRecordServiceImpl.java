package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.FundRecordDao;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.FundRecordSettle;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午1:44:45
* 类说明
*/
@Service
public class FundRecordServiceImpl implements FundRecordService {
    
	@Autowired
	FundRecordDao fundRecordDao;
	@Override
	public int addFundRecord(FundRecord fundRecord) {
    
	return  fundRecordDao.addFundRecord(fundRecord);
	}
	
    /*
     * (non-Javadoc)
     * @see com.zhongyang.java.service.FundRecordService#updateFundRecordByOrderId()
     * 根据orderId更新资金状态
     */
	public int updateFundRecordByOrderId(FundRecord fundRecord) {

		return fundRecordDao.updateFundRecordByOrderId(fundRecord);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.FundRecordService#findFundRecordByOrderId(com.zhongyang.java.pojo.FundRecord)
	 * 根据orderId查询用户ID
	 *
	 */
	public FundRecord findFundRecordByOrderId(FundRecord fundRecord) {
		
		return fundRecordDao.findFundRecordByOrderId(fundRecord);
	}


	public List<FundRecord> findFundRecordListByOrderId(FundRecord fundRecord) {
		
		return fundRecordDao.findFundRecordListByOrderId(fundRecord);
	}
	public FundRecord queryFundRecord(FundRecord fundRecord)  {
		return fundRecordDao.selectFundRecord(fundRecord);
	}

	@Override
	public void modifyFundRecord(FundRecord fundRecord)  {
		fundRecordDao.updateFundRecord(fundRecord);

	}

	@Override
	public int getFundRecordCount(Map<String, Object> params) throws Exception {
		return fundRecordDao.getFundRecordCount(params);
	}

	@Override
	public List<FundRecord> getFundRecordList(Page<FundRecord> page) throws Exception {
		return fundRecordDao.getFundRecordList(page);
	}

	@Override
	public List<FundRecordSettle> queryFundRecords(Page<FundRecord> page){
		return fundRecordDao.selectFundRecords(page);
	}

	@Override
	public List<User> queryInvestUsers(Page<User> page) {
		return fundRecordDao.selectInvestUsers(page);
	}

	@Override
	public int updateStatusForFailed(String nowdate, String newDate) throws Exception {
		return fundRecordDao.updateStatusForFailed(nowdate,newDate);
		
	}
}
