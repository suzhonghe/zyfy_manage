package com.zhongyang.java.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhongyang.java.biz.UmpTransactionBiz;
import com.zhongyang.java.biz.impl.InvestBizImpl;
import com.zhongyang.java.service.LoanRepayMentService;
import com.zhongyang.java.sys.uitl.FormatUtils;
import com.zhongyang.java.vo.loan.LoanRepayMent;

import net.sf.json.util.NewBeanInstanceStrategy;

@Component("repaymentStatusTask")
public class repaymentStatusTask {
	private static Logger logger = Logger.getLogger(InvestBizImpl.class);

	@Autowired
	UmpTransactionBiz umpTransactionBiz;
	public void repaymentStatusTaskM(){
		try{
			String type = "LOAN";
			String stringe = FormatUtils.simpleFormat(new Date());
			String startTime = stringe+" 00:00:00";
			String endTime = stringe+" 23:59:59";
			umpTransactionBiz.findSettledloan(type,startTime,endTime);
			logger.info("还款逾期修改状态未OVERDUE成功！");
		}catch(Exception e){
			logger.info("还款逾期修改状态失败"+e.getMessage());
			e.printStackTrace();
		}

	}
}
