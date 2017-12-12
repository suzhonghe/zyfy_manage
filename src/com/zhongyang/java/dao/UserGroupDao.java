package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.UserGroup;

/**
 * 
* @Title: UserGroupDao.java 
* @Package com.zhongyang.java.dao 
* @Description:用户组DAO
* @author 苏忠贺   
* @date 2016年3月7日 下午3:27:46 
* @version V1.0
 */
public interface UserGroupDao {
	/**
	 * 
	* @Title: insertUserGroup 
	* @Description:添加用户组
	* @return int    返回类型 
	* @throws
	 */
	public int insertUserGroup(UserGroup userGroup)throws Exception;
	/**
	 * 
	* @Title: updateUserGroup 
	* @Description:修改用户组
	* @return int    返回类型 
	* @throws
	 */
	public int updateUserGroup(UserGroup userGroup)throws Exception;
	/**
	 * 
	* @Title: selectByParams 
	* @Description:查询用户组列表
	* @return List<UserGroup>    返回类型 
	* @throws
	 */
	public List<UserGroup> selectByParams(UserGroup userGroup)throws Exception;
}
