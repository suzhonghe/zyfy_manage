package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.Job;

public interface JobService {

	public int addJob(Job job)throws Exception;
	
	public int modifyByParams(Job job)throws Exception;
	
	public int deleteByParams(Job job)throws Exception;
	
	public List<Job> queryByParams(Job job)throws Exception;
}
