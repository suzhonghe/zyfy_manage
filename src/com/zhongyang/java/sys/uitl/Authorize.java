package com.zhongyang.java.sys.uitl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.Role;
import com.zhongyang.java.pojo.SysAuthorization;
import com.zhongyang.java.pojo.SysFuncList;
import com.zhongyang.java.vo.Menu;

public class Authorize {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public List<Menu> getEmpMenu(Employee emp){
		
		Map<String,String> rolemp = (Map<String,String>) CacheUtils.get(GlobalConstants.CACHE_EMP_ROLE_MAP);
		Map<String,Role> rolemap = (Map<String,Role>) CacheUtils.get(GlobalConstants.CACHE_ROLE_MAP);
		if(rolemp.get(emp.getId())==null)
		return null;
		
		return this.getRoleMenu(rolemap.get(rolemp.get(emp.getId())));
	}
	
	public  List<Menu> getRoleMenu(Role role){
		
		Map<String,List<SysAuthorization>> authorizationMap = (Map<String,List<SysAuthorization>>)CacheUtils.get(GlobalConstants.CACHE_AUTH_MAP);
		Map<Integer, SysFuncList> funcMap = (Map<Integer, SysFuncList>) CacheUtils.get(GlobalConstants.CACHE_FUNC_MAP);
		
		if(authorizationMap == null)
			authorizationMap = GlobalConstants.authMap;
		if(funcMap ==  null)
			funcMap = GlobalConstants.funcMap;
		
		List<SysAuthorization> roleAuth = authorizationMap.get(role.getId());
		if(roleAuth == null)
			return null;


		List<SysFuncList> roleFuncs = new ArrayList<SysFuncList>();
		
		for(SysAuthorization auth : roleAuth){
			SysFuncList func = funcMap.get(auth.getFuncId());
			if(func.getIsMenu().intValue() == 1)
				roleFuncs.add(funcMap.get(auth.getFuncId()));
		}

		MenuTreeFactory menuTreeFactory = new MenuTreeFactory(roleFuncs);
		return menuTreeFactory.bulidMenus();
	}
	
	public List<Menu> getAdminMenus(){
		
		Map<Integer, SysFuncList> funcList =(Map<Integer,SysFuncList>) CacheUtils.get(GlobalConstants.CACHE_FUNC_MAP);

		
		List<SysFuncList> funcs = new ArrayList<SysFuncList>();
		
		for(Map.Entry<Integer, SysFuncList> entry : funcList.entrySet())
		{
			
			SysFuncList func = (SysFuncList)entry.getValue();
			if(func.getIsMenu().intValue()==1)
			funcs.add(entry.getValue());
		}
		
		MenuTreeFactory menuTreeFactory = new MenuTreeFactory(funcs);
		
		return menuTreeFactory.bulidMenus();
	}
	
	public String getMenuHtml(Employee emp){
		List<Menu> menus = new ArrayList<Menu>();
		Authorize auth = new Authorize();
		if(emp.getLoginName().equals("admin"))
			return getHtml(auth.getAdminMenus());
		
	
		
		return getHtml(auth.getEmpMenu(emp));
		
	}
	
	private String getHtml(List<Menu> menus){
	
		
		
		String menuHtml ="";
		for(Menu m: menus){
			menuHtml+="<li><h3 class=\""+m.getHclass()+"\"><span>"+m.getFuncName()+"</span></h3><ul>";
			if(m.getChild()!=null)
			{
				for(Menu child: m.getChild()){
					menuHtml+="<li>"+child.getFuncName()+"</li>";
				}
			}
			menuHtml+="</ul></li>";
		}
		
		return menuHtml;
	}
}
