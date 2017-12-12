package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.UserGroupBiz;
import com.zhongyang.java.pojo.UserGroup;
import com.zhongyang.java.service.UserGroupService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.UserGroupVO;

/**
 * 
* @Title: UserGroupBizImpl.java 
* @Package com.zhongyang.java.biz.impl 
* @Description: 用户组业务实现
* @author 苏忠贺   
* @date 2016年3月7日 下午3:52:41 
* @version V1.0
 */
@Service
public class UserGroupBizImpl implements UserGroupBiz{
	
	private static Logger logger = Logger.getLogger(UserGroupBizImpl.class);
	
	@Autowired
	private UserGroupService userGroupService;

	@Override
	public Message addUserGroup(UserGroup userGroup){
		Message message=new Message();
		try {
			userGroup.setId(UUID.randomUUID().toString().toUpperCase());
			userGroup.setTimecreated(new Date());
			userGroupService.addUserGroup(userGroup);
			message.setCode(1);
			message.setMessage("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "用户组添加失败");
		}
		return message;
	}

	@Override
	public Message modifyUserGroup(UserGroup userGroup){
		Message message=new Message();
		try {
			if(userGroup==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得修改信息");
			}
			userGroup.setTimelastupdated(new Date());
			userGroupService.modifyUserGroup(userGroup);
			message.setCode(1);
			message.setMessage("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "用户组修改失败");
		}
		return message;
	}

	@Override
	public List<UserGroupVO> queryByParams(UserGroup userGroup){
		try {
			List<UserGroup> groups = userGroupService.queryByParams(userGroup);
			List<UserGroupVO>userGroupVO=new ArrayList<UserGroupVO>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			for (UserGroup ug : groups) {
				UserGroupVO ugv=new UserGroupVO();
				ugv.setId(ug.getId());
				ugv.setName(ug.getName());
				ugv.setDescription(ug.getDescription());
				ugv.setTimecreated(sdf.format(ug.getTimecreated()));
				userGroupVO.add(ugv);
			}
			return userGroupVO;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询用户组失败");
		}
	}

}
