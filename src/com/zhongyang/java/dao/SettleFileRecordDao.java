package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.SettleFileRecord;
import com.zhongyang.java.system.page.Page;

/**
 * 
* @Title: SettleFileRecordDao.java 
* @Package com.zhongyang.java.dao 
* @Description:对账记录DAO
* @author 苏忠贺   
* @date 2016年3月3日 下午4:26:31 
* @version V1.0
 */
public interface SettleFileRecordDao {

	public int insertSettleFileRecord(SettleFileRecord record)throws Exception;
	
	public int updateByParams(SettleFileRecord record)throws Exception;
	
	public void deleteByParams(SettleFileRecord record)throws Exception;
	
	public List<SettleFileRecord> selectByParams(Page<SettleFileRecord> page)throws Exception;
	
	public int selectCountByParams(Map<String, Object> params)throws Exception;
}
