package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.FailedLoanOrder;

/**
 * 
* @Title: FailedLoanOrder.java 
* @Package com.zhongyang.java.service 
* @Description:流标订单业务接口 
* @author 苏忠贺   
* @date 2015年12月28日 上午9:09:28 
* @version V1.0
 */
public interface FailedLoanOrderService {
	/**
	 * 
	* @Title: addFailedLoanOrder 
	* @Description:添加流标记录 
	* @return void    返回类型 
	* @throws
	 */
	public void addFailedLoanOrder(FailedLoanOrder failedLoanOrder)throws Exception;
	/**
	 * 
	* @Title: queryByOrderId 
	* @Description:根据订单号查询流标记录 
	* @return FailedLoanOrder    返回类型 
	* @throws
	 */
	public FailedLoanOrder queryByOrderId(String orderId)throws Exception;
	/**
	 * 
	* @Title: queryByLoanId 
	* @Description:根据标的号查询流标记录
	* @return List<FailedLoanOrder>    返回类型 
	* @throws
	 */
	public List<FailedLoanOrder> queryByLoanId(String loanId)throws Exception;
	/**
	 * 
	* @Title: modifyFailedLoanOrder 
	* @Description:修改流标记录 
	* @return void    返回类型 
	* @throws
	 */
	public void modifyFailedLoanOrder(FailedLoanOrder failedLoanOrder)throws Exception;
}
	