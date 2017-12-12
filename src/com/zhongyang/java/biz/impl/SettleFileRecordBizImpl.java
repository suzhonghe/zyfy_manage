package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.SettleFileRecordBiz;
import com.zhongyang.java.pojo.SettleFileRecord;
import com.zhongyang.java.service.SettleFileRecordService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.SettleFileRecordVO;
import com.zhongyang.java.vo.settle.SettleStatus;
import com.zhongyang.java.vo.settle.SettleType;
@Service
public class SettleFileRecordBizImpl implements SettleFileRecordBiz{
	
	private static Logger logger = Logger.getLogger(SettleFileRecordBizImpl.class);
	
	@Autowired
	private SettleFileRecordService settleFileRecordService;

	@Override
	public Message addSettleFileRecord(SettleFileRecord record){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message modifyByParams(SettleFileRecord record){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message deleteByParams(SettleFileRecord record){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerVO<SettleFileRecordVO> queryByParams(PagerVO<SettleFileRecord> pager){
		try {
			PagerVO<SettleFileRecordVO> pagerVo=new PagerVO<SettleFileRecordVO>();
			Page<SettleFileRecord> page=new Page<SettleFileRecord>();
			page.setPageNo(pager.getStart());
			page.setPageSize(pager.getLength());
			page.setStartTime(new Date(pager.getStartTime()));
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("startTime", page.getStartTime());
			if(pager.getEndTime()==0){
				page.setEndTime(null);
			}
			else{
				page.setEndTime(new Date(pager.getEndTime()));
				map.put("endTime", page.getEndTime());
			}
			
			int count = settleFileRecordService.queryCountByParams(map);
			if(count%page.getPageSize()==0){
				pagerVo.setTotalPage(count/page.getPageSize());
			}
			else{
				pagerVo.setTotalPage(count/page.getPageSize()+1);
			}
			pagerVo.setRecordsTotal(count);
			List<SettleFileRecordVO> records=new ArrayList<SettleFileRecordVO>();
			List<SettleFileRecord> queryRecords = settleFileRecordService.queryByParams(page);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			for (SettleFileRecord settleFileRecord : queryRecords) {
				SettleFileRecordVO srv=new SettleFileRecordVO();
				srv.setId(settleFileRecord.getId());
				srv.setSettleDate(sf.format(settleFileRecord.getSettleDate()));
				srv.setSettleType(SettleType.getDescription(settleFileRecord.getSettleType()));
				srv.setStatus(SettleStatus.getDescription(settleFileRecord.getStatus()));
				srv.setApplyTime(sdf.format(settleFileRecord.getApplyTime()));
				records.add(srv);
			}
			pagerVo.setData(records);
			pagerVo.setStart(pager.getStart());
			pagerVo.setLength(pager.getLength());
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询记录失败");
		}
		
	}

}
