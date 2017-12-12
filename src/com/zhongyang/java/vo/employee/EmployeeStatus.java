package com.zhongyang.java.vo.employee;


public enum EmployeeStatus implements EmployeeStatusEnum {
	
	IternEmployee("实习员工", "刚入职，未转正"),
    RegularEmployee("正式员工", "已转正"),
    LeaveEmployee("离职员工", "已经辞职");

	private final String key;

	private EmployeeStatus(String key,String descriptionr) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}

}
