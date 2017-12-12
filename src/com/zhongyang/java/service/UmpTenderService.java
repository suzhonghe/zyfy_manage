package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.UmpTender;

/**
 * 
* @Title: Loan.java 
* @Package com.zhongyang.java.service 
* @Description:联动业务接口 
* @author 苏忠贺   
* @date 2015年12月3日 下午5:22:23 
* @version V1.0
 */
public interface UmpTenderService {
	/*
	 *添加一条UmpTender记录
	 */
	public void addOneUmpTender(UmpTender umpTender)throws Exception;


    public int updateAmound(UmpTender umpTender) throws Exception;
    
    public UmpTender queryByParams(UmpTender umpTender) throws Exception;
    
    public List<UmpTender> queryAll();

	/*
	 * 根据标的Id查询umpTender
	 */
	public UmpTender queryUmpTenderByLoanId(String loanId)throws Exception;
    
    public UmpTender getUmpTender(String laonId) throws Exception;
    /**
     * 
    * @Title: modifyUmpTender 
    * @Description:修改标的账户金额
    * @return void    返回类型 
    * @throws
     */
    public void modifyUmpTender(UmpTender umpTender)throws Exception;
}
