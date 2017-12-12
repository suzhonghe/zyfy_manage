package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UserFundDao;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.UserFundService;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月2日 上午9:44:07 
 * 类说明:用户资金类方法
 */
@Service
public class UserFundServiceImpl implements UserFundService {

	@Autowired
	private UserFundDao userFundDao;

	@Override
	public UserFund byUserID(UserFund userFund) {

		return userFundDao.byUserID(userFund);
	}
	@Override
	public void modifyUserFund(UserFund userFund) throws Exception {
		userFundDao.updateUserFund(userFund);
	}
	public int updateUserFundByUserID(UserFund userFund)  throws Exception{
		 return  userFundDao.updateUserFundByUserID(userFund);
	}

    @Override
    public int reduceAmountByUser(UserFund userFund) {
        return userFundDao.reduceAmountByUser(userFund);
    }

    @Override
    public int addAmountByUser(UserFund userFund) {
        return userFundDao.addAmountByUser(userFund);
    }
}
