package com.zhongyang.java.vo;

public class LoanPublished {
	
	private String project_id;//标的号(1-32位字母或数字)	Y
	
	private String project_name;//标的名称，Y
	
	private String project_amount;//标的金额,以分为单位	Y
	
	private String project_expire_date;//标的有效期，格式:YYYYMMDD，期望的满标日期	N
	
	private String loan_user_id;//标的融资人资金账户托管平台用户号	联动开立的用户号或商户号	当为商户号时loan_acc_type为必填字段，值02	Y
	
	private String loan_account_id;//联动开立的账户号，商户可以不传递，如果传递必须和注册时返回的保持一致，如果注册未返回则忽略此字段	N
	
	private String loan_acc_type;//借款方账户类型01 个人	02 商户 默认为：01个人	N
	
	private String warranty_user_id;//	标的代偿方平台用户号	N
	
	private String warranty_account_id;//联动开立的标的代偿方平台账户号，商户可以不传递，如果传递必须和开户时返回的保持一致，如果未返回则忽略此字段
										//如果要增加多个担保方，请通过标的更新接口增加	N
	private String receive_user_id;//标的资金使用方平台用户号	N
	
	private String receive_account_id;//联动开立的标的资金使用方平台账户号，商户可以不传递，如果传递必须和开户时返回的保持一致，如果未返回则忽略此字段
										//如果要增加多个资金使用方，请通过标的更新接口增加	N
	private String receive_acc_type;//资金使用方账户类型 01 个人02 商户	注：资金使用方平台用户号和资金使用方账户类型必须同时传递或不传递 N

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getProject_amount() {
		return project_amount;
	}

	public void setProject_amount(String project_amount) {
		this.project_amount = project_amount;
	}

	public String getProject_expire_date() {
		return project_expire_date;
	}

	public void setProject_expire_date(String project_expire_date) {
		this.project_expire_date = project_expire_date;
	}

	public String getLoan_user_id() {
		return loan_user_id;
	}

	public void setLoan_user_id(String loan_user_id) {
		this.loan_user_id = loan_user_id;
	}

	public String getLoan_account_id() {
		return loan_account_id;
	}

	public void setLoan_account_id(String loan_account_id) {
		this.loan_account_id = loan_account_id;
	}

	public String getLoan_acc_type() {
		return loan_acc_type;
	}

	public void setLoan_acc_type(String loan_acc_type) {
		this.loan_acc_type = loan_acc_type;
	}

	public String getWarranty_user_id() {
		return warranty_user_id;
	}

	public void setWarranty_user_id(String warranty_user_id) {
		this.warranty_user_id = warranty_user_id;
	}

	public String getWarranty_account_id() {
		return warranty_account_id;
	}

	public void setWarranty_account_id(String warranty_account_id) {
		this.warranty_account_id = warranty_account_id;
	}

	public String getReceive_user_id() {
		return receive_user_id;
	}

	public void setReceive_user_id(String receive_user_id) {
		this.receive_user_id = receive_user_id;
	}

	public String getReceive_account_id() {
		return receive_account_id;
	}

	public void setReceive_account_id(String receive_account_id) {
		this.receive_account_id = receive_account_id;
	}

	public String getReceive_acc_type() {
		return receive_acc_type;
	}

	public void setReceive_acc_type(String receive_acc_type) {
		this.receive_acc_type = receive_acc_type;
	}

	@Override
	public String toString() {
		return "LoanPublished [project_id=" + project_id + ", project_name=" + project_name + ", project_amount="
				+ project_amount + ", project_expire_date=" + project_expire_date + ", loan_user_id=" + loan_user_id
				+ ", loan_account_id=" + loan_account_id + ", loan_acc_type=" + loan_acc_type + ", warranty_user_id="
				+ warranty_user_id + ", warranty_account_id=" + warranty_account_id + ", receive_user_id="
				+ receive_user_id + ", receive_account_id=" + receive_account_id + ", receive_acc_type="
				+ receive_acc_type + "]";
	}
}
