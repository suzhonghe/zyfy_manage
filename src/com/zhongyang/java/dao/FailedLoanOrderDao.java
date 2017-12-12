package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.FailedLoanOrder;

/**
 * 
* @Title: FailedLoanOrder.java 
* @Package com.zhongyang.java.dao 
* @Description:流标订单Dao 
* @author 苏忠贺   
* @date 2015年12月28日 上午9:09:28 
* @version V1.0
 */
public interface FailedLoanOrderDao {
	/**
	 * 
	* @Title: insertFailedLoanOrder 
	* @Description: 添加流标记录 
	* @return void    返回类型 
	* @throws
	 */
	public void insertFailedLoanOrder(FailedLoanOrder failedLoanOrder)throws Exception;
	/**
	 * 
	* @Title: selectByOrderId 
	* @Description:根据订单号查询流标记录 
	* @return FailedLoanOrder    返回类型 
	* @throws
	 */
	public FailedLoanOrder selectByOrderId(String orderId)throws Exception;
	/**
	 * 
	* @Title: selectByLoanId 
	* @Description:根据标的ID查询流标记录
	* @return List<FailedLoanOrder>    返回类型 
	* @throws
	 */
	public List<FailedLoanOrder> selectByLoanId(String loanId)throws Exception;
	/**
	 * 
	* @Title: updateFailedLoanOrder 
	* @Description:根据订单号修改流标记录
	* @return void    返回类型 
	* @throws
	 */
	public void updateFailedLoanOrder(FailedLoanOrder failedLoanOrder)throws Exception;
}
	