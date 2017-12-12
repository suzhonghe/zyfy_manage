package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.UserFund;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月2日 上午9:44:49 类说明:用户资金
 */
public interface UserFundDao {
    /**
     *
     * @return:Obiect,
     * 通过用户Id查询用户资金表
     */
	public UserFund byUserID(UserFund userFund);
	/**
	 * 

	* @Title: updateUserFund 
	* @Description:修改账户资金
	* @return void    返回类型 
	* @throws
	 */
	public void updateUserFund(UserFund userFund)throws Exception;

	 /* @param UserFund
	 * @return
	 */
	public int updateUserFundByUserID(UserFund UserFund);



    public int reduceAmountByUser(UserFund userFund);

    public int addAmountByUser(UserFund userFund);
}
