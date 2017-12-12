package com.zhongyang.java.biz.impl;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.EmployeeBiz;
import com.zhongyang.java.dao.EmpRoleMapper;
import com.zhongyang.java.dao.EmployeeMapper;
import com.zhongyang.java.dao.RoleMapper;
import com.zhongyang.java.dao.SysAuthoritiesMapper;
import com.zhongyang.java.pojo.EmpRoleKey;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.Organization;
import com.zhongyang.java.pojo.Role;
import com.zhongyang.java.pojo.SysAuthorities;
import com.zhongyang.java.pojo.SysAuthorization;
import com.zhongyang.java.pojo.SysFuncList;
import com.zhongyang.java.service.EmployeeRoleService;
import com.zhongyang.java.service.EmployeeService;
import com.zhongyang.java.service.FuncListService;
import com.zhongyang.java.service.OrganizationService;
import com.zhongyang.java.service.RolesService;
import com.zhongyang.java.service.SystemAuthorizationService;
import com.zhongyang.java.sys.uitl.AuthType;
import com.zhongyang.java.sys.uitl.AuthorityHelper;
import com.zhongyang.java.sys.uitl.Authorize;
import com.zhongyang.java.sys.uitl.CacheUtils;
import com.zhongyang.java.sys.uitl.FormatUtils;
import com.zhongyang.java.sys.uitl.GlobalConstants;
import com.zhongyang.java.sys.uitl.MStringUtils;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.Password;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.TextCipher;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.EmployeeVo;
import com.zhongyang.java.vo.sys.LoginVO;
import com.zhongyang.java.vo.sys.MenuVo;
import com.zhongyang.java.vo.sys.RoleVo;

@Service
public class EmployeeBizImpl implements EmployeeBiz {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@Autowired
	private FuncListService funcListService;

	@Autowired
	private SystemAuthorizationService systemAuthorizationService;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private SysAuthoritiesMapper sysAuthoritiesMapper;

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	EmpRoleMapper empRoleMapper;
	
	@Autowired
	private OrganizationService organizationService;

	@Override
	public Message login(HttpServletRequest req, LoginVO loginVO) {
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Pattern pattern = Pattern.compile("[0-9]*");
		int num = loginVO.getLoginCounter();
		loginVO.setLoginCounter(num++);
		Employee employee;

		// CacheManager cacheManager = (CacheManager)
		// ContextLoader.getCurrentWebApplicationContext().getBean("cacheManager");
		/****
		 * 
		 * 
		 * 
		 CacheUtils.setCacheManager(cacheManager); Map<String, Employee>
		 * empMap = (Map<String, Employee>) CacheUtils
		 * .get(GlobalConstants.CACHE_EMP_MAP); if (empMap == null)
		 * this.runALL();
		 */
		try {
			if (pattern.matcher(loginVO.getLoginname()).matches()) {
				employee = employeeService.selectByCell(cipher.encrypt(loginVO
						.getLoginname()));
			} else {
				employee = employeeService.selectByLoginName(loginVO
						.getLoginname());
			}
			if (employee != null) {
				if(!employee.getEnabled()){
					return new Message(1003, "你已被禁止登陆");
				}
				
				
				String salt = employee.getSalt();
				String password = Password.getPassphrase(salt,
						loginVO.getPassphrase());

				if (employee.getPassphrase().equals(password)) {
					loginVO.setLoginCounter(0);

					employee.setPassphrase(null);
					employee.setSalt(null);
					req.getSession().setAttribute("zycfLoginEmp", employee);

					// if (employee.getLoginName().equalsIgnoreCase("admin"))
					// req.getSession().setAttribute("emp_role", "admin");
					employee.setLastlogindate(new Date());
					
					employeeMapper.updateByPrimaryKeySelective(employee);
					
					req.getSession().setAttribute("emp_auths",
							this.getEmpAuthorities(employee.getId()));
					
					return new Message(1000, "登陆成功");
				}
			} else {
				return new Message(1002, "用户名不存在");
			}
		} catch (GeneralSecurityException e) {
			throw new UException(SystemEnum.LOGIN_ERROR, "登录异常");
		}
		return new Message(1001, "用户名和密码不匹配");

	}

	private void emploginCounter(int plusone) {

	}

	@Override
	public MenuVo getEmpAuth(HttpServletRequest request) {

		Employee emp = (Employee) request.getSession().getAttribute(
				"zycfLoginEmp");
		Map<String, List<String>> empRoleAuth = getRoleAuth(emp);
		Map<String, String> empRoleMap = (Map<String, String>) CacheUtils
				.get(GlobalConstants.CACHE_EMP_ROLE_MAP);
		if (emp.getLoginName().equalsIgnoreCase("admin")) {
			request.getSession().setAttribute("emp_role", "admin");
		}

		if (empRoleAuth != null) {
			request.getSession().setAttribute("emp_role",
					empRoleMap.get(emp.getId()));
			request.getSession().setAttribute("emp_auth",
					empRoleAuth.get("auth"));
		} else {
			throw new UException(SystemEnum.LOGIN_ERROR, "用户未授权");
		}
		MenuVo menu = new MenuVo();
		menu.setMenuHtml(getEmpMenus(emp));

		return menu;
	}

	/**
	 * 获取用户的授权菜单
	 * 
	 * @param emp
	 * @return
	 */

	private String getEmpMenus(Employee emp) {

		// Employee emp =(Employee) request.getAttribute("zycfLoginEmp");

		Authorize auth = new Authorize();
		return auth.getMenuHtml(emp);

	}

	/*
	 * 获取用户的角色权限信息
	 */
	private Map<String, List<String>> getRoleAuth(Employee emp) {
		Map<String, List<String>> authorizationMap = (Map<String, List<String>>) CacheUtils
				.get(GlobalConstants.CACHE_AUTH_METHOD_MAP);

		if (authorizationMap == null) {
			runALL();
			authorizationMap = (Map<String, List<String>>) CacheUtils
					.get(GlobalConstants.CACHE_AUTH_METHOD_MAP);

		}
		Map<String, String> empRoleMap = (Map<String, String>) CacheUtils
				.get(GlobalConstants.CACHE_EMP_ROLE_MAP);

		Map<String, List<String>> empRoleAuth = new HashMap<String, List<String>>();

		empRoleAuth.put("auth",
				authorizationMap.get(empRoleMap.get(emp.getId())));

		return empRoleAuth;
	}

	/**
	 * 初始化启动时候加载全部全局对象
	 * 
	 * @param ctx
	 * @param servletContext
	 * @param parameMap
	 */
	private void runALL() {
		this.runRoleMap();
		this.runFuncMap();
		this.runEmpMap();
		this.runEmpRoleMap();
		this.runAuthorizationMap();
		this.runMethodAuthority();

	}

	/***
	 * 加载全部的角色
	 * 
	 * @param ctx
	 * @param servletContext
	 * @param parameMap
	 */
	private void runRoleMap() {

		Map<String, Role> roleMap = (Map<String, Role>) GlobalConstants.roleMap;

		if (roleMap == null) {
			roleMap = new HashMap<String, Role>();
		}
		// RolesService rolesService = (RolesService)
		// ctx.getBean("rolesService");
		List<Role> list = rolesService.selectAll();
		for (Role role : list) {
			roleMap.put(role.getId(), role);
		}

		CacheUtils.remove(GlobalConstants.CACHE_ROLE_MAP);
		CacheUtils.put(GlobalConstants.CACHE_ROLE_MAP, roleMap);

		GlobalConstants.roleMap.clear();
		for (Role role : list) {
			GlobalConstants.roleMap.put(role.getId(), role);
		}
	}

	/**
	 * 加载所有的函数id与函数名称
	 * 
	 * @param ctx
	 * @param servletContext
	 * @param parameMap
	 */
	private void runFuncMap() {

		Map<Integer, SysFuncList> funcMap = GlobalConstants.funcMap;
		// (Map<Integer,SysFuncList>)CacheUtils.get(GlobalConstants.CACHE_FUNC_MAP);

		// CacheUtils.remove(GlobalConstants.CACHE_FUNC_MAP);

		// FuncListService funcListService = (FuncListService)
		// ctx.getBean("loanService");
		List<SysFuncList> funcList = funcListService.selectAll();
		GlobalConstants.funcMap.clear();
		if (funcMap == null) {
			funcMap = new HashMap<Integer, SysFuncList>();
		}
		for (SysFuncList func : funcList) {
			funcMap.put(func.getId(), func);
			GlobalConstants.funcMap.put(func.getId(), func);

		}
		CacheUtils.remove(GlobalConstants.CACHE_FUNC_MAP);
		CacheUtils.put(GlobalConstants.CACHE_FUNC_MAP, funcMap);
		/*
		 * GlobalConstants.allMenu.clear(); for (SysFuncList func : funcList) {
		 * if(func.getParentId() == 0){ GlobalConstants.allMenu.add(func);
		 * }else{ if(func.getIsMenu() == 1){ Map<Long,SysFuncList> funcMapEh=
		 * (Map
		 * <Long,SysFuncList>)CacheUtils.get(GlobalConstants.CACHE_FUNC_MAP);
		 * 
		 * if (funcMapEh == null) { funcMapEh = GlobalConstants.funcMap; }
		 * 
		 * SysAuthFunc pFunc = funcMapEh.get(func.getParentId()); //SysAuthFunc
		 * pFunc = GlobalConstants.funcMap.get(func.getParentId());
		 * pFunc.setIsChild(true); pFunc.addChild(func); }
		 * 
		 * } }
		 */

	}

	/***
	 * 加载全部的员工
	 * 
	 * @param ctx
	 * @param servletContext
	 * @param parameMap
	 */
	private void runEmpMap() {

		Map<String, Employee> empMap = (Map<String, Employee>) CacheUtils
				.get(GlobalConstants.CACHE_EMP_MAP);

		if (empMap == null) {
			empMap = new HashMap<String, Employee>();
		}
		// EmployeeService employeeService = (EmployeeService)
		// ctx.getBean("employeeService");
		// List<Employee> list = empooy.selectAll();

		List<Employee> list = new ArrayList<Employee>();
		for (Employee emp : list) {
			empMap.put(emp.getId(), emp);
		}

		CacheUtils.remove(GlobalConstants.CACHE_EMP_MAP);
		CacheUtils.put(GlobalConstants.CACHE_EMP_MAP, empMap);

	}

	/**
	 * 加载员工角色关系
	 * 
	 * @param ctx
	 * @param servletContext
	 */
	private void runEmpRoleMap() {

		Map<String, String> empRoleMap = (Map<String, String>) CacheUtils
				.get(GlobalConstants.CACHE_EMP_ROLE_MAP);

		if (empRoleMap == null) {
			empRoleMap = new HashMap<String, String>();
		}
		// EmployeeRoleService employeeRoleService = (EmployeeRoleService)
		// ctx.getBean("employeeRoleService");
		List<EmpRoleKey> list = employeeRoleService.selectAll();
		for (EmpRoleKey emp : list) {
			empRoleMap.put(emp.getEmpId(), emp.getRoleId());
			GlobalConstants.empRoleMap.put(emp.getEmpId(), emp.getRoleId());
		}

		CacheUtils.remove(GlobalConstants.CACHE_EMP_ROLE_MAP);
		CacheUtils.put(GlobalConstants.CACHE_EMP_ROLE_MAP, empRoleMap);

	}

	/**
	 * 加载角色权限表
	 * 
	 * @param ctx
	 * @param servletContext
	 */
	private void runAuthorizationMap() {

		Map<String, List<SysAuthorization>> authorizationMap = (Map<String, List<SysAuthorization>>) CacheUtils
				.get(GlobalConstants.CACHE_AUTH_MAP);

		if (authorizationMap == null) {
			authorizationMap = new HashMap<String, List<SysAuthorization>>();
		}

		List<SysAuthorization> list = systemAuthorizationService.selectAll();
		for (SysAuthorization rolemp : list) {
			if (authorizationMap.containsKey(rolemp.getRoleId())) {
				authorizationMap.get(rolemp.getRoleId()).add(rolemp);

			} else {
				List array = new ArrayList<SysAuthorization>();
				array.add(rolemp);
				authorizationMap.put(rolemp.getRoleId(), array);

			}

		}

		CacheUtils.remove(GlobalConstants.CACHE_AUTH_MAP);
		CacheUtils.put(GlobalConstants.CACHE_AUTH_MAP, authorizationMap);

	}

	private void runMethodAuthority() {

		Map<String, List<String>> authorizationMap = (Map<String, List<String>>) CacheUtils
				.get(GlobalConstants.CACHE_AUTH_METHOD_MAP);

		if (authorizationMap == null) {
			authorizationMap = new HashMap<String, List<String>>();
		}
		List<SysAuthorization> list = systemAuthorizationService.selectAll();

		Map<Integer, SysFuncList> funcMap = (Map<Integer, SysFuncList>) CacheUtils
				.get(GlobalConstants.CACHE_FUNC_MAP);

		for (SysAuthorization rolemp : list) {
			if (authorizationMap.containsKey(rolemp.getRoleId())) {
				if (funcMap.get(rolemp.getFuncId()).getPrivilige() != null)
					authorizationMap.get(rolemp.getRoleId()).add(
							funcMap.get(rolemp.getFuncId()).getPrivilige());

			} else {
				if (funcMap.get(rolemp.getFuncId()).getPrivilige() != null) {
					List array = new ArrayList<String>();
					array.add(funcMap.get(rolemp.getFuncId()).getPrivilige());
					authorizationMap.put(rolemp.getRoleId(), array);
				}

			}

		}

		CacheUtils.remove(GlobalConstants.CACHE_AUTH_METHOD_MAP);
		CacheUtils.put(GlobalConstants.CACHE_AUTH_METHOD_MAP, authorizationMap);
	}

	@Override
	@Transactional
	public Message addEmployee(HttpServletRequest request, Employee emp) {

		if (emp.getLoginName() == null)
			return new Message(1, "新增用户错误");

		if (employeeService.selectByLoginName(emp.getLoginName()) != null)
			return new Message(2, "用户名已经存在");

		String salt = Password.getSalt(null);
		String defaultPassword = "111111";
		String password = Password.getPassphrase(salt, defaultPassword);
		emp.setClientcode("ZYCF");
		emp.setRegisterdate(new Date());
		emp.setLastlogindate(new Date());
		emp.setSalt(salt);
		emp.setPassphrase(password);
		emp.setId(UUID.randomUUID().toString().toUpperCase());

		if (emp.getEnabled() == null)
			emp.setEnabled(Boolean.TRUE);

		if (emp.getNeedchangepassword() == null)
			emp.setNeedchangepassword(Boolean.FALSE);

		TextCipher cipher = DESTextCipher.getDesTextCipher();
		try {
			emp.setIdnumber(cipher.encrypt(emp.getIdnumber()));
			emp.setMobile(cipher.encrypt(emp.getMobile()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.EN_CODE_MD5_EXCEPTION, "身份证手机加密错误");
		}

		return employeeService.addEmployee(emp);

	}

	/**
	 * 新建角色
	 */
	@Override
	@Transactional
	public Message addRole(RoleVo roleVo) {

		Role role = roleVo.getRole();
		if (role.getName() == null || role.getName().equalsIgnoreCase(""))
			return new Message(1, "角色名称不存在");

		Role rol = roleMapper.selectByName(role);
		if (rol != null)
			return new Message(2, "角色已存在");

		role.setClientcode("ZYCF");
		role.setId(UUID.randomUUID().toString().toUpperCase());
		role.setPriviliges(AuthorityHelper.makeAuthority(roleVo.getPriviliges()));

		int re = rolesService.addRole(role);
		if (re == 1)
			return new Message(0, "添加角色成功");
		else
			return new Message(3, "添加角色失败");
	}

	/**
	 * 员工编辑
	 * 
	 * @param request
	 * @param emp
	 * @return
	 */
	@Override
	@Transactional
	public Message updateEmployee(HttpServletRequest request, Employee emp) {
		if (emp.getLoginName() == null || emp.getLoginName() == "")
			return new Message(2, "用户名为空");
		TextCipher cipher = DESTextCipher.getDesTextCipher();
		if (emp.getPassphrase() != null || emp.getPassphrase() != "") {
			emp.setSalt(employeeMapper.selectByLoginName(emp.getLoginName())
					.getSalt());

			emp.setPassphrase(Password.getPassphrase(emp.getSalt(),
					emp.getPassphrase()));
		}
		try {
			if (emp.getIdnumber() != null || emp.getIdnumber() != "")
				emp.setIdnumber(cipher.encrypt(emp.getIdnumber()));
			if (emp.getMobile() != null || emp.getMobile() != "")
				emp.setMobile(cipher.encrypt(emp.getMobile()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.EN_CODE_MD5_EXCEPTION, "身份证手机加密错误");
		}

		emp.setId(employeeMapper.selectByLoginName(emp.getLoginName()).getId());

		if (employeeMapper.updateByPrimaryKeySelective(emp) > 0)
			return new Message(0, "更新用户成功");
		else
			return new Message(1, "更新失败");
	}

	/*
	 * 列出所有权限 (non-Javadoc)
	 * 
	 * @see com.zhongyang.java.biz.EmployeeBiz#selectAllSysAuthorities()
	 */

	private Map<String, List<SysAuthorities>> selectAllSysAuthorities() {
		List<SysAuthorities> list = sysAuthoritiesMapper.selectAll();

		Map<String, List<SysAuthorities>> map = new HashMap<String, List<SysAuthorities>>();

		for (SysAuthorities auth : list) {
			String cato = AuthType.valueOf(auth.getType()).getDescription();
			if (map.containsKey(cato))
				map.get(cato).add(auth);
			else {
				List<SysAuthorities> l = new ArrayList<SysAuthorities>();
				l.add(auth);
				map.put(cato, l);
			}
		}

		return map;
	}

	/**
	 * 列出所有权限，及特定角色的权限
	 */

	@Override
	public Map<String, List<SysAuthorities>> getRoleAuthorities(Role role) {
		if (role.getId() == null || role.getId() == "") {

			return this.selectAllSysAuthorities();
		}

		role = roleMapper.selectByPrimaryKey(role.getId());

		List<SysAuthorities> list = sysAuthoritiesMapper.selectAll();

		Map<String, List<SysAuthorities>> map = new HashMap<String, List<SysAuthorities>>();

		for (SysAuthorities auth : list) {

			String cato = AuthType.valueOf(auth.getType()).getDescription();

			auth.setCato(cato);
			if (AuthorityHelper.checkAuthority(auth.getPindex(),
					role.getPriviliges()))
				auth.setFlag(true);

			if (map.containsKey(cato))
				map.get(cato).add(auth);
			else {
				List<SysAuthorities> l = new ArrayList<SysAuthorities>();
				l.add(auth);
				map.put(cato, l);
			}
		}

		return map;
	}

	public List<Employee> getRoleEmp(Role role) {

		return employeeMapper.selectByRole(role);

	}

	/**
	 * 获取员工权限
	 * 
	 * @param empId
	 * @return
	 */
	public String getEmpAuthorities(String empId) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("empId", empId);

		List<Role> list = roleMapper.selectUserAuth(map);

		return AuthorityHelper.unifyAuthorities(list);
	}

	/**
	 * 获取员工列表
	 */
	@Override
	public Page<Employee> getEmpList(HttpServletRequest request,
			Page<Employee> page) {

		TextCipher decipher = DESTextCipher.getDesTextCipher();
		try {
			String orgId = (String)page.getParams().get("orgId");
			Organization organization=new Organization();
			List<Employee> list=new ArrayList<Employee>();
			if("".equals(orgId)){
				list = employeeMapper.selectAll(page);
			}
			else{
				organization.setId(orgId);
				Organization queryOrganization = organizationService.queryOrganizationByParams(organization);
				List<Employee> result=new ArrayList<Employee>();
				List<Employee> re = getRecursion(result,queryOrganization);
				int end=page.getPageNo()*page.getPageSize();
				
				int start=page.getPageNo()*page.getPageSize()-page.getPageSize();
				
				//计算分页页数与总记录数
				page.setTotalPage(re.size()/page.getPageSize()+1);
				page.setTotalRecord(re.size());
				
				for (int i=0;i<re.size();i++) {
					if(start<=i&&i<end){
						list.add(re.get(i));
					}
				}
				
			}
			
			for (Employee emp : list) {
				emp.setIdnumber(MStringUtils.decrypt(
						decipher.decrypt(emp.getIdnumber()), 7, 15, '*'));
				emp.setMobile(MStringUtils.decrypt(
						decipher.decrypt(emp.getMobile()), 4, 8, '*'));

				emp.setLastDate(FormatUtils.millisDateFormat(emp
						.getLastlogindate()));
				emp.setRegDate(FormatUtils.millisDateFormat(emp
						.getRegisterdate()));

			}
			page.setResults(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	List<Employee> getRecursion(List<Employee> result,Organization organization){
		
		Page<Employee> page=new Page<Employee>();
		page.setPageNo(1);
		page.setPageSize(Integer.MAX_VALUE);
		page.getParams().put("orgId", organization.getId());
		List<Employee> list = employeeMapper.selectAll(page);
		if(list.size()!=0)
			result.addAll(list);
		
		if(organization.getChildren().size()!=0){
			for (Organization org : organization.getChildren()) {
				getRecursion(result,org);
			}
		}
		return result;
	}

	/**
	 * 员工权限页面初始化，获取所有角色，角色对应的权限以及该角色下所属的员工。
	 */
	@Override
	public Map roleAuthInit() {

		Map<String, Object> map = new HashMap<String, Object>();

		List<Role> roles = roleMapper.selectAll();

		Map<String, List<SysAuthorities>> auth = this.getRoleAuthorities(roles
				.get(0));

		List<Employee> emps = this.getRoleEmp(roles.get(0));
		map.put("roles", roles);
		map.put("auths", auth);
		map.put("emps", emps);
		return map;
	}

	@Override
	@Transactional
	public Message updateRoleAuths(RoleVo roleVo) {
		Role role = roleVo.getRole();
		role = rolesService.selectByPrimarykey(role.getId());
		role.setPriviliges(AuthorityHelper.makeAuthority(roleVo.getPriviliges()));

		int result = rolesService.updateRoleByPrimarykey(role);

		if (result > 0)

			return new Message(1, "更新成功");

		return new Message(0, "更新失败");
	}

	@Override
	public Map getRoleAuthsDetails(Role role) {
		Map map = new HashMap<String, Object>();
		map.put("auths", this.getRoleAuthorities(role));
		map.put("emps", employeeMapper.selectByRole(role));
		return map;
	}

	@Override
	@Transactional
	public Message dispatchEmpRole(String empid, String roleid,String orgId) {
		if (empid == null || empid == "" || roleid == null || roleid == "")
			return new Message(0, "用户或角色不存在");

		EmpRoleKey record = new EmpRoleKey();
		record.setEmpId(empid);
		record.setRoleId(roleid);
		EmpRoleKey queryEmpRole = empRoleMapper.selectEmpRoleKey(record);
		int result=0;
		if(queryEmpRole==null){
			result = empRoleMapper.insert(record);
		}
		
		Employee emp=new Employee();
		emp.setOrgId(orgId);
		emp.setId(empid);
		int count = employeeMapper.updateByPrimaryKeySelective(emp);

		if (result > 0||count>0)
			return new Message(1, "权限分配或隶属机构修改成功");
		return new Message(2, "数据库错误");
	}

	@Override
	public List<Role> getAllRoles() {
		return rolesService.selectAll();
	}

	@Override
	public Map<String, Object> getUserRoles(Employee emp) {
		Map<String, Object> map = new HashMap<String, Object>();

		Employee employee = employeeMapper.selectByParams(emp);
		TextCipher decipher = DESTextCipher.getDesTextCipher();
		if (employee != null) {
			try{
			employee.setIdnumber(MStringUtils.decrypt(
					decipher.decrypt(employee.getIdnumber()), 7, 15, '*'));
			employee.setMobile(MStringUtils.decrypt(
					decipher.decrypt(employee.getMobile()), 4, 8, '@'));

			employee.setLastDate(FormatUtils.millisDateFormat(employee
					.getLastlogindate()));
			employee.setRegDate(FormatUtils.millisDateFormat(employee
					.getRegisterdate()));
			}catch(Exception e){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"数据错误");
			}
		}
		map.put("roles", getAllRoles());
		map.put("employee", employee);
		Map<String, String> params = new HashMap<String, String>();
		params.put("empId", employee.getId());
		map.put("userRoles", roleMapper.selectUserAuth(params));

		return map;
	}

	@Override
	@Transactional
	public Message updatePass(HttpServletRequest request,EmployeeVo emp) {
		Employee employee =(Employee) request.getSession().getAttribute("zycfLoginEmp");
		employee = employeeService.selectByLoginName(employee.getLoginName());
		if (emp.getOldpasswd() != null || emp.getOldpasswd()!= "") {
			
			String oldPass = Password.getPassphrase(employee.getSalt(), emp.getOldpasswd());
			
			if(!oldPass.equalsIgnoreCase(employee.getPassphrase()))
				return new Message(3,"旧密码错误");
		}
		employee.setPassphrase(Password.getPassphrase(employee.getSalt(), emp.getPassphrase()));

		if (employeeMapper.updateByPrimaryKeySelective(employee) > 0)
			return new Message(0, "修改密码成功");
		else
			return new Message(1, "修改密码失败");
	}

	@Override
	public Message emplpyeeBan(Employee emp) {
		Message message=new Message();
		emp.setEnabled(false);
		int count = employeeService.updateByParams(emp);
		if(count==1){
			message.setCode(1);
			message.setMessage("禁用成功");
		}else{
			message.setCode(0);
			message.setMessage("禁用失败");
		}
		return message;
	}
}
