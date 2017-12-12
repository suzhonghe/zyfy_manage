package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.UmpSettleWithdrawRecord;
import com.zhongyang.java.system.page.Page;
/**
 * 
* @Title: UmpSettleWithdrawRecordService.java 
* @Package com.zhongyang.java.service 
* @Description:提现对账 
* @author 苏忠贺   
* @date 2016年1月12日 下午6:34:53 
* @version V1.0
 */
public interface UmpSettleWithdrawRecordService{
	/**
	 * 
	* @Title: addRecord 
	* @Description:插入一条记录 
	* @return int    返回类型 
	* @throws
	 */
	public int batchAddRecord(List<UmpSettleWithdrawRecord> umpSettleWithdrawRecords)throws Exception;
	/**
	 * 
	* @Title: batchDeleteRecord 
	* @Description:批量删除
	* @return int    返回类型 
	* @throws
	 */
	public int batchDeleteRecord(List<UmpSettleWithdrawRecord> umpSettleWithdrawRecords)throws Exception;
	/**
	 * 
	* @Title: queryAllRecords 
	* @Description:根据条件查询所有记录 
	* @return List<UmpSettleRechargeRecord>    返回类型 
	* @throws
	 */
	public List<UmpSettleWithdrawRecord> queryAllRecords(Page<UmpSettleWithdrawRecord>page)throws Exception;
	/**
	 * 
	* @Title: modifyRecord 
	* @Description:更新记录
	* @return int    返回类型 
	* @throws
	 */
	public int modifyRecord(UmpSettleWithdrawRecord umpSettleWithdrawRecord)throws Exception;
	/**
	 * 
	* @Title: queryCount 
	* @Description:根据条件查询结果数量 
	* @return int    返回类型 
	* @throws
	 */
	public int queryCount(Map map)throws Exception;
}
