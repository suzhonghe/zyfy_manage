package com.zhongyang.java.biz;

import org.springframework.web.multipart.MultipartFile;

import com.zhongyang.java.system.Message;

public interface PhototManageBiz {
	
	public String uploadPhoto(MultipartFile items_pic,String type,int name);
	
	public Message deletePhoto(String path,String type);

}
