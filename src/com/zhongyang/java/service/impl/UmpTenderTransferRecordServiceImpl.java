package com.zhongyang.java.service.impl;
/**
 * 
* @Title: UmpTenderTransferRecordServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description: 联动交易记录业务实现 
* @author 苏忠贺   
* @date 2015年12月10日 下午4:47:28 
* @version V1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpTenderTransferRecordDao;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;
import com.zhongyang.java.service.UmpTenderTransferRecordService;

@Service
public class UmpTenderTransferRecordServiceImpl implements UmpTenderTransferRecordService{
	
	@Autowired
	private UmpTenderTransferRecordDao uttrDao;

	@Override
	public int addUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord) throws Exception {
		return uttrDao.insertUmpTenderTransferRecord(uttRecord);
	}

	@Override
	public int modifyUmpTenderTransferRecord(UmpTenderTransferRecord uttRecord) throws Exception {
		return uttrDao.updateUmpTenderTransferRecord(uttRecord);
	}

	@Override
	public UmpTenderTransferRecord getUmpTenderTransferRecord(String orderId) throws Exception {
		return uttrDao.getUmpTenderTransferRecord(orderId);
	}

	@Override
	public List<UmpTenderTransferRecord> findSettledloan(String type, String startTime, String endTime)
			throws Exception {
		return uttrDao.findSettledloan(type,startTime,endTime);
	}

	@Override
	public List<UmpTenderTransferRecord>  findSettledloanByLoanId(String loanId) throws Exception {

		return uttrDao.findSettledloanByLoanId(loanId);
	}

}
