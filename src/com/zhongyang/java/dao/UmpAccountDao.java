package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.User;

/**
 * 
* @Title: UmpAccountDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 联动账户信息 
* @author 苏忠贺   
* @date 2015年12月7日 下午5:04:52 
* @version V1.0
 */
public interface UmpAccountDao {
	
	/*
	 * 根据UmpAccount查询联动信息
	 */
	public UmpAccount getUmpAccountByUserId(UmpAccount umpAccount)throws Exception;
	
	public UmpAccount selectByParams(UmpAccount umpAccount)throws Exception;
	
	public User selectByAccount(UmpAccount umpAccount)throws Exception;
}
