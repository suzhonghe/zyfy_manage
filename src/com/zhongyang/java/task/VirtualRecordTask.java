package com.zhongyang.java.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.pojo.FreshAmount;
import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.service.ExperienceAmountService;
import com.zhongyang.java.service.FreshAmountService;
import com.zhongyang.java.service.VirtualRecordService;
import com.zhongyang.java.system.SystemPro;

@Component("virtualRecordTask")
public class VirtualRecordTask {
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_FRESH_LIMITDAYS = (String) sysMap.get("ZYCF_FRESH_LIMITDAYS");
		ZYCF_FRESH_LIMITAMOUNT = (String) sysMap.get("ZYCF_FRESH_LIMITAMOUNT");
		ZYCF_FRESH_LIMITMOTHS = (String) sysMap.get("ZYCF_FRESH_LIMITMOTHS");
		
	}
	
	private static Logger logger = Logger.getLogger(VirtualRecordTask.class);
	
	private static String ZYCF_FRESH_LIMITDAYS;//抵现券有效天数
	
	private static String ZYCF_FRESH_LIMITAMOUNT;//抵现券激活投资限制额度
	
	private static String ZYCF_FRESH_LIMITMOTHS;
	
	@Autowired
	private VirtualRecordService virtualRecordService;
	
	@Autowired
	private ExperienceAmountService experienceAmountService;
	
	@Autowired
	private FreshAmountService freshAmountService;
	
	@Transactional
	public void task(){
		
		try{
						
			//查询当日体验到期的记录
			List<VirtualRecord> results = virtualRecordService.queryRecordByTime();
			
			List<FreshAmount>freshAmounts=new ArrayList<FreshAmount>();
			for (VirtualRecord virtualRecord : results) {
				virtualRecord.setFlag(true);
				FreshAmount freshAmount=new FreshAmount();
				freshAmount.setId(UUID.randomUUID().toString().toUpperCase());
				Date date = new Date();
				freshAmount.setCreateTime(date);
				freshAmount.setAmount(virtualRecord.getRealEarning());
				freshAmount.setStatus(0);
				freshAmount.setType(2);
				freshAmount.setName("体验金利息抵现券");
				freshAmount.setEffectDay(Integer.valueOf(ZYCF_FRESH_LIMITDAYS));
				freshAmount.setEndTime(new Date(date.getTime()+Integer.valueOf(ZYCF_FRESH_LIMITDAYS)*24*3600*1000L));
				freshAmount.setAmountLimit(new BigDecimal(ZYCF_FRESH_LIMITAMOUNT));
				freshAmount.setMonths(Integer.valueOf(ZYCF_FRESH_LIMITMOTHS));
				freshAmount.setUserId(virtualRecord.getUserId());
				freshAmounts.add(freshAmount);
			}
			
			if(results!=null&&results.size()!=0){
				//批量发放抵现券
				freshAmountService.addBatchFreshAmount(freshAmounts);
				//批量更新体验金投资记录（返券状态改为已返券）
				virtualRecordService.modifyBatchVirtualRecords(results);
			}
			
			//更改体验金未用过期作废
			experienceAmountService.modifyExperiences();
			
			//更改抵现券到期未用过期作废
			freshAmountService.modifyFreshAmounts();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}		
	}
}
