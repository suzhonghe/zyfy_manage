package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.SettleFileRecord;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.FundRecordSettle;

/**
 * 
* @Title: SettleFileRecordService.java 
* @Package com.zhongyang.java.dao 
* @Description:对账记录Service
* @author 苏忠贺   
* @date 2016年3月3日 下午4:26:31 
* @version V1.0
 */
public interface SettleFileRecordService {

	public int addSettleFileRecord(SettleFileRecord record)throws Exception;
	
	public int modifyByParams(SettleFileRecord record)throws Exception;
	
	public void deleteByParams(SettleFileRecord record)throws Exception;
	
	public List<SettleFileRecord> queryByParams(Page<SettleFileRecord> page)throws Exception;
	
	public int queryCountByParams(Map<String, Object> params)throws Exception;
}
