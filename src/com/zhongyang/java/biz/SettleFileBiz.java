package com.zhongyang.java.biz;

import com.zhongyang.java.system.Message;

/**
 * 
* @Title: SettleFileBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:对账业务接口 
* @author 苏忠贺   
* @date 2016年1月7日 下午1:16:48 
* @version V1.0
 */
public interface SettleFileBiz {
	/**
	 * 
	* @Title: settleRecharge 
	* @Description:充值对账 
	* @return String    返回类型 
	* @throws
	 */
	public Message settleRecharge(String number,String startDate,String endDate);
	/**
	 * 
	* @Title: settleWithdraw 
	* @Description:提现对账
	* @return String    返回类型 
	* @throws
	 */
	public Message settleWithdraw(String number,String startDate,String endDate);
	/**
	 * 
	* @Title: settleZTransfer 
	* @Description:转账对账 
	* @return String    返回类型 
	* @throws
	 */
	public Message settleTransfer(String number,String startDate,String endDate);
	/**
	 * 
	* @Title: settleTender 
	* @Description:标的转账对账 
	* @return String    返回类型 
	* @throws
	 */
	public Message settleTender(String number,String startDate,String endDate);
}
