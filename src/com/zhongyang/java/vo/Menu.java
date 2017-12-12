package com.zhongyang.java.vo;

import java.util.List;

public class Menu {

	private String funcUrl;
	private String funcName;
	private Integer id;
	private Integer fucLevel;
	private Integer parentId;
	private List<Menu> child;
	private boolean isChild;
	private String hclass;
	public String getHclass() {
		return hclass;
	}
	public void setHclass(String hclass) {
		this.hclass = hclass;
	}
	private Integer isMenu;
	
	public Integer getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}
	public boolean isChild() {
		return isChild;
	}
	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}
	public String getFuncUrl() {
		return funcUrl;
	}
	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFucLevel() {
		return fucLevel;
	}
	public void setFucLevel(Integer fucLevel) {
		this.fucLevel = fucLevel;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public List<Menu> getChild() {
		return child;
	}
	public void setChild(List<Menu> child) {
		this.child = child;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "[funcUrl ="+this.getFuncUrl()+",funcName="+this.getFuncName()+",funcLevel"+this.getFucLevel()
				+",parentId="+this.getParentId()+",isMenu="+this.getIsMenu()+"]";
		return str;
	}
	
}
