package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpUserDao;
import com.zhongyang.java.pojo.UmpUser;
import com.zhongyang.java.service.UmpUserService;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年7月1日 上午10:13:12
* 类说明
*/
@Service
public class UmpUserServiceImpl implements UmpUserService {

	@Autowired
    UmpUserDao umpUserDao;
	public UmpUser getOldUserIdByAccountName(String accountName) {
		return umpUserDao.getOldUserIdByAccountName(accountName);
	}

}
