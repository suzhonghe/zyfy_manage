
package com.zhongyang.java.biz.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.VirtualRecordBiz;
import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.service.VirtualRecordService;
import com.zhongyang.java.system.page.Page;

@Service
public class VirtualRecordBizImpl implements VirtualRecordBiz {
	
	@Autowired
	private VirtualRecordService virtualRecordService;
	
	@Override
	public Page<VirtualRecord> queryVirtualRecordByParams(Page<VirtualRecord> page) {
		
		List<VirtualRecord> results = virtualRecordService.queryByParams(page);
		page.setPageNo(page.getPageNo());
		page.setResults(results);
		int count = page.getTotalRecord();
		if(count%page.getPageSize()==0){
			page.setTotalPage(count/page.getPageSize());
		}else{
			page.setTotalPage(count/page.getPageSize()+1);
		}
		return page;
	}
}
