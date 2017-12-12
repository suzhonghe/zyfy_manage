package com.zhongyang.java.pojo;

import java.util.Date;

/**
 * 
* @Title: BannerPhoto.java 
* @Package com.zhongyang.java.pojo 
* @Description:banner图实体 
* @author 苏忠贺   
* @date 2016年1月18日 上午11:13:45 
* @version V1.0
 */
public class BannerPhoto {
	
	private String id;
	
	private String photoName;
	
	private String pathAddress;//
	
	private String jumpAddress;//
	
	private Date time;
	
	private String type;
	
	private Boolean whetherShow;
	
	private int serialNumber;//
	
	private String title;
	
	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Boolean getWhetherShow() {
		return whetherShow;
	}

	public void setWhetherShow(Boolean whetherShow) {
		this.whetherShow = whetherShow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPathAddress() {
		return pathAddress;
	}

	public void setPathAddress(String pathAddress) {
		this.pathAddress = pathAddress;
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
