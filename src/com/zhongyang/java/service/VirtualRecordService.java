
package com.zhongyang.java.service;

import java.util.List;
import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.system.page.Page;

public interface VirtualRecordService {
	
	public List<VirtualRecord>queryByParams(Page<VirtualRecord>page);
	
	public int modifyVirtualRecord(VirtualRecord virtualRecord);
	
	public int modifyBatchVirtualRecords(List<VirtualRecord> list);
	
	public List<VirtualRecord> queryRecordByTime();
}
