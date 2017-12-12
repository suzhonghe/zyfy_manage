package com.zhongyang.java.sys.uitl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.Role;
import com.zhongyang.java.pojo.SysAuthorization;
import com.zhongyang.java.pojo.SysFuncList;

public class GlobalConstants {

	// public static final String CACHE_ROLE_URL_MAP = "roleUrlMap";
	public static final String CACHE_ROLE_MAP = "roleMap";
	public static final String CACHE_FUNC_MAP = "funcMap";
	public static final String CACHE_EMP_MAP = "employeeMap";
	public static final String CACHE_EMP_ROLE_MAP = "empRoleMap";
	public static final String CACHE_AUTH_MAP = "authMap";
	public static final String CACHE_ROLE_AUTH="roleAuthMap";
	public static final String CACHE_AUTH_METHOD_MAP = "authMethodMap";
	
	public static final String LOGIN_EMP_COUNTER = "loginEmpCounter";
	
	public static int DAYSOFMONTH = 30;
	

	public static Map<Integer, SysFuncList> funcMap = new LinkedHashMap<Integer, SysFuncList>();
	public static Map<String, Employee> employeeMap = new ConcurrentHashMap<String, Employee>();
	public static Map<String, Role> roleMap = new ConcurrentHashMap<String, Role>();
	public static List<SysAuthorization> allMenu = new LinkedList<SysAuthorization>();
	public static Map<String, String> empRoleMap = new ConcurrentHashMap<String, String>();
	public static Map<String, List<SysAuthorization>> authMap = new ConcurrentHashMap<String, List<SysAuthorization>>();

	public static final String EXCLUDE_URL_INIT="exclude";
	public static final String EXCLUDE_DIRECTORY_REGEX="^/css/|^/images/|^/js/|^/upload/|^/img/|^/script/|^/interaction/|^/manualCallback/|^/debtinteraction/";


}
