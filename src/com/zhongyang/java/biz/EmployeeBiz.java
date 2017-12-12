package com.zhongyang.java.biz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.Role;
import com.zhongyang.java.pojo.SysAuthorities;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.EmpVO;
import com.zhongyang.java.vo.EmployeeVo;
import com.zhongyang.java.vo.sys.LoginVO;
import com.zhongyang.java.vo.sys.MenuVo;
import com.zhongyang.java.vo.sys.RoleVo;

public interface EmployeeBiz {

	
	public Message login(HttpServletRequest req, LoginVO loginVO);
	
	public MenuVo getEmpAuth(HttpServletRequest request);
	
	public Message addEmployee(HttpServletRequest request, Employee empvo);
	
	public Message addRole(RoleVo roleVo);
	
	
	public Message updateEmployee(HttpServletRequest request, Employee emp);
	
//	public List<SysAuthorities> selectAllSysAuthorities();
	
	public Map<String, List<SysAuthorities>> getRoleAuthorities(Role role);
	public  List<Employee> getRoleEmp(Role role);
	
	public Page<Employee> getEmpList(HttpServletRequest request, Page<Employee> page);
	public String getEmpAuthorities(String empId);
	
	public Map roleAuthInit();
	
	public Message updateRoleAuths(RoleVo roleVo);
	public Map getRoleAuthsDetails(Role role);
	
	public Message dispatchEmpRole(String empid,String roleid,String orgId);
	
	public List<Role> getAllRoles();
	public Map<String,Object> getUserRoles(Employee emp);
	
	public Message updatePass(HttpServletRequest request,EmployeeVo emp);
	
	public Message emplpyeeBan(Employee emp);
}
