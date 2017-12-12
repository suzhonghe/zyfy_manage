package com.zhongyang.java.pojo;

import java.util.Date;

public class UserCredit {
    private String id;

    private Integer creditavailable;

    private Integer creditlimit;

    private String creditrank;

    private String lastmodifiedby;

    private Date timecreated;

    private Date timelastupdated;

    private Integer score;

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

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby == null ? null : lastmodifiedby.trim();
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}