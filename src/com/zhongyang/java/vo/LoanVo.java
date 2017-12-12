package com.zhongyang.java.vo;

import java.math.BigDecimal;

import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.pojo.Proof;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.User;
/**
 * 
* @Title: ProjectApply.java 
* @Package com.zhongyang.java.vo 
* @Description: 项目申请扩展类
* @author 苏忠贺   
* @date 2015年12月2日 下午12:23:31 
* @version V1.0
 */
public class LoanVo {
	
	private User user;
	
	private Project project;
	
	private Proof proof;
	
	private Loan loan;
	
	private String method;//还款方式;
	
	private String status;//标的状态
	
	private String id;//标的Id
	
	private String loanId;//标的Id
	
	private String title;//标题	
	
	private BigDecimal amount;//金额
	
	private BigDecimal rate;//利率
	
	private Integer months;//期限
	
	private UmpAccount umpAccount=new UmpAccount();
	
	private String[] legalPersonPhoto;
	
	private String[]enterpriseInfoPhoto;
	
	private String[]contractPhoto;
	
	private String[]assetsPhoto;
	
	private String[]othersPhoto;
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UmpAccount getUmpAccount() {
		return umpAccount;
	}

	public void setUmpAccount(UmpAccount umpAccount) {
		this.umpAccount = umpAccount;
	}

	public Proof getProof() {
		return proof;
	}

	public void setProof(Proof proof) {
		this.proof = proof;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public String[] getLegalPersonPhoto() {
		return legalPersonPhoto;
	}

	public void setLegalPersonPhoto(String[] legalPersonPhoto) {
		this.legalPersonPhoto = legalPersonPhoto;
	}

	public String[] getEnterpriseInfoPhoto() {
		return enterpriseInfoPhoto;
	}

	public void setEnterpriseInfoPhoto(String[] enterpriseInfoPhoto) {
		this.enterpriseInfoPhoto = enterpriseInfoPhoto;
	}

	public String[] getContractPhoto() {
		return contractPhoto;
	}

	public void setContractPhoto(String[] contractPhoto) {
		this.contractPhoto = contractPhoto;
	}

	public String[] getAssetsPhoto() {
		return assetsPhoto;
	}

	public void setAssetsPhoto(String[] assetsPhoto) {
		this.assetsPhoto = assetsPhoto;
	}

	public String[] getOthersPhoto() {
		return othersPhoto;
	}

	public void setOthersPhoto(String[] othersPhoto) {
		this.othersPhoto = othersPhoto;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
    
	
	
	
}
