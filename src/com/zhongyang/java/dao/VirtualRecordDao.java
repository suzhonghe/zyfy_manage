
package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.system.page.Page;


public interface VirtualRecordDao {
	
	public List<VirtualRecord>selectByParams(Page<VirtualRecord>page);
	
	public int updateVirtualRecord(VirtualRecord virtualRecord);
	
	public int updateBatchVirtualRecords(List<VirtualRecord> list);
	
	//查询当日体验到期记录
	public List<VirtualRecord> selectRecordByTime();
	
}
