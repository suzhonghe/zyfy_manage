package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhongyang.java.biz.PhototManageBiz;
import com.zhongyang.java.system.Message;

@Controller
public class PhototManageController extends BaseController {
	
	@Autowired
	private PhototManageBiz photoManageBiz;

	@RequestMapping(value="/uploadPhoto")
	public @ResponseBody String upload(@RequestBody MultipartFile items_pic,String type,int name){
		return photoManageBiz.uploadPhoto(items_pic, type,name);
	}
	
	@RequestMapping(value="/deletePhoto")
	public @ResponseBody Message deletePhoto(String path,String type){
		return photoManageBiz.deletePhoto(path, type);
	}
}
