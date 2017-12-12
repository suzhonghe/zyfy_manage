package com.zhongyang.java.pojo;

import java.util.Date;

/**
 * 
* @Title: ProofPhoto.java 
* @Package com.zhongyang.java.pojo 
* @Description:证明实体类 
* @author 苏忠贺   
* @date 2015年12月24日 上午10:05:28 
* @version V1.0
 */
public class ProofPhoto {
	
	private String id;// varchar(36) NOT NULL
	
	private String photoName;// 图片名称,
	
	private String urlPath;//URL路径,
	
	private String projectId;//项目ID,
	
	private Date submitTime;//提交时间,

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
}
