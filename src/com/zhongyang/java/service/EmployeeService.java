package com.zhongyang.java.service;

import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.system.Message;

public interface EmployeeService {

	//public List<Employee> selectAll();
	
	public Employee selectByLoginName(String loginName);
	public Employee selectByCell(String cell);
	
	public Message addEmployee(Employee emp);
	
	public Employee selectByParams(Employee emp);
	public int updateByParams(Employee emp);
	

}
