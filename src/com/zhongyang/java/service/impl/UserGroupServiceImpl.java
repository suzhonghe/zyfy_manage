package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UserGroupDao;
import com.zhongyang.java.pojo.UserGroup;
import com.zhongyang.java.service.UserGroupService;
/**
 * 
* @Title: UserGroupServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:用户组service实现
* @author 苏忠贺   
* @date 2016年3月7日 下午3:48:54 
* @version V1.0
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {
	
	@Autowired
	private UserGroupDao userGroupDao;

	@Override
	public int addUserGroup(UserGroup userGroup) throws Exception {
		return userGroupDao.insertUserGroup(userGroup);
	}

	@Override
	public int modifyUserGroup(UserGroup userGroup) throws Exception {
		return userGroupDao.updateUserGroup(userGroup);
	}

	@Override
	public List<UserGroup> queryByParams(UserGroup userGroup) throws Exception {
		return userGroupDao.selectByParams(userGroup);
	}

}
