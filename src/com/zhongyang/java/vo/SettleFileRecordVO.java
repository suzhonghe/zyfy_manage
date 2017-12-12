package com.zhongyang.java.vo;

import com.zhongyang.java.vo.settle.SettleStatus;
import com.zhongyang.java.vo.settle.SettleType;

/**
 * 
* @Title: SettleFileRecord.java 
* @Package com.zhongyang.java.pojo 
* @Description:对账记录实体
* @author 苏忠贺   
* @date 2016年3月3日 下午4:22:32 
* @version V1.0
 */
public class SettleFileRecordVO {
	
	private String id;
	
	private String settleDate;//对账日期
	
	private String settleType;//对账类型
	
	private String status;//对账状态
	
	private String applyTime;//申请对账时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
}
