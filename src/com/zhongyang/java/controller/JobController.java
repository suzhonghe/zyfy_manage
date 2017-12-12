package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.JobBiz;
import com.zhongyang.java.pojo.Job;
import com.zhongyang.java.system.Message;

@Controller
public class JobController extends BaseController{
	
	@Autowired
	private JobBiz jobBiz;
	
	@RequestMapping(value="/addJob")
	public @ResponseBody Message addJob(Job job){
		return jobBiz.addJob(job);
	}
	
	@RequestMapping(value="/modifyJobByParams")
	public @ResponseBody Message modifyByParams(Job job){
		return jobBiz.modifyByParams(job);
	}
	
	@RequestMapping(value="/deleteJobByParams")
	public @ResponseBody Message deleteByParams(Job job){
		return jobBiz.deleteByParams(job);
	}
	
	@RequestMapping(value="/queryJobByParams")
	public @ResponseBody Job queryByParams(Job job){
		return jobBiz.queryByParams(job);
	}
}
