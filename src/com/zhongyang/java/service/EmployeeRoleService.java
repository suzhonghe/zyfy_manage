package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.EmpRoleKey;


public interface EmployeeRoleService {

	List<EmpRoleKey> selectAll();
	EmpRoleKey getUserRole(String userid);
}
