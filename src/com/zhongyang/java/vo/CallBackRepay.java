package com.zhongyang.java.vo;

/**
 * Created by Administrator on 2015/12/28.
 */
public class CallBackRepay extends UmpVO{
        private String order_id;
        private String mer_date;
        private String trade_no;
        private String mer_check_date;
        private String ret_code;
        private String ret_msg;
        
        private String source;


    public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

	public String getOrder_id() { return order_id;}

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getMer_check_date() {
        return mer_check_date;
    }

    public void setMer_check_date(String mer_check_date) {
        this.mer_check_date = mer_check_date;
    }

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

    @Override
    public String toString() {
        return "CallBackRepay{" +
                "order_id='" + order_id + '\'' +
                ", mer_date='" + mer_date + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", mer_check_date='" + mer_check_date + '\'' +
                ", ret_code='" + ret_code + '\'' +
                ", ret_msg='" + ret_msg + '\'' +
                '}';
    }

    public CallBackRepay(String service, String sign_type, String sign, String mer_id, String version, String order_id, String mer_date, String trade_no, String mer_check_date, String ret_code, String ret_msg) {
        super(service, sign_type, sign, mer_id, version);
        this.order_id = order_id;
        this.mer_date = mer_date;
        this.trade_no = trade_no;
        this.mer_check_date = mer_check_date;
        this.ret_code = ret_code;
        this.ret_msg = ret_msg;
    }

    public CallBackRepay() {
    }
}
