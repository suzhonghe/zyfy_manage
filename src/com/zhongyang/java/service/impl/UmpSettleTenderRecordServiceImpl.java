package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpSettleTenderRecordDao;
import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.service.UmpSettleTenderRecordService;
import com.zhongyang.java.system.page.Page;
/**
 * 
* @Title: umpSettleTenderRecordServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description: 标的对账业务实现
* @author 苏忠贺   
* @date 2016年1月12日 下午7:53:26 
* @version V1.0
 */
@Service
public class UmpSettleTenderRecordServiceImpl implements UmpSettleTenderRecordService{
	
	@Autowired
	private UmpSettleTenderRecordDao umpSettleTenderRecordDao;
	
	@Override
	public int batchAddRecord(List<UmpSettleTenderRecord> umpSettleTenderRecords)throws Exception {
		return umpSettleTenderRecordDao.batchInsertRecord(umpSettleTenderRecords);
	}

	@Override
	public List<UmpSettleTenderRecord> queryAllRecords(Page<UmpSettleTenderRecord> page) throws Exception {
		return umpSettleTenderRecordDao.selectAllRecords(page);
	}
	
	@Override
	public int modifyRecord(UmpSettleTenderRecord umpSettleTenderRecord) throws Exception {
		return umpSettleTenderRecordDao.updateRecord(umpSettleTenderRecord);
	}

	@Override
	public int queryCount(Map map) throws Exception {
		return umpSettleTenderRecordDao.selectCount(map);
	}

	@Override
	public int batchDeleteRecord(List<UmpSettleTenderRecord> umpSettleTenderRecords) throws Exception {
		return umpSettleTenderRecordDao.batchDeleteRecord(umpSettleTenderRecords);
	}

}
