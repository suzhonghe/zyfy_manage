package com.zhongyang.java.biz;
import com.zhongyang.java.pojo.SettleFileRecord;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.SettleFileRecordVO;

/**
 * 
* @Title: SettleFileRecordService.java 
* @Package com.zhongyang.java.dao 
* @Description:对账记录Service
* @author 苏忠贺   
* @date 2016年3月3日 下午4:26:31 
* @version V1.0
 */
public interface SettleFileRecordBiz {

	public Message addSettleFileRecord(SettleFileRecord record);
	
	public Message modifyByParams(SettleFileRecord record);
	
	public Message deleteByParams(SettleFileRecord record);
	
	public PagerVO<SettleFileRecordVO> queryByParams(PagerVO<SettleFileRecord> pager);
}
