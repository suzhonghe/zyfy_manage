package com.zhongyang.java.sys.uitl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;

import com.zhongyang.java.pojo.EmpRoleKey;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.Role;
import com.zhongyang.java.pojo.SysAuthorization;
import com.zhongyang.java.pojo.SysFuncList;
import com.zhongyang.java.service.EmployeeRoleService;
import com.zhongyang.java.service.EmployeeService;
import com.zhongyang.java.service.FuncListService;
import com.zhongyang.java.service.RolesService;
import com.zhongyang.java.service.SystemAuthorizationService;



public class Application {
	
	
	private static Application application = null;
	public static final ThreadLocal<Application> session = new ThreadLocal<Application>();

	public static Application getInstance() {
		application = session.get();
		if (application == null) {
			application = new Application();
			session.set(application);
		}
		return application;
	}
	
	
	
	/***
	 *  获取全局变量的对象，目前application 未来可能是keyvalue 的nosql中获取
	 * @param request
	 * @param key
	 * @return
	 */
	public Object getAppKey(HttpServletRequest request,String key) {
		return request.getSession(true).getServletContext().getAttribute(key);
	}
	
	

	
	/**
	 * 初始化启动时候加载全部全局对象
	 * @param ctx
	 * @param servletContext
	 * @param parameMap
	 */
	public void runALL(ApplicationContext ctx, ServletContext servletContext) {
//		this.runRoleMap(ctx, servletContext);
		this.runFuncMap(ctx, servletContext);
		this.runEmpMap(ctx, servletContext);
		this.runEmpRoleMap(ctx, servletContext);
		this.runAuthorizationMap(ctx, servletContext);

	}


    /***
	 * 加载全部的角色
	 * @param ctx
	 * @param servletContext
	 * @param parameMap
	 */
	private void runRoleMap(ApplicationContext ctx, ServletContext servletContext) {
		
		
		Map<String,Role> roleMap= (Map<String,Role>) GlobalConstants.roleMap;
		

		if (roleMap == null) {
			roleMap = new HashMap<String,Role>();
		}
		RolesService rolesService = (RolesService) ctx.getBean("rolesService");		
		List<Role> list = rolesService.selectAll();
		for (Role role : list) {
			roleMap.put(role.getId(), role);
		}
		
//		CacheUtils.remove(GlobalConstants.CACHE_ROLE_MAP);
//		CacheUtils.put(GlobalConstants.CACHE_ROLE_MAP, roleMap);
		

		

/*		GlobalConstants.roleMap.clear();
		for (Role role : list) {
			GlobalConstants.roleMap.put(role.getId(), role);
		}*/
	}
	
	/**
	 * 加载所有的函数id与函数名称
	 * @param ctx
	 * @param servletContext
	 * @param parameMap
	 */
	private void runFuncMap(ApplicationContext ctx, ServletContext servletContext) {
		
		Map<Integer,SysFuncList> funcMap= GlobalConstants.funcMap;
				//(Map<Integer,SysFuncList>)CacheUtils.get(GlobalConstants.CACHE_FUNC_MAP);
		
	//	CacheUtils.remove(GlobalConstants.CACHE_FUNC_MAP);
		
		FuncListService funcListService = (FuncListService) ctx.getBean("funcListService");
		List<SysFuncList> funcList = funcListService.selectAll();
		GlobalConstants.funcMap.clear();
		if (funcMap == null) {
			funcMap= new HashMap<Integer,SysFuncList>();
		}
		for (SysFuncList func : funcList) {
			funcMap.put(func.getId(),func);
			GlobalConstants.funcMap.put(func.getId(),func);
		}
		CacheUtils.put(GlobalConstants.CACHE_FUNC_MAP, funcMap);
        GlobalConstants.allMenu.clear();
   /*     for (SysFuncList func : funcList) {
            if(func.getParentId() == 0){
                GlobalConstants.allMenu.add(func);
            }else{
                if(func.getIsMenu() == 1){
                	Map<Long,SysFuncList> funcMapEh= (Map<Long,SysFuncList>)CacheUtils.get(GlobalConstants.CACHE_FUNC_MAP);
                	
                	if (funcMapEh == null) {
                		funcMapEh = GlobalConstants.funcMap;
                	}
                	
                    SysAuthFunc pFunc = funcMapEh.get(func.getParentId());
                   //SysAuthFunc pFunc = GlobalConstants.funcMap.get(func.getParentId());
                    pFunc.setIsChild(true);
                    pFunc.addChild(func);
                }

            }
        }*/

	}
	

    /***
	 * 加载全部的员工
	 * @param ctx
	 * @param servletContext
	 * @param parameMap
	 */
	private void runEmpMap(ApplicationContext ctx, ServletContext servletContext) {
		
		
		Map<String,Employee> empMap = (Map<String,Employee>)CacheUtils.get(GlobalConstants.CACHE_EMP_MAP);

		if (empMap == null) {
			empMap = new HashMap<String,Employee>();
		}
		EmployeeService employeeService = (EmployeeService) ctx.getBean("employeeService");		
	//	List<Employee> list = employeeService.selectAll();
		List<Employee> list = new ArrayList<Employee>();
		for (Employee emp : list) {
			empMap.put(emp.getId(), emp);
		}
		
		CacheUtils.remove(GlobalConstants.CACHE_EMP_MAP);
		CacheUtils.put(GlobalConstants.CACHE_EMP_MAP, empMap);	

		

	}
	
	/**
	 * 加载员工角色关系
	 * @param ctx
	 * @param servletContext
	 */
	private void runEmpRoleMap(ApplicationContext ctx, ServletContext servletContext) {
		
		
		Map<String,String> empRoleMap = (Map<String,String>)CacheUtils.get(GlobalConstants.CACHE_EMP_ROLE_MAP);

		if (empRoleMap == null) {
			empRoleMap = new HashMap<String,String>();
		}
		EmployeeRoleService employeeRoleService = (EmployeeRoleService) ctx.getBean("employeeRoleService");
		List<EmpRoleKey> list = employeeRoleService.selectAll();
		for (EmpRoleKey emp : list) {
			empRoleMap.put(emp.getEmpId(), emp.getRoleId());
			GlobalConstants.empRoleMap.put(emp.getEmpId(), emp.getRoleId());
		}
		
		CacheUtils.remove(GlobalConstants.CACHE_EMP_MAP);
		CacheUtils.put(GlobalConstants.CACHE_EMP_MAP, empRoleMap);	

		

	}
	
	/**
	 * 加载角色权限表
	 * @param ctx
	 * @param servletContext
	 */
	private void runAuthorizationMap(ApplicationContext ctx, ServletContext servletContext) {
		
		
//		Map<String,List<SysAuthorization>> authorizationMap = (Map<String,List<SysAuthorization>>)CacheUtils.get(GlobalConstants.CACHE_AUTH_MAP);
		Map<String,List<String>> authorizationMap = (Map<String,List<String>>)CacheUtils.get(GlobalConstants.CACHE_AUTH_MAP);

		if (authorizationMap == null) {
			authorizationMap = new HashMap<String,List<String>>();
		}
		SystemAuthorizationService systemAuthorizationService = (SystemAuthorizationService) ctx.getBean("systemAuthorizationService");
		
		List<SysAuthorization> list = systemAuthorizationService.selectAll();

		Map<Integer, SysFuncList> funcMap = (Map<Integer,SysFuncList>)CacheUtils.get(GlobalConstants.CACHE_FUNC_MAP);

		
		for (SysAuthorization rolemp : list) {
			if(authorizationMap.containsKey(rolemp.getRoleId()))
			{
				authorizationMap.get(rolemp.getRoleId()).add(funcMap.get(rolemp.getFuncId()).getPrivilige());
				
			}else{
				List array = new ArrayList<SysAuthorization>();
				array.add(rolemp);
				authorizationMap.put(rolemp.getRoleId(), array);
				
			}
			
			
		}
		
		CacheUtils.remove(GlobalConstants.CACHE_AUTH_MAP);
		CacheUtils.put(GlobalConstants.CACHE_AUTH_MAP, authorizationMap);	
	}
	
	
	

}
