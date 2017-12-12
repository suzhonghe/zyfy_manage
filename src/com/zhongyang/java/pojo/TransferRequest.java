package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TransferRequest {
    private String id;

    private String account;

    private BigDecimal amount;

    private String auditemployee;

    private String description;

    private String orderid;

    private String requestemployee;

    private String status;

    private Date timerecorded;

    private String userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAuditemployee() {
        return auditemployee;
    }

    public void setAuditemployee(String auditemployee) {
        this.auditemployee = auditemployee == null ? null : auditemployee.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getRequestemployee() {
        return requestemployee;
    }

    public void setRequestemployee(String requestemployee) {
        this.requestemployee = requestemployee == null ? null : requestemployee.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getTimerecorded() {
        return timerecorded;
    }

    public void setTimerecorded(Date timerecorded) {
        this.timerecorded = timerecorded;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}