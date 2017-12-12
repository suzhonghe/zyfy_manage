package com.zhongyang.java.pojo;

import java.util.Date;

public class RepayOrder {
    private String id;

    private String investrepayid;

    private String loanrepayid;

    private String orderdate;

    private String orderid;

    private String stat;

    private Date timerecorded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInvestrepayid() {
        return investrepayid;
    }

    public void setInvestrepayid(String investrepayid) {
        this.investrepayid = investrepayid == null ? null : investrepayid.trim();
    }

    public String getLoanrepayid() {
        return loanrepayid;
    }

    public void setLoanrepayid(String loanrepayid) {
        this.loanrepayid = loanrepayid == null ? null : loanrepayid.trim();
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate == null ? null : orderdate.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat == null ? null : stat.trim();
    }

    public Date getTimerecorded() {
        return timerecorded;
    }

    public void setTimerecorded(Date timerecorded) {
        this.timerecorded = timerecorded;
    }
}