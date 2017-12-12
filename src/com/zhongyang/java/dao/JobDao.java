package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.Job;

public interface JobDao {

	public int insertJob(Job job)throws Exception;
	
	public int updateByParams(Job job)throws Exception;
	
	public int deleteByParams(Job job)throws Exception;
	
	public List<Job> selectByParams(Job job)throws Exception;
}
