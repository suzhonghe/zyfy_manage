package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpSettleTransferRecordDao;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.service.UmpSettleTransferRecordService;
import com.zhongyang.java.system.page.Page;

/**
 * 
* @Title: UmpSettleTransferRecordServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:转账对账业务实现
* @author 苏忠贺   
* @date 2016年1月12日 下午7:56:20 
* @version V1.0
 */
@Service
public class UmpSettleTransferRecordServiceImpl implements UmpSettleTransferRecordService{
	
	@Autowired
	private UmpSettleTransferRecordDao umpSettleTransferRecordDao;
	
	@Override
	public int batchAddRecord(List<UmpSettleTransferRecord> umpSettleTransferRecords)throws Exception {
		return umpSettleTransferRecordDao.batchInsertRecord(umpSettleTransferRecords);
	}

	@Override
	public List<UmpSettleTransferRecord> queryAllRecords(Page<UmpSettleTransferRecord> page) throws Exception {
		return umpSettleTransferRecordDao.selectAllRecords(page);
	}

	@Override
	public int modifyRecord(UmpSettleTransferRecord umpSettleTransferRecord) throws Exception {
		return umpSettleTransferRecordDao.updateRecord(umpSettleTransferRecord);
	}

	@Override
	public int queryCount(Map map) throws Exception {
		return umpSettleTransferRecordDao.selectCount(map);
	}

	@Override
	public int batchDeleteRecord(List<UmpSettleTransferRecord> umpSettleTransferRecords) throws Exception {
		return umpSettleTransferRecordDao.batchDeleteRecord(umpSettleTransferRecords);
	}

}
