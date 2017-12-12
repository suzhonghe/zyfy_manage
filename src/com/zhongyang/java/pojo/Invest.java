package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.loan.InvestStatus;
import com.zhongyang.java.vo.loan.Method;

public class Invest {
	
	private String id;
	
	private String orderId;

    private BigDecimal amount;

    private String bidmethod;

    private String creditassignid;

    private String loanid;

    private BigDecimal originalamount;

    private String purpose;

    private BigDecimal rate;

    private Method repaymethod;

    private String source;

    private InvestStatus status;

    private Date submittime;

    private String userid;

    private Integer days;

    private Integer months;

    private Integer years;
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBidmethod() {
        return bidmethod;
    }

    public void setBidmethod(String bidmethod) {
        this.bidmethod = bidmethod == null ? null : bidmethod.trim();
    }

    public String getCreditassignid() {
        return creditassignid;
    }

    public void setCreditassignid(String creditassignid) {
        this.creditassignid = creditassignid == null ? null : creditassignid.trim();
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid == null ? null : loanid.trim();
    }

    public BigDecimal getOriginalamount() {
        return originalamount;
    }

    public void setOriginalamount(BigDecimal originalamount) {
        this.originalamount = originalamount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }
    
    public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

    public Method getRepaymethod() {
		return repaymethod;
	}

	public void setRepaymethod(Method repaymethod) {
		this.repaymethod = repaymethod;
	}

	public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
 
	public InvestStatus getStatus() {
		return status;
	}

	public void setStatus(InvestStatus status) {
		this.status = status;
	}

	public Date getSubmittime() {
        return submittime;
    }

    public void setSubmittime(Date submittime) {
        this.submittime = submittime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }
}