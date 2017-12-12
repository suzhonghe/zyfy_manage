package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.vo.LoanVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月2日 下午5:18:24
* 类说明：用户ump账户
*/
public interface UmpAccountBiz {
    
	/*
     *根据用户Id获取用户umpAccount信息 
     */
	public String queryInfoByUmpAccount(LoanVo loanVo);
}
