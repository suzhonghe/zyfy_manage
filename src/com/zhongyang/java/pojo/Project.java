package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.loan.Method;

public class Project {
	
    private String id;//ID varchar(36) not null,
    
    private BigDecimal amount;//AMOUNT int(11) default 0 comment '借款金额',

    private Integer autoable;//AUTOABLE varchar(255) default NULL comment '是否自动标的',

    private String clientPriv;//CLIENTPRIV   varchar(255) default NULL comment '商户平台自定义信息'
    
    private String description;//DESCRIPTION  text default NULL comment '借款描述'
    
    private String employeeId;//EMPLOYEEID  varchar(255) default NULL comment '经办员工ID',

    private String guaranteeInfo;//MORTGAGEINFO   text default NULL comment '抵押信息说明',
    
    private String guarantyStyle;//GUARANTYSTYLE  varchar(255) default NULL comment '担保方式',

    private Integer hidden;//HIDDEN  tinyint(1) default 0 comment '是否对所有人可见',

    private Method method;//METHOD   varchar(255) default NULL comment '还款方式',

    private String mortgageInfo;//GUARANTEEINFO text default NULL comment '担保情况说明',
     
    private Integer mortgaged;//MORTGAGED tinyint(1) default 0 comment '是否抵押'

    private String parentId;//PARENTID   varchar(255) default NULL comment '关联借款信息ID',

    private String productId;//PRODUCTID   varchar(255) default NULL comment '归属产品ID ',

    private Integer pureRequest;//PUREREQUEST  tinyint(1) default 0 comment '是否仅有借款申请',

    private String purPose;//PURPOSE   varchar(255) default NULL comment '借款目的',

    private BigDecimal rate;//RATE   decimal(10.5) default 0 comment '年化利率',

    private String reviewComment;//REVIEWCOMMENT    varchar(4000) default NULL comment '审核记录 ',

    private String riskInfo;//RISKINFO    text default NULL comment '风险信息说明 ',
    
    private String serial;//SERIAL   varchar(255) default NULL comment '借款唯一编号 ',

    private String source;//SOURCE   varchar(255) default NULL comment '来源',

    private Integer status;//STATUS    int(1) default 0 comment '借款状态 ',

    private Date timeSubmit;//TIMESUBMIT   datetime default NULL comment '提交时间',

    private String title;//TITLE   varchar(60) default NULL comment '借款标题',
    
    private String userId;//USER_ID   varchar(60) default NULL comment '用户ID',

    private Integer days;//DAYS int(11) default NULL comment '借款期限（日）',
    
    private Integer months;//MONTHS   int(11) default NULL comment '借款期限（月）',
    
    private Integer years;//YEARS  int(11) default NULL comment '借款期限（年）',

    private String guaranteeId;//GUARANTEE_ID   varchar(255) default NULL comment '担保实体ID',

    private String guaranteeRealm;//GUARANTEE_REALM    varchar(255) default NULL comment '担保实体',

    private BigDecimal maxAmount;//MAXAMOUNT  int(11) default NULL comment '最大投资金额',

    private Integer maxTimes;//MAXTIMES  int(11) default NULL comment '最大投资次数',

    private BigDecimal maxTotaLamount;//MAXTOTALAMOUNT  int(11) default NULL comment '总投资金额',

    private BigDecimal minAmount;//MINAMOUNT  int(11) default NULL comment '最小投资金额',

    private BigDecimal stepAmount;//STEPAMOUNT    int(11) default NULL comment '投资增量',

    private Integer dayofRepayment;//DAYOFREPAYMENT  int(11) default NULL comment '固定还款日',

    private Integer daysofYear;//DAYSOFYEAR  int(11) default NULL comment '年计算日期',

    private String repaymentPeriod;//REPAYMENTPERIOD    varchar(255) default NULL comment '多次还款方式',

    private String realm;//REALM   varchar(255) default NULL comment '对应实体　',
    
    private BigDecimal investInterestFee;//INVESTINTERESTFEE   decimal(10.5) default NULL comment '投资利息管理费',

    private BigDecimal loanGuaranteeFee;//LOANGUARANTEEFEE decimal(10.5) default NULL comment '担保费率',

    private BigDecimal loanInterestFee;//LOANINTERESTFEE   decimal(10.5) default NULL comment '贷款利息管理费',

    private BigDecimal loanManageFee;//LOANMANAGEFEE   decimal(10.5) default NULL comment '管理费',

    private BigDecimal loanRiskFee;//LOANRISKFEE   decimal(10.5) default NULL comment '风险费率',

    private BigDecimal loanServiceFee;//LOANSERVICEFEE   decimal(10.5) default NULL comment '服务费',
   
    private BigDecimal surplusAmount;//SURPLUSAMOUNT   int(11) default NULL comment '剩余可发标金额',
    
    private BigDecimal deliveryAmount;// DELIVERYAMOUNT   int(11) default NULL comment '在途金额',
   
    private BigDecimal publishedAmount;// PUBLISEDAMOUNT   int(11) default NULL comment '已发标金额',
    
    private String operationRange;// OPERATIONRANGE  varchar(255) default NULL comment '经营范围',
    
    private String repaySource;// REPAYSOURCE   varchar(255) default NULL comment '还款来源',
    
    private String firmInfo;//FIRMINFO   varchar(255) default NULL comment '公司信息',
    
    private String mortgagedType;//MORTGAGEDTYPE  varchar(255) default NULL comment '抵押类型',
    
    private String loanUserId;//LOANUSERID   varchar(36) default NULL comment '借款人用户ID',
    
    private String legalPerson;//借款单位法人
    
    private String agentPerson;//借款单位代理人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getAutoable() {
		return autoable;
	}

	public void setAutoable(Integer autoable) {
		this.autoable = autoable;
	}

	public String getClientPriv() {
		return clientPriv;
	}

	public void setClientPriv(String clientPriv) {
		this.clientPriv = clientPriv;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getGuaranteeInfo() {
		return guaranteeInfo;
	}

	public void setGuaranteeInfo(String guaranteeInfo) {
		this.guaranteeInfo = guaranteeInfo;
	}

	public String getGuarantyStyle() {
		return guarantyStyle;
	}

	public void setGuarantyStyle(String guarantyStyle) {
		this.guarantyStyle = guarantyStyle;
	}

	public Integer getHidden() {
		return hidden;
	}

	public void setHidden(Integer hidden) {
		this.hidden = hidden;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getMortgageInfo() {
		return mortgageInfo;
	}

	public void setMortgageInfo(String mortgageInfo) {
		this.mortgageInfo = mortgageInfo;
	}

	public Integer getMortgaged() {
		return mortgaged;
	}

	public void setMortgaged(Integer mortgaged) {
		this.mortgaged = mortgaged;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getPureRequest() {
		return pureRequest;
	}

	public void setPureRequest(Integer pureRequest) {
		this.pureRequest = pureRequest;
	}

	public String getPurPose() {
		return purPose;
	}

	public void setPurPose(String purPose) {
		this.purPose = purPose;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public String getRiskInfo() {
		return riskInfo;
	}

	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getTimeSubmit() {
		return timeSubmit;
	}

	public void setTimeSubmit(Date timeSubmit) {
		this.timeSubmit = timeSubmit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(String guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public String getGuaranteeRealm() {
		return guaranteeRealm;
	}

	public void setGuaranteeRealm(String guaranteeRealm) {
		this.guaranteeRealm = guaranteeRealm;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Integer getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}

	public BigDecimal getMaxTotaLamount() {
		return maxTotaLamount;
	}

	public void setMaxTotaLamount(BigDecimal maxTotaLamount) {
		this.maxTotaLamount = maxTotaLamount;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getStepAmount() {
		return stepAmount;
	}

	public void setStepAmount(BigDecimal stepAmount) {
		this.stepAmount = stepAmount;
	}

	public Integer getDayofRepayment() {
		return dayofRepayment;
	}

	public void setDayofRepayment(Integer dayofRepayment) {
		this.dayofRepayment = dayofRepayment;
	}

	public Integer getDaysofYear() {
		return daysofYear;
	}

	public void setDaysofYear(Integer daysofYear) {
		this.daysofYear = daysofYear;
	}

	public String getRepaymentPeriod() {
		return repaymentPeriod;
	}

	public void setRepaymentPeriod(String repaymentPeriod) {
		this.repaymentPeriod = repaymentPeriod;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public BigDecimal getInvestInterestFee() {
		return investInterestFee;
	}

	public void setInvestInterestFee(BigDecimal investInterestFee) {
		this.investInterestFee = investInterestFee;
	}

	public BigDecimal getLoanGuaranteeFee() {
		return loanGuaranteeFee;
	}

	public void setLoanGuaranteeFee(BigDecimal loanGuaranteeFee) {
		this.loanGuaranteeFee = loanGuaranteeFee;
	}

	public BigDecimal getLoanInterestFee() {
		return loanInterestFee;
	}

	public void setLoanInterestFee(BigDecimal loanInterestFee) {
		this.loanInterestFee = loanInterestFee;
	}

	public BigDecimal getLoanManageFee() {
		return loanManageFee;
	}

	public void setLoanManageFee(BigDecimal loanManageFee) {
		this.loanManageFee = loanManageFee;
	}

	public BigDecimal getLoanRiskFee() {
		return loanRiskFee;
	}

	public void setLoanRiskFee(BigDecimal loanRiskFee) {
		this.loanRiskFee = loanRiskFee;
	}

	public BigDecimal getLoanServiceFee() {
		return loanServiceFee;
	}

	public void setLoanServiceFee(BigDecimal loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}

	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public BigDecimal getPublishedAmount() {
		return publishedAmount;
	}

	public void setPublishedAmount(BigDecimal publishedAmount) {
		this.publishedAmount = publishedAmount;
	}

	public String getOperationRange() {
		return operationRange;
	}

	public void setOperationRange(String operationRange) {
		this.operationRange = operationRange;
	}

	public String getRepaySource() {
		return repaySource;
	}

	public void setRepaySource(String repaySource) {
		this.repaySource = repaySource;
	}

	public String getFirmInfo() {
		return firmInfo;
	}

	public void setFirmInfo(String firmInfo) {
		this.firmInfo = firmInfo;
	}

	public String getMortgagedType() {
		return mortgagedType;
	}

	public void setMortgagedType(String mortgagedType) {
		this.mortgagedType = mortgagedType;
	}

	public String getLoanUserId() {
		return loanUserId;
	}

	public void setLoanUserId(String loanUserId) {
		this.loanUserId = loanUserId;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getAgentPerson() {
		return agentPerson;
	}

	public void setAgentPerson(String agentPerson) {
		this.agentPerson = agentPerson;
	}
}