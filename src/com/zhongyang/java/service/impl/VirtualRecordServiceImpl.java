
package com.zhongyang.java.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhongyang.java.dao.VirtualRecordDao;
import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.service.VirtualRecordService;
import com.zhongyang.java.system.page.Page;
/**
 * 红包/体验金操作记录
 * @author Administrator
 *
 */
@Service
public class VirtualRecordServiceImpl implements VirtualRecordService {

	@Autowired
	private VirtualRecordDao  virtualRecordDao;

	@Override
	public List<VirtualRecord> queryByParams(Page<VirtualRecord> page) {
		return virtualRecordDao.selectByParams(page);
	}
	
	@Override
	public List<VirtualRecord> queryRecordByTime() {
		return virtualRecordDao.selectRecordByTime();
	}

	@Override
	public int modifyVirtualRecord(VirtualRecord virtualRecord) {
		return virtualRecordDao.updateVirtualRecord(virtualRecord);
	}

	@Override
	public int modifyBatchVirtualRecords(List<VirtualRecord> list) {
		return virtualRecordDao.updateBatchVirtualRecords(list);
	}
}
