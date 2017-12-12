package com.zhongyang.java.vo;

/**
 * Created by Matthew on 2015/12/29.
 */
public class ProjectTransfer {

    private String order_id;//商户订单号
    private String mer_date;//商户订单日期
    private String project_id;//标的号
    private String project_account_id;//标的账户号
    private String serv_type;//业务类型
    private String trans_action;//转账方向
    private String partic_type;//转账方类型
    private String partic_acc_type;//转账方账户类型
    private String partic_user_id;//转账方用户号（个人）或商户号（商户）
    private String partic_account_id;//转账方账户号
    private long amount;//金额
    private String ret_url;
    private String notify_url;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMer_date() {
        return mer_date;
    }

    public void setMer_date(String mer_date) {
        this.mer_date = mer_date;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_account_id() {
        return project_account_id;
    }

    public void setProject_account_id(String project_account_id) {
        this.project_account_id = project_account_id;
    }

    public String getServ_type() {
        return serv_type;
    }

    public void setServ_type(String serv_type) {
        this.serv_type = serv_type;
    }

    public String getTrans_action() {
        return trans_action;
    }

    public void setTrans_action(String trans_action) {
        this.trans_action = trans_action;
    }

    public String getPartic_type() {
        return partic_type;
    }

    public void setPartic_type(String partic_type) {
        this.partic_type = partic_type;
    }

    public String getPartic_acc_type() {
        return partic_acc_type;
    }

    public void setPartic_acc_type(String partic_acc_type) {
        this.partic_acc_type = partic_acc_type;
    }

    public String getPartic_user_id() {
        return partic_user_id;
    }

    public void setPartic_user_id(String partic_user_id) {
        this.partic_user_id = partic_user_id;
    }

    public String getPartic_account_id() {
        return partic_account_id;
    }

    public void setPartic_account_id(String partic_account_id) {
        this.partic_account_id = partic_account_id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getRet_url() {
        return ret_url;
    }

    public void setRet_url(String ret_url) {
        this.ret_url = ret_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public ProjectTransfer() {
    }

    public ProjectTransfer(String order_id, String mer_date, String project_id, String project_account_id, String serv_type, String trans_action, String partic_type, String partic_acc_type, String partic_user_id, String partic_account_id, long amount, String ret_url, String notify_url) {
        this.order_id = order_id;
        this.mer_date = mer_date;
        this.project_id = project_id;
        this.project_account_id = project_account_id;
        this.serv_type = serv_type;
        this.trans_action = trans_action;
        this.partic_type = partic_type;
        this.partic_acc_type = partic_acc_type;
        this.partic_user_id = partic_user_id;
        this.partic_account_id = partic_account_id;
        this.amount = amount;
        this.ret_url = ret_url;
        this.notify_url = notify_url;
    }
}
