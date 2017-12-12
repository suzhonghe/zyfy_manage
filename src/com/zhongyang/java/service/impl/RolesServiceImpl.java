package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.pojo.Role;
import com.zhongyang.java.service.RolesService;
import com.zhongyang.java.dao.RoleMapper;

@Service
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> selectAll() {
		// TODO Auto-generated method stub
		return roleMapper.selectAll();
	}

	@Override
	public int addRole(Role role) {
		
		
		// TODO Auto-generated method stub
		return roleMapper.insert(role);
	}

	@Override
	public Role selectByPrimarykey(String id) {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateRoleByPrimarykey(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.updateByPrimaryKey(role);
	}
	
	

}
