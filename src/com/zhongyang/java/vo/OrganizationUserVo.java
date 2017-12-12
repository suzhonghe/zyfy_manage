package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.List;

import com.zhongyang.java.pojo.Invest;

public class OrganizationUserVo {
	
	private String id;
	
	private String name;//员工姓名
	
	private String mobile;//员工手机号码
	
	private List<Invest>invests;

	public List<Invest> getInvests() {
		return invests;
	}

	public void setInvests(List<Invest> invests) {
		this.invests = invests;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
