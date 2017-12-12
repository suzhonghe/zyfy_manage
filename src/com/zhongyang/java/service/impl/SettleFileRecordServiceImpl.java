package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.FundRecordDao;
import com.zhongyang.java.dao.SettleFileRecordDao;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.SettleFileRecord;
import com.zhongyang.java.service.SettleFileRecordService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.FundRecordSettle;
@Service
public class SettleFileRecordServiceImpl implements SettleFileRecordService {
	
	@Autowired
	private SettleFileRecordDao settleFileRecordDao;

	@Override
	public int addSettleFileRecord(SettleFileRecord record) throws Exception {
		return settleFileRecordDao.insertSettleFileRecord(record);
	}

	@Override
	public int modifyByParams(SettleFileRecord record) throws Exception {
		return settleFileRecordDao.updateByParams(record);
	}

	@Override
	public void deleteByParams(SettleFileRecord record) throws Exception {
		settleFileRecordDao.deleteByParams(record);
	}

	@Override
	public List<SettleFileRecord> queryByParams(Page<SettleFileRecord> page) throws Exception {
		return settleFileRecordDao.selectByParams(page);
	}

	@Override
	public int queryCountByParams(Map<String, Object> params) throws Exception {
		return settleFileRecordDao.selectCountByParams(params);
	}

}
