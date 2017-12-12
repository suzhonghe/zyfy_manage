package com.zhongyang.java.pojo;

import java.util.Date;

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
public class SettleFileRecord {
	
	private String id;
	
	private Date settleDate;//对账日期
	
	private SettleType settleType;//对账类型
	
	private SettleStatus status;//对账状态
	
	private Date applyTime;//申请对账时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public SettleType getSettleType() {
		return settleType;
	}

	public void setSettleType(SettleType settleType) {
		this.settleType = settleType;
	}

	public SettleStatus getStatus() {
		return status;
	}

	public void setStatus(SettleStatus status) {
		this.status = status;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
}
