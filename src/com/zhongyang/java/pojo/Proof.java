package com.zhongyang.java.pojo;

import java.util.Date;

public class Proof {
	private String id;//ID varchar(36) not null
	private String legalPersonIdentityCard;//   LEGALPERSONIDENTITYCARD varchar(255) not null comment '法人身份证'
	private String companyInfo;//   COMPANYINFO varchar(500) default NULL comment '企业信息'
	private String employee;//   EMPLOYEE varchar(255) default NULL comment '上传证明员工'
	private String contractDocument;//   CONTRACTDOCUMENT  varchar(255) not null comment '合同文件'
	private String propertyInfo;//   PROPERTYINFO  varchar(255) not null comment '资产信息'
	private Date submitTime;//   SUBMITTIME   datetime not null comment '提交时间'
	private String requestId;//   REQUEST_ID   varchar(255) default NULL comment '关联认证用户ID'
	private String certificateId;//   CERTIFICATE_ID  varchar(36) default NULL comment '认证ID'
	private String otherDetails;//   OTHERDETAILS  varchar(255) comment '其他资料'
	private String projectId;
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLegalPersonIdentityCard() {
		return legalPersonIdentityCard;
	}
	public void setLegalPersonIdentityCard(String legalPersonIdentityCard) {
		this.legalPersonIdentityCard = legalPersonIdentityCard;
	}
	public String getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getContractDocument() {
		return contractDocument;
	}
	public void setContractDocument(String contractDocument) {
		this.contractDocument = contractDocument;
	}
	public String getPropertyInfo() {
		return propertyInfo;
	}
	public void setPropertyInfo(String propertyInfo) {
		this.propertyInfo = propertyInfo;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public String getOtherDetails() {
		return otherDetails;
	}
	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}
	
}