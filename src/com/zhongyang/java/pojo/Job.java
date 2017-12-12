package com.zhongyang.java.pojo;

import java.util.List;

public class Job {
	
	private String id;
	
	private String duty;
	
	private String description;
	
	private String level;
	
	private String superior;
	
	private List<Job> children;

	public List<Job> getChildren() {
		return children;
	}

	public void setChildren(List<Job> children) {
		this.children = children;
	}

	public String getSuperior() {
		return superior;
	}

	public void setSuperior(String superior) {
		this.superior = superior;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
