package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.system.page.Page;
/**
 * 
* @Title: UmpSettleRechargeRecordService.java 
* @Package com.zhongyang.java.service 
* @Description:充值对账业务接口
* @author 苏忠贺   
* @date 2016年1月12日 上午8:58:31 
* @version V1.0
 */
public interface UmpSettleRechargeRecordService{
	/**
	 * 
	* @Title: addRecord 
	* @Description:插入一条记录 
	* @return int    返回类型 
	* @throws
	 */
	public int batchAddRecord(List<UmpSettleRechargeRecord> umpSettleRechargeRecords)throws Exception;
	/**
	 * 
	* @Title: queryAllRecords 
	* @Description:根据条件查询所有记录 
	* @return List<UmpSettleRechargeRecord>    返回类型 
	* @throws
	 */
	public List<UmpSettleRechargeRecord> queryAllRecords(Page<UmpSettleRechargeRecord>page)throws Exception;
	/**
	 * 
	* @Title: modifyRecord 
	* @Description:修改记录 
	* @return int    返回类型 
	* @throws
	 */
	public int modifyRecord(UmpSettleRechargeRecord umpSettleRechargeRecord)throws Exception;
	/**
	 * 
	* @Title: queryCount 
	* @Description:根据条件查询结果数量 
	* @return int    返回类型 
	* @throws
	 */
	public int queryCount(Map map)throws Exception;
	/**
	 * 
	* @Title: queryByParams 
	* @Description:根据参数查询对应结果
	* @return UmpSettleRechargeRecord    返回类型 
	* @throws
	 */
	public UmpSettleRechargeRecord queryByParams(UmpSettleRechargeRecord umpSettleRechargeRecord)throws Exception;
	/**
	 * 
	* @Title: batchDeleteRecord 
	* @Description:批量删除
	* @return int    返回类型 
	* @throws
	 */
	public int batchDeleteRecord(List<UmpSettleRechargeRecord> umpSettleRechargeRecords)throws Exception;
}
