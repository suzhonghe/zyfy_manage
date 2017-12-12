package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpSettleRechargeRecordDao;
import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.service.UmpSettleRechargeRecordService;
import com.zhongyang.java.system.page.Page;

/**
 * 
* @Title: UmpSettleRechargeRecordServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:充值对账业务实现
* @author 苏忠贺   
* @date 2016年1月12日 上午9:00:58 
* @version V1.0
 */
@Service
public class UmpSettleRechargeRecordServiceImpl implements UmpSettleRechargeRecordService{
	
	@Autowired
	private UmpSettleRechargeRecordDao umpSettleRechargeRecordDao;
	
	@Override
	public int batchAddRecord(List<UmpSettleRechargeRecord> umpSettleRechargeRecords) throws Exception {
		return umpSettleRechargeRecordDao.batchInsertRecord(umpSettleRechargeRecords);
	}

	@Override
	public List<UmpSettleRechargeRecord> queryAllRecords(Page<UmpSettleRechargeRecord> page) throws Exception {
		return umpSettleRechargeRecordDao.selectAllRecords(page);
	}

	@Override
	public int modifyRecord(UmpSettleRechargeRecord umpSettleRechargeRecord) throws Exception {
		return umpSettleRechargeRecordDao.updateRecord(umpSettleRechargeRecord);
	}

	@Override
	public int queryCount(Map map) throws Exception {
		return umpSettleRechargeRecordDao.selectCount(map);
	}

	@Override
	public UmpSettleRechargeRecord queryByParams(UmpSettleRechargeRecord umpSettleRechargeRecord) throws Exception {
		return umpSettleRechargeRecordDao.selectByParams(umpSettleRechargeRecord);
	}

	@Override
	public int batchDeleteRecord(List<UmpSettleRechargeRecord> umpSettleRechargeRecords) throws Exception {
		return umpSettleRechargeRecordDao.batchDeleteRecord(umpSettleRechargeRecords);
	}

}
