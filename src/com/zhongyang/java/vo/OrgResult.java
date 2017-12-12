package com.zhongyang.java.vo;

import java.util.List;

public class OrgResult {
	
	String name;
	List<OrgResult>children;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<OrgResult> getChildren() {
		return children;
	}
	public void setChildren(List<OrgResult> children) {
		this.children = children;
	}
}
