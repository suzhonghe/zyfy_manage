package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.FundRecordSettle;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月7日 上午10:52:31
* 类说明
*/
public interface FundRecordDao {
	/*
	 * 添加用户资金
	 */
	public int addFundRecord(FundRecord fundRecord);
	
	public int  updateFundRecordByOrderId(FundRecord fundRecord);

	public FundRecord findFundRecordByOrderId(FundRecord fundRecord);

	public List<FundRecord> findFundRecordListByOrderId(FundRecord fundRecord);

	/*
	 * 根据订单号和资金操作查询资金记录
	 */
	public FundRecord selectFundRecord(FundRecord fundRecord);
	/*
	 * 根据操作和订单号修改记录状态
	 */
	public void updateFundRecord(FundRecord fundRecord);

	public int getFundRecordCount(Map<String, Object> params) throws Exception;

	public List<FundRecord> getFundRecordList(Page<FundRecord> page) throws Exception;
	
	public List<FundRecordSettle> selectFundRecords(Page<FundRecord> page);
	
	public List<User> selectInvestUsers(Page<User> page);

	public int updateStatusForFailed(String nowdate, String newDate) throws Exception;

}
