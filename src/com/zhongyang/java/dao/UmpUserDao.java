package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.UmpUser;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年7月1日 上午10:13:36
* 类说明
*/
public interface UmpUserDao {

	public UmpUser getOldUserIdByAccountName(String accountName);

}
