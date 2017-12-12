package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.EmployeeMapper;
import com.zhongyang.java.dao.SysAuthorizationMapper;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.SysAuthorization;
import com.zhongyang.java.service.SystemAuthorizationService;

@Service
public class SystemAuthorizationServiceImpl  implements SystemAuthorizationService{

	@Autowired
	SysAuthorizationMapper sysAuthorizationMapper;
	
	 public List<SysAuthorization> selectAll(){
		
		return sysAuthorizationMapper.selectAll();
	}
}
