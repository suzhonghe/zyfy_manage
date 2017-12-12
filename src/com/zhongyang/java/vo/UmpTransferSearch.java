package com.zhongyang.java.vo;

public class UmpTransferSearch {
	
	private String ret_code;//返回码
	
	private String ret_msg;//返回信息
	
	private String order_id;//订单号
	
	private String mer_check_date;//对账日期，格式YYYYMMDD
	
	private String mer_date;//商户生成订单的日期，格式YYYYMMDD
	
	private String trade_no;//交易平台流水号
	
	private String busi_type;//业务类型 取值范围：	01充值 	02提现 	03标的转账 	04转账

	private String amount;//交易金额	单位：元
	
	private String orig_amt;//原交易金额	单位：元
	
	private String com_amt;//手续费	单位：元
	
	/*手续费承担方类型	变长3	
	1 前向手续费：交易方承担
	2 前向手续费：平台商户（手续费账户）承担

	-99 按手续费账户上线时间区分交易，参照如下说明：
	新交易：非前向收费
	存量交易：无法区分，详见商户协议里手续费相关部分。*/
	private String com_amt_type;
	
	/*交易状态：01充值：
	0初始、2成功、3失败、4不明、5、交易关闭（超过7个自然日的初始状态交易会按此状态返回）、6其他

	02提现：
	提现交易中间状态：0初始、1受理中、4不明（待确认）、12已冻结、13待解冻、14财务已审核、6其他（需人工查询跟进）
	提现交易最终态：
	成功：2成功
	失败：3失败、5交易关闭（提现）、15财务审核失败

	03标的转账：
	0初始、2成功、3失败、4不明、5、交易关闭、6其他
	04转账：
	0初始、2成功、3失败、4不明、5、交易关闭、6其他
*/
	private String tran_state;//

	private String sms_num;//短信个数

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMer_check_date() {
		return mer_check_date;
	}

	public void setMer_check_date(String mer_check_date) {
		this.mer_check_date = mer_check_date;
	}

	public String getMer_date() {
		return mer_date;
	}

	public void setMer_date(String mer_date) {
		this.mer_date = mer_date;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getBusi_type() {
		return busi_type;
	}

	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrig_amt() {
		return orig_amt;
	}

	public void setOrig_amt(String orig_amt) {
		this.orig_amt = orig_amt;
	}

	public String getCom_amt() {
		return com_amt;
	}

	public void setCom_amt(String com_amt) {
		this.com_amt = com_amt;
	}

	public String getCom_amt_type() {
		return com_amt_type;
	}

	public void setCom_amt_type(String com_amt_type) {
		this.com_amt_type = com_amt_type;
	}

	public String getTran_state() {
		return tran_state;
	}

	public void setTran_state(String tran_state) {
		this.tran_state = tran_state;
	}

	public String getSms_num() {
		return sms_num;
	}

	public void setSms_num(String sms_num) {
		this.sms_num = sms_num;
	}
}
