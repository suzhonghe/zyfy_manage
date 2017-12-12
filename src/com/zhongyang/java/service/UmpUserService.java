package com.zhongyang.java.service;

import com.zhongyang.java.pojo.UmpUser;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年7月1日 上午10:11:31
* 类说明
*/
public interface UmpUserService {
  
	public UmpUser getOldUserIdByAccountName(String accountName) throws Exception;
}
