package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;

public class UmpTenderTransferRecord {
    private String orderid;

    private BigDecimal amount;

    private String loanid;

    private Date mercheckdate;

    private Date merdate;

    private String particaccounttype;

    private String partictype;

    private String retcode;

    private String retmsg;

    private FundRecordStatus status;

    private FundRecordOperation tenderaction;

    private Date timecreated;

    private Date timelastupdated;

    private String tradeno;

    private FundRecordType transfertype;

    private String umpaccountid;

    private String umpaccountname;

    private String umptenderaccountid;

    private String umptenderid;

    private String userid;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
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
        this.loanid = loanid == null ? null : loanid.trim();
    }

    public Date getMercheckdate() {
        return mercheckdate;
    }

    public void setMercheckdate(Date mercheckdate) {
        this.mercheckdate = mercheckdate;
    }

    public Date getMerdate() {
		return merdate;
	}

	public void setMerdate(Date merdate) {
		this.merdate = merdate;
	}

	public FundRecordType getTransfertype() {
		return transfertype;
	}

	public String getParticaccounttype() {
        return particaccounttype;
    }

    public void setParticaccounttype(String particaccounttype) {
        this.particaccounttype = particaccounttype == null ? null : particaccounttype.trim();
    }

    public String getPartictype() {
        return partictype;
    }

    public void setPartictype(String partictype) {
        this.partictype = partictype == null ? null : partictype.trim();
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode == null ? null : retcode.trim();
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg == null ? null : retmsg.trim();
    }

    public void setStatus(FundRecordStatus status) {
		this.status = status;
	}

    public FundRecordStatus getStatus() {
		return status;
	}

	public FundRecordOperation getTenderaction() {
		return tenderaction;
	}

	public void setTenderaction(FundRecordOperation tenderaction) {
		this.tenderaction = tenderaction;
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

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno == null ? null : tradeno.trim();
    }

    public String getUmpaccountid() {
        return umpaccountid;
    }

    public void setTransfertype(FundRecordType transfertype) {
		this.transfertype = transfertype;
	}

	public void setUmpaccountid(String umpaccountid) {
        this.umpaccountid = umpaccountid == null ? null : umpaccountid.trim();
    }

    public String getUmpaccountname() {
        return umpaccountname;
    }

    public void setUmpaccountname(String umpaccountname) {
        this.umpaccountname = umpaccountname == null ? null : umpaccountname.trim();
    }

    public String getUmptenderaccountid() {
        return umptenderaccountid;
    }

    public void setUmptenderaccountid(String umptenderaccountid) {
        this.umptenderaccountid = umptenderaccountid == null ? null : umptenderaccountid.trim();
    }

    public String getUmptenderid() {
        return umptenderid;
    }

    public void setUmptenderid(String umptenderid) {
        this.umptenderid = umptenderid == null ? null : umptenderid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}