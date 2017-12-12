package com.zhongyang.java.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.LoanDao;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ManagerData;
import com.zhongyang.java.vo.loan.SelectLoanResultVo;
/**
 * 
* @Title: LoanServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:标的业务实现类
* @author 苏忠贺   
* @date 2015年12月4日 上午9:05:01 
* @version V1.0
 */
@Service
public class LoanServiceImpl implements LoanService{
	
	@Autowired
	private LoanDao loanDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.LoanService#addOneLoan(com.zhongyang.java.pojo.Loan)
	 */
	@Override
	public void addOneLoan(Loan loan) throws Exception {
		loanDao.insertOneLoan(loan);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.LoanService#queryLoanById(java.lang.String)
	 */
	@Override
	public Loan queryLoanById(String id) throws Exception {
		return loanDao.selectLoanById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.LoanService#queryLoanByProjectId(java.lang.String)
	 */
	@Override
	public List<Loan> queryLoanByProjectId(Loan loan) throws Exception {

		return loanDao.selectLoanByProjectId(loan);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.LoanService#modifyLoan(com.zhongyang.java.pojo.Loan)
	 */
	@Override
	public void modifyLoan(Loan loan) throws Exception {
		loanDao.updateLoan(loan);
	}

	@Override
	public List<Loan> queryLoanByStatus(Page<Loan>page) throws Exception {
		return loanDao.selectLoanByStatus(page);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.LoanService#getFinishedLoan(com.zhongyang.java.pojo.Loan)
	 * 查询已满标(状态：FINISHED)的标的
	 */
	public List<Loan> getFinishedLoan(Loan loan) throws Exception{
		return loanDao.getFinishedLoan(loan);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.LoanService#queryPublishedLoans()
	 */
	@Override
	public List<Loan> queryPublishedLoans(Page<Loan>page) throws Exception {
		return loanDao.selectPublishedLoans(page);
	}

	@Override
	public SelectLoanResultVo queryResult(Map<String,Object>params) throws Exception {
		return loanDao.selectResult(params);
	}

	@Override
	public int getUsetLoanNumber(String userId) throws Exception {
		return loanDao.getUsetLoanNumber(userId);
	}

	@Override
	public int getUserLoanCount(Map<String, Object> params) throws Exception {
		return loanDao.getUserLoanCount(params);
	}

	@Override
	public List<Loan> getUserLoanList(Page<FundRecord> page) throws Exception {
		return loanDao.getUserLoanList(page);
	}

	@Override
	public void updateLoanStatus(String loanid, String beforStatus, String afterStatus) {
		loanDao.updateLoanStatus(loanid, beforStatus, afterStatus);
		
	}

	@Override
	public ManagerData queryLoanAmount(Date date) {
		return loanDao.selectLoanAmount(date);
	}

	@Override
	public int queryLoanPerson(Date date) {
		return loanDao.selectLoanPerson(date);
	}

	@Override
	public int queryInPerson(Date date) {
		return loanDao.selectInPerson(date);
	}

	@Override
	public int queryLimitDays(Date date) {
		return loanDao.selectLimitDays(date);
	}

	@Override
	public double queryRate(Date date) {
		return loanDao.selectRate(date);
	}
	
}
