
package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.system.page.Page;

public interface VirtualRecordBiz {
	
	public Page<VirtualRecord> queryVirtualRecordByParams(Page<VirtualRecord>page);
	
}
