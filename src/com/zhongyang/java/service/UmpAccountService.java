package com.zhongyang.java.service;

import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.User;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月2日 下午5:23:29
* 类说明:UmpAccountService
*/
public interface UmpAccountService {
	
	/*
	 * 根据UmpAccount查询联动信息
	 */
	public UmpAccount getUmpAccountByUserId(UmpAccount umpAccount)throws Exception;
	
	public UmpAccount queryByParams(UmpAccount umpAccount)throws Exception;
	
	public User queryByAccount(UmpAccount umpAccount)throws Exception;
}
