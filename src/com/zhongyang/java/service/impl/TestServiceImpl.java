package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.TestDao;
import com.zhongyang.java.pojo.Test;
import com.zhongyang.java.service.TestService;
import com.zhongyang.java.system.page.Page;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestDao testDao; 
	
	@Override
	public List<Test> getTest(Page<Test> page) {
		return testDao.getTest(page);
	}

}
