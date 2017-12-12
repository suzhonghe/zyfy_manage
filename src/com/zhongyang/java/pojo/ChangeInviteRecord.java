package com.zhongyang.java.pojo;

import java.util.Date;

public class ChangeInviteRecord {
	
	private int id;//主键
	private String oldUserId; //被变更人用户ID
	private String newUserId; //变更后用户ID
	private String oldPhoneNo; //被变更人手机号码
	private String newPhoneNo; //变更后手机号码
	private String operator; // 操作人
	private String type; //变更类型
	private Date createTime; //创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOldUserId() {
		return oldUserId;
	}
	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}
	public String getNewUserId() {
		return newUserId;
	}
	public void setNewUserId(String newUserId) {
		this.newUserId = newUserId;
	}
	public String getOldPhoneNo() {
		return oldPhoneNo;
	}
	public void setOldPhoneNo(String oldPhoneNo) {
		this.oldPhoneNo = oldPhoneNo;
	}
	public String getNewPhoneNo() {
		return newPhoneNo;
	}
	public void setNewPhoneNo(String newPhoneNo) {
		this.newPhoneNo = newPhoneNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	

}
