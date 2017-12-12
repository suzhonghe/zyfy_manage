package com.zhongyang.java.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.zhongyang.java.vo.loan.LoanStatus;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月18日 上午11:08:31
* 类说明
*/
public class LoanInvestVo {
	private String title;//   TITLE    varchar(255) default NULL comment '借款标标题'
	private BigDecimal amount;//   AMOUNT   int(11) comment '贷款金额'
	private LoanStatus status;//   STATUS    varchar(255) comment '借款状态'
	private String timeFinished;//   TIMEFINISHED   datetime default NULL comment '募集成功结束时间'
	private String timeOpen;//   TIMEOPEN  datetime default NULL comment '开始募集时间'
	private String timeOut;//   TIMEOUT   int(11) default NULL comment '开放募集时间'
	private String timeSettled;//TIMESETTLED    datetime default NULL comment '结标时间'
    private BigDecimal bidAmount;//   BID_AMOUNT   int(11) default NULL comment '实际投标金额'
	private Integer bidNumber;//   BID_NUMBER   int(11) default NULL comment '实际投标数目'
    private BigDecimal InvestAmount;
    private String loanid;
    private String userid;
	private String submittime;
	private String name;
	private String InvestName;
	private String longTime;
	List<LoanInvestVo> loanInvestVol = new ArrayList<>();

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal bigDecimal) {
		this.amount = bigDecimal;
	}
	public LoanStatus getStatus() {
		return status;
	}
	public void setStatus(LoanStatus status) {
		this.status = status;
	}
	
	public String getTimeFinished() {
		return timeFinished;
	}
	public void setTimeFinished(String timeFinished) {
		this.timeFinished = timeFinished;
	}
	public String getTimeOpen() {
		return timeOpen;
	}
	public void setTimeOpen(String timeOpen) {
		this.timeOpen = timeOpen;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getTimeSettled() {
		return timeSettled;
	}
	public void setTimeSettled(String timeSettled) {
		this.timeSettled = timeSettled;
	}
	public BigDecimal getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(BigDecimal bigDecimal) {
		this.bidAmount = bigDecimal;
	}
	public Integer getBidNumber() {
		return bidNumber;
	}
	public void setBidNumber(Integer bidNumber) {
		this.bidNumber = bidNumber;
	}
	public BigDecimal getInvestAmount() {
		return InvestAmount;
	}
	public void setInvestAmount(BigDecimal investAmount) {
		InvestAmount = investAmount;
	}
	public String getLoanid() {
		return loanid;
	}
	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSubmittime() {
		return submittime;
	}
	public void setSubmittime(String submittime) {
		this.submittime = submittime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLongTime() {
		return longTime;
	}
	public void setLongTime(String longTime) {
		this.longTime = longTime;
	}
	public List<LoanInvestVo> getLoanInvestVol() {
		return loanInvestVol;
	}
	public void setLoanInvestVol(List<LoanInvestVo> loanInvestVol) {
		this.loanInvestVol = loanInvestVol;
	}
	public String getInvestName() {
		return InvestName;
	}
	public void setInvestName(String investName) {
		InvestName = investName;
	}
    
	
}
