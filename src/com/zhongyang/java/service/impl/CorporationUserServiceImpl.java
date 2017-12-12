package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.CorporationUserDao;
import com.zhongyang.java.pojo.CorporationUser;
import com.zhongyang.java.service.CorporationUserService;
import com.zhongyang.java.system.page.Page;
@Service
public class CorporationUserServiceImpl implements CorporationUserService {
	
	@Autowired
	private CorporationUserDao corporationUserDao;
	@Override
	public int addCorporationUser(CorporationUser corporationUser) throws Exception {
		return corporationUserDao.insertCorporationUser(corporationUser);
	}

	@Override
	public List<CorporationUser> queryAllCorporationUser(CorporationUser corporationUser) throws Exception {
		return corporationUserDao.selectAllCorporationUser(corporationUser);
	}

}
