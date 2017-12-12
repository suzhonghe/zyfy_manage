package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.UserGroup;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.UserGroupVO;

/**
 * 
* @Title: UserGroupBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:用户组业务处理
* @author 苏忠贺   
* @date 2016年3月7日 下午3:51:44 
* @version V1.0
 */
public interface UserGroupBiz {
	/**
	 * 
	* @Title: addUserGroup 
	* @Description:添加用户组
	* @return int    返回类型 
	* @throws
	 */
	public Message addUserGroup(UserGroup userGroup);
	/**
	 * 
	* @Title: modifyUserGroup 
	* @Description:修改用户组
	* @return int    返回类型 
	* @throws
	 */
	public Message modifyUserGroup(UserGroup userGroup);
	/**
	 * 
	* @Title: queryByParams 
	* @Description:查询用户组列表
	* @return List<UserGroup>    返回类型 
	* @throws
	 */
	public List<UserGroupVO> queryByParams(UserGroup userGroup);
}
