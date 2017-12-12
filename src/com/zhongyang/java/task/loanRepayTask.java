package com.zhongyang.java.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhongyang.java.biz.impl.InvestBizImpl;
import com.zhongyang.java.service.LoanRepayMentService;
import com.zhongyang.java.vo.loan.LoanRepayMent;

@Component("loanRepayTask")
public class loanRepayTask {
	private static Logger logger = Logger.getLogger(InvestBizImpl.class);
	@Autowired
	private LoanRepayMentService loanRepaymentService;
	
	public void loanRepaymentTask(){
		try{
			String status = LoanRepayMent.OVERDUE.toString();
			loanRepaymentService.updateStatusByTime(status);
			logger.info("还款逾期修改状态未OVERDUE成功！");
		}catch(Exception e){
			logger.info("还款逾期修改状态失败"+e.getMessage());
			e.printStackTrace();
		}

	}
}
