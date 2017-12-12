package com.zhongyang.java.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.JobBiz;
import com.zhongyang.java.pojo.Job;
import com.zhongyang.java.service.JobService;
import com.zhongyang.java.system.Message;

@Service
public class JobBizImpl implements JobBiz {
	
	@Autowired
	private JobService jobService;

	@Override
	public Message addJob(Job job){
		Message message=new Message();
		try {
			int count = jobService.addJob(job);
			if(count>0){
				message.setCode(0);
				message.setMessage("添加成功");
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Message modifyByParams(Job job){
		Message message=new Message();
		try {
			int count = jobService.modifyByParams(job);
			if(count>0){
				message.setCode(0);
				message.setMessage("修改成功");
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Message deleteByParams(Job job){
		Message message=new Message();
		try {
			int count = jobService.deleteByParams(job);
			if(count>0){
				message.setCode(0);
				message.setMessage("删除成功");
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Job queryByParams(Job job){
		try {
			Job jo=new Job();
			jo.setLevel("0");
			List<Job> result = jobService.queryByParams(jo);
			return result.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
