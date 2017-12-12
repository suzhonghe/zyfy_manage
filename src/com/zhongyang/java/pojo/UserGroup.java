package com.zhongyang.java.pojo;

import java.util.Date;

public class UserGroup {
	
    private String id;

    private String description;

    private String emailpattern;

    private String groupcode;

    private String name;

    private String parentid;

    private String priv;

    private Date timecreated;

    private Date timelastupdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getEmailpattern() {
        return emailpattern;
    }

    public void setEmailpattern(String emailpattern) {
        this.emailpattern = emailpattern == null ? null : emailpattern.trim();
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode == null ? null : groupcode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv == null ? null : priv.trim();
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
}