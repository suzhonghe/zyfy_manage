package com.zhongyang.java.pojo;

import java.util.Date;

public class UserAutoBid {
    private String id;

    private Boolean active;

    private Date activedTime;

    private Boolean enable;

    private Date lastBidTime;

    private Boolean mortgaged;

    private String priv;

    private Integer reservedAmount;

    private Integer singleAmount;

    private Integer maxcredit;

    private Integer maxduration;

    private Integer maxrate;

    private Integer mincredit;

    private Integer minduration;

    private Integer minrate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getActivedTime() {
        return activedTime;
    }

    public void setActivedTime(Date activedTime) {
        this.activedTime = activedTime;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getLastBidTime() {
        return lastBidTime;
    }

    public void setLastBidTime(Date lastBidTime) {
        this.lastBidTime = lastBidTime;
    }

    public Boolean getMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(Boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv == null ? null : priv.trim();
    }

    public Integer getReservedAmount() {
        return reservedAmount;
    }

    public void setReservedAmount(Integer reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public Integer getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Integer singleAmount) {
        this.singleAmount = singleAmount;
    }

    public Integer getMaxcredit() {
        return maxcredit;
    }

    public void setMaxcredit(Integer maxcredit) {
        this.maxcredit = maxcredit;
    }

    public Integer getMaxduration() {
        return maxduration;
    }

    public void setMaxduration(Integer maxduration) {
        this.maxduration = maxduration;
    }

    public Integer getMaxrate() {
        return maxrate;
    }

    public void setMaxrate(Integer maxrate) {
        this.maxrate = maxrate;
    }

    public Integer getMincredit() {
        return mincredit;
    }

    public void setMincredit(Integer mincredit) {
        this.mincredit = mincredit;
    }

    public Integer getMinduration() {
        return minduration;
    }

    public void setMinduration(Integer minduration) {
        this.minduration = minduration;
    }

    public Integer getMinrate() {
        return minrate;
    }

    public void setMinrate(Integer minrate) {
        this.minrate = minrate;
    }
}