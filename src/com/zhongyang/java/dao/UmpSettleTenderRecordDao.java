package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.system.page.Page;
/**
 * 
* @Title: UmpSettleTenderRecordDao.java 
* @Package com.zhongyang.java.dao 
* @Description:标的转账对账DAO 
* @author 苏忠贺   
* @date 2016年1月12日 下午2:12:04 
* @version V1.0
 */
public interface UmpSettleTenderRecordDao{
	/**
	 * 
	* @Title: insertRecord 
	* @Description:插入一条记录 
	* @return int    返回类型 
	* @throws
	 */
	public int batchInsertRecord(List<UmpSettleTenderRecord> umpSettleTenderRecords)throws Exception;
	/**
	 * 
	* @Title: batchDeleteRecord 
	* @Description:批量删除
	* @return int    返回类型 
	* @throws
	 */
	public int batchDeleteRecord(List<UmpSettleTenderRecord> umpSettleTenderRecords)throws Exception;
	/**
	 * 
	* @Title: selectAllRecord 
	* @Description:根据条件查询所有记录 
	* @return List<UmpSettleRechargeRecord>    返回类型 
	* @throws
	 */
	public List<UmpSettleTenderRecord> selectAllRecords(Page<UmpSettleTenderRecord>page)throws Exception;
	/**
	 * 
	* @Title: updateRecord 
	* @Description:更新记录
	* @return int    返回类型 
	* @throws
	 */
	public int updateRecord(UmpSettleTenderRecord umpSettleTenderRecord)throws Exception;
	/**
	 * 
	* @Title: selectCount 
	* @Description:根据条件查询结果数量
	* @return int    返回类型 
	* @throws
	 */
	public int selectCount(Map map)throws Exception;
}
