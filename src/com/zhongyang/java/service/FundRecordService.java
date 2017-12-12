package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.FundRecordSettle;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午1:44:23
* 类说明
*/
public interface FundRecordService {

	public int addFundRecord(FundRecord fundRecord);
	
    public int updateFundRecordByOrderId(FundRecord fundRecord);

	public FundRecord findFundRecordByOrderId(FundRecord fundRecord);

	public List<FundRecord> findFundRecordListByOrderId(FundRecord fundRecord);
	/*
	 * 根据订单号和资金操作查询资金记录
	 */
	public FundRecord queryFundRecord(FundRecord fundRecord);
	/*
	 * 根据Id修改记录状态
	 */
	public void modifyFundRecord(FundRecord fundRecord);

	public int getFundRecordCount(Map<String, Object> params) throws Exception;
    
	public List<FundRecord> getFundRecordList(Page<FundRecord> page) throws Exception;

	public List<FundRecordSettle> queryFundRecords(Page<FundRecord> page) ;
	
	public List<User> queryInvestUsers(Page<User> page);

	public int updateStatusForFailed(String nowdate, String newDate) throws Exception;
}
