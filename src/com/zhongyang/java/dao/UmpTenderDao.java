package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.UmpTender;

/**
 * 
* @Title: LoanDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 联动投标信息DAO
* @author 苏忠贺   
* @date 2015年12月3日 下午3:46:27 
* @version V1.0
 */
public interface UmpTenderDao {
	
	/*
	 *添加一条UmpTender记录
	 */
	public void insertOneUmpTender(UmpTender umpTender)throws Exception;

	public UmpTender getUmpTender(String loanId) throws Exception;
	
	public UmpTender selectByParams(UmpTender umpTender) throws Exception;
	
	public List<UmpTender> selecAll();
	
	/*
	 * 根据标的Id查询umpTender
	 */
	public UmpTender selectUmpTenderByLoanId(String loanId)throws Exception;
	/**
	 * 
	* @Title: updateUmpTender 
	* @Description: 修改标的账户金额
	* @return void    返回类型 
	* @throws
	 */
	public void updateUmpTender(UmpTender umpTender)throws Exception;
	
	public int updateAmound(UmpTender umpTender) throws Exception;
}
