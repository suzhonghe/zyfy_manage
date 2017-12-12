package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.InvestDao;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.InvestRecord;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.Result;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月17日 下午4:48:04
* 类说明
*/
@Service
public class InvestServiceImpl implements InvestService {
    
	
	@Autowired
	InvestDao investDao;
	
	/*
	 * 根据标的ID查询投资人ID
	 */
	public List<Invest> getInvestByLoanId(Invest invest) throws Exception {
		return investDao.getInvestByLoanId(invest);
	}

	@Override
	public List<Invest> queryInvest(Invest invest) throws Exception {
		return investDao.selectInvest(invest);
	}

	@Override
	public void modifyInvest(Invest invest) throws Exception {
		investDao.updateInvest(invest);
	}
	/*
	 * 根据loanId更新投资人资金状态：处理中---成功|失败
	 */
	public void updateInvestByLoanId(Invest invest) throws Exception {
		investDao.updateInvestByLoanId(invest);

	}

	@Override
	public int getUsetInvestNumber(String userId) throws Exception {
		return investDao.getUsetInvestNumber(userId);
	}

	@Override
	public List<InvestVo> getUserInvestList(Page<Invest> pager) throws Exception {
		return investDao.getUserInvestList(pager);
	}

	@Override
	public int getInvestCount(Map<String, Object> params) throws Exception {
		return investDao.getInvestCount(params);
	}

	public Result queryResultByMap(Map<String,Object>map)throws Exception{
		return investDao.selectResultByMap(map);
	}
	
	public List<InvestRecord> queryByParams(Page<Invest> page)throws Exception{
		return investDao.selectByParams(page);
	}

	@Override
	public List<InvestVo> getInvestAndMobileByLoanId(String loanId) throws Exception {
		return investDao.getInvestAndMobileByLoanId(loanId);
	}

	@Override
	public Invest queryByInvest(Invest invest) throws Exception {
		return investDao.selectByInvest(invest);
	}
	
	@Override
	public Invest queryById(Invest invest) throws Exception {
		return investDao.selectById(invest);
	}

	@Override
	public void updateInvestStatus(String loanid, String beforStatus, String afterStatus,String userid) {
		investDao.updateInvestStatus(loanid, beforStatus, afterStatus,userid);
		
	}

	@Override
	public int findInvestRepayMent(String loanId) throws Exception {
		return investDao.findInvestRepayMent(loanId);
	}
}
