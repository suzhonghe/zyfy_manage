package com.zhongyang.java.biz.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.TimerBiz;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.uitl.TimerPublishedLoan;
import com.zhongyang.java.vo.loan.LoanStatus;
@Scope("prototype")
@Service
public class TimerBizImpl implements TimerBiz{
	
	private static Logger logger = Logger.getLogger(TimerBizImpl.class);
	
	@Autowired
	private TimerPublishedLoan timerPublishedLoan;
	
	@Autowired
	private LoanService loanService;
	
	@Override
	@Transactional
	public Message timerBidPublished(String loanId, String publishedTime) {
		Message message=new Message();
		try {
			Timer timer=new Timer();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = dateFormat.parse(publishedTime);
			timerPublishedLoan.setLoanId(loanId);
			timer.schedule(timerPublishedLoan,date);
			Loan loan=new Loan();
			loan.setId(loanId);
			loan.setStatus(LoanStatus.SCHEDULED);
			loan.setTimeOpen(date);
			Date timeScheduled=new Date();
			loan.setTimeScheduled(timeScheduled);
			loanService.modifyLoan(loan);
			message.setCode(1);
			message.setMessage("定时发布成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "定时发布失败");
		}
	}

}
