package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.system.page.Page;

/**
 * 
* @Title: UmpSettleRechargeRecordDao.java 
* @Package com.zhongyang.java.dao 
* @Description:充值对账DAO 
* @author 苏忠贺   
* @date 2016年1月11日 下午4:25:52 
* @version V1.0
 */
public interface UmpSettleRechargeRecordDao{
	/**
	 * 
	* @Title: insertRecord 
	* @Description:插入一条记录 
	* @return int    返回类型 
	* @throws
	 */
	public int batchInsertRecord(List<UmpSettleRechargeRecord> umpSettleRechargeRecords)throws Exception;
	/**
	 * 
	* @Title: selectAllRecord 
	* @Description:根据条件查询所有记录 
	* @return List<UmpSettleRechargeRecord>    返回类型 
	* @throws
	 */
	public List<UmpSettleRechargeRecord> selectAllRecords(Page<UmpSettleRechargeRecord>page)throws Exception;
	/**
	 * 
	* @Title: updateRecord 
	* @Description:更新记录
	* @return int    返回类型 
	* @throws
	 */
	public int updateRecord(UmpSettleRechargeRecord umpSettleRechargeRecord)throws Exception;
	/**
	 * 
	* @Title: selectCount 
	* @Description:根据条件查询结果数量
	* @return int    返回类型 
	* @throws
	 */
	public int selectCount(Map map)throws Exception;
	/**
	 * 
	* @Title: selectByParams 
	* @Description:根据参数查询对应结果
	* @return UmpSettleRechargeRecord    返回类型 
	* @throws
	 */
	public UmpSettleRechargeRecord selectByParams(UmpSettleRechargeRecord umpSettleRechargeRecord)throws Exception;
	/**
	 * 
	* @Title: batchDeleteRecord 
	* @Description:批量删除
	* @return int    返回类型 
	* @throws
	 */
	public int batchDeleteRecord(List<UmpSettleRechargeRecord> umpSettleRechargeRecords)throws Exception;
}
