package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.UserGroupBiz;
import com.zhongyang.java.pojo.UserGroup;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.UserGroupVO;
@Controller
public class UserGroupController extends BaseController{
	
	@Autowired
	private UserGroupBiz userGroupBiz;
	
	@RequestMapping(value="/addUserGroup",method=RequestMethod.POST)
	public @ResponseBody Message addUserGroup(UserGroup userGroup){
		return userGroupBiz.addUserGroup(userGroup);
	}
	
	@RequestMapping(value="/modifyUserGroup",method=RequestMethod.POST)
	public @ResponseBody Message modifyUserGroup(UserGroup userGroup){
		return userGroupBiz.modifyUserGroup(userGroup);
	}
	@RequestMapping(value="/queryByUserGroup",method=RequestMethod.POST)
	public @ResponseBody List<UserGroupVO> queryByParams(UserGroup userGroup){
		return userGroupBiz.queryByParams(userGroup);
	}
}
