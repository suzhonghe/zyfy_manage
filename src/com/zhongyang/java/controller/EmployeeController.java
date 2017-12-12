package com.zhongyang.java.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.EmployeeBiz;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.Role;
import com.zhongyang.java.pojo.SysAuthorities;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.EmployeeVo;
import com.zhongyang.java.vo.sys.LoginVO;
import com.zhongyang.java.vo.sys.MenuVo;
import com.zhongyang.java.vo.sys.RoleVo;


@Controller
public class EmployeeController extends BaseController{

	@Autowired
	private EmployeeBiz employeeBiz;
	
	
	@RequestMapping(value = "/emplogin")
	public @ResponseBody Message login(HttpServletRequest req, LoginVO loginVo) {
		return employeeBiz.login(req, loginVo);
	}
	

	@RequestMapping(value="/empAuthorization",method= RequestMethod.POST)
	public @ResponseBody MenuVo getEmpAuthorization(HttpServletRequest request){
		
		
		return  employeeBiz.getEmpAuth(request);
	}
	
	@RequestMapping(value="/emplogout",method=RequestMethod.POST)
	public @ResponseBody Message empLogout(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		session.removeAttribute("zycfLoginEmp");
		session.removeAttribute("emp_role");
		session.removeAttribute("emp_auth");
		return new Message(0,"退出成功");
	}
	
	@FireAuthority(authorities=Authorities.EMPLIST)
	@RequestMapping(value="/empList")
	public @ResponseBody Page<Employee> listEmployees(HttpServletRequest request, Page<Employee> page){
		
		return employeeBiz.getEmpList(request, page);
	}
	
	@FireAuthority(authorities=Authorities.EMPADD)
	@RequestMapping(value="/addEmployee",method=RequestMethod.POST)
		public @ResponseBody Message addEmployee(HttpServletRequest request,Employee emp){
		
			return employeeBiz.addEmployee(request, emp);
		}
	
	@FireAuthority(authorities=Authorities.ROLEADD)
	@RequestMapping(value="/addRole",method=RequestMethod.POST)
	public @ResponseBody Message addRole(RoleVo roleVo){
		
		return employeeBiz.addRole(roleVo);
	}
	
	/**
	 * 添加角色初始化接口
	 * @param role
	 * @return
	 */
	@FireAuthority(authorities=Authorities.ROLEAUTHINIT)
	@RequestMapping(value="/addRoleAuths")
	public @ResponseBody Map<String, List<SysAuthorities>> addRoleAuths(Role role){
		return employeeBiz.getRoleAuthorities(role);
	}
	
	@FireAuthority(authorities=Authorities.ROLEAUTHUPD)
	@RequestMapping(value="/updateRoleAuths")
	public @ResponseBody Message updateRoleAuths(RoleVo role){
		return employeeBiz.updateRoleAuths(role);
	}
	
	@FireAuthority(authorities=Authorities.ROLEAUTH)
	@RequestMapping(value="/getRoleAuthsDetails")
	public @ResponseBody Map getRoleAuthsDetails(Role role){
		return employeeBiz.getRoleAuthsDetails(role);
	}
	
	@FireAuthority(authorities=Authorities.EMPUPD)
	@RequestMapping(value="/updateEmployee", method=RequestMethod.GET)
	public @ResponseBody Message updateEmployee(HttpServletRequest request,Employee emp){

		
		return employeeBiz.updateEmployee(request, emp);
	}
	
	@RequestMapping(value="/updatePass",method=RequestMethod.POST)
	public @ResponseBody Message updatePass(HttpServletRequest request,EmployeeVo emp){
		return employeeBiz.updatePass(request,emp);
	}
	
	@FireAuthority(authorities=Authorities.ROLEAUTH)
	@RequestMapping(value="/getRoleAuth")
	public @ResponseBody Map<String, List<SysAuthorities>> getRoleAuth(Role role){
		return employeeBiz.getRoleAuthorities(role);
	}
	
	/**
	 * 角色权限初始化列表，获取所有角色及其对用的权限，该角色对用的员工。
	 * @return
	 */
	@FireAuthority(authorities=Authorities.ROLEAUTHINIT)
	@RequestMapping(value="/authRoleInit")
	public @ResponseBody  Map  authRole(){
		
		return employeeBiz.roleAuthInit();
		
	}
	
//	/***
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value="/selectAllAuth" )
//	
//		public @ResponseBody String selectAllAuth(){
//		
//			Role role = new Role();
//			role.setId("F5FB3902-67F8-4CF6-89C4-751DAC2B3DFA");
//			 String empId = "712D1329-E3EF-4BD1-97BA-91E65809C77F";
//			return employeeBiz.getEmpAuthorities(empId);
//		}
//	
	@RequestMapping(value="/dispatchEmpRole")
	public @ResponseBody Message dispatchEmpRole(String empId,String roleId,String orgId){
		return employeeBiz.dispatchEmpRole(empId, roleId,orgId);
	}
	
	/**
	 * 员工角色分配调用接口
	 * @return
	 */
	@RequestMapping(value="/getUserRoles")
	public @ResponseBody Map getUserRoles(Employee emp){
		return employeeBiz.getUserRoles(emp);
	}
	/**
	 * 
	* @Title: emplpyeeBan 
	* @Description:员工禁用
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.EMPBAN)
	@RequestMapping(value="/emplpyeeBan")
	public @ResponseBody Message emplpyeeBan(Employee emp){
		return employeeBiz.emplpyeeBan(emp);
	}
}
