package com.zhongyang.java.vo;

import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.Product;

/**
 * 
* @Title: LoanDetail.java 
* @Package com.zhongyang.java.vo 
* @Description:标的详情 
* @author 苏忠贺   
* @date 2016年1月7日 下午4:05:06 
* @version V1.0
 */
public class LoanDetail {

	private Loan loan;
	
	private Product product;
	
	private ProjectDetail projectDtail;
	
	public ProjectDetail getProjectDtail() {
		return projectDtail;
	}

	public void setProjectDtail(ProjectDetail projectDtail) {
		this.projectDtail = projectDtail;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
