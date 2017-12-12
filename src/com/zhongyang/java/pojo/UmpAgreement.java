package com.zhongyang.java.pojo;

import java.util.Date;

public class UmpAgreement {
    private String accountname;

    private String cardno;

    private Boolean debit;

    private Boolean instant;

    private Boolean invest;

    private Boolean repay;

    private Date timecreated;

    private Date timelastupdated;

    private String userid;

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname == null ? null : accountname.trim();
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public Boolean getDebit() {
        return debit;
    }

    public void setDebit(Boolean debit) {
        this.debit = debit;
    }

    public Boolean getInstant() {
        return instant;
    }

    public void setInstant(Boolean instant) {
        this.instant = instant;
    }

    public Boolean getInvest() {
        return invest;
    }

    public void setInvest(Boolean invest) {
        this.invest = invest;
    }

    public Boolean getRepay() {
        return repay;
    }

    public void setRepay(Boolean repay) {
        this.repay = repay;
    }

    public Date getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Date timecreated) {
        this.timecreated = timecreated;
    }

    public Date getTimelastupdated() {
        return timelastupdated;
    }

    public void setTimelastupdated(Date timelastupdated) {
        this.timelastupdated = timelastupdated;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}