package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.JobDao;
import com.zhongyang.java.pojo.Job;
import com.zhongyang.java.service.JobService;

@Service
public class JobServiceImpl implements JobService {
	
	@Autowired
	private JobDao jobDao;

	@Override
	public int addJob(Job job) throws Exception {
		return jobDao.insertJob(job);
	}

	@Override
	public int modifyByParams(Job job) throws Exception {
		return jobDao.updateByParams(job);
	}

	@Override
	public int deleteByParams(Job job) throws Exception {
		return deleteByParams(job);
	}

	@Override
	public List<Job> queryByParams(Job job) throws Exception {
		return jobDao.selectByParams(job);
	}

}
