package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ClientFundRecordDao;
import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.service.ClientFundRecordService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ClientFundRecordVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月21日 上午11:11:42
* 类说明:平台标的交易记录
*/
@Service
public class ClientFundRecordServiceImpl implements ClientFundRecordService{
	
    @Autowired
	ClientFundRecordDao clientFundRecordDao;
	public int addClientFundRecord(ClientFundRecord clientFundRecord) throws Exception {
		return clientFundRecordDao.addClientFundRecord(clientFundRecord);
	}
	
	public int updateClentFundByOrderId(ClientFundRecord clientFundRecord) throws Exception{
		
		return clientFundRecordDao.updateClentFundByOrderId(clientFundRecord);
		
	}
	

	public ClientFundRecord getClientFundRecordByOrderId(ClientFundRecord clientFundRecord) throws Exception {

		
		return clientFundRecordDao.getClientFundRecordByOrderId(clientFundRecord);
	}

	
	public List<ClientFundRecord> getClientFundRecordByStatus(ClientFundRecord clientFundRecord) throws Exception {
		
		return clientFundRecordDao.getClientFundRecordByStatus(clientFundRecord);
	}

	
	public ClientFundRecord getClientFundRecordByStatusAmountSum(ClientFundRecord clientFundRecord) throws Exception {
		return clientFundRecordDao.getClientFundRecordByStatusAmountSum(clientFundRecord);
	}

	
	public ClientFundRecord getClentFundByOrderId(ClientFundRecord clientFundRecord) throws Exception {
		return clientFundRecordDao.getClentFundByOrderId(clientFundRecord);
	}

	public int getClientFundRecordCount(Map<String, Object> params) throws Exception {
		return clientFundRecordDao.getClientFundRecordCount(params);
	}

	public List<ClientFundRecord> getClientFundRecord(Page<ClientFundRecord> page) throws Exception {
		return clientFundRecordDao.getClientFundRecord(page);
	}

	public List<ClientFundRecordVo> ClientFundRecordOutExcle(Page<ClientFundRecord> page) throws Exception {
		return clientFundRecordDao.ClientFundRecordOutExcle(page);
	}
}
