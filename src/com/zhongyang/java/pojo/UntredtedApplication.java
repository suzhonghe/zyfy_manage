package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.fund.FundRecordStatus;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月14日 下午5:01:03
* 类说明
*/
public class UntredtedApplication {
	  private String id;
	  private String account;
	  private String operationEmplyee;
	  private String telephone;
	  private BigDecimal tradeAmount;
	  private FundRecordStatus status;
	  private Date createTime;
	  private String opreation;
	  private String userName;
	  private String userId;
	  private String ordreId;
	  private String description;
	  private String userUmpAccount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOperationEmplyee() {
		return operationEmplyee;
	}
	public void setOperationEmplyee(String operationEmplyee) {
		this.operationEmplyee = operationEmplyee;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public FundRecordStatus getStatus() {
		return status;
	}
	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOpreation() {
		return opreation;
	}
	public void setOpreation(String opreation) {
		this.opreation = opreation;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrdreId() {
		return ordreId;
	}
	public void setOrdreId(String ordreId) {
		this.ordreId = ordreId;
	}
	public String getUserUmpAccount() {
		return userUmpAccount;
	}
	public void setUserUmpAccount(String userUmpAccount) {
		this.userUmpAccount = userUmpAccount;
	}
	
	
}
