package com.zhongyang.java.vo;

/**
 * Created by matthew on 2015/12/28.
 */
public class UmpVO {
        private String service;
        private String sign_type;
        private String sign;
        private String mer_id;
        private String version;

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMer_id() {
        return mer_id;
    }

    public void setMer_id(String mer_id) {
        this.mer_id = mer_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public UmpVO() {

    }

    public UmpVO(String service, String sign_type, String sign, String mer_id, String version) {
        this.service = service;
        this.sign_type = sign_type;
        this.sign = sign;
        this.mer_id = mer_id;
        this.version = version;
    }

    @Override
    public String toString() {
        return "UmpVO{" +
                "service='" + service + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", sign='" + sign + '\'' +
                ", mer_id='" + mer_id + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
