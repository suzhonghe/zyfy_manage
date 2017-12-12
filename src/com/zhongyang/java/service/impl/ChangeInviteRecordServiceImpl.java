package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ChangeInviteRecordDao;
import com.zhongyang.java.pojo.ChangeInviteRecord;
import com.zhongyang.java.service.ChangeInviteRecordService;
@Service
public class ChangeInviteRecordServiceImpl implements ChangeInviteRecordService {

	@Autowired
	private ChangeInviteRecordDao changeInviteRecordDao;
	@Override
	public void insertChangeInviteRecord(ChangeInviteRecord changeInviteRecord) {
		
		changeInviteRecordDao.insertChangeInviteRecord(changeInviteRecord);
		
	}

	
}
