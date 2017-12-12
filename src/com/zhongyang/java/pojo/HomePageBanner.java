package com.zhongyang.java.pojo;

import java.util.Date;

/**
 * 
* @Title: HomePageBanner.java 
* @Package com.zhongyang.java.pojo 
* @Description:首页Banner图实体
* @author 苏忠贺   
* @date 2016年1月18日 上午11:07:50 
* @version V1.0
 */
public class HomePageBanner {
	
	private String id;
	
	private int serialNumber;
	
	private String photoName;//图片名称
	
	private String accessAddress;//图片存储路径 
	
	private String jumpAddress;//跳转路径
	
	private Date time;
	
	private String type;//图片类型(app_banner  pc_banner)
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getAccessAddress() {
		return accessAddress;
	}

	public void setAccessAddress(String accessAddress) {
		this.accessAddress = accessAddress;
	}

	public String getJumpAddress() {
		return jumpAddress;
	}

	public void setJumpAddress(String jumpAddress) {
		this.jumpAddress = jumpAddress;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
