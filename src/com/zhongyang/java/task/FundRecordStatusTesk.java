package com.zhongyang.java.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.vo.fund.FundRecordStatus;

@Component("fundRecordStatusTask")
public class FundRecordStatusTesk {
	private static Logger logger = Logger.getLogger(FundRecordStatusTesk.class);
	
	@Autowired
	private FundRecordService fundRecordService;
	
	public void fundRecordStatusTaskM(){
		try{
			Date date = new Date();
			Date dateNew =  getDateAfter(date,7);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			int count = fundRecordService.updateStatusForFailed(sf.format(date), sf.format(dateNew));
			logger.info("修改状态PROCESSING成功！"+count);
		}catch(Exception e){
			logger.info("修改状态PROCESSING为失败"+e.getMessage());
			e.printStackTrace();
		}

	}
	public static Date getDateAfter(Date d, int day) {  
		 Calendar now = Calendar.getInstance();  
	        now.setTime(d);  
	        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
	     return now.getTime();  
    }
}
