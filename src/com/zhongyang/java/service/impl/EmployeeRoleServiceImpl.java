package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.EmpRoleMapper;
import com.zhongyang.java.pojo.EmpRoleKey;
import com.zhongyang.java.service.EmployeeRoleService;

@Service
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

	@Autowired
	private EmpRoleMapper empRoleMapper;
	
	@Override
	public List<EmpRoleKey> selectAll() {
		// TODO Auto-generated method stub
		
		return empRoleMapper.selectAll();
		
		
	}

	@Override
	public EmpRoleKey getUserRole(String userid) {
		// TODO Auto-generated method stub
		
		
		return empRoleMapper.getUserRole(userid);
	}
	
	

}
