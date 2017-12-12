package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;

/**
 * 
* @Title: UmpTenderTransferRecord.java 
* @Package com.zhongyang.java.dao 
* @Description: 联动交易记录DAO 
* @author 苏忠贺   
* @date 2015年12月10日 下午3:45:59 
* @version V1.0
 */
public interface UmpTenderTransferRecordDao {
	
	/*
	 * 添加一天记录
	 */
	public int insertUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord)throws Exception;
	
	public int updateUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord)throws Exception;

	public UmpTenderTransferRecord getUmpTenderTransferRecord(String orderId)throws Exception;

	public List<UmpTenderTransferRecord> findSettledloan(String type, String startTime, String endTime)throws Exception;

	public List<UmpTenderTransferRecord> findSettledloanByLoanId(String loanId)throws Exception;

}
