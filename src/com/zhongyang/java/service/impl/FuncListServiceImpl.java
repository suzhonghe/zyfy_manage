package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.SysFuncListMapper;
import com.zhongyang.java.pojo.SysFuncList;
import com.zhongyang.java.service.FuncListService;

@Service
public class FuncListServiceImpl implements FuncListService {

	@Autowired
	private SysFuncListMapper sysFuncListMapper;
	@Override
	public List<SysFuncList> selectAll() {
		// TODO Auto-generated method stub
		return sysFuncListMapper.selectAll();
	}

}
