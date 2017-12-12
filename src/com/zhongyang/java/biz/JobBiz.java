package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.Job;
import com.zhongyang.java.system.Message;

public interface JobBiz {

	public Message addJob(Job job);
	
	public Message modifyByParams(Job job);
	
	public Message deleteByParams(Job job);
	
	public Job queryByParams(Job job);
}
