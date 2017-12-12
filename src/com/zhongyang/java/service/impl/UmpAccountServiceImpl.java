package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpAccountDao;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.UmpAccountService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月2日 下午5:24:11
* 类说明
*/
@Service
public class UmpAccountServiceImpl implements UmpAccountService {
	
	@Autowired
	private UmpAccountDao umpAccountDao;

	@Override
	public UmpAccount getUmpAccountByUserId(UmpAccount umpAccount) throws Exception {
		return umpAccountDao.getUmpAccountByUserId(umpAccount);
	}

	@Override
	public UmpAccount queryByParams(UmpAccount umpAccount) throws Exception {
		return umpAccountDao.selectByParams(umpAccount);
	}
	public User queryByAccount(UmpAccount umpAccount)throws Exception{
		return umpAccountDao.selectByAccount(umpAccount);
	}
}
