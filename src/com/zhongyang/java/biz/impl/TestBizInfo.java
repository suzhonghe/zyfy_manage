package com.zhongyang.java.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.TestBiz;
import com.zhongyang.java.pojo.Test;
import com.zhongyang.java.service.TestService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;


@Service
public class TestBizInfo implements TestBiz {
	
	@Autowired
	private TestService testServlet;
	
	@Override
	@Transactional
	public List<Test> getTest() {
		return testServlet.getTest(new Page<Test>());
	}
	
	@Override
	public List<Test> getException() {
		throw  new UException(SystemEnum.UNKNOW_EXCEPTION,"王如意错误");
	}
}
