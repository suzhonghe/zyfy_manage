package com.zhongyang.java.service;

import com.zhongyang.java.pojo.UserFund;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午5:32:23
* 类说明：用户资金
*/
public interface UserFundService {
    /**
     * 
    * @Title: modifyUserFund 
    * @Description:修改资金账户 
    * @return void    返回类型 
    * @throws
     */
    public void modifyUserFund(UserFund userFund)throws Exception;

    public UserFund byUserID(UserFund userFund) throws Exception;
    
    public int updateUserFundByUserID(UserFund userFund)  throws Exception;

    public int reduceAmountByUser(UserFund userFund);

    public int addAmountByUser(UserFund userFund);
}
