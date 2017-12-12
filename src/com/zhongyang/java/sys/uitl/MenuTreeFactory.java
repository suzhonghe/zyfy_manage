package com.zhongyang.java.sys.uitl;

import java.util.ArrayList;
import java.util.List;

import com.zhongyang.java.pojo.SysFuncList;
import com.zhongyang.java.vo.Menu;

public class MenuTreeFactory {

	List<SysFuncList> funcs;
	List<Menu> rolemenus;
	
	public MenuTreeFactory(List<SysFuncList> funcs){
		this.funcs = funcs;
		this.menuGenerator(funcs);
		
	}
	
	private void menuGenerator(List<SysFuncList> funcs){
		
		this.rolemenus = new ArrayList<Menu>();
		
		for(SysFuncList func: funcs)
			
			
		{		Menu menu = new Menu();
	//	menu.setChild(false);
		menu.setFucLevel(func.getFucLevel());
		menu.setFuncName(func.getFuncName());
		menu.setFuncUrl(func.getFuncUrl());
		menu.setParentId(func.getParentId());
		menu.setId(func.getId());
		menu.setIsMenu(func.getIsMenu());
		menu.setHclass(func.getHclass());
		this.rolemenus.add(menu);
		}
	}
	
	public List<Menu> bulidMenus(){
		
		List<Menu> menus = new ArrayList<Menu>();

		for(Menu menu : rolemenus)
		{

			if(menu.getFucLevel().intValue() == 0){				
				build(menu);
				menus.add(menu);
			}
				
		}
		return menus;
	}
	
	
	private void build(Menu menu){
		
		List<Menu> children = getChildren(menu);
		if(children == null)
			return;
		menu.setChild(children);
		for(Menu m : children)
		{
			build(m);
		}
	
	}
	
	private List<Menu> getChildren(Menu menu){
		List<Menu> children = new ArrayList<Menu>();
		for(Menu m : rolemenus)
		{
			if(menu.getId().equals(m.getParentId()))
				children.add(m);
		}
		return children;
	}
}
