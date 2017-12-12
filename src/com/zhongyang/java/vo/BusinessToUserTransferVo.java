package com.zhongyang.java.vo;

import java.util.ArrayList;
import java.util.List;


/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月13日 下午5:01:33
* 类说明
*/
public class BusinessToUserTransferVo {
    
	private String availableAmount;//可用金额
    private String balanceAmount;//账户余额：
    private String frozenAmount;//冻结余额：
    List<UntredtedApplicationVo> applications = new ArrayList();
    
	public String getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
    }
	public List<UntredtedApplicationVo> getApplications() {
		return applications;
	}
	public void setApplications(List<UntredtedApplicationVo> applications) {
		this.applications = applications;
	}
	
  
}
