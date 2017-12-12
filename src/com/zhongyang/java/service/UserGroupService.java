package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.UserGroup;

/**
 * 
* @Title: UserGroupService.java 
* @Package com.zhongyang.java.service 
* @Description:用户组service
* @author 苏忠贺   
* @date 2016年3月7日 下午3:47:33 
* @version V1.0
 */
public interface UserGroupService {
	/**
	 * 
	* @Title: addUserGroup 
	* @Description:添加用户组
	* @return int    返回类型 
	* @throws
	 */
	public int addUserGroup(UserGroup userGroup)throws Exception;
	/**
	 * 
	* @Title: modifyUserGroup 
	* @Description:修改用户组
	* @return int    返回类型 
	* @throws
	 */
	public int modifyUserGroup(UserGroup userGroup)throws Exception;
	/**
	 * 
	* @Title: queryByParams 
	* @Description:查询用户组列表
	* @return List<UserGroup>    返回类型 
	* @throws
	 */
	public List<UserGroup> queryByParams(UserGroup userGroup)throws Exception;
}
