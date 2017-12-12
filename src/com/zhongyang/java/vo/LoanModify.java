package com.zhongyang.java.vo;
/**
 * 
* @Title: ModifyLoan.java 
* @Package com.zhongyang.java.vo 
* @Description: 标的更新包装类
* @author 苏忠贺   
* @date 2015年12月17日 下午12:58:39 
* @version V1.0
 */
public class LoanModify {
	
	private String project_id;//标的号	Y
	
	private String change_type;  // Y 01：更新标的	
								//02：标的融资人 即为借款人，借款人不一定是资金使用方（注意：仅限建标状态下可以替换。）
								//03：标的代偿方 
								//04：标的资金使用方 即为标的资金使用方，个人和企业均支持
								//每次操作只能选择一种更新类型；
	private String project_state;//取值范围：	0：开标、1：投标中、2：还款中、3：已还款、4：结束（前提条件：余额为0）	建标后，标的状态为[建标成功]，由商户设定为开标状态，然后在更新为投标中才可以投资；	change_type=01时，才使用	N
	
	private String project_name;//标的名称	change_type=01时，才使用；标的状态处于[投标中]，则不可更改；标的状态处于投标中后，这个参数不可再传递	N
	
	private String project_amount;//标的金额，以分为单位	change_type=01时，才使用；标的状态处于[投标中]，则不可更改；标的状态处于投标中后，这个参数不可再传递	N

	private String project_expire_date;//标的有效期格式:YYYYMMDD，期望的满标日期  change_type=01时，才使用；标的状态处于[投标中]，则不可更改；标的状态处于投标中后，这个参数不可再传递	N
	
	private String option_type;//操作借款人、担保方、资金使用方时，必须传。0-新增（当操作的是借款人时，操作含义是替换，仅限建标状态下可以替换。）；	1-注销	N

	private String loan_user_id;//标的融资人资金账户托管平台用户号	联动开立的用户号	change_type=02时，必传	N

	private String loan_account_id;//联动开立的账户号，商户可以不传递，如果传递必须和开户时返回的保持一致，如果未返回则忽略此字段	change_type=02时，必传	N
	
	private String loan_acc_type;//01 个人	02 商户	默认为：01个人	N
	
	private String warranty_user_id;//标的代偿方平台用户号change_type=03时，必传	N
	
	private String warranty_account_id;//商户可以不传递，如果传递必须和开户时返回的保持一致，如果未返回则忽略此字段	change_type=03时，必传	N

	private String receive_user_id;//标的资金使用方平台用户号	change_type=04时，必传	N

	private String receive_account_id;//商户可以不传递，如果传递必须和开户时返回的保持一致，如果未返回则忽略此字段	change_type=04时，必传	N
	
	private String receive_acc_type;//01 个人 	02 商户	默认为01 个人	注：资金使用方平台用户号和资金使用方账户类型必须同时传递或不传递	N

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getChange_type() {
		return change_type;
	}

	public void setChange_type(String change_type) {
		this.change_type = change_type;
	}

	public String getProject_state() {
		return project_state;
	}

	public void setProject_state(String project_state) {
		this.project_state = project_state;
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

	public String getOption_type() {
		return option_type;
	}

	public void setOption_type(String option_type) {
		this.option_type = option_type;
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
		return "LoanModify [project_id=" + project_id + ", change_type=" + change_type + ", project_state="
				+ project_state + ", project_name=" + project_name + ", project_amount=" + project_amount
				+ ", project_expire_date=" + project_expire_date + ", option_type=" + option_type + ", loan_user_id="
				+ loan_user_id + ", loan_account_id=" + loan_account_id + ", loan_acc_type=" + loan_acc_type
				+ ", warranty_user_id=" + warranty_user_id + ", warranty_account_id=" + warranty_account_id
				+ ", receive_user_id=" + receive_user_id + ", receive_account_id=" + receive_account_id
				+ ", receive_acc_type=" + receive_acc_type + "]";
	}
}
