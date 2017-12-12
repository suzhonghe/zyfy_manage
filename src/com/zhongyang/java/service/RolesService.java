package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.Role;



public interface RolesService {

	public List<Role> selectAll();
	
	public int addRole(Role role);
	
	public Role selectByPrimarykey(String id);
	public int updateRoleByPrimarykey(Role role);
}
