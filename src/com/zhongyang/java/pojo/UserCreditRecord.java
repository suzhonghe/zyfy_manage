package com.zhongyang.java.pojo;

import java.util.Date;

public class UserCreditRecord {
    private String id;

    private Integer creditavailable;

    private Integer creditlimit;

    private String creditrank;

    private String modifiedby;

    private Date timerecorded;

    private Integer score;

    private String userCreditId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getCreditavailable() {
        return creditavailable;
    }

    public void setCreditavailable(Integer creditavailable) {
        this.creditavailable = creditavailable;
    }

    public Integer getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(Integer creditlimit) {
        this.creditlimit = creditlimit;
    }

    public String getCreditrank() {
        return creditrank;
    }

    public void setCreditrank(String creditrank) {
        this.creditrank = creditrank == null ? null : creditrank.trim();
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby == null ? null : modifiedby.trim();
    }

    public Date getTimerecorded() {
        return timerecorded;
    }

    public void setTimerecorded(Date timerecorded) {
        this.timerecorded = timerecorded;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUserCreditId() {
        return userCreditId;
    }

    public void setUserCreditId(String userCreditId) {
        this.userCreditId = userCreditId == null ? null : userCreditId.trim();
    }
}