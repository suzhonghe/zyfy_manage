package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class UmpTender {
	
    private String loanid;

    private Date timecreated;

    private String umpcheckdate;

    private String umptenderaccountid;
    
    private BigDecimal amount;
    
    private String umptenderid;
    
    public String getUmptenderid() {
		return umptenderid;
	}

	public void setUmptenderid(String umptenderid) {
		this.umptenderid = umptenderid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }

    public Date getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Date timecreated) {
        this.timecreated = timecreated;
    }

    public String getUmpcheckdate() {
        return umpcheckdate;
    }

    public void setUmpcheckdate(String umpcheckdate) {
        this.umpcheckdate = umpcheckdate;
    }

    public String getUmptenderaccountid() {
        return umptenderaccountid;
    }

    public void setUmptenderaccountid(String umptenderaccountid) {
        this.umptenderaccountid = umptenderaccountid;
    }
    
}