package com.zhongyang.java.system;

/**
 * @author Matthew(于文扬)
 * @date 2015-11-24 14:44
 * @version 1.0
 * 系统操作
 */
public enum SystemEnum {
	/**
	 * 登录成功
	 */
	LOGIN_SUCCESS(1000),
	/**
	 * 没有登录
	 */
	USER_NOLOGIN(1003),
	/**
	 * 没有开通第三方账户
	 */
	NOREGISTERED_UMPACCOUNT(1004),
	
	/**
	 * 用户名或密码错误
	 */
	USER_PASSWORD_VAILD_FAILURE(1001),
	/**
	 * 用户不存在  
	 */
	USER_NOT_EXISTS(1002),
	LOGIN_ERROR(1017),
	/**
	 * 数据库连接失败
	 */
	SERVER_REFUSED(9001),
	/**
	 * MD5加密错误
	 */
	EN_CODE_MD5_EXCEPTION(9002),
	/**
	 * 文件读写错误
	 */
	FILE_READ_WRITE_EXCEPTION(9003),
	/**
	 * 存款准备失败
	 */
	PREPARE_DEPOSIT_FAILED(8001),
	/**
	 * 暂无可结算的标的
	 */
	NO_INFORMATIONLOAN(6666),
	
	/**
	 * 没有投资的用户
	 */
	NO_UUSERINVEST(6667),
	
	
	/**
	 * 不知道用户名字
	 */
	UNKNOW_NAME(6668),
	
	/**
	 * 不知道用户名字
	 */
	USETTLE_FAIL(7777),
	
	/**
	 * 未知异常,请与管理员联系
	 */
	UNKNOW_EXCEPTION(9999);
	
    
	 private final Integer value;  
	    
	  /**
	 * @param value
	 */
	private SystemEnum(Integer value){  
	      this.value=value;  
	  }  
	    
	  /**
	 * @return
	 */
	public Integer value(){  
	      return value;  
	  }  
}
