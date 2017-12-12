package com.zhongyang.java.vo;

import java.math.BigDecimal;

/**
 * Created by Matthew on 2016/1/15.
 */
public class RepaymentInfo {
    private String id;//还款ID;
    private BigDecimal amountprincipal;//还款本金
    private BigDecimal amountinterest;//还款利息

    public RepaymentInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmountprincipal() {
        return amountprincipal;
    }

    public void setAmountprincipal(BigDecimal amountprincipal) {
        this.amountprincipal = amountprincipal;
    }

    public BigDecimal getAmountinterest() {
        return amountinterest;
    }

    public void setAmountinterest(BigDecimal amountinterest) {
        this.amountinterest = amountinterest;
    }

    public RepaymentInfo(String id, BigDecimal amountprincipal, BigDecimal amountinterest) {
        this.id = id;
        this.amountprincipal = amountprincipal;
        this.amountinterest = amountinterest;
    }

    @Override
    public String toString() {
        return "RepaymentInfo{" +
                "id='" + id + '\'' +
                ", amountprincipal=" + amountprincipal +
                ", amountinterest=" + amountinterest +
                '}';
    }
}
