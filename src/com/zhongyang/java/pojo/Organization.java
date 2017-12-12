package com.zhongyang.java.pojo;

import java.util.Date;
import java.util.List;

/**
 * 
* @Title: Organization.java 
* @Package com.zhongyang.java.pojo 
* @Description:机构实体
* @author 苏忠贺   
* @date 2016年3月9日 下午4:16:13 
* @version V1.0
 */
public class Organization {
	
	private String id;
	
	private String name;
	
	private String description;
	
	private Date createTime;
	
	private Integer level;
	
	private String parantOrgId;
	
	private String category;
	
	private String address;
	
	private String tel;
	
	private boolean del;
	
	private List<Organization>children;

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParantOrgId() {
		return parantOrgId;
	}

	public void setParantOrgId(String parantOrgId) {
		this.parantOrgId = parantOrgId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
