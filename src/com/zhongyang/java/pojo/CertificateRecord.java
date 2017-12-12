package com.zhongyang.java.pojo;

import java.util.Date;

public class CertificateRecord {
    private String id;

    private String auditinfo;

    private String auditor;

    private String status;

    private Date timerecorded;

    private Integer score;

    private String certificateId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAuditinfo() {
        return auditinfo;
    }

    public void setAuditinfo(String auditinfo) {
        this.auditinfo = auditinfo == null ? null : auditinfo.trim();
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId == null ? null : certificateId.trim();
    }
}