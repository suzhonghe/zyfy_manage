package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.EmployeeMapper;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.service.EmployeeService;
import com.zhongyang.java.system.Message;
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;
//
//	public List<Employee> selectAll() {
//		// TODO Auto-generated method stub
//		return employeeMapper.selectAll();
//	}

	@Override
	public Employee selectByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return employeeMapper.selectByLoginName(loginName);
	} 

	@Override
	public Employee selectByCell(String cell) {
		// TODO Auto-generated method stub
		return employeeMapper.selectByCell(cell);
	}

	public Message addEmployee(Employee emp){
		try{
			employeeMapper.insert(emp);
		}catch(Exception e){
			return new Message(3,"数据库错误");
			
		}
		
		return new Message(0,"添加用户成功");
	}

	@Override
	public Employee selectByParams(Employee emp) {
		return employeeMapper.selectByParams(emp);
	}

	@Override
	public int updateByParams(Employee emp) {
		return employeeMapper.updateByPrimaryKeySelective(emp);
	}
}
