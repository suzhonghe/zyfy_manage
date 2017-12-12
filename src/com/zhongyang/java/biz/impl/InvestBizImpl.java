package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.InvestBiz;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.statusUtil;
import com.zhongyang.java.vo.InvestDetail;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.fund.OrderTime;
import com.zhongyang.java.vo.loan.InvestStatus;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月17日 下午4:34:36
* 类说明：投标信息
*/
@Service
public class InvestBizImpl implements InvestBiz {
	
	private static Logger logger = Logger.getLogger(InvestBizImpl.class);
    
	@Autowired
	private InvestService investService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	LoanService loanService;
	
	public List<Invest> getInvestByLoanId(Invest invest){
		
		return null;
	}

	@Override
	public List<InvestDetail> queryInvestByLoanId(Invest invest) {
		try {
			if(invest.getLoanid()==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得标的ID");
			}
			Loan loan = loanService.queryLoanById(invest.getLoanid());
			List<InvestDetail>investDetails=new ArrayList<InvestDetail>();
			List<Invest> queryInvest = investService.queryInvest(invest);
			User user=new User();
			for (Invest it : queryInvest) {
				if(InvestStatus.INITIALIZED.equals(it.getStatus()))continue;
				InvestDetail ids=new InvestDetail();
				user.setId(it.getUserid());
				User queryUser = userService.queryByParams(user);
				ids.setUserId(queryUser.getId());
				ids.setUserName(queryUser.getName());
				ids.setAmount(it.getAmount());
				ids.setInvestStatus(statusUtil.getStr(it.getStatus().toString()));
				ids.setLoginName(queryUser.getLoginname() == null ? "" : queryUser.getLoginname());
				ids.setLoanName("【"+loan.getProductName()+"】"+loan.getTitle());
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date submittime = it.getSubmittime();
				ids.setTime(sdf.format(submittime));
				investDetails.add(ids);
			}
			return investDetails;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public PagerVO<InvestVo> getUserInvestList(PagerVO<Invest> pager) {
		PagerVO<InvestVo> fundRecordPgVo=new PagerVO<InvestVo>();
		List<InvestVo>  investList =  new ArrayList<InvestVo>();
		List<InvestVo>  fundRecordlistvo =  new ArrayList<InvestVo>();
		try {
			Page<Invest>page=new Page<Invest>();
			if(pager.getStart()!=0){
				page.setPageNo(pager.getStart());
			}
			else{
				page.setPageNo(1);
			}
			if(pager.getLength()!=0){
				page.setPageSize(pager.getLength());
			}
			else{
				page.setPageSize(5);
			}
			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fields = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fields.length; i++) {
					if("xxx".equals(values[i]))continue;
					page.getParams().put(fields[i],values[i]);
				}
			}
			if(pager.getSort()!=null){
				page.getParams().put("sort",pager.getSort());
			}
			else{
				page.getParams().put("sort","desc");
			}
			Map<String, Object> params = page.getParams();
			int count = investService.getInvestCount(params);
			investList = investService.getUserInvestList(page);
			OrderTime time = new OrderTime();
			if(investList.size() > 0){
				for(int i = 0; i<investList.size();i++){
					InvestVo investVo = new  InvestVo();
					investVo.setAmount(investList.get(i).getAmount());;//金额
					investVo.setLoanId(investList.get(i).getLoanId());;//loanid
					investVo.setSubmittime(investList.get(i).getSubmittime());//投资时间
					investVo.setTitle(investList.get(i).getTitle());//标题
					investVo.setRate(investList.get(i).getRate());//利率
					investVo.setMethod(investList.get(i).getMethods().getKey());//还款方式
					investVo.setMonths(investList.get(i).getMonths());//期限
					investVo.setStatus(investList.get(i).getStatuss().getKey());//标的状态
					fundRecordlistvo.add(investVo);
				}
				fundRecordPgVo.setRecordsTotal(count);
				fundRecordPgVo.setData(fundRecordlistvo);
			}
			return fundRecordPgVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
		
	}

	private Loan getLoanInfo(String loanid) {
		Loan loan = new Loan();
		try {
		loan = loanService.queryLoanById(loanid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
		return loan;
	}

}
