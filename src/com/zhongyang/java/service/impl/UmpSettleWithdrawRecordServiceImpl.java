package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpSettleWithdrawRecordDao;
import com.zhongyang.java.pojo.UmpSettleWithdrawRecord;
import com.zhongyang.java.service.UmpSettleWithdrawRecordService;
import com.zhongyang.java.system.page.Page;

/**
 * 
* @Title: UmpSettleWithdrawRecordServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:提现对账业务实现
* @author 苏忠贺   
* @date 2016年1月12日 下午7:58:51 
* @version V1.0
 */
@Service
public class UmpSettleWithdrawRecordServiceImpl implements UmpSettleWithdrawRecordService{
	
	@Autowired
	private UmpSettleWithdrawRecordDao umpSettleWithdrawRecordDao;
	
	@Override
	public int batchAddRecord(List<UmpSettleWithdrawRecord> umpSettleWithdrawRecords) throws Exception {
		return umpSettleWithdrawRecordDao.batchInsertRecord(umpSettleWithdrawRecords);
	}

	@Override
	public List<UmpSettleWithdrawRecord> queryAllRecords(Page<UmpSettleWithdrawRecord> page) throws Exception {
		return umpSettleWithdrawRecordDao.selectAllRecords(page);
	}

	@Override
	public int modifyRecord(UmpSettleWithdrawRecord umpSettleWithdrawRecord) throws Exception {
		return umpSettleWithdrawRecordDao.updateRecord(umpSettleWithdrawRecord);
	}

	@Override
	public int queryCount(Map map) throws Exception {
		return umpSettleWithdrawRecordDao.selectCount(map);
	}

	@Override
	public int batchDeleteRecord(List<UmpSettleWithdrawRecord> umpSettleWithdrawRecords) throws Exception {
		return umpSettleWithdrawRecordDao.batchDeleteRecord(umpSettleWithdrawRecords);
	}

}
